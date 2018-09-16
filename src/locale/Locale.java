package locale;

import database.*;
import facade.*;
import persone.*;

import java.util.*;




public class Locale {

    public ArrayList<Evento> eventi_locale;
    public String id_locale;
    private int numMaxTavoli;
    private int numMaxPosti;
    private GregorianCalendar oraApertura, oraChiusura, giornodichiusura;
    private ArrayList<Tavolo> tavoli;
    private ArrayList<Tavolo> tavoliUtilizzati = new ArrayList<>();
    private Map<String,ArrayList<Tavolo>> agenda;
    private boolean agendaCharged;




    public Locale(String id_locale, int numMaxTavoli, GregorianCalendar oraApertura, GregorianCalendar oraChiusura, GregorianCalendar giornodichiusura) {
        this.giornodichiusura=giornodichiusura;
        this.id_locale=id_locale;
        this.numMaxTavoli=numMaxTavoli;
        this.tavoli = Facade.getInstance().getTavoli(id_locale);
        this.oraApertura=oraApertura;
        this.oraChiusura=oraChiusura;
        eventi_locale = new ArrayList<>();
        //this.agenda = new HashMap<>();
    }
    //per il db
    public Locale(String ID_Loc, int numMaxTavoli, String orarioApertura, String orarioChiusura, String giornoChiusura){

        this.id_locale=ID_Loc;
        this.numMaxTavoli=numMaxTavoli;
        this.oraApertura=ricavaOrario(orarioApertura);
        this.oraChiusura=ricavaOrario(orarioChiusura);
        this.giornodichiusura=ricavaGiorno(giornoChiusura);
        this.tavoli = Facade.getInstance().getTavoli(id_locale);
        eventi_locale = new ArrayList<>();
        this.agenda=new HashMap<>();
        this.agenda=Facade.getInstance().getAgenda(ID_Loc);
        agendaCharged=false;
    }


    public void aggiungiEvento(Evento nuovoevento){
        if(nuovoevento.getLocation().getId_locale().equals(id_locale))
            eventi_locale.add(nuovoevento);
    }

    public void aggiungiTavoli(ArrayList<Tavolo> tavoliTotali){
        for (Tavolo t : tavoliTotali){
            if(t.getID_Loc().equals(id_locale)){ tavoli.add(t); }

        }
    }

    /*Smista tutti invitati ad un particolare evento nei tavoli. Fatto ciò, restituisce un arraylist di tutti i tavoli utilizzati*/
    public ArrayList<Tavolo> smistamentoTavoli(Evento e){
        rimuoviGiaPresenti(e);
        int count = 0;
        ArrayList<Invitato>listainvitati;
        Evento gEvDiLocale=null;
        for (Evento ev : eventi_locale)
            if (ev.equals(e)) {
                gEvDiLocale=ev;
            }
        for (Tavolo t:tavoli) {
                if (t.getArraylistInvitati().size()!=0)
                    tavoliUtilizzati.add(t);
        }
        for (Evento ev : eventi_locale)
            if (ev.equals(e)) {
                listainvitati = ev.getListaInvitati();
                if(listainvitati.size()!=0) {
                    for (Tavolo t : tavoli) {
                        //il cambiamento è qua
                        while (t.getDisponibile() && count < listainvitati.size()) {
                            t.addGuest(listainvitati.get(count));
                            count++;
                            if ((count) == listainvitati.size())
                                t.setDisponibile(false);
                        }
                        if (!tavoliUtilizzati.contains(t))
                            tavoliUtilizzati.add(t);
                        if ((count) == listainvitati.size())
                            break;
                    }
                }
            }
        occupaInAgenda(tavoliUtilizzati,e.getStringData());
        return tavoliUtilizzati;
    }


    // aggiunta Lecce: occupiamo i tavoli che alla fine sono utilizzati per l'evento
    private void occupaInAgenda(ArrayList<Tavolo> tavoliOcc, String dataEvento) {
        //parte adibita al salvataggio in agenda
        ArrayList<Tavolo> tavoliOccGiorno=agenda.get(dataEvento);
        if (tavoliOccGiorno==null)
            agenda.put(dataEvento,tavoliOcc);
        else{
            tavoliOccGiorno.addAll(tavoliOcc);
            agenda.put(dataEvento,tavoliOccGiorno);
        }
        //parte adibita a salvataggio in db
        //creazione stringa tavoli
        String stringTavoli= new String("");
        for (int i=0;i<tavoliOcc.size();i++) {
            Tavolo t=tavoliOcc.get(i);
            if (i==0){//&& (tavoliOcc==null || tavoliOcc.equals(""))
                stringTavoli+=t.getRealID_Tav();
            }
            else{ stringTavoli+=" "+t.getRealID_Tav(); }

        }
        try {
            Facade.getInstance().inserisciAgenda(this.id_locale,dataEvento,stringTavoli);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (DatabaseNullException e) {
            e.printStackTrace();
        }

    }



    //Rimuove dagli invitati da smistare, le persone che sono già sedute al tavolo.
    public void rimuoviGiaPresenti(Evento e){
        for (Tavolo t : tavoli){
            for (Invitato i:t.getArraylistInvitati()) {
                e.removeInvitati(i);

            }
        }
    }

    /*Restituisce un arraylist di tutti gli invitati presenti in tutti i tavoli utilizzati*/
    public ArrayList<Invitato> getInvitatiOgniTavolo(){

        ArrayList<Invitato> invitatiTotali = new ArrayList<>();

        for (Tavolo t : tavoliUtilizzati){
            invitatiTotali.addAll(t.getArraylistInvitati());
        }
        return invitatiTotali;
    }

    /*Cancella il contenuto di tavoliUtilizzati, in modo da poter essere riutilizzato per un altro evento*/
    public void clearTavoliUtilizzati(){
        for (Tavolo t : tavoliUtilizzati){
            tavoliUtilizzati.remove(t);
        }
    }


    public int getMinTavoli(int num_invitati){
        int num_tavoli = 0;
        int posti_tavolo;
        int contatore_posti = 0;
        for(Tavolo t: tavoli){
            if (contatore_posti <= num_invitati){
                posti_tavolo = t.getNumPosti();
                contatore_posti += posti_tavolo;
                num_tavoli++;
            }
        }
        return num_tavoli;
    }

    public String showInvitatiAiTavoli(){
        String invitatiTavoli = "";
        for (Tavolo t : tavoli){
            if (!(t.getNumPosti()==t.getPostiTot()))
            invitatiTavoli += t.showInvitati() + "\n\n\n";
        }
        return invitatiTavoli;
    }
    /*creo un metodo per calcolare il massimo numero di posti
     * derivato dalla capienza dei tavoli presenti nel locale
     * @author: Gabrielesavella
     */

    public int getMaxSeats() {
        int capienza = 0;
        for(Tavolo tavolo:tavoli) {
            capienza += tavolo.getNumPosti();
        }
        numMaxPosti = capienza;
        return capienza;

    }

    public ArrayList<Evento> getEventi(){ return eventi_locale; }

    public String stampaNomeEventi(){
        String a="";
        for (Evento e : eventi_locale){
            a += e.getName() + "\n";
        }
        return a;
    }
    /*
    creo un getter per il numero max di posti
    che li conta ogni volta tramite il metodo getMaxSeats()
    @author Gabrielesavella
     */
    public int getNumMaxPosti() {
        getMaxSeats();
        return numMaxPosti;
    }
    /*
    controllo che il numero massimo di tavoli sia minore di quello che ho nel locale se è così
    aggiungo un tavolo al locale, di conseguenza calcolo nuovamente il numero massimo di posti @author Gabrielesavella
     */
    public void addTable(Tavolo newTable){
        boolean added = false;
        if(tavoli.size()< numMaxTavoli) {
            added = tavoli.add(newTable);
        }
        //se il tavolo è stato aggiunto aggiungo il numero massimo dei posti
        if(added) {
            numMaxPosti += newTable.getNumPosti();
        }

    }

    /*
    modifica: il metodo restituisce gli invitati di un determinato tavolo specificato da progamma
    @author Gabrielesavella
     */
    public ArrayList<Invitato> getInvitatiAlTavolo(Tavolo t){
        ArrayList<Invitato> tableGuests = new ArrayList<Invitato>();
        tableGuests.addAll(t.getArraylistInvitati());
        return tableGuests;
    }


    public ArrayList<Tavolo> getTavoliLocale() {
        return tavoli;
    }
    public int getNPostiTavolo(String idTavolo) {
        int numposti = 0;

        for (Tavolo t : tavoli) {
            if (t.getIDTavolo().equals(idTavolo))
                numposti += t.getPostiTot();
        }
        return numposti;
    }

    public String getId_locale() {
        return id_locale;
    }

    public void setTavoli(ArrayList<Tavolo> tavoli) {
        this.tavoli = tavoli;
    }

    public ArrayList<Tavolo> getTavoliUtilizzati() {
        return tavoliUtilizzati;
    }

    public void setAgenda(Map<String,ArrayList<Tavolo>> agenda){ this.agenda=agenda; }

    public boolean checkDisponibilita(String data, int invitati) {
        tavoli=Facade.getInstance().getTavoli(this.id_locale);
        agenda=Facade.getInstance().getAgenda(id_locale);
        ArrayList <Tavolo> tavoliInAgenda= agenda.get(data);
        aggiornaTavoliInData(tavoliInAgenda);
        int numPostiDisponibili=0;
        //copia incolla di aggiorna tavoli
        for (Tavolo t:tavoli) {
            numPostiDisponibili+=t.getPostiTot();
        }
        if (numPostiDisponibili<invitati)
            return false;
        return true;
    }

    private void aggiornaTavoliInData(ArrayList<Tavolo> tavoliInAgenda) {

        if (!(tavoliInAgenda==null || tavoliInAgenda.size()==0)){
            for (Tavolo t:tavoliInAgenda) {
                for (int i=0;i<tavoli.size();i++){
                    if(tavoli.get(i).getIDTavolo().equals(t.getIDTavolo())){
                        tavoli.remove(i);
                        i--;
                    }
                }
            }
        }

    }

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

}