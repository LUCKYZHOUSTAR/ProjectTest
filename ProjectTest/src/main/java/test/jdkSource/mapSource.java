/**
 * 
 */
package test.jdkSource;

/**
 * @author LUCKY
 *
 */
public class mapSource {

}

class EntryTest<K,V> implements test.jdkSource.Entry<K,V>{

    final K key;
    V value;
    Entry<K, V> next;
    int hash;
    
    
    EntryTest(int h,K k,V v,Entry<K, V> n){
        value=v;
        next=n;
        key=k;
        hash=h;
    }
    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V newValue) {
        V oldValue=value;
        value=newValue;
        return oldValue;
    }
    
    
    
}
interface Entry<K,V>{
    K getKey();
    V getValue();
    V setValue(V value);
    boolean equals(Object o);
    int hashCode();
}
