package tester;

import database.*;
import gui.finestre.FinestraLogin;
import locale.GestoreLocale;
import locale.Tavolo;
import sun.util.calendar.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TesterGui {
    public static void main(String[] args) throws DatabaseNullException, DatabaseException {
        ConnessioneDB connessione = new ConnessioneDB();
        ArrayList <GestoreLocale> locali= new ArrayList<>();

        locali = connessione.getLocali();
        //parte grafica
        FinestraLogin frame= new FinestraLogin(locali);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
