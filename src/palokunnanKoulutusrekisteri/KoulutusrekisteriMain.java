package palokunnanKoulutusrekisteri;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import koulutusRekisteri.Koulutusrekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma Palokunnan koulutusrekisteri -ohjelman käynnistämiseksi
 * @author mitulint, tuomas.mikko.lintula@gmail.com
 * @version 1.0, 21.4.2021  / Koska huonosti pidetty versionhallinta...
 * @version 1.1, 13.5.2021  / Source --> Format korjaukset
 * @version 1.2, 20.5.2021  / Poistettu kanta -paketti, koska tein oikeellisuustarkistuksen suoraan alkuperäisiin java-luokkiin
 */
public class KoulutusrekisteriMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(
                    getClass().getResource("KoulutusrekisteriGUIView.fxml"));
            final Pane root = (Pane) ldr.load();
            final KoulutusrekisteriGUIController koulutusrekisteriCtrl = (KoulutusrekisteriGUIController) ldr
                    .getController();

            Koulutusrekisteri koulutusrekisteri = new Koulutusrekisteri();
            koulutusrekisteriCtrl.setKoulutusrekisteri(koulutusrekisteri);

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass()
                    .getResource("Koulutusrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Palokunnankoulutusrekisteri");

            primaryStage.setOnCloseRequest((event) -> {
                if (!koulutusrekisteriCtrl.voikoSulkea())
                    event.consume();
            });

            primaryStage.show();
            koulutusrekisteriCtrl.avaa();
            if (!koulutusrekisteriCtrl.avaa())
                Platform.exit();

        } catch (Exception e) {
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
