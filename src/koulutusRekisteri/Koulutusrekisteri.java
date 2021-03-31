package koulutusRekisteri;

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
 * @version 3.3.2021
 *
 */
public class Koulutusrekisteri {
    
    private final Tyontekijat tyontekijat = new Tyontekijat();
    private final Koulutukset koulutukset = new Koulutukset();
    private final Relaatiot   relaatiot   = new Relaatiot();
    
    
    /**
     * Palauttaa koulutusrekisterin työntekijämäärän
     * @return työntekijöiden lkm
     */
    public int getTyontekijoita() {
        return this.tyontekijat.getLkm();
    }
    
    
    /**
     * Palauttaa koulutusrekisterin relaatioiden määrän
     * @return relaatioiden lkm
     */
    public int getRelaatioTunnus() {
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
       * aku1.rekisteroi(); aku2.rekisteroi();
       * koulutusrekisteri.getTyontekijoita() === 0;
       * koulutusrekisteri.lisaa(aku1); koulutusrekisteri.getTyontekijoita() === 1;
       * koulutusrekisteri.lisaa(aku2); koulutusrekisteri.getTyontekijoita() === 2;
       * koulutusrekisteri.lisaa(aku1); koulutusrekisteri.getTyontekijoita() === 3;
       * koulutusrekisteri.getTyontekijoita() === 3;
       * koulutusrekisteri.annaTyontekija(0) === aku1;
       * koulutusrekisteri.annaTyontekija(1) === aku2;
       * koulutusrekisteri.annaTyontekija(2) === aku1;
       * koulutusrekisteri.annaTyontekija(3) === aku1; #THROWS IndexOutOfBoundsException
       * koulutusrekisteri.lisaa(aku1); koulutusrekisteri.getTyontekijoita() === 4;
       * koulutusrekisteri.lisaa(aku1); koulutusrekisteri.getTyontekijoita() === 5;
       * koulutusrekisteri.lisaa(aku1);            #THROWS SailoException
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
     * Antaa koulutusrekisterin i:n työntekijän
     * @param i monesko työntekijä (alkaa 0:sta)
     * @return työntekijä paikasta i
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Tyontekija annaTyontekija(int i) throws IndexOutOfBoundsException {
        return tyontekijat.annaTyontekija(i);
    }
    

         /**
          * Haetaan kaikki työntekijän koulutukset
          * @param tyontekija työntekijä jolle koulutuksia haetaan
          * @return tietorakenne jossa viiteet löydetteyihin koulutuksiin
          * @example
          * <pre name="test">
          * #import java.util.*;
          *
          *  Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
          *  Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(), aku3 = new Tyontekija();
          *  aku1.lisaaTyontekija(); aku2.lisaaTyontekija(); aku3.lisaaTyontekija();
          *  int id1 = aku1.getTyontekijaTunnus();
          *  int id2 = aku2.getTyontekijaTunnus();
          *  Koulutus pitsi11 = new Koulutus(id1); koulutusrekisteri.lisaa(pitsi11);
          *  Koulutus pitsi12 = new Koulutus(id1); koulutusrekisteri.lisaa(pitsi12);
          *  Koulutus pitsi21 = new Koulutus(id2); koulutusrekisteri.lisaa(pitsi21);
          *  Koulutus pitsi22 = new Koulutus(id2); koulutusrekisteri.lisaa(pitsi22);
          *  Koulutus pitsi23 = new Koulutus(id2); koulutusrekisteri.lisaa(pitsi23);
          * 
          *  List<Koulutus> loytyneet;
          *  loytyneet = koulutusrekisteri.annaKoulutukset(aku3);
          *  loytyneet.size() === 0;
          *  loytyneet = koulutusrekisteri.annaKoulutukset(aku1);
          *  loytyneet.size() === 2;
          *  loytyneet.get(0) == pitsi11 === true;
          *  loytyneet.get(1) == pitsi12 === true;
          *  loytyneet = koulutusrekisteri.annaKoulutukset(aku2);
          *  loytyneet.size() === 3;
          *  loytyneet.get(0) == pitsi21 === true;
          * </pre>
          */
         public List<Koulutus> annaKoulutukset(Tyontekija tyontekija) {
             return koulutukset.annaKoulutukset(tyontekija.getTyontekijaTunnus());
         }
         
        
    /**
     * @param i monesko relaatio
     * @return relaatio paikassa i
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Relaatio annaRelaatio(int i) throws IndexOutOfBoundsException {
         return relaatiot.annaRelaatio(i);
     }
         
         
      /**
       * Lukee koulutusrekisterin tiedot tiedostosta
       * @param nimi jota käyteään lukemisessa
       * @throws SailoException jos lukeminen epäonnistuu
       */
      public void lueTiedostosta(String nimi) throws SailoException {
          tyontekijat.lueTiedostosta(nimi);
          koulutukset.lueTiedostosta(nimi);
      }
  
  
       /**
        * Tallettaa koulutusrekisterin tiedot tiedostoon
        * @throws SailoException jos tallettamisessa ongelmia
        */
       public void talleta() throws SailoException {
           tyontekijat.talleta();
           koulutukset.talleta();
           // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
       }
    
       
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
        
        try {
             // kerho.lueTiedostosta("kelmit");
 
             Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija();
             aku1.lisaaTyontekija();
             aku1.vastaaAkuAnkka();
             aku2.lisaaTyontekija();
             aku2.vastaaAkuAnkka();
 
             koulutusrekisteri.lisaa(aku1);
             koulutusrekisteri.lisaa(aku2);
             int id1 = aku1.getTyontekijaTunnus();
             int id2 = aku2.getTyontekijaTunnus();
             Koulutus pitsi11 = new Koulutus(id1); pitsi11.vastaaVesisukeltaja(id1); koulutusrekisteri.lisaa(pitsi11);
             Koulutus pitsi12 = new Koulutus(id1); pitsi12.vastaaVesisukeltaja(id1); koulutusrekisteri.lisaa(pitsi12);
             Koulutus pitsi21 = new Koulutus(id2); pitsi21.vastaaVesisukeltaja(id2); koulutusrekisteri.lisaa(pitsi21);
             Koulutus pitsi22 = new Koulutus(id2); pitsi22.vastaaVesisukeltaja(id2); koulutusrekisteri.lisaa(pitsi22);
             Koulutus pitsi23 = new Koulutus(id2); pitsi23.vastaaVesisukeltaja(id2); koulutusrekisteri.lisaa(pitsi23);
 
             // lisää relaatio-oliot (työntekijäid + koulutusid) --> muut tulee täytettynä
             System.out.println("============= Kerhon testi =================");

             for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
                 Tyontekija jasen = koulutusrekisteri.annaTyontekija(i);
                 System.out.println("Jäsen paikassa: " + i);
                 jasen.tulosta(System.out);
                 List<Koulutus> loytyneet = koulutusrekisteri.annaKoulutukset(jasen);
                 for (Koulutus harrastus : loytyneet)
                     harrastus.tulosta(System.out);
             }
 
         } catch (SailoException ex) {
             System.out.println(ex.getMessage());
         }
    }
    
}