package vincoli;

/**
 *
 * @author Salvatore Parisi
 */

import database.ConnessioneDB;
import locale.Tavolo;
import persone.Invitato;

import java.util.*;

public class GestoreVincoliTavolo {

    private ConnessioneDB c;

    private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
    private ArrayList<Tavolo> tavoliVincolati;
    private ArrayList<SpecificaTavolo> nessunaSpecifica, unicaSpecifica, piuSpecifiche, vincoliTav;
    private HashMap<String, Integer> contatori;
    private ArrayList<Invitato> invitati;
    private Invitato invi;

    public GestoreVincoliTavolo(String ID_Ev) {
        c.startConn();
        this.vincoliTav = c.getVincoloTavolo(ID_Ev);
        this.invitati = c.getInvitato(ID_Ev);
        smistaInvitati();
        Collections.sort(tavoli);
        assegnaTavoli(contaInvitatiPerVincolo(this.unicaSpecifica));
        accomodaInvitati(this.unicaSpecifica);

        accomodaInvitati(this.piuSpecifiche);
        chiudiTavolo();

    }

    public void smistaInvitati() {
        this.nessunaSpecifica = new ArrayList<SpecificaTavolo>();
        this.unicaSpecifica = new ArrayList<SpecificaTavolo>();
        this.piuSpecifiche = new ArrayList<SpecificaTavolo>();
        for (SpecificaTavolo s : vincoliTav) {
            int sum = s.vicinoTV + s.vegetariano + s.tavoloOnore + s.tavoloIsolato + s.difficoltaMotorie + s.bambini;
            switch (sum) {
                case 0:
                    this.nessunaSpecifica.add(s);
                case 1:
                    this.unicaSpecifica.add(s);
                default:
                    this.piuSpecifiche.add(s);

            }

        }

    }

    public HashMap<String, Integer> contaInvitatiPerVincolo(ArrayList<SpecificaTavolo> sp) {
        int contBambi = 0;
        int contVege = 0;
        int contTavOn = 0;
        int contTavIsola = 0;
        int contDiffMot = 0;
        int contVicinoTV = 0;
        contatori = new HashMap<String, Integer>();

        for (SpecificaTavolo s : sp) {
            contBambi += s.getBambini();
            contVege += s.getVegetariano();
            contVicinoTV += s.getVicinoTV();
            contDiffMot += s.getDifficoltaMotorie();
            contTavIsola += s.getTavoloIsolato();
            contTavOn += s.getTavoloOnore();
        }

        contatori.put("bambini", contBambi);
        contatori.put("vegetariani", contVege);
        contatori.put("vicinoTV", contVicinoTV);
        contatori.put("difficoltaMotorie", contDiffMot);
        contatori.put("tavoloIsolato", contTavIsola);
        contatori.put("tavoloOnore", contTavOn);

        List<String> mapChiavi = new ArrayList<>(contatori.keySet());
        List<Integer> mapValori = new ArrayList<>(contatori.values());
        Collections.sort(mapValori, Collections.reverseOrder());
        Collections.sort(mapChiavi, Collections.reverseOrder());

        LinkedHashMap<String, Integer> contatoriSort = new LinkedHashMap<>();

        Iterator<Integer> valoreIt = mapValori.iterator();
        while (valoreIt.hasNext()) {
            int val = valoreIt.next();

            Iterator<String> chiaveIt = mapChiavi.iterator();
            while (chiaveIt.hasNext()) {
                String key = chiaveIt.next();
                int comp1 = contatori.get(key);
                int comp2 = val;

                if (comp1 == comp2) {
                    chiaveIt.remove();
                    contatoriSort.put(key, val);
                    break;
                }
            }
        }
        return (HashMap) contatoriSort;
    }

    public void assegnaTavoli(HashMap<String, Integer> mappa) {
        tavoliVincolati = new ArrayList<>();
        for (Map.Entry<String, Integer> entrata : mappa.entrySet()) {
            for (Tavolo t : tavoli) {
                if ((!(t.getIDTavolo().equals(entrata.getKey()))) && (t.getPostiTot() >= entrata.getValue())) {
                    t.setId_tavolo(entrata.getKey());
                    tavoliVincolati.add(t);
                }
            }
        }
    }

    public Invitato prendiInvitati(String ID) {

        for (Invitato i : invitati) {
            if (ID == i.getID_Inv()) {
                this.invi = i;
                invitati.remove(i);
            }
        }
        return this.invi;
    }

    public void accomodaInvitati(ArrayList<SpecificaTavolo> vinc) {

        for (SpecificaTavolo sp : vinc) {
            for (Tavolo t : tavoli) {
                while (sp.getBambini() == 1 && t.getIDTavolo().equals("bambini") && t.getNumPosti() != 0) {
                    t.addGuest(prendiInvitati(sp.getID_Inv()));
                    vinc.remove(sp);
                }
                while (sp.getDifficoltaMotorie() == 1 && t.getIDTavolo().equals("difficoltaMotorie") && t.getNumPosti() != 0) {
                    t.addGuest(prendiInvitati(sp.getID_Inv()));
                    vinc.remove(sp);
                }
                while (sp.getTavoloOnore() == 1 && t.getIDTavolo().equals("tavoloOnore") && t.getNumPosti() != 0) {
                    t.addGuest(prendiInvitati(sp.getID_Inv()));
                    vinc.remove(sp);
                }
                while (sp.getVegetariano() == 1 && t.getIDTavolo().equals("vegetariani") && t.getNumPosti() != 0) {
                    t.addGuest(prendiInvitati(sp.getID_Inv()));
                    vinc.remove(sp);
                }
                while (sp.getVicinoTV() == 1 && t.getIDTavolo().equals("vicinoTV") && t.getNumPosti() != 0) {
                    t.addGuest(prendiInvitati(sp.getID_Inv()));
                    vinc.remove(sp);
                }
                while (sp.getTavoloIsolato() == 1 && t.getIDTavolo().equals("tavoloIsolato") && t.getNumPosti() != 0) {
                    t.addGuest(prendiInvitati(sp.getID_Inv()));
                    vinc.remove(sp);
                }
            }
        }
    }

    public void chiudiTavolo() {
        for (Tavolo t : tavoliVincolati) {
            if (t.getNumPosti() != 0) {
                t.setDisponibile(false);
            }
        }
    }
}
