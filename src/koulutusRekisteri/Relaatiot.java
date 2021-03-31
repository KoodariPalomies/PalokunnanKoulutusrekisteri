/**
 * 
 */
package koulutusRekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.*;

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
 * @version 22.3.2021
 *
 */
public class Relaatiot implements Iterable<Koulutus> {
    
  private String                      tiedostonNimi = "";
    
  /** 
   * Taulukko harrastuksista 
   */
  private final Collection<Koulutus> alkiot        = new ArrayList<Koulutus>();
  
  
  /**
   * Koulutusten alustaminen
   */
  public Relaatiot() {
      //alkiot = new Tyontekija[MAX_TYONTEKIJOITA]; Attribuuttien oma alustus riittää
  }

  
    /**
     * Lisää uuden koulutuksen tietorakenteeseen.  Ottaa koulutuksen omistukseensa.
     * @param koul lisättävä koulutus.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Koulutus koul) {
        alkiot.add(koul);
    }
  
        
  /**
   * Lukee työntekijät tiedostosta. 
   * TODO Kesken.
   * @param hakemisto tiedoston hakemisto
   * @throws SailoException jos lukeminen epäonnistuu
   */
  public void lueTiedostosta(String hakemisto) throws SailoException {
      tiedostonNimi = hakemisto + ".har";
      throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
  }
  
              
  /**
   * Palauttaa koulutusrekisterin koulutusten lukumäärän
   * @return koulutusten lukumäärä
   */
  public int getLkm() {
      return alkiot.size();
  }
  
  
        /**
         * Tallentaa työntekijät tiedostoon. 
         * TODO Kesken.
         * @throws SailoException jos talletus epäonnistuu
         */
        public void talleta() throws SailoException {
            throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
        }
  
        
  /**
   * Iteraattori kaikkien koulutusten läpikäymiseen
   * @return koulutusiteraattori
   *
   * @example
   * <pre name="test">
   * #PACKAGEIMPORT
   * #import java.util.*;
   *
   *  Koulutukset harrasteet = new Koulutukset();
   *  Koulutus pitsi21 = new Koulutus(2); harrasteet.lisaa(pitsi21);
   *  Koulutus pitsi11 = new Koulutus(1); harrasteet.lisaa(pitsi11);
   *  Koulutus pitsi22 = new Koulutus(2); harrasteet.lisaa(pitsi22);
   *  Koulutus pitsi12 = new Koulutus(1); harrasteet.lisaa(pitsi12);
   *  Koulutus pitsi23 = new Koulutus(2); harrasteet.lisaa(pitsi23);
   *
   *  Iterator<Koulutus> i2=harrasteet.iterator();
   *  i2.next() === pitsi21;
   *  i2.next() === pitsi11;
   *  i2.next() === pitsi22;
   *  i2.next() === pitsi12;
   *  i2.next() === pitsi23;
   *  i2.next() === pitsi12;  #THROWS NoSuchElementException 
   * 
   *  int n = 0;
   *  int jnrot[] = {2,1,2,1,2};
   * 
   *  for ( Koulutus har:harrasteet ) {
   *    har.getTyontekijaTunnus() === jnrot[n]; n++; 
   *  }
   * 
   *  n === 5;
   * 
  * </pre>
  */
 @Override
 public Iterator<Koulutus> iterator() {
     return alkiot.iterator();
 }
             
             
   /**
   * Haetaan kaikki jäsen harrastukset
   * @param tunnusnro jäsenen tunnusnumero jolle harrastuksia haetaan
   * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
   * @example
   * <pre name="test">
   * #import java.util.*;
   *
   *  Koulutukset koulutukset = new Koulutukset();
   *  Koulutus pitsi21 = new Koulutus(2); koulutukset.lisaa(pitsi21);
   *  Koulutus pitsi11 = new Koulutus(1); koulutukset.lisaa(pitsi11);
   *  Koulutus pitsi22 = new Koulutus(2); koulutukset.lisaa(pitsi22);
   *  Koulutus pitsi12 = new Koulutus(1); koulutukset.lisaa(pitsi12);
   *  Koulutus pitsi23 = new Koulutus(2); koulutukset.lisaa(pitsi23);
   *  Koulutus pitsi51 = new Koulutus(5); koulutukset.lisaa(pitsi51);
   * 
   *  List<Harrastus> loytyneet;
   *  loytyneet = koulutukset.annaKoulutukset(3);
   *  loytyneet.size() === 0;
   *  loytyneet = koulutukset.annaKoulutukset(1);
   *  loytyneet.size() === 2;
   *  loytyneet.get(0) == pitsi11 === true;
   *  loytyneet.get(1) == pitsi12 === true;
   *  loytyneet = koulutukset.annaKoulutukset(5);
   *  loytyneet.size() === 1;
   *  loytyneet.get(0) == pitsi51 === true;
   * </pre>
   */
  public List<Koulutus> annaKoulutukset(int tunnusnro) {
      List<Koulutus> loydetyt = new ArrayList<Koulutus>();
      for (Koulutus koul : alkiot)
          if (koul.getKoulutusTunnus() == tunnusnro) loydetyt.add(koul);
      return loydetyt;
  }
  
  
   /**
    * Testiohjelma harrastuksille
    * @param args ei käytössä
    */
   public static void main(String[] args) {
       Koulutukset koulutukset = new Koulutukset();             // pelkästään relaatio-olioita
       Koulutus pitsi1 = new Koulutus();
       pitsi1.vastaaVesisukeltaja(2);
       Koulutus pitsi2 = new Koulutus();
       pitsi2.vastaaVesisukeltaja(1);
       Koulutus pitsi3 = new Koulutus();
       pitsi3.vastaaVesisukeltaja(2);
       Koulutus pitsi4 = new Koulutus();
       pitsi4.vastaaVesisukeltaja(2);

       koulutukset.lisaa(pitsi1);
       koulutukset.lisaa(pitsi2);
       koulutukset.lisaa(pitsi3);
       koulutukset.lisaa(pitsi2);
       koulutukset.lisaa(pitsi4);

       System.out.println("============= Harrastukset testi =================");

       List<Koulutus> koulutukset2 = koulutukset.annaKoulutukset(2);

       for (Koulutus koul : koulutukset2) {
           System.out.print(koul.getKoulutusTunnus() + " ");
           koul.tulosta(System.out);
       }

  }
   
}
