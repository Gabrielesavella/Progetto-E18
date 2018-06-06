import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AbstractFacade {
    protected ArrayList <String> field = new ArrayList<String>();

    private void WriteClient(String name, String surname, String email, String password){
        field.add(name);
        field.add(surname);
        field.add(email);
        field.add(password);
    }

    private void WriteGuests(String fiscaleCode,String nameGuest, String surnameGuest,int age){
        field.add(fiscaleCode);
        field.add(nameGuest);
        field.add(surnameGuest);
        field.add(Integer.toString(age));


    }

    private void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber){
       //promemoria per @author Gabrielesavella : da convertire dateevent in string (ora mancanzi di tempo causa lezione
        field.add(nameEvent);
        field.add(Integer.toString(dateEvent.get(Calendar.DATE)));
        field.add(Integer.toString(guestNumber));

    }
}
