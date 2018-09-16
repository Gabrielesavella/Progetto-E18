

package persone;


/*@author Marco Maffoni,Gabriele Savella*/
public class Invitato {

    private String ID_Inv;
    private String nome,cognome;
    private int eta;


    public Invitato (String nome,String cognome,int eta){
        this(Invitato.setID_Inv(nome, cognome,eta), nome, cognome, eta);

    }

    public Invitato (String ID_Inv, String nome,String cognome,int eta){

        this.ID_Inv=ID_Inv;
        this.cognome = cognome;
        this.nome = nome;
        this.eta=eta;
    }

    public String getID_Inv() {
        return ID_Inv;
    }

    public static String setID_Inv (String nome, String cognome,int eta){

    String univoco = "";
    String n= nome.substring(0,3);
    String c= cognome.substring(0,3);
    univoco= n + c + eta;
    return univoco;
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