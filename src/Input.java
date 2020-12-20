import java.util.Scanner;

public class Input {

    private Scanner sc;

    public Input(){
        this.sc = new Scanner (System.in);
    }

    public String getString(String prompt){
        System.out.println(prompt);
        return this.sc.nextLine();
    }

    public int getNumber(String prompt, int min, int max){
        System.out.println(prompt);
        int num;
        String choice = this.sc.next();
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


}
