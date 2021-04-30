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
 * @version 1.0, 22.4.2021 / Tätä ennen en pitänyt järkevää versioseurantaa...
 * @version 1.1, 30.4.2021 / HT6 testien lisäämistä
 *
 */
public class Relaatiot implements Iterable<Relaatio> {
    
    private static final int MAX_RELAATIOITA = 5;
    private boolean muutettu = false;           //======================================================================
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "relaatiot";     //"relaatiot"
    private Relaatio alkiot[] = new Relaatio[MAX_RELAATIOITA];
    
    
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
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * 
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(), rel2 = new Relaatio();
     * relaatiot.getLkm() === 0;
     * relaatiot.lisaa(rel1); relaatiot.getLkm() === 1;
     * relaatiot.lisaa(rel2); relaatiot.getLkm() === 2;
     * relaatiot.lisaa(rel1); relaatiot.getLkm() === 3;
     * relaatiot.anna(0) === rel1;
     * relaatiot.anna(1) === rel2;
     * relaatiot.anna(2) === rel1;
     * relaatiot.anna(1) == rel1 === false;
     * relaatiot.anna(1) == rel2 === true;
     * relaatiot.anna(3) === rel1; #THROWS IndexOutOfBoundsException
     * relaatiot.lisaa(rel1); relaatiot.getLkm() === 4;
     * relaatiot.lisaa(rel1); relaatiot.getLkm() === 5;
     * relaatiot.lisaa(rel1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Relaatio rel) throws SailoException {
        if ( lkm >= alkiot.length ) {
            muutettu = true;
        }
        else {
                alkiot[lkm++] = rel;
                muutettu = true;
            }
    }
    
    
    /**
     * Palauttaa viitteen i:teen relaatioon
     * @param n monennenko relaation viite halutaan
     * @return viite relaatioon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos ei ole sallitulla alueella
     */
    public List<Relaatio> annaRelaatiot(int n) throws IndexOutOfBoundsException {
        List<Relaatio> relaatiot = new ArrayList<Relaatio>();
        
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTyontekijaTunnus() == n) {
                relaatiot.add(alkiot[i]);
            }
        }
        return relaatiot;
    }
    
    
    /**
     * Lukee relaatiot tiedostosta.
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(), rel2 = new Relaatio();
     * rel1.vastaaRelaatio();
     * rel2.vastaaRelaatio();
     * String hakemisto = "testirelaatiot";
     * String tiedNimi = hakemisto+"/relaatiot";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * 
     * relaatiot.lueTiedostosta(tiedNimi); #THROWS SailoException
     * relaatiot.lisaa(rel1);
     * relaatiot.lisaa(rel2);
     * relaatiot.talleta();
     * relaatiot = new Relaatiot();
     * relaatiot.lueTiedostosta(tiedNimi);
     * 
     * Iterator<Relaatio> i = relaatiot.iterator();
     * i.next() === rel1;
     * i.next() === rel2;
     * i.hasNext() === false;
     * relaatiot.lisaa(rel2);
     * relaatiot.talleta();
     * ftied.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Työntekijän nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
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
        System.out.println(tied);
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa relaatiot tiedostoon.
     * Tiedoston muoto:
     * <pre>
     * 1|1|1|1.1.2021|1.1.2031
     * 2|1|2|1.1.2021|1.1.2031
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        if ( !muutettu ) return;

        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (int i = 0; i <lkm; i++) {
                if (alkiot[i] == null) return;
                String rivi = alkiot[i].toString();
                fo.println(rivi);
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }    
    
    
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
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
  /**
   * Asettaa tiedoston perusnimen ilman tarkenninta
   * @param tied tallennustiedoston perusnimi
   */
  public void setTiedostonPerusNimi(String tied) {
      tiedostonPerusNimi = tied;
  }
    
    
    /**
     * Luokka relaatioiden iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(), rel2 = new Relaatio();
     * rel1.rekisteroi(); rel2.rekisteroi();
     * 
     * relaatiot.lisaa(rel1);
     * relaatiot.lisaa(rel2);
     * relaatiot.lisaa(rel1);
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Relaatio relaatio:relaatiot)
     *      ids.append(" "+relaatio.getRelaatioTunnus());
     *      
     * String tulos = " " + rel1.getRelaatioTunnus() + " " + rel2.getRelaatioTunnus() + " " + rel1.getRelaatioTunnus();
     * 
     * ids.toString() === tulos;
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Relaatio> i=relaatiot.iterator(); i.hasNext(); {
     *      Relaatio relaatio = i.next();
     *      ids.append(" "+relaatio.getRelaatioTunnus());
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Relaatio> i=relaatiot.iteratori();
     * i.next() == rel1 === true;
     * i.next() == rel2 === true;
     * i.next() == rel1 === true;
     * 
     * i.next(); #THROWS NoSuchElementException
     * </pre>
     */
    public class RelaatiotIterator implements Iterator<Relaatio> {
        private int kohdalla = 0;
    
    /**
     * Onko olemassa vielä seuraavaa relaatiota
     * @see java.util.Iterator#hasNext()
     * @return true jos on vielä relaatioita
     */
    @Override
    public boolean hasNext() {
        return kohdalla < getLkm();
    }
    
    
    /**
     * Annetaan seuraava relaatio
     * @return seuraava relaatio
     * @throws NoSuchElementException jos seuraava alkiota ei enää ole
     * @see java.util.Iterator#next()
     */
    @Override
    public Relaatio next() throws NoSuchElementException {
        if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
        return annaRelaatiot(kohdalla++);
    }
    
    
    /**
     * @param i hh
     * @return yksittäisen relaation paikassa i
     */
    public Relaatio annaRelaatiot(int i) {
        if (i < 0 || i < lkm) return null;
        return alkiot[i];
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
            
        } catch ( SailoException ex) {
            System.out.println(ex.getMessage());
        }
        }
        
}
