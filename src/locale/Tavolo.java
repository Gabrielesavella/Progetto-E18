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
    private boolean disponibile = true;
    private Invitato[] arrPostiTavolo;
    private SpecificaTavolo tipoTavolo;

    /*mettiamo due costruttori, in cui specifichiamo che il tavolo
    (almeno nella fase iniziale del progetto) è interno al locale.
    Nel costruttore 1, il tavolo ha un numero fisso di posti .
    Nel costruttore 2, il tavolo ha un numero di posti deciso da programma
    ( in modo da rendere più flessibile il programma )
    @author Salvi
     */
    public Tavolo(String id_tavolo){
        this.id_tavolo = id_tavolo;
        this.num_posti = 6;
        arrPostiTavolo = new Invitato[num_posti];
        interno = true;
    }
    /*CORREZIONE: nel costruttore1 va inizializzato l'array degli invitati, in modo che 
     * se si acceda al metodo assegnaposti l'array venga inizializzato,
     *  nel costruttore2 ho aggiunto la memorizzazione del dato 
     * numeroposti @author Gabrielesavella
     */
    public Tavolo (String id_tavolo, int num_posti){
        this.id_tavolo = id_tavolo;
        arrPostiTavolo = new Invitato[num_posti];
        interno = true;
        disponibile = true;
        this.num_posti = num_posti;
    }

    public ArrayList<Invitato> addInvitato(ArrayList<Invitato> in){
        if (num_posti>0) {
            for (Invitato i : in) {

                if(arrPostiTavolo==null) {

                    arrPostiTavolo[num_posti] = i;
                    num_posti--;
                    in.remove(i);

                }
            }
        }
        return in;
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
    public Invitato[] getArrayInvitati(){
        return arrPostiTavolo;
    }


    /*Restituisce l'arraylist di Invitati per ogni tavolo.*/
    public ArrayList<Invitato> getArraylistInvitati(){

        ArrayList<Invitato> invitatiPerTavolo = new ArrayList<>();

        for (Invitato i : arrPostiTavolo){
            invitatiPerTavolo.add(i);
        }
        return invitatiPerTavolo;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "num_posti=" + num_posti +
                ", assegnamenti=" + assegnamenti +
                ", interno=" + interno +
                ", id_tavolo='" + id_tavolo + '\'' +
                ", disponibile=" + disponibile +
                ", arrPostiTavolo=" + (arrPostiTavolo == null ? null : Arrays.asList(arrPostiTavolo)) +
                ", tipoTavolo=" + tipoTavolo +
                '}';
    }
}
