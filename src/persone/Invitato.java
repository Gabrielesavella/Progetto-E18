
package persone;

import java.util.Random;

/*@author Marco Maffoni,Gabriele Savella, Salvatore Parisi*/
public class Invitato {

    private String ID_Inv;
    private String nome,cognome;
    private int eta;

    /*Il costruttore di Invitato a 3 parametri richiama il costruttore a 4 parametri fornendogli i 3 suddetti parametri + ID_Inv
    * creato dal metodo statico setID_Inv*/

    public Invitato (String nome,String cognome,int eta){
        this(Invitato.setID_Inv(nome, cognome), nome, cognome, eta);

    }

    public Invitato (String ID_Inv, String nome, String cognome, int eta){
        this.cognome = cognome;
        this.nome = nome;
        this.eta=eta;
        this.ID_Inv=ID_Inv;

    }

    public String getID_Inv() {
        return ID_Inv;
    }

     /*
    @parametri:
    ID_inv è l'identificativo dell'invitato, composto dalle prime tre cifre del nome e del cognome dell'invitato + un numero random intero compreso tra 1 e 1000
    */

    public static String setID_Inv (String nom, String cog){

        String ID_Inv = "";
        String n= nom.substring(0,3);
        String c= cog.substring(0,3);
        Random r= new Random();
        int k= r.nextInt(1000);
        String a = Integer.toString(k);
        ID_Inv= n + c + a;


        return ID_Inv;
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