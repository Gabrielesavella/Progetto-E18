/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.scene.input.DataFormat;
import locale.*;
import locale.Locale;
import persone.Cliente;
import persone.Invitato;
import vincoli.PreferenzaInvitato;
import vincoli.SpecificaTavolo;

import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

import static java.sql.JDBCType.NULL;


/**
 * @author Salvatore Parisi
 */
public class ConnessioneDB {// crea la connessione col database "smistamento_posti" in entrata ed uscita

    private final String dbUser = "root"; //nome utente
    private final String dbPwd = "21187"; //password utente
    private final String dbDriver = "com.mysql.cj.jdbc.Driver";  //il driver per collegarsi al DB ?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false
    private final String dbUrl = "jdbc:mysql://127.0.0.1:3306/smistamento_posti?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false"; //url del database e schema
    private Connection conn = null; //si crea una nuova connessione, per adesso nulla
    private boolean openConn = false; //variabile per verificare se la connessione col DB Ã¨ aperta o meno



    public void startConn() { //metodo per inizializzare la connessione

        try {

            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            openConn = true;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkConn() { //controlla se la connessione Ã¨ avvenuta

        return this.openConn;
    }

    public void closeConn() { //metodo per chiudere la connessione
        try {
            conn.close();
            openConn = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /*Ho creato cinque TABLE:
    1) Clienti (chiave: ID_Cliente)
    2) Eventi (chiavi: ID_Cliente e ID_Evento)
    3) Locali (chiave: ID_locale)
    4) Tavoli (chiave: ID_tavolo)
    5) Invitati (chiavi ID_Evento e ID_Invitato)
    6) Specifica_Tavolo, vincoli relativi alla scelta del tavolo  (chiavi: ID_Evento ed ID_Invitato)
    7) Preferenze_Invitato, vincoli relativi a quali invitati avere vicino o lontano, (chiavi: ID_Evento ed ID_Invitato)
    Per ognuna di queste table c'Ã¨ un metodo:
     - inserisciDatiNomedellaTable, che inserisce nuovi valori nei campi della Table.
     - getNomeTable, che seleziona e ritorna i valori della Table, sempre secondo la chiave primaria selezionata, da inserire nel costruttore corrispettivo
    // aggiunta Lecce
    8) Agende (chiave:IDLocale,GregorianCalendar,Arraylist<Tavolo> tavoli)
    */

    //Metodo che inserisce nuovi dati nella table Cliente.

    public void inserisciDatiCliente(String ID_Cliente, String nomeCliente, String cognomeCliente, String emailCliente, String passwordCliente) throws DatabaseException {
        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "clienti";

        //controllo che l'ID_Cliente non sia giÃ  presente nel Database

        if(!(getCliente(ID_Cliente)==null)){
            throw new DatabaseException("Cliente", ID_Cliente, table);

        }

        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Cliente`, `NomeCliente`, `CognomeCliente`, `EmailCliente`, `PasswordCliente`) VALUES ('" + ID_Cliente + "','" + nomeCliente + "','" + cognomeCliente + "','" + emailCliente + "','" + passwordCliente + "');";

            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //teoricamente chiudendo il resultset si dovrebbe chiudere anche lo statement, ma preferisco essere piÃ¹ preciso
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();
    }




    // metodo che inserisce i dati del singolo invitato nella table "Locali"

    public void inserisciDatiLocale (String ID_Locale, int numMaxtavoli, String oraApertura, String oraChiusura, String giornoChiusura) throws DatabaseException {
        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "locali";

        if(!(getLocale(ID_Locale)==null)){
            throw new DatabaseException("Locale", ID_Locale, table);

        }

        if (!openConn) {
            startConn();

        }

        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Locale`, `numMaxtavoli`, `oraApertura`, `oraChiusura`, `giornoChiusura`) VALUES ('" + ID_Locale + "','" + numMaxtavoli + "','" + oraApertura + "','" + oraChiusura + "','" + giornoChiusura + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }


    // metodo che inserisce i dati di ogni tavolo nella table "Tavoli"

    public void inserisciTavoli (String ID_Locale, String ID_Tavolo, int numeroPosti) throws DatabaseException, DatabaseNullException {

        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "tavoli";

//        if (ID_Tavolo.contains("Tavolo")){
//            LocalDateTime now= LocalDateTime.now();
//            DateTimeFormatter dtf= DateTimeFormatter.ofPattern("HH:mm:ss");
//            ID_Tavolo+=dtf.format(now);
//        }
        if(!(getTavoloSingolo(ID_Tavolo)==null)){
            throw new DatabaseException("Tavolo", ID_Tavolo, table);

        }

        //Controllo che il Locale esista nella table "locali", altrimenti avrÃ² errori futuri nell'elaborazione dei dati

        if (getLocale(ID_Locale)==null){
            throw new DatabaseNullException("Locale", ID_Locale, "locali");

        }


        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Locale`, `ID_Tavolo`, `numeroPosti`) VALUES ('" + ID_Locale + "','" + ID_Tavolo + "','" + numeroPosti + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }


    //Metodo che inserisce i dati del singolo Evento nella TABLE "Eventi"

    public void inserisciDatiEvento(String ID_Cliente, String ID_Evento, String dataEvento, String nomeLocale, int numeroInvitati) throws DatabaseException, DatabaseNullException {

        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "eventi";

        if(!(getEventoSingolo(ID_Evento)==null)){
            throw new DatabaseException("Evento", ID_Evento, table);

        }

        if (getLocale(nomeLocale)==null){
            throw new DatabaseNullException("Locale", nomeLocale, "locali");

        }

        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Evento`, `ID_Cliente`, `dataEvento`, `ID_Locale`, `NumeroInvitati`) VALUES ('" + ID_Evento + "','" + ID_Cliente + "','" + dataEvento + "','" + nomeLocale + "','" + numeroInvitati + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();
    }




    // metodo che inserisce i dati del singolo invitato nella table "Invitati"

    public void inserisciDatiInvitato(String ID_Evento, String ID_Inv, String nomeInv, String cognomeInv, int etaInv) throws DatabaseException, DatabaseNullException {

        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "invitati";


        if(getEventoSingolo(ID_Evento)==null){
            throw new DatabaseNullException("Evento", ID_Evento, "eventi");

        }

        if (!openConn) {
            startConn();
        }
        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Evento`, `ID_Invitato`, `nomeInvitato`, `cognomeInvitato`, `etaInvitato`) VALUES ('" + ID_Evento + "','" + ID_Inv + "','" + nomeInv + "','" + cognomeInv + "','" + etaInv + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }




    //metodo che inserisce vincoli sul tavolo secondo uno specifico Evento e uno specifico Invitato.
    // il metodo leggere i valori 1 e 0, che in Mysql sono tradotti in TINYINT= BOOLEAN.
    // TRUE: vincolo inserito, FALSE: vincolo non inserito

    public void inserisciVincoliTavolo(String ID_Evento, String ID_Inv, int tavoloOnore, int difficoltaMotorie, int vegetariano, int vicinoTV, int bambini, int tavoloIsolato) throws DatabaseException, DatabaseNullException {

        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "specifica_tavolo";

        if(getEventoSingolo(ID_Evento)==null){
            throw new DatabaseNullException("Evento", ID_Evento, "eventi");

        }

        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Evento`, `ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('" + ID_Evento + "','" + ID_Inv + "','" + tavoloOnore + "','" + difficoltaMotorie + "','" + vegetariano + "','" + vicinoTV + "','" + bambini + "','" + tavoloIsolato + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }



    public void inserisciVincoloInvitati(String ID_Evento, String ID_Inv, String starVicino, String starLontano) throws DatabaseException, DatabaseNullException {
        startConn();

        Statement stmt = null;
        ResultSet rs = null;
        String table = "preferenza_invitato";


        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            String queryEntry = "INSERT INTO " + table + " (`ID_Evento`, `ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('" + ID_Evento + "','" + ID_Inv + "','" + starVicino + "','" + starLontano + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }

    // metodo che inserisce i dati del singolo invitato nella table "Invitati"

    public void inserisciInAgenda(String ID_Locale, String data, String tavoliOccupati,String tavoliRinominati) throws DatabaseException, DatabaseNullException {

        startConn();

        Statement stmt = null;
        ResultSet rs = null;

        //controllo su tavoli locale.

        if(getLocale(ID_Locale)==null){
            throw new DatabaseNullException("Locale", ID_Locale, "locali");

        }

        if (!openConn) {
            startConn();
        }
        try {
            stmt = conn.createStatement();//" + table + "
            String queryEntry = "INSERT INTO `agenda` VALUES ('" + ID_Locale + "','" + data + "','" + tavoliOccupati +  "','" + tavoliRinominati + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }

    // metodo che inserisce i dati del singolo invitato nella table "Invitati"

    public void inserisciInAgenda(String ID_Locale, String data, String tavoliOccupati) throws DatabaseException, DatabaseNullException {

        startConn();

        Statement stmt = null;
        ResultSet rs = null;

        //controllo su tavoli locale.

        if(getLocale(ID_Locale)==null){
            throw new DatabaseNullException("Locale", ID_Locale, "locali");

        }

        if (!openConn) {
            startConn();
        }
        try {
            stmt = conn.createStatement();//" + table + "
            String queryEntry = "INSERT INTO `agenda` VALUES ('" + ID_Locale + "','" + data + "','" + tavoliOccupati + "');";
            stmt.executeUpdate(queryEntry);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();

    }





    //prende dalla TABLE clienti i dati relativi al cliente con la specifica chiave ID_Cliente, che sono quelli da inserire nel costruttore di Cliente()

    public Cliente getCliente(String ID_Cliente) {
        startConn();

        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        Cliente cl=null;

        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM clienti WHERE `ID_Cliente`='" + ID_Cliente + "';");
            while (rs.next()) {


                String ID_Cl= rs.getString(1);
                String nomeCl=rs.getString(2);
                String cognomeCl=rs.getString(3);
                String emailCl=rs.getString(4); //Aggiunta una colonna per il campo email
                String pwdCl= rs.getString(5);

                cl= new Cliente (nomeCl, cognomeCl, ID_Cl, emailCl, pwdCl);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeConn();

        return cl;
    }


    //Metodo che ritorna l'ArrayList di Clienti

    public ArrayList<Cliente>  getClienti () {
        startConn();
        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        Cliente cl=null;
        ArrayList<Cliente> clienti= new ArrayList<>();
        if (!openConn) {
            startConn();

        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM clienti ;");

            while (rs.next()) {


                String ID_Cl= rs.getString(1);
                String nomeCl=rs.getString(2);
                String cognomeCl=rs.getString(3);
                String emailCl= rs.getString(4);
                String pwdCl= rs.getString(5);

                cl= new Cliente (nomeCl, cognomeCl, ID_Cl, emailCl, pwdCl);
                clienti.add(cl);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeConn();

        return clienti;
    }


    //metodo che ritorna il signolo Locale

    public Locale getLocale(String ID_Locale) {
        Locale l=null;
        ArrayList <Locale> locali= new ArrayList<>();

        startConn();
        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        if (!openConn) {
            startConn();

        }

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM locali WHERE `ID_Locale`='" + ID_Locale + "';");
            while (rs.next()) {

                ID_Locale= rs.getString(1);
                int numMaxtavoli=rs.getInt(2);
                String oraApertura=rs.getString(3);
                String oraChiusura= rs.getString(4);
                String giornoChiusura= rs.getString(5);

                l= new Locale(ID_Locale, numMaxtavoli, oraApertura, oraChiusura, giornoChiusura);
                locali.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        closeConn();
        return l;
    }

    //Metodo che ritorna un'ArrayList di GestoreLocale

    public ArrayList<GestoreLocale> getLocali() {
        Locale l=null;
        ArrayList <GestoreLocale> locali= new ArrayList<>();

        startConn();
        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        if (!openConn) {
            startConn();

        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM locali;");
            while (rs.next()) {

                String ID_Locale= rs.getString(1);
                int numMaxtavoli=rs.getInt(2);
                String oraApertura=rs.getString(3);
                String oraChiusura= rs.getString(4);
                String giornoChiusura= rs.getString(5);

                l= new Locale(ID_Locale, numMaxtavoli, oraApertura, oraChiusura, giornoChiusura);

                locali.add( l.gestisciLocale());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeConn();

        return locali;
    }

    //metodo che ritorna l'ArrayList di Eventi di uno specifico Locale

    public ArrayList<Evento> getEvento(String id_locale) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        Evento ev=null;
        ArrayList<Evento> eventi= new ArrayList<>();

        if (!openConn) {
            startConn();

        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM eventi WHERE `ID_Locale`='" + id_locale + "';");
            while (rs.next()) {


                String ID_Ev= rs.getString(1);
                String ID_Cl=rs.getString(2);
                String dataEv=rs.getString(3);
                String nomeLoc= rs.getString(4);
                int numInv= rs.getInt(5);

                ev= new Evento(ID_Ev, dataEv, nomeLoc, numInv);
                eventi.add(ev);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeConn();

        return eventi;
    }

    //restuituisce i dati relativi al singolo evento

    public Evento getEventoSingolo(String ID_Evento) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        Evento ev=null;
        ArrayList<Evento> eventi= new ArrayList<>();
        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM eventi WHERE `ID_Evento`='" + ID_Evento + "';");
                while (rs.next()) {


                    String ID_Ev= rs.getString(1);
                    String  ID_Cl=rs.getString(2);
                    String dataEv=rs.getString(3);
                    String nomeLoc= rs.getString(4);
                    int numInv= rs.getInt(5);

                    ev= new Evento(ID_Ev, dataEv, nomeLoc, numInv);
                    eventi.add(ev);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        closeConn();
        return ev;
    }

    //Metodo che ritorna l'ArrayList di Tavoli dello specifico Locale

    public ArrayList<Tavolo> getTavolo (String ID_Locale) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        Tavolo t;
        ArrayList<Tavolo> tavoli= new ArrayList<>();
        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM tavoli WHERE `ID_Locale`='" + ID_Locale + "';");
                while (rs.next()) {


                    ID_Locale= rs.getString(1);
                    String ID_Tavolo=rs.getString(2);
                    int numeroPosti=rs.getInt(3);


                    t= new Tavolo (ID_Locale, ID_Tavolo, numeroPosti);
                    tavoli.add(t);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        closeConn();
        return tavoli;
    }

    //metodo che ritorna i dati relativi al singolo Tavolo

    public Tavolo getTavoloSingolo (String ID_Tavolo) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        Tavolo t=null;

        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM tavoli WHERE `ID_Tavolo`='" + ID_Tavolo + "';");
                while (rs.next()) {


                    String ID_Locale= rs.getString(1);
                    ID_Tavolo=rs.getString(2);
                    int numeroPosti=rs.getInt(3);


                    t= new Tavolo (ID_Locale, ID_Tavolo, numeroPosti);


                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        closeConn();
        return t;
    }

    //ritorna l'array di invitati per lo specifico Evento di identificativo ID_Ev

    public ArrayList<Invitato> getInvitato(String ID_Ev) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        Invitato i;
        ArrayList<Invitato> invitati= new ArrayList<>();
        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM invitati WHERE `ID_Evento`='" + ID_Ev + "';");
                while (rs.next()) {


                    ID_Ev= rs.getString(1);
                    String ID_Inv=rs.getString(2);
                    String nomeInv=rs.getString(3);
                    String cogInv= rs.getString(4);
                    int etaInv= rs.getInt(5);

                    i = new Invitato(ID_Inv, nomeInv, cogInv, etaInv);
                    invitati.add(i);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        closeConn();
        return invitati;

    }

    public Invitato getInvitatoSingolo(String ID_Inv) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        Invitato i=null;

        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM invitati WHERE `ID_Invitato`='" + ID_Inv + "';");
                while (rs.next()) {


                    String ID_Ev= rs.getString(1);
                    ID_Inv=rs.getString(2);
                    String nomeInv=rs.getString(3);
                    String cogInv= rs.getString(4);
                    int etaInv= rs.getInt(5);

                    i = new Invitato(ID_Inv, nomeInv, cogInv, etaInv);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        closeConn();
        return i;

    }

    //ritorna l'array di vincoli relativi alle specifiche sul Tavolo per lo specifico Evento di identificativo ID_Ev

    public ArrayList<SpecificaTavolo> getVincoloTavolo(String ID_Ev) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        SpecificaTavolo v;
        ArrayList<SpecificaTavolo> vincoliTav = new ArrayList<>();

        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM specifica_tavolo WHERE `ID_Evento`='" + ID_Ev + "';");
                while (rs.next()) {


                    ID_Ev= rs.getString(1);
                    String ID_Inv=rs.getString(2);
                    int tavOnore=rs.getInt(3);
                    int diffMot= rs.getInt(4);
                    int veg= rs.getInt(5);
                    int vicTV= rs.getInt(6);
                    int bamb= rs.getInt(7);
                    int tavIsol= rs.getInt(8);

                    v= new SpecificaTavolo(ID_Ev, ID_Inv, tavOnore, diffMot, veg, vicTV, bamb, tavIsol);
                    vincoliTav.add(v);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        closeConn();
        return vincoliTav;
    }

    //metodo che ritorna il singolo vincolo Tavolo relativo allo specifico invitato

    public SpecificaTavolo getVincoloTavoloSingolo (String ID_Inv) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        SpecificaTavolo v=null;
        ArrayList<SpecificaTavolo> vincoliTav = new ArrayList<>();

        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM specifica_tavolo WHERE `ID_Invitato`='" + ID_Inv + "';");
                while (rs.next()) {


                    String ID_Ev= rs.getString(1);
                    ID_Inv=rs.getString(2);
                    int tavOnore=rs.getInt(3);
                    int diffMot= rs.getInt(4);
                    int veg= rs.getInt(5);
                    int vicTV= rs.getInt(6);
                    int bamb= rs.getInt(7);
                    int tavIsol= rs.getInt(8);

                    v= new SpecificaTavolo(ID_Ev, ID_Inv, tavOnore, diffMot, veg, vicTV, bamb, tavIsol);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        closeConn();
        return v;
    }

    //ritorna l'array di vincoli relativi alle preferenze sugli invitati, per lo specifico Evento di identificativo ID_Ev

    public ArrayList<PreferenzaInvitato> getVincoloInvitato(String ID_Ev) {
        startConn();
        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        PreferenzaInvitato p;
        ArrayList<PreferenzaInvitato> vincoliInv = new ArrayList<>();
        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM preferenza_invitato WHERE `ID_Evento`='" + ID_Ev + "';");
                while (rs.next()) {
                    ID_Ev= rs.getString(1);
                    String ID_Inv=rs.getString(2);
                    String vicino=rs.getString(3);
                    String lontano= rs.getString(4);
                    if(vicino!=null || lontano!=null){
                        p= new PreferenzaInvitato(ID_Ev, ID_Inv, vicino, lontano);
                        vincoliInv.add(p);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        closeConn();
        return vincoliInv;
    }

    //ritorna il singolo vincolo Invitato per lo specifico Invitato

    public PreferenzaInvitato getVincoloInvitatoSingolo (String ID_Inv) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        PreferenzaInvitato p=null;

        if (!openConn) {
            startConn();

        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM preferenza_invitato WHERE `ID_Invitato`='" + ID_Inv + "';");

                while (rs.next()) {


                    String ID_Ev= rs.getString(1);
                    ID_Inv=rs.getString(2);
                    String vicino=rs.getString(3);
                    String lontano= rs.getString(4);

                    if(vicino!=null || lontano!=null){

                        p= new PreferenzaInvitato(ID_Ev, ID_Inv, vicino, lontano);

                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        closeConn();
        return p;
    }

    //ritorna il numero di tavoli di ogni Locale

    public int getNumeroTavoli (String ID_Locale) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        int contatoreTavoli=0;

        if (!openConn) {
            startConn();

        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM tavoli WHERE `ID_Locale`='" + ID_Locale + "';");
            rs.next();
            contatoreTavoli=rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeConn();
        return contatoreTavoli;
    }

    public String getInAgenda(String ID_Locale,String data){
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        String tavoli= null;

        if (!openConn) {
            startConn();
        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM `agenda` WHERE `ID_Locale`='" + ID_Locale + "' AND `data`='" + data + "';");

                while (rs.next()) {


                    String ID_Loc= rs.getString(1);
                    String date=rs.getString(2);
                    String tavOcc=rs.getString(3);

                    if(tavOcc!=null){
                        tavoli=tavOcc;
                    }
                }

            } catch (SQLException e) {
                //e.printStackTrace();
                return null;
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        closeConn();
        if(tavoli!=null && tavoli.substring(tavoli.length()-1).equals(" "))
            return tavoli.substring(tavoli.length()-1);
        return tavoli;

    }

//    public String getRinominatiInAgenda(String ID_Locale,String data){
//        startConn();
//        Statement stmt = null;
//        ResultSet rs = null;
//        String tavoli= null;
//
//        if (!openConn) {
//            startConn();
//        }
//
//        if (openConn) {
//            try {
//                stmt = conn.createStatement();
//                rs = stmt.executeQuery("SELECT * FROM `agenda` WHERE `ID_Locale`='" + ID_Locale + "' AND `data`='" + data + "';");
//
//                while (rs.next()) {
//
//
//                    String ID_Loc= rs.getString(1);
//                    String date=rs.getString(2);
//                    String tavOcc=rs.getString(3);
//                    String tavRin=rs.getString(4);
//
//                    if(tavRin!=null){
//                        tavoli=tavRin;
//                    }
//                }
//
//            } catch (SQLException e) {
//                //e.printStackTrace();
//                return null;
//            } finally {
//                if (rs != null) {
//                    try {
//                        rs.close();
//
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (stmt != null) {
//                    try {
//                        stmt.close();
//
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }
//        closeConn();
//        if(tavoli!=null && tavoli.substring(tavoli.length()-1).equals(" "))
//            return tavoli.substring(tavoli.length()-1);
//        return tavoli;
//
//    }

    // ottengo una agenda di un locale da db
    public Map<String,ArrayList<Tavolo>> getAgendaLocale(String ID_Locale){
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        String tavoli= null;
        ArrayList<Tavolo> tavoloArrayList=new ArrayList<>();
        Map<String,ArrayList<Tavolo>> agenda= new HashMap<>(3);

        if (!openConn) {
            startConn();
        }

        if (openConn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM agenda WHERE `ID_Locale`='" + ID_Locale + "';");

                while (rs.next()) {
//                    ArrayList<Tavolo> tavoliDaAgenda = new ArrayList<>();

                    String ID_Loc= rs.getString(1);
                    String date=rs.getString(2);
                    String tavOcc=rs.getString(3);

                    Locale l=getLocale(ID_Locale);
//                    String[] campiData=date.split(" ");
                    GregorianCalendar gregData=new GregorianCalendar();//Integer.parseInt(campiData[2]),Integer.parseInt(campiData[1]),Integer.parseInt(campiData[0])
//                    gregData.set(GregorianCalendar.YEAR,Integer.parseInt(campiData[2]));
//                    gregData.set(GregorianCalendar.MONTH,Integer.parseInt(campiData[1]));
//                    int day=Integer.parseInt(campiData[0]);
//                    gregData.set(GregorianCalendar.DAY_OF_MONTH,day);

//                    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
//                    Date date1= dateFormat.parse(date);
//                    gregData.setTime(date1);

                    if(tavOcc!=null){
                        for (String IDTav:tavOcc.split(" ")) {
                            tavoloArrayList.add(getTavoloSingolo(IDTav));
                        }
                    }
                    agenda.put(date,(ArrayList<Tavolo>) tavoloArrayList.clone());
                    tavoloArrayList.clear();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        closeConn();

        agenda.remove(null,null);
        return agenda;

    }



    //metodo che cancella i dati nella tabella "nomeTable" dove alla variabile "nomeChiave" corrisponde il valore "valoreChiave"

    public void deleteEntry(String nomeTable, String nomeChiave, String valoreChiave) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        if (!openConn) {
            startConn();

        }
        try {

            stmt = conn.createStatement();
            String queryEntry = "DELETE FROM " + nomeTable + " WHERE `"+ nomeChiave +"`='" + valoreChiave + "'";
            stmt.executeUpdate(queryEntry);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        closeConn();

    }

    //aggiunta Lecce
    public void deleteDoubleKeyEntry(String table,String firstPrimaryKeyColumn,String firstPrimaryKey,String secondPrimaryKeyColumn,String secondPrimaryKey) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        if (!openConn) {
            startConn();

        }
        try {

            stmt = conn.createStatement();
            String queryEntry = "DELETE FROM `"+ table +"` WHERE `"+ firstPrimaryKeyColumn +"` ='" + firstPrimaryKey + "' AND `"+ secondPrimaryKeyColumn +"`='" + secondPrimaryKey + "';";
            stmt.executeUpdate(queryEntry);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        closeConn();

    }


    public Cliente getIntestatarioEvento(String nomeEvento){
        startConn();

        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        Cliente Intestatario=null;

        if (!openConn) {
            startConn();
        }

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT clienti.* FROM eventi JOIN clienti on eventi.ID_Cliente=clienti.ID_Cliente WHERE `eventi.ID_Evento`='" + nomeEvento + "';");
            while (rs.next()) {


                String ID_Cl= rs.getString(1);
                String nomeCl=rs.getString(2);
                String cognomeCl=rs.getString(3);
                String emailCl=rs.getString(4); //Aggiunta una colonna per il campo email
                String pwdCl= rs.getString(5);

                Intestatario= new Cliente (nomeCl, cognomeCl, ID_Cl, emailCl, pwdCl);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        closeConn();

        return Intestatario;


    }







}