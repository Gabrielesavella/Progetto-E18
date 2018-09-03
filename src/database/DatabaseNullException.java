package database;
/**
 * @author Salvatore Parisi
 */

//Eccezione che avverte che il valore di cui si necessita per l'inserimento di dati non Ã¨ presente nella table apposita, quindi genererebbe errore

public class DatabaseNullException extends Exception {

    public DatabaseNullException(String msg){
        super(msg);

    }

    public DatabaseNullException(String nomeID, String valoreID, String nomeTable){
        this("Il " + nomeID + " " + valoreID + " non e' presente nella table " + nomeTable);

    }
}
