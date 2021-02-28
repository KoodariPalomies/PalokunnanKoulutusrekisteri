package palokunnanKoulutusrekisteri;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import koulutusRekisteri.Koulutusrekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma Palokunnan koulutusrekisteri -ohjelman käynnistämiseksi
 * @author mitulint
 * @version 18.2.2021
 */
public class KoulutusrekisteriMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("KoulutusrekisteriGUIView.fxml"));
			//Scene scene = new Scene(root);
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KoulutusrekisteriGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final KoulutusrekisteriGUIController koulutusrekisteriCtrl = (KoulutusrekisteriGUIController)ldr.getController();

			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Koulutusrekisteri.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Palokunnan koulutusrekisteri");
			//primaryStage.show();
			
            primaryStage.setOnCloseRequest((event) -> {
                if ( !koulutusrekisteriCtrl.voikoSulkea() ) event.consume();
            });
			
			Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
			koulutusrekisteriCtrl.setKoulutusrekisteri(koulutusrekisteri);
			
            primaryStage.show();
            if ( !koulutusrekisteriCtrl.avaa() ) Platform.exit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
    /**
     * Käynnistetään käyttöliittymä 
     * @param args komentorivin parametrit
     */
	public static void main(String[] args) {
		launch(args);
	}
}
