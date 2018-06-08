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

        int numMaxVincolati = lista_vincolati.size();
        int tavoli_disponibili = evento.getLocation().getTavoliLocale().size();


        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            int size = t.getNumPosti();
            int invitati_daSmistare = lista_vincolati.size();
            boolean ulterioriTavoli;

            for(int k=0; (k-2)==numMaxVincolati;) {
                if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) && t.getDisponibile() && invitati_daSmistare > 0 && (size >= lista_vincolati.size())) {

                    lista_vincolati.remove(k);
                    invitati_daSmistare--;

                } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(k)))) {
                    k++;

                } else if (k > numMaxVincolati) {
                    t.addAllGuests(lista_vincolati);
                    ulterioriTavoli=false;
                    break;

                } else if (t.getDisponibile() == false || size < lista_vincolati.size()) {

                    tavoli_disponibili--;
                    break;

                }
                if (tavoli_disponibili == 0) {

                    System.out.println("Gli invitati:\n " + getNomeVincolati() + "non possono essere posizionati secondo il vincolo" + preferenza + "\n");
                    break;
                }
            }

            if (ulterioriTavoli=false){break;}
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

                System.out.println("Gli invitati:\n "+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo" + preferenza +"\n");

            }

        }
    }

    private void mettiLontano() {

        int i = 0;

        int invitati_da_smistare = lista_vincolati.size();

        if (evento.getLocation().getTavoliLocale().get(i).getDisponibile()==true && invitati_da_smistare>0 && i<evento.getLocation().getTavoliLocale().size()) {

            evento.getLocation().getTavoliLocale().get(i).addGuest(lista_vincolati.get(i));
            invitati_da_smistare--;
            i++;

        } else if (evento.getLocation().getTavoliLocale().get(i).getDisponibile()==false && invitati_da_smistare>0 && i<evento.getLocation().getTavoliLocale().size()){

            i++;

        } else if (i>evento.getLocation().getTavoliLocale().size()){

            System.out.println("Gli invitati:\n "+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo" + preferenza +"\n");

        } else if (invitati_da_smistare<=0){

            System.out.println("Gli invitati:\n "+ getNomeVincolati() + "sono stati posizionati secondo i vincoli.\n");
        }
    }


    public String getNomeVincolati() {
        String a = "";

        for (Invitato i : lista_vincolati){
            a += i.getNome()+ i.getCognome() + "\n";
        }

        return a;
    }


}