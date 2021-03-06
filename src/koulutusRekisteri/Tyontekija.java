package koulutusRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;


/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Tyontekija                          | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    |                   |
 * | - Ei tiedä Koulutusrekisteristä eikä               |                   |
 * |   käyttöliittymästä.                               |                   |
 * |                                                    |                   |
 * | - Tietää työntekijän kentät                        |                   |
 * | (Työntekijätunnus, Nimi, Tehtäväalue, Virka-asema).|                   |
 * |                                                    |                   |
 * | - Osaa tarkistaa tietyn kentän oikeellisuuden      |                   |
 * |   (syntaksin).                                     |                   |
 * |                                                    |                   |
 * | - Osaa muuttaa 1|Ankka Aku|.. - merkkijonon        |                   |
 * |   työntekijän tiedoiksi.                           |                   |
 * |                                                    |                   |  
 * | - Osaa antaa merkkijonona i:n kentän tiedot.       |                   |
 * |                                                    |                   |
 * | - Osaa laittaa merkkijonon i:neksi kentäksi.       |                   |
 * |                                                    |                   |  
 * |-------------------------------------------------------------------------
 * 
 * @author mitulint, tuomas.mikko.lintula@gmail.com
 * @version 1.0, 3.3.2021   / Huonosti seurannut näitä versiokehityksiä
 * @version 1.1, 30.4.2021  / HT6 testejä
 * @version 1.2, 14.5.2021  / Lisätty clone()
 * @version 1.3, 16.5.2021  / Lisätty throw new IllegalArgumentException setNimi --> oikeellisuustarkistusta varten --> ei tarvita kuitenkaan
 * @version 1.4, 20.5.2021  / setNimi muokattu oikeellisuustarkistusta varten
 * HUOM: puuttuu työntekijän poistaminen
 */
public class Tyontekija implements Cloneable {
    
    private int         tyontekijaTunnus;
    private String      nimi                     = "";
    private String      tehtavaAlue              = "";
    private String      virkaAsema               = "";
    
    private static int  seuraavaTyontekijatunnus = 1;   // Tällä saadaan uutta työntekijää luotaessa seuraava tunnus!


    /**
     * Alustetaan työntekijä.
     */
    public Tyontekija() {
        // ei mitään
    }
    
    
    /**
     * Alustetaan tietyn työntekijän tiedot.
     * @param tyontekijaTunnus työntekijän viitenumero
     */
    public Tyontekija(int tyontekijaTunnus) {
        this.tyontekijaTunnus = tyontekijaTunnus;
    }

    
      /**
       * Apumetodi, jolla saadaan täytettyä testiarvot työntekijälle.
       */
      public void vastaaAkuAnkka() {
          nimi          = "Ankka Aku";
          tehtavaAlue   = "Pelastus";
          virkaAsema    = "Palomies";
      }
      
      
    /**
     * Asettaa työntekijän nimen
     * @param nimi työntekijän nimi
     * @return syötetty työntekijän nimi tai virheilmoitus
     */
    public String setNimi(String nimi) {
        if (nimi.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei numeroita ja erikoismerkkejä!";
        if (nimi.matches("")) return "Ei tyhjäarvoa!";
        this.nimi = nimi;
            return null;
    }
    
    
    /**
     * Asettaa työntekijän tehtäväalueen
     * @param tehtavaAlue työntekijän tehtäväalue
     * @return syötetty tehtäväalueen nimi tai virheilmoitus
     */
    public String setTehtavaAlue(String tehtavaAlue) {
        if (tehtavaAlue.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\&\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei numeroita ja erikoismerkkejä!";
        if (tehtavaAlue.matches("")) return "Ei tyhjäarvoa!";  
        this.tehtavaAlue = tehtavaAlue;
            return null;
      }
    
    
    /**
     * Asettaa työntekijän virka-aseman
     * @param virkaAsema työntekijän virka-asema
     * @return syötetty virka-aseman nimi tai virheilmoitus
     */
    public String setVirkaAsema(String virkaAsema) {
        if (virkaAsema.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\&\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei numeroita ja erikoismerkkejä!";
        if (virkaAsema.matches("")) return "Ei tyhjäarvoa!";  
        this.virkaAsema = virkaAsema;
            return null;
      }
    
    
    /**
     * Tulostetaan työntekijän tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Työntekijätunnus: "           + String.format("%03d", tyontekijaTunnus));
        out.println(" Työntekijän nimi: "           + nimi);
        out.println(" Työntekijän tehtäväalue: "    + tehtavaAlue);
        out.println(" Työntekijän virka-asema: "    + virkaAsema);
    }
    
    
    /**
     * Tulostetaan työntekijän tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa uudelle työntekijälle seuraavan työntekijätunnuksen.
     * @return työntekijän uusi tyontekijaTunnus
     * @example
     * <pre name="test">
     *      Tyontekija aku1 = new Tyontekija();
     *      aku1.getTyontekijaTunnus() === 0;
     *      aku1.rekisteroi();
     *      Tyontekija aku2 = new Tyontekija();
     *      aku2.rekisteroi();
     *      int n1 = aku1.getTyontekijaTunnus();
     *      int n2 = aku2.getTyontekijaTunnus();
     *      n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        this.tyontekijaTunnus = seuraavaTyontekijatunnus;
        seuraavaTyontekijatunnus++;
        return this.tyontekijaTunnus;
    }
    
    
    /**
     * Palautetaan työntekijän työntekijätunnus.
     * @return työntekijän työntekijätunnus
     */
    public int getTyontekijaTunnus() {
        return tyontekijaTunnus;
    }
    
    
    /**
     * Palauttaa alkuperäisen työntekijätunnuksen String-muodossa.
     * @return alkuperäinen vuosi
     */
    public String getTyontekijaTunnusString(){
        return ""+tyontekijaTunnus;
    }
    
    
    /**
     * Palauttaa työntekijän tehtävä-alueen String-muodossa.
     * @return tyontekijan tehtava-alueen
     */
    public String getTehtavaAlue() {
        return tehtavaAlue;
    }
    
    
    /**
     * Palauttaa työntekijän virka-aseman String-muodossa.
     * @return tyontekijan virka-aseman
     */
    public String getVirkaAsema() {
        return virkaAsema;
    }
    
    
    /**
     * @return työntekijän nimi
     * @example
     * <pre name="test">
     *   Tyontekija aku = new Tyontekija();
     *   aku.vastaaAkuAnkka();
     *   aku.getNimi() =R= "Ankka Aku";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Asettaa työntekijätunnuksen ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava työntekijätunnus
     */
    private void setTyontekijaTunnus(int nr) {
        tyontekijaTunnus = nr;
        if (tyontekijaTunnus >= seuraavaTyontekijatunnus) seuraavaTyontekijatunnus = tyontekijaTunnus + 1;
    }
    
    
    /**
     * Palauttaa työntekijän tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return työntekijä tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tyontekija tyontekija = new Tyontekija();
     *   tyontekija.parse("   1  |  Ankka Aku   | Pelastustoiminta | Palomies ");
     *   tyontekija.toString() === "1|Ankka Aku|Pelastustoiminta|Palomies";
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + getTyontekijaTunnus() + "|" + nimi + "|" + tehtavaAlue + "|" + virkaAsema;
    }


    /**
     * Selvittää työntekijän tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tyontekijaTunnus.
     * @param rivi josta työntekijän tiedot otetaan
     * @example
     * <pre name="test">
     *   Tyontekija tyontekija = new Tyontekija();
     *   tyontekija.parse("   1  |  Ankka Aku   | Pelastustoiminta | Palomies ");
     *   tyontekija.getTyontekijaTunnus() === 1;
     *   tyontekija.toString() === "1|Ankka Aku|Pelastustoiminta|Palomies";
     *
     *   tyontekija.rekisteroi();
     *   int n = tyontekija.getTyontekijaTunnus();
     *   tyontekija.parse(""+(n+20));
     *   tyontekija.rekisteroi();
     *   tyontekija.getTyontekijaTunnus() === n+20+1;
     *   tyontekija.toString() === "" + (n+20+1) + "|Ankka Aku|Pelastustoiminta|Palomies";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTyontekijaTunnus(Mjonot.erota(sb, '|', getTyontekijaTunnus()));
        nimi = Mjonot.erota(sb, '|', nimi);
        tehtavaAlue = Mjonot.erota(sb, '|', tehtavaAlue);
        virkaAsema = Mjonot.erota(sb, '|', virkaAsema); // ei välttämättä tarvitse |
    }
    
    
    /**
     * Tehdään identtinen klooni työntekijästä
     * @return Object kloonattu työntekijä
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Tyontekija tyontekija = new Tyontekija();
     *   tyontekija.parse("   3  |  Ankka Aku   | 123");
     *   Tyontekija kopio = tyontekija.clone();
     *   kopio.toString() === tyontekija.toString();
     *   tyontekija.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(tyontekija.toString()) === false;
     * </pre>
     */
    @Override
    public Tyontekija clone() throws CloneNotSupportedException {
        Tyontekija uusi;
        uusi = (Tyontekija) super.clone();
        return uusi;
    }
    
    
    /**
     * Testiohjelma työntekijästä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tyontekija aku = new Tyontekija();
        Tyontekija aku2 = new Tyontekija();
        
        aku.rekisteroi();
        aku2.rekisteroi();
        
        aku.tulosta(System.out);
        aku.vastaaAkuAnkka();
        aku.tulosta(System.out);
        
        aku2.tulosta(System.out);
        aku2.vastaaAkuAnkka();
        aku2.tulosta(System.out);
    }
}
