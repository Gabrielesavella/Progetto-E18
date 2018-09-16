package tester;

import database.*;
import gui.finestre.FinestraLogin;

public class TesterGui {
    public static void main(String[] args) throws DatabaseNullException, DatabaseException {

        // inizializzazione parte grafica
        FinestraLogin frame= new FinestraLogin();
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
