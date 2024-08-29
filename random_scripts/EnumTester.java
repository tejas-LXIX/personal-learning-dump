public class EnumTester {
    public static void main(String args[]) {
        System.out.println(Mart.valueOf("B2B"));
//        System.out.println(Mart.valueOf("B212B"));
        if(Mart.valueOf("B2B").equals(Mart.B2B))
            System.out.println("Yes it works");
    }
}
