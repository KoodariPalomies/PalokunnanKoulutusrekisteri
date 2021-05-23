package koulutusRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;


/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Relaatio                            | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   |
 * |                                                    |                   |
 * | - Ei tiedä Koulutusrekisteristä eikä               |                   |
 * |   käyttöliittymästä.                               |                   |
 * |                                                    |                   |
 * | - Tietää relaation kentät                          |                   |
 * | (Relaatiotunnus, Työntekijätunnus,                 |                   |
 * | Koulutuksen tunnus, Suoritettu, Umpeutuu).         |                   |
 * |                                                    |                   |
 * | - Osaa tarkistaa tietyn kentän oikeellisuuden      |                   |
 * |   (syntaksin).                                     |                   |
 * |                                                    |                   |
 * | - Osaa muuttaa 1|1|01.01.2021|... - merkkijonon    |                   |
 * |   relation tiedoiksi.                              |                   |
 * |                                                    |                   |  
 * | - Osaa antaa merkkijonona i:n kentän tiedot.       |                   |
 * |                                                    |                   |
 * | - Osaa laittaa merkkijonon i:neksi kentäksi.       |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * 
 * @author mitulint, tuomas.mikko.lintula@gmail.com
 * @version 1.0, 24.3.2021  / Huonosti ylläpidetty versiopäiväkirja....
 * @version 1.1, 30.4.2021  / HT6 testejä...
 * @version 1.2, 13.5.2021  / Lisäsin getKoulutusTunnusString()
 * @version 1.3, 20.5.2021  / Poistin turhat importit
 * @version 1.4, 20.5.2021  / Viimeistelyt
 * @version 1.5, 23.5.2021  / Lisätty setSuoritettu(), setUmpeutuu(), implements Cloneable, getKoulutus()
 */
public class Relaatio implements Cloneable {
    
    private int         relaatioTunnus;
    private int         tyontekijaTunnus;
    private int         koulutusTunnus;
    private String      koulutus;
    private String      suoritettu;
    private String      umpeutuu;
    
    private static int  seuraavaRelaatioTunnus = 1;   // Tällä saadaan uutta relaatiota luotaessa seuraava tunnus!

    
    /**
     * Alustetaan relaatio.
     */
    public Relaatio() {
        // EI vielä mitään
    }
    
    
    /**
     * Alustetaan tietyn työntekijän koulutus.
     * @param tyontekijaTunnus työntekijän viitenumero
     * @param koulutusTunnus koulutuksen viitenumero
     */
    public Relaatio(int tyontekijaTunnus, int koulutusTunnus) {
        this.tyontekijaTunnus = tyontekijaTunnus;
        this.koulutusTunnus = koulutusTunnus;
    }
    
    
    /**
     * Asettaa työntekijän koulutuksen nimen
     * @param koulutus työntekijän koulutuksen nimi
     * @return syötetty työntekijän koulutuksen nimi tai virheilmoitus
     */
    public String setKoulutus(String koulutus) {
        if (koulutus.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei numeroita ja erikoismerkkejä!";
        if (koulutus.matches("")) return "Ei tyhjäarvoa!";
        this.koulutus = koulutus;
            return null;
    }
    
    
    /**
     * Asettaa työntekijän koulutuksen suorituspäivämäärän
     * @param suoritettu työntekijän koulutuksen suorituspäivämäärä
     * @return syötetty työntekijän suorituspvm tai virheilmoitus
     */
    public String setSuoritettu(String suoritettu) {
        if (!suoritettu.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei kirjaimia ja erikoismerkkejä!";
        if (suoritettu.matches("")) return "Ei tyhjäarvoa!";
        this.suoritettu = suoritettu;
            return null;
    }
    
    
    /**
     * Asettaa työntekijän koulutuksen umpeutumispäivämäärän
     * @param umpeutuu työntekijän koulutuksen umpeutumispäivämäärä
     * @return syötetty työntekijän umpeutumispvm tai virheilmoitus
     */
    public String setUmpeutuu(String umpeutuu) {
        if (!umpeutuu.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei kirjaimia ja erikoismerkkejä!";
        if (umpeutuu.matches("")) return "Ei tyhjäarvoa!";
        this.umpeutuu = umpeutuu;
            return null;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot relaatiolle.
     * TODO: poista kun kaikki toimii
     */
    public void vastaaRelaatio() {
        suoritettu          = "2021";
        umpeutuu            = "2031";
    }
     
    
    /**
     * Lisätään relaatiotunnus, joka lisääntyy aina yhdellä.
     * @return seuraavan relaation tunnus
     * @example
     * <pre name="test">
     *   Relaatio rel1 = new Relaatio();
     *   rel1.getRelaatioTunnus() === 0;
     *   rel1.rekisteroi();
     *   Relaatio rel2 = new Relaatio();
     *   rel2.rekisteroi();
     *   int n1 = rel1.getRelaatioTunnus();
     *   int n2 = rel2.getRelaatioTunnus();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        relaatioTunnus = seuraavaRelaatioTunnus;
        seuraavaRelaatioTunnus++;
        return relaatioTunnus;
    }
    
    
    /**
     * Palautetaan relaation relaatiotunnus.
     * @return relaation relaatiotunnus
     */
    public int getRelaatioTunnus() {
        return relaatioTunnus;
    }
    
    
    /**
     * Palautetaan mille työntekijälle koulutus kuuluu
     * @return työntekijän tunnuksen
     */
    public int getTyontekijaTunnus() {
        return tyontekijaTunnus;
    }
    
    
    /**
     * Palautetaan mikä koulutus kuuluu työntekijälle
     * @return koulutuksen tunnus
     */
    public int getKoulutusTunnus() {
        return koulutusTunnus;
    }
    
    /**
     * Palautetaan mikä koulutus kuuluu työntekijälle
     * @return koulutuksen nimi
     */
    public String getKoulutus() {
        return koulutus;
    }
    
    
    /**
     * Palauttaa työntekijän koulutustunnuksen String-muodossa.
     * @return tyontekijan koulutustunnus
     */
    public String getKoulutusTunnusString() {
        return ""+koulutusTunnus;
    }
    
    
    /**
     * Palautetaan koulutuksen suorituspäivämäärä
     * @return koulutuksen suorituspäivämäärä
     */
    public String getSuoritettu() {
        return suoritettu;
    }
    
    
    /**
     * Palautetaan koulutuksen umpeutumispäivämäärä
     * @return koulutuksen suorituspäivämäärä
     */
    public String getUmpeutuu() {
        return umpeutuu;
    }
    
    
    /**
     * Tulostetaan relaation tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Relaatiotunnus: "     + String.format("%03d", relaatioTunnus));
        out.println(" Työntekijätunnus: "   + String.format("%03d", tyontekijaTunnus));
        out.println(" Koulutuksen tunnus: " + String.format("%03d", koulutusTunnus));
        out.println(" Koulutuksen nimi: "   + koulutus); // Tämä lisätty, jotta on selkeämpi lisätä ja muokata työntekijälle laitettavaa koulutusta
        out.println(" Suoritettu: "         + suoritettu);
        out.println(" Umpeutuu: "           + umpeutuu);
    }
    
    
    /**
     * Tulostetaan relaation tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
    * Asettaa relaatiotunnuksen ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param nr asetettava relaatiotunnus
    */
   private void setTunnusNro(int nr) {
       relaatioTunnus = nr;
       if ( relaatioTunnus >= seuraavaRelaatioTunnus ) seuraavaRelaatioTunnus = relaatioTunnus + 1;
   }


   /**
    * Palauttaa relaation tiedot merkkijonona jonka voi tallentaa tiedostoon.
    * @return relaatio tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Relaatio relaatio = new Relaatio();
    *   relaatio.parse("   1   |  1  |   1  | Vesisukeltaja | 1.1.2021 | 1.1.2031 ");
    *   relaatio.toString()    === "1|1|1|Vesisukeltaja|1.1.2021|1.1.2031";
    * </pre>
    */
   @Override
   public String toString() {
       return "" + getRelaatioTunnus() + "|" + getTyontekijaTunnus() + "|" + getKoulutusTunnus() + "|" + getKoulutus() + "|" + getSuoritettu() + "|" + getUmpeutuu();
   }


   /**
    * Selvittää relaation tiedot | erotellusta merkkijonosta.
    * Pitää huolen että seuraavaRelaatioTunnus on suurempi kuin tuleva relaatioTunnus.
    * @param rivi josta relaation tiedot otetaan
    * @example
    * <pre name="test">
    *   Relaatio relaatio = new Relaatio();
    *   relaatio.parse("   1   |  1  |   1  |Vesisukeltaja | 1.1.2021 | 1.1.2031 ");
    *   relaatio.getRelaatioTunnus() === 1;
    *   relaatio.toString()    === "1|1|1|Vesisukeltaja|1.1.2021|1.1.2031";
    *   
    *   relaatio.rekisteroi();
    *   int n = relaatio.getRelaatioTunnus();
    *   relaatio.parse(""+(n+20));
    *   relaatio.rekisteroi();
    *   relaatio.getRelaatioTunnus() === n+20+1;
    * </pre>
    */
   public void parse(String rivi) {
       StringBuffer sb = new StringBuffer(rivi);
       setTunnusNro(Mjonot.erota(sb, '|', getRelaatioTunnus()));
       tyontekijaTunnus     = Mjonot.erota(sb, '|', tyontekijaTunnus);
       koulutusTunnus       = Mjonot.erota(sb, '|', koulutusTunnus);
       koulutus             = Mjonot.erota(sb, '|', koulutus);
       suoritettu           = Mjonot.erota(sb, '|', suoritettu);
       umpeutuu             = Mjonot.erota(sb, '|', umpeutuu);
   }
   
   
   /**
    * Tehdään identtinen klooni relaatiosta
    * @return Object kloonattu relaatio
    * @example
    * <pre name="test">
    * #THROWS CloneNotSupportedException 
    *   Relaatio relaatio = new Relaatio();
    *   relaatio.parse("   1   |  1  |   1  |Vesisukeltaja | 1.1.2021 | 1.1.2031 ");
    *   Relaatio kopio = relaatio.clone();
    *   kopio.toString() === relaatio.toString();
    *   relaatio.parse("   2   |  1  |   1  |Vesisukeltaja | 1.1.2021 | 1.1.2031 ");
    *   kopio.toString().equals(relaatio.toString()) === false;
    * </pre>
    */
   @Override
   public Relaatio clone() throws CloneNotSupportedException {
       Relaatio uusi;
       uusi = (Relaatio) super.clone();
       return uusi;
   }
    
    
    /**
     * Testiohjelma relaatiosta
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Relaatio rel = new Relaatio();
        Relaatio rel2 = new Relaatio();
        
        rel.rekisteroi();
        rel2.rekisteroi();
        
        rel.vastaaRelaatio();
        rel.vastaaRelaatio();
        
        rel.tulosta(System.out);
        rel2.tulosta(System.out);
    }

}