package palokunnanKoulutusrekisteri;

import java.io.PrintStream;
import java.net.URL;
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
import koulutusRekisteri.SailoException;
import koulutusRekisteri.Tyontekija;

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
     * Käsitellään lisää työntekijälle koulutus
     */
    @FXML private void handleLisaaKoulutus() {
        //Dialogs.showQuestionDialog("Lisää koulutus", "Lisätäänkö työntekijälle koulutus?", "Kyllä", "Ei");
        //ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("KoulutusDialogView.fxml"), "Lisää koulutus", null, "");
        uusiKoulutus();
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
    private TextArea            areaTyontekija = new TextArea();   // TODO: poista lopuksi
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaa tulostaa työntekijän tiedot.
     * Alustetaan myös työntekijälistan kuuntelija
     */
    private void alusta() {
        panelTyontekija.setContent(areaTyontekija);
        areaTyontekija.setFont(new Font("Courier New", 12));
        panelTyontekija.setFitToHeight(true);
        
        chooserTyontekijat.clear();
        chooserTyontekijat.addSelectionListener(e -> naytaTyontekija());
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
    }
    
    /**
     * Hakee työntekijän tiedot listaan
     * @param tnro työntekijäntunnus, joka aktivoituu haun jälkeen
     */
    private void hae(int tnro) {
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
     * Lisätään rekisteriin uusi työntekijä
     */
    public void uusiTyontekija() {
        Tyontekija uusi = new Tyontekija();
        uusi.lisaaTyontekija();
        uusi.vastaaAkuAnkka();        // TODO: korvaa dialogilla aikanaan
        try {
            koulutusrekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }
        hae(uusi.getTyontekijaTunnus());
    }
    
    
    /**
     * Tekee uuden tyhjän koulutuksen editointia varten
     */ 
    public void uusiKoulutus() { 
        if ( tyontekijaKohdalla == null ) return; 
        Koulutus koul = new Koulutus(); 
        koul.lisaaKoulutus(); 
        koul.vastaaVesisukeltaja(tyontekijaKohdalla.getTyontekijaTunnus()); 
        try {
            koulutusrekisteri.lisaa(koul);
        } catch (SailoException e) {
            e.printStackTrace();
        } 
        hae(koul.getKoulutusTunnus());
    }
    
    
    /**
     * Asetetaan käytettävä koulutusrekisteri
     * @param koulutusrekisteri jota käytetään
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) {
        this.koulutusrekisteri = koulutusrekisteri;
        naytaTyontekija();
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
             List<Koulutus> koulutukset = koulutusrekisteri.annaKoulutukset(tyontekija);   
             for (Koulutus koul:koulutukset)
                 koul.tulosta(os); 
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
             }
         }
    
}
