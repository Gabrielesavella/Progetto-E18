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
        JLabel  labelDisposizione = new JLabel("Disposizione Tavoli per evento " + evento.getName());
        JTextArea stampaDisposizione = new JTextArea();

        Dimension d=new Dimension(400,400);
        stampaDisposizione.setSize(d);
        stampaDisposizione.setLineWrap(true);

        stampaDisposizione.append("numero invitati: " + evento.getNumInvitati()+"\n");
        for (Invitato i:evento.getListaInvitati()) {
            stampaDisposizione.append(i.getCf()+"\t"+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEtà()+"\n");
        }
//        stampaDisposizione.append("\nDisposizione:\n");
//        for (Tavolo t:evento) {
//
//        }
//        stampaDisposizione.append();
        evento.getLocation().smistamentoTavoli(evento);
//        stampaDisposizione.append("\t"+evento.getLocation().);

        this.add(labelDisposizione);
        this.add(stampaDisposizione);

    }
}
