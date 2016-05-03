package algorithm.sorted;

public class Example {

    public static void select_sorted(Comparable[] a) {

        //将a[]按照升序排列
        int N = a.length;
        for (int i = 0; i < N; i++) {
            //将a[i]和a[i+1.. N]中最小的元素交换
            int min = i;//最小元素的索引
            //从i+1开始寻找最小元素的索引
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min]))
                    min = j;
                exch(a, i, min);
            }
        }
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i] + "");
        }
    }

    public static boolean isSorted(Comparable[] a) {
        //测试数组元素是否有序
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    public static void insertSorted(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                } else {
                    //如果不小于的话，直接放置到这里即可，由于之前是已经排好序的了
                    break;
                }
            }
        }
    }

    public static void shell_sort(int[] unsorted, int len) {
        int group, i, j, temp;
        //增量值分组
        for (group = len / 2; group > 0; group /= 2) {
            for (i = group; i < len; i++) {
                for (j = i - group; j >= 0; j -= group) {
                    if (unsorted[j] > unsorted[j + group]) {
                        temp = unsorted[j];
                        unsorted[j] = unsorted[j + group];
                        unsorted[j + group] = temp;
                    }
                }
            }
        }
    }

    
    
    public static void test3(Comparable[] a, int len) {
        //定义增量后
        for (int group = len / 2; group > 0; group = group / 2) {
            //从增量开始与小增量的数目进行比较
            for (int i = group; i < len; i++) {
                //从头开始的那个元素
                for (int j = i - group; j >= 0; j = j - group) {
                    if (less(a[j + group], a[j])) {
                        exch(a, j+group, j);
                    }
                }
            }
        }
    }

    
    
    
    public static void main(String[] args) {
        Comparable[] x = { 3, 6, 2, 4, 1, 5, 9, 10, 15, 26, 23 };
        test3(x, 11);

        for (Comparable i : x) {
            System.out.println(i);
        }
    }

    public static void test(Comparable[] a) {

        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[i])) {
                    exch(a, i, j);
                }
            }
        }
    }

    public static void test2(Comparable[] a) {
        //由于是插入排序，所以留第一个位置，后面元素是插入进去的
        for (int i = 1; i < a.length; i++) {
            //拿要插入的元素与前面的元素进行比较
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                } else {
                    //因为是已经排好序了
                    break;
                }
            }
        }
    }
}
