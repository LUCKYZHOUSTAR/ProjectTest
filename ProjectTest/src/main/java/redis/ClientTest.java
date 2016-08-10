/**
 * 
 */
package redis;

import redis.clients.jedis.Jedis;

/**
 * @author LUCKY
 *
 */
public class ClientTest {

    public static void main(String[] args) {

        Jedis client = new Jedis("10.200.1.77", 22121);
        for (int i = 0; i < 10000; i++) {

            client.set("bb" + i, "bb" + i);
//            System.out.println(client.get("bb" + i));
        }

        //88停止了
        System.out.println("Ok");
    }
}
