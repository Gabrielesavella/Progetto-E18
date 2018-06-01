package locale;
import  vincoli.*;
import persone.*;


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
        arrPostiTavolo = new Invitato[num_posti];
        interno = true;
        disponibile = true;
        this.num_posti = num_posti;
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
