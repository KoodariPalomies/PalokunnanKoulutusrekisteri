package koulutusRekisteri.test;
// Generated by ComTest BEGIN
import java.io.File;
import koulutusRekisteri.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.26 19:53:41 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class KoulutuksetTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa60 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa60() throws SailoException {    // Koulutukset: 60
    Koulutukset koulutukset = new Koulutukset(); 
    Koulutus vesi1 = new Koulutus(), vesi2 = new Koulutus(); 
    assertEquals("From: Koulutukset line: 64", 0, koulutukset.getLkm()); 
    koulutukset.lisaa(vesi1); assertEquals("From: Koulutukset line: 65", 1, koulutukset.getLkm()); 
    koulutukset.lisaa(vesi2); assertEquals("From: Koulutukset line: 66", 2, koulutukset.getLkm()); 
    koulutukset.lisaa(vesi1); assertEquals("From: Koulutukset line: 67", 3, koulutukset.getLkm()); 
    assertEquals("From: Koulutukset line: 68", vesi1, koulutukset.annaKoulutus(0)); 
    assertEquals("From: Koulutukset line: 69", vesi2, koulutukset.annaKoulutus(1)); 
    assertEquals("From: Koulutukset line: 70", vesi1, koulutukset.annaKoulutus(2)); 
    assertEquals("From: Koulutukset line: 71", false, koulutukset.annaKoulutus(1) == vesi1); 
    assertEquals("From: Koulutukset line: 72", true, koulutukset.annaKoulutus(1) == vesi2); 
    try {
    assertEquals("From: Koulutukset line: 73", vesi1, koulutukset.annaKoulutus(3)); 
    fail("Koulutukset: 73 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    koulutukset.lisaa(vesi1); assertEquals("From: Koulutukset line: 74", 4, koulutukset.getLkm()); 
    koulutukset.lisaa(vesi1); assertEquals("From: Koulutukset line: 75", 5, koulutukset.getLkm()); 
    try {
    koulutukset.lisaa(vesi1); 
    fail("Koulutukset: 76 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta105 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta105() throws SailoException {    // Koulutukset: 105
    Koulutukset koulutukset = new Koulutukset(); 
    Koulutus vesi1 = new Koulutus(); vesi1.vastaaVesisukeltaja(); 
    Koulutus vesi2 = new Koulutus(); vesi2.vastaaVesisukeltaja(); 
    Koulutus vesi3 = new Koulutus(); vesi3.vastaaVesisukeltaja(); 
    String tiedNimi = "testikoulutukset"; 
    File ftied = new File (tiedNimi+".dat"); 
    ftied.delete(); 
    try {
    koulutukset.lueTiedostosta(tiedNimi); 
    fail("Koulutukset: 115 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    koulutukset.lisaa(vesi1); 
    koulutukset.lisaa(vesi2); 
    koulutukset.lisaa(vesi3); 
    koulutukset.tallenna(); 
    koulutukset = new Koulutukset(); 
    koulutukset.lueTiedostosta(tiedNimi); 
    Iterator<Koulutus> i = koulutukset.iterator(); 
    assertEquals("From: Koulutukset line: 123", vesi1.toString(), i.next().toString()); 
    assertEquals("From: Koulutukset line: 124", vesi2.toString(), i.next().toString()); 
    assertEquals("From: Koulutukset line: 125", vesi3.toString(), i.next().toString()); 
    assertEquals("From: Koulutukset line: 126", false, i.hasNext()); 
    koulutukset.lisaa(vesi3); 
    koulutukset.tallenna(); 
    assertEquals("From: Koulutukset line: 129", true, ftied.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testIterator224 */
  @Test
  public void testIterator224() {    // Koulutukset: 224
    Koulutukset koulutukset = new Koulutukset(); 
    Koulutus vesi1 = new Koulutus(1); vesi1.rekisteroi(); 
    Koulutus vesi2 = new Koulutus(2); vesi2.rekisteroi(); 
    try {
    koulutukset.lisaa(vesi1); 
    koulutukset.lisaa(vesi2); 
    }  catch ( Exception e) {
    System.err.println(e.getMessage()); 
    }
    Iterator<Koulutus> i=koulutukset.iterator(); 
    assertEquals("From: Koulutukset line: 239", vesi1, i.next()); 
    assertEquals("From: Koulutukset line: 240", vesi2, i.next()); 
    try {
    assertEquals("From: Koulutukset line: 241", vesi1, i.next()); 
    fail("Koulutukset: 241 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
    int n = 0; 
    int jnrot[] = { 1,2} ; 
    for ( Koulutus koul : koulutukset) {
    assertEquals("From: Koulutukset line: 247", jnrot[n], koul.getKoulutusTunnus()); n++; 
    }
    assertEquals("From: Koulutukset line: 250", 2, n); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiKoulutus266 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiKoulutus266() throws SailoException {    // Koulutukset: 266
    Koulutukset koulutukset = new Koulutukset(); 
    Koulutus vesi1 = new Koulutus(); vesi1.parse("1|Vesisukeltaja"); 
    Koulutus vesi2 = new Koulutus(); vesi2.parse("1|Vesisukeltaja"); 
    Koulutus vesi3 = new Koulutus(); vesi3.parse("1|Vesisukeltaja"); 
    koulutukset.lisaa(vesi1); koulutukset.lisaa(vesi2); koulutukset.lisaa(vesi3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa300 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa300() throws SailoException,CloneNotSupportedException {    // Koulutukset: 300
    Koulutukset koulutukset = new Koulutukset(); 
    Koulutus aku1 = new Koulutus(), aku2 = new Koulutus(); 
    aku1.rekisteroi(); aku2.rekisteroi(); 
    assertEquals("From: Koulutukset line: 306", 0, koulutukset.getLkm()); 
    koulutukset.korvaaTaiLisaa(aku1); assertEquals("From: Koulutukset line: 307", 1, koulutukset.getLkm()); 
    koulutukset.korvaaTaiLisaa(aku2); assertEquals("From: Koulutukset line: 308", 2, koulutukset.getLkm()); 
    Koulutus aku3 = new Koulutus(); aku3.rekisteroi(); koulutukset.korvaaTaiLisaa(aku3); 
    Iterator<Koulutus> it = koulutukset.iterator(); 
    assertEquals("From: Koulutukset line: 311", true, it.next() == aku1); 
    koulutukset.korvaaTaiLisaa(aku3); assertEquals("From: Koulutukset line: 312", 2, koulutukset.getLkm()); 
    it = koulutukset.iterator(); 
    Koulutus j0 = it.next(); 
    assertEquals("From: Koulutukset line: 315", aku3, j0); 
    assertEquals("From: Koulutukset line: 316", true, j0 == aku3); 
    assertEquals("From: Koulutukset line: 317", false, j0 == aku1); 
  } // Generated by ComTest END
}