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

import koulutusRekisteri.Tyontekijat.TyontekijatIterator;

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
    
    private static final int MAX_KOULUTUKSIA = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "koulutukset";
    private Koulutus koulutukset[] = new Koulutus[MAX_KOULUTUKSIA];
    //private final ArrayList<Koulutus> alkiot = new ArrayList<Koulutus>();  // Collection
    
    /**
     * Oletusmuodostaja
     */
    public Koulutukset() {
        // Attribuuttien oma alustus riittää
    }
    
    
   // /**
   //  * Haetaan listana kaikki tietokannassa olevat koulutukset.
   //  * @return lista kaikista koulutuksista
   //  */
    //public ArrayList<Koulutus> annaKaikkiKoulutukset() {
      //  return alkiot;
    //}
    
    
    /**
     * Lisää uuden koulutuksen tietorakenteeseen. Ottaa koulutuksen omistukseensa.
     * @param koulutus lisättävän koulutuksen viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    //public void lisaa(Koulutus koulutus) throws SailoException {
      //  alkiot.add(koulutus);
        //muutettu = true;

    //}
    public void lisaa(Koulutus koulutus) throws SailoException {
        if ( lkm >= koulutukset.length ) {
            kasvataTaulukkoa(koulutus);
            muutettu = true;
        }
        else {
            koulutukset[lkm++] = koulutus;
            muutettu = true;
        }
    }
    
    
    /**
     * Kasvatetaan taulukkoa dynaamisesti, kun se täyttyy.
     * @param koulutus taulukko
     */
    public void kasvataTaulukkoa(Koulutus koulutus) {
        Koulutus[] k2 = new Koulutus[koulutukset.length*2];
        
        for (int i = 0; i < lkm; i++) {
            k2[i] = koulutukset[i];
            }
        k2[lkm++] = koulutus;
        koulutukset = k2;
    }
    
    
    /**
     * Palauttaa viitteen i:teen koulutukseen.
     * @param i monennenko koulutuksen viite halutaan
     * @return viite koulutukseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Koulutus annaKoulutus(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return koulutukset[i];
    }
    
    
  //  /**
  //   * Palauttaa viitteen i:teen työntekijään.
  //   * @param tunnusnro viite työntekijään
  //   * @return viite työntekijään, jonka indeksi on i
  //   * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
  //   */
  //  public List<Koulutus> annaKoulutukset(int tunnusnro) {
  //      List<Koulutus> tyontekijanKoulutukset = new ArrayList<Koulutus>();
  //      for (Koulutus koul : alkiot)
  //          if (koul.getKoulutusTunnus() == tunnusnro) tyontekijanKoulutukset.add(koul);
  //      return tyontekijanKoulutukset;
  //  }

    
    
  /**
   * Lukee koulutukset tiedostosta.  Kesken.
   * @param tied tiedoston nimi
   * @throws SailoException jos lukeminen epäonnistuu
   */
    public void lueTiedostosta(String tied) throws SailoException {
    setTiedostonPerusNimi(tied);
    
    try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
        kokoNimi = fi.readLine();
        if ( kokoNimi == null ) throw new SailoException("Koulutuksen nimi puuttuu");
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
        if ( !muutettu ) return;
        File ftied = new File(getTiedostonNimi());

//        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
  //          for (Koulutus koul : this) {
    //            String rivi = koul.toString();
      //          fo.println(rivi);    // fo.println(koul.toString());
        //    }
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(koulutukset.length);
            for (Koulutus koul : this) {
                fo.println(koul.toString());
            }
            
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    
    /**
     * Palauttaa koulutuksen koko nimen
     * @return koulutuksen koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
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
     * Luokka koulutusten iteroimiseksi.
     */
    public class KoulutuksetIterator implements Iterator<Koulutus> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa koulutuksia
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        
        
        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Koulutus next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return annaKoulutus(kohdalla++);
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
     * 
     */
    @Override
    public Iterator<Koulutus> iterator() {
        return new KoulutuksetIterator();
    }
    
    
    /**
     * @param hakuehto hh
     * @param t hh
     * @return hh
     */
    @SuppressWarnings("unused")
    public Collection<Koulutus> etsiKoulutus(String hakuehto, int t) { 
        Collection<Koulutus> loytyneet = new ArrayList<Koulutus>(); 
        for (Koulutus koulutus : this) { 
            loytyneet.add(koulutus);  
        } 
        return loytyneet; 
    }
    
    
    /**
     * Palauttaa koulutusrekisterin työntekijöiden lukumäärän
     * @return työntekijöiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
//    /**
  //   * Palauttaa koulutusrekisterin koulutusten lukumäärän
    // * @return koulutusten lukumäärä
//     */
  //  public int getLkm() {
    //    return alkiot.size();
    //}
    
    
//    /**
  //   * @param i koulutus, jonka -..
    // * @return koulutuksen ...
//     */
  //  public Koulutus annaKoulutus(int i) {
    //    return alkiot.get(i);
//    }
    
    
//    @Override
  //  public Iterator<Koulutus> iterator() {
    //    return alkiot.iterator();
   // }
    
    
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
            
//            for (int i = 0; i < koulutukset.getLkm(); i++) {
  //              List<Koulutus> koulutus = koulutukset.annaKoulutukset(i);
    //            System.out.println("Koulutustunnus: " + i);
      //          ((Koulutus) koulutus).tulosta(System.out);
        //    }
            for (int i = 0; i < koulutukset.getLkm(); i++) {
                Koulutus koulutus = koulutukset.annaKoulutus(i);
                System.out.println("Työntekijätunnus: " + i);
                koulutus.tulosta(System.out);
                
            }
        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }
    }
}
