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
public class KoulutusDialogController implements ModalControllerInterface<Koulutus>,Initializable {   // aikasemmin <String> + lisätty Initializable
    
    @FXML private TextField koulutuksenNimi;
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
        if (koul != null && koul.getKoulutus().trim().equals("")) {
            naytaVirhe("Koulutus ei saa olla tyhjä!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    
    @FXML private void handlePeruuta() {
        koul = null;
        ModalController.closeStage(labelVirhe);
    }
    
    
//============= Metodit alla ==================================================================================================================================================================================
    
    
    private Koulutusrekisteri koulutusrekisteri;
    private Koulutus koul;
    private TextField edits[];
    

    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
//     * @param koul2 koulutus jota käsitellään
//     * @param koulr koulutusrekisteri
//     * @throws SailoException jos menee pieleen
     
    public void avaa(Koulutus koul2, Koulutusrekisteri koulr) throws SailoException {
        this.koulutusrekisteri = koulr;
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("KoulutusDialogView.fxml"), "Lisää koulutus", null, koul2);
    }
    */
    
    
    /**
     * Luodaan tekstikenttään koulutuksennimi
     * @param koulutuksenNimi mihin koulutuksen tiedot tuodaan
     * @return luotu tekstikenttä
     */
    public static TextField[] luoKentta(TextField koulutuksenNimi) {
        TextField[] edits = new TextField[1];
        return edits;
    }
    
    /**
     * Tyhjentään tekstikentät 
     * @param edits tyhjennettävät kentät
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit: edits) 
            if ( edit != null ) edit.setText(""); 
    }

    
    /**
     * Alustetaan koulutustekstikentän kuuntelijan
     */
    private void alusta() {

        edits = new TextField[] {koulutuksenNimi};
        
        for (TextField edit : edits)  
            if (edit != null) {  
                edit.setEditable(true);
            } 
    }
    
    
    @Override
    public void setDefault(Koulutus oletus) {
        koul = oletus;
        naytaKoulutus(edits, koul);
    }
    
    
    @Override
    public Koulutus getResult() {
        return koul;
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
     * @param koulutuksenNimi muuttunut tekstikenttä
     */
    protected void kasitteleUusiKoulutus(TextField koulutuksenNimi) {
        if (koul == null) return;
        try {
            String virhe;
            Koulutus koulutus = new Koulutus();
            koulutus = koul.clone();
            virhe = koulutus.setKoulutus(edits[0].getText());
            if (virhe != null) {
                Dialogs.showMessageDialog(virhe);
                return;
            }
            koulutusrekisteri.korvaaTaiLisaa(koulutus);
            //haeKoulutus(koulutus.getKoulutusTunnus());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }

    
    
    /**
     * Asettaa koulutuksen lisäysikkunaan tekstikentälle sinne kuuluvat tiedot.
     * @param edit taulukko tekstikentistä
     * @param koulutus jota käsitellään
     */
    public void naytaKoulutus(TextField[] edit, Koulutus koulutus) {
        if (koulutus == null) return;
        edit[0].setText(koulutus.getKoulutus());
    }
    
    
    /**
     * Luodaan koulutuksen lisäys dialogi ja palautetaan sama tietue muutettuna tahi null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param koul koulutus jota käsitellään
     * @param koulutusrekisteri rekisteri, jossa käsiteltävät koulutukset sijaitsevat
     * @return ladattavan modaalisen ikkunan
     */
    public static Koulutus uudenLisaaminen(Stage modalityStage, Koulutus koul, Koulutusrekisteri koulutusrekisteri) {
        return ModalController.<Koulutus, KoulutusDialogController>showModal(
                KoulutusDialogController.class.getResource("KoulutusDialogView.fxml"), 
                "Lisää koulutus", 
                modalityStage, koul, 
                controller->controller.setKoulutusrekisteri(koulutusrekisteri));
    }
}