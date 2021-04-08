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
      * Antaa koulutusrekisterin i:n koulutuksen
      * @param i työntekijä jolle koulutuksia haetaan
      * @return tietorakenne jossa viiteet löydetteyihin koulutuksiin
      * @throws IndexOutOfBoundsException jos i väärin
      */
     public Koulutus annaKoulutus(int i) throws IndexOutOfBoundsException {
         return koulutukset.annaKoulutus(i);
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
          relaatiot.lueTiedostosta(nimi);
      }
  
  
       /**
        * Tallettaa koulutusrekisterin tiedot tiedostoon
        * @throws SailoException jos tallettamisessa ongelmia
        */
       public void talleta() throws SailoException {
           tyontekijat.talleta();
           koulutukset.talleta();
           relaatiot.talleta();
           // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
       }
    
       
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
        
        try {
             // kerho.lueTiedostosta("kelmit");
 
             Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); koulutusrekisteri.lisaa(aku1);
             Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); koulutusrekisteri.lisaa(aku2);
             
             Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); koulutusrekisteri.lisaa(vesi1);
             Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi(); koulutusrekisteri.lisaa(vesi2);

             Relaatio rel1 = new Relaatio(); rel1.vastaaRelaatio(); koulutusrekisteri.lisaa(rel1);
             Relaatio rel2 = new Relaatio(); rel2.vastaaRelaatio(); koulutusrekisteri.lisaa(rel2);
 
             // lisää relaatio-oliot (työntekijäid + koulutusid) --> muut tulee täytettynä
             System.out.println("============= Koulutusrekisterin testi =================");

             for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
                 Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
                 System.out.println("Työntekijä paikassa: " + i);
                 tyontekija.tulosta(System.out);
             }
             
             for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
                 Koulutus koulutus = koulutusrekisteri.annaKoulutus(i);
                 System.out.println("Koulutus paikassa: " + i);
                 koulutus.tulosta(System.out);
             }
 
         } catch (SailoException ex) {
             System.out.println(ex.getMessage());
         }
    }
    
}