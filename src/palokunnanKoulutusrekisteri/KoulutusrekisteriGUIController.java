package palokunnanKoulutusrekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import koulutusRekisteri.Koulutusrekisteri;
import koulutusRekisteri.SailoException;
import koulutusRekisteri.Tyontekija;

/**
 * @author mitulint
 * @version 18.2.2021
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 */
public class KoulutusrekisteriGUIController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ListChooser<Tyontekija> chooserTyontekijat;
    @FXML private ScrollPane panelTyontekija;
    
    
    /**
     * @param url ei tietoa
     * @param bundle ei tietoa
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle ) {
        alusta();
        }

    
    /**
     * Käsitellään uuden työntekijän lisääminen
     */
    @FXML private void handleUusiTyontekija() {
        //Dialogs.showQuestionDialog("Uusi työntekijä", "Lisätäänkö uusi työntekijä?", "Kyllä", "Ei");
        //ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("UusiTyontekijaDialogView.fxml"), "Lisää uusi työntekijä", null, "");
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
        Dialogs.showMessageDialog("Ei osata vielä avata");
    }
    
    
    /**
     * Käsitellään toiminnan lopettaminen
     */
    @FXML private void handleLopeta() {
        Dialogs.showMessageDialog("Ei osata vielä lopettaa :)");
    }


    /**
     * Käsitellään valittujen asioiden tulostaminen
     */
    @FXML private void handleTulosta() {
        TulostusController.tulosta("Ei osata vielä tulostaa");
    }

    
    /**
     * Käsitellään tietojen tallentaminen
     */
    @FXML private void handleTallenna() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa");
    }
    
    
    /**
     * Käsitellään lisää työntekijälle koulutus
     */
    @FXML private void handleLisaaKoulutus() {
        Dialogs.showQuestionDialog("Lisää koulutus", "Lisätäänkö työntekijälle koulutus?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("KoulutusDialogView.fxml"), "Lisää koulutus", null, "");
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
    
    
    private Koulutusrekisteri koulutusrekisteri;
    private Tyontekija tyontekijaKohdalla;
    private TextArea areaTyontekija = new TextArea();   // TODO: poista lopuksi
    
    private void alusta() {
        panelTyontekija.setContent(areaTyontekija);
        areaTyontekija.setFont(new Font("Courier New", 12));
        panelTyontekija.setFitToHeight(true);
        chooserTyontekijat.clear();
        chooserTyontekijat.addSelectionListener(e -> naytaTyontekija());
    }
    
    
    public boolean avaa() {
        //String uusinimi = KerhonNimiController.kysyNimi(null, kerhonnimi);
        String uusinimi = AloitusIkkunaController.kysyNimi(null, kerhonnimi;)
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    private void naytaTyontekija() {
        Tyontekija tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        
        if (tyontekijaKohdalla == null) return;
        
        areaTyontekija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tyontekijaKohdalla.tulosta(os);
        }
    }
    
    
    private void hae(int tnro) {
        chooserTyontekijat.clear();
        
        int index = 0;
        for (int i = 0; i < koulutusrekisteri.getTyontekijoita(); i++) {
            Tyontekija tyontekija = koulutusrekisteri.annaTyontekija(i);
            if (tyontekija.getTyontekijaTunnus() == tnro) index = i;
            chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
        }
        chooserTyontekijat.setSelectedIndex(index);
    }
    
    
    /**
     * Lisätään rekisteriin uusi työntekijä
     */
    public void uusiTyontekija() {
        Tyontekija tyontekija = new Tyontekija();
        tyontekija.lisaaTyontekija();
        tyontekija.vastaaAkuAnkka();        // TODO: korvaa dialogilla aikanaan
        try {
            koulutusrekisteri.lisaa(tyontekija);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
            //e.printStackTrace();
        }
        hae(tyontekija.getTyontekijaTunnus());
    }
    
    
    /**
     * Asetetaan käytettävä koulutusrekisteri
     * @param koulutusrekisteri jota käytetään
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) {
        this.koulutusrekisteri = koulutusrekisteri;
    }
    
    
}
