package koulutusRekisteri;


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
    
    private Tyontekijat tyontekijat = new Tyontekijat();
    
    
    /**
     * Lisätään uusi työntekijä
     * @param tyontekija lisättävä työntekijä
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Tyontekija tyontekija) throws SailoException {
        this.tyontekijat.lisaa(tyontekija);
    }
    
    
    /**
     * @return työntekijöiden lkm
     */
    public int getTyontekijoita() {
        return this.tyontekijat.getLkm();
    }
    
    
    /**
     * Antaa koulutusrekisterin i:n työntekijän
     * @param i monesko työntekijä (alkaa 0:sta)
     * @return työntekijä paikasta i
     */
    public Tyontekija annaTyontekija(int i) {
        return tyontekijat.anna(i);
    }
    
    
    /**
     * 
     */
    public Koulutukset koulutukset = new Koulutukset();
    
    
    /**
     * Lisätään uusi koulutus
     * @param koulutus lisättävä koulutus
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Koulutus koulutus) throws SailoException {
        this.koulutukset.lisaa(koulutus);
    }
    
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
        
        Tyontekija aku = new Tyontekija();
        Tyontekija aku2 = new Tyontekija();
        aku.lisaaTyontekija();
        aku.vastaaAkuAnkka();       // aku.taytaAkuAnkkaTiedoilla();
        aku2.lisaaTyontekija();
        aku2.vastaaAkuAnkka();      // aku2.taytaAkuAnkkaTiedoilla();
        
        try {
            koulutusrekisteri.lisaa(aku);
            koulutusrekisteri.lisaa(aku);
            koulutusrekisteri.lisaa(aku2);
            koulutusrekisteri.lisaa(aku2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
            Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
            tyontekija.tulosta(System.out);
            //System.out.println(tyontekija);
        }

    }

}
