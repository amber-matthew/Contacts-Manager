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
            int choice = displayHomeScreen(sc, allContacts);
            System.out.println(" ");
            if(choice == 5){
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
                addNewContact(sc, allContacts);
                break;
            case 3:
                searchByName(sc, allContacts);
                break;
            case 4:
                deleteContact(sc, allContacts);
                break;
        }
        return option;
    }

    public static void addNewContact(Input sc, List<String> allContacts) throws IOException {
        String newContactName = sc.getString("What is this person's name?");
        String newContactNumber = sc.getString("What is this person's number?");

        //TODO: FORMAT THE NUMBER TO (111)-111-1111;

        Contact person1 = new Contact(newContactName, newContactNumber);
        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
        contactReader.writeToLog(person1);
        allContacts.add(person1.getName());
        allContacts.add(person1.getNumber());
    }




    public static void viewAllContacts(List<String> allContacts) throws IOException {

        // TODO: FORMAT THE OUTPUT

        System.out.println(" ");
        System.out.printf("Name      | Phone number |%n--------------------------%n");

        for(int i = 0; i < allContacts.size(); i+=2){
            format(allContacts.get(i),allContacts.get(i+1));
        }

    }





    public static void searchByName(Input sc, List<String> allContacts){
        String nameToFind = sc.getString("Who are you looking for? Enter a name");
        int index = allContacts.indexOf(nameToFind);

        if(allContacts.contains(nameToFind)){
            System.out.println(nameToFind + "'s number is " + allContacts.get(index + 1));
        } else {
            System.out.println("This contact does not exist");
        }
    }




    public static  void deleteContact(Input sc, List<String> allContacts) throws IOException {
        System.out.println("Current contact list: " + allContacts);
        String choice = sc.getString("What contact do you want to delete?");

        //TODO: TRY TO IMPLEMENT A TRY CATCH FOR A NUMBER THAT DOES NOT EXIST


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