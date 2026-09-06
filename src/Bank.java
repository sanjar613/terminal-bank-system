import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<User> users;
    private List<Account> accounts;

    public Bank(){
        loadData();
    }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }

    public void registerUser(String firstName, String lastName, String middleName, String nickName, String password){
        LocalDateTime createdAt = LocalDateTime.now();
        User user = new User(firstName, lastName, middleName, nickName, password, createdAt, User.Role.CLIENT);
        users.add(user);
        saveData();
    }

    public User login(String nickName, String password){
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            if (currentUser.getNickName().equals(nickName) && currentUser.getPassword().equals(password)) {
                return currentUser;
            }
        }
        System.out.println("password or nick name is wrong");
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
        saveData();
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
        saveData();
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
        } else if(amount > 0 && sender.withdraw(amount)){
            reciever.deposit(amount);
            System.out.println("transfer completed succesfully");
        } else {
            System.out.println("transfer failed");
        }
        saveData();
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
        saveData();
    }

    public boolean isNickNameTaken(String nickName){
        for (int i = 0; i < users.size(); i++){
            if(users.get(i).getNickName().equals(nickName)){
                return true;
            }
        }
        return false;
    }

    public void saveData() {
        try {
            FileOutputStream openFile = new FileOutputStream("bank_data.txt");
            ObjectOutputStream insertFiles = new ObjectOutputStream(openFile);
            insertFiles.writeObject(accounts);
            insertFiles.writeObject(users);
            insertFiles.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(){
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        User admin = new User("admin","adminov","adminovich","admin","12345",createdAt,User.Role.ADMIN);
        User user = new User("User","Userov","Userovich","user","12345678",createdAt, User.Role.CLIENT);
        users.add(admin);
        users.add(user);

        try {
            FileInputStream openFile = new FileInputStream("bank_data.txt");
            ObjectInputStream insertFiles = new ObjectInputStream(openFile);
            this.accounts = (List<Account>) insertFiles.readObject();
            this.users = (List<User>) insertFiles.readObject();
            insertFiles.close();
        } catch (IOException | ClassNotFoundException e){
        }
    }

    public List<IndividualAccount> getIndividualAccounts(){
        ArrayList<IndividualAccount> list = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++){
            Account account = accounts.get(i);
            if (account instanceof IndividualAccount){
                list.add((IndividualAccount) account);
            }
        }
        return list;
    }

    public List<JuridicalAccount> getJuridicalAccounts(){
        ArrayList<JuridicalAccount> list = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++){
            Account account = accounts.get(i);
            if (account instanceof JuridicalAccount){
                list.add((JuridicalAccount) account);
            }
        }
        return list;
    }
}