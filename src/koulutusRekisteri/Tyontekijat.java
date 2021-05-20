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
 * @version 1.0, 20.4.2021  / Väärin pidetty versiokirjanpito
 * @version 1.1, 4.5.2021   / HT6 testejä
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
    public void lisaa(Tyontekija tyontekija) throws SailoException {
        if ( lkm >= tyontekijat.length) {
            muutettu = true;
        }
        else {
            tyontekijat[lkm++] = tyontekija;
            muutettu = true;
        }
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
     * Tyontekijat tyontekijat = new Tyontekijat();
     * Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka();
     * Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka();
     * Tyontekija aku3 = new Tyontekija(); aku3.vastaaAkuAnkka();
     * String tiedNimi = "testityontekijat";
     * File ftied = new File (tiedNimi+".dat");
     * ftied.delete();
     * tyontekijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * tyontekijat.lisaa(aku1);
     * tyontekijat.lisaa(aku2);
     * tyontekijat.lisaa(aku3);
     * tyontekijat.tallenna();
     * tyontekijat = new Tyontekijat();
     * tyontekijat.lueTiedostosta(tiedNimi);
     * Iterator<Tyontekija> i = tyontekijat.iterator();
     * i.next().toString() === aku1.toString();
     * i.next().toString() === aku2.toString();
     * i.next().toString() === aku3.toString();
     * i.hasNext() === false;
     * tyontekijat.lisaa(aku3);
     * tyontekijat.tallenna();
     * ftied.delete() === true;
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
                Tyontekija tyon = new Tyontekija();
                tyon.parse(rivi);
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
     * Tallentaa työntekijat tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * 2
     * 1|Ankka Aku|Pelastustoiminta|Palomies
     * 2|Ankka Tupu|Pelastustoiminta|Palomies
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
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
         * Luokka työntekijöiden iteroimiseksi.
         * @example
         * <pre name="test">
         * #PACKAGEIMPORT
         * #THROWS SailoException
         * #import java.util.*;
         * 
         * Tyontekijat tyontekijat = new Tyontekijat();
         * Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija();
         * aku1.rekisteroi(); aku2.rekisteroi();
         * 
         * tyontekijat.lisaa(aku1); #THROWS SailoException
         * tyontekijat.lisaa(aku2); #THROWS SailoException
         * tyontekijat.lisaa(aku1); #THROWS SailoException
         * 
         * StringBuffer ids = new StringBuffer(30);
         * for ( Tyontekija tyon:tyontekijat )
         *  ids.append(" "+tyon.getTyontekijaTunnus());
         * 
         * String tulos = " " + aku1.getTyontekijaTunnus() + " " + aku2.getTyontekijaTunnus() + " " + aku1.getTyontekijaTunnus();
         * 
         * ids.toString() === tulos;
         * 
         * ids = new StringBuffer(30);
         * for (Iterator<Tyontekija> i=tyontekijat.iterator(); i.hasNext(); ) {
         *  Tyontekija tyon = i.next();
         *  ids.append(" "+tyon.getTyontekijaTunnus());
         * }
         * 
         * ids.toString() === tulos;
         * 
         * Iterator<Tyontekija> i=tyontekijat.iterator();
         * i.next() == aku1 === true;
         * i.next() == aku2 === true;
         * i.next() == aku1 === true;
         * 
         * i.next(); #THROWS NoSuchElementException
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
         * Palautetaan iteraattori työntekijöistä.
         * @return tyontekijaiteraattori
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
         * Tyontekijat tyontekijat = new Tyontekijat();
         * Tyontekija aku1 = new Tyontekija(); aku1.parse("1|Ankka Aku|Pelastustoiminta|Palomies");
         * Tyontekija aku2 = new Tyontekija(); aku2.parse("2|Ankka Tupu|Pelastustoiminta|Palomies");
         * Tyontekija aku3 = new Tyontekija(); aku3.parse("3|Ankka Hupu|Pelastustoiminta|Palomies");
         * tyontekijat.lisaa(aku1); tyontekijat.lisaa(aku2); tyontekijat.lisaa(aku3);
         * </pre> 
         */ 
        @SuppressWarnings("unused")
        public Collection<Tyontekija> etsiTyontekija(String hakuehto, int t) { 
            Collection<Tyontekija> loytyneet = new ArrayList<Tyontekija>(); 
            for (Tyontekija tyontekija : this) {
                if (hakuehto.isEmpty() || tyontekija.getNimi().toLowerCase().matches("(.*)" + hakuehto.toLowerCase() + "(.*)"))
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
     * Korvaa työntekijän tietorakenteessa.  Ottaa työntekijän omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva työntekijä.  Jos ei löydy, niin lisätään uutena työntekijänä.
     * @param tyontekija lisättävän työntekijän viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Tyontekijat tyontekijat = new Tyontekijat();
     * Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * tyontekijat.getLkm() === 0;
     * tyontekijat.korvaaTaiLisaa(aku1); tyontekijat.getLkm() === 1;
     * tyontekijat.korvaaTaiLisaa(aku2); tyontekijat.getLkm() === 2;
     * Tyontekija aku3 = aku1.clone();
     * aku3.aseta(3,"kkk");
     * Iterator<Tyontekija> it = tyontekijat.iterator();
     * it.next() == aku1 === true;
     * tyontekijat.korvaaTaiLisaa(aku3); tyontekijat.getLkm() === 2;
     * it = tyontekijat.iterator();
     * Tyontekija j0 = it.next();
     * j0 === aku3;
     * j0 == aku3 === true;
     * j0 == aku1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Tyontekija tyontekija) throws SailoException {
        int id = tyontekija.getTyontekijaTunnus();
        for (int i = 0; i < lkm; i++) {
            if ( tyontekijat[i].getTyontekijaTunnus() == id ) {
                tyontekijat[i] = tyontekija;
                muutettu = true;
                return;
            }
        }
        lisaa(tyontekija);
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
