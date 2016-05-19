/**
 * 
 */
package DistributeDataBase.client.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jdt.core.dom.ThisExpression;

/** 
* @ClassName: UniqId 
* @Description: 在加上一个前缀的话，会更加的安全了
* 当前时间毫秒级+ip+自增计数器+线程的hashcode
* @author LUCKY
* @date 2016年5月16日 下午7:55:49 
*  
*/
public class UniqId {

    private static char[]                  digits   = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static Map<Character, Integer> rDigits  = new HashMap<Character, Integer>(16);

    private static UniqId                  instance = new UniqId();

    private String                         hostAddr;
    private Random                         random   = new SecureRandom();
    private MessageDigest                  mHasher;

    private UniqTimer                      timer    = new UniqTimer();
    private ReentrantLock                  opLock   = new ReentrantLock();

    private UniqId() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            this.hostAddr = addr.getHostAddress();

        } catch (IOException e) {
            this.hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if ((this.hostAddr == null) || (this.hostAddr.length() == 0)
            || ("127.0.0.1".equals(this.hostAddr))) {
            this.hostAddr = String.valueOf(System.currentTimeMillis());
        }

        try {
            this.mHasher = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            this.mHasher = null;
        }

    }

    public static UniqId getInstance() {
        return instance;
    }

    public long getUniqTime() {
        return this.timer.getCurrentTime();
    }

    public String getUniqID() {
        StringBuffer sb = new StringBuffer();
        long t = this.timer.getCurrentTime();

        sb.append(t).append("-").append(this.random.nextInt(99) + 1000);
        sb.append("-");
        sb.append(this.hostAddr);
        sb.append(Thread.currentThread().hashCode());
        System.out.println("当前线程"+Thread.currentThread().hashCode());
        return sb.toString();
    }

    public String getUniqIDHashString() {
        return hashString(getUniqID());
    }

    public byte[] getUniqIDHash() {
        return hash(getUniqID());
    }

    public byte[] hash(String str) {
        this.opLock.lock();
        try {
            //md5是16位的
            byte[] bt = this.mHasher.digest(str.getBytes("UTF-8"));
            if ((null == bt) || (bt.length != 16)) {
                throw new IllegalArgumentException("md5 need");
            }
            return bt;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported utf-8 encoding", e);
        } finally {
            this.opLock.unlock();
        }
    }

    public byte[] hash(byte[] data) {
        this.opLock.lock();
        try {
            byte[] bt = this.mHasher.digest(data);
            if ((null == bt) || (bt.length != 16)) {
                throw new IllegalArgumentException("md5 need");
            }
            return bt;
        } finally {
            this.opLock.unlock();
        }
    }

    public String hashString(String str) {
        byte[] bt = hash(str);
        return bytes2string(bt);
    }

    public String hashBytes(byte[] str) {
        byte[] bt = hash(str);
        return bytes2string(bt);
    }

    public String bytes2string(byte[] bt) {
        int l = bt.length;

        char[] out = new char[l << 1];

        int i = 0;
        for (int j = 0; i < l; i++) {
            out[(j++)] = digits[((0xF0 & bt[i]) >>> 4)];
            out[(j++)] = digits[(0xF & bt[i])];
        }

        return new String(out);
    }

    public byte[] string2bytes(String str) {
        if (null == str) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (str.length() != 32) {
            throw new IllegalArgumentException("字符串长度必须是32");
        }

        byte[] data = new byte[16];
        char[] chs = str.toCharArray();
        for (int i = 0; i < 16; i++) {
            int h = ((Integer) rDigits.get(Character.valueOf(chs[(i * 2)]))).intValue();
            int l = ((Integer) rDigits.get(Character.valueOf(chs[(i * 2 + 1)]))).intValue();
            data[i] = ((byte) ((h & 0xF) << 4 | l & 0xF));
        }
        return data;
    }

    private static class UniqTimer {
        private AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());

        public long getCurrentTime() {
            return this.lastTime.getAndIncrement();
        }
    }

    public static void main(String[] args) {
        System.out.println(UniqId.getInstance().getUniqID());
        System.out.println(UniqId.getInstance().getUniqIDHashString());
    }
    

}
