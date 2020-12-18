import java.io.File;
import java.io.IOException;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        Input sc = new Input();

        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");


        String newContact = sc.getString("add a contact");

        contactReader.writeToLog(newContact);


    }
}