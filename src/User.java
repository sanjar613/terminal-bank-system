import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String password;
    private LocalDateTime createdAt;
    private List<Account> accounts;
    private Role role;

    public User(String firstName, String lastName, String middleName, String login, String password, LocalDateTime createdAt, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.accounts = new ArrayList<>();
    }

    public Role getRole() {
        return role;
    }

    public enum Role {
        CLIENT, ADMIN
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void addAccount(Account account){
        accounts.add(account);
    }
    public void removeAccount(Account account){
        accounts.remove(account);
    }
    public List<Account> getAccounts(){
        return accounts;
    }
}