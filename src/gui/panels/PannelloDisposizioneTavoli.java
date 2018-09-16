package gui.panels;

import gui.controller.SistemaDiPrenotazione;
import gui.finestre.FinestraLogin;
import gui.finestre.FinestraSpecificheEvento;
import locale.*;
import persone.*;
import vincoli.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


/**
 *
 * @author lecciovich,gabrieleSavella
 */

public class PannelloDisposizioneTavoli extends PaintedPanel {


    public PannelloDisposizioneTavoli(Locale locale, Evento gestoreEvento, GestoreVincoliTavolo gestoreVincoliTavolo, JFrame frame, SistemaDiPrenotazione sisPr){
        super("images/celebration-dining-drink-696214.jpg",frame);

        JScrollPane  pAllGuests;
        JScrollPane  pTablesGuests;
        JScrollPane  pVincNnRisp;
        JPanel pTablesAndLabel = new JPanel();
        JPanel pAllAndLabel = new JPanel();
        JPanel pVincAndLabel=new JPanel();
        JPanel pButtons = new JPanel();
        JLabel  labelElenco = new JLabel(" Elenco invitati all'evento ''"+gestoreEvento.getName()+"'' ");
        JTextArea stampaElenco = new JTextArea(10,20);
        JLabel  labelDisposizione = new JLabel(" Elenco invitati disposti per tavoli ");
        JTextArea stampaDisposizione = new JTextArea(10,35);
        JLabel labelVincoliNnRisp= new JLabel(" Vincoli Non rispettati:");
        JTextArea stampaVincoliNnRisp = new JTextArea(10,35);
        //JButton reinizializzaDb = new JButton("Reinserisci invitati e vincoli");
        JButton ritornaHome = new JButton("Ritorna alla Home");


        Font fontB=new Font ("Monotype Corsiva", Font.ROMAN_BASELINE, 18);
        labelElenco.setFont(fontB);
        labelDisposizione.setFont(fontB);
        labelVincoliNnRisp.setFont(fontB);

        labelElenco.setOpaque(true);
        labelElenco.setBackground(new Color(255,235,205,200));

        labelDisposizione.setOpaque(true);
        labelDisposizione.setBackground(new Color(255,235,205,200));

        labelVincoliNnRisp.setOpaque(true);
        labelVincoliNnRisp.setBackground(new Color(255,235,205,200));

        Dimension d=new Dimension(400,400);


        stampaDisposizione.setLineWrap(true);
        stampaDisposizione.setWrapStyleWord(true);
        stampaDisposizione.setEditable(false);
        stampaDisposizione.setAutoscrolls(true);
        stampaElenco.setLineWrap(true);
        stampaElenco.setWrapStyleWord(true);
        stampaElenco.setEditable(false);
        stampaElenco.setAutoscrolls(true);
        stampaElenco.setBackground(new Color(255,235,205));
        stampaVincoliNnRisp.setLineWrap(true);
        stampaVincoliNnRisp.setWrapStyleWord(true);
        stampaVincoliNnRisp.setEditable(false);
        stampaVincoliNnRisp.setAutoscrolls(true);
        stampaVincoliNnRisp.setBackground(new Color(255,235,205));


        pAllGuests = new JScrollPane(stampaElenco);
        pAllGuests.setBackground(new Color(255,235,205));
        pAllGuests.setOpaque(false);
        pAllGuests.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pAllGuests.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pTablesGuests = new JScrollPane(stampaDisposizione);
        pTablesGuests.setBackground(new Color(255,235,205));
        pTablesGuests.setOpaque(false);
        pTablesGuests.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pTablesGuests.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pVincNnRisp = new JScrollPane(stampaVincoliNnRisp);
        pVincNnRisp.setBackground(new Color(255,235,205));
        pVincNnRisp.setOpaque(false);
        pVincNnRisp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pVincNnRisp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        pTablesAndLabel.add(labelDisposizione);
        pTablesAndLabel.add(pTablesGuests);
        pTablesAndLabel.setOpaque(false);
        pAllAndLabel.add(labelElenco);
        pAllAndLabel.add(pAllGuests);
        pAllAndLabel.setOpaque(false);
        //pButtons.add(reinizializzaDb);
        pButtons.add(ritornaHome);
        pButtons.setOpaque(false);
        pVincAndLabel.add(labelVincoliNnRisp);
        pVincAndLabel.add(pVincNnRisp);
        pVincAndLabel.setOpaque(false);

        this.setLayout(new GridLayout(0,1));
        add(pAllAndLabel);
        add(pTablesAndLabel);
        add(pVincAndLabel);
        add(pButtons);

        stampaDisposizione.append("Numero invitati: " + gestoreEvento.getNumInvitati()+"\n\n");
        for (Invitato i: gestoreEvento.getListaInvitati()) {
            stampaElenco.append(" • "+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEta()+"\n");
        }
        stampaDisposizione.append("\nDisposizione:\n\n");
        stampaDisposizione.setBackground(new Color(255, 235, 205));
        locale.aggiungiEvento(gestoreEvento);
        ArrayList<Tavolo> tavoliTot= gestoreVincoliTavolo.getTavoliTotali();
        ArrayList<Tavolo> tavoliDisp = new ArrayList<>();
        ArrayList<Tavolo> tavoliVinc = gestoreVincoliTavolo.getTavoliVincolati();
        tavoliDisp = gestoreVincoliTavolo.getTavoliDisponibili();
        CreatePreferenza createPreferenza= new CreatePreferenza(gestoreEvento.getName(),tavoliTot);
        createPreferenza.smista();
        createPreferenza.smistaRestanti(locale,gestoreEvento);

        for (Tavolo ta : tavoliVinc){
            stampaDisposizione.append(ta.showInvitati()+"\n");
        }

        for (Tavolo t:createPreferenza.getTavoliUtilizzati()) {
            stampaDisposizione.append(t.showInvitati()+"\n");
        }


        for (String pref:createPreferenza.getPreferenze_non_rispettate()) {
            stampaVincoliNnRisp.append(pref+"\n\n");
        }

        /*reinizializzaDb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                sisPr.reinizializzaDb(gestoreEvento);
                FinestraSpecificheEvento fs= new FinestraSpecificheEvento(locale, gestoreEvento);//fetchEvento
                fs.setVisible(true);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                frame.dispose();
            }
        });*/

        ritornaHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                frame.dispose();
                FinestraLogin fl= new FinestraLogin();
                fl.setVisible(true);
            }
        });


    }

}
