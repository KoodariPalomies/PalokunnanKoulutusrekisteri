package palokunnanKoulutusrekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import koulutusRekisteri.Koulutus;
import koulutusRekisteri.Koulutusrekisteri;
import koulutusRekisteri.Relaatio;
import koulutusRekisteri.SailoException;
import koulutusRekisteri.Tyontekija;
import koulutusRekisteri.Tyontekijat;

/**
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author mitulint
 * @version 24.3.2021
 */
public class KoulutusrekisteriGUIController implements Initializable {
    
    @FXML private TextField                 hakuehto;
    @FXML private ComboBoxChooser<String>   cbKentat;
    @FXML private Label                     labelVirhe;
    @FXML private ListChooser<Tyontekija>   chooserTyontekijat;
    @FXML private ScrollPane                panelTyontekija;
    @FXML private ListChooser<Koulutus>     chooserKoulutukset;
    @FXML private ScrollPane                panelKoulutus;
    
    private String kayttajatunnus   = "";
    private String salasana         = "";
    
    
    /**
     * @param url ei tietoa
     * @param bundle ei tietoa
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle ) {
        alusta();
        }
    
    
    @FXML private void handleHakuehto() {
         String hakukentta = cbKentat.getSelectedText();
         String ehto = hakuehto.getText(); 
         if ( ehto.isEmpty() )
             naytaVirhe(null);
         else
             naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
    }

    
    /**
     * Käsitellään uuden työntekijän lisääminen
     */
    @FXML private void handleUusiTyontekija() {
        uusiTyontekija();
    }
    
    
    /**
     * Käsitellään työntekijän muokkaaminen
     */
    @FXML private void handleMuokkaaTyontekija() {
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("TyontekijaDialogView.fxml"), "Muokkaa työntekijää", null, "");
    }
    
    
    /**
     * Käsitellään työntekijän poistaminen
     */
    @FXML private void handlePoistaTyontekija() {
        Dialogs.showQuestionDialog("Poista työntekijä", "Poistetaanko työntekijä?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("PoistoGUIView.fxml"), "Poista työntekijä", null, "");
    }
    
    
    /**
     * Käsitellään tietojen avaaminen
     */
    @FXML private void handleAvaa() {
        avaa();
    }


    /**
     * Käsitellään valittujen asioiden tulostaminen
     */
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea()); 
    }

    
    /**
     * Käsitellään tietojen tallentaminen
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään toiminnan lopettaminen
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    /**
     * Käsitellään uuden koulutuksen lisääminen
     */
    @FXML private void handleLisaaKoulutus() {
        uusiKoulutus();
    }
    
    /**
     * Käsitellään työntekijän koulutustietojen lisäämisen koulutusrekisteriin
     */
    @FXML private void handleLisaaTyontekijalleKoulutus() {
        lisaaTyontekijalleKoulutus();
    }
    
    
    /**
     * Käsitellään koulutuksen muokkaaminen
     */
    @FXML private void handleMuokkaaKoulutusta() {
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("MuokkaaKoulutustaDialogView.fxml"), "Muokkaa työntekijän koulutusta", null, "");
    }
    
    
    /**
     * Käsitellään koulutuksen poistaminen
     */
    @FXML private void handlePoistaKoulutus() {
        Dialogs.showQuestionDialog("Poista koulutus", "Poistetaanko työntekijän koulutus?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("PoistaKoulutusGUIView.fxml"), "Poista koulutus", null, "");
    }
    
    
    /**
     * Käsitellään apujen hakeminen
     * TODO: lukeminen tiedostosta tai selaimessa
     */
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Try harder!");
    }
    
    
    /**
     * Käsitellään sovelluksen tietojen hakeminen
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }


//==============================================================================================================================
// Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    
    private Koulutusrekisteri   koulutusrekisteri;
    private Tyontekija          tyontekijaKohdalla;
    private Koulutus            koulutusKohdalla;
    private TextArea            areaTyontekija      = new TextArea();   // TODO: poista lopuksi
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaa tulostaa työntekijän ja koulutuksen tiedot.
     * Alustetaan myös työntekijälistan ja koulutuslistan kuuntelijat
     */
    private void alusta() {
        panelTyontekija.setContent(areaTyontekija);
        areaTyontekija.setFont(new Font("Courier New", 12));
        panelTyontekija.setFitToHeight(true);
        
        chooserTyontekijat.clear();
        chooserTyontekijat.addSelectionListener(e -> naytaTyontekija());
        
        chooserKoulutukset.clear();
        chooserKoulutukset.addSelectionListener(e -> naytaKoulutus());
    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    /**
     * Alustaa koulutusrekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta työntekijän tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        kayttajatunnus = nimi;
        setTitle("Palomies - " + kayttajatunnus);
        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
        // if (virhe != null) 
           Dialogs.showMessageDialog(virhe);
    }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = AloitusIkkunaGUIController.kysyNimi(null, kayttajatunnus);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan, sitten kun osataan!");
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
        
    
    /**
     * Näyttää listasta valitun työntekijän tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaTyontekija() {
        tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        
        if (tyontekijaKohdalla == null) return;
        
        areaTyontekija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tulosta(os, tyontekijaKohdalla);
        }
        naytaTyontekijanKoulutukset();
    }
    
    
    /**
     * Näyttää listasta valitun koulutuksen tiedot, tilapäisesti yhteen isoon edit kenttään
     */
    private void naytaKoulutus() {
        koulutusKohdalla = chooserKoulutukset.getSelectedObject();
        
        if (koulutusKohdalla == null) return;
       
        areaTyontekija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tulosta(os, koulutusKohdalla);
        }
    }
    
    
    /**
     * Näyttää listasta valitun työntekijän koulutukset, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaTyontekijanKoulutukset() {
        tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        
        if (tyontekijaKohdalla == null) return;
        
        areaTyontekija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tulosta(os, tyontekijaKohdalla);
        }
    }


    /**
     * Hakee työntekijän tiedot listaan
     * @param tnro työntekijätunnus, joka aktivoituu haun jälkeen
     */
    private void haeTyontekijat(int tnro) {
        chooserTyontekijat.clear();
        
        int index = 0;
        for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
            Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
            if (tyontekija.getTyontekijaTunnus() == tnro) index = i;
            chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
        }
        chooserTyontekijat.setSelectedIndex(index);     // tästä tulee muutosviesti, joka näyttää työntekijän
    }
    
    
    /**
     * Hakee koulutuksen tiedot listaan
     * @param knro koulutustunnus, joka aktivoituu haun jälkeen
     */
    private void haeKoulutukset(int knro) {
        chooserKoulutukset.clear();
        
        int index = 0;
        for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
            Koulutus koulutus = koulutusrekisteri.annaKoulutus(i);
            if (koulutus.getKoulutusTunnus() == knro) index = i;
            chooserKoulutukset.add(koulutus.getKoulutus(), koulutus);
        }
        chooserKoulutukset.setSelectedIndex(index);
    }
    
    
    /**
     * Hakee työntekijän koulutustiedot listaan
     */
    private void haeRelaatiot() {
        for (Relaatio rel:koulutusrekisteri.annaRelaatiot(tyontekijaKohdalla)) {
            rel.tulosta(System.out);
        }
       
    }
    
    
    /**
     * Lisätään rekisteriin uusi työntekijä
     */
    public void uusiTyontekija() {
        Tyontekija uusi = new Tyontekija();
        uusi.rekisteroi();
        uusi.vastaaAkuAnkka();        // TODO: korvaa dialogilla aikanaan
        try {
            koulutusrekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }
        haeTyontekijat(uusi.getTyontekijaTunnus());
    }
    
    
    /**
     * Lisätään rekisteriin uusi koulutus
     */ 
    public void uusiKoulutus() { 
        Koulutus koul = new Koulutus(); 
        koul.rekisteroi(); 
        koul.vastaaVesisukeltaja(); 
        try {
            koulutusrekisteri.lisaa(koul);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        } 
        haeKoulutukset(koul.getKoulutusTunnus());
    }
    
    
    /**
     * Lisätään koulutusrekisteriin työntekijän koulutustiedot
     */
    public void lisaaTyontekijalleKoulutus() {
        //tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        //koulutusKohdalla = chooserKoulutukset.getSelectedObject();
        if ( tyontekijaKohdalla == null ) return; 
        if ( koulutusKohdalla == null ) return;
        
        Relaatio rel = new Relaatio(tyontekijaKohdalla.getTyontekijaTunnus(), koulutusKohdalla.getKoulutusTunnus());
        rel.rekisteroi();
        rel.vastaaRelaatio();
        try {
            koulutusrekisteri.lisaa(rel);
        } catch (SailoException e) {
            e.printStackTrace();
        }
        haeRelaatiot();
    }
    
    
    /**
     * Asetetaan käytettävä koulutusrekisteri
     * @param koulutusrekisteri jota käytetään
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) {
        this.koulutusrekisteri = koulutusrekisteri;
        naytaTyontekija();
        naytaKoulutus();
        naytaTyontekijanKoulutukset();
    }
    
    
         /**
          * Tulostaa työntekijän tiedot
          * @param os tietovirta johon tulostetaan
          * @param tyontekija tulostettava työntekijä
          */
         public void tulosta(PrintStream os, final Tyontekija tyontekija) {
             os.println("----------------------------------------------");
             tyontekija.tulosta(os);
             os.println("----------------------------------------------");
             
             for (Relaatio rel:koulutusrekisteri.annaRelaatiot(tyontekija)) {
                 rel.tulosta(os);
             }

         }
         
         
         /**
          * Tulostaa koulutuksen tiedot
          * @param os tietovirta johon tulostetaan
          * @param koulutus tulostettava koulutus
          */
         private void tulosta(PrintStream os, final Koulutus koulutus) {
             os.println("----------------------------------------------");
             koulutus.tulosta(os);
             os.println("----------------------------------------------");
             
             for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
                 Koulutus koulutus2 = koulutusrekisteri.annaKoulutus(i);
                 System.out.println("Koulutus paikassa: " + i);
                 koulutus2.tulosta(System.out);
             }
         }
         
         
         /**
          * Tulostaa työntekijän koulutustiedot
          * @param os tietovirta johon tulostetaan
          * @param relaatio tulostettavat koulutukset
          */
         private void tulosta(PrintStream os, final Relaatio relaatio) {
             os.println("----------------------------------------------");
             relaatio.tulosta(os);
             os.println("----------------------------------------------");
             
            // for (int i = 0; i < koulutusrekisteri.getRelaatiot(); i++) {
              //   List<Relaatio> relaatio2 = koulutusrekisteri.annaRelaatiot(tyontekijaKohdalla);
                // System.out.println("Koulutus paikassa: " + i);
                 //((Koulutus) relaatio2).tulosta(System.out);
             
                 List<Relaatio> relaatiot = koulutusrekisteri.annaRelaatiot(tyontekijaKohdalla);
                 for (Relaatio rel:relaatiot)
                     rel.tulosta(os);
         }
        
        
         /**
          * Tulostaa listassa olevat työntekijät tekstialueeseen
          * @param text alue johon tulostetaan
          */
         public void tulostaValitut(TextArea text) {
             try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
                 os.println("Tulostetaan kaikki työntekijät");
                 for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
                     Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
                     tulosta(os, tyontekija);
                     os.println("\n\n");
                 }
                 
                 for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
                     Koulutus koulutus = koulutusrekisteri.annaKoulutus(i);
                     tulosta(os, koulutus);
                     os.println("\n\n");
                     
                 // Laitetaanko tänne se relaationkin tulostus?
                // for (int i = 0; i < koulutusrekisteri.getRelaatiot(); i++) {
                  //   List<Relaatio> relaatio = koulutusrekisteri.annaRelaatiot(tyontekijaKohdalla);
                    // tulosta(os, relaatio);
                    // os.println("\n\n");
                 }
                 }
             }
         }