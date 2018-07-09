package vincoli;

import database.ConnessioneDB;
import locale.Evento;
import locale.GestoreEvento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;

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
    ConnessioneDB conn = new ConnessioneDB();

    public PreferenzaInvitato(String ID_Ev,String ID_Inv,String vicino,String lontano){

        this.ID_Ev=ID_Ev;
        this.ID_Inv=ID_Inv;
        this.vicino=vicino;
        this.lontano=lontano;
        evento=null;
        invitati = new ArrayList<>();
        tavoli = new ArrayList<>();
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
    public ArrayList<Invitato> creaListaInvitatiVicini(String vicin) {

        if(!(vicin==null)) {
            String[] st = vicin.split(" ");

            if (!(st.length==0)) {
                for (int x = 0; x < st.length; x++) {
                    lista_vincolati_vicini.add(prendiInvitato(st[x]));
                }
            }
        }

        return lista_vincolati_vicini;

    }

    //Questo metodo prende la lista_vincolati_lontani.
    public ArrayList<Invitato> creaListaInvitatiLontani(String lont){

        if(!(lont==null)){
            String[] st = lont.split(" ");

            if (!(st.length==0)){

                for (int x = 0; x < st.length; x++)
                {
                    lista_vincolati_lontani.add(prendiInvitato(st[x]));
                }
            }

        }
        return lista_vincolati_lontani;

    }

    public ArrayList<GestorePreferenzaInvitato> gestisciPreferenza(){

        ArrayList<GestorePreferenzaInvitato> ges= new ArrayList<>();

        if (!(vicino==null)) {
            GestorePreferenzaInvitato newPref1;
            newPref1 = new GestorePreferenzaInvitato(prendiInvitato(ID_Inv), creaListaInvitatiVicini(vicino), getTavoli(), PreferenzaInvitatoEnum.STA_VICINO_A);
            ges.add(newPref1);
        }

        if (!(lontano==null)) {
            GestorePreferenzaInvitato newPref2;
            newPref2 = new GestorePreferenzaInvitato(prendiInvitato(ID_Inv), creaListaInvitatiLontani(lontano), getTavoli(), PreferenzaInvitatoEnum.NON_STA_VICINO_A);
            ges.add(newPref2);
        }



        return ges;
    }


}