/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import static kanta.HetuTarkistus.*; // HetuTarkistus --> kanta packet --> voi käyttää johonkin muuhun tarkistamiseen

/**
 * Kopioi CRC-kortin sisältö
 * @author mitulint
 * @version 27.2.2021
*/
public class Tyontekija {
    
    private int         tyontekijaTunnus;
    private String      nimi                     = "";
    private String      tehtavaAlue              = "";
    private String      virkaAsema               = "";
    
    private static int  seuraavaTyontekijatunnus = 1;   // Tällä saadaan uutta työntekijää luotaessa seuraava tunnus!

    
    /**
     * Alustetaan kaikki tyhjäksi
     */
    public Tyontekija() {
        // alustuslauseet hoitaa kaiken
        // lisaaTyontekija();
    }
    
    
    /**
     * Tulostetaan työntekijän tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" Työntekijätunnus: " + String.format("%03d", tyontekijaTunnus));
        out.println(" Työntekijän nimi: " + nimi);
        out.println(" Työntekijän tehtäväalue: " + tehtavaAlue);
        out.println(" Työntekijän virka-asema: " + virkaAsema);
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
    public int lisaaTyontekija() {
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
     * Apumetodi, jolla saadaan täytettyä testiarvot työntekijälle.
     * TODO: poista kun kaikki toimii
     */
    public void vastaaAkuAnkka() {
        tyontekijaTunnus = 1;
        nimi = "Ankka Aku";
        tehtavaAlue = "Pelastus";
        virkaAsema = "Palomies";
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tyontekija aku = new Tyontekija();
        Tyontekija aku2 = new Tyontekija();
        
        aku.lisaaTyontekija();
        aku2.lisaaTyontekija();
        
        aku.tulosta(System.out);
        aku.vastaaAkuAnkka();       // aku.taytaAkuAnkkaTiedoilla();
        aku.tulosta(System.out);
        
        aku2.tulosta(System.out);
        aku2.vastaaAkuAnkka();      // aku2.taytaAkuAnkkaTiedoilla();
        aku2.tulosta(System.out);
    }

}
