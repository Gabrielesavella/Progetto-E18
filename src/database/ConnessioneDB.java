/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.GregorianCalendar;
import locale.Evento;
import locale.Locale;
import persone.Cliente;
import persone.Invitato;

/**
 *
 * @author salvi
 */
public class ConnessioneDB {// crea la connessione col database "smistamento_posti"

    private final String dbUser = "root"; //nome utente 
    private final String dbPwd = "21187"; //password utente
    private final String dbDriver = "com.mysql.cj.jdbc.Driver";  //il driver per collegarsi al DB
    private final String dbUrl = "jdbc:mysql://localhost:3306/smistamento_posti?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false"; //url del database e schema
    private Connection conn = null; //si crea una nuova connessione, per adesso nulla
    private boolean openConn = false; //variabile per verificare se la connessione col DB è aperta o meno

    String nomeUser;
    String cognomeUser;
    String ID_User;
    String password;
    String ID_Inv;
    String nomeInv;
    String cognomeInv;
    int etaInv;
    String ID_Evento;
    GregorianCalendar dataEvento;
    Locale location;
    int numeroInvitati;
    String nomeLocale;
    
    //per ora inserisco i booleani relativi al vincolo come istanze di classe, una volta finalizzati ai vincoli li collego al singolo invitato
    
    boolean starVicino;
    boolean starLontano;
    boolean tavoloOnore;
    boolean difficoltaMotorie;
    boolean vegetariano;
    boolean vicinoTV;

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

    public void collegaDati() {
        Cliente cliente1 = new Cliente(nomeUser, cognomeUser, ID_User, password);
        this.nomeUser = cliente1.getNome();
        this.cognomeUser = cliente1.getCognome();
        this.ID_User = cliente1.getID();
        this.password = cliente1.getPsw();
        Invitato inv = new Invitato(nomeInv, cognomeInv, etaInv);
        this.ID_Inv = inv.getID_Inv();
        this.nomeInv = inv.getNome();
        this.cognomeInv = inv.getCognome();
        this.etaInv = inv.getEta();
        Evento evento1 = new Evento(ID_Evento, dataEvento, location, numeroInvitati);
        this.ID_Evento = evento1.getName();
        this.dataEvento = evento1.getDataEvento();
        this.location = evento1.getLocation();
        this.numeroInvitati = evento1.getNumInvitati();
        nomeLocale = location.id_locale;

    }
    
    /*Ho creato tre TABLE:
    1) dati_User: la cui chiave primaria è ID_User
    2) dati_Evento: la cui chiave è ID_Evento
    3) dati_Invitati: le cui chiavi sono ID_Evento e ID_Invitato, in modo da poter fare la select su entrambe le chiavi
    */

    public void inserisciDatiUser(String ID_User, String nomeUser, String cognomeUser, String password) {//inserire nuovi valori nella TABLE
        Statement stmt = null;
        ResultSet rs = null;
        String table = "dati_User";
        if (!openConn) {
            startConn();

        } else {
            try {

                String queryEntry = "INSERT INTO " + table + " (`ID_User`, `NomeUser`, `CognomeUser`, `Password`) VALUES ('" + ID_User + "','" + nomeUser + "','" + cognomeUser + "','" + password + "');";
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
                if (stmt != null) { //teoricamente chiudendo il resultset si dovrebbe chiudere anche lo statement, ma preferisco essere più preciso
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    /*questo metodo cancella i dati nelle table dati_User e dati_Evento (la chiave primaria è ID_User)
*/    

    public void cancellaDatiUser(String ID_User) {
        Statement stmt1 = null;
        ResultSet rs1 = null;
        String table1 = "dati_User";
        Statement stmt2 = null;
        ResultSet rs2 = null;
        String table2 = "dati_Evento";
        if (!openConn) {
            startConn();

        } else {
            try {

                String queryEntry1 = "DELETE FROM " + table1 + " WHERE `ID_User`='" + ID_User + "'";
                stmt1.executeUpdate(queryEntry1);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs1 != null) {
                    try {
                        rs1.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt1 != null) {
                    try {
                        stmt1.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {

                String queryEntry2 = "DELETE FROM " + table2 + " WHERE `ID_User`='" + ID_User + "'";
                stmt1.executeUpdate(queryEntry2);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs2 != null) {
                    try {
                        rs2.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (stmt2 != null) {
                    try {
                        stmt2.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void inserisciDatiEvento(String ID_User, String ID_Evento, GregorianCalendar dataEvento, String nomeLocale, int numeroInvitati) {
        Statement stmt = null;
        ResultSet rs = null;
        String table = "dati_Evento";
        if (!openConn) {
            startConn();

        } else {
            try {

                String queryEntry = "INSERT INTO " + table + " (`ID_User`, `ID_Evento`, `dataEvento`, `Locale`, `NumeroInvitati`) VALUES ('" + ID_User + "','" + ID_Evento + "','" + dataEvento + "','" + nomeLocale + "','" + numeroInvitati + "');";
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
                if (stmt != null) { //teoricamente chiudendo il resultset si dovrebbe chiudere anche lo statement, ma preferisco essere più preciso
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
    
    // i campi relativi ai vincoli li ho inseriti come boolean

    public void inserisciDatiInvitato(String ID_Evento, String ID_Inv, String nomeInv, String cognomeInv, int etaInv, boolean starVicino, boolean starLontano, boolean tavoloOnore, boolean difficoltaMotorie, boolean vegetariano, boolean vicinoTV) {
        Statement stmt = null;
        ResultSet rs = null;
        String table = "dati_Invitati";
        if (!openConn) {
            startConn();

        } else {
            try {

                String queryEntry = "INSERT INTO " + table + " (`ID_Evento`, `ID_Invitato`, `nomeInvitato`, `cognomeInvitato`, `etaInvitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,) VALUES ('" + ID_Evento + "','" + ID_Inv + "','" + nomeInv + "','" + cognomeInv + "','" + etaInv + "','" + starVicino + "','" + starLontano + "','" + tavoloOnore + "','" + difficoltaMotorie + "','" + vegetariano + "','" + vicinoTV + "');";
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
                if (stmt != null) { //teoricamente chiudendo il resultset si dovrebbe chiudere anche lo statement, ma preferisco essere più preciso
                    try {
                        stmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

}
