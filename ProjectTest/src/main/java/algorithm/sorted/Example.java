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
                        exch(a, j + group, j);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Comparable[] x = { 3, 6, 2, 4, 1, 5, 9, 10, 15, 26, 23 };
        shell_test6(x);

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

    //选择排序，先拿第一个元素与所有的元素进行比较，找到最小的元素后，与第一位的元素进行交换位置
    public static void test4(Comparable[] a) {

        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[i])) {
                    exch(a, i, j);
                }
            }
        }
    }

    //插入排序，是先把一个位置放置上后，后面的元素是一个一个放置上去的，只要放置上去就是已经排好序的元素
    public static void test5(Comparable[] a) {

        //第一层循环要放置的元素,但是要从第二个元素开始
        for (int i = 1; i < a.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (less(a[j + 1], a[j])) {
                    exch(a, j + 1, j);
                } else {
                    break;
                }
            }
        }
    }

    //希尔排序是把变量自缩小来递减，让变量值内交换比较，直到变为1即可
    public static void shell_test6(Comparable[] a) {

        //5
        for (int i = a.length / 2; i > 0; i = i / 2) {
            //5,6,7,8,9,10
            for (int j = i; j < a.length; j++) {
                //0,1,2,3,4,5
                for (int z = j - i; z >= 0; z = z - i) {
                    if (less(a[z], a[z + i])) {
                        exch(a, z, z + i);
                    }
                }
            }

        }
    }
    
   
}
