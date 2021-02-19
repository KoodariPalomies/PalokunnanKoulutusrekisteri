package palokunnanKoulutusrekisteri;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("KoulutusrekisteriGUIView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Koulutusrekisteri.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Palokunnan koulutusrekisteri");
			primaryStage.show();
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
