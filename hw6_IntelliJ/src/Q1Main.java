import java.util.NoSuchElementException;

public class Q1Main {

    public static void main(String[] args) {


        Passengers p1 = new Passengers(1,1000,5,25);
        Passengers p2 = new Passengers(2,2000,5,25);
        Passengers p3 = new Passengers(10,500,10,36);
        Passengers p4 = new Passengers(3,500,10,36);
        Passengers p5 = new Passengers(4,300,10,36);
        Passengers p6 = new Passengers(5,300,5,36);
        Passengers p7 = new Passengers(6,200,2,65);
        Passengers p8 = new Passengers(7,200,2,36);


        try {

            BinaryHeap<Passengers> binaryHeap1 = new BinaryHeap<>(p1,null,null);
            BinaryHeap<Passengers> binaryHeap2 = new BinaryHeap<>(p2,null,null);
            BinaryHeap<Passengers> binaryHeap3 = new BinaryHeap<>(p3,binaryHeap1,null);
            BinaryHeap<Passengers> binaryHeap = new BinaryHeap<>(p4,binaryHeap2,binaryHeap3);

            binaryHeap.add(p5);
            binaryHeap.add(p6);
            binaryHeap.add(p7);
            binaryHeap.add(p8);


/*
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
            binaryHeap.add(new Integer(1));
            binaryHeap.add(new Integer(2));
            binaryHeap.add(new Integer(3));
            binaryHeap.add(new Integer(4));
            binaryHeap.add(new Integer(5));
            binaryHeap.add(new Integer(6));
            binaryHeap.add(new Integer(7));
            binaryHeap.add(new Integer(8));
            binaryHeap.add(new Integer(9));
            binaryHeap.add(new Integer(10));
            binaryHeap.add(new Integer(11));
            binaryHeap.add(new Integer(12));
            binaryHeap.add(new Integer(13));
            binaryHeap.add(new Integer(14));
*/

            System.out.println(binaryHeap.toString());

            binaryHeap.remove();
            System.out.println(binaryHeap.toString());

            binaryHeap.poll();
            System.out.println(binaryHeap.toString());

            binaryHeap.remove();
            System.out.println(binaryHeap.toString());

            binaryHeap.remove();
            System.out.println(binaryHeap.toString());

            binaryHeap.remove();
            System.out.println(binaryHeap.toString());




        }
        catch (NullPointerException e)
        {
            System.out.println("hatalı argument.");
            e.printStackTrace();
        }
        catch (NoSuchElementException e)
        {
            System.out.println("eleman yok.");
            e.printStackTrace();
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }



    }
}
