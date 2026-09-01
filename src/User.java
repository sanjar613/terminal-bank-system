import java.util.ArrayList;
import java.util.List;

public class User {
    public enum Role {
        ADMIN, CLIENT
    }

    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String password;
    private Role role;
    private List<Account> accounts;

    public User(String firstName, String lastName, String middleName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.role = Role.CLIENT; // По умолчанию все новые - клиенты
        this.accounts = new ArrayList<>();
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public List<Account> getAccounts() { return accounts; }
}