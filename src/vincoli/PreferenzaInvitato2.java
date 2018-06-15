package vincoli;

import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;


public class PreferenzaInvitato2 implements Vincolo {

    private ArrayList<Invitato> vincolatiAInvitato;
    private Evento evento;
    private ArrayList<Invitato> lista_vincolati = new ArrayList<Invitato>();
    private PreferenzaInvitatoEnum preferenza;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati = new ArrayList<Invitato>();
    private ArrayList<Invitato> lista_vincolati_solo_duplicati = new ArrayList<Invitato>();
    private final int numero_vincolati;

    public PreferenzaInvitato2(Invitato invitato, ArrayList<Invitato> vincolatiAInvitato, Evento evento, PreferenzaInvitatoEnum preferenza) {

        this.vincolatiAInvitato=vincolatiAInvitato;
        this.evento = evento;
        this.preferenza = preferenza;
        lista_vincolati.add(invitato);
        lista_vincolati.addAll(vincolatiAInvitato);
        this.numero_vincolati = lista_vincolati.size();
        verificaIdoneita();

    }

    //Questo metodo verifica se le persone che vengono vincolate siano realmente presenti all'evento.
    public void verificaIdoneita(){
        if (evento.getListaInvitati().containsAll(lista_vincolati)){
            creaVincolo();
        }
    }
    //Questo metodo crea il vincolo secondo la preferenza.
    private void creaVincolo() {

        switch (preferenza){

            case STA_VICINO_A:

                mettiVicini();

            case NON_STA_VICINO_A:

                mettiLontani();

        }

    }

    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiVicini(){

        for (int k=0; k < numero_vincolati;  k++){

            if (controllaSePresente(lista_vincolati.get(k))==true){

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

            if (t.getDisponibile() && t.getNumPosti()>=lista_vincolati.size() && tavoliDispVincoloVicinanza()>1){
                t.addAllGuests(lista_vincolati);
                break;

            } else if ((t.getDisponibile()==false || t.getNumPosti()>=lista_vincolati.size()) && tavoliDispVincoloVicinanza()==1){
                System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\n");
                break;
            }
        }
    };

    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiLontani(){

        for (int k=0; k < numero_vincolati;  k++){

            if (controllaSePresente(lista_vincolati.get(k))==true){

                smistaLontaniSenzaDuplicati();
                break;

            } else  {
                smistaLontani();
                break;
            }
        }

    }

    ////Questo metodo mette gli invitati lontani al primo tavolo disponibile.
    private void smistaLontani() {

        int k = 0;
        for (Tavolo t : evento.getLocation().getTavoliLocale()){

            if(t.getDisponibile() && (t.getPostiTot()-t.mostraInvitatiSeduti())>= 1 && k<lista_vincolati.size() && tavoliDispVincoloLontananza()>=lista_vincolati.size() ){
                t.addGuest(lista_vincolati.get(k));
                k++;

            } else if (tavoliDispVincoloLontananza()<lista_vincolati.size()){
                System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\n");
                break;
            }
        }
    }

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di lontananza.
    public int tavoliDispVincoloLontananza(){
        int tavoliDisp = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){
            if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())== 0)){
                tavoliDisp--;
            }
        }
        return tavoliDisp;
    }


    private void smistaLontaniSenzaDuplicati() {
    };

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di vicinanza.
    public int tavoliDispVincoloVicinanza(){

        int tavoliDispVic = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){

            if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())<numero_vincolati)){
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
    public ArrayList<Invitato> creaListaDuplicati(){

        for (Tavolo t : evento.getLocation().getTavoliLocale()){

                for (int k = 0; k < lista_vincolati.size(); k++){

                    if (t.getArraylistInvitati().contains(lista_vincolati.get(k))) {

                        lista_vincolati_solo_duplicati.add(lista_vincolati.get(k));

                    }
                }
        }
        return lista_vincolati_solo_duplicati;
    }

    //Rimuove le persone già sedute a tavola, dalle persone da smistare.
    public ArrayList<Invitato> removeDuplicati(){

        lista_vincolati.removeAll(creaListaDuplicati());
        lista_vincolati_senza_duplicati.addAll(lista_vincolati);
        return lista_vincolati_senza_duplicati;

    }

    //Questo metodo mette a sedere le persone, considerando che alcuni sono già seduti a tavola.
    public void smistaViciniSenzaDuplicati(){


        for (Tavolo t: evento.getLocation().getTavoliLocale()){


            for (int n=0; n < lista_vincolati.size(); n++){

                if(t.getArraylistInvitati().contains(lista_vincolati.get(n)) && t.getDisponibile() && t.getNumPosti()>= (numero_vincolati-creaListaDuplicati().size())){
                    t.addAllGuests(removeDuplicati());
                    break;

                } else if (t.getArraylistInvitati().contains(lista_vincolati.get(n)) && (t.getDisponibile()== false) || t.getNumPosti()<(numero_vincolati- creaListaDuplicati().size())){
                    System.out.println("Gli invitati:\n "+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\n");
                    break;
                }
            }

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
