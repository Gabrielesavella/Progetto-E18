package tester;

import database.ConnessioneDB;
import locale.Evento;
import locale.Locale;
import persone.Cliente;

import java.sql.Date;
import java.util.ArrayList;


public class TesterDB {
    public static void main(String args[]) {
        String ID_Cliente="darvad001";
        String nomeCliente="Darth";
        String cognomeCliente="Vader";
        String password="Padme";
        String ID_Evento="Cena con Imperatore";
        String dataEvento= "21-01-2021";
        String nomeLocale= "La Morte Nera";
        int numeroInvitati= 300;
        String ID_Inv="Obiwan002";
        String nomeInv= "ObiWan";
        String cognomeInv= "Kenobi";
        String starVicino= "LukSky003, LeiMor004";
        String starLontano = "ImpPal005";
        String oraAp = "09,00";
        String oraChi="02,00";
        String gioChi="Lunedì";
        int numMaxTavoli=42;
        int etaInv= 50;
        String id_tavolo="tavola rotonda";
        int numMaxPosti= 10;

        ConnessioneDB connetto= new ConnessioneDB();
        System.out.println("Stato della connessione prima del tentativo di connessione: " + connetto.checkConn());
        connetto.startConn();
        System.out.println("Stato della connessione dopo il tentativo di connessione: " + connetto.checkConn());


        //connetto.inserisciTavoli(nomeLocale, id_tavolo, numMaxPosti );
        //connetto.inserisciVincoliTavolo(ID_Evento, ID_Inv, 1, 0, 1 , 0, 0 , 0 );
        //connetto.inserisciVincoloInvitati(ID_Evento, ID_Inv, starVicino, starLontano);
        //connetto.inserisciDatiLocale(nomeLocale, numMaxTavoli, oraAp, oraChi, gioChi);
        //connetto.inserisciDatiCliente(ID_Cliente, nomeCliente, cognomeCliente, password);
        //connetto.inserisciDatiEvento(ID_Cliente, ID_Evento, dataEvento, nomeLocale, numeroInvitati);
        //connetto.inserisciDatiInvitato(ID_Evento, ID_Inv, nomeInv, cognomeInv, etaInv);

        /*
        ArrayList<Locale> locali=connetto.getLocali();
        for(Locale l: locali){
            System.out.println(l.getId_locale() + " " + l.getNumMaxPosti() + " " + l.getOraApertura() + " " + l.getOraChiusura() + " " + l.getGiornodichiusura());
        }
        */


        /*
        Locale l=connetto.getLocale(nomeLocale);
        System.out.println(l.getId_locale() + " " + l.getNumMaxPosti() + " " + l.getOraApertura() + " " + l.getOraChiusura() + " " + l.getGiornodichiusura());
        */


       /*
        ArrayList<Cliente> clienti= connetto.getClienti();
        for (Cliente cl: clienti){
            System.out.println(cl.getID()  + " " + cl.getNome()  + " " + cl.getCognome()  + " " + cl.getPsw());
        }
        */

       /*
        ArrayList<Evento> eventi= connetto.getEvento(nomeLocale);
        for (Evento ev: eventi){
            System.out.println(ev.getName() + ev.getDataEvento().toString() + ev.getNomeLocale() + ev.getNumInvitati());
        }
        */

       /*
       Evento ev= connetto.getEventoSingolo(ID_Evento);
        System.out.println(ev.getName() + ev.getDataEvento().toString() + ev.getNomeLocale() + ev.getNumInvitati());
        */

        //connetto.getInvitato(ID_Inv);
        //connetto.getVincoloInvitato(ID_Inv);
        //connetto.getVincoloTavolo(ID_Inv);
        connetto.closeConn();
        System.out.println("Stato della connessione dopo il tentativo di chiusura di essa: " + connetto.checkConn());
    }

}
