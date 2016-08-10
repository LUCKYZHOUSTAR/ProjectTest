package test;


public class Test {

    protected Test() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
    }
}
