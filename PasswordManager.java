import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public class PasswordManager implements PasswordManagerInterface {

    private Map<String, Map<String, String>> storedLogins = new HashMap<>();
    private String currentUser;

    public PasswordManager(String currentUser) {
        this.currentUser = currentUser;
        storedLogins.put(currentUser, new HashMap<>());
    }

    @Override
    public void storePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter website/app name: ");
        String website = scanner.nextLine().toLowerCase();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        if (storedLogins.containsKey(currentUser) && storedLogins.get(currentUser).containsKey(website)) {
            String existingCredentials = storedLogins.get(currentUser).get(website);
            String[] parts = existingCredentials.split(" : ");
            String existingUsername = parts[0];

            if (existingUsername.equals(username)) {
                System.out.println("Username already exists for this website. Go update the login.");
                return;
            }
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        storedLogins.get(currentUser).put(website, username + " : " + password);
        System.out.println("Login stored successfully.");
    }

    @Override
    public void retrievePasswords() {
        if (storedLogins.containsKey(currentUser) && !storedLogins.get(currentUser).isEmpty()) {
            System.out.println("Stored logins for " + currentUser + ":");
            for (Map.Entry<String, String> entry : storedLogins.get(currentUser).entrySet()) {
                System.out.println("Website: " + entry.getKey());
                String[] parts = entry.getValue().split(" : ");
                System.out.println("Username: " + parts[0]);
                System.out.println("Password: " + parts[1]);
            }
        } else {
            System.out.println("No logins found.");
        }
    }

    @Override
    public void updatePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter website/app name: ");
        String website = scanner.nextLine().toLowerCase();

        if (storedLogins.containsKey(currentUser) && storedLogins.get(currentUser).containsKey(website)) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            storedLogins.get(currentUser).put(website, newUsername + " : " + newPassword);
            System.out.println("Login updated successfully.");
        } else {
            System.out.println("No login found for this website.");
        }
    }

    @Override
    public void deleteAllLogins() {
        if (storedLogins.containsKey(currentUser) && !storedLogins.get(currentUser).isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to delete all logins or just one? ");
            System.out.print("Enter 'all' to delete all, 'one' to delete one by one: ");
            String option = scanner.nextLine().toLowerCase();

            if (option.equals("all")) {
                storedLogins.get(currentUser).clear();
                System.out.println("All logins deleted.");
            } else if (option.equals("one")) {
                if (storedLogins.get(currentUser).isEmpty()) {
                    System.out.println("No logins to delete.");
                    return;
                }

                while (!storedLogins.get(currentUser).isEmpty()) {
                    System.out.println("Stored logins for " + currentUser + ":");
                    int index = 1;
                    for (Map.Entry<String, String> entry : storedLogins.get(currentUser).entrySet()) {
                        System.out.println(index++ + ". " + entry.getKey());
                    }

                    System.out.print("Enter the number of the login to delete: ");
                    int choice = -1;

                    while (true) {
                        if (scanner.hasNextInt()) {
                            choice = scanner.nextInt();
                            scanner.nextLine();  // consume leftover newline
                            if (choice >= 1 && choice <= storedLogins.get(currentUser).size()) {
                                break;
                            } else {
                                System.out.print("Number out of range. Try again: ");
                            }
                        } else {
                            System.out.print("Invalid input. Please enter a number: ");
                            scanner.nextLine(); // discard invalid input
                        }
                    }

                    String websiteToDelete = (String) storedLogins.get(currentUser).keySet().toArray()[choice - 1];
                    storedLogins.get(currentUser).remove(websiteToDelete);
                    System.out.println("Login for " + websiteToDelete + " deleted.");
                    break;
                }
            } else {
                System.out.println("Invalid option. Returning to main menu.");
            }
        } else {
            System.out.println("No logins to delete.");
        }
    }

    @Override
    public void generateUsernameAndPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter website/app name: ");
        String website = scanner.nextLine().toLowerCase();
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        String password = generateRandomPassword();

        if (storedLogins.containsKey(currentUser) && storedLogins.get(currentUser).containsKey(website)) {
            System.out.println("Credentials already exist for this website.");
            System.out.print("Do you want to update the existing password? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equals("yes")) {
                System.out.println("Operation canceled. Existing credentials were not changed.");
                return;
            }
        }

        storedLogins.get(currentUser).put(website, username + " : " + password);
        System.out.println("Generated password: " + password);
        System.out.println("Credentials saved for " + website);
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder password = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 12; i++) {
            int index = rand.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private String generateRandomUsername() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder username = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rand.nextInt(characters.length());
            username.append(characters.charAt(index));
        }

        return username.toString();
    }
}
