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
public class Relaatiot implements Iterable<Relaatio> {
    
    private String tiedostonNimi = "";
    
    /**
     * Taulukko työntekijän koulutuksista
     */
    private final Collection<Relaatio> alkiot = new ArrayList<Relaatio>();
    
    
    /**
     * Relaatioiden alustaminen
     */
    public Relaatiot() {
        // EI vielä
    }

    
    /**
     * Lisää uuden relaation tietorakenteeseen. Ottaa relaation omistukseensa.
     * @param rel lisättävä relaatio. Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Relaatio rel) {
        alkiot.add(rel);
    }
    
    
    /**
     * Palauttaa koulutusrekisterin relaatioiden lukumäärän
     * @return relaatioiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Iteraattori kaikkien relaatioiden läpikäymiseen
     * @return relaatioiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(1); relaatiot.lisaa(rel1);
     * Relaatio rel2 = new Relaatio(2); relaatiot.lisaa(rel2);
     * Relaatio rel3 = new Relaatio(3); relaatiot.lisaa(rel3);
     * 
     * Iterator<Relaatio> i2=relaatiot.iterator();
     * i2.next() === rel1;
     * i2.next() === rel2;
     * i2.next() === rel3;
     * 
     * int n = 0;
     * int jnrot[] = {1,2,3};
     * 
     * for (Relaatio rel:relaatiot) {
     *  rel.getTyontekijaTunnus() === jnrot[n]; n++;
     * }
     * 
     * n === 3;
     * 
     * </pre>
     */
    @Override
    public Iterator<Relaatio> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Haetaan kaikki työntekijän koulutukset
     * @param tyontekijaTunnus työntekijän tunnusnumero, jolle koulutukset haetaan
     * @return tietorakenne jossa viitteet löydettyihin koulutuksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(1); relaatiot.lisaa(rel1);
     * Relaatio rel2 = new Relaatio(2); relaatiot.lisaa(rel2);
     * Relaatio rel3 = new Relaatio(3); relaatiot.lisaa(rel3);
     * 
     * List<Relaatio> loytyneet;
     * loytyneet = relaatiot.annaRelaatiot(3);
     * loytyneet.size() === 0;
     * loytyneet = relaatiot.annaRelaatiot(1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == rel1 === true;
     * loytyneet.get(1) == rel2 === true;
     * loytyneet = relaatiot.annaRelaatiot(3);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == rel3 === true;
     * </pre>
     */
    public List<Relaatio> annaRelaatiot(int tyontekijaTunnus) {
        List<Relaatio> loydetyt = new ArrayList<Relaatio>();
        for (Relaatio rel : alkiot)
            if (rel.getTyontekijaTunnus() == tyontekijaTunnus) loydetyt.add(rel);
        return loydetyt;
    }
    
    
      /**
       * Lukee työntekijät tiedostosta.  Kesken.
       * @param hakemisto tiedoston hakemisto
       * @throws SailoException jos lukeminen epäonnistuu
       */
      public void lueTiedostosta(String hakemisto) throws SailoException {
          tiedostonNimi = hakemisto + "/relaatiot.dat";
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
        Relaatiot relaatiot     = new Relaatiot();
        
        Relaatio rel1           = new Relaatio();
        rel1.vastaaRelaatio();
        
        Relaatio rel2           = new Relaatio();
        rel2.vastaaRelaatio();
        
        Relaatio rel3           = new Relaatio();
        rel3.vastaaRelaatio();
        
        relaatiot.lisaa(rel1);
        relaatiot.lisaa(rel2);
        relaatiot.lisaa(rel3);
        
        System.out.println("============= Relaatiot testi =================");
        
        List<Relaatio> relaatiot2 = relaatiot.annaRelaatiot(2);
        
        for (Relaatio rel : relaatiot2) {
            System.out.println(rel.getTyontekijaTunnus() + " ");
            rel.tulosta(System.out);
        }
        
    }
}