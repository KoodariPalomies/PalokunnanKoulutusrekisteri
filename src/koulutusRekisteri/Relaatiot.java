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
    private String tiedostonPerusNimi = "relaatiot";     //======================================================================
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
        if ( lkm >= alkiot.length ) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = rel;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Palauttaa viitteen i:teen työntekijään
     * @param i monennenko työntekijän viite halutaan
     * @return viite työntekijään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos ei ole sallitulla alueella
     */
    public Relaatio annaRelaatio(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <=i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + 1);
        return alkiot[i];
    }
    
    
    /**
     * Lukee relaatiot tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Relaatiot relaatiot = new Relaatiot();
     *  Relaatio pitsi21 = new Relaatio(); pitsi21.vastaaPitsinNyplays(2);
     *  Relaatio pitsi11 = new Relaatio(); pitsi11.vastaaPitsinNyplays(1);
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  relaatiot.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  relaatiot.lisaa(pitsi21);
     *  relaatiot.lisaa(pitsi11);
     *  relaatiot.tallenna();
     *  relaatiot = new Harrastukset();
     *  relaatiot.lueTiedostosta(tiedNimi);
     *  Iterator<Relaatio> i = relaatiot.iterator();
     *  i.next().toString() === pitsi21.toString();
     *  i.next().toString() === pitsi11.toString();
     *  i.hasNext() === false;
     *  relaatiot.lisaa(pitsi21);
     *  relaatiot.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            //tiedostonPerusNimi = fi.readLine();
            kokoNimi = fi.readLine();
            //if ( tiedostonPerusNimi == null ) throw new SailoException("Koulutusrekisterin nimi puuttuu");
            if ( kokoNimi == null) throw new SailoException("Koulutusrekisterin nimi puuttuu");
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
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa relaatiot tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Relaatio relaatio : this) {
                fo.println(relaatio.toString());
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
   * Palauttaa tiedoston nimen, jota käytetään tallennukseen
   * @return tallennustiedoston nimi
   */
  public String getTiedostonNimi() {
      return getTiedostonPerusNimi() + ".dat";
  }


  /**
   * Palauttaa varakopiotiedoston nimen
   * @return varakopiotiedoston nimi
   */
  public String getBakNimi() {
      return tiedostonPerusNimi + ".bak";
  }


//===========================================================================================================================================================================================
    
    
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
        return annaRelaatio(kohdalla++);
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
     * Palauttaa taulukosta hakuehtoon vastaavien  työntekijöiden viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä työntekijöistä
     */
    public Collection<Relaatio> etsi(String hakuehto, int k) {
        Collection<Relaatio> loytyneet = new ArrayList<Relaatio>();
        for (Relaatio relaatio : this) {
            loytyneet.add(relaatio);
        }
        return loytyneet;
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
                Relaatio relaatio = relaatiot.annaRelaatio(i);
                System.out.println("Työntekijäntunnus: " + i);
                relaatio.tulosta(System.out);
            }
        } catch ( SailoException ex) {
            System.out.println(ex.getMessage());
        }
        }
        
    }
