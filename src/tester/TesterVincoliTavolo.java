package tester;

import database.ConnessioneDB;
import database.DatabaseException;
import database.DatabaseNullException;
import facade.Facade;
import locale.*;
import locale.Locale;
import persone.*;
import vincoli.*;

import java.util.*;

public class TesterVincoliTavolo {

    public static void main(String[] args){

        Locale locale=null;
        ArrayList<Tavolo> tavoli;
        ConnessioneDB connetto = new ConnessioneDB();
        ArrayList<Tavolo> tavoli_finali;

        try {
            Facade.getInstance().inserisciDatiEvento("BruWay39","TesterJUnit", "13/02/2018", "Fortezza della Solitudine", 5);

            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "marmaf23", "marco", "maffoni", 23);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "marlec23", "marco", "lecce", 23);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "gabsav24", "gabriele", "savella", 24);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "salpar31", "salvatore", "parisi", 31);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "feddor23", "federico", "dorigo", 23);

            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "marmaf23", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "marlec23", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "gabsav24", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "salpar31", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "feddor23", 0, 0, 0, 0, 0, 0);

            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "marmaf23", "marlec23", "gabsav24");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "marlec23", " ", " ");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "gabsav24", " ", " ");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "salpar31", " ", " ");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "feddor23", "salpar31", "gabsav24");

            locale = Facade.getInstance().getLocale("Fortezza della Solitudine");
            locale.getTavoliLocale().addAll(Facade.getInstance().getTavoli("Fortezza della Solitudine"));

        } catch (DatabaseException e1) {
            e1.printStackTrace();
        } catch (DatabaseNullException e1) {
            e1.printStackTrace();
        }

        //mostra tavoli e invitati vincolati
        GestoreVincoliTavolo gestoreTavolo = new GestoreVincoliTavolo("TesterJUnit", locale);

        tavoli = gestoreTavolo.getTavoliTotali();

        //mostra tavoli e invitati non vincolati
        CreatePreferenza create = new CreatePreferenza("TesterJUnit", tavoli);

        create.smista();

        tavoli_finali = create.getTavoliUtilizzati();

        for (Tavolo t : tavoli_finali){
            System.out.println(t.showInvitati());
        }

        Facade.getInstance().removeVincoliOnly(Facade.getInstance().getEvento("TesterJUnit"));
        Facade.getInstance().deleteEvento("TesterJUnit");


    }
}
