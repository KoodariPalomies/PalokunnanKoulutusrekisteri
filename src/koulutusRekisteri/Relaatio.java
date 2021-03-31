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
 * @author mitulint
 * @version 24.3.2021
 * https://tim.jyu.fi/view/kurssit/tie/ohj2/harjoitustyo/vaiheet/harrastukset-relaatioilla 
 */
public class Relaatio {
    // ota kaikki työntekijät ja koulutukset pois --> tilalle relaatio
    
    private int         relaatioTunnus;
    private int         tyontekijaTunnus;
    private int         koulutusTunnus;
    private LocalDate   suoritettu;
    private LocalDate   umpeutuu;
    
    private static int  seuraavaRelaatioTunnus = 1;   // Tällä saadaan uutta relaatiota luotaessa seuraava tunnus!

    
    /**
     * Lisätään relaatiotunnus, joka lisääntyy aina yhdellä.
     * @return seuraavan relaation tunnus
     */
    public int lisaaRelaatio() {                        // lisaaRelaatio rel = new Relaatio // getRelaatioTunnus, vaihda testien teemat vastaamaan relaatiootaa
        relaatioTunnus = seuraavaRelaatioTunnus;        // muokkaa vastaamaan relaatioid:tä
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
     * Palautetaan koulutuksen koulutustunnus.
     * @return koulutuksen koulutustunnus
     */
    public int getKoulutusTunnus() {
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
     * Apumetodi, jolla saadaan täytettyä testiarvot työntekijälle.
     * TODO: poista kun kaikki toimii
     * @param nro viite työntekijään, jonka koulutuksesta on kyse
     */
    public void vastaaVesisukeltaja(int nro) {                              
        relaatioTunnus      = nro;                      // joku luku suoraan
        tyontekijaTunnus    = nro;                      // joku luku suoraan
        koulutusTunnus      = 1;
        suoritettu          = LocalDate.now();
        umpeutuu            = LocalDate.now();
    }
    
    
    /**
     * Tulostetaan koulutuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Relaatiotunnus: "     + String.format("%03d", relaatioTunnus));
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
