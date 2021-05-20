package koulutusRekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.*;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


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
 * @version 1.0, 22.4.2021  / Tätä ennen en pitänyt järkevää versioseurantaa...
 * @version 1.1, 30.4.2021  / HT6 testien lisäämistä
 * @version 1.2, 10.5.2021  / Lisätty poista + etsiId
 * @version 1.3, 13.5.2021  / Lisätty poistaTyontekijanKoulutus() + muokattu poista()
 * @version 1.4, 20.5.2021  / Muokattu lisaa() -aliohjelma toimimaan listalla
 */
public class Relaatiot implements Iterable<Relaatio> {

    private static final int MAX_RELAATIOITA = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "relaatiot";
    private Relaatio alkiot[] = new Relaatio[MAX_RELAATIOITA];

    
    /**
     * Relaatioiden alustaminen
     */
    public Relaatiot() {
        // EI vielä
    }


    /**
     * Poistetaan relaatio taulukosta, jolla on valittu relaatiotunnus
     * @param id poistettavan relaation id
     * @return 0 jos epäonnistui ja 1 jos onnistui
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(), rel2 = new Relaatio(), rel3 = new Relaatio();
     * rel1.rekisteroi(); rel2.rekisteroi(); rel3.rekisteroi();
     * int id1 = rel1.getRelaatioTunnus();
     * relaatiot.lisaa(rel1); relaatiot.lisaa(rel2); relaatiot.lisaa(rel3);
     * relaatiot.poista(id1+1) === 1; 
     * relaatiot.annaId(id1+1) === null; relaatiot.getLkm() === 2; 
     * relaatiot.poista(id1) === 1; relaatiot.getLkm() === 1; 
     * relaatiot.poista(id1+3) === 0; relaatiot.getLkm() === 1;
     * </pre>
     */
    public int poista(int id) {
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getRelaatioTunnus() == id) {
                while (i < alkiot.length - 1) {
                    alkiot[i] = alkiot[i + 1];
                    i++;
                    muutettu = true;
                }
                lkm--;
                return 1;
            }
        }
        return 0;
    }


    /** 
     * Etsii relaation id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen relaation indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Relaatiot relaatiot = new Relaatiot(); 
     * Relaatio rel1 = new Relaatio(), rel2 = new Relaatio(), rel3 = new Relaatio(); 
     * rel1.rekisteroi(); rel2.rekisteroi(); rel3.rekisteroi(); 
     * int id1 = rel1.getRelaatioTunnus(); 
     * relaatiot.lisaa(rel1); relaatiot.lisaa(rel2); relaatiot.lisaa(rel3); 
     * relaatiot.etsiId(id1+1) === 1; 
     * relaatiot.etsiId(id1+2) === 2; 
     * </pre> 
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++)
            if (id == alkiot[i].getRelaatioTunnus())
                return i;
        return -1;
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
     * relaatiot.annaRelaatiot(0) === rel1;
     * relaatiot.annaRelaatiot(1) === rel2;
     * relaatiot.annaRelaatiot(2) === rel1;
     * relaatiot.annaRelaatiot(1) == rel1 === false;
     * relaatiot.annaRelaatiot(1) == rel2 === true;
     * relaatiot.annaRelaatiot(3) === rel1; #THROWS IndexOutOfBoundsException
     * relaatiot.lisaa(rel1); relaatiot.getLkm() === 4;
     * relaatiot.lisaa(rel1); relaatiot.getLkm() === 5;
     * relaatiot.lisaa(rel1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Relaatio rel) throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+MAX_RELAATIOITA);
        alkiot[lkm] = rel;
        lkm++;
        muutettu = true;
    }


    /**
     * Palauttaa viitteen i:teen relaatioon
     * @param n monennenko relaation viite halutaan
     * @return viite relaatioon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos ei ole sallitulla alueella
     */
    public List<Relaatio> annaRelaatiot(int n)
            throws IndexOutOfBoundsException {
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

        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi()))) {
            kokoNimi = fi.readLine();
            if (kokoNimi == null)
                throw new SailoException("Relaatio puuttuu");
            String rivi = fi.readLine();
            if (rivi == null)
                throw new SailoException("Maksimikoko puuttuu");

            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';')
                    continue;
                Relaatio rel = new Relaatio();
                rel.parse(rivi);
                lisaa(rel);
            }
            muutettu = false;

        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException(
                    "Ongelmia tiedoston kanssa: " + e.getMessage());
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
    public void tallenna() throws SailoException {
        if (!muutettu)
            return;
        File ftied = new File(getTiedostonNimi());

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(ftied.getCanonicalPath()))) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Relaatio rel : this) {
                fo.println(rel.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException(
                    "Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName()
                    + " kirjoittamisessa ongelmia");
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
     * for (Iterator<Relaatio> i=relaatiot.iterator(); i.hasNext();) {
     *      Relaatio relaatio = i.next();
     *      ids.append(" "+relaatio.getRelaatioTunnus());
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Relaatio> i=relaatiot.iterator();
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
            if (!hasNext())
                throw new NoSuchElementException("Ei oo");
            return annaRelaatiot(kohdalla++);
        }


        /**
         * Palauttaa viitteen i:teen relaatioon.
         * @param i monennenko relaation viite halutaan
         * @return viite relaatioon, jonka indeksi on i
         * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella 
         */
        public Relaatio annaRelaatiot(int i) throws IndexOutOfBoundsException {
            if (i < 0 || lkm <= i)
                throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
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
     * #THROWS SailoException
     * #import java.util.*;
     * 
     * Relaatiot relaatiot = new Relaatiot();
     * Relaatio rel1 = new Relaatio(); rel1.rekisteroi(); relaatiot.lisaa(rel1); #THROWS SailoException
     * Relaatio rel2 = new Relaatio(); rel2.rekisteroi(); relaatiot.lisaa(rel2); #THROWS SailoException
     * Relaatio rel3 = new Relaatio(); rel3.rekisteroi(); relaatiot.lisaa(rel3); #THROWS SailoException
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
        Relaatiot relaatiot = new Relaatiot();

        Relaatio rel1 = new Relaatio();
        rel1.rekisteroi();
        rel1.vastaaRelaatio();

        Relaatio rel2 = new Relaatio();
        rel2.rekisteroi();
        rel2.vastaaRelaatio();

        Relaatio rel3 = new Relaatio();
        rel3.rekisteroi();
        rel3.vastaaRelaatio();

        try {
            relaatiot.lisaa(rel1);
            relaatiot.lisaa(rel2);
            relaatiot.lisaa(rel3);

            System.out
                    .println("============= Relaatiot testi =================");

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
