import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        Input sc = new Input();

//        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
//
//       List<String> allContacts =  contactReader.getFileLines();
//        System.out.println(allContacts);



//        for(int i = 0; i < allContacts.size(); i+=2){
//            format(allContacts.get(i),allContacts.get(i+1));
//        }




        boolean notExit = true;

        do{
            displayHomeScreen(sc);
            if(displayHomeScreen(sc) == 5){
                notExit = false;
            }
        }while(notExit);
        System.out.println("You chose to exit Bye!");


    }

    public static void format(String name, String number){
        System.out.println(name + " | " + number);

    }


    public static int displayHomeScreen(Input sc) throws IOException {
        System.out.println("1 - View contact");
        System.out.println("2 - Add a new contact");
        System.out.println("3 - Search a contact by name");
        System.out.println("4 - Delete an existing contact");
        System.out.println("5 - Exit");

        return runUserOption(sc.getNumber("Enter an option (1, 2, 3, 4, or 5)", 1,5), sc);

    }

    public static int runUserOption(int option, Input sc) throws IOException {
        switch(option){
            case 1:
                viewAllContacts();
                break;
            case 2:
                addNewContact(sc);
                break;
            case 3:
                searchByName();
                break;
            case 4:
                deleteContact();
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

    public static void viewAllContacts(){
        System.out.println("Will display contact");
    }

    public static void searchByName(){
        System.out.println("Will display specified contaict");
    }

    public static  void deleteContact(){
        System.out.println("Will delete specified contact");
    }

}