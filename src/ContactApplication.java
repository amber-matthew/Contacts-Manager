import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ContactApplication {

    public static void main(String[] args) throws IOException {

        Input sc = new Input();
        Ascii art = new Ascii();

        boolean notExit = true;
        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
        List<String> allContacts =  contactReader.getFileLines();

        art.art2();
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
        System.out.printf("\033[0;34m|  %-20s|  %-20s|%n",name,number);
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

        FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
        FileReader logWriter = new FileReader("src", "contacts.log", "contacts.log");

        if (allContacts.contains(newContactName)) {
            boolean validInput = true;
            if (sc.yesNo("There is already a contact named " + newContactName + " would you like to override?")) {

                int personIndex = allContacts.indexOf(newContactName);
                String oldNumber = allContacts.get(personIndex + 1);
                String newContactNumber = sc.getString("What is " + newContactName + "'s new contact number?");

                try{
                    Integer.parseInt(newContactNumber);
                } catch (Exception e){
                    logWriter.writeToLog(newContactNumber+" is not a valid number- "+e.getMessage());
                    validInput = false;
                }

                if(validInput){
                    String formattedContactNumber = formatPhoneNum(newContactNumber);
                    allContacts.set(personIndex + 1, formattedContactNumber);
                    contactReader.updateLog(allContacts, oldNumber, formattedContactNumber);
                }



            }else{
                System.out.println("Re-enter information");
                addNewContact(sc, allContacts);
            }

        }else {
            boolean isValid = true;

            String newContactNumber = sc.getString("What is this person's number?");

            try{
                Integer.parseInt(newContactNumber);
            } catch (Exception e){
                logWriter.writeToLog(newContactNumber+" is not a valid number for contact " +newContactName + " "+e.getMessage());
                isValid = false;
            }


            if(isValid) {
                String formattedNewNumber = formatPhoneNum(newContactNumber);
                Contact person1 = new Contact(newContactName, formattedNewNumber);
                contactReader.writeToLog(person1);
                allContacts.add(person1.getName());
                allContacts.add(person1.getNumber());
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




    public static void viewAllContacts(List<String> allContacts) throws IOException {

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

        for(int i = 0; i < allContacts.size(); i+=2){
            format(allContacts.get(i),allContacts.get(i+1));
        }
        for(int i = 0; i < 46; i++){
            System.out.print("\033[0;33m#\033[0;38m");
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
        boolean validChoice = true;
        System.out.println("Current contact list: ");
        viewAllContacts(allContacts);
        System.out.println();
        String choice = sc.getString("What contact do you want to delete?");

        //TODO: TRY TO IMPLEMENT A TRY CATCH FOR A NUMBER THAT DOES NOT EXIST
        int personIndex = allContacts.indexOf(choice);


        try{
            allContacts.get(personIndex);

        } catch (Exception e){
            FileReader logWriter = new FileReader("src", "contacts.log", "contacts.log");
            logWriter.writeToLog("Unable to find contact name: "+choice+"- "+e.getMessage());
            validChoice = false;
        }


        if(validChoice){
            String numberToDelete = allContacts.get(personIndex + 1);
            if (sc.yesNo("You are deleting " + choice + " with number " + numberToDelete + " are you sure?")) {
                allContacts.remove(personIndex);
                allContacts.remove(personIndex);
                FileReader contactReader = new FileReader("src", "contacts.txt", "contacts.txt");
                contactReader.overwriteLog(allContacts, choice, numberToDelete);
            }
        }
    }

}