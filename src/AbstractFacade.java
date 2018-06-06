import java.util.ArrayList;
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

    }

    private void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber){

    }
}
