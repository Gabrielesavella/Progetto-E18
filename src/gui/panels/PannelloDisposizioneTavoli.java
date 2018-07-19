package gui.panels;

import locale.*;
import persone.*;
import vincoli.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author lecciovich,gabrieleSavella
 */

public class PannelloDisposizioneTavoli extends JPanel {

    private Image backgroundImage;

    public PannelloDisposizioneTavoli(GestoreLocale gestoreLocale, GestoreEvento gestoreEvento, GestoreVincoliTavolo gestoreVincoliTavolo,JFrame frame){

        try{
            backgroundImage = ImageIO.read(new File("images/celebration-dining-drink-696214.jpg"));
            backgroundImage=backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
        JPanel  pAllGuests = new JPanel();
        JPanel  pTablesGuests = new JPanel();
        JLabel  labelElenco = new JLabel("Elenco invitati a " + gestoreEvento.getName());
        JTextArea stampaElenco = new JTextArea();
        JLabel  labelDisposizione = new JLabel("Elenco invitati disposti per tavoli");
        JTextArea stampaDisposizione = new JTextArea();
        labelElenco.setOpaque(true);
        labelElenco.setBackground(new Color(255,255,255,127));

        labelDisposizione.setOpaque(true);
        labelDisposizione.setBackground(new Color(255,255,255,127));
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
        pAllGuests.setOpaque(false);
        pTablesGuests.setOpaque(false);
        add(pAllGuests);
        add(pTablesGuests);

        stampaDisposizione.append("numero invitati: " + gestoreEvento.getNumInvitati()+"\n");
        for (Invitato i: gestoreEvento.getListaInvitati()) {
            stampaElenco.append(" "+i.getNome()+"\t"+i.getCognome()+"\t"+i.getEta()+"\n");
        }
        stampaDisposizione.append("\nDisposizione:\n");
        gestoreLocale.aggiungiEvento(gestoreEvento);
        ArrayList<Tavolo> tavoliTot= gestoreVincoliTavolo.getTavoliTotali();
        CreatePreferenza createPreferenza= new CreatePreferenza(gestoreEvento.getName(),tavoliTot);
        createPreferenza.smista();
        createPreferenza.smistaRestanti(gestoreLocale,gestoreEvento);

        for (Tavolo t:createPreferenza.getTavoliUtilizzati()) {
            stampaDisposizione.append(t.showInvitati()+"\n");
        }
    }
    //parte adibita alla "pittura" della foto sullo sfondo
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim= this.getSize();
        int width= dim.width;
        int height= dim.height;
        g.drawImage(backgroundImage, 0, 0,width,height, this);

    }

}
