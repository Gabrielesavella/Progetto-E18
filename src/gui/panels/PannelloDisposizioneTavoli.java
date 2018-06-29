package gui.panels;

import locale.*;
import persone.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lecciovich
 */

public class PannelloDisposizioneTavoli extends JPanel {
    public PannelloDisposizioneTavoli(Locale locale, Evento evento){
        JPanel  pAllGuests = new JPanel();
        JPanel  pTablesGuests = new JPanel();
        JLabel  labelElenco = new JLabel("Elenco invitati a " + evento.getName());
        JTextArea stampaElenco = new JTextArea();
        JLabel  labelDisposizione = new JLabel("Elenco invitati disposti per tavoli");
        JTextArea stampaDisposizione = new JTextArea();

        Dimension d=new Dimension(400,400);
        stampaDisposizione.setSize(d);
        stampaDisposizione.setLineWrap(true);

        pAllGuests.add(labelElenco);
        pAllGuests.add(stampaElenco);

        pTablesGuests.add(labelDisposizione);
        pTablesGuests.add(stampaDisposizione);



        stampaDisposizione.append("numero invitati: " + evento.getNumInvitati()+"\n");
        for (Invitato i:evento.getListaInvitati()) {
            stampaDisposizione.append(i.getID_Inv()+"\t"+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEta()+"\n");

        }


//        for (Invitato i:evento.getListaInvitati()) {
//            stampaElenco.append(i.getID_Inv(+"\t"+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEta()+"\n");
//        }
        stampaDisposizione.append("\nDisposizione:\n");

        for (Tavolo t:locale.smistamentoTavoli(evento)) {
            stampaDisposizione.append(t.getIDTavolo()+"\n");
            for (Invitato invitato:t.getArraylistInvitati()) {
                stampaDisposizione.append(invitato.getID_Inv()+"\t"+invitato.getNome()+"\t"+invitato.getCognome()+"\t"+invitato.getEta()+"\n");
            }
        }



    }
}
