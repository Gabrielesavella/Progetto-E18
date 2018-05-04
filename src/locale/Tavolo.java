package locale;

import persone.Invitato;
import vincoli.SpecificaTavolo;

public class Tavolo {
    
    private int num_posti;
    private boolean interno;
    private String id_tavolo;
    private boolean disponibile;
    private Invitato[] arrPostiTavolo;
    private SpecificaTavolo tipoTavolo;
    
    public Tavolo (String id_tavolo, int num_posti){
        arrPostiTavolo = new Invitato[num_posti];
        disponibile = true;
        interno=true;
    }
    
    public boolean getInterno() {
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
