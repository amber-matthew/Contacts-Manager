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


}
