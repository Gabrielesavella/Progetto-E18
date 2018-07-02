package persone;

import java.util.GregorianCalendar;

public class Cliente {
    private String ID,username,Psw,nome,cognome,email;


    public Cliente(String nome, String cognome, String ID, String psw) {
        this.ID = ID;
        Psw = psw;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Cliente(String nome, String cognome) {//, String luogonascita
        this.nome = nome;
        this.cognome = cognome;
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

