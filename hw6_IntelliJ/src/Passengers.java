/**
 * Created by AyKo on 19.04.2017.
 */
public class Passengers implements Comparable<Passengers> {
    private int identityNumber;
    private double totalGain;
    private int totalFlyCount;
    int age;
    /*
    ID Eşit ise Kişiler Eşit
    Öncelik Sırası
        Firmanın Kişi Üzerinden Kazancı Yüksek Olana öncelik
        Kazanç Eşit ise
            Uçuş Sayısı az olana öncelik
            uçuş Sayısı Eşit ise
                yaşlı olanlara öncelik
                    O da eşit ise
                        ilk gelene öncelik
     */

    @Override
    public int compareTo(Passengers o) {
        if (identityNumber==o.identityNumber)
            return 0;
        else if (totalGain<o.totalGain)
            return 1;
        else if (totalGain>o.totalGain)
            return -1;
        else
            if (totalFlyCount<o.totalFlyCount)
                return -1;
            else if (totalFlyCount>o.totalFlyCount)
                return 1;
            else
                if (age<o.age)
                    return 1;
                else
                    return -1;
    }

    public Passengers(int ID,double totalGain,int totalFlyCount,int age) {
        identityNumber=ID;
        this.totalGain=totalGain;
        this.totalFlyCount=totalFlyCount;
        this.age=age;
    }

    @Override
    public String toString() {
        return identityNumber+" "+totalGain+" "+totalFlyCount+" "+age;
    }
}
