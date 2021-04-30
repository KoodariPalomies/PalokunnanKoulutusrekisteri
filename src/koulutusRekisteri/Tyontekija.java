/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;
import static kanta.HetuTarkistus.*; // HetuTarkistus --> kanta packet --> voi käyttää johonkin muuhun tarkistamiseen

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
 * @author mitulint
 * @version 1.0, 3.3.2021 / Huonosti seurannut näitä versiokehityksiä
 * @version 1.1, 30.4.2021 / HT6 testejä
*/
public class Tyontekija {
    
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
       * TODO: poista kun kaikki toimii
       */
      public void vastaaAkuAnkka() {
          //tyontekijaTunnus = 1;
          nimi          = "Ankka Aku";
          tehtavaAlue   = "Pelastus";
          virkaAsema    = "Palomies";
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
     * @return työntekijän nimi
     * @example
     * <pre name="test">
     *   Tyontekija aku = new Tyontekija();
     *   aku.vastaaAkuAnkka();
     *   aku.getNimi() =R= "Ankka Aku .*";
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
        return "" +
                    getTyontekijaTunnus() + "|" + 
                    nimi + "|" + 
                    tehtavaAlue + "|" + 
                    virkaAsema;
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
     *   tyontekija.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   tyontekija.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
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
    
    
    @Override
    public boolean equals(Object tyontekija) {
        if ( tyontekija == null ) return false;
        return this.toString().equals(tyontekija.toString());
    }


    @Override
    public int hashCode() {
        return tyontekijaTunnus;
    }
    
    
    /**
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
