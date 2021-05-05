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
 * @version 2021.05.05 17:53:46 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class TyontekijatTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa60 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa60() throws SailoException {    // Tyontekijat: 60
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(); 
    assertEquals("From: Tyontekijat line: 64", 0, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 65", 1, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku2); assertEquals("From: Tyontekijat line: 66", 2, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 67", 3, tyontekijat.getLkm()); 
    assertEquals("From: Tyontekijat line: 68", aku1, tyontekijat.annaTyontekija(0)); 
    assertEquals("From: Tyontekijat line: 69", aku2, tyontekijat.annaTyontekija(1)); 
    assertEquals("From: Tyontekijat line: 70", aku1, tyontekijat.annaTyontekija(2)); 
    assertEquals("From: Tyontekijat line: 71", false, tyontekijat.annaTyontekija(1) == aku1); 
    assertEquals("From: Tyontekijat line: 72", true, tyontekijat.annaTyontekija(1) == aku2); 
    try {
    assertEquals("From: Tyontekijat line: 73", aku1, tyontekijat.annaTyontekija(3)); 
    fail("Tyontekijat: 73 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 74", 4, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 75", 5, tyontekijat.getLkm()); 
    try {
    tyontekijat.lisaa(aku1); 
    fail("Tyontekijat: 76 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta108 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta108() throws SailoException {    // Tyontekijat: 108
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(); aku1.vastaaAkuAnkka(); 
    Tyontekija aku2 = new Tyontekija(); aku2.vastaaAkuAnkka(); 
    Tyontekija aku3 = new Tyontekija(); aku3.vastaaAkuAnkka(); 
    String tiedNimi = "testityontekijat"; 
    File ftied = new File (tiedNimi+".dat"); 
    ftied.delete(); 
    try {
    tyontekijat.lueTiedostosta(tiedNimi); 
    fail("Tyontekijat: 118 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    tyontekijat.lisaa(aku1); 
    tyontekijat.lisaa(aku2); 
    tyontekijat.lisaa(aku3); 
    tyontekijat.tallenna(); 
    tyontekijat = new Tyontekijat(); 
    tyontekijat.lueTiedostosta(tiedNimi); 
    Iterator<Tyontekija> i = tyontekijat.iterator(); 
    assertEquals("From: Tyontekijat line: 126", aku1.toString(), i.next().toString()); 
    assertEquals("From: Tyontekijat line: 127", aku2.toString(), i.next().toString()); 
    assertEquals("From: Tyontekijat line: 128", aku3.toString(), i.next().toString()); 
    assertEquals("From: Tyontekijat line: 129", false, i.hasNext()); 
    tyontekijat.lisaa(aku3); 
    tyontekijat.tallenna(); 
    assertEquals("From: Tyontekijat line: 132", true, ftied.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testTyontekijatIterator241 */
  @Test
  public void testTyontekijatIterator241() {    // Tyontekijat: 241
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(); 
    aku1.rekisteroi(); aku2.rekisteroi(); 
    try {
    tyontekijat.lisaa(aku1); 
    fail("Tyontekijat: 250 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    tyontekijat.lisaa(aku2); 
    fail("Tyontekijat: 251 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    try {
    tyontekijat.lisaa(aku1); 
    fail("Tyontekijat: 252 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    StringBuffer ids = new StringBuffer(30); 
    for ( Tyontekija tyon:tyontekijat )
    ids.append(" "+tyon.getTyontekijaTunnus()); 
    String tulos = " " + aku1.getTyontekijaTunnus() + " " + aku2.getTyontekijaTunnus() + " " + aku1.getTyontekijaTunnus(); 
    assertEquals("From: Tyontekijat line: 260", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Tyontekija> i=tyontekijat.iterator(); i.hasNext(); ) {
    Tyontekija tyon = i.next(); 
    ids.append(" "+tyon.getTyontekijaTunnus()); 
    }
    assertEquals("From: Tyontekijat line: 268", tulos, ids.toString()); 
    Iterator<Tyontekija> i=tyontekijat.iterator(); 
    assertEquals("From: Tyontekijat line: 271", true, i.next() == aku1); 
    assertEquals("From: Tyontekijat line: 272", true, i.next() == aku2); 
    assertEquals("From: Tyontekijat line: 273", true, i.next() == aku1); 
    try {
    i.next(); 
    fail("Tyontekijat: 275 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiTyontekija335 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiTyontekija335() throws SailoException {    // Tyontekijat: 335
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(); aku1.parse("1|Ankka Aku|Pelastustoiminta|Palomies"); 
    Tyontekija aku2 = new Tyontekija(); aku2.parse("2|Ankka Tupu|Pelastustoiminta|Palomies"); 
    Tyontekija aku3 = new Tyontekija(); aku3.parse("3|Ankka Hupu|Pelastustoiminta|Palomies"); 
    tyontekijat.lisaa(aku1); tyontekijat.lisaa(aku2); tyontekijat.lisaa(aku3); 
  } // Generated by ComTest END
}