import javax.annotation.processing.Filer;
import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.*;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        Input sc = new Input();
        Ascii art = new Ascii();

        boolean notExit = true;
        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
        List<String> allContacts =  contactReader.getFileLines();
        HashMap<String, String> contactsList = new HashMap<>();

        for(int i = 0; i < allContacts.size(); i+=2){
            contactsList.put(allContacts.get(i), allContacts.get(i + 1));
        }

        art.art2();
        do{
            int choice = displayHomeScreen(sc, contactsList, allContacts, contactReader);
            System.out.println(" ");
            if(choice == 5){
                notExit = false;
            }
        }while(notExit);
        System.out.println("You chose to exit Bye!");
    }


    public static void format(String name, String number){
        System.out.printf("\033[0;34m|  %-20s|  %-20s|%n",name,number);
    }


    public static int displayHomeScreen(Input sc, HashMap<String, String> allContacts, List<String> contactList, FileReader contactReader) throws IOException {
        System.out.println("1 - View contacts");
        System.out.println("2 - Add a new contact");
        System.out.println("3 - Search a contact by name");
        System.out.println("4 - Delete an existing contact");
        System.out.println("5 - Exit");

        return runUserOption(sc.getNumber("Enter an option (1, 2, 3, 4, or 5)", 1,5), sc, allContacts, contactList, contactReader);
    }

    public static int runUserOption(int option, Input sc, HashMap<String, String> allContacts, List<String> contactList, FileReader contactReader) throws IOException {
        switch(option){
            case 1:
                viewAllContacts(allContacts, contactReader);
                break;
            case 2:
                addNewContact(sc, allContacts, contactList, contactReader);
                break;
            case 3:
                searchByName(sc, allContacts);
                break;
            case 4:
                deleteContact(sc, allContacts, contactList, contactReader);
                break;
        }
        return option;
    }

    public static void addNewContact(Input sc, HashMap<String, String> allContacts, List<String> contactList, FileReader contactReader) throws IOException {
        String inputName = sc.getString("What is this person's name?");
        String newContactName = firstLastCap(inputName);

        FileReader logWriter = new FileReader("src", "contacts.log", "contacts.log");

        if (allContacts.get(newContactName) != null) {
            boolean validInput = true;
            if (sc.yesNo("There is already a contact named " + newContactName + " would you like to override?")) {

                String oldNumber = allContacts.get(newContactName);
                String newContactNumber = sc.getString("What is " + newContactName + "'s new contact number?");
                if(newContactNumber.contains("-")){
                    newContactNumber = newContactNumber.replaceAll("-", "");
                }
                try{
                    Long.parseLong(newContactNumber);
                } catch (Exception e){
                    logWriter.writeToLog(newContactNumber+" is not a valid number- "+e.getMessage());
                    validInput = false;
                }

                if(validInput){
                    String formattedContactNumber = formatPhoneNum(newContactNumber);
                    allContacts.put(newContactName, formattedContactNumber);
                    contactList.add(newContactName);
                    contactList.add(formattedContactNumber);
                    contactReader.updateLog(allContacts, oldNumber, formattedContactNumber);
                }
            }else{
                System.out.println("Re-enter information");
                addNewContact(sc, allContacts, contactList, contactReader);
            }

        }else {
            boolean isValid = true;
            String newContactNumber = sc.getString("What is this person's number?");
            if(newContactNumber.contains("-")){
                newContactNumber = newContactNumber.replaceAll("-", "");
            }

            try{
                Long.parseLong(newContactNumber);
            } catch (Exception e){
                logWriter.writeToLog(newContactNumber+" is not a valid number for contact " +newContactName + " "+e.getMessage());
                isValid = false;
            }

            if(isValid) {
                String formattedNewNumber = formatPhoneNum(newContactNumber);
                Contact person1 = new Contact(newContactName, formattedNewNumber);
                contactList.add(newContactName);
                contactList.add(formattedNewNumber);
                allContacts.put(person1.getName(), person1.getNumber());
                contactReader.overwriteLog(allContacts, "Unable to add new contact");
            }
        }
    }

    public static String formatPhoneNum(String num){
        if(num.length() == 10){
            return tenFormat(num);
        } else if(num.length() == 7){
            return sevenFormat(num);
        } else if(num.length() == 11){
            return elevenFormat(num);
        }
        return num;
    }
    public static String tenFormat(String num){
        return "("+num.substring(0, 3)+")-"+sevenFormat(num.substring(3));
    }
    public static String sevenFormat(String num){
        return num.substring(0, 3)+"-"+num.substring(3);
    }
    public static String elevenFormat(String num){
        return num.charAt(0)+"-"+tenFormat(num.substring(1));
    }




    public static void viewAllContacts(HashMap<String, String> allContacts, FileReader contactReader) throws IOException {
        System.out.println(" ");
        String name = "Name";
        String number = "Number";

        for(int i = 0; i < 46; i++){
            System.out.print("\033[0;33m#");
        }
        System.out.printf("%n\033[0;34m|  %-20s|  %-20s|%n|", name, number);
        for(int i = 0; i < 45; i++){
            if(i == 44){
                System.out.printf("-|%n");
            }else
            System.out.print("-");
        }

        List<String> displaySortedContacts = contactReader.storeDataAsSorted(allContacts);

        for(int i = 0; i < displaySortedContacts.size(); i+=2){
            format(displaySortedContacts.get(i), displaySortedContacts.get(i + 1));
        }

        for(int i = 0; i < 46; i++){
            System.out.print("\033[0;33m#\033[0;38m");
        }
    }





    public static void searchByName(Input sc, HashMap<String, String> allContacts){
        String inputName = sc.getString("Who are you looking for? Enter a name");
        String nameToFind = firstLastCap(inputName);

        boolean doesExist = false;
        for(Map.Entry<String, String> contact : allContacts.entrySet()){
            if(contact.getKey().toLowerCase().trim().equals(nameToFind.toLowerCase().trim())){
                doesExist = true;
                System.out.println(nameToFind + "'s number is " + contact.getValue());
            }
        }

        if(!doesExist){
            System.out.println("This contact does not exist.");
        }
    }




    public static  void deleteContact(Input sc, HashMap<String, String> allContacts, List<String> contactList, FileReader contactReader) throws IOException {

        boolean validChoice = true;
        System.out.println("Current contact list: ");
        viewAllContacts(allContacts, contactReader);
        System.out.println();

        String choice = sc.getString("What contact do you want to delete?").toLowerCase();
        contactList.replaceAll(ContactName -> ContactName.toLowerCase().trim());

        String[] nameToDeleteArr = choice.split(" ");
        String lastL = "";
        String restOfLast = "";
        if (nameToDeleteArr.length > 1) {
            lastL = nameToDeleteArr[1].substring(0, 1).toUpperCase();
            restOfLast = nameToDeleteArr[1].substring(1).toLowerCase();
        }
        String firstL = nameToDeleteArr[0].substring(0, 1).toUpperCase();
        String restOfFirst = nameToDeleteArr[0].substring(1).toLowerCase();

        String firstName = firstL + restOfFirst;
        String lastName = lastL + restOfLast;


        try{
            int index = contactList.indexOf(choice);
            contactList.get(index);

        } catch (Exception e){
            FileReader logWriter = new FileReader("src", "contacts.log", "contacts.log");
            logWriter.writeToLog("Unable to find contact name: "+choice+"- "+e.getMessage());
            validChoice = false;
            System.out.println("No contact found with name " +choice);
        }

        if(validChoice){
            String numberToDelete = "";
            for(Map.Entry<String, String> person : allContacts.entrySet()) {
                if(choice.equals(person.getKey().toLowerCase().trim())){
                    numberToDelete = person.getValue();
                }
            }
            if (sc.yesNo("You are deleting " + firstLastCap(choice) + " with number " + numberToDelete + " are you sure?")) {
                allContacts.remove(formatName(firstName, lastName));
                contactReader.overwriteLog(allContacts, choice, numberToDelete);
            }
        }
    }

    public static String formatName(String first, String last){
        return first + " " + last;
    }

    public static String firstLastCap(String input){
        String[] names = input.split(" ");
        if(names.length > 1) {
            return names[0].substring(0, 1).toUpperCase() + names[0].substring(1).toLowerCase() + " " + names[1].substring(0, 1).toUpperCase() + names[1].substring(1).toLowerCase();
        }
        return names[0].substring(0, 1).toUpperCase() + names[0].substring(1).toLowerCase() + " ";
    }

}