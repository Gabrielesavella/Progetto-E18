package vincoli;

import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;


public class PreferenzaInvitato implements Vincolo {

    private Evento evento;
    private ArrayList<Invitato> lista_vincolati = new ArrayList<>();
    private PreferenzaInvitatoEnum preferenza;

    public PreferenzaInvitato(ArrayList<Invitato> lista_vincolati, Evento evento, PreferenzaInvitatoEnum preferenza) {

        this.lista_vincolati = lista_vincolati;
        this.evento = evento;
        this.preferenza = preferenza;
        creaVincolo();
    }


    private void creaVincolo() {


        switch (preferenza) {

            case STA_VICINO_A:

                for(Tavolo t : evento.getLocation().getTavoliLocale()){

                    int n = 0;

                    if (t.getArraylistInvitati().contains(lista_vincolati.get(n)) && lista_vincolati.size()>=n){

                        mettiVicinoAiVincolati();


                    } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(n))) && lista_vincolati.size()>=n) {

                        n++;

                    } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(n))) && lista_vincolati.size()<n){

                        mettiVicino();

                    }

                }


            case NON_STA_VICINO_A:

                for(Tavolo t : evento.getLocation().getTavoliLocale()){

                    int n = 0;

                    if (t.getArraylistInvitati().contains(lista_vincolati.get(n)) && lista_vincolati.size()>=n){

                        mettiLontanoAiVincolati();


                    } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(n))) && lista_vincolati.size()>=n) {

                        n++;

                    } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(n))) && lista_vincolati.size()<n){

                        mettiLontano();

                    }

                }

        }

    }

    private void mettiLontanoAiVincolati() {

    }

    private void mettiVicinoAiVincolati() {
        int k = 0;
        for (Tavolo t : evento.getLocation().getTavoliLocale()){
            if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) && t.getDisponibile() && (t.getAssegnamentiTavolo().size() >= lista_vincolati.size()-1)){

                lista_vincolati.remove(k);
                t.addAllGuests(lista_vincolati);

            } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(k)))){
                k++;
            } else if (k>lista_vincolati.size()){
                break;
            }
        }
    }


    private void mettiVicino() {

        int contatore = 0;

        int tavoli_disponibili = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t : evento.getLocation().getTavoliLocale()) {

            if (t.getDisponibile() && t.getNumPosti() >= lista_vincolati.size() && contatore<lista_vincolati.size()) {

                t.addAllGuests(lista_vincolati);
                contatore = lista_vincolati.size();
            }

            else if ((contatore + 1) == lista_vincolati.size()){

                break;

            } else if (t.getDisponibile()==false || t.getNumPosti() <=lista_vincolati.size()){

                tavoli_disponibili--;

            } else if (tavoli_disponibili==0){

                System.out.println("Il vincolo non può essere rispettato!");

            }

        }
    }

    private void mettiLontano() {

        int i = 0;

        if (evento.getLocation().getTavoliLocale().get(i).getDisponibile()==true && lista_vincolati.size()>0 && i<evento.getLocation().getTavoliLocale().size()) {

            evento.getLocation().getTavoliLocale().get(i).addGuest(lista_vincolati.get(i));
            lista_vincolati.remove(lista_vincolati.get(i));
            i++;

        } else if (evento.getLocation().getTavoliLocale().get(i).getDisponibile()==false && lista_vincolati.size()>0 && i<evento.getLocation().getTavoliLocale().size()){

            i++;

        } else if (i>evento.getLocation().getTavoliLocale().size()){

            System.out.println("Il vincolo non è rispettabile!");

        }

    }


}