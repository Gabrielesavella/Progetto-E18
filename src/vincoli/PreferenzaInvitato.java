package vincoli;

import database.ConnessioneDB;
import locale.Evento;
import locale.GestoreEvento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;
import java.util.Collections;

public class PreferenzaInvitato{

    private ConnessioneDB c = new ConnessioneDB();
    private ArrayList<Invitato> invitati;
    private ArrayList<GestoreEvento> eventi;
    private String ID_Ev, ID_Inv,vicino, lontano;
    private GestoreEvento gestoreEvento;
    private ArrayList<Invitato> lista_vincolati_vicini = new ArrayList<>();
    private ArrayList<Invitato> lista_vincolati_lontani = new ArrayList<>();
    private Evento evento;
    private ArrayList<Tavolo> tavoli;
    private ArrayList<Invitato> lista_vicini = new ArrayList<>();
    private ArrayList<Invitato> lista_lontani = new ArrayList<>();
    private ArrayList<Invitato> daSistemare = new ArrayList<>();
    private ArrayList<Invitato> lista_vincolati_solo_duplicati = new ArrayList<>();

    public PreferenzaInvitato(String ID_Ev,String ID_Inv,String vicino,String lontano){

        this.ID_Ev=ID_Ev;
        this.ID_Inv=ID_Inv;
        this.vicino=vicino;
        this.lontano=lontano;
        //evento=null;
        invitati = new ArrayList<>();
        tavoli = new ArrayList<>();

    }

    public void generaListe(){
        creaListaInvitatiVicini(vicino);
        creaListaInvitatiLontani(lontano);
        lista_vicini.add(prendiInvitato(ID_Inv));
        if(!(lista_vincolati_vicini==null)){lista_vicini.addAll(lista_vincolati_vicini);}
        lista_lontani.add(prendiInvitato(ID_Inv));
        if(!(lista_vincolati_lontani==null)){lista_lontani.addAll(lista_vincolati_lontani);}
    }


    public void verifica_sistemaVicini(){

        daSistemare.addAll(lista_vicini);
        removeDuplicatiVicini();

        if (daSistemare.size()==0 || lista_vicini.size()==1) {

            System.out.println("Non è stato trovato nessun vincolo di vicinanza per la persona oppure si confuta qualche vincolo precedente" + ID_Inv + "\n");
        }

        else {
            mettiVicini();
        }

        daSistemare.clear();
        lista_vincolati_solo_duplicati.clear();

    }

    public void verifica_sistemaLontani(){

        daSistemare.addAll(lista_lontani);
        removeDuplicatiLontani();

        if (daSistemare.size()==0 || lista_lontani.size()==1) {

            System.out.println("Non è stato trovato nessun vincolo di lontananza per la persona " + ID_Inv + "\n");
        }

        else {
            mettiLontani();
        }

        daSistemare.clear();
        lista_vincolati_solo_duplicati.clear();

    }

    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiVicini(){
        int tavoliInutilizzabili=0;
        for (Tavolo t: tavoli){
            if (t.getDisponibile()==true && t.getNumPosti()>=daSistemare.size() && daSistemare.size()==lista_vicini.size()) {
                t.addAllGuests(daSistemare);
                break;
            }
            else if(t.getDisponibile()==true && t.getNumPosti()>=daSistemare.size() && daSistemare.size()<lista_vicini.size() && t==tavoliDispVincoloVicinanza()){
                t.addAllGuests(daSistemare);
                break;
            }
            else {
                tavoliInutilizzabili++;
                if(tavoliInutilizzabili==tavoli.size()){
                    System.out.println("Nessun tavolo disponibile");
                }
            }
        }

    }


    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiLontani(){
        int k=0;

        for (Tavolo t : tavoli) {


            if (t.getDisponibile() && t.getNumPosti()>=1 && daSistemare.size()==lista_lontani.size() && (k+1)<=daSistemare.size()) {

                t.addGuest(daSistemare.get(k));
                k++;

            }
            else if (daSistemare.size()<lista_lontani.size()) {
                mettiLontaniSenzaDuplicati();
                break;
            }

        }

    }

    public void mettiLontaniSenzaDuplicati(){
        int m=0;

        for (Tavolo t : tavoli){
            if (t.getDisponibile() && t.getNumPosti()>=1 && !(tavoliNonAccessibiliLontananza().contains(t)) && (m+1)<=daSistemare.size()){
                t.addGuest(daSistemare.get(m));
                m++;
            }
        }

    }


    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di vicinanza.
    public Tavolo tavoliDispVincoloVicinanza(){

        Tavolo tavoliDispVic= null;

        for (Tavolo t: tavoli){
            for (int k=0; k<lista_vicini.size(); k++){
                if (!(lista_vicini.get(k)==null)) {
                    if (t.getArraylistInvitati().contains(lista_vicini.get(k)) || t.mostraID_Invitati().contains(lista_vicini.get(k).getID_Inv())) {
                        tavoliDispVic = t;
                    }
                }
            }
        }
        return tavoliDispVic;

    }


    //Crea una lista di duplicati.
    public ArrayList<Invitato> creaListaDuplicatiVicini(){

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista_vicini.size(); k++){
                if (!(lista_vicini.get(k)==null)) {

                    if (t.getArraylistInvitati().contains(lista_vicini.get(k)) || t.mostraID_Invitati().contains(lista_vicini.get(k).getID_Inv())) {

                        lista_vincolati_solo_duplicati.add(lista_vicini.get(k));

                    }
                }
            }
        }
        return lista_vincolati_solo_duplicati;
    }

    //Crea una lista di duplicati.
    public ArrayList<Invitato> creaListaDuplicatiLontani(){

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista_lontani.size(); k++){
                if (!(lista_lontani.get(k)==null)) {

                    if (t.getArraylistInvitati().contains(lista_lontani.get(k)) || t.mostraID_Invitati().contains(lista_lontani.get(k).getID_Inv())) {

                        lista_vincolati_solo_duplicati.add(lista_lontani.get(k));

                    }
                }
            }
        }
        return lista_vincolati_solo_duplicati;
    }

    //Questo metodo controlla quali tavoli non sono accessibili per smistare le persone secondo il vincolo di lontananza.
    public ArrayList<Tavolo> tavoliNonAccessibiliLontananza(){
        ArrayList<Tavolo> tav= new ArrayList<>();

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista_lontani.size(); k++){

                if (!(lista_lontani.get(k)==null)) {

                    if (t.getArraylistInvitati().contains(lista_lontani.get(k)) || t.mostraID_Invitati().contains(lista_lontani.get(k).getID_Inv())) {

                        tav.add(t);
                        break;
                    }
                }
            }
        }
        return tav;

    }

    //Rimuove le persone già sedute a tavola, dalle persone da smistare.
    public ArrayList<Invitato> removeDuplicatiVicini(){

        daSistemare.removeAll(creaListaDuplicatiVicini());

        return daSistemare;


    }

    //Rimuove le persone già sedute a tavola, dalle persone da smistare.
    public ArrayList<Invitato> removeDuplicatiLontani(){

        daSistemare.removeAll(creaListaDuplicatiLontani());

        return daSistemare;


    }

    public ArrayList<Tavolo> getTavoli() {
        return tavoli;
    }

    public void setEvento(Evento ev){
        this.evento=ev;
    }


    public GestoreEvento creaGestoreEvento(){

        GestoreEvento ge;

        ge = evento.gestisciEvento();

        return ge;
    }

    public void addInvitati(ArrayList<Invitato> in){
        invitati.addAll(in);
    }


    public Invitato prendiInvitato(String ID) {

        Invitato invi=null;
        for (Invitato i : invitati) {
            if (ID.equals(i.getID_Inv())) {
                invi = i;
                break;
            }
        }
        return invi;
    }

    //Questo metodo prende la lista_vincolati_lontani.
    public void creaListaInvitatiVicini(String vicin) {

        if(!(vicin==null)) {
            String[] st = vicin.split(" ");

            if (!(st.length==0)) {
                for (int x = 0; x < st.length; x++) {
                    lista_vincolati_vicini.add(prendiInvitato(st[x]));
                }
            }
        }

    }

    //Questo metodo prende la lista_vincolati_lontani.
    public void creaListaInvitatiLontani(String lont){

        if(!(lont==null)){
            String[] st = lont.split(" ");

            if (!(st.length==0)){

                for (int x = 0; x < st.length; x++)
                {
                    lista_vincolati_lontani.add(prendiInvitato(st[x]));
                }
            }

        }

    }


    public String getVicino(){return vicino;}

    public String getLontano(){return lontano;}

    public ArrayList<Invitato> getLista_vicini() {
        return lista_vicini;
    }

    public ArrayList<Invitato> getLista_lontani() {
        return lista_lontani;
    }
}