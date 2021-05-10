package koulutusRekisteri;

//===================================================================================================================================
import java.io.File;
import java.util.Collection;
//===================================================================================================================================

import java.util.List;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Koulutusrekisteri                   | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Tyontekija      |
 * | - Huolehtii Tyontekijat ja Koulutukset -luokkien   |                   | 
 * |   välisestä yhteistyöstä ja välittää näitä tietoja | - Tyontekijat     | 
 * |   pyydettäessä.                                    |                   |
 * |                                                    | - Koulutus        |
 * | - Lukee ja kirjoittaa Koulutusrekisterin tiedostoon|                   | 
 * |   pyytämällä apua avustajilta.                     | - Koulutukset     |
 * |                                                    |                   |
 * |                                                    | - Relaatiot       |
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author mitulint
 * @version 1.0, ennen 29.4.2021 / pidin huonosti kirjaa mitä tein ja ehkäpä siksi vaikea pysyä muutoksissa kärryillä
 * @version 1.1, 29.4.2021 / HT6 tulostukset tekstikenttiin ja valinta (työntekijä ja koulutus) toimimaan
 * @version 1.2, 30.4.2021 / HT6 testejä
 * @version 1.3, 10.5.2021 / Lisätty poista-aliohjelma
 *
 */
public class Koulutusrekisteri {
    
    private Tyontekijat tyontekijat = new Tyontekijat();
    private Koulutukset koulutukset = new Koulutukset();
    private Relaatiot   relaatiot   = new Relaatiot();
    
    
    /** 
     * Poistaa valitun relaation eli koulutuksen.
     * @param relaatio työntekijältä poistettava koulutus 
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKerho();
     *   koulutusrekisteri.annaRelaatiot(aku1).size() === 2;
     *   koulutusrekisteri.poista(pitsi11);
     *   koulutusrekisteri.annaRelaatiot(aku1).size() === 1;
     */
    public void poista(Relaatio relaatio) { 
        relaatiot.poista(relaatio.getRelaatioTunnus()); 
    } 

    
    
    /**
     * Palauttaa koulutusrekisterin työntekijöiden määrän
     * @return tyontekijoiden lkm
     */
    public int getTyontekijoita() {
        return this.tyontekijat.getLkm();
    }
    
    
    /**
     * Palauttaa koulutusrekisterin koulutusten määrän
     * @return koulutusten lkm
     */
    public int getKoulutuksia() {
        return this.koulutukset.getLkm();
    }
    
    
    /**
     * Palauttaa koulutusrekisterin relaatioiden määrän
     * @return relaatioiden lkm
     */
    public int getRelaatiot() {
        return this.relaatiot.getLkm();
    }
    

    /**
     * Poistaa työntekijöistä ja koulutuksista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako työntekijää poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {                // 7 vaiheessa ei saa olla SuppressWarnings
        return 0;
    }

    
    /**
     * Lisää uuden työntekijän
     * @param tyontekija lisättävä työntekijä
     * @throws SailoException jos lisääminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
     * Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija();
     * koulutusrekisteri.lisaa(aku1);
     * koulutusrekisteri.lisaa(aku2);
     * koulutusrekisteri.lisaa(aku1);
     * Collection<Tyontekija> loytyneet = koulutusrekisteri.etsiTyontekija("", -1);
     * Iterator<Tyontekija> it = loytyneet.iterator();
     * it.next() === aku1;
     * it.next() === aku2;
     * it.next() === aku1;
     * </pre>
     */
    public void lisaa(Tyontekija tyontekija) throws SailoException {
        tyontekijat.lisaa(tyontekija);
    }
    
    
    /**
     * Lisätään uusi koulutus
     * @param koulutus lisättävä koulutus
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Koulutus koulutus) throws SailoException {
        koulutukset.lisaa(koulutus);
    }
    
    
    /**
     * Lisätään uusi relaatio
     * @param relaatio lisättävä relaatio
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Relaatio relaatio) throws SailoException {
        relaatiot.lisaa(relaatio);
        
    }
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien tyontekijöiden viitteet 
     * @param hakuehto hakuehto  
     * @param t etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä työntekijöistä 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Tyontekija> etsiTyontekija(String hakuehto, int t) throws SailoException { 
        return tyontekijat.etsiTyontekija(hakuehto, t); 
    }
    
    
    /**
     * Palauttaa "taulukosta" hakuehtoon vastaavien koulutusten viitteet
     * @param hakuehto hakuehto
     * @param t etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä koulutuksista
     * @throws SailoException jos jotain menee mönkään
     */
    public Collection<Koulutus> etsiKoulutus(String hakuehto, int t) throws SailoException {
        return koulutukset.etsiKoulutus(hakuehto, t);
    }
    

    /**
     * Antaa koulutusrekisterin i:n työntekijän
     * @param i työntekijä jota haetaan
     * @return työntekijä työntekijän tunnuksella
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Tyontekija annaTyontekija(int i) throws IndexOutOfBoundsException {
        return tyontekijat.annaTyontekija(i);
    }

    
     /**
      * Antaa koulutusrekisterin i:n koulutuksen
      * @param i työntekijä jolle koulutuksia haetaan
      * @return tietorakenne jossa viiteet löydetteyihin koulutuksiin
      * @throws IndexOutOfBoundsException jos i väärin
      */
     public Koulutus annaKoulutus(int i) throws IndexOutOfBoundsException {
         return koulutukset.annaKoulutus(i);
     }
         
        
    /**
     * Haetaan tietyn tyntekijän relaatio
     * @param i minkä työntekijän relaatio
     * @return relaatio työntekijän tunnuksella
     * @throws IndexOutOfBoundsException jos i väärin
     * @example
     * <pre name="test">
     * #THROWS IndexOutOfBoundsException
     * #THROWS SailoException
     * #import java.util.*;
     * 
     * Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
     * 
     * Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); 
     * Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
     * Tyontekija aku3 = new Tyontekija(); aku3.vastaaAkuAnkka(); aku3.rekisteroi(); 
     * 
     * Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); 
     * Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi();
     * 
     * int id1 = aku1.getTyontekijaTunnus();
     * int id2 = aku2.getTyontekijaTunnus();
     * int id3 = vesi1.getKoulutusTunnus();
     * int id4 = vesi2.getKoulutusTunnus();
     *      
     * Relaatio rel1 = new Relaatio(id1, id3); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
     * Relaatio rel2 = new Relaatio(id2, id4); rel2.vastaaRelaatio(); rel2.rekisteroi();
     * 
     * koulutusrekisteri.lisaa(aku1); #THROWS SailoException
     * koulutusrekisteri.lisaa(aku2); #THROWS SailoException
     * koulutusrekisteri.lisaa(vesi1); #THROWS SailoException
     * koulutusrekisteri.lisaa(vesi2); #THROWS SailoException
     * koulutusrekisteri.lisaa(rel1); #THROWS SailoException
     * koulutusrekisteri.lisaa(rel2); #THROWS SailoException
     * 
     * List<Relaatio> loytyneet;
     * loytyneet = koulutusrekisteri.annaRelaatiot(1);
     * loytyneet.size() === 1;
     * loytyneet = koulutusrekisteri.annaRelaatiot(2);
     * loytyneet.size() === 2;
     * </pre>
     */
    public List<Relaatio> annaRelaatiot(int i) throws IndexOutOfBoundsException {
        return relaatiot.annaRelaatiot(i);
    }
         
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        
        tyontekijat.setTiedostonPerusNimi(hakemistonNimi + "tyontekijat");
        koulutukset.setTiedostonPerusNimi(hakemistonNimi + "koulutukset");
        relaatiot.setTiedostonPerusNimi(hakemistonNimi   + "relaatiot");
    }

         
      /**
       * Lukee koulutusrekisterin tiedot tiedostosta
       * @param nimi jota käyteään lukemisessa
       * @throws SailoException jos lukeminen epäonnistuu
       * 
       * @example
       * <pre name="test">
       * #THROWS SailoException
       * #import java.io.*;
       * #import java.util.*;
       * 
       * Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
       *
       * Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); 
       * Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); 
       *     
       * Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); 
       * Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi(); 
       * 
       * int id1 = aku1.getTyontekijaTunnus();
       * int id2 = aku2.getTyontekijaTunnus();
       * int id3 = vesi1.getKoulutusTunnus();
       * int id4 = vesi2.getKoulutusTunnus();
       *      
       * Relaatio rel1 = new Relaatio(id1, id3); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
       * Relaatio rel2 = new Relaatio(id2, id4); rel2.vastaaRelaatio(); rel2.rekisteroi(); 
       * 
       * String hakemisto = "testit";
       * File dir = new File(hakemisto);
       * File ftied = new File(hakemisto+"/tyontekijat.dat");
       * File fktied = new File(hakemisto+"/koulutukset.dat");
       * File frtied = new File(hakemisto+"/relaatiot.dat");
       * dir.mkdir();
       * ftied.delete();
       * fktied.delete();
       * frtied.delete();
       * koulutusrekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
       * 
       * koulutusrekisteri.lisaa(aku1);
       * koulutusrekisteri.lisaa(aku2);
       * koulutusrekisteri.lisaa(vesi1);
       * koulutusrekisteri.lisaa(vesi2);
       * koulutusrekisteri.lisaa(rel1);
       * koulutusrekisteri.lisaa(rel2);
       * koulutusrekisteri.tallenna();
       * 
       * koulutusrekisteri = new Koulutusrekisteri();
       * koulutusrekisteri.lueTiedostosta(hakemisto);
       * 
       * Collection<Tyontekija> kaikki = koulutusrekisteri.etsiTyontekija("", -1);
       * Iterator<Tyontekija> it = kaikki.iterator();
       * it.next() === aku1;
       * it.next() === aku2;
       * it.hasNext() === false;
       * 
       * Collection<Koulutus> koulutus = koulutusrekisteri.etsiKoulutus("", -1);
       * Iterator<Koulutus> ik = koulutus.iterator();
       * ik.next() === vesi1;
       * ik.next() === vesi2;
       * ik.hasNext() === false;
       * 
       * List<Relaatio> loytyneet = koulutusrekisteri.annaRelaatiot(0);
       * Iterator<Relaatio> ir = loytyneet.iterator();
       * ir.next() === rel1;
       * ir.next() === rel2;
       * ir.hasNext() === false;
       * loytyneet = koulutusrekisteri.annaRelaatiot(1);
       * ir = loytyneet.iterator();
       * ir.next() === rel1;
       * ir.next() === rel2;
       * ir.hasNext() === false;
       * koulutusrekisteri.lisaa(aku1);
       * koulutusrekisteri.lisaa(vesi1);
       * koulutusrekisteri.lisaa(rel1);
       * koulutusrekisteri.tallenna();
       * ftied.delete() === true;
       * fktied.delete() === true;
       * frtied.delete() === true;
       * dir.delete() === true;
       * </pre>
       */
      public void lueTiedostosta(String nimi) throws SailoException {
          tyontekijat = new Tyontekijat();
          koulutukset = new Koulutukset();
          relaatiot = new Relaatiot();

          setTiedosto(nimi);
          tyontekijat.lueTiedostosta();
          koulutukset.lueTiedostosta();
          relaatiot.lueTiedostosta();

      }
  
  
       /**
        * Tallentaa koulutusrekisterin tiedot tiedostoon.
        * @throws SailoException jos tallettamisessa ongelmia
        */
       public void tallenna() throws SailoException {
           String virhe = "";
           try {
               tyontekijat.tallenna();
           } catch ( SailoException ex ) {
               virhe = ex.getMessage();
           }

           try {
               koulutukset.tallenna();
           } catch ( SailoException ex ) {
               virhe += ex.getMessage();
           }
           
           try {
               relaatiot.tallenna();
           } catch ( SailoException ex ) {
               virhe = ex.getMessage();
           }
           
           if ( !"".equals(virhe) ) throw new SailoException(virhe);
       }
    
       
    /**
     * Testiohjelma koulutusrekisteristä
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
        
        try {
            
             Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); koulutusrekisteri.lisaa(aku1);
             Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); koulutusrekisteri.lisaa(aku2);
             
             Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); koulutusrekisteri.lisaa(vesi1);
             Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi(); koulutusrekisteri.lisaa(vesi2);
             
             int id1 = aku1.getTyontekijaTunnus();
             int id2 = aku2.getTyontekijaTunnus();
             int id3 = vesi1.getKoulutusTunnus();
             int id4 = vesi2.getKoulutusTunnus();
             
             Relaatio rel1 = new Relaatio(id1, id3); rel1.vastaaRelaatio(); rel1.rekisteroi(); koulutusrekisteri.lisaa(rel1);
             Relaatio rel2 = new Relaatio(id2, id4); rel2.vastaaRelaatio(); rel2.rekisteroi(); koulutusrekisteri.lisaa(rel2);
 
             System.out.println("============= Koulutusrekisterin testi =================");         
             
             for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
                 Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
                 System.out.println("Työntekijä paikassa: " + i);
                 tyontekija.tulosta(System.out);
                 
                 for (int n = 0; n < koulutusrekisteri.getKoulutuksia(); n++) {
                     Koulutus koulutus = koulutusrekisteri.annaKoulutus(n);
                     System.out.println("Koulutus paikassa: " + n);
                     koulutus.tulosta(System.out);                 
                     
                 }
             }
         } catch (SailoException ex) {
             System.out.println(ex.getMessage());
             }
        }
    }

