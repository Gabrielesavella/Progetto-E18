package gui.panels;

import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import persone.Invitato;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

        this.add(pAllGuests);
        this.add(pTablesGuests);

        stampaElenco.append("Numero invitati: " + evento.getNumInvitati()+"\n");
        stampaElenco.append("Invitati presenti: " + evento.getListaInvitati().size()+"\n");

        for (Invitato i:evento.getListaInvitati()) {
            stampaElenco.append(i.getCf()+"\t"+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEtà()+"\n");
        }
        stampaDisposizione.append("\nDisposizione:\n");

        for (Tavolo t:locale.smistamentoTavoli(evento)) {
            stampaDisposizione.append(t.getIDTavolo()+"\n");
            for (Invitato invitato:t.getArraylistInvitati()) {
                stampaDisposizione.append(invitato.getCf()+"\t"+invitato.getNome()+"\t"+invitato.getCognome()+"\t"+invitato.getEtà()+"\n");
            }
        }
//        stampaDisposizione.append();
//        for (Tavolo tavolo:evento.getLocation().smistamentoTavoli(evento)) {
//            ArrayList<Invitato> invitatiTavolo=tavolo.getArraylistInvitati();
//            for (Invitato i:invitatiTavolo) {
//                stampaDisposizione.append(i.getCf()+"\t"+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEtà()+"\n");
//            }
//        }
//        stampaDisposizione.append("\t"+evento.getLocation().);


    }
}
