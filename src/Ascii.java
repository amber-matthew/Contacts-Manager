import java.util.List;

public class Ascii {
    public static final String WHITE = "\033[0;37m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String RESET = "\033[0m";

    public Ascii(){}

    public void art2(){
        System.out.println(WHITE +   "      *********               ***********           ***** ****    --- *****   *********************  ---- ***                *********        **********************    ");
        System.out.println(WHITE +   "    ***** ** *****        ****** ----  ******       *****  ****    -- *****   ********************* -- **** ****           ***** ** *****     **********************    ");
        System.out.println(YELLOW +  "  ***** ----  *****    ****** ----        ******    *****-  ****   -- *****      ---- *****      --- *****   *****        ***** ---- *****       ---- *****             ");
        System.out.println(YELLOW +  "  ***** ---            ****** --        - ******    *****-   ****   - *****       --- *****    ---- *****     *****      ***** ---                --- *****             ");
        System.out.println(BLUE +    "  ***** --             ****** -       --  ******    *****-    ****  - *****        -- *****        ***** ***** *****     ***** --                  -- *****             ");
        System.out.println(BLUE +    "   *****      *****    ******       ----  ******    *****--    ****   *****         - *****       ***** ******* *****     *****      *****          - *****             ");
        System.out.println(PURPLE +  "    ***** ** *****        ******  ---- ******       *****--     ****  *****           *****      *****        -- *****     ***** ** *****             *****             ");
        System.out.println(PURPLE +  "      *********               ***********           *****---     **** *****           *****     *****        ---- *****      *********                *****             ");
        System.out.println("                                                                                                                                                                                  ");
        System.out.println("                                                                                                                                                                                  ");
        System.out.println("                                  ****       ****      ****      ****    **      ****        ******    ******  ******                                                             ");
        System.out.println("                   -------------  **  **   **  **     **  **     ** **   **     **  **      **         **      **   **  -------------                                             ");
        System.out.println("                   -------------  **   ** **   **    **    **    **  **  **    **    **    **   ****   ****    ******   -------------                                             ");
        System.out.println("                   -------------  **    ***    **   **      **   **   ** **   **      **    **    **   **      **   **  -------------                                             ");
        System.out.println("                                  **           **  **        **  **    ****  **        **    ******    ******  **    **                                                           ");
        System.out.println(RESET);
        System.out.println("");


    }

    public static void art1(){
        System.out.println("       ^      ");
        System.out.println("     / •  \\    ");
        System.out.println("    /      \\    ");
        System.out.println("   /  •  •  \\    ");
        System.out.println("  /      •   \\    ");
        System.out.println(" /     •      \\    ");
    }

}
