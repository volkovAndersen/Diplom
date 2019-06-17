package tools;

public class Counter {
    private static int counter = 0;

    public static void plusCounter(){
        ++counter;
    }

    public static int getCounter() {
        return counter;
    }
    public static void countDown(){
        counter = 0;
    }
}
