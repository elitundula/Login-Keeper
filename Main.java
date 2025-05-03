import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManagerInterface accountManager = new AccountManager();
        PasswordManagerInterface passwordManager = null;

        boolean loggedIn = false;

        while (true) {
            System.out.println("\n====================================");
            System.out.println("  Welcome to the Login Keeper!");
            System.out.println("====================================");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = getValidIntInput(scanner);

            switch (choice) {
                case 1: // Create account
                    accountManager.createAccount();
                    System.out.println("\nThanks for signing up! You can now log in.");

                    // Show welcome back banner before login
                    System.out.println("\n=====================================");
                    System.out.println("  Welcome back to the Login Keeper!");
                    System.out.println("=====================================\n");

                    // Immediately proceed to login
                    if (accountManager.login()) {
                        loggedIn = true;
                        passwordManager = new PasswordManager(accountManager.getCurrentUser());
                        System.out.println("You are now Inside The Website!");
                    } else {
                        System.out.println("Login failed. Returning to main menu.");
                    }
                    break;

                case 2: // Login
                    if (accountManager.login()) {
                        loggedIn = true;
                        passwordManager = new PasswordManager(accountManager.getCurrentUser());
                        System.out.println("You are now Inside The Website!");
                    } else {
                        System.out.println("Returning to main menu.");
                    }
                    break;

                case 3: // Exit
                    System.out.println("Exiting Login Keeper. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // Logged-in session
            while (loggedIn) {
                System.out.println("\n=====================================");
                System.out.println(" Main Menu: Choose an Action");
                System.out.println("=====================================\n");
                System.out.println("1. Store Password");
                System.out.println("2. Retrieve Logins");
                System.out.println("3. Generate Random Password");
                System.out.println("4. Login Updates");
                System.out.println("5. Delete Logins");
                System.out.println("6. Logout");
                System.out.print("Choose an option: ");
                int option = getValidIntInput(scanner);

                switch (option) {
                    case 1:
                        System.out.println("\n====================================");
                        System.out.println("          Store Password");
                        System.out.println("====================================");
                        passwordManager.storePassword();
                        break;

                    case 2:
                        System.out.println("\n====================================");
                        System.out.println("         Retrieve Logins");
                        System.out.println("====================================");
                        passwordManager.retrievePasswords();
                        break;

                    case 3:
                        System.out.println("\n====================================");
                        System.out.println("    Generate Username & Password");
                        System.out.println("====================================");
                        passwordManager.generateUsernameAndPassword();
                        break;

                    case 4:
                        System.out.println("\n====================================");
                        System.out.println("         Update Password");
                        System.out.println("====================================");
                        passwordManager.updatePassword();
                        break;

                    case 5:
                        System.out.println("\n====================================");
                        System.out.println("         Delete All Logins");
                        System.out.println("====================================");
                        passwordManager.deleteAllLogins();
                        break;

                    case 6:
                        accountManager.logout();
                        loggedIn = false;
                        System.out.println("\nChoose to create a new account, log in, or exit below");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static int getValidIntInput(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
