package palokunnanKoulutusrekisteri;

//import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Tulostuksen hoitava luokka
 * 
 * @author mitulint
 * @version 18.2.2021
 */
public class TulostusController implements ModalControllerInterface<String> {
    @FXML TextArea tulostusAlue;
    
    @FXML private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }
    
    
    @FXML private Button buttonSulje;
    
    @FXML private void handleDefaultCancel() {
        ModalController.closeStage(buttonSulje);
    }

    
    @FXML private Button buttonTulosta;
    
    @FXML private void handleTulosta() {
        ModalController.closeStage(buttonTulosta);
    }

    
    @Override
    public String getResult() {
        return null;
    } 

    
    @Override
    public void setDefault(String oletus) {
        if ( oletus == null ) return;
        tulostusAlue.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        //
    }
    
    
          /**
           * @return alue johon tulostetaan
           */
          public TextArea getTextArea() {
              return tulostusAlue;
          }
    
    
                /**
                 * Näyttää tulostusalueessa tekstin
                 * @param tulostus tulostettava teksti
                 * @return kontrolleri, jolta voidaan pyytää lisää tietoa
                 */
                public static TulostusController tulosta(String tulostus) {
                    TulostusController tulostusCtrl = 
                      ModalController.showModeless(TulostusController.class.getResource("TulostusView.fxml"),
                                                   "Tulostus", tulostus);
                    return tulostusCtrl;
                }

}
