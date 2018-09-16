
package locale;

import facade.*;
import persone.Invitato;

import java.text.*;
import java.util.*;


public class Evento {

    private String nomeEvento;

    private Locale location = null;
    private ArrayList <Invitato> invitati;
    private GregorianCalendar dataEvento;
    private String stringData;
    private int numInvitati;





    public Evento(String nomeEvento, GregorianCalendar dataEvento, Locale location, int numInvitati){

        /*Crea un Evento caratterizzato da un nome, una data e un Locale.*/


        this.nomeEvento = nomeEvento;
        this.location = location;
        this.dataEvento = dataEvento;
        this.invitati = new ArrayList(numInvitati);
        this.numInvitati=numInvitati;

    }
    public Evento(String nomeEvento, String dataEvento, String nomeLocale, int numInvitati){

        /*Crea un Evento caratterizzato da un nome, una data e un Locale. Al suo interno verranno successivamente inseriti
        una lista di Invitati e di Vincoli*/

        this.nomeEvento = nomeEvento;
        this.location = prendiLocale(nomeLocale);
        this.numInvitati=numInvitati;
        this.dataEvento=ricavaData(dataEvento);
    }
    public static GregorianCalendar ricavaData(String data){

        GregorianCalendar dataEventoCalendario = new GregorianCalendar();
        String[] st= null;
        if(data.contains("/"))
            st = data.split("/");
        else
            st = data.split("-");
        if(st[0]!=null) {
            dataEventoCalendario.add(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(st[0]));
        }
        if(st[1]!=null){
            dataEventoCalendario.add(GregorianCalendar.MONTH, Integer.parseInt(st[1]));
        }
        if(st[2]!=null){
            dataEventoCalendario.add(GregorianCalendar.YEAR, Integer.parseInt(st[2]));
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dataEventoCalendario.setTime(date);


        return dataEventoCalendario;
    }
    public Locale prendiLocale(String nomeLoc) {//Locale
        Locale loca=null;
        ArrayList<Locale> locali= Facade.getInstance().getLocali();
        for (Locale l : locali) {
            if (nomeLoc == l.getId_locale()) {
                loca = l;
            }
        }
        return loca;
    }

    public String getName(){return nomeEvento;}




    private String createStringData() {
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        format.setCalendar(dataEvento);
        String dataFormatted=format.format(dataEvento.getTime());

        return dataFormatted;
    }


    public Locale getLocation(){ return location;}


    public void addInvitati(Invitato invitato){
        invitati.add(invitato);
    }

    public void removeInvitati(Invitato invitato){
        if (invitato!=null && invitati!=null && invitati.size()!=0){
            for (int i = 0;i<invitati.size();i++) {
                if(invitati.get(i)!=null && invitati.get(i).getID_Inv()!=null && invitati.get(i).getID_Inv().equals(invitato.getID_Inv())) {
                    invitati.remove(i);
                    i--;
                }
            }
        }
    }





    public int getNumInvitati(){return numInvitati;}

    public GregorianCalendar getDataEvento(){
        return dataEvento;
    }


    public ArrayList<Invitato> getListaInvitati(){return invitati;}

    public void showListaInvitati(){
        for (Invitato singleGuest:invitati) {
            System.out.println(singleGuest.toString());

        }
    }

    public String getStringData(){
        stringData=createStringData();
        return stringData; }

    public void setDataEvento(GregorianCalendar dataEvento) {
        this.dataEvento = dataEvento;
    }
    public ArrayList<Invitato> ricavaInvitati(String ID_Evento){

        ArrayList<Invitato> invitati= Facade.getInstance().getInvitato(ID_Evento);
        return invitati;
    }
    public String getNomeLocale(){
        return location.getId_locale();
    }

    public void clear(){
        this.invitati.clear();
    }
}