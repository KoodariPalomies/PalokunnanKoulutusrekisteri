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
     * Apumetodi, jolla saadaan täytettyä testiarvot relaatiolle.
     * TODO: poista kun kaikki toimii
     */
    public void vastaaVesisukeltaja() {                              
        relaatioTunnus      = 1;                      // joku luku suoraan
        tyontekijaTunnus    = 1;                      // joku luku suoraan
        koulutusTunnus      = 1;
        suoritettu          = LocalDate.now();
        umpeutuu            = LocalDate.now();
    }
    
    
    /**
     * Tulostetaan relaation tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Relaatiotunnus: "     + String.format("%03d", relaatioTunnus));
        out.println(" Työntekijätunnus: "   + String.format("%03d", tyontekijaTunnus));
        out.println(" Koulutuksen tunnus: " + String.format("%03d", koulutusTunnus));
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
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Relaatio rel = new Relaatio();
        rel.lisaaRelaatio();
        rel.vastaaVesisukeltaja();
        rel.tulosta(System.out);
    }

}
