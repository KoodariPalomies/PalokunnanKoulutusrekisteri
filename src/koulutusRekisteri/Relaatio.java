/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate; // import the LocalDate class

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
 * | (Työntekijätunnus, Koulutuksen tunnus,             |                   |
 * |  Suoritettu, Umpeutuu).                            |                   |
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
 * @author mitulint
 * @version 24.3.2021
 *
 */
public class Relaatio {
    
    private int         tyontekijaTunnus;
    private int         koulutusTunnus;
    private LocalDate   suoritettu;
    private LocalDate   umpeutuu;
    
    private static int  seuraavaKoulutustunnus = 1;   // Tällä saadaan uutta koulutusta luotaessa seuraava tunnus!

    
    /**
     * Alustetaan kaikki tyhjäksi
     */
    public void Koulutus() {
        // alustuslauseet hoitaa kaiken
        // lisaaTyontekija();
    }
    
    
    /**
     * Alustetaan tietyn työntekijän koulutus
     * @param koulutusTunnus koulutuksen tunnusluku
     */
    public void Koulutus(int koulutusTunnus) {
        this.koulutusTunnus = koulutusTunnus;
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
     * @return koulutuksen suorituspäivämäärä
     */
    public LocalDate getSuoritettu() {
        return suoritettu;
    }
    
    
    /**
     * @return koulutuksen suorituspäivämäärä
     */
    public LocalDate getUmpeutuu() {
        return suoritettu.plusYears(10);
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
     * @param nro viite työntekijään, jonka koulutuksesta on kyse
     */
    public void vastaaVesisukeltaja(int nro) {
        tyontekijaTunnus    = nro;
        koulutusTunnus      = 1;
        suoritettu          = LocalDate.now();
        umpeutuu            = LocalDate.now();
    }
    
    
    /**
     * Tulostetaan koulutuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Työntekijätunnus: "   + String.format("%03d", tyontekijaTunnus));
        out.println(" Koulutuksen tunnus: " + koulutusTunnus);
        out.println(" Suoritettu: "         + suoritettu);
        out.println(" Umpeutuu: "           + umpeutuu);
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
        Koulutus koul = new Koulutus();
        //koul.lisaaKoulutus();
        koul.vastaaVesisukeltaja(1);
        koul.tulosta(System.out);
    }

}
