package persone;

import java.util.GregorianCalendar;

public class Cliente {
    private String ID,conf_Psw,Psw,nome,cognome;//,luogonascita
    //private GregorianCalendar data;
    //private boolean okpws;

    public Cliente(String nome, String cognome, String ID, String psw) {//, String conf_Psw, String luogonascita
        this.ID = ID;
//        this.conf_Psw = conf_Psw;
        Psw = psw;
        this.nome = nome;
        this.cognome = cognome;
//        this.luogonascita = luogonascita;

    }

    public Cliente(String nome, String cognome) {//, String luogonascita
        this.nome = nome;
        this.cognome = cognome;
//        this.luogonascita = luogonascita;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPsw(String psw) {
        Psw = psw;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getID() {
        return ID;
    }

    public String getPsw() {
        return Psw;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }


}

