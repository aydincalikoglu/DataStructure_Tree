import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Q2Main {
    public static void main(String[] args) throws FileNotFoundException {
        HuffmanTree hf=new HuffmanTree();


        PrintStream pS2=new PrintStream(new File("freq.txt"));
        for (int i = 0; i < 128; i++) {

            if(i<10)
                pS2.println(i + " " + (new Double(Math.pow(i, 2))).intValue());
            else if(i>65)
            {
                char ch = (char) i;
                pS2.println(ch + " " + (new Double(Math.pow(i, 2))).intValue());
            }
        }
        pS2.println("space 1000002");
        pS2.close();

        try {
            hf.readCodeFileBuildTree("freq.txt");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }

        try {

            /* TESTING */
            System.out.println((hf.encode("yi ga")));
            System.out.println(hf.decode(hf.encode("yi ga")));

            System.out.println((hf.encode("yi 123ga")));
            System.out.println(hf.decode(hf.encode("yi 123ga")));


            System.out.println((hf.encode("TESTING123avsqqqqqqqqqqqqsadasdasdasdwqczxc")));
            System.out.println(hf.decode(hf.encode("TESTING123avsqqqqqqqqqqqqsadasdasdasdwqczxc")));


        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        PrintStream pS=new PrintStream(new File("output.txt"));
        hf.printCode(pS);

    }
}
