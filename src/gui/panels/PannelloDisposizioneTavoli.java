package gui.panels;

import database.*;
import locale.*;
import persone.*;
import vincoli.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author lecciovich
 */

public class PannelloDisposizioneTavoli extends JPanel {
    public PannelloDisposizioneTavoli(GestoreLocale gestoreLocale, GestoreEvento gestoreEvento, GestoreVincoliTavolo gestoreVincoliTavolo){
        JPanel  pAllGuests = new JPanel();
        JPanel  pTablesGuests = new JPanel();
        JLabel  labelElenco = new JLabel("Elenco invitati a " + gestoreEvento.getName());
        JTextArea stampaElenco = new JTextArea();
        JLabel  labelDisposizione = new JLabel("Elenco invitati disposti per tavoli");
        JTextArea stampaDisposizione = new JTextArea();
        Dimension d=new Dimension(400,400);
        stampaDisposizione.setSize(d);
        stampaDisposizione.setLineWrap(true);
        stampaDisposizione.setEditable(false);
        stampaDisposizione.setAutoscrolls(true);
        stampaElenco.setEditable(false);
        stampaElenco.setAutoscrolls(true);
        stampaElenco.setSize(d);

        pAllGuests.add(labelElenco);
        pAllGuests.add(stampaElenco);

        pTablesGuests.add(labelDisposizione);
        pTablesGuests.add(stampaDisposizione);

        add(pAllGuests);
        add(pTablesGuests);



        stampaDisposizione.append("numero invitati: " + gestoreEvento.getNumInvitati()+"\n");
        for (Invitato i: gestoreEvento.getListaInvitati()) {
            stampaElenco.append(i.getID_Inv()+"\t"+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEta()+"\n");
        }


        stampaDisposizione.append("\nDisposizione:\n");

        ConnessioneDB conn = new ConnessioneDB();
        ArrayList <GestoreEvento> eventi = new ArrayList<>();
        eventi.add(gestoreEvento);
        gestoreLocale.aggiungiEventi(eventi);
        //stampaDisposizione.append(gestoreLocale.showInvitatiAiTavoli());
        ArrayList<SpecificaTavolo> specifiche= new ArrayList<>();
        specifiche=conn.getVincoloTavolo(gestoreEvento.getName());

        //GestoreVincoliTavolo vincolitavoli = new GestoreVincoliTavolo(gestoreEvento.getName());

        ArrayList<Tavolo> tavoliDisp= gestoreVincoliTavolo.getTavoliDisponibili();
        ArrayList<Tavolo> tavoliVincolati= gestoreVincoliTavolo.getTavoliVincolati();
        CreatePreferenza createPreferenza= new CreatePreferenza(gestoreEvento.getName(),tavoliDisp);
        createPreferenza.smista();
        for (Tavolo t:createPreferenza.getTavoli()) {
            stampaDisposizione.append(t.showInvitati()+"\n");
        }
    }
}
