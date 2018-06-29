

package persone;

import java.util.Random;

/*@author Marco Maffoni,Gabriele Savella*/
public class Invitato {

    private String ID_Inv;
    private String nome,cognome;
    private int eta;
    /*
    @parametri:
    ID_inv è l'identificativo dell'invitato, composto dalle prime tre cifre del nome e del cognome + un numero random intero compreso tra 1 e 1000
    nome è il nome dell'invitato
    cognome è il cognome dell'invitato
     */
    public Invitato (String nome,String cognome,int eta){

        this.cognome = cognome;
        this.nome = nome;
        this.eta=eta;
        this.ID_Inv=setID_Inv();
    }

    public String getID_Inv() {
        return ID_Inv;
    }

    public String setID_Inv (){

    String univoco = "";
    String n= nome.substring(0,3);
    String c= cognome.substring(0,3);
    Random r= new Random();
    int k= r.nextInt(1000);
    String a = Integer.toString(k);
    univoco= n + c + a;
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