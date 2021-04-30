/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
import fi.jyu.mit.ohj2.Mjonot;
//import static kanta.HetuTarkistus.rand;         // jotta voi tulostaa lukuja randomilla
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
 * @author mitulint
 * @version 22.3.2021
 *
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
     * Palautetaan koulutuksen nimi
     * @return koulutuksen nimi
     * @example
     * <pre name="test">
     *      Koulutus vesi = new Koulutus();
     *      vesi.vastaaVesisukeltaja();
     *      vesi.getKoulutus() =R= "Vesisukeltaja .*";
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
        koulutus            = "Vesisukeltaja";
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
     *      vesi1.getTyontekijaTunnus() === 0;
     *      vesi.rekisteroi();
     *      Koulutus vesi2 = new Koulutus();
     *      vesi2.rekisteroi();
     *      int n1 = vesi1.getTyontekijaTunnus();
     *      int n2 = vesi2.getTyontekijaTunnus();
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
     *   koulutus.parse("1|Vesisukeltaja");
     *   koulutus.toString() === "1|Vesisukeltaja";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getKoulutusTunnus() + "|" + 
                    koulutus;
    }               // tahi getKoulutus()


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
        //koulutus = Mjonot.erota(sb, '|', getKoulutus());
        koulutus = Mjonot.erota(sb, '|', koulutus); // ei välttämättä tarvitse |
    }


    @Override
    public boolean equals(Object koulutus) {
        if ( koulutus == null ) return false;
        return this.toString().equals(koulutus.toString());
    }
    

    @Override
    public int hashCode() {
        return koulutusTunnus;
    }

    
    /**
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