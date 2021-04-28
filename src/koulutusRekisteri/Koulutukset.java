/**
 * 
 */
package koulutusRekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Koulutukset implements Iterable<Koulutus> {
    
    //private static final int    MAX_KOULUTUKSIA     = 5;         // Vakio, mallin vuoksi 5 kpl
    //private int                 lkm                 = 0;
    //private String              tiedostonNimi       = "";
    //private Koulutus[]          alkiot              = new Koulutus[MAX_KOULUTUKSIA];
    private boolean muutettu = false;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "koulutukset";      // tai "koulutukset"
    private final ArrayList<Koulutus> alkiot = new ArrayList<Koulutus>();  // Collection
    
    /**
     * Oletusmuodostaja
     */
    public Koulutukset() {
        // Attribuuttien oma alustus riittää
    }
    
    
    /**
     * Haetaan listana kaikki tietokannassa olevat koulutukset.
     * @return lista kaikista koulutuksista
     */
    public ArrayList<Koulutus> annaKaikkiKoulutukset() {
        return alkiot;
    }
    
    
    /**
     * Lisää uuden työntekijän tietorakenteeseen. Ottaa työntekijän omistukseensa.
     * @param koulutus lisättävän työntekijän viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void lisaa(Koulutus koulutus) throws SailoException {
        alkiot.add(koulutus);
        muutettu = true;

    }
    
    
    /**
     * Palauttaa viitteen i:teen työntekijään.
     * @param tunnusnro viite työntekijään
     * @return viite työntekijään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public List<Koulutus> annaKoulutukset(int tunnusnro) {
        List<Koulutus> tyontekijanKoulutukset = new ArrayList<Koulutus>();
        for (Koulutus koul : alkiot)
            if (koul.getKoulutusTunnus() == tunnusnro) tyontekijanKoulutukset.add(koul);
        return tyontekijanKoulutukset;
    }

    
    
      /**
       * Lukee koulutukset tiedostosta.  Kesken.
     * @param tied tiedoston nimi
       * @throws SailoException jos lukeminen epäonnistuu
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
            Koulutus koul = new Koulutus();
            koul.parse(rivi);
            lisaa(koul);
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
     * Tallentaa työntekijät tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        tiedostonPerusNimi = "koulutukset.dat";
        if ( !muutettu ) return;

        //File fbak = new File(getBakNimi());             // turha?
        File ftied = new File(getTiedostonPerusNimi());
        //fbak.delete(); //  if ... System.err.println("Ei voi tuhota");          // turha?
        //ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");   // turha?

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Koulutus koul : this) {
                String rivi = koul.toString();
                fo.println(rivi);    // fo.println(koul.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;

    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
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


//    /**
//     * Palauttaa varakopiotiedoston nimen
  //   * @return varakopiotiedoston nimi
    // */
//    public String getBakNimi() {
  //      return tiedostonPerusNimi + ".bak";
    //}

    
    /**
     * Palauttaa koulutusrekisterin koulutusten lukumäärän
     * @return koulutusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * @param i koulutus, jonka -..
     * @return koulutuksen ...
     */
    public Koulutus annaKoulutus(int i) {
        return alkiot.get(i);
    }
    
    
    @Override
    public Iterator<Koulutus> iterator() {
        return alkiot.iterator();
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
            
          //  System.out.println("========== Työntekijät testi ==========");
            
          //  for (int i = 0; i < koulutukset.getLkm(); i++) {
            //    List<Koulutus> koulutus = koulutukset.annaKoulutukset(i);
              //  System.out.println("Koulutustunnus: " + i);
                //((Koulutus) koulutus).tulosta(System.out);
            //}
    
        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }

    }
}
//