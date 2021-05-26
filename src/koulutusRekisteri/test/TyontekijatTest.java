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
 * @version 2021.05.26 20:00:11 // Generated by ComTest
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
  /** testTyontekijatIterator240 */
  @Test
  public void testTyontekijatIterator240() {    // Tyontekijat: 240
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(); 
    aku1.rekisteroi(); aku2.rekisteroi(); 
    try {
    tyontekijat.lisaa(aku1); 
    tyontekijat.lisaa(aku2); 
    tyontekijat.lisaa(aku1); 
    } catch (SailoException e) {
    System.err.println(e.getMessage()); 
    }
    StringBuffer ids = new StringBuffer(30); 
    for ( Tyontekija tyon:tyontekijat )
    ids.append(" "+tyon.getTyontekijaTunnus()); 
    String tulos = " " + aku1.getTyontekijaTunnus() + " " + aku2.getTyontekijaTunnus() + " " + aku1.getTyontekijaTunnus(); 
    assertEquals("From: Tyontekijat line: 263", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Tyontekija> i=tyontekijat.iterator(); i.hasNext(); ) {
    Tyontekija tyon = i.next(); 
    ids.append(" "+tyon.getTyontekijaTunnus()); 
    }
    assertEquals("From: Tyontekijat line: 271", tulos, ids.toString()); 
    Iterator<Tyontekija> i=tyontekijat.iterator(); 
    assertEquals("From: Tyontekijat line: 274", true, i.next() == aku1); 
    assertEquals("From: Tyontekijat line: 275", true, i.next() == aku2); 
    assertEquals("From: Tyontekijat line: 276", true, i.next() == aku1); 
    try {
    i.next(); 
    fail("Tyontekijat: 278 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiTyontekija338 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiTyontekija338() throws SailoException {    // Tyontekijat: 338
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(); aku1.parse("1|Ankka Aku|Pelastustoiminta|Palomies"); 
    Tyontekija aku2 = new Tyontekija(); aku2.parse("2|Ankka Tupu|Pelastustoiminta|Palomies"); 
    Tyontekija aku3 = new Tyontekija(); aku3.parse("3|Ankka Hupu|Pelastustoiminta|Palomies"); 
    tyontekijat.lisaa(aku1); tyontekijat.lisaa(aku2); tyontekijat.lisaa(aku3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa372 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa372() throws SailoException,CloneNotSupportedException {    // Tyontekijat: 372
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(); 
    aku1.rekisteroi(); aku2.rekisteroi(); 
    assertEquals("From: Tyontekijat line: 378", 0, tyontekijat.getLkm()); 
    tyontekijat.korvaaTaiLisaa(aku1); assertEquals("From: Tyontekijat line: 379", 1, tyontekijat.getLkm()); 
    tyontekijat.korvaaTaiLisaa(aku2); assertEquals("From: Tyontekijat line: 380", 2, tyontekijat.getLkm()); 
    Tyontekija aku3 = aku1.clone(); 
    Iterator<Tyontekija> it = tyontekijat.iterator(); 
    assertEquals("From: Tyontekijat line: 383", true, it.next() == aku1); 
    tyontekijat.korvaaTaiLisaa(aku3); assertEquals("From: Tyontekijat line: 384", 2, tyontekijat.getLkm()); 
    it = tyontekijat.iterator(); 
    Tyontekija j0 = it.next(); 
    assertEquals("From: Tyontekijat line: 387", aku3, j0); 
    assertEquals("From: Tyontekijat line: 388", true, j0 == aku3); 
    assertEquals("From: Tyontekijat line: 389", false, j0 == aku1); 
  } // Generated by ComTest END
}