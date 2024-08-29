public class Exceptions {
    public static void main(String[] args) {
        int a=73;
        int b=0;
        String str = null;
        try {
            System.out.println(a/b);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        System.out.println("reached here");
        try {
            System.out.println(str.length());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println("reached here part 2");
    }
}
