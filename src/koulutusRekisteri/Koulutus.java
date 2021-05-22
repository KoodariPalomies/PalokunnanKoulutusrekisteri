package koulutusRekisteri;

import java.io.OutputStream;
import fi.jyu.mit.ohj2.Mjonot;
import java.io.PrintStream;


/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Koulutus                            | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    |                   |
 * | - Ei tiedä Koulutusrekisteristä eikä               |                   |
 * |   käyttöliittymästä.                               |                   |
 * |                                                    |                   |
 * | - Tietää koulutusten kentät                        |                   |
 * | (Koulutuksentunnus, Koulutus).                     |                   |
 * |                                                    |                   |
 * | - Osaa tarkistaa tietyn kentän oikeellisuuden      |                   |
 * |   (syntaksin).                                     |                   |
 * |                                                    |                   |
 * | - Osaa muuttaa 1|Vesisukeltaja|... - merkkijonon   |                   |
 * |   koulutusten tiedoiksi.                           |                   |
 * |                                                    |                   |  
 * | - Osaa antaa merkkijonona i:n kentän tiedot.       |                   |
 * |                                                    |                   |
 * | - Osaa laittaa merkkijonon i:neksi kentäksi.       |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * 
 * @author mitulint, tuomas.mikko.lintula@gmail.com
 * @version 1.0, 22.3.2021  / Tajusin vasta kurssin loppupuolella kuinka näitä versiotietoja tulee käyttää...
 * @version 1.1, 30.4.2021  / HT6 Testejä
 * @version 1.2, 20.5.2021  / HT7 Viimeistelyjä
 * @version 1.3, 22.5.2021  / Lisätty setKoulutus(), jotta voi asettaa koulutukselle muunkin nimen kuin Vesisukeltaja
 * HUOM: puuttuu koulutuksen muokkaus sekä oikeellisuustarkistukset muokattaessa
 */
public class Koulutus {
    
    private int         koulutusTunnus;
    private String      koulutus  = "";
    
    private static int  seuraavaKoulutustunnus = 1;   // Tällä saadaan uutta koulutusta luotaessa seuraava tunnus!
    
    
    /**
     * Alustetaan koulutus
     */
    public Koulutus() {
        //
    }
    
    
    /**
     * Alustetaan tietyn koulutuksen tiedot.
     * @param koulutusTunnus koulutuksen viitenumero
     */
    public Koulutus(int koulutusTunnus) {
        this.koulutusTunnus = koulutusTunnus;
    }
    
    
    /**
     * Palautetaan koulutuksen koulutustunnus.
     * @return koulutuksen koulutustunnus
     */
    public int getKoulutusTunnus() {
        return koulutusTunnus;
    }
    
    
    /**
     * Asettaa koulutuksen nimen
     * @param koulutus koulutuksen nimi
     * @return syötetty koulutuksen nimi tai virheilmoitus
     */
    public String setKoulutus(String koulutus) {
        if (koulutus.matches("(.*)[0-9\\+\\-\\*\\!\\#\\¤\\%\\/\\(\\)\\=\\?\\§\\^]+(.*)")) return "Ei numeroita ja erikoismerkkejä!";
        if (koulutus.matches("")) return "Ei tyhjäarvoa!";
        this.koulutus = koulutus;
            return null;
    }
    
    
    /**
     * Palautetaan koulutuksen nimi
     * @return koulutuksen nimi
     * @example
     * <pre name="test">
     *      Koulutus vesi = new Koulutus();
     *      vesi.vastaaVesisukeltaja();
     *      vesi.getKoulutus() =R= "Vesisukeltaja";
     * </pre>
     */
    public String getKoulutus() {
        return koulutus;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot työntekijälle.
     * TODO: poista kun kaikki toimii
     */
    public void vastaaVesisukeltaja() {
        koulutus = "Vesisukeltaja";
    }
    
    
    /**
     * Tulostetaan koulutuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Koulutuksen tunnus: "     + String.format("%03d", koulutusTunnus));
        out.println(" Koulutus: "               + koulutus);
    }
    
    
    /**
     * Tulostetaan työntekijän tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa uudelle koulutukselle seuraavan koulutustunnuksen.
     * @return koulutuksen uusi koulutusTunnus
     * @example
     * <pre name="test">
     *      Koulutus vesi1 = new Koulutus();
     *      vesi1.getKoulutusTunnus() === 0;
     *      vesi1.rekisteroi();
     *      Koulutus vesi2 = new Koulutus();
     *      vesi2.rekisteroi();
     *      int n1 = vesi1.getKoulutusTunnus();
     *      int n2 = vesi2.getKoulutusTunnus();
     *      n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        koulutusTunnus = seuraavaKoulutustunnus;
        seuraavaKoulutustunnus++;  
        return koulutusTunnus;
    }
    
    
    /**
     * Testiohjelma Harrastukselle.
     * @param args ei käytössä
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setKoulutusTunnus(int nr) {
        koulutusTunnus = nr;
        if ( koulutusTunnus >= seuraavaKoulutustunnus ) seuraavaKoulutustunnus = koulutusTunnus + 1;
    }


    /**
     * Palauttaa koulutuksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return koulutuksen tiedot tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Koulutus koulutus = new Koulutus();
     *   koulutus.parse(" 1  | Vesisukeltaja  ");
     *   koulutus.toString() === "1|Vesisukeltaja";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getKoulutusTunnus() + "|" + 
                    koulutus;
    }


    /**
     * Selvittää koulutuksen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaKoulutustunnus on suurempi kuin tuleva koulutusTunnus.
     * @param rivi josta koulutuksen tiedot otetaan
     * @example
     * <pre name="test">
     *   Koulutus koulutus = new Koulutus();
     *   koulutus.parse("1|Vesisukeltaja");
     *   koulutus.getKoulutusTunnus() === 1;
     *   koulutus.toString() === "1|Vesisukeltaja";
     *   
     *   koulutus.rekisteroi();
     *   int n = koulutus.getKoulutusTunnus();
     *   koulutus.parse(""+(n+20));
     *   koulutus.rekisteroi();
     *   koulutus.getKoulutusTunnus() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setKoulutusTunnus(Mjonot.erota(sb, '|', getKoulutusTunnus()));
        koulutus = Mjonot.erota(sb, '|', koulutus);
    }

    
    /**
     * Tehdään identtinen klooni työntekijästä
     * @return Object kloonattu työntekijä
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Koulutus koulutus = new Koulutus();
     *   koulutus.parse("   3  |  Ankka Aku   | 123");
     *   koulutus kopio = koulutus.clone();
     *   kopio.toString() === koulutus.toString();
     *   koulutus.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(koulutus.toString()) === false;
     * </pre>
     */
    @Override
    public Koulutus clone() throws CloneNotSupportedException {
        Koulutus uusi;
        uusi = (Koulutus) super.clone();
        return uusi;
    }
    
    
    /**
     * Testiohjelma koulutuksesta
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutus koul = new Koulutus();
        Koulutus koul2 = new Koulutus();
        
        koul.rekisteroi();
        koul2.rekisteroi();
        
        koul.vastaaVesisukeltaja();
        koul2.vastaaVesisukeltaja();
        
        koul.tulosta(System.out);
        koul2.tulosta(System.out);
    }

}