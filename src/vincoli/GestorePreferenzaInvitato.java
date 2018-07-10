package vincoli;

import locale.GestoreEvento;
import locale.Tavolo;
import persone.Invitato;

import java.util.ArrayList;
import java.util.Collections;


public class GestorePreferenzaInvitato implements Vincolo {

    private GestoreEvento gestoreEvento;
    private ArrayList<Invitato> lista_vincolati = new ArrayList<Invitato>();
    private PreferenzaInvitatoEnum preferenza;
    private ArrayList<Invitato> lista_vincolati_senza_duplicati = new ArrayList<Invitato>();
    private ArrayList<Invitato> lista_vincolati_solo_duplicati = new ArrayList<Invitato>();
    private final int numero_vincolati;
    private ArrayList<GestorePreferenzaInvitato> lista_vincoli;
    private ArrayList<Tavolo> tavoli = new ArrayList<>();
    private ArrayList<Invitato> daSistemare = new ArrayList<>();


    public GestorePreferenzaInvitato(Invitato invitato, ArrayList<Invitato> vincolatiAInvitato, ArrayList<Tavolo> tavoli, PreferenzaInvitatoEnum preferenza) {

        this.gestoreEvento = gestoreEvento;
        this.preferenza = preferenza;
        lista_vincoli= new ArrayList<>();
        lista_vincolati.add(invitato);
        if(!(vincolatiAInvitato==null))
        {lista_vincolati.addAll(vincolatiAInvitato);}
        this.numero_vincolati = lista_vincolati.size();
        this.tavoli= tavoli;

    }

    public void sortaTavoli(){
        if (preferenza==PreferenzaInvitatoEnum.STA_VICINO_A){
            Collections.sort(tavoli, Collections.reverseOrder());
        }
        else { Collections.sort(tavoli);}
    }
    //Questo metodo verifica se le persone che vengono vincolate siano realmente presenti all'gestoreEvento e se ci sono
    //altri vincoli che confutano questo.
    /*public void verificaIdoneita(){
        if (gestoreEvento.getListaInvitati().containsAll(lista_vincolati) ){
            creaVincolo();
        } else {
            System.out.println("Vincolo incongruente!\nGli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\nControlla di aver messo effettivamente persone invitate all'gestoreEvento, oppure di non aver violato un vincolo precedente.\n");
        }
    }*/


    //Questo metodo crea il vincolo secondo la preferenza.
    public void verificaIdoneita() {

        daSistemare.addAll(lista_vincolati);
        removeDuplicati();

        if (daSistemare.size()==0 || lista_vincolati.size()==1) {

            System.out.println("Nessun vincolo");}

        else{

            if (preferenza == PreferenzaInvitatoEnum.STA_VICINO_A) {


                mettiVicini();

            } else if (preferenza == PreferenzaInvitatoEnum.NON_STA_VICINO_A) {

                mettiLontani();

            }
        }

    }

    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiVicini(){
        int tavoliInutilizzabili=0;
        for (Tavolo t: tavoli){
            if (t.getDisponibile() && t.getNumPosti()>=daSistemare.size() && daSistemare.size()==lista_vincolati.size()) {
                t.addAllGuests(daSistemare);
                break;
            }
            else if(t.getDisponibile() && t.getNumPosti()>=daSistemare.size() && daSistemare.size()<lista_vincolati.size() && t==tavoliDispVincoloVicinanza()){
                t.addAllGuests(daSistemare);
                break;
            }
            else {
                tavoliInutilizzabili++;
                if(tavoliInutilizzabili==tavoli.size()){
                    System.out.println("Nessun tavolo disponibile");
                }
            }
        }

    }



    //Questo metodo mette gli invitati vicino al primo tavolo disponibile.
    /*private void smistaVicini() {

        for (Tavolo t : tavoli){

            if (t.getDisponibile() && t.getNumPosti()>=lista_vincolati.size() && tavoliDispVincoloVicinanza()>1){
                t.addAllGuests(lista_vincolati);
                break;

            } else if ((t.getDisponibile()==false || t.getNumPosti()>=lista_vincolati.size()) && tavoliDispVincoloVicinanza()==1){
                System.out.println("Gli invitati:\n"+ getNomeVincolati() + "non possono essere posizionati secondo il vincolo " + preferenza +"\n");
                break;
            }
        }
    };*/

    //Questo metodo indica come mettere a sedere gli invitati a seconda che alcuni siano già seduti o meno.
    public void mettiLontani(){
        int k=0;

        for (Tavolo t : tavoli) {


                if (t.getDisponibile() && t.getNumPosti()>=1 && daSistemare.size()==lista_vincolati.size() && (k+1)<=daSistemare.size()) {

                    t.addGuest(daSistemare.get(k));
                    k++;

                }
                else if (daSistemare.size()<lista_vincolati.size()) {
                    mettiLontaniSenzaDuplicati();
                    break;
                }

        }

    }

    public void mettiLontaniSenzaDuplicati(){
        int m=0;

        for (Tavolo t : tavoli){
            if (t.getDisponibile() && t.getNumPosti()>=1 && !(tavoliNonAccessibiliLontananza().contains(t)) && (m+1)<=daSistemare.size()){
                t.addGuest(daSistemare.get(m));
                m++;
            }
        }

    }



    ////Questo metodo mette gli invitati lontani al primo tavolo disponibile.
    private void smistaLontani() {

        int k = 0;
        for (Tavolo t : tavoli){

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
        int tavoliDispLont = tavoli.size();

        for (Tavolo t: tavoli){
            if (t.getDisponibile()==false || ((t.getPostiTot()-t.mostraInvitatiSeduti())== 0)){
                tavoliDispLont--;
            }
        }
        return tavoliDispLont;
    }

    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di lontananza, essendoci già person
    //sedute.
    public int tavoliDispVincoloLontananzaContandoDuplicati(){
        int tavoliDispLont = tavoli.size();

        for (Tavolo t: tavoli){
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

        int k=0;

        boolean non_possibile=false;



        for (Tavolo t: tavoli){

            for (int n=0; n<lista_vincolati.size(); n++) {
                if(!(t.getArraylistInvitati().contains(lista_vincolati.get(n))) && t.getDisponibile() && t.getNumPosti()>=1 && tavoliDispVincoloLontananzaContandoDuplicati()>=(numero_vincolati- creaListaDuplicati().size()) && !(tavoliNonAccessibiliLontananza().contains(t))){
                    if(!(removeDuplicati().size()==0)){t.addGuest(removeDuplicati().get(0));
                        removeDuplicati().remove(removeDuplicati().get(0));}
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
    public Tavolo tavoliDispVincoloVicinanza(){

        Tavolo tavoliDispVic= null;

        for (Tavolo t: tavoli){
            for (int k=0; k<lista_vincolati.size(); k++){
                if (t.getArraylistInvitati().contains(lista_vincolati.get(k))){
                    tavoliDispVic=t;
                }
            }
        }
        return tavoliDispVic;

    }
    // Controlla se è già seduto ad un tavolo l'invitato
    public boolean controllaSePresente(Invitato i){
        boolean giaPresente = false;
        for (Tavolo t: tavoli){
            if (t.getArraylistInvitati().contains(i)){
                giaPresente = true;
                break;
            } else { giaPresente = false;}
        }
        return giaPresente;
    }

    //Crea una lista di duplicati.
    public ArrayList<Invitato> creaListaDuplicati(){

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista_vincolati.size(); k++){

                if (t.getArraylistInvitati().contains(lista_vincolati.get(k))) {

                    lista_vincolati_solo_duplicati.add(lista_vincolati.get(k));

                }
            }
        }
        return lista_vincolati_solo_duplicati;
    }


    public ArrayList<Tavolo> tavoliNonAccessibiliLontananza(){
        ArrayList<Tavolo> tav= new ArrayList<>();

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista_vincolati.size(); k++){

                if (t.getArraylistInvitati().contains(lista_vincolati.get(k))) {

                    tav.add(t);
                    break;
                }
            }
        }
        return tav;

    }
    //Rimuove le persone già sedute a tavola, dalle persone da smistare.
    public ArrayList<Invitato> removeDuplicati(){

        daSistemare.removeAll(creaListaDuplicati());
        //lista_vincolati_senza_duplicati.addAll(daSistemare);

        return daSistemare;


    }

    //Questo metodo mette a sedere le persone vicine, considerando che alcuni sono già seduti a tavola.
    public void smistaViciniSenzaDuplicati(){


        for (Tavolo t: tavoli){


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

            if (!(i==null)){a += i.getNome()+ i.getCognome() + "\n";};
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
    /*public boolean controllaIncongruenze() {

        boolean incongruente=false;
        int max_accettabile=2;

        for (GestorePreferenzaInvitato g : lista_vincoli){

            for (int k=0; k<lista_vincolati.size(); k++){

                if (!((g.getPreferenza().equals(preferenza)) && g.getLista_vincolati().contains(lista_vincolati.get(k)) && max_accettabile>0)){

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
    }*/


    public ArrayList<GestorePreferenzaInvitato> getListaVincoli(){
        return lista_vincoli;
    }

    public void aggiungiListaVincoli(ArrayList<GestorePreferenzaInvitato> gpi){
        lista_vincoli.addAll(gpi);
    }
}
