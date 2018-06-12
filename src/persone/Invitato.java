
package persone;

/*@author Marco Maffoni,Gabriele Savella*/
public class Invitato {

    private String ID_Inv;
    private String nome,cognome;
    private int eta;
    /*
    @parametri:
    ID_inv è l'identificativo dell'invitato, composto da NomeEvento + contatore
    nome è il nome dell'invitato
    cognome è il cognome dell'invitato
     */
    public Invitato (String nome,String cognome,int eta){
        this.ID_Inv="";
        this.cognome = cognome;
        this.nome = nome;
        this.eta=eta;

        ;
    }

    public void setID_Inv(String ID_Inv) {
        this.ID_Inv = ID_Inv;
    }

    public String getID_Inv() {
                return ID_Inv
                ;
    }



    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEta() {
        return eta;
    }

    @Override
    public String toString() {
        return "Invitato " +
                ID_Inv +
                ", nome: " + nome +
                ", cognome: " + cognome  +
                ", età: " + eta +
                '\n';
    }
}