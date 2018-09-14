package tester;

import org.junit.Assert;
import org.junit.Test;

import database.ConnessioneDB;

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