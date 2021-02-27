package koulutusRekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import koulutusRekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.02.27 23:00:10 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class TyontekijatTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa32 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa32() throws SailoException {    // Tyontekijat: 32
    Tyontekijat tyontekijat = new Tyontekijat(); 
    Tyontekija aku1 = new Tyontekija(), aku2 = new Tyontekija(); 
    assertEquals("From: Tyontekijat line: 36", 0, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 37", 1, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku2); assertEquals("From: Tyontekijat line: 38", 2, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 39", 3, tyontekijat.getLkm()); 
    assertEquals("From: Tyontekijat line: 40", aku1, tyontekijat.anna(0)); 
    assertEquals("From: Tyontekijat line: 41", aku2, tyontekijat.anna(1)); 
    assertEquals("From: Tyontekijat line: 42", aku1, tyontekijat.anna(2)); 
    assertEquals("From: Tyontekijat line: 43", false, tyontekijat.anna(1) == aku1); 
    assertEquals("From: Tyontekijat line: 44", true, tyontekijat.anna(1) == aku2); 
    try {
    assertEquals("From: Tyontekijat line: 45", aku1, tyontekijat.anna(3)); 
    fail("Tyontekijat: 45 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 46", 4, tyontekijat.getLkm()); 
    tyontekijat.lisaa(aku1); assertEquals("From: Tyontekijat line: 47", 5, tyontekijat.getLkm()); 
    try {
    tyontekijat.lisaa(aku1); 
    fail("Tyontekijat: 48 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}