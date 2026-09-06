import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleUI {
    private Bank bank;
    private Scanner input;

    public ConsoleUI(){
        this.bank = new Bank();
        this.input = new Scanner(System.in);
    }

    public void start(){
        while(true) {
            System.out.println("1. Register ");
            System.out.println("2. Login ");
            System.out.println("3. Exit ");
            int choice = readInt();
            switch (choice){
                case 1:{
                    System.out.println("registration started");
                    System.out.print("enter first name: ");
                    String firstName = readName();
                    System.out.print("enter last name: " );
                    String lastName = readName();
                    System.out.print("enter middle name: ");
                    String middleName = readName();
                    System.out.print("enter nick name: ");
                    String nickName  = readUniqueNickName();
                    System.out.print("enter password: ");
                    String password = readPassword();

                    bank.registerUser(firstName, lastName, middleName, nickName, password);

                    User currentUser = bank.login(nickName, password);
                    if(currentUser != null){
                        if (currentUser.getRole() == User.Role.ADMIN ){
                            showAdminMenu(currentUser);
                        } else if (currentUser.getRole() == User.Role.CLIENT){
                            showClientMenu(currentUser);
                        }
                    }
                    break;
                }
                case 2:{
                    System.out.print("enter nick name: ");
                    String nickName = readNotBlank();
                    System.out.print("enter password: ");
                    String password = readNotBlank();
                    User currentUser = bank.login(nickName, password);
                    if(currentUser != null){
                        if (currentUser.getRole() == User.Role.ADMIN ){
                            showAdminMenu(currentUser);
                        } else if (currentUser.getRole() == User.Role.CLIENT){
                            showClientMenu(currentUser);
                        }
                    }
                    break;
                }
                case 3:
                    return;
                default:
                    System.out.println("Warning, please enter valid number");
                    break;
            }
        }
    }

    private void showAdminMenu(User currentUser){
        while (true){
            System.out.println("""
                       ===== Admin Menu =====
                    1. Create Individual Account
                    2. Create Juridical Account
                    3. View All Accounts
                    4. View Account Details
                    5. Update Account Details
                    6. Delete Account
                    7. View Individual Accounts
                    8. View Juridical Accounts
                    9. Logout
                    """);
            int adminChoice = readInt();

            switch (adminChoice) {
                case 1:{
                    System.out.println("enter branch: ");
                    String branch = readNotBlank();
                    bank.createIndividualAccount(currentUser, branch);
                    break;
                }
                case 2:{
                    System.out.println("enter company name: ");
                    String company = readNotBlank();
                    bank.createJuridicalAccount(currentUser, company);
                    break;
                }
                case 3:{
                    System.out.println("All accounts: " + bank.getAccounts());
                    break;
                }
                case 4: {
                    System.out.println("enter account number to see details");
                    String accountNumber = input.nextLine();
                    System.out.println("Account details: " + bank.findAccountByNumber(accountNumber));
                    break;
                }
                case 5: {
                    System.out.println("enter account number: ");
                    String accountNumber = readNotBlank();
                    Account accountToUpdate = bank.findAccountByNumber(accountNumber);
                    if (accountToUpdate instanceof IndividualAccount indAcc) {
                        System.out.print("enter new branch: ");
                        String newBranch = readNotBlank();
                        indAcc.setBranch(newBranch);
                        System.out.println("account branch successfully updated");
                    } else if (accountToUpdate instanceof JuridicalAccount jurAcc) {
                        System.out.print("enter new company name: ");
                        String newCompany = readNotBlank();
                        jurAcc.setCompany(newCompany);
                        System.out.println("account company name successfully updated");
                    }
                    // Сохраняем после обновления, чтобы изменения не пропали!
                    bank.saveData();
                    break;
                }
                case 6:{
                    System.out.println("enter account number to delete");
                    String accountNumber = input.nextLine();
                    bank.deleteAccount(accountNumber);
                    break;
                }
                case 7:{
                    System.out.println("all Individual accounts: " + bank.getIndividualAccounts());
                    break; // Исправлено (добавлен break)
                }
                case 8:{
                    System.out.println("all Juridical accounts: " + bank.getJuridicalAccounts());
                    break; // Исправлено (добавлен break)
                }
                case 9:{
                    return;
                }
                default:
                    System.out.println("Warning please enter valid number");
            }
        }
    }

    private void showClientMenu(User currentUser) {
        while (true) {
            System.out.println("""
                    ===== User Menu ===== 
                    1. Create account 
                    2. See all accounts
                    3. Get Profile information
                    4. Transfer
                    5. Deposit
                    6. Withdraw
                    7. Delete account
                    8. Logout
                    """);
            int clientChoice = readInt();
            switch (clientChoice){
                case 1: {
                    if (currentUser.getAccounts().isEmpty()) {
                        System.out.println("1. Individual account");
                        System.out.println("2. Juridical account");
                        int typeChoice = readInt();
                        if (typeChoice == 1) {
                            System.out.println("enter branch: ");
                            String branch = readNotBlank();
                            bank.createIndividualAccount(currentUser, branch);
                        } else if (typeChoice == 2) {
                            System.out.println("enter company name: ");
                            String company = readNotBlank();
                            bank.createJuridicalAccount(currentUser, company);
                        } else {
                            System.out.println("Warning! enter valid number");
                        }
                    } else {
                        Account firstAccount = currentUser.getAccounts().get(0);
                        if (firstAccount instanceof IndividualAccount){
                            System.out.println("enter a new branch");
                            String branch = readNotBlank();
                            bank.createIndividualAccount(currentUser, branch);
                        } else if (firstAccount instanceof JuridicalAccount) {
                            System.out.println("enter company name ");
                            String company = readNotBlank();
                            bank.createJuridicalAccount(currentUser, company);
                        }
                    }
                    break;
                }
                case 2:
                    System.out.println("your accounts: " + currentUser.getAccounts());
                    break;
                case 3:
                    System.out.println("First Name: " + currentUser.getFirstName());
                    System.out.println("Last Name: " + currentUser.getLastName());
                    System.out.println("Nick Name: " + currentUser.getNickName());
                    System.out.println("Password: " + currentUser.getPassword());
                    break;
                case 4: {
                    if (currentUser.getAccounts().isEmpty()){
                        System.out.println("you have no account yet");
                        continue;
                    }
                    System.out.println("choose your account to send money from: ");
                    for (int i = 0; i < currentUser.getAccounts().size(); i++){
                        System.out.println((i + 1) + ". " + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1 || accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account senderAccount = currentUser.getAccounts().get(accountChoice - 1);
                    if (senderAccount.getBalance().compareTo(BigDecimal.ZERO) <= 0){
                        System.out.println("Warning, not enough money, deposit your balance");
                        continue;
                    }
                    System.out.print("enter receive account number: ");
                    String reciever;
                    while (true) {
                        reciever = readNotBlank();
                        if (senderAccount.getAccountNumber().equals(reciever)) {
                            System.out.println("you can not send money to yourself");
                            continue;
                        }
                        Account recieverAccount = bank.findAccountByNumber(reciever);
                        if (recieverAccount == null) {
                            continue;
                        }
                        break;
                    }
                    while (true) {
                        System.out.println("enter amount : ");
                        double amount = readDouble();
                        if (amount < 1000 || amount > 1000000) {
                            System.out.println("warning, amount must be between 1000 and 1000000");
                            continue;
                        }
                        String sender = senderAccount.getAccountNumber();
                        bank.transfer(sender, reciever, amount);
                        break;
                    }
                    break;
                }
                case 5: {
                    if (currentUser.getAccounts().isEmpty()) {
                        System.out.println("you have no accounts yet ");
                        continue;
                    }
                    System.out.println("choose account to deposit: ");
                    for (int i = 0; i < currentUser.getAccounts().size(); i++) {
                        System.out.println((i + 1) + ". " + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1 || accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account accountDeposit = currentUser.getAccounts().get(accountChoice - 1);
                    System.out.print("enter amount of money in sum : ");
                    Double amount = readDouble();
                    if (amount < 1000 || amount > 1000000) {
                        System.out.println("warning, amount must be between 1000 and 1000000");
                        continue;
                    }
                    accountDeposit.deposit(amount);
                    System.out.println("balance updated successfully");
                    bank.saveData(); // Сохраняем после депозита
                    break;
                }
                case 6:{
                    if (currentUser.getAccounts().isEmpty()) {
                        System.out.println("you have no accounts yet ");
                        continue;
                    }
                    System.out.println("choose account to withdraw: ");
                    for (int i = 0; i < currentUser.getAccounts().size(); i++) {
                        System.out.println((i + 1) + ". " + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1 || accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account accountWithdraw = currentUser.getAccounts().get(accountChoice - 1);
                    System.out.println("enter amount of money in sum: ");
                    Double amount = readDouble();
                    if (amount < 1000 || amount > 1000000) {
                        System.out.println("warning, amount must be between 1000 and 1000000");
                        continue;
                    }
                    if(accountWithdraw.withdraw(amount)) {
                        System.out.println("money withdraw finished successfully");
                        bank.saveData(); // Сохраняем после снятия
                    } else {
                        System.out.println("not enough money on your balance");
                    }
                    break;
                }
                case 7:{
                    if (currentUser.getAccounts().isEmpty()){
                        System.out.println("you have no account yet");
                        continue;
                    }
                    System.out.println("choose account to delete: ");
                    for (int i=0; i<currentUser.getAccounts().size(); i++){
                        System.out.println((i + 1) + ". " + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1 || accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account accountDelete = currentUser.getAccounts().get(accountChoice - 1);
                    String account = accountDelete.getAccountNumber();
                    bank.deleteAccount(account);
                    break;
                }
                case 8:
                    return;
                default:
                    System.out.println("Warning please enter valid number");
                    break;
            }
        }
    }

    private int readInt(){
        while (true){
            try {
                String line = input.nextLine();
                int number = Integer.parseInt(line);
                return number;
            } catch (NumberFormatException e){
                System.out.println("Warning! please enter valid number");
            }
        }
    }

    private double readDouble(){
        while (true){
            try {
                String line = input.nextLine();
                double number = Double.parseDouble(line);
                return number;
            } catch (NumberFormatException e){
                System.out.println("Warning! please enter valid number");
            }
        }
    }

    private String readName(){
        while (true){
            String line = input.nextLine();
            if (line.matches("[a-zA-Z]+")){
                return line;
            } else {
                System.out.println("enter letters only");
            }
        }
    }

    public String readPassword(){
        while (true){
            String line = input.nextLine();
            if(line.length() >= 8 && line.length() <= 16){
                return line;
            } else {
                System.out.println("password length should be between 8 and 16");
            }
        }
    }

    public String readUniqueNickName(){
        while (true){
            String nickName = readNotBlank();
            if (!nickName.matches("[a-zA-Z0-9_]{3,20}")){
                System.out.println("enter valid nickname");
                continue;
            }
            if (bank.isNickNameTaken(nickName)){
                System.out.println("this nickname is already taken, enter another one");
            } else {
                return nickName;
            }
        }
    }

    public String readNotBlank(){
        while (true){
            String line = input.nextLine();
            if(line.isBlank()){
                System.out.println("can not be empty");
            } else {
                return line;
            }
        }
    }
}