package vincoli;

import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;


public class PreferenzaInvitato implements Vincolo {


    String vicino, lontano, ID_Inv, ID_Ev;

    public PreferenzaInvitato(String ID_Ev, String ID_Inv, String vicino, String lontano) {

        this.ID_Ev = ID_Ev;
        this.ID_Inv= ID_Inv;
        this.vicino=vicino;
        this.lontano=lontano;

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