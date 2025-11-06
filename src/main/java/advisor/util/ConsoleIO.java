package advisor.util;

import java.util.Scanner;

public class ConsoleIO {
    private final Scanner sc = new Scanner(System.in);

    public String ask(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public void println(String s) { System.out.println(s); }
    public void warn(String s) { System.out.println("[!] " + s); }
    public void ok(String s) { System.out.println("[OK] " + s); }

    public void pause() {
        System.out.print("\nPress Enter to return to the menu...");
        sc.nextLine();
    }

    public boolean yesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = sc.nextLine().trim().toLowerCase();
            if (v.equals("y") || v.equals("yes")) return true;
            if (v.equals("n") || v.equals("no") || v.isBlank()) return false;
            if (v.equals("b") || v.equals("back")) throw new BackToMenu();
            System.out.println("Please type y or n (or B to go back).");
        }
    }
}
