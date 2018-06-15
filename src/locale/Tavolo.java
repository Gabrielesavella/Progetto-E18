package locale;
import  vincoli.*;
import persone.*;

import java.util.ArrayList;
import java.util.Arrays;


public class Tavolo {
    /*
     * aggiunta dell'attributo assegnamenti, che verrà modificato in base
     * agli assegnamenti dei posti disponibili negli invitati
     * @author Gabrielesavella
     */

    private int num_posti,assegnamenti = 0;
    private boolean interno;
    private String id_tavolo;
    private boolean disponibile;
    private ArrayList<Invitato> AssegnamentiTavolo;
    private SpecificaTavolo tipoTavolo;
    private final int postiTot;

    /*mettiamo due costruttori, in cui specifichiamo che il tavolo
    (almeno nella fase iniziale del progetto) è interno al locale.
    Nel costruttore 1, il tavolo ha un numero fisso di posti .
    Nel costruttore 2, il tavolo ha un numero di posti deciso da programma
    ( in modo da rendere più flessibile il programma )
    @author Salvi
     */
    public Tavolo(String id_tavolo){
        disponibile = true;
        this.id_tavolo = id_tavolo;
        num_posti = 6;
        AssegnamentiTavolo = new ArrayList<Invitato>(num_posti);
        interno = true;
        postiTot=num_posti;
    }
    /*CORREZIONE: nel costruttore1 va inizializzato l'array degli invitati, in modo che
     * se si acceda al metodo assegnaposti l'array venga inizializzato,
     *  nel costruttore2 ho aggiunto la memorizzazione del dato
     * numeroposti @author Gabrielesavella
     */
    public Tavolo (String id_tavolo, int num_posti){
        this.id_tavolo = id_tavolo;
        this.num_posti = num_posti;
        interno = true;
        disponibile = true;
        AssegnamentiTavolo = new ArrayList<Invitato>(num_posti);
        postiTot=num_posti;
    }


    /*
    questo metodo aggiunge un solo invitato ( sotto il metodo per aggiungere una lista di invitati)
    @author: Gabrielesavella
     */
    public void addGuest(Invitato Guest){
        /*
        se il numero di posti è maggiore di zero il tavolo è disponibile , quando il numero di posti cala a zero,
         la disponibilità del tavolo viene meno.
         */
        if (disponibile) {
            AssegnamentiTavolo.add(Guest);
            num_posti--;
        }
        // controllo quanti posti sono rimasti se sono zero metto il tavolo non disponible
        if(num_posti == 0){
            endAssignment();
        }
    }

    /*
    in questo metodo passo la lista degli invitati che può stare in un determinato tavolo
     */
    public void addAllGuests(ArrayList<Invitato> guests){

        if (disponibile && guests.size()<= num_posti) {
            AssegnamentiTavolo.addAll(guests);
            num_posti = num_posti - guests.size();
        }
        // controllo quanti posti sono rimasti se sono zero metto il tavolo non disponible
        if(num_posti == 0){
            endAssignment();
        }
    }

    /*
       questo metodo rimuove un solo invitato, quindi i posti disponibili viene incrementato e il tavolo diventa di
        nuovo disponibile
         */

    public void removeGuest(Invitato in){
        AssegnamentiTavolo.remove(in);
        num_posti++;
        openAssignment();
    }

    public String showInvitati(){
        String invitatiTavolo = "";
        for (Invitato i : AssegnamentiTavolo){
            invitatiTavolo += i.getNome() + i.getCognome() + "\n";
        }
        return "Tavolo: " + getIDTavolo() + "\n\n"+ invitatiTavolo;
    }

    public final int getPostiTot(){
        return postiTot;
    }

    public int mostraInvitatiSeduti(){
        int seduti = 0;
        for (Invitato i : AssegnamentiTavolo){
            seduti++;
        }
        return seduti;
    }
    /*
    questo metodo rimuove tutti gli invitati dal tavolo, successivamente rende disponibile il tavolo e aggiorna il numero
    di posti
     */
    public void removeAllGuests(ArrayList <Invitato> Guests){
        AssegnamentiTavolo.removeAll(Guests);
        num_posti = num_posti + Guests.size();
        openAssignment();
    }



    public boolean getInterno(){
        return interno;
    }


    public String getIDTavolo(){
        return id_tavolo;
    }

    public boolean getDisponibile(){
        return disponibile;
    }

    public int getNumPosti(){
        return num_posti;
    }


    /*Restituisce l'array di Invitati per ogni tavolo.*/
    public ArrayList<Invitato> getAssegnamentiTavolo(){
        return AssegnamentiTavolo;
    }


    /*Restituisce l'arraylist di Invitati per ogni tavolo.*/
    public ArrayList<Invitato> getArraylistInvitati(){


        return AssegnamentiTavolo;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "num_posti=" + num_posti +
                ", assegnamenti=" + assegnamenti +
                ", interno=" + interno +
                ", id_tavolo='" + id_tavolo + '\'' +
                ", disponibile=" + disponibile +
                ", arrPostiTavolo=" + ( AssegnamentiTavolo== null ? null : Arrays.asList(AssegnamentiTavolo)) +
                ", tipoTavolo=" + tipoTavolo +
                '}';
    }

    public void endAssignment(){
        disponibile = false;
    }

    public void openAssignment(){
        disponibile = true;
    }

}