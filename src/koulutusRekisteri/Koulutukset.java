/**
 * 
 */
package koulutusRekisteri;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Koulutukset                         | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Koulutus        | 
 * | - Pitää yllä varsinaista koulutusrekisteriä, eli   |                   | 
 * |   osaa lisätä ja poistaa koulutuksen               |                   |
 * |                                                    |                   | 
 * | - Lukee ja kirjoittaa koulutukset tiedostoon.      |                   |
 * |                                                    |                   | 
 * | - Osaa etsiä ja lajitella.                         |                   | 
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author mitulint
 * @version 22.3.2021
 *
 */
public class Koulutukset {
    
    private static final int    MAX_KOULUTUKSIA     = 10;         // Vakio, mallin vuoksi 5 kpl
    private int                 lkm                 = 0;
    private Koulutus[]        alkiot                = new Koulutus[MAX_KOULUTUKSIA];
    
    
    /**
     * Luodaan alustava taulukko
     */
    public Koulutukset() {
        //alkiot = new Tyontekija[MAX_TYONTEKIJOITA]; Attribuuttien oma alustus riittää
    }

    
    /**
     * Lisää uuden koulutuksen tietorakenteeseen. Ottaa koulutuksen omistukseensa.
     * @param koulutus lisättävän työntekijän viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Tyontekijat tyontekijat = new Tyontekijat();
     * Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija();
     * tyontekijat.getLkm() === 0;
     * tyontekijat.lisaa(aku1); tyontekijat.getLkm() === 1;
     * tyontekijat.lisaa(aku2); tyontekijat.getLkm() === 2;
     * tyontekijat.lisaa(aku1); tyontekijat.getLkm() === 3;
     * tyontekijat.anna(0) === aku1;
     * tyontekijat.anna(1) === aku2;
     * tyontekijat.anna(2) === aku1;
     * tyontekijat.anna(1) == aku1 === false;
     * tyontekijat.anna(1) == aku2 === true;
     * tyontekijat.anna(3) === aku1; #THROWS IndexOutOfBoundsException 
     * tyontekijat.lisaa(aku1); tyontekijat.getLkm() === 4;
     * tyontekijat.lisaa(aku1); tyontekijat.getLkm() === 5;
     * tyontekijat.lisaa(aku1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Koulutus koulutus) throws SailoException {
        if ( lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        this.alkiot[this.lkm] = koulutus;
        lkm++;
    }
    
    
    /**
     * Palauttaa koulutusrekisterin koulutusten lukumäärän
     * @return koulutusten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa viitteen i:teen koulutukseen.
     * @param i monennenko koulutuksen viite halutaan
     * @return viite koulutukseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Koulutus anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
       //   /**
      //     * Lukee jäsenistön tiedostosta.  Kesken.
       //    * @param hakemisto tiedoston hakemisto
       //    * @throws SailoException jos lukeminen epäonnistuu
        //   */
        //  public void lueTiedostosta(String hakemisto) throws SailoException {
        //      tiedostonNimi = hakemisto + "/nimet.dat";
        //      throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
        //  }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tyontekijat tyontekijat = new Tyontekijat();
        Tyontekija aku = new Tyontekija();
        Tyontekija aku2 = new Tyontekija();
        
        aku.lisaaTyontekija();
        aku.vastaaAkuAnkka();       // aku.taytaAkuAnkkaTiedoilla();
        aku2.lisaaTyontekija();
        aku2.vastaaAkuAnkka();      // aku2.taytaAkuAnkkaTiedoilla();
        
        try {
            tyontekijat.lisaa(aku);
            tyontekijat.lisaa(aku2);
            tyontekijat.lisaa(aku2);
            tyontekijat.lisaa(aku2);
            tyontekijat.lisaa(aku2);
            tyontekijat.lisaa(aku2);    // Ensimmäinen poikkeus --> SailoException
            tyontekijat.lisaa(aku2);
            tyontekijat.lisaa(aku2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }
        
        System.out.println("========== Työntekijät testi ==========");
        
        for (int i = 0; i < tyontekijat.getLkm(); i++) {
            Tyontekija tyontekija = tyontekijat.anna(i);
            System.out.println("Koulutus indeksi: " + i);
            tyontekija.tulosta(System.out);
        }
    }
}