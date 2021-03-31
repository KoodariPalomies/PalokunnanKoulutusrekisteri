/**
 * 
 */
package koulutusRekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.*;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Relaatiot                           | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   |
 * |                                                    | - Relaatio        |
 * | - Relaatio-kokoelman ylläpito (lisäys-poisto,      |                   |
 * |   etsiminen, tiedoston luku ja kirjoitus).         |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * @author mitulint
 * @version 22.3.2021
 *
 */
public class Relaatiot {
    
    private static final int    MAX_RELAATIOITA   = 5;         // Vakio, mallin vuoksi 5 kpl
    private int                 lkm               = 0;
    private String              tiedostonNimi     = "";
    private Relaatio[]          alkiot            = new Relaatio[MAX_RELAATIOITA];

    
    /**
     * Lisää uuden relaation tietorakenteeseen. Ottaa relaation omistukseensa.
     * @param relaatio lisättävän relaation viite. Huom tietorakenne muuttuu omistajaksi
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
    public void lisaa(Relaatio relaatio) throws SailoException {
        if ( lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        this.alkiot[this.lkm] = relaatio;
        lkm++;
    }
    
    
    /**
     * Palauttaa koulutusrekisterin relaatioiden lukumäärän
     * @return relaatioiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa viitteen i:teen relaatioon.
     * @param i monennenko relaation viite halutaan
     * @return viite relaatioon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Relaatio annaRelaatio(int i) throws IndexOutOfBoundsException {
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
          tiedostonNimi = hakemisto + "/nimet.dat";
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
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Relaatiot relaatiot = new Relaatiot();
        
        Relaatio aku          = new Relaatio();
        Relaatio aku2         = new Relaatio();
        aku.lisaaRelaatio();
        aku.vastaaVesisukeltaja();
        aku2.lisaaRelaatio();
        aku2.vastaaVesisukeltaja();
        
        try {
            relaatiot.lisaa(aku);
            relaatiot.lisaa(aku2);
            
            System.out.println("========== Työntekijät testi ==========");
            
            for (int i = 0; i < relaatiot.getLkm(); i++) {
                Relaatio tyontekija = relaatiot.annaRelaatio(i);
                System.out.println("Työntekijä indeksi: " + i);
                tyontekija.tulosta(System.out);
            }

        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }

    }

}
