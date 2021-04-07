/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
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
    public int lisaaKoulutus() {
        koulutusTunnus = seuraavaKoulutustunnus;
        seuraavaKoulutustunnus++;  
        return koulutusTunnus;
    }
    
    
    /**
     * @return koulutuksen nimi
     */
    public String getKoulutus() {
        return koulutus;
    }
    
    
    /**
     * Palautetaan koulutuksen koulutustunnus.
     * @return koulutuksen koulutustunnus
     */
    public int getKoulutusTunnus() {
        return koulutusTunnus;
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
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutus koul = new Koulutus(), koul2 = new Koulutus();
        
        koul.lisaaKoulutus();
        koul2.lisaaKoulutus();
        
        koul.vastaaVesisukeltaja();
        koul2.vastaaVesisukeltaja();
        
        koul.tulosta(System.out);
        koul2.tulosta(System.out);
    }

}
