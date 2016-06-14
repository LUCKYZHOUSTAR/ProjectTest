/**
 * 
 */
package com.basic;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import weblogic.utils.collections.TreeMap;

/** 
* @ClassName: SortedTest 
* @Description: 
* @author LUCKY
* @date 2016年6月1日 上午9:31:34 
*  
*/
public class SortedTest {

    public static void main(String[] args) {
        SortedSet<UserTest> userSet = new TreeSet<>();
        SortedMap<UserTest, Integer> userMap = new TreeMap<>();
        UserTest userTest = new UserTest("1", 1);
        UserTest userTest1 = new UserTest("2", 2);
        UserTest userTest2 = new UserTest("3", 3);
        UserTest userTest3 = new UserTest("4", 4);
        UserTest userTest4 = new UserTest("5", 5);
        
        
        userMap.put(userTest4, 1);
        userMap.put(userTest3, 2);
        userMap.put(userTest2, 3);
        userMap.put(userTest1, 4);
        userMap.put(userTest, 5);
        
        
        

        userSet.add(userTest);
        userSet.add(userTest4);
        userSet.add(userTest3);
        userSet.add(userTest2);
        userSet.add(userTest1);

        for (UserTest user : userSet) {
            System.out.println(user.getAge());
        }
        
        System.out.println("---------------------------");

        
        for(UserTest test:userMap.keySet()){
//            System.out.println(test.getAge());
            System.out.println(userMap.get(test));
        }
        
    }
}

class UserTest implements Comparable<UserTest> {

    public UserTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private int    age;

    @Override
    public int compareTo(UserTest o) {
        return this.age - o.age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}