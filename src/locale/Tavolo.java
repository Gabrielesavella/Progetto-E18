package locale;
import  vincoli.*;
import persone.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;


public class Tavolo implements Comparable{
    /*
     * aggiunta dell'attributo assegnamenti, che verrà modificato in base
     * agli assegnamenti dei posti disponibili negli invitati
     * @author Gabrielesavella
     */

    private int num_posti,assegnamenti;
    private String id_tavolo,id_tavoloReale, ID_Locale;
    private boolean disponibile;
    private ArrayList<Invitato> AssegnamentiTavolo;
    private int postiTot;



    public Tavolo (String ID_Locale, String id_tavolo, int num_posti){
        assegnamenti = 0;
        this.ID_Locale=ID_Locale;
        this.id_tavolo = id_tavolo;
        id_tavoloReale=id_tavolo;
        this.num_posti = num_posti;
        disponibile = true;
        AssegnamentiTavolo = new ArrayList<>(num_posti);
        postiTot=num_posti;
    }
    //metodo utilizzato per il tavolo
    public Tavolo (String id_tavolo, int num_posti){
        assegnamenti = 0;
        this.id_tavolo = id_tavolo;
        this.num_posti = num_posti;
        disponibile = true;
        AssegnamentiTavolo = new ArrayList<>(num_posti);
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
        String idTav= id_tavolo;
        if (!id_tavolo.equals(id_tavoloReale))
            idTav+=" "+id_tavoloReale;

        int k=1;
        for (int i=0; i<AssegnamentiTavolo.size(); i++) {
            if (AssegnamentiTavolo.get(i)!=null)
                invitatiTavolo += " "+k+") "+AssegnamentiTavolo.get(i).getNome() + " " + AssegnamentiTavolo.get(i).getCognome() + "\n";
                k++;
        }

        return "♦ Tavolo: " + idTav + "  ▬  Numero posti: " + getPostiTot() + " ♦\n\n" + invitatiTavolo;

    }

    public final int getPostiTot(){
        return postiTot;
    }

    public int contaInvitatiSeduti(){
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


    public String getIDTavolo(){
        return id_tavolo;
    }

    public boolean getDisponibile(){
        return disponibile;
    }

    public int getNumPosti(){
        return num_posti;
    }

    /*i seguenti due metodi sono nescessari per la gestione dei vincoli*/

    public void setDisponibile(boolean disp){
        this.disponibile=disp;
    }

    public void setId_tavolo(String id_tavolo) {
        this.id_tavolo = id_tavolo;
    }

    public void setNum_posti(int numero){
        this.num_posti=numero;
    }


    /*Restituisce l'array di Invitati per ogni tavolo.*/
    public ArrayList<Invitato> getAssegnamentiTavolo(){
        return AssegnamentiTavolo;
    }


    /*Restituisce l'arraylist di Invitati per ogni tavolo.*/
    public ArrayList<Invitato> getArraylistInvitati(){


        return AssegnamentiTavolo;
    }

    public ArrayList<String> mostraID_Invitati() {
        ArrayList<String> stringhe=new ArrayList<>();
        for (Invitato i : AssegnamentiTavolo){
            if (!(i==null)) {stringhe.add(i.getID_Inv());}
        }
        return stringhe;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "num_posti=" + num_posti +
                ", assegnamenti=" + assegnamenti +
                ", id_tavolo='" + id_tavolo + '\'' +
                ", disponibile=" + disponibile +
                ", arrPostiTavolo=" + ( AssegnamentiTavolo== null ? null : Arrays.asList(AssegnamentiTavolo)) +
                '}';
    }

    public void endAssignment(){
        disponibile = false;
    }

    public void openAssignment(){
        disponibile = true;
    }


    @Override
    public int compareTo(Object compareTav) {
        int compareNumPosti = ((Tavolo) compareTav).getNumPosti();
        return this.num_posti - compareNumPosti;
    }

    public String getID_Loc() {
        return ID_Locale;
    }

    public String getRealID_Tav(){ return id_tavoloReale; }
}