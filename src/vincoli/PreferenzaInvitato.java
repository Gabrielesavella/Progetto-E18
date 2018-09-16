package vincoli;

import database.ConnessioneDB;
import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;
import java.util.Collections;

public class PreferenzaInvitato{

    private ConnessioneDB c = new ConnessioneDB();
    private ArrayList<Invitato> invitati;
    private String ID_Ev, ID_Inv,vicino, lontano;
    private ArrayList<Invitato> lista_vincolati_vicini = new ArrayList<>();
    private ArrayList<Invitato> tutti_vincolati_lontani_a_ID = new ArrayList<>();
    private ArrayList<Invitato> tutti_vincolati_lontani_a_primo = new ArrayList<>();
    private ArrayList<Invitato> tutti_vincolati_lontani_a_secondo = new ArrayList<>();
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

    public void verifica_sistemaLontani(){

        daSistemare.addAll(lista_lontani);
        removeDuplicatiLontani();

        if (daSistemare.size()==0 || lista_lontani.size()==1) {

            System.out.println("Non è stato trovato nessun vincolo di lontananza per la persona " + ID_Inv + "\n");
        }

        else {
            mettiLontaniSenzaDuplicati();
        }

        daSistemare.clear();
        lista_vincolati_solo_duplicati.clear();

    }

    public void mettiLontaniSenzaDuplicati(){

        eliminaDupAllLists();

        for (Tavolo t : tavoli){
            if (t.getDisponibile() && t.getNumPosti()>=1 && !(tavoliNonAccessibiliLontananza(tutti_vincolati_lontani_a_ID).contains(t)) && mostraID_Inv(daSistemare).contains(lista_lontani.get(0).getID_Inv())){
                t.addGuest(lista_lontani.get(0));
                break;
            }
        }

        if(lista_lontani.size()>1){
            for (Tavolo t : tavoli){
                if (t.getDisponibile() && t.getNumPosti()>=1 && !(tavoliNonAccessibiliLontananza(tutti_vincolati_lontani_a_primo).contains(t)) && mostraID_Inv(daSistemare).contains(lista_lontani.get(1).getID_Inv())){
                    t.addGuest(lista_lontani.get(1));
                    break;
                }
            }
        }

        if(lista_lontani.size()>2){
            for (Tavolo t : tavoli){
                if (t.getDisponibile() && t.getNumPosti()>=1 && !(tavoliNonAccessibiliLontananza(tutti_vincolati_lontani_a_secondo).contains(t)) && mostraID_Inv(daSistemare).contains(lista_lontani.get(2).getID_Inv())){
                    t.addGuest(lista_lontani.get(2));
                    break;
                }
            }
        }

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
    public ArrayList<Tavolo> tavoliNonAccessibiliLontananza(ArrayList<Invitato> lista){
        ArrayList<Tavolo> tav= new ArrayList<>();

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista.size(); k++){

                if (!(lista.get(k)==null)) {

                    if (t.getArraylistInvitati().contains(lista.get(k)) || t.mostraID_Invitati().contains(lista.get(k).getID_Inv())) {

                        tav.add(t);
                        break;
                    }
                }
            }
        }
        return tav;

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

    public ArrayList<String> mostraID_Inv(ArrayList<Invitato> listina) {
        ArrayList<String> id_invitati = new ArrayList<>();
        for (Invitato i : listina){
            if(i.getID_Inv()!=null){id_invitati.add(i.getID_Inv());}
        }
        return id_invitati;
    }

    public void eliminaDupLists(ArrayList<Invitato> list){

        for (Invitato i : invitati){
            int contatore = 0;

            for(int k=0; k<list.size(); k++){

                if (list.get(k).getID_Inv().equals(i.getID_Inv())){
                    contatore++;
                }

                if(contatore>1){
                    list.remove(k);
                    k--;
                    contatore--;
                }
            }

        }
    }

    public void eliminaDupAllLists(){
        eliminaDupLists(tutti_vincolati_lontani_a_ID);
        eliminaDupLists(tutti_vincolati_lontani_a_primo);
        eliminaDupLists(tutti_vincolati_lontani_a_secondo);

        cancellaSoggetti();
    }

    public void cancellaSoggetti(){
        if(tutti_vincolati_lontani_a_ID.size()>0) {
            for (int i = 0; i < tutti_vincolati_lontani_a_ID.size(); i++) {
                if (lista_lontani.get(0).getID_Inv().equals(tutti_vincolati_lontani_a_ID.get(i))) {
                    tutti_vincolati_lontani_a_ID.remove(i);
                    i--;
                }
            }
        }
        if(tutti_vincolati_lontani_a_primo.size()>0 && lista_lontani.size()>1) {
            for (int i = 0; i < tutti_vincolati_lontani_a_primo.size(); i++) {
                if (lista_lontani.get(1).getID_Inv().equals(tutti_vincolati_lontani_a_primo.get(i))) {
                    tutti_vincolati_lontani_a_primo.remove(i);
                    i--;
                }
            }
        }
        if(tutti_vincolati_lontani_a_secondo.size()>0 && lista_lontani.size()>2) {
            for (int i = 0; i < tutti_vincolati_lontani_a_secondo.size(); i++) {
                if (lista_lontani.get(2).getID_Inv().equals(tutti_vincolati_lontani_a_secondo.get(i))) {
                    tutti_vincolati_lontani_a_secondo.remove(i);
                    i--;
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

    public ArrayList<Invitato> getTutti_vincolati_lontani_a_ID() {
        return tutti_vincolati_lontani_a_ID;
    }

    public ArrayList<Invitato> getTutti_vincolati_lontani_a_primo() {
        return tutti_vincolati_lontani_a_primo;
    }

    public ArrayList<Invitato> getTutti_vincolati_lontani_a_secondo() {
        return tutti_vincolati_lontani_a_secondo;
    }
}