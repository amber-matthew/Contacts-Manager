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
            int choice = displayHomeScreen(sc, contactsList, allContacts);
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


    public static int displayHomeScreen(Input sc, HashMap<String, String> allContacts, List<String> contactList) throws IOException {
        System.out.println("1 - View contacts");
        System.out.println("2 - Add a new contact");
        System.out.println("3 - Search a contact by name");
        System.out.println("4 - Delete an existing contact");
        System.out.println("5 - Exit");

        return runUserOption(sc.getNumber("Enter an option (1, 2, 3, 4, or 5)", 1,5), sc, allContacts, contactList);
    }

    public static int runUserOption(int option, Input sc, HashMap<String, String> allContacts, List<String> contactList) throws IOException {
        switch(option){
            case 1:
                viewAllContacts(allContacts);
                break;
            case 2:
                addNewContact(sc, allContacts, contactList);
                break;
            case 3:
                searchByName(sc, allContacts);
                break;
            case 4:
                deleteContact(sc, allContacts, contactList);
                break;
        }
        return option;
    }

    public static void addNewContact(Input sc, HashMap<String, String> allContacts, List<String> contactList) throws IOException {
        String inputName = sc.getString("What is this person's name?");
        String newContactName = firstLastCap(inputName);

        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
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
                addNewContact(sc, allContacts, contactList);
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
                contactReader.writeToLog(person1);
                contactList.add(newContactName);
                contactList.add(formattedNewNumber);
                allContacts.put(person1.getName(), person1.getNumber());
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




    public static void viewAllContacts(HashMap<String, String> allContacts) throws IOException {
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
        ArrayList<String> toSortContacts = new ArrayList<>();

        for(Map.Entry<String, String> entry : allContacts.entrySet()){
            StringBuilder contactWithNumber = new StringBuilder();
            contactWithNumber.append(entry.getKey());
            contactWithNumber.append("%");
            contactWithNumber.append(entry.getValue());
            toSortContacts.add(contactWithNumber.toString());
        }

        Collections.sort(toSortContacts);

        for(String contact : toSortContacts){
            format(contact.split("%")[0], contact.split("%")[1]);
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




    public static  void deleteContact(Input sc, HashMap<String, String> allContacts, List<String> contactList) throws IOException {

        boolean validChoice = true;
        System.out.println("Current contact list: ");
        viewAllContacts(allContacts);
        System.out.println();

        // ====== LOWER CASE THE INPUT FOR WHO THEY WANT TO DELETE
        String choice = sc.getString("What contact do you want to delete?").toLowerCase();
        // LOWERCASE ALL THE CONTACTS FROM THE TEXT FILE => TRY CATCH => IF NOT BOTH IN LOWER CASE, THE VALUE WILL NEVER BE FOUND EVEN IF IT DOES EXIST
        contactList.replaceAll(ContactName -> ContactName.toLowerCase().trim()); // FOR EVERY CONTACT, LOWERCASE THEM ALL AND PUT THEM BACK IN THE CONTACT LIST FOR THE TRY CATCH

        //ALL OF THIS WAS DONE SO THE FIRST NAME, LAST NAME VARIABLES CAN BE PASSED IN AS THE CONTACT TO BE REMOVED FROM THE CONTACTS.TXT FILE => {
        String[] nameToDeleteArr = choice.split(" ");
        String lastL = "";
        String restOfLast = "";
        if (nameToDeleteArr.length > 1) {
            lastL = nameToDeleteArr[1].substring(0, 1).toUpperCase();
            restOfLast = nameToDeleteArr[1].substring(1).toLowerCase();
        }
        String firstL = nameToDeleteArr[0].substring(0, 1).toUpperCase();
        String restOfFirst = nameToDeleteArr[0].substring(1).toLowerCase();

        // THE NAMES IN THE HASH MAP CONTACTS TEXT FILE ARE FORMATTED IN A VERY SPECIFIC
        String firstName = firstL + restOfFirst;
        String lastName = lastL + restOfLast;
    //}




        try{
            int index = contactList.indexOf(choice); // POTENTIALLY FIND A VALUE THAT DOES NOT EXIST IN THE CONTACT LIST ARRAY
            contactList.get(index); // IF THAT CHOICE'S INDEX IS NOT THERE, THE INT INDEX WILL BE -1 => CONTACT LIST .GET OF A -1 INDEX WILL SET THE ERROR

        } catch (Exception e){
            FileReader logWriter = new FileReader("src", "contacts.log", "contacts.log");
            logWriter.writeToLog("Unable to find contact name: "+choice+"- "+e.getMessage());
            validChoice = false;
            System.out.println("No contact found with name " +choice);
        }

        if(validChoice){
            String numberToDelete = ""; // SET TO EMPTY STRING UNTIL THE NAME THEY ARE LOOKING FOR IS FOUND TO THEN SET THIS TO THAT SPECIFIC PERSON'S NUMBER
            for(Map.Entry<String, String> person : allContacts.entrySet()) { // LOOP THROUGH THE HASH MAP/CONTACTS.TXT TO FIND IF THE NAME HASH MAP
                if(choice.equals(person.getKey().toLowerCase().trim())){ // CHECK IF IT IS EQUAL TO ANY NAMES IN THE LIST ALSO LOWER CASED
                    numberToDelete = person.getValue(); // RE ASSIGN THE VALUE OF THE NUMBER TO THAT "PERSON"(KEY) TO THE "PERSON"'S NUMBER(VALUE)
                }
            }
            if (sc.yesNo("You are deleting " + firstLastCap(choice) + " with number " + numberToDelete + " are you sure?")) {
                allContacts.remove(formatName(firstName, lastName));
                FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
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