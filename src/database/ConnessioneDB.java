/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import locale.Evento;
import persone.Cliente;
import persone.Invitato;
import vincoli.PreferenzaInvitato;
import vincoli.SpecificaTavolo;

import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;



/**
 * @author Salvatore Parisi
 */
public class ConnessioneDB {// crea la connessione col database "smistamento_posti" in entrata ed uscita

    private final String dbUser = "root"; //nome utente
    private final String dbPwd = "21187"; //password utente
    private final String dbDriver = "com.mysql.cj.jdbc.Driver";  //il driver per collegarsi al DB ?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false
    private final String dbUrl = "jdbc:mysql://localhost:3306/smistamento_posti?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false"; //url del database e schema
    private Connection conn = null; //si crea una nuova connessione, per adesso nulla
    private boolean openConn = false; //variabile per verificare se la connessione col DB Ã¨ aperta o meno

    private String ID_Cl, nomeCl, cognomeCl, pwdCl, nomeLoc, ID_Ev, ID_Inv, nomeInv, cogInv, vicino, lontano;
    private int numInv, etaInv, diffMot, veg, bamb, tavOnore, tavIsol, vicTV;
    private Date dataEv;




    public void startConn() { //metodo per inizializzare la connessione

        try {

            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            openConn = true;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkConn() { //controlla se la connessione è avvenuta

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
    1) Clienti: la cui chiave primaria è ID_Cliente
    2) Eventi: le cui chiavi sono ID_Cliente e ID_Evento
    3) Invitati: le cui chiavi sono ID_Evento e ID_Invitato, in modo da poter fare la select su entrambe le chiavi
    4) Specifica_Tavolo (vincoli relativi al tavolo): le cui chiavi sono ID_Evento ed ID_Invitato
    5) Preferenze_Invitato (vincoli relativi all'invitato): le cui chiavi sono ID_Evento ed ID_Invitato

    Per ognuna di queste table c'è un metodo:
     - inserisciDatiNomedellaTable, che inserisce nuovi valori nei campi della Table
     - getNomeTable, che seleziona e ritorna i valori della Table, sempre secondo la chiave primaria selezionata
    */

    //Metodo che inserisce nuovi dati nella table Cliente

    public void inserisciDatiCliente(String ID_Cliente, String nomeCliente, String cognomeCliente, String passwordCliente) {

        Statement stmt = null;
        ResultSet rs = null;
        String table = "clienti";

        if (!openConn) {
            startConn();

        } else {
            try {
                stmt = conn.createStatement();
                String queryEntry = "INSERT INTO " + table + " (`ID_Cliente`, `NomeCliente`, `CognomeCliente`, `PasswordCliente`) VALUES ('" + ID_Cliente + "','" + nomeCliente + "','" + cognomeCliente + "','" + passwordCliente + "');";

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
                //teoricamente chiudendo il resultset si dovrebbe chiudere anche lo statement, ma preferisco essere più preciso
                if (stmt != null) {
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    //Metodo che inserisce i dati del singolo Evento nella TABLE "Eventi"

    public void inserisciDatiEvento(String ID_Cliente, String ID_Evento, Date dataEvento, String nomeLocale, int numeroInvitati) {

        Statement stmt = null;
        ResultSet rs = null;
        String table = "eventi";



        if (!openConn) {
            startConn();

        } else {
            try {
                stmt = conn.createStatement();
                String queryEntry = "INSERT INTO " + table + " (`ID_Cliente`, `ID_Evento`, `dataEvento`, `Locale`, `NumeroInvitati`) VALUES ('" + ID_Cliente + "','" + ID_Evento + "','" + dataEvento + "','" + nomeLocale + "','" + numeroInvitati + "');";
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

        }
    }

    // metodo che inserisce i dati del sinvolo invitato nella table "Invitati"

    public void inserisciDatiInvitato(String ID_Evento, String ID_Inv, String nomeInv, String cognomeInv, int etaInv) {

        Statement stmt = null;
        ResultSet rs = null;
        String table = "invitati";
        if (!openConn) {
            startConn();

        } else {
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

        }


    }

    //metodo che inserisce vincoli sul tavolo secondo uno specifico Evento e uno specifico Invitato.
    // il metodo leggere i valori 1 e 0, che in Mysql sono tradotti in TINYINT= BOOLEAN.
    // TRUE: vincolo inserito, FALSE: vincolo non inserito

    public void inserisciVincoliTavolo(String ID_Evento, String ID_Inv, int tavoloOnore, int difficoltaMotorie, int vegetariano, int vicinoTV, int bambini, int tavoloIsolato) {

        Statement stmt = null;
        ResultSet rs = null;
        String table = "specifica_tavolo";
        if (!openConn) {
            startConn();

        } else {
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

        }

    }

    public void inserisciVincoloInvitati(String ID_Evento, String ID_Inv, String starVicino, String starLontano) {

        Statement stmt = null;
        ResultSet rs = null;
        String table = "preferenza_invitato";
        if (!openConn) {
            startConn();

        } else {
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

        }


    }


    //prende dalla TABLE clienti i dati relativi al cliente con la specifica chiave ID_Cliente, che sono quelli da inserire nel costruttore di Cliente()

    public void getCliente(String ID_Cliente) {
        startConn();
        Statement stmt = null; //creazione di uno Statement, per adesso nullo
        ResultSet rs = null; //variabile che contiene il risultato dello Statement
        if (!openConn) {
            startConn();

        } else {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM clienti WHERE `ID_Cliente`='" + ID_Cliente + "';");
                while (rs.next()) {


                    this.ID_Cl= rs.getString(1);
                    this.nomeCl=rs.getString(2);
                    this.cognomeCl=rs.getString(3);
                    this.pwdCl= rs.getString(4);


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
    }

    //prende dalla TABLE clienti i dati relativi all'evento con la specifica chiave ID_Evento, che sono quelli da inserire nel costruttore di Evento()

    public void getEvento(String ID_Evento) {
        startConn();
        Statement stmt = null;
        ResultSet rs = null;
        if (!openConn) {
            startConn();

        } else {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM eventi WHERE `ID_Evento`='" + ID_Evento + "';");
                while (rs.next()) {


                    this.ID_Cl= rs.getString(1);
                    this.ID_Ev=rs.getString(2);
                    this.dataEv=rs.getDate(3);
                    this.nomeLoc= rs.getString(4);
                    this.numInv= rs.getInt(5);



                   // Evento e= new Evento(ID_Ev, dataEv, nomeLoc, numInv);

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

        } else {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM invitati WHERE `ID_Evento`='" + ID_Ev + "';");
                while (rs.next()) {


                    this.ID_Ev= rs.getString(1);
                    this.ID_Inv=rs.getString(2);
                    this.nomeInv=rs.getString(3);
                    this.cogInv= rs.getString(4);
                    this.etaInv= rs.getInt(5);

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

        return invitati;

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

        } else {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM specifica_tavolo WHERE `ID_Evento`='" + ID_Ev + "';");
                while (rs.next()) {


                    this.ID_Ev= rs.getString(1);
                    this.ID_Inv=rs.getString(2);
                    this.tavOnore=rs.getInt(3);
                    this.diffMot= rs.getInt(4);
                    this.veg= rs.getInt(5);
                    this.vicTV= rs.getInt(6);
                    this.bamb= rs.getInt(7);
                    this.tavIsol= rs.getInt(8);

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

        return vincoliTav;
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

        } else {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM preferenza_invitato WHERE `ID_Evento`='" + ID_Ev + "';");
                System.out.println(String.format("%-30s %-30s %-30s %-30s", "ID_EVENTO", "ID_INVITATO", "VICINO", "NONVICINO"));
                while (rs.next()) {


                    this.ID_Ev= rs.getString(1);
                    this.ID_Inv=rs.getString(2);
                    this.vicino=rs.getString(3);
                    this.lontano= rs.getString(4);

                    p= new PreferenzaInvitato(ID_Ev, ID_Inv, vicino, lontano);
                    vincoliInv.add(p);
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

    return vincoliInv;
    }



}
