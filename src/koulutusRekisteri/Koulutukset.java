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
 * @version 1.0, 24.3.2021  / Väärin pidetty versiokirjanpito
 * @version 1.1, 4.5.2021   / HT6 testejä
 * @version 1.2, 14.5.2021  / Lisätty lisaa -aliohjelmaan Arrays.copyOf
 * @version 1.3, 19.5.2021  / Muutettu rakenne taulukosta listaksi
 *
 */
public class Koulutukset implements Iterable<Koulutus> {
    
    /*
    private static final int MAX_KOULUTUKSIA = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "koulutukset";
    private Koulutus koulutukset[] = new Koulutus[MAX_KOULUTUKSIA];
    */
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";
    
    /**
     * Taulukko koulutuksista
     */
    private final List<Koulutus> koulutukset = new ArrayList<Koulutus>();
    
    
    /**
     * Oletusmuodostaja
     */
    public Koulutukset() {
        // Attribuuttien oma alustus riittää
    }
    
    
    /**
     * Lisää uuden koulutuksen tietorakenteeseen. Ottaa koulutuksen omistukseensa.
     * @param koulutus lisättävän koulutuksen viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Koulutukset koulutukset = new Koulutukset();
     * Koulutus vesi1 = new Koulutus(), vesi2 = new Koulutus();
     * koulutukset.getLkm() === 0;
     * koulutukset.lisaa(vesi1); koulutukset.getLkm() === 1;
     * koulutukset.lisaa(vesi2); koulutukset.getLkm() === 2;
     * koulutukset.lisaa(vesi1); koulutukset.getLkm() === 3;
     * koulutukset.annaKoulutus(0) === vesi1;
     * koulutukset.annaKoulutus(1) === vesi2;
     * koulutukset.annaKoulutus(2) === vesi1;
     * koulutukset.annaKoulutus(1) == vesi1 === false;
     * koulutukset.annaKoulutus(1) == vesi2 === true;
     * koulutukset.annaKoulutus(3) === vesi1; #THROWS IndexOutOfBoundsException
     * koulutukset.lisaa(vesi1); koulutukset.getLkm() === 4;
     * koulutukset.lisaa(vesi1); koulutukset.getLkm() === 5;
     * koulutukset.lisaa(vesi1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Koulutus koulutus) throws SailoException {
        //if (lkm >= koulutukset.length) koulutukset = Arrays.copyOf(koulutukset, lkm+20); 
        //koulutukset[lkm] = koulutus;
        //lkm++;
        //muutettu = true;
        koulutukset.add(koulutus);
        muutettu = true;
    }
    
    
    /**
     * Palauttaa viitteen i:teen koulutukseen.
     * @param i monennenko koulutuksen viite halutaan
     * @return viite koulutukseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     
    public Koulutus annaKoulutus(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return koulutukset[i];
    }
    */
    public List<Koulutus> annaKoulutus(int i) {
        List<Koulutus> loydetyt = new ArrayList<Koulutus>();
        for (Koulutus koul : koulutukset)
            if (koul.getKoulutusTunnus() == i) loydetyt.add(koul);
        return loydetyt;
    }
    
    
  /**
   * Lukee koulutukset tiedostosta.
   * @param tied tiedoston nimi
   * @throws SailoException jos lukeminen epäonnistuu
   * 
   * @example
   * <pre name="test">
   * #THROWS SailoException
   * #import java.io.File;
   * Koulutukset koulutukset = new Koulutukset();
   * Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja();
   * Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja();
   * Koulutus vesi3 = new Koulutus(); vesi3.vastaaVesisukeltaja();
   * String tiedNimi = "testikoulutukset";
   * File ftied = new File (tiedNimi+".dat");
   * ftied.delete();
   * koulutukset.lueTiedostosta(tiedNimi); #THROWS SailoException
   * koulutukset.lisaa(vesi1);
   * koulutukset.lisaa(vesi2);
   * koulutukset.lisaa(vesi3);
   * koulutukset.tallenna();
   * koulutukset = new Koulutukset();
   * koulutukset.lueTiedostosta(tiedNimi);
   * Iterator<Koulutus> i = koulutukset.iterator();
   * i.next().toString() === vesi1.toString();
   * i.next().toString() === vesi2.toString();
   * i.next().toString() === vesi3.toString();
   * i.hasNext() === false;
   * koulutukset.lisaa(vesi3);
   * koulutukset.tallenna();
   * ftied.delete() === true;
   */
    public void lueTiedostosta(String tied) throws SailoException {
    setTiedostonPerusNimi(tied);
    /*
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
    */
    try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
        
        String rivi;
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
     * Tallentaa koulutukset tiedostoon.
     * Tiedoston muoto:
     * <pre>
     * 2
     * 1|Vesisukeltaja
     * 2|Vesisukeltaja
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            //fo.println(getKokoNimi());
            //fo.println(koulutukset.length);
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
     
    public String getKokoNimi() {
        return kokoNimi;
    }
    */
    
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
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Koulutukset koulutukset = new Koulutukset();
     * Koulutus vesi1 = new Koulutus(), vesi2 = new Koulutus();
     * vesi1.rekisteroi(); vesi2.rekisteroi();
     * 
     * koulutukset.lisaa(vesi1);
     * koulutukset.lisaa(vesi2);
     * koulutukset.lisaa(vesi1);
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Koulutus koulutus:koulutukset)
     *      ids.append(" "+koulutus.getKoulutusTunnus());
     *      
     * String tulos = " " + vesi1.getKoulutusTunnus() + " " + vesi2.getKoulutusTunnus() + " " + vesi1.getKoulutusTunnus();
     * 
     * ids.toString() === tulos;
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Koulutus> i=koulutukset.iterator(); i.hasNext(); ) {
     *      Koulutus koulutus = i.next();
     *      ids.append(" "+koulutus.getKoulutusTunnus());
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Koulutus> i=koulutukset.iterator();
     * i.next() == vesi1 === true;
     * i.next() == vesi2 === true;
     * i.next() == vesi1 === true;
     * 
     * i.next(); #THROWS NoSuchElementException
     * 
     * </pre>
     
    public class KoulutuksetIterator implements Iterator<Koulutus> {
        private int kohdalla = 0;
        */
    
        /**
         * Palautetaan iteraattori koulutuksista.
         */
        @Override
        public Iterator<Koulutus> iterator() {
            return koulutukset.iterator();
        }

        /**
         * Onko olemassa vielä seuraavaa koulutuksia
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        */
        
        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         
        @Override
        public Koulutus next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return annaKoulutus(kohdalla++);
        }
        */
        
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    */
    
    /**
     * Palautetaan iteraattori koulutuksista.
     
    @Override
    public Iterator<Koulutus> iterator() {
        return new KoulutuksetIterator();
    }
    */
    
    
    /**
     * Palauttaa "taulukosta" hakuehtoon vastaavien koulutusten viitteet
     * @param hakuehto hakuehto
     * @param t etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä koulutuksista
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Koulutukset koulutukset = new Koulutukset();
     * Koulutus vesi1 = new Koulutus(); vesi1.parse("1|Vesisukeltaja");
     * Koulutus vesi2 = new Koulutus(); vesi2.parse("1|Vesisukeltaja");
     * Koulutus vesi3 = new Koulutus(); vesi3.parse("1|Vesisukeltaja");
     * koulutukset.lisaa(vesi1); koulutukset.lisaa(vesi2); koulutukset.lisaa(vesi3);
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
        //return lkm;
        return koulutukset.size();
    }
    
    
    /**
     * Korvaa koulutuksen tietorakenteessa.  Ottaa koulutuksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva koulutus.  Jos ei löydy, niin lisätään uutena koulutuksena.
     * @param koulutus lisättävän koulutuksen viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Koulutukset koulutukset = new Koulutukset();
     * Koulutus aku1 = new Koulutus(), aku2 = new Koulutus();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * koulutukset.getLkm() === 0;
     * koulutukset.korvaaTaiLisaa(aku1); koulutukset.getLkm() === 1;
     * koulutukset.korvaaTaiLisaa(aku2); koulutukset.getLkm() === 2;
     * Koulutus aku3 = aku1.clone();
     * aku3.aseta(3,"kkk");
     * Iterator<Koulutus> it = koulutukset.iterator();
     * it.next() == aku1 === true;
     * koulutukset.korvaaTaiLisaa(aku3); koulutukset.getLkm() === 2;
     * it = koulutukset.iterator();
     * Koulutus j0 = it.next();
     * j0 === aku3;
     * j0 == aku3 === true;
     * j0 == aku1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Koulutus koulutus) throws SailoException {
        //int id = koulutus.getKoulutusTunnus();
        //for (int i = 0; i < lkm; i++) {
          //  if ( koulutukset[i].getKoulutusTunnus() == id ) {
            //    koulutukset[i] = koulutus;
              //  muutettu = true;
                //return;
        int id = koulutus.getKoulutusTunnus();
        for (int i = 0; i < getLkm(); i++) {
            if (koulutukset.get(i).getKoulutusTunnus() == id) {
                koulutukset.set(i, koulutus);
                muutettu = true;
                return;
            }
        }
        lisaa(koulutus);
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

        System.out.println("============= Harrastukset testi =================");

        List<Koulutus> koulutukset2 = koulutukset.annaKoulutus(2);

        for (Koulutus koul : koulutukset2) {
            System.out.print(koul.getKoulutusTunnus() + " ");
            koul.tulosta(System.out);
        }

        /*
        try {
            koulutukset.lisaa(pitsi1);
            koulutukset.lisaa(pitsi2);
            
            System.out.println("========== Työntekijät testi ==========");
            
            for (int i = 0; i < koulutukset.getLkm(); i++) {
                Koulutus koulutus = koulutukset.annaKoulutus(i);
                System.out.println("Työntekijätunnus: " + i);
                koulutus.tulosta(System.out);
                
            }
        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }
        */
    }  catch (SailoException e) {
        System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon. 
}
    }
}
