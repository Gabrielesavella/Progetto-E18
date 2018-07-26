package tester;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import database.ConnessioneDB;
import database.DatabaseException;
import database.DatabaseNullException;
import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import persone.Cliente;
import persone.Invitato;
import vincoli.PreferenzaInvitato;
import vincoli.SpecificaTavolo;

public class TesterDBTest {
    ConnessioneDB connetto = new ConnessioneDB();
    @Test
    public void ProvaDB(){
        connetto.startConn();
        Assert.assertEquals(true,connetto.checkConn());
        connetto.closeConn();
        Assert.assertEquals(false,connetto.checkConn());
    }

}