
package persone;

/*@author Marco Maffoni,Gabriele Savella*/
public class Invitato {

    private String cf,nome,cognome;
    private int età;
    /*
    @parametri:
    cf è il codice fiscale dell'invitato
    nome è il nome dell'invitato
    cognome è il cognome dell'invitato
     */
    public Invitato (String nome,String cognome,int età){//String cf,
        //this.cf = cf;
        this.cognome = cognome;
        this.nome = nome;
        this.età = età;
    }

    public String getCf() {
        return cf;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEtà() {
        return età;
    }

    @Override
    public String toString() {
        return "Invitato " +
                cf +
                ", nome: " + nome +
                ", cognome: " + cognome  +
                ", età: " + età +
                '\n';
    }
}