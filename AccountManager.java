import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccountManager implements AccountManagerInterface {
    private Map<String, String> accounts = new HashMap<>();
    private String currentUser;

    @Override
    public boolean createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        // Check if username already exists
        if (accounts.containsKey(username)) {
            System.out.println("Username already exists! Please choose a different username.");
            return false;
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        accounts.put(username, password);  // Store username and password
        this.currentUser = username;
        System.out.println("Account created successfully!");
        return true;
    }

    @Override
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // If username doesn't exist, ask to create an account
        if (!accounts.containsKey(username)) {
            System.out.println("\nIt looks like you do not have an account.");
            System.out.print("Would you like to create one? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                boolean created = createAccount();
                if (created) {
                    System.out.println("\n====================================");
                    System.out.println("  Welcome back to the Login Keeper!");
                    System.out.println("====================================");
                    return login(); // Start login process again after creation
                } else {
                    System.out.println("Returning to main menu...");
                    return false;
                }
            } else {
                System.out.println("Unsuccessful Login");
                return false;
            }
        }

        // Password verification with up to 3 attempts
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (accounts.get(username).equals(password)) {
                this.currentUser = username;
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid password.");
                attempts++;
                if (attempts == 3) {
                    System.out.println("You have failed to log in 3 times.");
                    System.out.print("Would you like to reset your password? (yes/no): ");
                    String resetChoice = scanner.nextLine();
                    if (resetChoice.equalsIgnoreCase("yes")) {
                        resetPassword(username);
                    }
                }
            }
        }
        return false;
    }

    private void resetPassword(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        accounts.put(username, newPassword);  // Update the password
        System.out.println("Password has been reset successfully!");
    }

    @Override
    public void logout() {
        System.out.println("You have logged out.");
        this.currentUser = null;
    }

    @Override
    public String getCurrentUser() {
        return this.currentUser;
    }
}

