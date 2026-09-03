import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<User> users;
    private List<Account> accounts;
    private List<String> branches;

    public Bank(){
        this.users = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        User user = new User("admin","adminov","adminovich","admin","12345",createdAt,User.Role.ADMIN);
        users.add(user);
        this.accounts = new ArrayList<>();
    }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }

    public void registerUser(String firstName, String lastName, String middleName, String login, String password){
        LocalDateTime createdAt = LocalDateTime.now();
        User user = new User(firstName,lastName,middleName,login,password,createdAt, User.Role.CLIENT);
        users.add(user);
    }

    public User login(String login, String password){
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            if (currentUser.getLogin().equals(login) && currentUser.getPassword().equals(password)) {
                return currentUser;
            }
        }
        System.out.println("password or login is wrong");
        return null;
    }

    public void createIndividualAccount(User owner, String branch){
        String accountNumber = "";
        for (int i = 0; i < 16; i++){
            accountNumber += (int)(Math.random() * 10);
        }
        BigDecimal balance = BigDecimal.ZERO;
        IndividualAccount account = new IndividualAccount(accountNumber, balance, LocalDateTime.now(), owner, branch);
        accounts.add(account);
        owner.addAccount(account);
    }

    public void createJuridicalAccount(User owner, String company){
        String accountNumber = "";
        for (int i = 0; i < 16; i++){
            accountNumber += (int)(Math.random() * 10);
        }
        BigDecimal balance = BigDecimal.ZERO;
        JuridicalAccount account = new JuridicalAccount(accountNumber, balance, LocalDateTime.now(), owner, company);
        accounts.add(account);
        owner.addAccount(account);
    }

    public Account findAccountByNumber(String accountNumber){
        for (int i = 0; i < accounts.size(); i++) {
            Account currentAccount = accounts.get(i);
            if (accountNumber.equals(currentAccount.getAccountNumber())) {
                return currentAccount;
            }
        }
        System.out.println("card Number is not found");
        return null;
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount){
        Account sender = findAccountByNumber(fromAccountNumber);
        Account reciever = findAccountByNumber(toAccountNumber);
        if(sender == null || reciever == null){
            return;
        }else if(amount > 0 && sender.withdraw(amount)){
            reciever.deposit(amount);
            System.out.println("transfer completed succesfully");
        }else {
            System.out.println("transfer failed");
        }
    }

    public void deleteAccount(String accountNumber){
        Account account = findAccountByNumber(accountNumber);
        if(account == null){
            return;
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            System.out.println("account can be deleted when balance is 0");
            return;
        }

        User owner = account.getOwner();
        owner.removeAccount(account);
        accounts.remove(account);
        System.out.println("Account deleted");
    }

    public boolean isLoginTaken(String user){
        for (int i = 0; i < users.size(); i++){
            if(users.get(i).getLogin().equals(user)){
                return true;
            }
        }
        return false;
    }
}