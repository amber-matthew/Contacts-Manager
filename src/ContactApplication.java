import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        Input sc = new Input();

        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");

       List<String> allContacts =  contactReader.getFileLines();
        System.out.println(allContacts);

//        String newContactName = sc.getString("What is this person's name?");
//        String newContactNumber = sc.getString("What is this person's number?");
//
//        Contact person1 = new Contact(newContactName, newContactNumber);
//
//        contactReader.writeToLog(person1);

        for(int i = 0; i < allContacts.size(); i+=2){
            format(allContacts.get(i),allContacts.get(i+1));
        }




//        boolean notExit = true;
//
//        do{
//            displayHomeScreen();
//        }while(notExit);
//

    }

    public static void format(String name, String number){
        System.out.println(name + " | " + number);

    }


    public static void displayHomeScreen(){
        System.out.println("1 - View contact");
        System.out.println("2 - Add a new contact");
        System.out.println("3 - Search a contact by name");
        System.out.println("4 - Delete an existing contact");
        System.out.println("5 - Exit");
        System.out.println("Enter an option (1, 2, 3, 4, or 5)");
    }
}