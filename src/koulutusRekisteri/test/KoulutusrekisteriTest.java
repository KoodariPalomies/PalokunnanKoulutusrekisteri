package koulutusRekisteri.test;
// Generated by ComTest BEGIN
import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;
import koulutusRekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.20 16:33:32 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class KoulutusrekisteriTest {

  // Generated by ComTest BEGIN  // Koulutusrekisteri: 37
   private Koulutusrekisteri koulutusrekisteri; 
   private Tyontekija aku1; 
   private Tyontekija aku2; 
   private int jid1; 
   private int jid2; 
   private Koulutus pitsi21; 
   private Koulutus pitsi11; 
   private Koulutus pitsi22; 
   private Koulutus pitsi12; 
   private Koulutus pitsi23; 

   public void alusta() {
     koulutusrekisteri = new Koulutusrekisteri(); 
     aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); 
     aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); 
     jid1 = aku1.getTyontekijaTunnus(); 
     jid2 = aku2.getTyontekijaTunnus(); 
     pitsi21 = new Koulutus(jid2); pitsi21.vastaaVesisukeltaja(); 
     pitsi11 = new Koulutus(jid1); pitsi11.vastaaVesisukeltaja(); 
     pitsi22 = new Koulutus(jid2); pitsi22.vastaaVesisukeltaja(); 
     pitsi12 = new Koulutus(jid1); pitsi12.vastaaVesisukeltaja(); 
     pitsi23 = new Koulutus(jid2); pitsi23.vastaaVesisukeltaja(); 
     try {
     koulutusrekisteri.lisaa(aku1); 
     koulutusrekisteri.lisaa(aku2); 
     koulutusrekisteri.lisaa(pitsi21); 
     koulutusrekisteri.lisaa(pitsi11); 
     koulutusrekisteri.lisaa(pitsi22); 
     koulutusrekisteri.lisaa(pitsi12); 
     koulutusrekisteri.lisaa(pitsi23); 
     } catch ( Exception e) {
        System.err.println(e.getMessage()); 
     }
   }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaTyontekijanKoulutus85 
   * @throws Exception when error
   */
  @Test
  public void testPoistaTyontekijanKoulutus85() throws Exception {    // Koulutusrekisteri: 85
    alusta(); 
    Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri(); 
    Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); 
    Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); 
    Tyontekija aku3 = new Tyontekija(); aku3.vastaaAkuAnkka(); aku3.rekisteroi(); 
    Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); 
    Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi(); 
    int id1 = aku1.getTyontekijaTunnus(); 
    int id2 = aku2.getTyontekijaTunnus(); 
    int id3 = vesi1.getKoulutusTunnus(); 
    int id4 = vesi2.getKoulutusTunnus(); 
    Relaatio rel1 = new Relaatio(id1, id3); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
    Relaatio rel2 = new Relaatio(id2, id4); rel2.vastaaRelaatio(); rel2.rekisteroi(); 
    assertEquals("From: Koulutusrekisteri line: 106", 2, koulutusrekisteri.annaRelaatiot(1).size()); 
    koulutusrekisteri.poistaTyontekijanKoulutus(rel1); 
    assertEquals("From: Koulutusrekisteri line: 108", 1, koulutusrekisteri.annaRelaatiot(2).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa148 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa148() throws SailoException {    // Koulutusrekisteri: 148
    Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri(); 
    Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(); 
    koulutusrekisteri.lisaa(aku1); 
    koulutusrekisteri.lisaa(aku2); 
    koulutusrekisteri.lisaa(aku1); 
    Collection<Tyontekija> loytyneet = koulutusrekisteri.etsiTyontekija("", -1); 
    Iterator<Tyontekija> it = loytyneet.iterator(); 
    assertEquals("From: Koulutusrekisteri line: 157", aku1, it.next()); 
    assertEquals("From: Koulutusrekisteri line: 158", aku2, it.next()); 
    assertEquals("From: Koulutusrekisteri line: 159", aku1, it.next()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiTyontekija195 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testEtsiTyontekija195() throws CloneNotSupportedException, SailoException {    // Koulutusrekisteri: 195
    alusta(); 
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija jasen3 = new Tyontekija(); jasen3.rekisteroi(); 
    tyontekijat.korvaaTaiLisaa(jasen3); 
    koulutusrekisteri.lisaa(jasen3); 
    Collection<Tyontekija> loytyneet = koulutusrekisteri.etsiTyontekija("*Susi*",1); 
    assertEquals("From: Koulutusrekisteri line: 203", 1, loytyneet.size()); 
    Iterator<Tyontekija> it = loytyneet.iterator(); 
    assertEquals("From: Koulutusrekisteri line: 205", true, it.next() == jasen3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaRelaatiot253 
   * @throws IndexOutOfBoundsException when error
   */
  @Test
  public void testAnnaRelaatiot253() throws IndexOutOfBoundsException {    // Koulutusrekisteri: 253
    Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri(); 
    Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); 
    Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); 
    Tyontekija aku3 = new Tyontekija(); aku3.vastaaAkuAnkka(); aku3.rekisteroi(); 
    Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); 
    Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi(); 
    int id1 = aku1.getTyontekijaTunnus(); 
    int id2 = aku2.getTyontekijaTunnus(); 
    int id3 = vesi1.getKoulutusTunnus(); 
    int id4 = vesi2.getKoulutusTunnus(); 
    Relaatio rel1 = new Relaatio(id1, id3); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
    Relaatio rel2 = new Relaatio(id2, id4); rel2.vastaaRelaatio(); rel2.rekisteroi(); 
    try {
    koulutusrekisteri.lisaa(aku1); 
    fail("Koulutusrekisteri: 275 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    koulutusrekisteri.lisaa(aku2); 
    fail("Koulutusrekisteri: 276 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    koulutusrekisteri.lisaa(vesi1); 
    fail("Koulutusrekisteri: 277 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    koulutusrekisteri.lisaa(vesi2); 
    fail("Koulutusrekisteri: 278 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    koulutusrekisteri.lisaa(rel1); 
    fail("Koulutusrekisteri: 279 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    koulutusrekisteri.lisaa(rel2); 
    fail("Koulutusrekisteri: 280 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    List<Relaatio> loytyneet; 
    loytyneet = koulutusrekisteri.annaRelaatiot(1); 
    assertEquals("From: Koulutusrekisteri line: 284", 1, loytyneet.size()); 
    loytyneet = koulutusrekisteri.annaRelaatiot(2); 
    assertEquals("From: Koulutusrekisteri line: 286", 2, loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta316 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta316() throws SailoException {    // Koulutusrekisteri: 316
    Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri(); 
    Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); aku1.rekisteroi(); 
    Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); aku2.rekisteroi(); 
    Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); vesi1.rekisteroi(); 
    Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); vesi2.rekisteroi(); 
    int id1 = aku1.getTyontekijaTunnus(); 
    int id2 = aku2.getTyontekijaTunnus(); 
    int id3 = vesi1.getKoulutusTunnus(); 
    int id4 = vesi2.getKoulutusTunnus(); 
    Relaatio rel1 = new Relaatio(id1, id3); rel1.vastaaRelaatio(); rel1.rekisteroi(); 
    Relaatio rel2 = new Relaatio(id2, id4); rel2.vastaaRelaatio(); rel2.rekisteroi(); 
    String hakemisto = "testit"; 
    File dir = new File(hakemisto); 
    File ftied = new File(hakemisto+"/tyontekijat.dat"); 
    File fktied = new File(hakemisto+"/koulutukset.dat"); 
    File frtied = new File(hakemisto+"/relaatiot.dat"); 
    dir.mkdir(); 
    ftied.delete(); 
    fktied.delete(); 
    frtied.delete(); 
    try {
    koulutusrekisteri.lueTiedostosta(hakemisto); 
    fail("Koulutusrekisteri: 346 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    koulutusrekisteri.lisaa(aku1); 
    koulutusrekisteri.lisaa(aku2); 
    koulutusrekisteri.lisaa(vesi1); 
    koulutusrekisteri.lisaa(vesi2); 
    koulutusrekisteri.lisaa(rel1); 
    koulutusrekisteri.lisaa(rel2); 
    koulutusrekisteri.tallenna(); 
    koulutusrekisteri = new Koulutusrekisteri(); 
    koulutusrekisteri.lueTiedostosta(hakemisto); 
    Collection<Tyontekija> kaikki = koulutusrekisteri.etsiTyontekija("", -1); 
    Iterator<Tyontekija> it = kaikki.iterator(); 
    assertEquals("From: Koulutusrekisteri line: 361", aku1, it.next()); 
    assertEquals("From: Koulutusrekisteri line: 362", aku2, it.next()); 
    assertEquals("From: Koulutusrekisteri line: 363", false, it.hasNext()); 
    Collection<Koulutus> koulutus = koulutusrekisteri.etsiKoulutus("", -1); 
    Iterator<Koulutus> ik = koulutus.iterator(); 
    assertEquals("From: Koulutusrekisteri line: 367", vesi1, ik.next()); 
    assertEquals("From: Koulutusrekisteri line: 368", vesi2, ik.next()); 
    assertEquals("From: Koulutusrekisteri line: 369", false, ik.hasNext()); 
    List<Relaatio> loytyneet = koulutusrekisteri.annaRelaatiot(0); 
    Iterator<Relaatio> ir = loytyneet.iterator(); 
    assertEquals("From: Koulutusrekisteri line: 373", rel1, ir.next()); 
    assertEquals("From: Koulutusrekisteri line: 374", rel2, ir.next()); 
    assertEquals("From: Koulutusrekisteri line: 375", false, ir.hasNext()); 
    loytyneet = koulutusrekisteri.annaRelaatiot(1); 
    ir = loytyneet.iterator(); 
    assertEquals("From: Koulutusrekisteri line: 378", rel1, ir.next()); 
    assertEquals("From: Koulutusrekisteri line: 379", rel2, ir.next()); 
    assertEquals("From: Koulutusrekisteri line: 380", false, ir.hasNext()); 
    koulutusrekisteri.lisaa(aku1); 
    koulutusrekisteri.lisaa(vesi1); 
    koulutusrekisteri.lisaa(rel1); 
    koulutusrekisteri.tallenna(); 
    assertEquals("From: Koulutusrekisteri line: 385", true, ftied.delete()); 
    assertEquals("From: Koulutusrekisteri line: 386", true, fktied.delete()); 
    assertEquals("From: Koulutusrekisteri line: 387", true, frtied.delete()); 
    assertEquals("From: Koulutusrekisteri line: 388", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa438 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa438() throws SailoException {    // Koulutusrekisteri: 438
    alusta(); 
    assertEquals("From: Koulutusrekisteri line: 441", 2, koulutusrekisteri.etsiTyontekija("*",0).size()); 
    koulutusrekisteri.korvaaTaiLisaa(aku1); 
    assertEquals("From: Koulutusrekisteri line: 443", 2, koulutusrekisteri.etsiTyontekija("*",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa457 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa457() throws SailoException {    // Koulutusrekisteri: 457
    alusta(); 
    assertEquals("From: Koulutusrekisteri line: 460", 2, koulutusrekisteri.etsiKoulutus("*",0).size()); 
    koulutusrekisteri.korvaaTaiLisaa(pitsi21); 
    assertEquals("From: Koulutusrekisteri line: 462", 2, koulutusrekisteri.etsiKoulutus("*",0).size()); 
  } // Generated by ComTest END
}