package locale;
import  vincoli.*;
import persone.*;


public class Tavolo {
    
    private int num_posti;
    private boolean interno;
    private String id_tavolo;
    private boolean disponibile;
    private Invitato[] arrPostiTavolo;
    private SpecificaTavolo tipoTavolo;

    /*mettiamo due costruttori, in cui specifichiamo che il tavolo
    (almeno nella fase iniziale del progetto) è interno al locale.
    Nel costruttore 1, il tavolo ha un numero fisso di posti .
    Nel costruttore 2, il tavolo ha un numero di posti deciso da programma
    ( in modo da rendere più flessibile il programma )
     */
    public Tavolo(String id_tavolo){
        num_posti = 6;
        interno = true;
    }
    public Tavolo (String id_tavolo, int num_posti){
        arrPostiTavolo = new Invitato[num_posti];
        interno = true;
        disponibile = true;
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
}
