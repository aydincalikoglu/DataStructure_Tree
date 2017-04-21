import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Q3Main {
    public static void main(String[] args) {

        FamilyTree ft=new FamilyTree("Hasan");

        //TEST Family Tree add metod
        try {
            BufferedReader bR;
            bR = new BufferedReader(new FileReader("family.txt"));
            String line="";

            if ((line= bR.readLine()) != null)
                ft=new FamilyTree(line.trim().replace(" ",""));
            while ((line= bR.readLine()) != null)
            {
                String[] entrs=line.trim().replace(" ","").split(",");
                if (entrs.length==3)
                    ft.add(entrs[0],entrs[1],entrs[2]);
            }
            bR.close();
        } catch(IOException e) {
            System.out.println("File Okuma Hatası");
            e.printStackTrace();
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Eklenecek Kişinin Ebeveyni bulunamadı. ");
        }



        System.out.println(" ---TEST Family Tree - Level Order Iterator --- ");
        Iterator it3= ft.levelOrderIterator();
        while (it3.hasNext())
        {
            System.out.println(it3.next());
        }

    }
}
