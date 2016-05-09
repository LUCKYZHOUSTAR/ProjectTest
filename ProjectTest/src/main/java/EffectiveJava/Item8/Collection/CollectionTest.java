/**     
 * @FileName: CollectionTest.java   
 * @Package:EffectiveJava.Item8.Collection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 下午2:27:32   
 * @version V1.0     
 */
package EffectiveJava.Item8.Collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.junit.Test;

/**  
 * @ClassName: CollectionTest   
 * @Description: java中的集合测试类操作
 * @author: LUCKY  
 * @date:2016年4月19日 下午2:27:32     
 */
public class CollectionTest {

    
    public void CollectionTest() {
    }

    @Test
    public void ArrayListTest() {
        // 底层的数据结构使用的是数组结构（数组长度是可变的百分之五十延长）（
        //特点是查询很快，但增删较慢）线程不同步
        //集合中的元素可以重复
        List<String> strList = new ArrayList<>();
        System.out.println("Nihaoma");
        strList.add("AA");
        strList.add("BB");
        strList.add("CC");
        strList.add("DD");
        strList.add("EE");
        System.out.println(strList.contains("AA"));
        int c = strList.indexOf("DD");
        //类似于下面的查询比较的快，内部是数组结构
        System.out.println(strList.get(2));
        strList.set(2, "你好吗");
        strList.subList(3, 4);
    }

    public void linkListTest() {
        /*
         * LinkedList不同于前面两种List，它不是基于Array的，
         * 所以不受Array性能的限制。它每一个节点（Node）都包含两方面的内容：
         * 1.节点本身的数据（data）；
         * 2.下一个节点的信息（nextNode）。
         * 所以当对LinkedList做添加，删除动作的时候就不用像基于Array的List一样，
         * 必须进行大量的数据移动。只要更改nextNode的相关信息就可以实现了。
         * 这就是LinkedList的优势。
         */
        List<String> strList = new LinkedList<>();
        strList.add("AA");
        strList.remove(0);

    }

    @Test
    public void vectorTest() {
        /*
         *  基于Array的List，其实就是封装了Array所不具备的一些功能方便我们使用，
         *  它不可能不受Array的限制。性能也就不可能超越Array。
         *  所以，在可能的情况下，我们要多运用Array。另外很重要的一点就是Vector
                    ：sychronized”的，这个也是Vector和ArrayList的唯一的区别。
         */
        List<String> vector = new Vector<>();
        vector.add("aa");
        vector.get(0);
    }

    
    
    @Test
    public void HashTableTest(){
        Set<String> table=new HashSet<>();
        table.add("cc");
        
    }
}
