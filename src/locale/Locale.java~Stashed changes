package locale;

import database.ConnessioneDB;
import facade.Facade;

import java.util.*;

public class Locale {

    private String ID_Loc, orarioApertura, orarioChiusura, giornoChiusura;
    private int numInv;
    private GestoreLocale locale;
    private ConnessioneDB c=new ConnessioneDB();
    private ArrayList<Tavolo> tavoliTotali;
    private ArrayList<Evento> gestoreEventiTotali;
    private ArrayList<Evento> eventiTotali;
    //aggiunte Lecce
    private Map<String,ArrayList<Tavolo>> agenda;
    private boolean agendaCharged;


    public Locale(String ID_Loc, int numInv, String orarioApertura, String orarioChiusura, String giornoChiusura){

        this.ID_Loc=ID_Loc;
        this.numInv=numInv;
        this.orarioApertura=orarioApertura;
        this.orarioChiusura=orarioChiusura;
        this.giornoChiusura=giornoChiusura;
        //aggiunte Lecce
        this.agenda=new HashMap<>();
        agendaCharged=false;
//        agenda= Facade.getInstance().getAgenda(ID_Loc); // a mio parere era il meglio manda in loop infinito l'app
    }

    public GestoreLocale ricavaLocale() {

        if(!agendaCharged){
            locale = new GestoreLocale(ID_Loc, numInv, ricavaOrario(orarioApertura), ricavaOrario(orarioChiusura), ricavaGiorno(giornoChiusura));
            this.agenda= Facade.getInstance().getAgenda(ID_Loc);
            locale.setAgenda(this.agenda);
            agendaCharged=true;
        }
        return locale;
    }

    public GestoreLocale gestisciLocale() {

        ricavaLocale();
        aggiungiTavoli();
        //aggiungiEventi(); PARTE DA CORREGGERE

        return locale;
    }

    public void aggiungiTavoli(){
        if (!c.checkConn()) {
            c.startConn();
            tavoliTotali = Facade.getInstance().getTavoli(ID_Loc);
            c.closeConn();
        }
        else{
            tavoliTotali=Facade.getInstance().getTavoli(ID_Loc);
        }
        ricavaLocale().getTavoliLocale().addAll(tavoliTotali);
    }



    public void aggiungiEventi(){
        ricavaLocale().getEventi().addAll(creaListaGestoreEventi());
    }

    public ArrayList<Evento> creaListaGestoreEventi(){

        if(!c.checkConn()) {

            c.startConn();
            eventiTotali = c.getEvento(ID_Loc);
            c.closeConn();
        }
        else
        {
            eventiTotali = c.getEvento(ID_Loc);
        }


        return eventiTotali;
    }


    public String getID_Locale() {
        return ID_Loc;
    }

    //Per ricavare l'orario dalla stringa
    public GregorianCalendar ricavaOrario(String orario){

        GregorianCalendar time = new GregorianCalendar();

        String[] st = orario.split(":");

        if (!(st[0]==null)){
            time.add(GregorianCalendar.HOUR, Integer.parseInt(st[0]));
        }

        if(!(st[1]==null)){
            time.add(GregorianCalendar.MINUTE, Integer.parseInt(st[1]));
        }

        return time;
    }

    //Per ricavare il giorno dalla stringa
    public GregorianCalendar ricavaGiorno(String giorno) {

        GregorianCalendar day = new GregorianCalendar();

        switch (giorno){

            case "Lunedì":
                day.add(GregorianCalendar.MONDAY, Calendar.WEEK_OF_YEAR);
            case "Martedì":
                day.add(GregorianCalendar.TUESDAY, Calendar.WEEK_OF_YEAR);
            case "Mercoledì":
                day.add(GregorianCalendar.WEDNESDAY, Calendar.WEEK_OF_YEAR);
            case "Giovedì":
                day.add(GregorianCalendar.THURSDAY, Calendar.WEEK_OF_YEAR);
            case "Venerdì":
                day.add(GregorianCalendar.FRIDAY, Calendar.WEEK_OF_YEAR);
            case "Sabato":
                day.add(GregorianCalendar.SATURDAY, Calendar.WEEK_OF_YEAR);
            case "Domenica":
                day.add(GregorianCalendar.SUNDAY, Calendar.WEEK_OF_YEAR);
        }

        return day;
    }

    //aggiunte Lecce
    public ArrayList<Tavolo> getTavoliDisponibili(String calendar){
        //ArrayList<Tavolo> tavoliTot=tavoliTotali;
        ArrayList<Tavolo> tavoliOccupati = agenda.get(calendar);
        ArrayList<Tavolo> tavoliDisponibili=tavoliTotali;
        for (Tavolo t:tavoliTotali) {
            for (Tavolo tOcc:tavoliOccupati) {
                String idTavolo= t.getIDTavolo();
                String idTavoloOcc= tOcc.getIDTavolo();
                if(idTavolo.equals(idTavoloOcc)){ tavoliDisponibili.remove(t); }
            }
        }
        return tavoliDisponibili;
    }

    public String getOrarioApertura() {
        return orarioApertura;
    }

    public String getOrarioChiusura() {
        return orarioChiusura;
    }

    public String getGiornoChiusura() {
        return giornoChiusura;
    }

    public int getNumInv () {return numInv; }
}


