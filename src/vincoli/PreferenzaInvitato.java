package vincoli;

import database.ConnessioneDB;
import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class PreferenzaInvitato implements Vincolo {


    private final int numero_vincolati_vicini;
    private final int numero_vincolati_lontani;
    private Invitato invi;
    String vicino, lontano, ID_Inv, ID_Ev;
    private Evento evento;
    private ArrayList<Invitato> lista_vincolati_vicini = new ArrayList<Invitato>();
    private ArrayList<Invitato> lista_vincolati_lontani = new ArrayList<Invitato>();
    private ArrayList<Invitato> invitati;
    private ArrayList<Invitato> lista_vincolati;
    private ArrayList<Invitato> lista_vincolati_solo_duplicati_vicini;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati_vicini;
    private ArrayList<Invitato> lista_vincolati_solo_duplicati_lontani;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati_lontani;
    ConnessioneDB c;

    public PreferenzaInvitato(String ID_Ev, String ID_Inv, String vicino, String lontano) {

        c.startConn();
        this.ID_Ev = ID_Ev;
        //evento =
        this.ID_Inv= ID_Inv;
        this.vicino=vicino;
        this.lontano=lontano;
        invitati= c.getInvitato(ID_Ev);
        creaListaInvitatiLontani();
        creaListaInvitatiVicini();
        creaListaInvitati();
        this.numero_vincolati_vicini = lista_vincolati_vicini.size();
        this.numero_vincolati_lontani = lista_vincolati_lontani.size();
        evento.getLista_vincoli().add(this);
    }


    public ArrayList<Invitato> creaListaInvitati(){
        lista_vincolati.addAll(lista_vincolati_lontani);
        lista_vincolati.remove(prendiInvitati(ID_Inv));
        lista_vincolati.addAll(lista_vincolati_vicini);
        return lista_vincolati;
    }

    //Questo metodo crea la lista_vincolati_vicini.
    public ArrayList<Invitato> creaListaInvitatiVicini() {

        lista_vincolati_vicini.add(prendiInvitati(ID_Inv));

        String[] st = vicino.split(" ");

            for (int x = 0; x < st.length; x++)
            {
                lista_vincolati_vicini.add(prendiInvitati(st[x]));
            }
        return lista_vincolati_vicini;

    }

    //Questo metodo crea la lista_vincolati_lontani.
    public ArrayList<Invitato> creaListaInvitatiLontani(){

        lista_vincolati_lontani.add(prendiInvitati(ID_Inv));

        String[] st = lontano.split(" ");

        for (int x = 0; x < st.length; x++)
        {
            lista_vincolati_lontani.add(prendiInvitati(st[x]));
        }
        return lista_vincolati_lontani;

    }
    //Questo metodo prende l'esatto Invitato tramite il proprio ID_Inv.
    public Invitato prendiInvitati(String ID) {

        for (Invitato i : invitati) {
            if (ID == i.getID_Inv()) {
                this.invi = i;
                //invitati.remove(i);
            }
        }
        return this.invi;
    }

    public boolean getVerificaIdoneita(){
        return verificaIdoneita();
    }
    //Questo metodo verifica se le persone che vengono vincolate siano realmente presenti all'evento e se ci sono
    //altri vincoli che confutano questo.
    public boolean verificaIdoneita(){
        if (evento.getListaInvitati().containsAll(lista_vincolati) && controllaIncongruenzeLontani()==false && controllaIncongruenzeVicini()==false){

            return true;

        } else {

            return false;

        }
    }



    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiVicini(){

        for (int k=0; k < numero_vincolati_vicini;  k++){

            if (controllaSePresente(lista_vincolati_vicini.get(k))==true){

                smistaViciniSenzaDuplicati();
                break;

            } else  {
                smistaVicini();
                break;
            }
        }
    }

    //Questo metodo mette gli invitati vicino al primo tavolo disponibile.
    private void smistaVicini() {

        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            if (t.getDisponibile() && t.getNumPosti()>=lista_vincolati_vicini.size() && tavoliDispVincoloVicinanza()>1){
                t.addAllGuests(lista_vincolati_vicini);
                break;

            } else if ((t.getDisponibile()==false || t.getNumPosti()>=lista_vincolati_vicini.size()) && tavoliDispVincoloVicinanza()==1){
                System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo di vicinanza.\n");
                break;
            }
        }
    };

    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiLontani(){

        for (int k=0; k < numero_vincolati_lontani;  k++){

            if (controllaSePresente(lista_vincolati_lontani.get(k))==true){

                smistaLontaniSenzaDuplicati();
                break;

            } else  if ((k+1)==numero_vincolati_lontani && controllaSePresente(lista_vincolati_lontani.get(k))==false){
                smistaLontani();
                break;
            }
        }

    }

    ////Questo metodo mette gli invitati lontani al primo tavolo disponibile.
    private void smistaLontani() {

        int k = 0;
        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            if(t.getDisponibile() && (t.getPostiTot()-t.mostraInvitatiSeduti())>= 1 && k<lista_vincolati_lontani.size() && tavoliDispVincoloLontananza()>=lista_vincolati_lontani.size() ){
                t.addGuest(lista_vincolati_lontani.get(k));
                k++;

            } else if (tavoliDispVincoloLontananza()<lista_vincolati_lontani.size()){
                System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo di lontananza.\n");
                break;
            }
        }
    }

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di lontananza.
    public int tavoliDispVincoloLontananza(){
        int tavoliDispLont = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){
            if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())== 0)){
                tavoliDispLont--;
            }
        }
        return tavoliDispLont;
    }

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di lontananza, essendoci già person
    //sedute.
    public int tavoliDispVincoloLontananzaContandoDuplicati(){
        int tavoliDispLont = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){
            for (int n=0; n<lista_vincolati_lontani.size(); n++){

                if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())== 0) || t.getArraylistInvitati().contains(lista_vincolati_lontani.get(n))){
                    tavoliDispLont--;
                    break;
                }
            }
        }
        return tavoliDispLont;
    }

    //Questo metodo mette a sedere le persone lontane, considerando che alcuni sono già seduti a tavola.
    private void smistaLontaniSenzaDuplicati() {

        boolean non_possibile=false;

        for (Tavolo t: evento.getLocation().getTavoliLocale()){


            for (int n=0; n < lista_vincolati_lontani.size(); n++){

                if(!(t.getArraylistInvitati().contains(lista_vincolati_lontani.get(n))) && t.getDisponibile() && t.getNumPosti()>=1 && tavoliDispVincoloLontananzaContandoDuplicati()>=(numero_vincolati_lontani- creaListaDuplicatiLontani().size())){
                    t.addGuest(removeDuplicatiLontani().get(n));
                    removeDuplicatiLontani().remove(removeDuplicatiLontani().get(n));
                    break;

                } else if (tavoliDispVincoloLontananzaContandoDuplicati()<(numero_vincolati_lontani- creaListaDuplicatiLontani().size())){
                    System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo di lontananza.\n");
                    non_possibile=true;
                    break;
                }
            }

            if (non_possibile==true){break;}

        }

    };

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di vicinanza.
    public int tavoliDispVincoloVicinanza(){

        int tavoliDispVic = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){

            if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())<numero_vincolati_vicini)){
                tavoliDispVic--;
            }

        }
        return tavoliDispVic;

    }
    // Controlla se è già seduto ad un tavolo l'invitato
    public boolean controllaSePresente(Invitato i){
        boolean giaPresente = false;
        for (Tavolo t: evento.getLocation().getTavoliLocale()){
            if (t.getArraylistInvitati().contains(i)){
                giaPresente = true;
                break;
            } else { giaPresente = false;}
        }
        return giaPresente;
    }

    //Crea una lista di duplicati.
    public ArrayList<Invitato> creaListaDuplicatiVicini(){

        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            for (int k = 0; k < lista_vincolati_vicini.size(); k++){

                if (t.getArraylistInvitati().contains(lista_vincolati_vicini.get(k))) {

                    lista_vincolati_solo_duplicati_vicini.add(lista_vincolati_vicini.get(k));

                }
            }
        }
        return lista_vincolati_solo_duplicati_vicini;
    }

    //Crea una lista di duplicati.
    public ArrayList<Invitato> creaListaDuplicatiLontani(){

        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            for (int k = 0; k < lista_vincolati_lontani.size(); k++){

                if (t.getArraylistInvitati().contains(lista_vincolati_lontani.get(k))) {

                    lista_vincolati_solo_duplicati_lontani.add(lista_vincolati_lontani.get(k));

                }
            }
        }
        return lista_vincolati_solo_duplicati_lontani;
    }

    //Rimuove le persone già sedute a tavola, dalle persone da smistare.
    public ArrayList<Invitato> removeDuplicatiVicini(){

        lista_vincolati_vicini.removeAll(creaListaDuplicatiVicini());
        lista_vincolati_senza_duplicati_vicini.addAll(lista_vincolati_vicini);

        return lista_vincolati_senza_duplicati_vicini;

    }

    //Rimuove le persone già sedute a tavola, dalle persone da smistare.
    public ArrayList<Invitato> removeDuplicatiLontani(){

        lista_vincolati_lontani.removeAll(creaListaDuplicatiVicini());
        lista_vincolati_senza_duplicati_lontani.addAll(lista_vincolati_lontani);

        return lista_vincolati_senza_duplicati_lontani;


    }

    //Questo metodo mette a sedere le persone vicine, considerando che alcuni sono già seduti a tavola.
    public void smistaViciniSenzaDuplicati(){


        for (Tavolo t: evento.getLocation().getTavoliLocale()){


            for (int n=0; n < lista_vincolati_vicini.size(); n++){

                if(t.getArraylistInvitati().contains(lista_vincolati_vicini.get(n)) && t.getDisponibile() && t.getNumPosti()>=(numero_vincolati_vicini-creaListaDuplicatiVicini().size())){
                    t.addAllGuests(removeDuplicatiVicini());
                    break;

                } else if (t.getArraylistInvitati().contains(lista_vincolati_vicini.get(n)) && (t.getDisponibile()== false) || t.getNumPosti()<(numero_vincolati_vicini- creaListaDuplicatiVicini().size())){
                    System.out.println("Gli invitati:\n "+ getNomeVincolatiVicini() + "non possono essere posizionati secondo il vincolo di vicinanza.\n");
                    break;
                }
            }

        }

    }


    //Stampa il nome di tutti i vincolati presenti nella lista.
    public String getNomeVincolatiVicini() {
        String a = "";

        for (Invitato i : lista_vincolati_vicini){
            a += i.getNome()+ i.getCognome() + "\n";
        }

        return a;
    }

    public String getNomeVincolatiLontani() {
        String a = "";

        for (Invitato i : lista_vincolati_lontani){
            a += i.getNome()+ i.getCognome() + "\n";
        }

        return a;
    }

    public String getNomeVincolati() {
        String a = "";

        for (Invitato i : lista_vincolati){
            a += i.getNome()+ i.getCognome() + "\n";
        }

        return a;
    }

    public ArrayList<Invitato> getListaVincolatiVicini() {
        return lista_vincolati_vicini;
    }
    public ArrayList<Invitato> getListaVincolati() {
        return lista_vincolati;
    }
    public ArrayList<Invitato> getListaVincolatiLontani() {
        return lista_vincolati_lontani;
    }

    //Questo metodo controlla se ci sono o meno vincoli che vanno a confutare un altro vincolo.
    public boolean controllaIncongruenzeVicini() {

        boolean incongruente=false;
        int max_accettabile=2;

        for (Vincolo v : evento.getLista_vincoli()){

            for (int k=0; k<lista_vincolati_vicini.size(); k++){

                if (v instanceof PreferenzaInvitato && ((PreferenzaInvitato) v).getListaVincolatiLontani().contains(lista_vincolati_vicini.get(k)) && max_accettabile>0){

                    max_accettabile--;

                    if (max_accettabile==0){

                        incongruente=true;
                        break;
                    }

                }
            }

            if (incongruente==true){break;} else {max_accettabile=2;}

        }

        return incongruente;
    }

    //Questo metodo controlla se ci sono o meno vincoli che vanno a confutare un altro vincolo.
    public boolean controllaIncongruenzeLontani() {

        boolean incongruente=false;
        int max_accettabile=2;

        for (Vincolo v : evento.getLista_vincoli()){

            for (int k=0; k<lista_vincolati_lontani.size(); k++){

                if (v instanceof PreferenzaInvitato && ((PreferenzaInvitato) v).getListaVincolatiVicini().contains(lista_vincolati_lontani.get(k)) && max_accettabile>0){

                    max_accettabile--;

                    if (max_accettabile==0){

                        incongruente=true;
                        break;
                    }

                }
            }

            if (incongruente==true){break;} else {max_accettabile=2;}

        }

        return incongruente;
    }


    public String ID_Inv(){
        return ID_Inv;
    }

    public String ID_Ev(){
        return ID_Ev;}


    public String getVicino(){
        return this.vicino;
    }

    public String getLontano(){
        return this.lontano;
    }
}