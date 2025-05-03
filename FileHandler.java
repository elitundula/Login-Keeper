import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class FileHandler {
    public static void saveToFile(String username, Map<String, String> logins) {
        try (PrintWriter writer = new PrintWriter(username + "_logins.txt")) {
            for (Map.Entry<String, String> entry : logins.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving logins: " + e.getMessage());
        }
    }

    public static Map<String, String> loadFromFile(String username) {
        Map<String, String> logins = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(username + "_logins.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(":", 2);
                if (parts.length == 2) {
                    logins.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading logins: " + e.getMessage());
        }
        return logins;
    }
}
