import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    public enum Role { ADMIN, CLIENT }

    private String firstName;
    private String lastName;
    private String middleName;
    private String nickName;
    private String password;
    private LocalDateTime createdAt;
    private Role role;
    private List<Account> accounts;

    public User(String firstName, String lastName, String middleName, String nickName, String password, LocalDateTime createdAt, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.nickName = nickName;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.accounts = new ArrayList<>();
    }

    public String getNickName() { return nickName; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public List<Account> getAccounts() { return accounts; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }
}