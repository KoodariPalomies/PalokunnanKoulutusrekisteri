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
    private String tiedostonPerusNimi = "koulutukset";
    private final Collection<Koulutus> alkiot = new ArrayList<Koulutus>();
    
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
        List<Koulutus> loydetyt = new ArrayList<Koulutus>();
        for (Koulutus har : alkiot)
            if (har.getKoulutusTunnus() == tunnusnro) loydetyt.add(har);
        return loydetyt;
    }

    
    
      /**
       * Lukee koulutukset tiedostosta.  Kesken.
       * @param tied tiedoston hakemisto
       * @throws SailoException jos lukeminen epäonnistuu
       */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Koulutus koul = new Koulutus();
                koul.parse(rivi); // voisi olla virhekäsittely
                lisaa(koul);
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
     * Tallentaa työntekijät tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Koulutus har : this) {
                fo.println(har.toString());
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
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    
    /**
     * Palauttaa koulutusrekisterin koulutusten lukumäärän
     * @return koulutusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
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
                List<Koulutus> koulutus = koulutukset.annaKoulutukset(i);
                System.out.println("Koulutustunnus: " + i);
                ((Koulutus) koulutus).tulosta(System.out);
            }
    
        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }

    }

    @Override
    public Iterator<Koulutus> iterator() {
        return alkiot.iterator();
    }

    public Collection<Koulutus> etsiKoulutus(String hakuehto, int k) {
        // TODO Auto-generated method stub
        return null;
    }

    public Koulutus annaKoulutus(int i) {
        // TODO Auto-generated method stub
        return null;
    }
}