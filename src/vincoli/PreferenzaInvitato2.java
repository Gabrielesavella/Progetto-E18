package vincoli;

import locale.Evento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;


public class PreferenzaInvitato2 implements Vincolo {

    private Evento evento;
    private ArrayList<Invitato> lista_vincolati = new ArrayList<Invitato>();
    private PreferenzaInvitatoEnum preferenza;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati = new ArrayList<Invitato>();
    private ArrayList<Invitato> lista_vincolati_solo_duplicati = new ArrayList<Invitato>();
    private final int numero_vincolati;

    public PreferenzaInvitato2(Invitato invitato, ArrayList<Invitato> vincolatiAInvitato, Evento evento, PreferenzaInvitatoEnum preferenza) {
        
        this.evento = evento;
        this.preferenza = preferenza;
        lista_vincolati.add(invitato);
        lista_vincolati.addAll(vincolatiAInvitato);
        this.numero_vincolati = lista_vincolati.size();
        verificaIdoneita();
        evento.getLista_vincoli().add(this);
        lista_vincolati.removeAll(lista_vincolati);
        lista_vincolati.addAll(vincolatiAInvitato);
        lista_vincolati.add(invitato);
    }

    //Questo metodo verifica se le persone che vengono vincolate siano realmente presenti all'evento e se ci sono
    //altri vincoli che confutano questo.
    public void verificaIdoneita(){
        if (evento.getListaInvitati().containsAll(lista_vincolati) && controllaIncongruenze()==false){
            creaVincolo();
        } else {
            System.out.println("Vincolo incongruente!\nGli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\nControlla di aver messo effettivamente persone invitate all'evento, oppure di non aver violato un vincolo precedente.\n");
        }
    }


    //Questo metodo crea il vincolo secondo la preferenza.
    private void creaVincolo() {

        if (preferenza==PreferenzaInvitatoEnum.STA_VICINO_A){

            mettiVicini();

        } else if (preferenza==PreferenzaInvitatoEnum.NON_STA_VICINO_A){

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

            } else  if ((k+1)==numero_vincolati && controllaSePresente(lista_vincolati.get(k))==false){
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
        int tavoliDispLont = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){
            if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())== 0)){
                tavoliDispLont--;
            }
        }
        return tavoliDispLont;
    }

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di lontananza, essendoci già person
    //sedute.
    public int tavoliDispVincoloLontananzaContandoDuplicati(){
        int tavoliDispLont = evento.getLocation().getTavoliLocale().size();

        for (Tavolo t: evento.getLocation().getTavoliLocale()){
            for (int n=0; n<lista_vincolati.size(); n++){

                if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())== 0) || t.getArraylistInvitati().contains(lista_vincolati.get(n))){
                tavoliDispLont--;
                break;
                }
            }
        }
        return tavoliDispLont;
    }

    //Questo metodo mette a sedere le persone lontane, considerando che alcuni sono già seduti a tavola.
    private void smistaLontaniSenzaDuplicati() {

        boolean non_possibile=false;

        for (Tavolo t: evento.getLocation().getTavoliLocale()){


            for (int n=0; n < lista_vincolati.size(); n++){

                if(!(t.getArraylistInvitati().contains(lista_vincolati.get(n))) && t.getDisponibile() && t.getNumPosti()>=1 && tavoliDispVincoloLontananzaContandoDuplicati()>=(numero_vincolati- creaListaDuplicati().size())){
                    t.addGuest(removeDuplicati().get(n));
                    removeDuplicati().remove(removeDuplicati().get(n));
                    break;

                } else if (tavoliDispVincoloLontananzaContandoDuplicati()<(numero_vincolati- creaListaDuplicati().size())){
                    System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\n");
                    non_possibile=true;
                    break;
                }
            }

            if (non_possibile==true){break;}

        }

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

    //Questo metodo mette a sedere le persone vicine, considerando che alcuni sono già seduti a tavola.
    public void smistaViciniSenzaDuplicati(){


        for (Tavolo t: evento.getLocation().getTavoliLocale()){


            for (int n=0; n < lista_vincolati.size(); n++){

                if(t.getArraylistInvitati().contains(lista_vincolati.get(n)) && t.getDisponibile() && t.getNumPosti()>=(numero_vincolati-creaListaDuplicati().size())){
                    t.addAllGuests(removeDuplicati());
                    break;

                } else if (t.getArraylistInvitati().contains(lista_vincolati.get(n)) && (t.getDisponibile()== false) || t.getNumPosti()<(numero_vincolati- creaListaDuplicati().size())){
                    System.out.println("Gli invitati:\n "+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\n");
                    break;
                }
            }

        }

    }


    //Stampa il nome di tutti i vincolati presenti nella lista.
    public String getNomeVincolati() {
        String a = "";

        for (Invitato i : lista_vincolati){
            a += i.getNome()+ i.getCognome() + "\n";
        }

        return a;
    }

    public PreferenzaInvitatoEnum getPreferenza() {
        return preferenza;
    }

    public ArrayList<Invitato> getLista_vincolati() {
        return lista_vincolati;
    }

    //Questo metodo controlla se ci sono o meno vincoli che vanno a confutare un altro vincolo.
    public boolean controllaIncongruenze() {

        boolean incongruente=false;
        int max_accettabile=2;

        for (Vincolo v : evento.getLista_vincoli()){

            for (int k=0; k<lista_vincolati.size(); k++){

                if (v instanceof PreferenzaInvitato2 && !(((PreferenzaInvitato2) v).getPreferenza().equals(preferenza)) && ((PreferenzaInvitato2) v).getLista_vincolati().contains(lista_vincolati.get(k)) && max_accettabile>0){

                    max_accettabile--;

                    if (max_accettabile==0){

                        incongruente=true;
                        break;
                    }

                }
            }

            if (incongruente==true){break;} else {max_accettabile=2;}

        }

        return incongruente;
    }


}
