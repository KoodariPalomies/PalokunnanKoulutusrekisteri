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
 * @version 21.4.2021
 *
 */
public class Koulutusrekisteri {
    
    private Tyontekijat tyontekijat = new Tyontekijat();
    private Koulutukset koulutukset = new Koulutukset();
    private Relaatiot   relaatiot   = new Relaatiot();
    
    
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
     */
    //public List<Relaatio> annaRelaatiot(int i) throws IndexOutOfBoundsException {   //Tyontekija tyontekija
         //return relaatiot.annaRelaatiot(tyontekija.getTyontekijaTunnus());
    public Relaatio annaRelaatiot(int i) throws IndexOutOfBoundsException {
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
        * Tallettaa koulutusrekisterin tiedot tiedostoon
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
               relaatiot.talleta();
           } catch ( SailoException ex ) {
               virhe = ex.getMessage();
           }
           
           if ( !"".equals(virhe) ) throw new SailoException(virhe);
       }
    
       
    /**
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
                     
                     for (int r = 0; r < koulutusrekisteri.getRelaatiot(); r++) {
                         Relaatio relaatio = koulutusrekisteri.annaRelaatiot(r);
                         System.out.println("Relaatio paikassa: " + r);
                         relaatio.tulosta(System.out);
                     }
                 }
             }
         } catch (SailoException ex) {
             System.out.println(ex.getMessage());
             }
        }
    }

