package vincoli;

import database.ConnessioneDB;
import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class GestoreVincoliInvitato implements Vincolo {

    private Invitato invi;
    private ArrayList<Invitato> lista_vincolati = new ArrayList<>();
    private ArrayList<Invitato> lista_vincolati_solo_duplicati = new ArrayList<Invitato>();
    //private final int numero_vincolati;
    private Evento evento;
    private ArrayList<PreferenzaInvitato> lista_vincoli;
    private ArrayList<Invitato> invitati;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati = new ArrayList<Invitato>();
    ConnessioneDB c;

    public GestoreVincoliInvitato(String ID_Evento) {
        c.startConn();
        lista_vincoli= c.getVincoloInvitato(ID_Evento);
        invitati= c.getInvitato(ID_Evento);
        evento.getLista_vincoli().add(this);
        creaVincolo();
    }

    private void creaVincolo() {
        for (PreferenzaInvitato p : lista_vincoli){

            if (p.getVerificaIdoneita()==true) {
                p.mettiVicini();
                p.getListaVincolatiVicini().clear();
                p.getListaVincolatiVicini().addAll(p.creaListaInvitatiVicini());
                
            } else {
                System.out.println("Vincolo incongruente!\nGli invitati:\n"+ p.getNomeVincolatiVicini() + "non possono essere posizionati secondo " +
                        "i vincoli.\nControlla di aver messo effettivamente persone invitate all'evento, oppure di non aver violato un vincolo precedente.\n");
            }
        }

        for (PreferenzaInvitato p : lista_vincoli){

            if (p.getVerificaIdoneita()==true){
                p.mettiLontani();
                p.getListaVincolatiLontani().clear();
                p.getListaVincolatiLontani().addAll(p.creaListaInvitatiLontani());

            } else {
                System.out.println("Vincolo incongruente!\nGli invitati:\n"+ p.getNomeVincolatiLontani() + "non possono essere posizionati secondo " +
                        "i vincoli.\nControlla di aver messo effettivamente persone invitate all'evento, oppure di non aver violato un vincolo precedente.\n");
            }
        }
    }
}



