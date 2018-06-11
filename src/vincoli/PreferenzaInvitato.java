package vincoli;

import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;


public class PreferenzaInvitato implements Vincolo {

    private Evento evento;
    private ArrayList<Invitato> lista_vincolati = new ArrayList<>();
    private PreferenzaInvitatoEnum preferenza;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati = new ArrayList<>();

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

    /* Se devo disporre alcuni invitati vicino, devo però tenere presente anche gli altri vincoli ed essere sicuro che vengano
       tutti rispettati. Quindi controllo prima se un vincolo contiene un invitato che è già disposto a tavola, in tal caso
       dispongo tutti gli altri vincolati ad esso al suo stesso tavolo, altrimenti, se non è già seduto a tavola nessun vincolato,
       li dispongo normalmente nel primo tavolo disponibile.
     */

    private void mettiVicinoAiVincolati() {

        removeDuplicati();

        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            int tavoli_non_disponibili = 0;

            for (int k = 0; (k-2)<lista_vincolati.size();){

                if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) && t.getDisponibile() && t.getNumPosti()>=lista_vincolati_senza_duplicati.size() && (k-1)<lista_vincolati.size()) {

                    t.addAllGuests(lista_vincolati_senza_duplicati);
                    break;

                } else if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) && (t.getDisponibile()==false || t.getNumPosti()<lista_vincolati_senza_duplicati.size()) && (k-1)<lista_vincolati.size()){

                    tavoli_non_disponibili++;
                    break;

                } else if (!(t.getArraylistInvitati().contains(lista_vincolati.get(k))) && (k-1)<lista_vincolati.size()) {

                    k++;

                } else if ((k-1)==lista_vincolati.size()){

                    tavoli_non_disponibili++;

                } else if (tavoli_non_disponibili==evento.getLocation().getTavoliLocale().size()){

                    System.out.println("Gli invitati:\n "+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo" + preferenza +"\n");

                }
            }
        }

    }

    /* Ci possono essere più vincoli riguardanti lo stesso invitato, quindi prima di smistare gli invitati a tavola,
       è necessario non considerare gli invitati già disposti a tavola (per evitare dei duplicati) e creare una lista
       con i soli invitati che non hanno ancora vincoli in cui partecipano. Questo è ciò che fa removeDuplicati().
     */

    public void removeDuplicati(){

        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            for (int k = 0; (k-1)<lista_vincolati.size();){

                if (!(t.getArraylistInvitati().contains(lista_vincolati.get(k)))) {

                    lista_vincolati_senza_duplicati.add(lista_vincolati.get(k));
                    k++;

                } else { k++;}
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