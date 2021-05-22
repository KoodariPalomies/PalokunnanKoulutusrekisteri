package palokunnanKoulutusrekisteri;

import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import koulutusRekisteri.Koulutus;
import koulutusRekisteri.Koulutusrekisteri;
import koulutusRekisteri.SailoException;


/**
 * Käsitellään koulutusten lisäysikkunan tapahtumat
 * @author mitulint
 * @version 1.0, 19.2.2021  / Tiedoston luonti
 * @version 1.1, 22.5.2021  / Lisäyksiä ja muokkauksia, jotta uusi koulutus voidaan lisätä ja muokata dialogin kautta
 *
 */
public class KoulutusDialogController implements ModalControllerInterface<Koulutus> {   // aikasemmin <String>
    
    @FXML private Button buttonOK;
    
    @FXML private Button buttonPeruuta;
    
    @FXML private TextField koulutuksenNimi;
    
    @FXML private Label labelVirhe;

    
//============= Metodit alla ==================================================================================================================================================================================
    
    
    private Koulutusrekisteri koulutusrekisteri;
    private Koulutus koul;
    private TextField edits[];
    
    
    /**
     * Asettaa koulutuksen lisäysikkunalle saman koulutusrekisterin kuin pääikkunassa.
     * @param koulutusrekisteri asetettava koulutusrekisteri
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) {
        this.koulutusrekisteri = koulutusrekisteri;
    }
    
    /**
     * @param modalityStage ???
     * @param koul koulutus jota käsitellään
     * @param koulutusrekisteri rekisteri, jossa käsiteltävät koulutukset sijaitsevat
     * @return ladattavan modaalisen ikkunan
     */
    public static Koulutus uudenLisaaminen(Stage modalityStage, Koulutus koul, Koulutusrekisteri koulutusrekisteri) {
        return ModalController.<Koulutus, KoulutusDialogController>showModal(KoulutusDialogController.class.getResource("KoulutusDialogView.fxml"), "Lisää koulutus", modalityStage, koul, 
                controller->controller.setKoulutusrekisteri(koulutusrekisteri));
    }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @param koul2 koulutus jota käsitellään
     * @param koulr koulutusrekisteri
     * @throws SailoException jos menee pieleen
     */
    public void avaa(Koulutus koul2, Koulutusrekisteri koulr) throws SailoException {
        this.koulutusrekisteri = koulr;
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("KoulutusDialogView.fxml"), "Lisää koulutus", null, koul2);
    }
    

    @Override
    public Koulutus getResult() {
        return null;
    }

    
    @Override
    public void setDefault(Koulutus oletus) {
        koul = oletus;
    }
    
    
    @Override
    public void handleShown() {
        naytaKoulutus(edits, koul);        
    }
    
    
    /**
     * @param url ei tietoa
     * @param bundle ei tietoa
     */
    @SuppressWarnings("unused")
    public void initialize(URL url, ResourceBundle bundle ) {
        alusta();
        }
    
    
    /**
     * Alustetaan koulutustekstikentän kuuntelijan
     */
    private void alusta() {

        edits = new TextField[] {koulutuksenNimi};
        
        for (TextField edit: edits)  
            if (edit != null) {  
                edit.setEditable(true);
            } 
    }
    
    
    /**
     * Asettaa koulutuksen lisäysikkunaan tekstikentälle sinne kuuluvat tiedot.
     * @param edit taulukko tekstikentistä
     * @param koulu jota käsitellään
     */
    public void naytaKoulutus(TextField[] edit, Koulutus koulu) {
        //edit[0].setText(koulu.getKoulutus());
        edit[0].setText("");
    }
    
    
    /**
     * Käsitellään koulutuksen lisäysikkunan tallennusnapin painallus
     */
    @FXML void tallennaN() {
        try {
            koulutusrekisteri.tallenna();
            return;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
        }
    }
    
    
    /**
     * Käsitellään peruutusnapin painallusta
     * @param event triggeri peruutanapin tapahtumalle
     */
    @FXML
    void peruuta() {
        koul = null;
        ModalController.closeStage(labelVirhe);
        
    }
}
