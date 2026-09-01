import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
    private List<User> users = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();

    public Bank() {
        // Создаем дефолтного админа, чтобы можно было войти в админку
        User admin = new User("Admin", "Super", "Adminovich", "admin", "admin123");
        admin.setRole(User.Role.ADMIN);
        users.add(admin);
    }

    public void registerUser(String firstName, String lastName, String middleName, String login, String password) {
        User newUser = new User(firstName, lastName, middleName, login, password);
        users.add(newUser);
        System.out.println("Registration successful!");
    }

    public User login(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return user;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }

    public void createIndividualAccount(User user, String branch) {
        String accountNumber = generateAccountNumber();
        IndividualAccount acc = new IndividualAccount(accountNumber, BigDecimal.ZERO, LocalDateTime.now(), user, branch);
        accounts.add(acc);
        user.getAccounts().add(acc);
        System.out.println("Individual account created: " + accountNumber);
    }

    public void createJuridicalAccount(User user, String company) {
        String accountNumber = generateAccountNumber();
        JuridicalAccount acc = new JuridicalAccount(accountNumber, BigDecimal.ZERO, LocalDateTime.now(), user, company);
        accounts.add(acc);
        user.getAccounts().add(acc);
        System.out.println("Juridical account created: " + accountNumber);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account findAccountByNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public void deleteAccount(String accountNumber) {
        Account acc = findAccountByNumber(accountNumber);
        if (acc != null) {
            accounts.remove(acc);
            acc.getOwner().getAccounts().remove(acc);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String senderAccNumber, String receiverAccNumber, double amount) {
        Account sender = findAccountByNumber(senderAccNumber);
        Account receiver = findAccountByNumber(receiverAccNumber);

        if (sender != null && receiver != null) {
            if (sender.withdraw(amount)) {
                receiver.deposit(amount);
                System.out.println("Transfer successful!");
            }
        } else {
            System.out.println("Transfer failed. Check account numbers.");
        }
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}