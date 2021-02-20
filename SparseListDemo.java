public class SparseListDemo {
    public static void main(String[] args) {

        //With Integer
        SparseList<Integer> myList = new SparseList<>();
        System.out.println(myList.toString());

        myList.add(0);
        System.out.println(myList.toString());

        myList.add(1);
        myList.add(2);
        System.out.println(myList.toString());

        myList.add(4);
        myList.add(5);
        myList.add(3, 3);
        System.out.println(myList.toString());

        myList.add(9,9);

        System.out.println(myList.toString());

        System.out.println("");

        //With String
        SparseList<String> myTrophies = new SparseList<>();
        System.out.println(myTrophies.toString());

        myTrophies.add("Basketball");
        System.out.println(myTrophies.toString());

        myTrophies.add("GTA V Completed");
        myTrophies.add("RDR2 Completed");
        System.out.println(myTrophies.toString());

        myTrophies.add("Headshot");
        myTrophies.add("Best Costume Awards");
        myTrophies.add(3, "World Cup");
        System.out.println(myTrophies.toString());

        myTrophies.add(9,"Best Weeb Awards");

        System.out.println(myTrophies.toString());

        System.out.println("");

        //With Object
        SparseList<Object> myBucketList = new SparseList<>("Nothing");
        System.out.println(myBucketList.toString());

        myBucketList.add("Soccer");
        System.out.println(myBucketList.toString());

        myBucketList.add(2015);
        myBucketList.add("Naruto Storm 4");
        System.out.println(myBucketList.toString());

        myBucketList.add("Three");
        myBucketList.add("Best Friend");
        myBucketList.add(3, "Japan");
        System.out.println(myBucketList.toString());

        myBucketList.add(9,"Nine");

        System.out.println(myBucketList.toString());
    }
}
