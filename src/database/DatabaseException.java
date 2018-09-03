package database;
/**
 * @author Salvatore Parisi
 */

//Eccezione che avverte che il valore che si sta cercando di inserire Ã¨ giÃ  presente nella Table

public class DatabaseException extends Exception {

    public DatabaseException(String msg){
        super(msg);

    }

    public DatabaseException(String nomeID, String valoreID, String nomeTable){
        this("L'ID_" + nomeID + " " + valoreID + " e' gia'  presente nella table " + nomeTable);

    }

}