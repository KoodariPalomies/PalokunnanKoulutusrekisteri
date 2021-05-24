package palokunnanKoulutusrekisteri;

import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import koulutusRekisteri.Koulutusrekisteri;
import koulutusRekisteri.Relaatio;
import koulutusRekisteri.SailoException;

/**
 * Käsitellään koulutusten muokkausikkunan tapahtumat
 * @author mitulint
 * @version 1.0, 19.2.2021  / Tiedoston luonti
 * @version 1.1, 23.5.2021  / Käytännössä kaikki uusiksi, jotta uusi koulutus voidaan lisätä ja muokata dialogin kautta
 * @version 1.2, 24.5.2021  / Muokattu kasitteleTyontekijanUusiKoulutus() ja handleOK() --> dialogi toimii!
 */
public class LisaaTyontekijalleKoulutusDialogController implements ModalControllerInterface<Relaatio>, Initializable {
    
    @FXML private TextField koulutus;
    @FXML private TextField suoritettu;
    @FXML private TextField umpeutuu;
    @FXML private Label labelVirhe;
    
    
    /**
     * @param url ei tietoa
     * @param bundle ei tietoa
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle ) {
        alusta();
    }


    @FXML private void handleOK() {
        kasitteleTyontekijanUusiKoulutus(rel);
        ModalController.closeStage(labelVirhe);
    }    

    
    @FXML private void handlePeruuta() {
        rel = null;
        ModalController.closeStage(labelVirhe);
    }
    
    
//============= Metodit alla ==================================================================================================================================================================================
    
    
    private Koulutusrekisteri koulutusrekisteri;
    private Relaatio rel;
    private TextField tiedot[];

    
    /**
     * Alustetaan koulutustekstikentän kuuntelijan
     */
    private void alusta() {
        tiedot = new TextField[] {koulutus, suoritettu, umpeutuu};
        koulutus.setEditable(true);
        suoritettu.setEditable(true);
        umpeutuu.setEditable(true);
    }
    
    
    @Override
    public void setDefault(Relaatio oletus) {
        rel = oletus;
        //naytaRelaatio(tiedot, rel);
    }
    
    
    @Override
    public Relaatio getResult() {
        return rel;
    }
    
    
    /**
     * Asettaa koulutuksen lisäysikkunalle saman koulutusrekisterin kuin pääikkunassa.
     * @param koulutusrekisteri asetettava koulutusrekisteri
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) {
        this.koulutusrekisteri = koulutusrekisteri;
    }
    
    
    @Override
    public void handleShown() {
        //naytaKoulutus(edits, koul);        
    }
    
    
    @SuppressWarnings("unused")
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    /**
     * Käsitellään koulutukseen tullut muutos
     * @param rel muokattava relaatio
     */
    private void kasitteleTyontekijanUusiKoulutus(Relaatio rel) {
        try {
            rel.setKoulutus(tiedot[0].getText());
            rel.setSuoritettu(tiedot[1].getText());
            rel.setUmpeutuu(tiedot[2].getText());
            rel.rekisteroi();
            koulutusrekisteri.korvaaTaiLisaa(rel);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }
    }

    
    /**
     * Asettaa relaation lisäysikkunaan tekstikentälle sinne kuuluvat tiedot.
     * @param edit taulukko tekstikentistä
     * @param relaatio jota käsitellään
     */
    public void naytaRelaatio(TextField[] edit, Relaatio relaatio) {
        if (relaatio == null) return;
        edit[0].setText(relaatio.getKoulutus());
        edit[1].setText(relaatio.getSuoritettu());
        edit[2].setText(relaatio.getUmpeutuu());
    }
    
    
    /**
     * Luodaan koulutuksen lisäys dialogi ja palautetaan sama tietue muutettuna tahi null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param rel koulutus jota käsitellään
     * @param koulutusrekisteri rekisteri, jossa käsiteltävät koulutukset sijaitsevat
     * @return ladattavan modaalisen ikkunan
     */
    public static Relaatio uudenLisaaminen(Stage modalityStage, Relaatio rel, Koulutusrekisteri koulutusrekisteri) {
        return ModalController.<Relaatio, LisaaTyontekijalleKoulutusDialogController>showModal(
                LisaaTyontekijalleKoulutusDialogController.class.getResource("LisaaTyontekijalleKoulutusDialogView.fxml"), 
                "Lisää työntekijälle koulutustiedot", 
                modalityStage, rel, 
                controller->controller.setKoulutusrekisteri(koulutusrekisteri));
    }
}