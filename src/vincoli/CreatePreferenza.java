package vincoli;

import database.ConnessioneDB;
import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;


public class CreatePreferenza {

    ConnessioneDB c;
    ArrayList<PreferenzaInvitato> pref = new ArrayList<>();
    ArrayList<GestorePreferenzaInvitato> gpref = new ArrayList<>();
    ArrayList<Invitato> invitati = new ArrayList<>();
    ArrayList<Evento> eventi = new ArrayList<>();
    Evento ev;
    ArrayList<Tavolo> tavoli = new ArrayList<>();
    ArrayList<GestorePreferenzaInvitato> gp = new ArrayList<>();


    public CreatePreferenza(String ID_Ev, ArrayList<Tavolo> tav){

        c=new ConnessioneDB();
        c.startConn();

        this.invitati = c.getInvitato(ID_Ev);
        this.ev=c.getEventoSingolo(ID_Ev);
        pref= c.getVincoloInvitato(ID_Ev);
        c.closeConn();

        this.tavoli = tav;
        gpref= crea();
    }

    public ArrayList<Tavolo> getTavoli() {
        return tavoli;
    }


    public ArrayList<GestorePreferenzaInvitato> crea(){

        for (PreferenzaInvitato p : pref){
            p.setEvento(ev);
            p.addInvitati(invitati);
            p.getTavoli().addAll(tavoli);
            gp.addAll(p.gestisciPreferenza());

        }

        return gp;
    }

    public ArrayList<GestorePreferenzaInvitato> getGpref() {
        return gpref;
    }

    public void smista(){

        for (GestorePreferenzaInvitato gpi : gp){

            if(gpi.getPreferenza()==PreferenzaInvitatoEnum.STA_VICINO_A) {

                gpi.verificaIdoneita();
            }
        }

        for (GestorePreferenzaInvitato gpi : gp){

            if(gpi.getPreferenza()==PreferenzaInvitatoEnum.NON_STA_VICINO_A) {

                gpi.verificaIdoneita();
            }
        }
    }

    public ArrayList<PreferenzaInvitato> getPref() {
        return pref;
    }

}
