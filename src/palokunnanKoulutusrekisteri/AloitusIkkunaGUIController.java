package palokunnanKoulutusrekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Kysytään käyttäjätunnus ja salasana sekä luodaan tätä varten dialogi.
 * 
 * @author mitulint
 * @version 3.3.2021
 */
public class AloitusIkkunaGUIController implements ModalControllerInterface<String> {
    
    @FXML
    private ImageView imageview;
    //label = new JLabel("");
    //Image images = new Image(this.getClass().getResource("/panu.png")).getImage();
    //label.setIcon(new ImageIcon(images));
    //private Image image1 = new Image(getClass().getResource("panu.png"));
    
    @FXML private TextField textVastaus;
    private String vastaus = null;

    
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(textVastaus);
    }


    @Override
    public String getResult() {
        return vastaus;
    }

    
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
        // TODO: lisää tähän kuvan lisäys jutut 
        // File kuva = new File(ohje.getKuvanPolku());
        // Image image = new Image(kuva.toURI().toString());
        // Kuva.setImage(image);
        
    }
    
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AloitusIkkunaGUIController.class.getResource("AloitusIkkunaGUIView.fxml"),
                "Palokunnankoulutusrekisteri",
                modalityStage, oletus);
    }
}