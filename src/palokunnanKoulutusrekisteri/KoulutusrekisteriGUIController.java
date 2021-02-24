package palokunnanKoulutusrekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author mitulint
 * @version 18.2.2021
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 */
public class KoulutusrekisteriGUIController {

    
    /**
     * Käsitellään uuden työntekijän lisääminen
     */
    @FXML private void handleUusiTyontekija() {
        Dialogs.showQuestionDialog("Uusi työntekijä", "Lisätäänkö uusi työntekijä?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("UusiTyontekijaDialogView.fxml"), "Lisää uusi työntekijä", null, "");
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
        Dialogs.showMessageDialog("Ei osata poistaa työntekijää");
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
        Dialogs.showMessageDialog("Ei osata poistaa koulutusta");
    }
    
    
    /**
     * Käsitellään apujen hakeminen
     */
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Try harder!");
    }
    
    
    /**
     * Käsitellään sovelluksen tietojen hakeminen
     */
    @FXML private void handleTietoja() {
        //Dialogs.showMessageDialog("Ei löydy tietoa :(");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
}
