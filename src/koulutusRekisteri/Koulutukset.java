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
 * @version 1.0, 24.3.2021  / Väärin pidetty versiokirjanpito
 * @version 1.1, 4.5.2021   / HT6 testejä
 * @version 1.2, 14.5.2021  / Lisätty lisaa -aliohjelmaan Arrays.copyOf
 * @version 1.3, 19.5.2021  / Muutettu rakenne taulukosta listaksi
 * @version 1.4, 20.5.2021  / Viimeistelyt
 */
public class Koulutukset implements Iterable<Koulutus> {
    
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
        koulutukset.add(koulutus);
        muutettu = true;
    }
    
    
    /**
     * Palauttaa viitteen i:teen koulutukseen.
     * @param i monennenko koulutuksen viite halutaan
     * @return viite koulutukseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
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
     * 1|Vesisukeltaja
     * 2|Vesisukeltaja
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            
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
     * Iteraattori kaikkien koulutusten läpikäymiseksi.
     * @return koulutusiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Koulutukset koulutukset = new Koulutukset();
     * Koulutus vesi1 = new Koulutus(1); vesi1.rekisteroi(); koulutukset.lisaa(vesi1);
     * Koulutus vesi2 = new Koulutus(2); vesi2.rekisteroi(); koulutukset.lisaa(vesi2);
     * 
     * Iterator<Koulutus> i=koulutukset.iterator();
     * i.next() === vesi1;
     * i.next() === vesi2;
     * i.next() === vesi3; #THROWS NoSuchElementException 
     * 
     * int n = 0;
     * int jnrot[] = {1,2};
     * 
     * for ( Koulutus koul : koulutukset) {
     *  koul.getKoulutusTunnus() === jnrot[n]; n++;
     *  }
     *  
     *  n === 2;
     * 
     * </pre>
     */
    @Override
    public Iterator<Koulutus> iterator() {
        return koulutukset.iterator();
    }
    
    
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
        return koulutukset.size();
    }
    
    
    /**
     * Korvaa koulutuksen tietorakenteessa.  Ottaa koulutuksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva koulutus.  Jos ei löydy, niin lisätään uutena koulutuksena.
     * 
     * @param koulutus lisättävän koulutuksen viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * 
     * @example
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

    }  catch (SailoException e) {
        System.err.println(e.getMessage());
    }
    }
}
