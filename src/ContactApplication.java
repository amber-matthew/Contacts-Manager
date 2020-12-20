import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        Input sc = new Input();


        boolean notExit = true;
        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
        List<String> allContacts =  contactReader.getFileLines();

        do{
            displayHomeScreen(sc, allContacts);
            System.out.println(" ");
            if(displayHomeScreen(sc, allContacts) == 5){
                notExit = false;
            }
        }while(notExit);
        System.out.println("You chose to exit Bye!");


    }

    public static void format(String name, String number){
        System.out.println(name + " | " + number);
    }


    public static int displayHomeScreen(Input sc, List<String> allContacts) throws IOException {
        System.out.println("1 - View contacts");
        System.out.println("2 - Add a new contact");
        System.out.println("3 - Search a contact by name");
        System.out.println("4 - Delete an existing contact");
        System.out.println("5 - Exit");

        return runUserOption(sc.getNumber("Enter an option (1, 2, 3, 4, or 5)", 1,5), sc, allContacts);
    }

    public static int runUserOption(int option, Input sc, List<String> allContacts) throws IOException {
        switch(option){
            case 1:
                viewAllContacts(allContacts);
                break;
            case 2:
                addNewContact(sc);
                break;
            case 3:
                searchByName();
                break;
            case 4:
                deleteContact(sc, allContacts);
                break;
        }
        return 5;
    }

    public static void addNewContact(Input sc) throws IOException {
        String newContactName = sc.getString("What is this person's name?");
        String newContactNumber = sc.getString("What is this person's number?");

        Contact person1 = new Contact(newContactName, newContactNumber);
        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
        contactReader.writeToLog(person1);
    }




    public static void viewAllContacts(List<String> allContacts) throws IOException {

        System.out.println(" ");
        System.out.printf("Name      | Phone number |%n--------------------------%n");

        for(int i = 0; i < allContacts.size(); i+=2){
            format(allContacts.get(i),allContacts.get(i+1));
        }

    }





    public static void searchByName(){
        System.out.println("Will display specified contact");
    }





    public static  void deleteContact(Input sc, List<String> allContacts) throws IOException {
        System.out.println("Current contact list: " + allContacts);
        String choice = sc.getString("What contact do you want to delete?");


        int personIndex = allContacts.indexOf(choice);
        String numberToDelete = allContacts.get(personIndex + 1);
        System.out.println("You are deleting " + choice + " with number " + numberToDelete + " are you sure?");
        allContacts.remove(personIndex);


        allContacts.remove(personIndex);
        System.out.println(allContacts);


//        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
//        contactReader.overwriteLog(allContacts, choice, numberToDelete);


    }

}