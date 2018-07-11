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
//        JScrollBar scroll = new JScrollPane(stampaElenco);

        //stampaElenco.(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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

       // for (Tavolo t: gestoreLocale.smistamentoTavoli(gestoreEvento)) {
           // stampaDisposizione.append(t.getIDTavolo()+"\n");
          ////   stampaDisposizione.append(invitato.getID_Inv()+"\t"+invitato.getNome()+"\t"+invitato.getCognome()+"\t"+invitato.getEta()+"\n");
          //  }
        //}

        stampaDisposizione.append("\nDisposizione:\n");

        ConnessioneDB conn = new ConnessioneDB();
        ArrayList <GestoreEvento> eventi = new ArrayList<>();
        eventi.add(gestoreEvento);
        System.out.println("numero eventi prima: "+gestoreLocale.getEventi().toString());

        gestoreLocale.aggiungiEventi(eventi);
        System.out.println("numero eventi dopo: "+gestoreLocale.getEventi().size());
        for (Tavolo t:gestoreLocale.smistamentoTavoli(gestoreEvento)) {
            System.out.println("sono dentro!");
//            stampaDisposizione.append(t.showInvitati());
            }

        //stampaDisposizione.append(gestoreLocale.showInvitatiAiTavoli());
        ArrayList<SpecificaTavolo> specifiche= new ArrayList<>();
        specifiche=conn.getVincoloTavolo(gestoreEvento.getName());

        if(specifiche!=null){
            System.out.println("le specifiche ci sono");
        }


        //GestoreVincoliTavolo vincolitavoli = new GestoreVincoliTavolo(gestoreEvento.getName());

        ArrayList<Tavolo> tavoliDisp= gestoreVincoliTavolo.getTavoliDisponibili();
        ArrayList<Tavolo> tavoliVincolati= gestoreVincoliTavolo.getTavoliVincolati();
        CreatePreferenza createPreferenza= new CreatePreferenza(gestoreEvento.getName(),tavoliDisp);
        createPreferenza.smista();

//        for (Tavolo t:tavoliVincolati) {
//            stampaDisposizione.append(String.valueOf(t.getArraylistInvitati().size()));
//            stampaDisposizione.append(t.showInvitati());
//            for (Invitato i:t.getArraylistInvitati()) {
//                stampaDisposizione.append(i.getID_Inv()+"\n");
//            }
//        }
        for (Tavolo t:createPreferenza.getTavoli()) {
            stampaDisposizione.append(t.showInvitati()+"\n");
        }




       /* for (Tavolo t:gestoreLocale.getTavoliLocale()){
            System.out.println("ciao");
            stampaDisposizione.append(t.showInvitati());
            //System.out.println("tavolo vincolato"+t.getIDTavolo());
        }*/

       // GestorePreferenzaInvitato gestorepreferenza = new GestorePreferenzaInvitato(gestoreEvento.getName(),vincolitavoli)
    }
}
