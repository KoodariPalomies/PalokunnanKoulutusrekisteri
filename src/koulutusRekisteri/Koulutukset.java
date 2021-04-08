/**
 * 
 */
package koulutusRekisteri;

import java.util.*;

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
 * @version 24.3.3021
 *
 */
public class Koulutukset {
    
    private static final int    MAX_KOULUTUKSIA     = 5;         // Vakio, mallin vuoksi 5 kpl
    private int                 lkm                 = 0;
    private String              tiedostonNimi       = "";
    private Koulutus[]          alkiot              = new Koulutus[MAX_KOULUTUKSIA];
    
    
    /**
     * Oletusmuodostaja
     */
    public Koulutukset() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * Lisää uuden työntekijän tietorakenteeseen. Ottaa työntekijän omistukseensa.
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
     * tyontekijat.annaTyontekija(0) === aku1;
     * tyontekijat.annaTyontekija(1) === aku2;
     * tyontekijat.annaTyontekija(2) === aku1;
     * tyontekijat.annaTyontekija(1) == aku1 === false;
     * tyontekijat.annaTyontekija(1) == aku2 === true;
     * tyontekijat.annaTyontekija(3) === aku1; #THROWS IndexOutOfBoundsException 
     * tyontekijat.lisaa(aku1); tyontekijat.getLkm() === 4;
     * tyontekijat.lisaa(aku1); tyontekijat.getLkm() === 5;
     * tyontekijat.lisaa(aku1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Koulutus koulutus) throws SailoException {
        if ( lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = koulutus;
        lkm++;
    }
    
    
    /**
     * Palauttaa viitteen i:teen työntekijään.
     * @param i monennenko työntekijän viite halutaan
     * @return viite työntekijään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Koulutus annaKoulutus(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
      /**
       * Lukee työntekijät tiedostosta.  Kesken.
       * @param hakemisto tiedoston hakemisto
       * @throws SailoException jos lukeminen epäonnistuu
       */
      public void lueTiedostosta(String hakemisto) throws SailoException {
          tiedostonNimi = hakemisto + "/koulutukset.dat";
          throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
      }
    
    
    /**
     * Tallentaa työntekijät tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palauttaa koulutusrekisterin työntekijöiden lukumäärän
     * @return työntekijöiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutukset koulutukset = new Koulutukset();
        
        Koulutus pitsi1  = new Koulutus();
        Koulutus pitsi2 = new Koulutus();
        
        pitsi1.rekisteroi();
        pitsi2.rekisteroi();
        
        pitsi1.vastaaVesisukeltaja();
        pitsi2.vastaaVesisukeltaja();
    
        
        try {
            koulutukset.lisaa(pitsi1);
            koulutukset.lisaa(pitsi2);
            
            System.out.println("========== Työntekijät testi ==========");
            
            for (int i = 0; i < koulutukset.getLkm(); i++) {
                Koulutus koulutus = koulutukset.annaKoulutus(i);
                System.out.println("Koulutustunnus: " + i);
                koulutus.tulosta(System.out);
            }
    
        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }

    }
}