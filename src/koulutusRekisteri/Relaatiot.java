/**
 * 
 */
package koulutusRekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.*;
import java.util.NoSuchElementException;

//======================================================================
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//======================================================================


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
 * @version 22.4.2021
 *
 */
public class Relaatiot implements Iterable<Relaatio> {
    
    private static final int MAX_TYONTEKIJOITA = 10;
    private boolean muutettu = false;           //======================================================================
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "";     //"relaatiot"
    private Relaatio alkiot[] = new Relaatio[MAX_TYONTEKIJOITA];
    
    
    /**
     * Relaatioiden alustaminen
     */
    public Relaatiot() {
        // EI vielä
    }
    
    /**
     * Lisää uuden relaation tietorakenteeseen. Ottaa relaation omistukseensa.
     * @param rel lisättävä relaatio. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void lisaa(Relaatio rel) throws SailoException {
        if ( lkm >= alkiot.length ) {
            kasvataTaulukkoa(rel);
            muutettu = true;
        }
        else {
                alkiot[lkm++] = rel;
                muutettu = true;
            }
    }
    
    
    /**
     * Kasvattaa taulukkoa dynaamisesti, kun se täyttyy.
     * @param rel olio, joka halutaan lisätä taulukkoon
     */
    public void kasvataTaulukkoa(Relaatio rel) {
        Relaatio[] r2 = new Relaatio[alkiot.length*2];
        
        for (int i = 0; i < lkm; i++) {
            r2[i] = alkiot[i];
        }
        r2[lkm++] = rel;
        alkiot = r2;
    }
    
    
    /**
     * Palauttaa viitteen i:teen työntekijään
     * @param i monennenko työntekijän viite halutaan
     * @return viite työntekijään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos ei ole sallitulla alueella
     */
    public Relaatio annaRelaatiot(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <=i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + 1);
        return alkiot[i];
    }
    
    
    /**
     * Lukee relaatiot tiedostosta.
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta() throws SailoException {     // String tied
        
        tiedostonPerusNimi = "relaatiot.dat";
        
        try ( BufferedReader fi = new BufferedReader(new FileReader(tiedostonPerusNimi)) ) {
            String rivi = "";
            
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi)) continue;
                
                Relaatio rel = new Relaatio();
                rel.parse(rivi);
                lisaa(rel);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonPerusNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Tallentaa relaatiot tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        tiedostonPerusNimi = "relaatiot.dat";
        if ( !muutettu ) return;

        File ftied = new File(getTiedostonPerusNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Relaatio relaatio : this) {
                String rivi = relaatio.toString();
                fo.println(rivi);
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }
//=================================================================================================================    
    
    
    /**
     * Palauttaa Koulutusrekisterin koko nimen
     * @return Koulutusrekisterin koko nimi merkkijonona
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Palauttaa koulutusrekisterin relaatioiden lukumäärän
     * @return relaatioiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
       
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
  /**
   * Asettaa tiedoston perusnimen ilman tarkenninta
   * @param tied tallennustiedoston perusnimi
   */
  public void setTiedostonPerusNimi(String tied) {
      tiedostonPerusNimi = tied;
  }
    
    
    /**
     * @author Käyttäjä
     * @version 22.4.2021
     *
     */
    public class RelaatiotIterator implements Iterator<Relaatio> {
        private int kohdalla = 0;
    
    /**
     * Onko olemassa vielä seuraavaa työntekijää
     * @see java.util.Iterator#hasNext()
     * @return true jos on vielä työntekijöitä
     */
    @Override
    public boolean hasNext() {
        return kohdalla < getLkm();
    }
    
    
    /**
     * Annetaan seuraava työntekijä
     * @return seuraava työntekijä
     * @throws NoSuchElementException jos seuraava alkiota ei enää ole
     * @see java.util.Iterator#next()
     */
    @Override
    public Relaatio next() throws NoSuchElementException {
        if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
        return annaRelaatiot(kohdalla++);
    }
    
    
    /**
     * Tuhoamista ei ole toteutettu
     * @throws UnsupportedOperationException aina
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Me ei poisteta");
    }
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
        return new RelaatiotIterator();
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Relaatiot relaatiot     = new Relaatiot();
        
        Relaatio rel1           = new Relaatio();
        rel1.rekisteroi();
        rel1.vastaaRelaatio();
        
        Relaatio rel2           = new Relaatio();
        rel2.rekisteroi();
        rel2.vastaaRelaatio();
        
        Relaatio rel3           = new Relaatio();
        rel3.rekisteroi();
        rel3.vastaaRelaatio();
        
        try {
            relaatiot.lisaa(rel1);
            relaatiot.lisaa(rel2);
            relaatiot.lisaa(rel3);
            
            System.out.println("============= Relaatiot testi =================");
            
            for (int i = 0; i < relaatiot.getLkm(); i++) {
                Relaatio relaatio = relaatiot.annaRelaatiot(i);
                System.out.println("Työntekijäntunnus: " + i);
                relaatio.tulosta(System.out);
            }
        } catch ( SailoException ex) {
            System.out.println(ex.getMessage());
        }
        }
        
}
