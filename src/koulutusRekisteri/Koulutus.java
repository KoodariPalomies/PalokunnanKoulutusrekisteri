/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
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
    
    private static int  seuraavaKoulutustunnus = 1;   // Tällä saadaan uutta työntekijää luotaessa seuraava tunnus!

    
    /**
     * Alustetaan kaikki tyhjäksi
     */
    public Koulutus() {
        // alustuslauseet hoitaa kaiken
        // lisaaTyontekija();
    }
    
    
    /**
     * Alustetaan tietyn työntekijän koulutus
     * @param koulutusTunnus koulutuksen tunnusluku
     */
    public Koulutus(int koulutusTunnus) {
        this.koulutusTunnus = koulutusTunnus;
    }
    
    
    /**
     * Tulostetaan koulutuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Koulutus: " + String.format("%03d", koulutusTunnus));
        out.println(" Suoritettu: " + koulutus);
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
    public int lisaaKoulutus() {
        koulutusTunnus = seuraavaKoulutustunnus;
        seuraavaKoulutustunnus++;
        return koulutusTunnus;
    }
    
    
    /**
     * @return koulutuksen nimi
     */
    public String getNimi() {
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
    public void vastaaAkuAnkka() {
        koulutusTunnus      = 1;
        koulutus            = "Vesisukeltaja";
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutus koul = new Koulutus();
        koul.lisaaKoulutus();
        koul.vastaaAkuAnkka();
        koul.tulosta(System.out);
    }
        
}
