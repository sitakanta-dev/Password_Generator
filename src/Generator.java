import java.util.Scanner;

public class Generator {
    Alphabet alphabet;
    private final Scanner keyboard;

    public Generator(Scanner scanner) {
        this.keyboard = scanner;
    }

    public Generator(boolean includeUpper, boolean includeLower, boolean includeNum, boolean includeSym) {
        this.alphabet = new Alphabet(includeUpper, includeLower, includeNum, includeSym);
        this.keyboard = null; // not needed for generation-only constructor
    }

    public void mainLoop() {
        System.out.println("Welcome to Ziz Password Services :)");
        printMenu();

        String userOption = "-1";
        while (!userOption.equals("4")) {
            userOption = keyboard.next();

            switch (userOption) {
                case "1" -> {
                    requestPassword();
                    printMenu();
                }
                case "2" -> {
                    checkPassword();
                    printMenu();
                }
                case "3" -> {
                    printUsefulInfo();
                    printMenu();
                }
                case "4" -> printQuitMessage();
                default -> {
                    System.out.println("\nInvalid choice. Try again.");
                    printMenu();
                }
            }
        }
    }

    private Password generatePassword(int length) {
        StringBuilder pass = new StringBuilder();
        String pool = alphabet.getAlphabet();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * pool.length());
            pass.append(pool.charAt(index));
        }

        return new Password(pass.toString());
    }

    private void requestPassword() {
        boolean includeUpper = false;
        boolean includeLower = false;
        boolean includeNum = false;
        boolean includeSym = false;
        boolean retry;

        System.out.println("\nHello! Answer the following questions with Yes or No.");

        do {
            retry = false;
            String input;

            System.out.print("Include lowercase letters? ");
            input = keyboard.next();
            if (validateInput(input)) includeLower = input.equalsIgnoreCase("yes");

            System.out.print("Include uppercase letters? ");
            input = keyboard.next();
            if (validateInput(input)) includeUpper = input.equalsIgnoreCase("yes");

            System.out.print("Include numbers? ");
            input = keyboard.next();
            if (validateInput(input)) includeNum = input.equalsIgnoreCase("yes");

            System.out.print("Include symbols? ");
            input = keyboard.next();
            if (validateInput(input)) includeSym = input.equalsIgnoreCase("yes");

            if (!includeUpper && !includeLower && !includeNum && !includeSym) {
                System.out.println("At least one type must be selected. Let's try again.");
                retry = true;
            }
        } while (retry);

        System.out.print("Enter desired password length: ");
        int length = keyboard.nextInt();

        Generator tempGen = new Generator(includeUpper, includeLower, includeNum, includeSym);
        Password generatedPassword = tempGen.generatePassword(length);
        System.err.println("Your generated password: " + generatedPassword);
    }

    private void checkPassword() {
        System.out.print("\nEnter your password: ");
        String input = keyboard.next();
        Password p = new Password(input);
        System.out.println(p.calculateScore());
    }

    private boolean validateInput(String input) {
        if (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
            System.out.println("Invalid input. Please enter Yes or No.");
            return false;
        }
        return true;
    }

    private void printMenu() {
        System.out.println("""
            \nMenu:
            1 - Password Generator
            2 - Password Strength Checker
            3 - Useful Information
            4 - Quit
            Choice:""");
    }

    private void printUsefulInfo() {
        System.out.println("""
            \nPassword Tips:
            - Use at least 8 characters (preferably 12+)
            - Include a mix of upper/lowercase letters, digits, and symbols
            - Avoid dictionary words or repeated characters
            - Don't reuse passwords
            - Avoid personal info (e.g., name, birthdate)
            """);
    }

    private void printQuitMessage() {
        System.out.println("Thank you for using Ziz Password Services. Goodbye!");
    }
}
