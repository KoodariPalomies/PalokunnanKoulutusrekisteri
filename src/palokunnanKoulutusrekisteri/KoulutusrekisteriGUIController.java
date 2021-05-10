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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
 * @version 24.3.2021
 */
public class KoulutusrekisteriGUIController implements Initializable {
    
    @FXML private TextField                 hakuehto;
    @FXML private ComboBoxChooser<String>   cbKentat;
    //@FXML private Label                     labelVirhe;
    @FXML private ListChooser<Tyontekija>   chooserTyontekijat;
    @FXML private ScrollPane                panelTyontekija;
    @FXML private ListChooser<Koulutus>     chooserKoulutukset;
    @FXML private ScrollPane                panelKoulutus;
    
    
    /**
     * @param url ei tietoa
     * @param bundle ei tietoa
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle ) {
        alusta();
        }
    
    
    @FXML private void handleHakuehto() {
         if ( tyontekijaKohdalla != null )
             hae(tyontekijaKohdalla.getTyontekijaTunnus()); 

    }

    
    /**
     * Käsitellään uuden työntekijän lisääminen
     */
    @FXML private void handleUusiTyontekija() {
        uusiTyontekija();
    }
    
    
    /**
     * Käsitellään työntekijän muokkaaminen
     */
    @FXML private void handleMuokkaaTyontekija() {
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("TyontekijaDialogView.fxml"), "Muokkaa työntekijää", null, "");
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
     */
    @FXML private void handleAvaa() {
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
        uusiKoulutus();
    }
    
    /**
     * Käsitellään työntekijän koulutustietojen lisäämisen koulutusrekisteriin
     */
    @FXML private void handleLisaaTyontekijalleKoulutus() {
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
        Dialogs.showQuestionDialog("Poista koulutus", "Poistetaanko työntekijän koulutus?", "Kyllä", "Ei");
        ModalController.showModal(KoulutusrekisteriGUIController.class.getResource("PoistaKoulutusGUIView.fxml"), "Poista koulutus", null, "");
    }
    
    
    /**
     * Käsitellään apujen hakeminen
     * TODO: lukeminen tiedostosta tai selaimessa
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
    
    private String kayttajatunnus   = "";
    //private String salasana         = "";
    private Koulutusrekisteri   koulutusrekisteri;
    private Tyontekija          tyontekijaKohdalla;
    private Koulutus            koulutusKohdalla;
    private TextArea            areaTyontekija      = new TextArea();   // TODO: poista lopuksi
    private TextArea            areaKoulutus        = new TextArea();   // TODO: poista lopuksi (tämä lisätty, kun koulutukset eivät tulostuneet!)
    //=================== Alla olevat tullee TextAre juttujen tilalle! ====================
    // private TextField edits[];
    // private TextField editsPaa[];
    //=====================================================================================
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaa tulostaa työntekijän ja koulutuksen tiedot.
     * Alustetaan myös työntekijälistan ja koulutuslistan kuuntelijat
     */
    private void alusta() {
        panelTyontekija.setContent(areaTyontekija);
        areaTyontekija.setFont(new Font("Courier New", 12));
        panelTyontekija.setFitToHeight(true);
        
        panelKoulutus.setContent(areaKoulutus);             // tekee TextArean koulutuksille
        areaKoulutus.setFont(new Font("Courier New", 12));
        panelKoulutus.setFitToHeight(true);
        
        chooserTyontekijat.clear();
        chooserTyontekijat.addSelectionListener(e -> naytaTyontekija());
        
        //naytaKoulutus();
        chooserKoulutukset.clear();             //tyhjentää chooserin
        chooserKoulutukset.addSelectionListener(e -> naytaKoulutustieto());
        //================= Alla olevat tullee tilalle! =======================================
        // resultTeos.clear();
        // resultTeos.addSelectionListener(e -> naytaTeos());
        // edits = new TextField[] {snimi, enimi, tnimi, alknimi, kieli, pvuosi, alkvuosi};
        // editsPaa = new TextField[] {nimi, tnimi, alknimi, kieli, pvuosi, alkvuosi, status};
        //=====================================================================================
    }
    
   /* 
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
    */
    
    @SuppressWarnings("unused")
    private void setTitle(String title) {
        //ModalController.getStage(hakuehto).setTitle(title);
    }
    
    //====================== Tässä alemmassa saattaa olla jotain pielessä! ========================
    /**
     * Alustaa koulutusrekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta työntekijän tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
           kayttajatunnus = nimi;
           setTitle("Palokunnankoulutusrekisteri - " + kayttajatunnus);
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
     */
    public boolean avaa() {
        String uusinimi = AloitusIkkunaGUIController.kysyNimi(null, kayttajatunnus);
        System.out.println(uusinimi + "Jee!");
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        naytaTyontekija();
        naytaKoulutus();
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
     * Näyttää listasta valitun työntekijän tiedot listChooseriin ja tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaTyontekija() {
        tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        
        if (tyontekijaKohdalla == null) {
            areaTyontekija.clear();
            return;
        }
        
        areaTyontekija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tulosta(os, tyontekijaKohdalla);
        }
    }
//======================= Välissä oleva tulisi korvata nykyiset rävellykset ==============================
    /**
     * Näyttää valitun teoksen tiedot tekstikentissä.
     
    protected void naytaTeos(){
        teosKohdalla = resultTeos.getSelectedObject();
        if(teosKohdalla == null) return;
        naytaTeos(editsPaa, teosKohdalla);
    }
    */
    
    /**
     * Näyttää teoksen tiedot pääikkunan tekstikentissä.
     * @param edit taulukko tekstikentissä
     * @param teos näytettävä teos
     
    public void naytaTeos(TextField[] edit, Teos teos) { 
        if (teos == null) return;
        
        edit[0].setText(kirjahylly.getTekijanNimi(teos.getTekijaID()));
        edit[1].setText(teos.getTeoksenNimi());
        edit[2].setText(teos.getalknimiString());
        edit[3].setText(teos.getKieli());
        edit[4].setText(teos.getpvuosiString());
        edit[5].setText(teos.getalkvuosiString());
        edit[6].setText(kirjahylly.getStatusNimi(teos.getStatusID()));
        
    }
    */
    
    /**
     * Poistaa valitun teoksen, ellei valinta ole null.
     
    protected void poistateos(){ 
        //teos = resultTeos.getSelectedObject();
        if (teosKohdalla == null) return;
        int id = kirjahylly.poistaTeos(teosKohdalla); // tï¿½mï¿½ palauttaa 0 tai tekijï¿½n id:n
        
        if (id !=0){
            kirjahylly.poistaTekija(id); 
        }
        hae(0);
    }
    */
//=========================================================================================================
    /**
     * Näyttää listasta valitun koulutuksen tiedot, tilapäisesti yhteen isoon edit kenttään
     */
    private void naytaKoulutustieto() {
        koulutusKohdalla = chooserKoulutukset.getSelectedObject();
        
        if (koulutusKohdalla == null) {
            areaKoulutus.clear();
            return;
        }
        
        areaKoulutus.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKoulutus)) {
            tulosta(os, koulutusKohdalla);
        }
    }
    
    
    /**
     * Näyttää listasta valitun koulutuksen tiedot listChooseriin
     */
    private void naytaKoulutus() {
        chooserKoulutukset.clear();

        for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
            Koulutus koulutus = koulutusrekisteri.annaKoulutus(i);
            chooserKoulutukset.add(koulutus.getKoulutus(), koulutus);
            }
    } 
    
    
    /**
     * Näyttää listasta valitun työntekijän koulutukset, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaTyontekijanKoulutukset() {
        tyontekijaKohdalla = chooserTyontekijat.getSelectedObject();
        //koulutusKohdalla = chooserKoulutukset.getSelectedObject();
        
        if (tyontekijaKohdalla == null) {
            areaTyontekija.clear();
            return;
        }
        
        areaTyontekija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tulosta(os, tyontekijaKohdalla);
        }
    }

    
    /**
     * 
     * @param tnro 
     */
    private void hae(int tnro) {
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        //if (k > 0 || ehto.length() > 0)
            //naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k, ehto));
      //  else
           // naytaVirhe(null);
        
        chooserTyontekijat.clear();
        
        int index = 0;
        Collection<Tyontekija> tyontekijat;
        try {
            tyontekijat = koulutusrekisteri.etsiTyontekija(ehto, k);
            int i = 0;
            for (Tyontekija tyontekija:tyontekijat) {
                if (tyontekija.getTyontekijaTunnus() == tnro) index = i;
                chooserTyontekijat.add(tyontekija);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Työntekijän hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserTyontekijat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää työntekijän
    }
    
    
    /**
     * Lisätään rekisteriin uusi työntekijä
     */
    public void uusiTyontekija() {
        try {
            Tyontekija uusi = new Tyontekija();
            uusi.rekisteroi();
            uusi.vastaaAkuAnkka();        // TODO: korvaa dialogilla aikanaan
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
            hae(koul.getKoulutusTunnus());
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
            //naytaTyontekijanKoulutukset();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }

    }
    
    
    /**
     * Asetetaan käytettävä koulutusrekisteri
     * @param koulutusrekisteri jota käytetään
     */
    public void setKoulutusrekisteri(Koulutusrekisteri koulutusrekisteri) {
        this.koulutusrekisteri = koulutusrekisteri;
        //naytaTyontekija();
        //naytaKoulutus();
        //naytaTyontekijanKoulutukset();
        //============== naytaTeos(editsPaa, teosKohdalla);
    }
    
    
         /**
          * Tulostaa työntekijän tiedot
          * @param os tietovirta johon tulostetaan
          * @param tyontekija tulostettava työntekijä
          */
         public void tulosta(PrintStream os, final Tyontekija tyontekija) {
             os.println("----------------------------------------------");
             tyontekija.tulosta(os);
             os.println("----------------------------------------------");
             
             List<Relaatio> relaatio2 = koulutusrekisteri.annaRelaatiot(tyontekija.getTyontekijaTunnus());
             for (Relaatio relaatio : relaatio2) {
                 
                 relaatio.tulosta(os);
             }

         }
         
         
         /**
          * Tulostaa koulutuksen tiedot
          * @param os tietovirta johon tulostetaan
          * @param koulutus tulostettava koulutus
          */
         private void tulosta(PrintStream os, final Koulutus koulutus) {
             os.println("----------------------------------------------");
             koulutus.tulosta(os);
             os.println("----------------------------------------------");
             
             for (int i = 0; i < koulutusrekisteri.getKoulutuksia(); i++) {
                 Koulutus koulutus2 = koulutusrekisteri.annaKoulutus(i);
                 koulutus2.tulosta(os);
             }
         }
        
        
         /**
          * Tulostaa listassa olevat työntekijät tekstialueeseen
          * @param text alue johon tulostetaan
          * @throws SailoException jos menee perseelleen
         
         public void tulostaValitut(TextArea text) throws SailoException {
             try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
                 os.println("Tulostetaan kaikki työntekijät");
                 for (int i = 0; i < koulutusrekisteri.getRelaatiot(); i++) {
                     Relaatio relaatio = koulutusrekisteri.annaRelaatiot(i);
                     try {
                        tulosta(os, relaatio);
                        os.println("\n\n");
                    } catch (SailoException ex) {
                        Dialogs.showMessageDialog("Työntekijän koulutusten tulostamisessa ongelmia! " + ex.getMessage());
                    }
                 }
              }
          }  */
}