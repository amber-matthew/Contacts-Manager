import java.util.Scanner;

public class Input {

    private Scanner sc;

    public Input(){

        this.sc = new Scanner (System.in);
        sc.useDelimiter("\\n");
    }

    public String getString(String prompt){
        System.out.println(prompt);

        return this.sc.nextLine();
    }



    public int getNumber(String prompt, int min, int max){
        System.out.println(prompt);
        int num;
        String choice = this.sc.nextLine();
        try{
            num = Integer.parseInt(choice);
            if(num > max || num < min){
               return  getNumber("Input not recognized, please try again", min, max);
            }
            return num;
        }catch (Exception e){
            e.printStackTrace();
        }
        return getNumber("Invalid input, please try again", min, max);
    }

    public boolean yesNo(String prompt){
       String response =  getString(prompt);
       return response.equalsIgnoreCase("yes");

    }


}
