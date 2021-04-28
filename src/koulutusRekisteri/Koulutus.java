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
     * Palautetaan koulutuksen koulutustunnus.
     * @return koulutuksen koulutustunnus
     */
    public int getKoulutusTunnus() {
        return koulutusTunnus;
    }
    
    
    /**
     * @return koulutuksen nimi
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
     * @return työntekijän uusi tunnusNro
     * @example
     * <pre name="test">
     *      Tyontekija aku1 = new Tyontekija();
     *      aku1.getTyontekijaTunnus() === 0;
     *      aku1.lisaaTyontekija();
     *      Tyontekija aku2 = new Tyontekija();
     *      aku2.lisaaTyontekija();
     *      int n1 = aku1.getTyontekijaTunnus();
     *      int n2 = aku2.getTyontekijaTunnus();
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
     * Palauttaa harrastuksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return harrastus tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.toString()    === "2|10|Kalastus|1949|22";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getKoulutusTunnus() + "|" + 
                    getKoulutus();
    }


    /**
     * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta harrastuksen tiedot otetaan
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.getJasenNro() === 10;
     *   harrastus.toString()    === "2|10|Kalastus|1949|22";
     *   
     *   harrastus.rekisteroi();
     *   int n = harrastus.getTunnusNro();
     *   harrastus.parse(""+(n+20));
     *   harrastus.rekisteroi();
     *   harrastus.getTunnusNro() === n+20+1;
     *   harrastus.toString()     === "" + (n+20+1) + "|10|Kalastus|1949|22";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setKoulutusTunnus(Mjonot.erota(sb, '|', getKoulutusTunnus()));
        koulutus = Mjonot.erota(sb, '|', getKoulutus());
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