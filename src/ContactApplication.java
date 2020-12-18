import java.io.File;
import java.io.IOException;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");

        contactReader.writeToLog("newContact 1-111-222-3333");

    }
}