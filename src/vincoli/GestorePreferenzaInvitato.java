package vincoli;

import com.sun.org.apache.xpath.internal.SourceTree;
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
            if (t.getDisponibile()==true && t.getNumPosti()>=daSistemare.size() && daSistemare.size()==lista_vincolati.size()) {
                t.addAllGuests(daSistemare);
                break;
            }
            else if(t.getDisponibile()==true && t.getNumPosti()>=daSistemare.size() && daSistemare.size()<lista_vincolati.size() && t==tavoliDispVincoloVicinanza()){
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


    //Questo metodo controlla se ci sono abbastanza tavoli liberi per creare questo vincolo di vicinanza.
    public Tavolo tavoliDispVincoloVicinanza(){

        Tavolo tavoliDispVic= null;

        for (Tavolo t: tavoli){
            for (int k=0; k<lista_vincolati.size(); k++){
                if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) || t.mostraID_Invitati().contains(lista_vincolati.get(k).getID_Inv())){
                    tavoliDispVic=t;
                }
            }
        }
        return tavoliDispVic;

    }


    //Crea una lista di duplicati.
    public ArrayList<Invitato> creaListaDuplicati(){

        for (Tavolo t : tavoli){

            for (int k = 0; k < lista_vincolati.size(); k++){

                if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) || t.mostraID_Invitati().contains(lista_vincolati.get(k).getID_Inv())) {

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

                if (t.getArraylistInvitati().contains(lista_vincolati.get(k)) || t.mostraID_Invitati().contains(lista_vincolati.get(k).getID_Inv())) {

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



    public ArrayList<GestorePreferenzaInvitato> getListaVincoli(){
        return lista_vincoli;
    }
}
