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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Tyontekijat                         | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Tyontekija      | 
 * | - Pitää yllä varsinaista työntekijälistaa, eli     |                   | 
 * |   osaa lisätä ja poistaa työntekijän.              |                   |
 * |                                                    |                   | 
 * | - Lukee ja kirjoittaa työntekijät tiedostoon.      |                   |
 * |                                                    |                   | 
 * | - Osaa etsiä ja lajitella.                         |                   |  
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author mitulit
 * @version 20.4.2021
 *
 */
public class Tyontekijat implements Iterable<Tyontekija>{

        private static final int MAX_TYONTEKIJOITA = 5;
        private boolean muutettu = false;
        private int lkm = 0;
        private String kokoNimi = "";
        private String tiedostonPerusNimi = "tyontekijat";
        private Tyontekija tyontekijat[] = new Tyontekija[MAX_TYONTEKIJOITA];

    
    /**
     * Oletusmuodostaja
     */
    public Tyontekijat() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * Lisää uuden työntekijän tietorakenteeseen. Ottaa työntekijän omistukseensa.
     * @param tyontekija lisättävän työntekijän viite. Huom tietorakenne muuttuu omistajaksi
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
    public void lisaa(Tyontekija tyontekija) throws SailoException {        // Tässä lienee jotain vikaa...
        //if ( lkm >= tyontekijat.length ) throw new SailoException("Liikaa alkioita");
        //tyontekijat[lkm] = tyontekija;   // laita watchiin debuggausta varten
        //lkm++;
        //muutettu = true;
        if ( lkm >= tyontekijat.length ) {
            kasvataTaulukkoa(tyontekija);
            muutettu = true;
        }
        else {
            tyontekijat[lkm++] = tyontekija;
            muutettu = true;
        }
    }
    
    
    /**
     * Kasvatetaan taulukkoa dynaamisesti, kun se täyttyy.
     * @param tyontekija taulukko
     */
    public void kasvataTaulukkoa(Tyontekija tyontekija) {
        Tyontekija[] t2 = new Tyontekija[tyontekijat.length*2];
        
        for (int i = 0; i < lkm; i++) {
            t2[i] = tyontekijat[i];
            }
        t2[lkm++] = tyontekija;
        tyontekijat = t2;
    }
    
    
    /**
     * Palauttaa viitteen i:teen työntekijään.
     * @param i monennenko työntekijän viite halutaan
     * @return viite työntekijään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Tyontekija annaTyontekija(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return tyontekijat[i];
    }
    
    
    /**
     * Lukee työntekijät tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Jasenet jasenet = new Jasenet();
     *  Jasen aku1 = new Jasen(), aku2 = new Jasen();
     *  aku1.vastaaAkuAnkka();
     *  aku2.vastaaAkuAnkka();
     *  String hakemisto = "testikelmit";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  jasenet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  jasenet.lisaa(aku1);
     *  jasenet.lisaa(aku2);
     *  jasenet.tallenna();
     *  jasenet = new Jasenet();            // Poistetaan vanhat luomalla uusi
     *  jasenet.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Jasen> i = jasenet.iterator();
     *  i.next() === aku1;
     *  i.next() === aku2;
     *  i.hasNext() === false;
     *  jasenet.lisaa(aku2);
     *  jasenet.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        // tähän voisi kovakoodata setTiedostonPerusNimi(tied); --> tiedostonPerusNimi = "tyontekijat"
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
        // tähän voisi tehdä tilalle String rivi = ""; ja poistaa kaikki ennen while -lausetta
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Työntekijän nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Tyontekija tyon = new Tyontekija();
                tyon.parse(rivi); // voisi olla virhekäsittely
                lisaa(tyon);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
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
         * Tallentaa jäsenistön tiedostoon.  
         * Tiedoston muoto:
         * <pre>
         * Kelmien kerho
         * 20
         * ; kommenttirivi
         * 2|Ankka Aku|121103-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
         * 3|Ankka Tupu|121153-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
         * </pre>
         * @throws SailoException jos talletus epäonnistuu
         */
        public void tallenna() throws SailoException {
            // tähän voisi kovakoodata tiedostonPerusNimi = "tyontekijat";
            if ( !muutettu ) return;

            File fbak = new File(getBakNimi());
            File ftied = new File(getTiedostonNimi());
            fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
            ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

            try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            // tähän voisi koodata for (Tyontekija tyon : this {
                //                      String rivi = tyon.toString();
                //                      fo.println(rivi);
                //                  }
                fo.println(getKokoNimi());
                fo.println(tyontekijat.length);
                for (Tyontekija tyon : this) {
                    fo.println(tyon.toString());
                }
            } catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
            } catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
            }

            muutettu = false;
            }
        
        
        /**
         * Palauttaa tyontekijän koko nimen
         * @return työntekijän koko nimi merkkijononna
         */
        public String getKokoNimi() {
            return kokoNimi;
        }
        
        
        /**
         * Palauttaa tiedoston nimen, jota käytetään tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonPerusNimi() {
            return tiedostonPerusNimi;
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
        
        
        /**
         * Luokka työntekijöiden iteroimiseksi.
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * #PACKAGEIMPORT
         * #import java.util.*;
         * 
         * Jasenet jasenet = new Jasenet();
         * Jasen aku1 = new Jasen(), aku2 = new Jasen();
         * aku1.rekisteroi(); aku2.rekisteroi();
         *
         * jasenet.lisaa(aku1); 
         * jasenet.lisaa(aku2); 
         * jasenet.lisaa(aku1); 
         * 
         * StringBuffer ids = new StringBuffer(30);
         * for (Jasen jasen:jasenet)   // Kokeillaan for-silmukan toimintaa
         *   ids.append(" "+jasen.getTunnusNro());           
         * 
         * String tulos = " " + aku1.getTunnusNro() + " " + aku2.getTunnusNro() + " " + aku1.getTunnusNro();
         * 
         * ids.toString() === tulos; 
         * 
         * ids = new StringBuffer(30);
         * for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
         *   Jasen jasen = i.next();
         *   ids.append(" "+jasen.getTunnusNro());           
         * }
         * 
         * ids.toString() === tulos;
         * 
         * Iterator<Jasen>  i=jasenet.iterator();
         * i.next() == aku1  === true;
         * i.next() == aku2  === true;
         * i.next() == aku1  === true;
         * 
         * i.next();  #THROWS NoSuchElementException
         *  
         * </pre>
         */
        public class TyontekijatIterator implements Iterator<Tyontekija> {
            private int kohdalla = 0;


            /**
             * Onko olemassa vielä seuraavaa jäsentä
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
            public Tyontekija next() throws NoSuchElementException {
                if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
                return annaTyontekija(kohdalla++);
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
        public Iterator<Tyontekija> iterator() {
            return new TyontekijatIterator();
        }
        
        
        /** 
         * Palauttaa "taulukossa" hakuehtoon vastaavien työntekijöiden viitteet 
         * @param hakuehto hakuehto 
         * @param t etsittävän kentän indeksi
         * @return tietorakenteen löytyneistä työntekijöistä 
         * @example 
         * <pre name="test"> 
         * #THROWS SailoException  
         *   Jasenet jasenet = new Jasenet(); 
         *   Jasen jasen1 = new Jasen(); jasen1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
         *   Jasen jasen2 = new Jasen(); jasen2.parse("2|Ankka Tupu||030552-123B|"); 
         *   Jasen jasen3 = new Jasen(); jasen3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
         *   Jasen jasen4 = new Jasen(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
         *   Jasen jasen5 = new Jasen(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
         *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
         *   // TODO: toistaiseksi palauttaa kaikki jäsenet 
         * </pre> 
         */ 
        @SuppressWarnings("unused")
        public Collection<Tyontekija> etsiTyontekija(String hakuehto, int t) { 
            Collection<Tyontekija> loytyneet = new ArrayList<Tyontekija>(); 
            for (Tyontekija tyontekija : this) { 
                loytyneet.add(tyontekija);  
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

    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tyontekijat tyontekijat = new Tyontekijat();
        
        Tyontekija aku  = new Tyontekija();
        Tyontekija aku2 = new Tyontekija();
        
        aku.rekisteroi();
        aku2.rekisteroi();
        
        aku.vastaaAkuAnkka();
        aku2.vastaaAkuAnkka();
        
        try {
            tyontekijat.lisaa(aku);
            tyontekijat.lisaa(aku2);
            
            System.out.println("========== Työntekijät testi ==========");
            
            for (int i = 0; i < tyontekijat.getLkm(); i++) {
                Tyontekija tyontekija = tyontekijat.annaTyontekija(i);
                System.out.println("Työntekijätunnus: " + i);
                tyontekija.tulosta(System.out);
            }

        } catch (SailoException e) {
            System.err.println(e.getMessage());     // Virhetiedot voidaan tietovirroilla ohjata menemään omaan lokitiedostoon.
        }

    }
    
}
