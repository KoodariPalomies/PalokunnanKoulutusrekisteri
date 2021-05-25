package koulutusRekisteri;

import java.io.File;
import java.util.Collection;
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
 * 
 * @author mitulint, tuomas.mikko.lintula@gmail.com
 * @version 1.0, ennen 29.4.2021 / pidin huonosti kirjaa mitä tein ja ehkäpä siksi vaikea pysyä muutoksissa kärryillä
 * @version 1.1, 29.4.2021  / HT6 tulostukset tekstikenttiin ja valinta (työntekijä ja koulutus) toimimaan
 * @version 1.2, 30.4.2021  / HT6 testejä
 * @version 1.3, 10.5.2021  / Lisätty poista()
 * @version 1.4, 13.5.2021  / Lisätty poistaTyontekijanKoulutus() + poista() muokkaus
 * @version 1.5, 14.5.2021  / Lisätty korvaaTaiLisaa() -aliohjelma, joka tarvitaan muokkaamista varten
 * @version 1.6, 19.5.2021  / annaKoulutus() muutettu listaksi
 * @version 1.7, 20.5.2021  / Poistettu turha poista() -aliohjelma
 * @version 1.8, 25.5.2021  / Lisätty Main() kaksi String id:tä, koska tarvitaan lisää työntekijälle koulutus dialogin alustamista varten. + testeihin samat lisäykset
 * HUOM: ohjelmassa ei vielä toimi koulutuksen ja työntekijän poistaminen
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 *  private Koulutusrekisteri koulutusrekisteri;
 *  private Tyontekija aku1;
 *  private Tyontekija aku2;
 *  private int jid1;
 *  private int jid2;
 *  private Koulutus pitsi21;
 *  private Koulutus pitsi11;
 *  private Koulutus pitsi22; 
 *  private Koulutus pitsi12; 
 *  private Koulutus pitsi23;
 *  private Relaatio rel1;
 *  
 *  public void alusta() {
 *    koulutusrekisteri = new Koulutusrekisteri();
 *    aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
 *    aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
 *    jid1 = aku1.getTyontekijaTunnus();
 *    jid2 = aku2.getTyontekijaTunnus();
 *    pitsi21 = new Koulutus(jid2); pitsi21.vastaaVesisukeltaja();
 *    pitsi11 = new Koulutus(jid1); pitsi11.vastaaVesisukeltaja();
 *    pitsi22 = new Koulutus(jid2); pitsi22.vastaaVesisukeltaja(); 
 *    pitsi12 = new Koulutus(jid1); pitsi12.vastaaVesisukeltaja(); 
 *    pitsi23 = new Koulutus(jid2); pitsi23.vastaaVesisukeltaja();
 *    try {
 *    koulutusrekisteri.lisaa(aku1);
 *    koulutusrekisteri.lisaa(aku2);
 *    koulutusrekisteri.lisaa(pitsi21);
 *    koulutusrekisteri.lisaa(pitsi11);
 *    koulutusrekisteri.lisaa(pitsi22);
 *    koulutusrekisteri.lisaa(pitsi12);
 *    koulutusrekisteri.lisaa(pitsi23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Koulutusrekisteri {
    
    private Tyontekijat tyontekijat = new Tyontekijat();
    private Koulutukset koulutukset = new Koulutukset();
    private Relaatiot   relaatiot   = new Relaatiot();
    
    
    /** 
     * Poistaa valitun relaation eli työntekijän koulutuksen.
     * @param relaatio työntekijältä poistettava koulutus 
     * @return palauttaa 1 jos onnistui tai 0 jos epäonnistui
     * <pre name="test">
     * #THROWS Exception
     *   alusta();
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
     * String id5 = vesi1.getKoulutus();
     * String id6 = vesi2.getKoulutus();
     *      
     * Relaatio rel1 = new Relaatio(id1, id3, id5); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
     * Relaatio rel2 = new Relaatio(id2, id4, id6); rel2.vastaaRelaatio(); rel2.rekisteroi();
     *   
     *   koulutusrekisteri.annaRelaatiot(1).size() === 2;
     *   koulutusrekisteri.poistaTyontekijanKoulutus(rel1);
     *   koulutusrekisteri.annaRelaatiot(2).size() === 1;
     * </pre>
     */
    public int poistaTyontekijanKoulutus(Relaatio relaatio) {
        return relaatiot.poista(relaatio.getRelaatioTunnus());
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
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   alusta();
     *   Tyontekijat tyontekijat = new Tyontekijat();
     *   Tyontekija jasen3 = new Tyontekija(); jasen3.rekisteroi();
     *   tyontekijat.korvaaTaiLisaa(jasen3);
     *   koulutusrekisteri.lisaa(jasen3);
     *   Collection<Tyontekija> loytyneet = koulutusrekisteri.etsiTyontekija("*Susi*",1);
     *   loytyneet.size() === 1;
     *   Iterator<Tyontekija> it = loytyneet.iterator();
     *   it.next() == jasen3 === true; 
     * </pre>
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
     * Palauttaa "taulukosta" hakuehtoon vastaavien relaatioiden viitteet
     * @param hakuehto hakuehto
     * @param t etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä relaatioista
     * @throws SailoException jos jotain menee mönkään
     */
    public Collection<Relaatio> etsiRelaatio(String hakuehto, int t) throws SailoException {
        return relaatiot.etsiRelaatio(hakuehto, t);
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
      * @throws SailoException jos i väärin
      */
    public List<Koulutus> annaKoulutus(int i) throws SailoException {
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
     * String id5 = vesi1.getKoulutus();
     * String id6 = vesi2.getKoulutus();
     *      
     * Relaatio rel1 = new Relaatio(id1, id3, id5); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
     * Relaatio rel2 = new Relaatio(id2, id4, id6); rel2.vastaaRelaatio(); rel2.rekisteroi();
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
     * @param nimi uusi tiedostonnimi
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
       * @param nimi avattavan tiedostom nimi
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
       * String id5 = vesi1.getKoulutus();
       * String id6 = vesi2.getKoulutus();
       *      
       * Relaatio rel1 = new Relaatio(id1, id3, id5); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
       * Relaatio rel2 = new Relaatio(id2, id4, id6); rel2.vastaaRelaatio(); rel2.rekisteroi(); 
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
               virhe = ex.getMessage();
           }
           
           try {
               relaatiot.tallenna();
           } catch ( SailoException ex ) {
               virhe = ex.getMessage();
           }
           
           if ( !"".equals(virhe) ) throw new SailoException(virhe);
       }
    
       
   /** 
    * Korvaa työntekijän tietorakenteessa.  Ottaa työntekijän omistukseensa. 
    * Etsitään samalla tunnusnumerolla oleva työntekijä.  Jos ei löydy, niin lisätään uutena työntekijänä. 
    * @param tyontekija lisättävän työntekijän viite.  Huom tietorakenne muuttuu omistajaksi 
    * @throws SailoException jos tietorakenne on jo täynnä 
    * @example
    * <pre name="test">
    * #THROWS SailoException  
    *  alusta();
    *  koulutusrekisteri.etsiTyontekija("*",0).size() === 2;
    *  koulutusrekisteri.korvaaTaiLisaa(aku1);
    *  koulutusrekisteri.etsiTyontekija("*",0).size() === 2;
    * </pre>
    */ 
   public void korvaaTaiLisaa(Tyontekija tyontekija) throws SailoException { 
       tyontekijat.korvaaTaiLisaa(tyontekija); 
   }
   
   
   /** 
    * Korvaa koulutuksen tietorakenteessa.  Ottaa koulutuksen omistukseensa. 
    * Etsitään samalla tunnusnumerolla oleva koulutus.  Jos ei löydy, niin lisätään uutena koulutuksena. 
    * @param koulutus lisättävän koulutuksen viite.  Huom tietorakenne muuttuu omistajaksi 
    * @throws SailoException jos tietorakenne on jo täynnä 
    * @example
    * <pre name="test">
    * #THROWS SailoException  
    *  alusta();
    *  koulutusrekisteri.etsiKoulutus("*",0).size() === 2;
    *  koulutusrekisteri.korvaaTaiLisaa(pitsi21);
    *  koulutusrekisteri.etsiKoulutus("*",0).size() === 2;
    * </pre>
    */ 
   public void korvaaTaiLisaa(Koulutus koulutus) throws SailoException { 
       koulutukset.korvaaTaiLisaa(koulutus); 
   }

   
   /** 
    * Korvaa relaation tietorakenteessa.  Ottaa relaation omistukseensa. 
    * Etsitään samalla tunnusnumerolla oleva relaatio.  Jos ei löydy, niin lisätään uutena relaationa. 
    * @param relaatio lisättävän relaation viite.  Huom tietorakenne muuttuu omistajaksi 
    * @throws SailoException jos tietorakenne on jo täynnä 
    * @example
    * <pre name="test">
    * #THROWS SailoException  
    *  alusta();
    *  koulutusrekisteri.etsiRelaatio("*",0).size() === 2;
    *  koulutusrekisteri.korvaaTaiLisaa(rel1);
    *  koulutusrekisteri.etsiRelaatio("*",0).size() === 2;
    * </pre>
    */ 
   public void korvaaTaiLisaa(Relaatio relaatio) throws SailoException { 
       relaatiot.korvaaTaiLisaa(relaatio); 
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
             String id5 = vesi1.getKoulutus();
             String id6 = vesi2.getKoulutus();
             
             Relaatio rel1 = new Relaatio(id1, id3, id5); rel1.vastaaRelaatio(); rel1.rekisteroi(); koulutusrekisteri.lisaa(rel1);
             Relaatio rel2 = new Relaatio(id2, id4, id6); rel2.vastaaRelaatio(); rel2.rekisteroi(); koulutusrekisteri.lisaa(rel2);
 
             System.out.println("============= Koulutusrekisterin testi =================");         
             
             for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
                 Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
                 System.out.println("Työntekijä paikassa: " + i);
                 tyontekija.tulosta(System.out);
                 
                 List<Koulutus> loytyneet = koulutusrekisteri.annaKoulutus(i);
                 for (Koulutus koul : loytyneet)
                     koul.tulosta(System.out);
                 }
             
         } catch (SailoException ex) {
             System.out.println(ex.getMessage());
         }
    }
}
           