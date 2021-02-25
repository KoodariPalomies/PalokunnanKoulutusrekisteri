package palokunnanKoulutusrekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Käsitellään koulutusten muokkausikkunan tapahtumat
 * @author mitulint
 * @version 19.2.2021
 *
 */
public class MuokkaaKoulutustaDialogController implements ModalControllerInterface<String> {
    
    @FXML private Button buttonTallenna;
    
    @FXML private void handleDefaultOK() {
        ModalController.closeStage(buttonTallenna);
    }
    
    
    @FXML private Button buttonSulje;
    
    @FXML private void handleDefaultCancel() {
        ModalController.closeStage(buttonSulje);
    }

    
    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    
    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
    }
    
}
