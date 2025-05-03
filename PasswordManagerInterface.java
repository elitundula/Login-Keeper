public interface PasswordManagerInterface {
    void storePassword();
    void retrievePasswords();
    void updatePassword();
    void deleteAllLogins();
    void generateUsernameAndPassword();
}
