public interface AccountManagerInterface {
    boolean createAccount();
    boolean login();
    void logout();
    String getCurrentUser();
}
