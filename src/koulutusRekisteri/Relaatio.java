/**
 * 
 */
package koulutusRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;     // import the LocalDate class

import fi.jyu.mit.ohj2.Mjonot;  //==============================================

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
    private String      suoritettu;
    private String      umpeutuu;
    
    private static int  seuraavaRelaatioTunnus = 1;   // Tällä saadaan uutta relaatiota luotaessa seuraava tunnus!

    
    /**
     * Alustetaan relaatio.
     */
    public Relaatio() {
        // EI vielä mitään
    }
    
    
    /**
     * Alustetaan tietyn työntekijän koulutus.
     * @param tyontekijaTunnus työntekijän viitenumero
     * @param koulutusTunnus koulutuksen viitenumero
     */
    public Relaatio(int tyontekijaTunnus, int koulutusTunnus) {
        this.tyontekijaTunnus = tyontekijaTunnus;
        this.koulutusTunnus = koulutusTunnus;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot relaatiolle.
     * TODO: poista kun kaikki toimii
     */
    public void vastaaRelaatio() {
        suoritettu          = "2021";
        umpeutuu            = "2031";
    }
     
    
    /**
     * Lisätään relaatiotunnus, joka lisääntyy aina yhdellä.
     * @return seuraavan relaation tunnus
     * @example
     * <pre name="test">
     *   Relaatio pitsi1 = new Relaatio();
     *   pitsi1.getRelaatioTunnus() === 0;
     *   pitsi1.rekisteroi();
     *   Relaatio pitsi2 = new Relaatio();
     *   pitsi2.rekisteroi();
     *   int n1 = pitsi1.getRelaatioTunnus();
     *   int n2 = pitsi2.getRelaatioTunnus();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        relaatioTunnus = seuraavaRelaatioTunnus;
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
     * Palautetaan mille työntekijälle koulutus kuuluu
     * @return työntekijän tunnuksen
     */
    public int getTyontekijaTunnus() {
        return tyontekijaTunnus;
    }
    
    
    /**
     * Palautetaan mikä koulutus kuuluu työntekijälle
     * @return koulutuksen tunnus
     */
    public int getKoulutusTunnus() {
        return koulutusTunnus;
    }
    
    
    /**
     * @return koulutuksen suorituspäivämäärä
     */
    public String getSuoritettu() {
        return suoritettu;
    }
    
    
    /**
     * @return koulutuksen suorituspäivämäärä
     */
    public String getUmpeutuu() {
        return umpeutuu;
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
    * Asettaa relaatiotunnuksen ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param nr asetettava relaatiotunnus
    */
   private void setTunnusNro(int nr) {
       relaatioTunnus = nr;
       if ( relaatioTunnus >= seuraavaRelaatioTunnus ) seuraavaRelaatioTunnus = relaatioTunnus + 1;
   }


   /**
    * Palauttaa relaation tiedot merkkijonona jonka voi tallentaa tiedostoon.
    * @return relaatio tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Relaatio relaatio = new Relaatio();
    *   relaatio.parse("   1   |  1  |   1  | 1.1.2021 | 1.1.2031 ");
    *   relaatio.toString()    === "1|1|1|1.1.2021|1.1.2031";
    * </pre>
    */
   @Override
   public String toString() {
       return "" + getRelaatioTunnus() + "|" + getTyontekijaTunnus() + "|" + getKoulutusTunnus() + "|" + getSuoritettu() + "|" + getUmpeutuu();
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
       setTunnusNro(Mjonot.erota(sb, '|', getRelaatioTunnus()));
       tyontekijaTunnus     = Mjonot.erota(sb, '|', tyontekijaTunnus);
       koulutusTunnus       = Mjonot.erota(sb, '|', koulutusTunnus);
       suoritettu           = Mjonot.erota(sb, '|', suoritettu);
       umpeutuu             = Mjonot.erota(sb, '|', umpeutuu);
   }


   @Override
   public boolean equals(Object relaatio) {
       if ( relaatio == null ) return false;
       return this.toString().equals(relaatio.toString());
   }
   

   @Override
   public int hashCode() {
       return tyontekijaTunnus;
   }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Relaatio rel = new Relaatio();
        Relaatio rel2 = new Relaatio();
        
        rel.rekisteroi();
        rel2.rekisteroi();
        
        rel.vastaaRelaatio();
        rel.vastaaRelaatio();
        
        rel.tulosta(System.out);
        rel2.tulosta(System.out);
    }

}