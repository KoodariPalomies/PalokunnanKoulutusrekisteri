package palokunnanKoulutusrekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import koulutusRekisteri.Koulutus;
import koulutusRekisteri.Koulutusrekisteri;
import koulutusRekisteri.Relaatio;
import koulutusRekisteri.SailoException;
import koulutusRekisteri.Tyontekija;
import koulutusRekisteri.Tyontekijat;

/**
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author mitulint
 * @version 1.0, 24.3.2021  / Huono versionhallinta...
 * @version 1.1, 12.5.2021  / HT7 muokkailuja --> TextFieldien lisääminen ja paneelien poisto
 * @version 1.2, 13.5.2021  / Lisäys naytaTyontekijanKoulutukset()
 * @version 1.3, 14.5.2021  / Lisäsin handleLisaaKoulutus() ja handleUusiTyontekija() Dialogit + muokkaa() tiedoston loppuun 
 * @version 1.4, 14.5.2021  / Lisätty haeKoulutus() --> jotta chooserKoulutukset päivittyy
 * @version 1.5, 14.5.2021  / Lisätty naytaVirhe() --> jos tulee virhe
 */
public class KoulutusrekisteriGUIController implements Initializable {
    
    @FXML private TextField                 hakuehto;
    @FXML private ComboBoxChooser<String>   cbKentat;
    @FXML private Label                     labelVirhe;
    @FXML private ListChooser<Tyontekija>   chooserTyontekijat;
    @FXML private ListChooser<Koulutus>     chooserKoulutukset;
    @FXML private ListChooser<Relaatio>     chooserTyontekijanKoulutukset;
    @FXML private TextField                 nimi;
    @FXML private TextField                 tyontekijatunnus;
    @FXML private TextField                 tehtavaalue;
    @FXML private TextField                 virkaasema;
    
    
    /**
     * @param url ei tietoa
     * @param bundle ei tietoa
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle ) {
        alusta();
        }
    
    
    /**
     * Käsitellään haun tekeminen
     */
    @FXML private void handleHakuehto() {
         hae(0);
    }

    
    /**
     * Käsitellään uuden työntekijän lisääminen
     */
    @FXML private void handleUusiTyontekija() {
        Dialogs.showQuestionDialog("Lisää työntekijä", "Lisätäänkö työntekijä?", "Kyllä", "Ei");
        uusiTyontekija();
    }
    
    
    /**
     * Käsitellään työntekijän muokkaaminen
     */
    @FXML private void handleMuokkaaTyontekija() {
        muokkaa(kentta);
    }
    
    
    /**
     * Käsitellään työntekijän poistaminen
     */
    @FXML private void handlePoistaTyontekija() {
        Dialogs.showQuestionDialog("Poista työntekijä", "Poistetaanko työntekijä?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("PoistoGUIView.fxml"), "Poista työntekijä", null, "");
    }
    
    
    /**
     * Käsitellään tietojen avaaminen
     * @throws SailoException jos menee pieleen
     */
    @FXML private void handleAvaa() throws SailoException {
        avaa();
    }


    /**
     * Käsitellään valittujen asioiden tulostaminen
     * @throws SailoException jos menee perseelleen
     */
    @FXML private void handleTulosta() throws SailoException {
        //TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        //tulostaValitut(tulostusCtrl.getTextArea()); 
    }

    
    /**
     * Käsitellään tietojen tallentaminen
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään toiminnan lopettaminen
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    /**
     * Käsitellään uuden koulutuksen lisääminen
     */
    @FXML private void handleLisaaKoulutus() {
        Dialogs.showQuestionDialog("Lisää koulutus", "Lisätäänkö koulutus?", "Kyllä", "Ei");
        uusiKoulutus();
    }
    
    
    /**
     * Käsitellään työntekijän koulutustietojen lisäämisen koulutusrekisteriin
     */
    @FXML private void handleLisaaTyontekijalleKoulutus() {
        Dialogs.showQuestionDialog("Lisää työntekijälle koulutus", "Lisätäänkö koulutus työntekijälle?", "Kyllä", "Ei");
        lisaaTyontekijalleKoulutus();
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
        Dialogs.showQuestionDialog("Poista koulutus", "Poistetaanko koulutus?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("PoistaKoulutusGUIView.fxml"), "Poista koulutus", null, "");
    }
    
    
    /**
     * Käsitellään työntekijän koulutuksen poistaminen
     */
    @FXML private void handlePoistaTyontekijanKoulutus() {
        Dialogs.showQuestionDialog("Poista koulutus", "Poistetaanko työntekijän koulutus?", "Kyllä", "Ei");
        poistaTyontekijanKoulutus1();
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
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }

//==============================================================================================================================
// Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private String              kayttajatunnus= "";
    private Koulutusrekisteri   koulutusrekisteri;
    private Tyontekija          tyontekijaKohdalla;
    private Koulutus            koulutusKohdalla;
    private Relaatio            relaatioKohdalla;
    private TextField           tyontekijaTiedot[];
    private int                 kentta = 0;
    
    
    /**
     * Alustetaan työntekijä-, koulutus- ja työntekijän koulutukset-listojen kuuntelijat.
     */
    private void alusta() {
        chooserTyontekijat.clear();
        chooserTyontekijat.addSelectionListener(e -> naytaTyontekija());
        chooserTyontekijat.addSelectionListener(e -> {
            try {
                naytaTyontekijanKoulutukset();
            } catch (SailoException e1) {
                e1.printStackTrace();
            }
        });
        tyontekijaTiedot = new TextField[] {nimi, tyontekijatunnus, tehtavaalue, virkaasema};
        
        chooserKoulutukset.clear();
        chooserKoulutukset.addSelectionListener(e -> valitseKoulutus());
        //haeKoulutus(0);
        
        chooserTyontekijanKoulutukset.clear();
        chooserTyontekijanKoulutukset.addSelectionListener(e -> valitseTyontekijanKoulutus());
        
        for (TextField edit: tyontekijaTiedot)  
            if (edit != null) {  
                edit.setEditable(true);
            } 
    }
    
    
    /**
     * Valitsee työntekijän koulutuksen
     */
   private void valitseTyontekijanKoulutus() {
        relaatioKohdalla = chooserTyontekijanKoulutukset.getSelectedObject();
    }
   
   
   /**
    * Valitsee koulutuksen, joka voidaan sitten lisätä työntekijälle
    */
   private void valitseKoulutus() {
       koulutusKohdalla = chooserKoulutukset.getSelectedObject();
   }

   
    /**
     * Kertoo käyttäjälle virheestä
     * @param virhe virheilmoitus
     */
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
     * Alustaa koulutusrekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta työntekijän tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
           kayttajatunnus = nimi;
           //setTitle("Palokunnankoulutusrekisteri - " + kayttajatunnus);
           try {
               koulutusrekisteri.lueTiedostosta(nimi);
               hae(0);
               return null;
           } catch (SailoException e) {
               hae(0);
               String virhe = e.getMessage(); 
               if ( virhe != null ) Dialogs.showMessageDialog(virhe);
               return virhe;
           }
    }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     * @throws SailoException jos menee pieleen
     */
    public boolean avaa() throws SailoException {
        String uusinimi = AloitusIkkunaGUIController.kysyNimi(null, kayttajatunnus);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        naytaTyontekija();
        naytaKoulutus();
        haeKoulutus(0);
        naytaTyontekijanKoulutukset();
        return true;
    }
    
    
    /**
     * Tietojen tallennus tiedostoon.
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            koulutusrekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
        
    
    /**
     * Näyttää listasta valitun työntekijän tiedot listChooseriin.
     */
    private void naytaTyontekija() {
        tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        if (tyontekijaKohdalla == null) return;
            naytaTyontekija(tyontekijaTiedot, tyontekijaKohdalla);
    }
    
    
    /**
     * Näyttää valitun työntekijän tiedot pääikkunan tekstikentissä.
     * @param edit taulukko tekstikentistä
     * @param tyontekija näytettävä työntekijä
     */
    public void naytaTyontekija(TextField[] edit, Tyontekija tyontekija) {
        if (tyontekija == null) return;
        edit[0].setText(tyontekija.getNimi());
        edit[1].setText(tyontekija.getTyontekijaTunnusString());
        edit[2].setText(tyontekija.getTehtavaAlue());
        edit[3].setText(tyontekija.getVirkaAsema());
    }
    
    
    /**
     * Näyttää listasta koulutuksien tiedot listChooseriin
     * @throws SailoException jos menee pieleen
     */
    private void naytaKoulutus() throws SailoException {
        chooserKoulutukset.clear();
        
        if (koulutusKohdalla == null) return;
        List<Koulutus> koulutus = koulutusrekisteri.annaKoulutus(koulutusKohdalla.getKoulutusTunnus());
        
        for (int i = 0; i < koulutus.size(); i++) {
            Koulutus koul = koulutus.get(i);
            koul.getKoulutusTunnus();
            koulutusrekisteri.annaKoulutus(i);
            chooserKoulutukset.add(koul.getKoulutus(), koul);
            }
    } 
    
    
    /**
     * Näyttää listasta valitun työntekijän koulutukset
     * @throws SailoException jos menee pieleen
     */
    private void naytaTyontekijanKoulutukset() throws SailoException {
        chooserTyontekijanKoulutukset.clear();
        
        if (tyontekijaKohdalla == null) return;
        List<Relaatio> relaatio = koulutusrekisteri.annaRelaatiot(tyontekijaKohdalla.getTyontekijaTunnus());
        
        for (int i = 0; i < relaatio.size(); i++) {
            Relaatio rel = relaatio.get(i);
            rel.getKoulutusTunnus();
            koulutusrekisteri.annaKoulutus(i);
            rel.getSuoritettu();
            rel.getUmpeutuu();
            chooserTyontekijanKoulutukset.add(rel.getKoulutusTunnusString(), rel);
            }
    }
    
    
    /**
     * Poistetaan relaatiotaulukosta valitulla kohdalla oleva relaatio, eli työntekijän koulutus.
     */
    private void poistaTyontekijanKoulutus1() {
        if (relaatioKohdalla == null) return;
        int id = koulutusrekisteri.poistaTyontekijanKoulutus(relaatioKohdalla);
        if (id != 0);
    }

    
    /**
     * Hakee työntekijöiden tiedot listaan
     * @param tnro työntekijän numero, joka aktivoidaan haun jälkeen
     */
    private void hae(int tnro) {
        int tnr = tnro;     // tnr työntekijän numero, joka aktivoituu haun jälkeen
        if (tnr <= 0) {
            Tyontekija kohdalla = tyontekijaKohdalla;
            if (kohdalla != null) tnr = kohdalla.getTyontekijaTunnus();
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        
        chooserTyontekijat.clear();
        
        int index = 0;
        Collection<Tyontekija> tyontekijat;
        try {
            tyontekijat = koulutusrekisteri.etsiTyontekija(ehto, k);
            int i = 0;
            for (Tyontekija tyontekija:tyontekijat) {
                if (tyontekija.getTyontekijaTunnus() == tnr) index = i;
                chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Työntekijän hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserTyontekijat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää työntekijän
    }
    
    
    /**
     * Hakee koulutusten tiedot listaan
     * @param knro koulutuksen numero, joka aktivoidaan haun jälkeen
     */
    private void haeKoulutus(int knro) {
        int knr = knro;     // knr koulutuksen numero, joka aktivoituu haun jälkeen
        if (knr <= 0) {
            Koulutus kohdalla = koulutusKohdalla;
            if (kohdalla != null) knr = kohdalla.getKoulutusTunnus();
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        
        chooserKoulutukset.clear();
        
        int index = 0;
        Collection<Koulutus> koulutukset;
        try {
            koulutukset = koulutusrekisteri.etsiKoulutus(ehto, k);
            int i = 0;
            for (Koulutus koulutus:koulutukset) {
                if (koulutus.getKoulutusTunnus() == knr) index = i;
                chooserKoulutukset.add(koulutus);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Työntekijän hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserKoulutukset.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää koulutuksen
    }
    
    
    /**
     * Lisätään rekisteriin uusi työntekijä
     */
    public void uusiTyontekija() {
        try {
            Tyontekija uusi = new Tyontekija();
            uusi.rekisteroi();
            uusi.vastaaAkuAnkka();
            koulutusrekisteri.lisaa(uusi);
            hae(uusi.getTyontekijaTunnus());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }
        
    }
    
    
    /**
     * Lisätään rekisteriin uusi koulutus
     */ 
    public void uusiKoulutus() { 
        try {
            Koulutus koul = new Koulutus(); 
            koul.rekisteroi(); 
            koul.vastaaVesisukeltaja(); 
            koulutusrekisteri.lisaa(koul);
            haeKoulutus(koul.getKoulutusTunnus());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        } 

    }
    
    
    /**
     * Lisätään koulutusrekisteriin työntekijän koulutustiedot
     */
    public void lisaaTyontekijalleKoulutus() {
        if ( tyontekijaKohdalla == null ) return; 
        if ( koulutusKohdalla == null ) return;
        
        try {
            Relaatio rel = new Relaatio(tyontekijaKohdalla.getTyontekijaTunnus(), koulutusKohdalla.getKoulutusTunnus());
            rel.rekisteroi();
            rel.vastaaRelaatio();
            koulutusrekisteri.lisaa(rel);
            hae(rel.getRelaatioTunnus());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }

    }
    
    
    /**
     * Asetetaan käytettävä koulutusrekisteri
     * @param koulutusrekisteri jota käytetään
     * @throws SailoException jos menee pieleen
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) throws SailoException {
        this.koulutusrekisteri = koulutusrekisteri;
        naytaTyontekija(tyontekijaTiedot, tyontekijaKohdalla);
        naytaKoulutus();
        naytaTyontekijanKoulutukset();
    }
    
    
         /**
          * Tulostaa työntekijän tiedot
          * @param os tietovirta johon tulostetaan
          * @param tyontekija tulostettava työntekijä
          */
         public void tulosta(PrintStream os, final Tyontekija tyontekija) {
             os.println("-------------------------");
             
             List<Relaatio> relaatio2 = koulutusrekisteri.annaRelaatiot(tyontekija.getTyontekijaTunnus());
             for (Relaatio relaatio : relaatio2) {
                 relaatio.tulosta(os);
                 
             os.println("-------------------------");
             }
         }
         
         
         /**
          * Tulostaa koulutuksen tiedot
          * @param os tietovirta johon tulostetaan
          * @param koulutus tulostettava koulutus
          */
         @SuppressWarnings("unused")
        private void tulosta(PrintStream os, final Koulutus koulutus) {
             os.println("----------------------------------------------");
             koulutus.tulosta(os);
             os.println("----------------------------------------------");
             /*
             for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
                 Koulutus koulutus2 = koulutusrekisteri.annaKoulutus(i);
                 koulutus2.tulosta(os);
             }
             */
             try {
                 List<Koulutus> koulutukset = koulutusrekisteri.annaKoulutus(0);
                 for (Koulutus koul : koulutukset) 
                     koul.tulosta(os);     
             } catch (SailoException ex) {
                 Dialogs.showMessageDialog("Harrastusten hakemisessa ongelmia! " + ex.getMessage());
             } 

         }
         
         
         /**
          * Aliohjelma, jolla muokataan työntekijän tietoja
          * @param k muokattava TextField
          *
          */
         private void muokkaa(int k) {
             if (tyontekijaKohdalla == null) return;
             try {
                 Tyontekija tyontekija = new Tyontekija();
                 tyontekija = tyontekijaKohdalla.clone();
                 tyontekija.setNimi(tyontekijaTiedot[0].getText());
                 tyontekija.setTehtavaAlue(tyontekijaTiedot[2].getText());
                 tyontekija.setVirkaAsema(tyontekijaTiedot[3].getText());
                 koulutusrekisteri.korvaaTaiLisaa(tyontekija);
                 hae(tyontekija.getTyontekijaTunnus());
             } catch (CloneNotSupportedException e) {
                 //
             } catch (SailoException e) {
                 Dialogs.showMessageDialog(e.getMessage());
             }
             //hae(0);
         }          
}