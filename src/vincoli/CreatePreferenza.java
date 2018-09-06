package vincoli;

import database.ConnessioneDB;
import facade.*;
import locale.*;
import persone.Invitato;

import java.util.ArrayList;
import java.util.Collections;


public class CreatePreferenza {


    ArrayList<PreferenzaInvitato> pref = new ArrayList<>();
    ArrayList<Invitato> invitati = new ArrayList<>();
    ArrayList<String> preferenze_non_rispettate = new ArrayList<>();
    ArrayList<Tavolo> tavoli = new ArrayList<>();
    ArrayList<Tavolo> tavoliVincolati = new ArrayList<>();
    ArrayList<Tavolo> grupponi= new ArrayList<>();
    ArrayList<Tavolo> insiemi = new ArrayList<>();
    ArrayList<Tavolo> insiemiLontananza = new ArrayList<>();
    ArrayList<Tavolo> tavoliTot = new ArrayList<>();

    public CreatePreferenza(String ID_Ev, ArrayList<Tavolo> tav){
        this.invitati = Facade.getInstance().getInvitato(ID_Ev);
        pref= Facade.getInstance().getVincoloInvitato(ID_Ev);
        this.tavoli = tav;
        aggiungiInvitati();
    }

    public void generaInsiemi(){

        for (int h=0; h<pref.size(); h++){
            insiemi.add(new Tavolo("Insieme n. "+h, 40));
            insiemiLontananza.add(new Tavolo("Insieme n. "+h, 40));
        }

    }

    public void ottieniTavoliDisponibili(){

        for (int t=0; t<tavoli.size(); t++){

            if (tavoli.get(t).getDisponibile()==false){
                tavoliVincolati.add(tavoli.get(t));
                tavoli.remove(t);
                t--;
            }
        }
    }


    public void controllaIncongruenze(){


        for (Tavolo ta: insiemiLontananza){

            for (Tavolo t : tavoliTot){

                int count=0;


                for (int g=0; g<ta.getArraylistInvitati().size(); g++){

                    if (t.getArraylistInvitati().size()!=0 && t.mostraID_Invitati().contains(ta.getArraylistInvitati().get(g).getID_Inv())){
                        count++;
                    }

                    if(count>1){
                        System.out.println("Le persone \n" + ta.mostraID_Invitati()+ "\nnon possono essere sistemate a tavola secondo lontananza, in quanto viene confutato un vincolo precedente di vicinanza.\n\n");
                        preferenze_non_rispettate.add("Le persone \n" + ta.mostraID_Invitati()+ "\nnon possono essere sistemate a tavola secondo lontananza, in quanto viene confutato un vincolo precedente di vicinanza.\n\n");
                        break;
                    }
                }


            }
        }
    }



    public ArrayList<Tavolo> getTavoli() {
        return tavoli;
    }

    public ArrayList<Tavolo> getTavoliUtilizzati(){
        ArrayList<Tavolo> utilizzati = new ArrayList<>();
        for (Tavolo t:getTavoli()) {
            if(t.getArraylistInvitati().size()!=0)
                utilizzati.add(t);
        }
        return utilizzati;
    }


    public void aggiungiInvitati(){

        for (PreferenzaInvitato p : pref){
            p.addInvitati(invitati);
            p.generaListe();

        }

    }

    public void aggiungiTavoli(){

        for (PreferenzaInvitato p : pref){
            p.getTavoli().addAll(tavoli);

        }
    }


    public void smista(){
        generaInsiemi();
        for (int i=0; i<pref.size(); i++){
            insiemi.get(i).getArraylistInvitati().addAll(pref.get(i).getLista_vicini());
            insiemiLontananza.get(i).getArraylistInvitati().addAll(pref.get(i).getLista_lontani());
        }
        aggiornaInsiemi();
        for (int k=0; k<insiemi.size(); k++){
            if (insiemi.get(k).getArraylistInvitati().size()<=1){
                insiemi.remove(k);
                k--;
            }
        }
        ottieniTavoliDisponibili();
        Collections.sort(tavoli);
        sistemaAiGruppi();
        rimuoviTavoliVuoti();
        sistemaAiTavoli();
        setTrueAllTables(tavoli);
        Collections.sort(tavoli);
        aggiungiTavoli();

        generaListeVincolatiLontani();
        for (PreferenzaInvitato prefe : pref){
            prefe.verifica_sistemaLontani();
        }
        tavoliTot.addAll(tavoli);
        tavoliTot.addAll(tavoliVincolati);
        controllaIncongruenze();
    }

    public void aggiornaInsiemi(){

        for (Tavolo t: tavoli){

            for (int i=0; i<insiemi.size(); i++){

                for (int k=0; k<insiemi.get(i).getArraylistInvitati().size(); k++){

                    if(t.getArraylistInvitati().size()!=0 && t.mostraID_Invitati().contains(insiemi.get(i).getArraylistInvitati().get(k).getID_Inv())){

                        if(insiemi.get(i).getArraylistInvitati().size()>1) {
                            preferenze_non_rispettate.add("Gli invitati " + insiemi.get(i).mostraID_Invitati() + " non possono essere sistemati vicini. "+insiemi.get(i).getArraylistInvitati().get(k).getID_Inv()+" è già vincolato ad un tavolo.\n");
                            System.out.println("Gli invitati " + insiemi.get(i).mostraID_Invitati() + " non possono essere sistemati vicini. "+insiemi.get(i).getArraylistInvitati().get(k).getID_Inv()+" è già vincolato ad un tavolo.\n");
                        }

                        insiemi.get(i).getArraylistInvitati().remove(k);
                        k--;
                    }
                }
            }
        }
    }


    public void organizza(Invitato inv, Tavolo tav){

        ArrayList<Invitato> lista = new ArrayList<>();

        for (Tavolo t : insiemi) {

            if(!(t.getArraylistInvitati().size()==0)) {

                for (Invitato i : t.getArraylistInvitati()) {

                    if (i.getID_Inv().equals(inv.getID_Inv())) {
                        lista.addAll(t.getArraylistInvitati());
                        t.getArraylistInvitati().clear();
                        break;
                    }
                }
            }
        }

        for (Tavolo t : insiemi) {

            boolean bool=false;
            if (!(t.getArraylistInvitati()==null)){

                for (Invitato i : t.getArraylistInvitati()){

                    for (int k = 0; k < lista.size(); k++) {

                        if (lista.get(k).getID_Inv().equals(i.getID_Inv())){

                            bool=true;
                            lista.addAll(t.getArraylistInvitati());
                            t.getArraylistInvitati().clear();
                            break;
                        }
                    }
                    if(bool==true){break;}
                }
            }
        }

        if (lista!=null) {eliminaDup(lista);}
        tav.addAllGuests(lista);
    }

    public void eliminaDup(ArrayList<Invitato> list){

        for (Invitato i : invitati){

            int contatore = 0;

            for (Invitato in : list){
                if (i.getID_Inv().equals(in.getID_Inv())){
                    contatore++;
                }
            }

            if(contatore>1){
                for (int inv=0; inv<list.size(); inv++){
                    if (i.getID_Inv().equals(list.get(inv).getID_Inv())){
                        list.remove(list.get(inv));
                        inv--;
                    }
                }

                list.add(i);
            }


        }
    }

    public void sistemaAiGruppi(){

        for (int i=0; i<invitati.size(); i++){
            grupponi.add(new Tavolo("Tavolo "+i, 40));
            organizza(invitati.get(i), grupponi.get(i));
        }

    }

    public void rimuoviTavoliVuoti(){
        for (int i=0; i<grupponi.size(); i++){
            if(grupponi.get(i).getArraylistInvitati().size()==0){
                grupponi.remove(i);
                i--;
            }
        }
    }

    public void sistemaAiTavoli(){

        if(grupponi.size()<=tavoli.size()) {

            for (Tavolo t : grupponi) {

                for (int tav = 0; tav < tavoli.size(); tav++) {

                    if (tavoli.get(tav).getDisponibile() == true && t.getArraylistInvitati().size() <= tavoli.get(tav).getNumPosti()) {
                        tavoli.get(tav).addAllGuests(t.getArraylistInvitati());
                        tavoli.get(tav).setDisponibile(false);
                        break;

                    } else if ((tav + 1) == tavoli.size() && (tavoli.get(tav).getDisponibile() == false || t.getArraylistInvitati().size() > tavoli.get(tav).getNumPosti())) {
                        System.out.println("Non è possibile sistemare gli invitati:\n" + t.mostraID_Invitati() + "\nai tavoli, poichè non sono sufficientemente grandi.\n\n");
                        preferenze_non_rispettate.add("Non è possibile sistemare gli invitati:\n" + t.mostraID_Invitati() + "\n  poichè non ci sono tavoli sufficientemente grandi da contenerli.\n\n");
                    }

                }
            }

        } else {
            System.out.println("I tavoli non sono sufficienti per contenere tutti i vincoli.\n\n");
            preferenze_non_rispettate.add("I tavoli non sono sufficienti per contenere tutti i vincoli.\n\n");
        }
    }

    public void setTrueAllTables(ArrayList<Tavolo> tavo){
        for (Tavolo t: tavo){
            t.setDisponibile(true);
        }
    }

    public void generaListeVincolatiLontani(){
        for (PreferenzaInvitato p : pref){
            for (PreferenzaInvitato pre : pref) {
                if (pre.getLista_lontani().size()>1 && pre.mostraID_Inv(pre.getLista_lontani()).contains(p.getLista_lontani().get(0).getID_Inv())){
                    p.getTutti_vincolati_lontani_a_ID().addAll(pre.getLista_lontani());
                }

                if (p.getLista_lontani().size()>1){
                    if (pre.getLista_lontani().size()>1 && pre.mostraID_Inv(pre.getLista_lontani()).contains(p.getLista_lontani().get(1).getID_Inv())){
                        p.getTutti_vincolati_lontani_a_primo().addAll(pre.getLista_lontani());
                    }
                }

                if (p.getLista_lontani().size()>2){
                    if (pre.getLista_lontani().size()>1 && pre.mostraID_Inv(pre.getLista_lontani()).contains(p.getLista_lontani().get(2).getID_Inv())){
                        p.getTutti_vincolati_lontani_a_secondo().addAll(pre.getLista_lontani());
                    }
                }
            }
        }
    }

    public ArrayList<Tavolo> sortaTavoli(){

        ArrayList<Tavolo> tavol = new ArrayList<>();
        Collections.sort(tavoli);
        for (Tavolo t : tavoliVincolati){
            t.setDisponibile(false);
        }
        tavol.addAll(tavoli);
        tavol.addAll(tavoliVincolati);
        return tavol;
    }

    public void smistaRestanti(GestoreLocale myLocale,GestoreEvento ev){
        myLocale.setTavoli(sortaTavoli());
        myLocale.smistamentoTavoli(ev);
    }

    public ArrayList<PreferenzaInvitato> getPref() {
        return pref;
    }

    public ArrayList<String> getPreferenze_non_rispettate() {
        return preferenze_non_rispettate;
    }

}
