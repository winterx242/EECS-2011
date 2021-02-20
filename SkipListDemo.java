public class SkipListDemo {
    public static void main(String[] args) {
        SkipList<Integer> myList = new SkipList<>();
        System.out.println(myList.toString());
        myList.add(0, 1);
        System.out.println(myList.toString());
        myList.add(1, 2);
        System.out.println(myList.toString());
        myList.add(0, 3);
        System.out.println(myList.toString());
        myList.add(8);
        System.out.println(myList.toString());

        System.out.println(myList.remove(1));
        System.out.println(myList.toString());

        myList.clear();
        System.out.println(myList.toString());

        System.out.println(myList.size());
    }
}
