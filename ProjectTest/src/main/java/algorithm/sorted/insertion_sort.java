package algorithm.sorted;

/**
 * @author LUCKY
 *插入排序算法
 */
public class insertion_sort {

    public static void insert_sort(int[] unsorted) {
        for (int i = 1; i < unsorted.length; i++) {
            if (unsorted[i - 1] > unsorted[i]) {
                int temp = unsorted[i];
                int j = i;
                while (j > 0 && unsorted[j - 1] > temp) {
                    unsorted[j] = unsorted[j - 1];
                    j--;
                }
                unsorted[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] x = { 6, 2, 4, 1, 5, 9 };
        insert_sort(x);

        for (int i : x) {
            System.out.println(i);
        }
    }

    /**
    *插入排序
    *@paramarr
    *@return
    */
    private static int[] insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    //TODO:
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    //接下来是无用功
                    break;
                }
            }
        }
        return arr;
    }
}
