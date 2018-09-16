package tester;

/**
 * @author Salvatore Parisi
 */

import database.ConnessioneDB;
import database.DatabaseException;
import database.DatabaseNullException;
import locale.*;
import persone.Cliente;
import persone.Invitato;
import vincoli.PreferenzaInvitato;
import vincoli.SpecificaTavolo;

import java.util.Scanner;


public class TesterDB {
    public static void main(String args[]) throws DatabaseException, DatabaseNullException {

        ConnessioneDB connetto= new ConnessioneDB();
        System.out.println("Stato della connessione prima del tentativo di connessione: " + connetto.checkConn());
        connetto.startConn();
        System.out.println("Stato della connessione dopo il tentativo di connessione: " + connetto.checkConn());

        Scanner sc= new Scanner(System.in);


        System.out.println("\n Fornire i dati relativi al nuovo Cliente da inserire nel database: \n ID, Nome, Cognome, E-mail, Password");
        connetto.inserisciDatiCliente(sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
        System.out.println("\n Cliente inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo Locale da inserire nel database: \n ID_Locale, numero di Tavoli, orario di apertura, orario di chiusura, giorno di chiusura");
        connetto.inserisciDatiLocale(sc.next(), sc.nextInt(), sc.next(), sc.next(), sc.next());
        System.out.println("\n Locale inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo Tavolo da inserire nel database: \n ID_Locale, ID_Tavolo, Numero di posti");
        connetto.inserisciTavoli(sc.next(), sc.next(), sc.nextInt());
        System.out.println("\n Tavolo inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo Evento da inserire nel database: \n ID_Cliente, ID_Evento, Data dell'evento, Nome del Locale, Numero di invitati");
        connetto.inserisciDatiEvento(sc.next(), sc.next(), sc.next(), sc.next(), sc.nextInt());
        System.out.println("\n Evento inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo Invitato da inserire nel database: \n ID_Evento, ID_Invitato, Nome, Cognome, Eta");
        connetto.inserisciDatiInvitato(sc.next(), sc.next(), sc.next(), sc.next(), sc.nextInt());
        System.out.println("\n Invitato inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo VincoloTavolo da inserire nel database: \n ID_Evento, ID_Invitato, ed elenco vincoli: tavoloOnore, difficoltaMotorie, vegetariano, vicinoTV, bambini, tavoloIsolato");
        connetto.inserisciVincoliTavolo(sc.next(), sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        System.out.println("\n VincoloTavolo inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo VincoloInvitato da inserire nel database: \n ID_Evento, ID_Invitato, vincolo star Vicino, vincolo star Lontano");
        connetto.inserisciVincoloInvitati(sc.next(), sc.next(), sc.next(), sc.next());
        System.out.println("\n VincoloInvitato inserito con successo nel database!");

        System.out.println("\n Fornire i dati relativi al nuovo elemento da cancellare nel database: \n nome Table, nome della Chiave, valore");
        connetto.deleteEntry(sc.next(), sc.next(), sc.next());
        System.out.println("\n Elemento cancellato con successo dal database!");



        System.out.println("\n Inserire l'ID_Cliente relativo al cliente che si vuole cercare");
        Cliente cli=connetto.getCliente (sc.next());
        System.out.println("\n Nome: " + cli.getNome() + "  Cognome: " + cli.getCognome() + "  Email: " + cli.getEmail() + "  Password: " + cli.getPsw());

        System.out.println("\n Inserire l'ID_Locale relativo al locale che si vuole cercare");
        String loc= (sc.next());
        Locale l=connetto.getLocale (loc);


        System.out.println("\n Inserire l'ID_Tavolo relativo al tavolo che si vuole cercare");
        Tavolo t=connetto.getTavoloSingolo(sc.next());
        System.out.println("\n Numero di posti: " + t.getNumPosti());

        System.out.println("\n Inserire l'ID_Evento relativo all'evento che si vuole cercare");
        Evento ev=connetto.getEventoSingolo (sc.next());
        System.out.println("\n Locale: " + ev.getNomeLocale() + "  Numero di invitati: " + ev.getNumInvitati() + "  Data Evento: " + ev.getDataEvento());

        System.out.println("\n Inserire l'ID_Invitato relativo all'invitato che si vuole cercare");
        Invitato i=connetto.getInvitatoSingolo (sc.next());
        System.out.println("\n Nome: " + i.getNome() + "  Cognome: " + i.getCognome() + "  Eta': " + i.getEta());

        System.out.println("\n Inserire l'ID_Invitato relativo all'invitato di cui si vuole cercare il VincoloTavolo");
        SpecificaTavolo s =connetto.getVincoloTavoloSingolo (sc.next());
        System.out.println("\n Bambino: " + s.getBambini() + "  Difficolta' Motorie: " + s.getDifficoltaMotorie() + "  Tavolo Isolato: "  + s.getTavoloIsolato() +
                "  Tavolo Onore:  " + s.getTavoloOnore() + "  Vegetariano: " + s.getVegetariano() + "  Vicino TV: " + s.getVicinoTV());

        System.out.println("\n Inserire l'ID_Invitato relativo all'invitato di cui si vuole cercare il VincoloInvitati");
        PreferenzaInvitato p =connetto.getVincoloInvitatoSingolo (sc.next());
        System.out.println("\n Star vicino a : " + p.getVicino() + "  Star Lontano a: " + p.getLontano());



        connetto.closeConn();
        System.out.println("Stato della connessione dopo il tentativo di chiusura di essa: " + connetto.checkConn());

    }

}
