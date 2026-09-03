import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleUI {
    private Bank bank;
    private Scanner input;

    public ConsoleUI(){
        this.bank = new Bank();
        this.input  = new Scanner(System.in);
    }

    public void start(){
        while(true) {
            System.out.println("1. register ");
            System.out.println("2. login ");
            System.out.println("3. Logout ");
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
                    String login  = readUnique();
                    System.out.print("enter password: ");
                    String password = readPassword();
                    bank.registerUser(firstName,lastName,middleName,login,password);
                    break;
                }
                case 2:{
                    System.out.print("enter nick name: ");
                    String login = input.nextLine();
                    System.out.print("enter password: ");
                    String password = input.nextLine();
                    User currentUser = bank.login(login,password);
                    if(currentUser != null){
                        if (currentUser.getRole() == User.Role.ADMIN ){
                            showAdminMenu(currentUser);
                        }else if (currentUser.getRole() == User.Role.CLIENT){
                            showClientMenu(currentUser);
                        }
                    }
                    break;
                }
                case 3:
                    return;
                default:
                    System.out.println("Warning , please enter valid number");
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
                    7. Logout
                    """);
            int adminChoice = readInt();

            switch (adminChoice) {
                case 1:{
                    System.out.println("enter branch: ");
                    String branch = input.nextLine();
                    bank.createIndividualAccount(currentUser, branch);
                    break;
                }
                case 2:{
                    System.out.println("enter company name: ");
                    String company = input.nextLine();
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
                    System.out.println(" Accont details: " + bank.findAccountByNumber(accountNumber));
                    break;
                }
                case 5: {
                    System.out.println("enter account number: ");
                    String accountNumber = input.nextLine();
                    Account accountToUpdate = bank.findAccountByNumber(accountNumber);
                    if (accountToUpdate == null) {
                        System.out.println("please enter valid account number");
                        break;
                    }
                    if (accountToUpdate instanceof IndividualAccount indAcc) {
                        System.out.print("enter new branch: ");
                        String newBranch = input.nextLine();
                        indAcc.setBranch(newBranch);
                        System.out.println(" account branch successfully updated");
                    } else if (accountToUpdate instanceof JuridicalAccount jurAcc) {
                        System.out.print("enter new company name: ");
                        String newCompany = input.nextLine();
                        jurAcc.setCompany(newCompany);
                        System.out.println("account company name successfully updated");
                    }
                    break;
                }
                case 6:{
                    System.out.println("enter account number to delete");
                    String accountNumber = input.nextLine();
                    bank.deleteAccount(accountNumber); // Проверка баланса теперь под капотом Bank
                    break;
                }
                case 7:{
                    return;
                }
                default:
                    System.out.println("Warning please enter valid number");
            }
        }
    }


    private void showClientMenu(User currentUser) {
        while (true) {
            System.out.println( """
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
                        System.out.println("1.individual account");
                        System.out.println("2.Juridical account");
                        int typeChoice = readInt();
                        if (typeChoice == 1) {
                            System.out.println("enter branch: ");
                            String branch = input.nextLine();
                            bank.createIndividualAccount(currentUser, branch);
                        } else if (typeChoice == 2) {
                            System.out.println("enter company name: ");
                            String company = input.nextLine();
                            bank.createJuridicalAccount(currentUser, company);
                        }else {
                            System.out.println("Warning! enter valid number");
                        }
                    }else {
                        Account firstAccount = currentUser.getAccounts().get(0);
                        if (firstAccount instanceof IndividualAccount){
                            System.out.println("enter a new branch");
                            String branch = input.nextLine();
                            bank.createIndividualAccount(currentUser, branch);
                        } else if (firstAccount instanceof JuridicalAccount) {
                            System.out.println("enter company name ");
                            String company = input.nextLine();
                            bank.createJuridicalAccount(currentUser,company);
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
                    System.out.println("Nick Name: "+ currentUser.getLogin());
                    System.out.println("Password: " +currentUser.getPassword());
                    break;
                case 4: {
                    if (currentUser.getAccounts().isEmpty()){
                        System.out.println("you have no account yet");
                        continue;
                    }
                    System.out.println("choose your account to send money from: ");
                    for (int i = 0;i<currentUser.getAccounts().size();i++){
                        System.out.println(i + 1 + " ." + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1|| accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account senderAccount = currentUser.getAccounts().get(accountChoice - 1);
                    if (senderAccount.getBalance().compareTo(BigDecimal.ZERO)<=0){
                        System.out.println("Warning, not enough money, deposit your balance");
                        continue;
                    }
                    System.out.print("enter recieve  account number: ");
                    String reciever = input.nextLine();
                    System.out.println("enter amount : ");
                    double amount = readDouble();
                    if (amount<1000 || amount >1000000){
                        System.out.println("warning, amount must be between 1000 and 1000000");
                        continue;
                    }
                    String sender = senderAccount.getAccountNumber();
                    bank.transfer(sender, reciever, amount);
                    break;
                }
                case 5: {
                    if (currentUser.getAccounts().isEmpty()) {
                        System.out.println("you have no accounts yet ");
                        continue;
                    }
                    System.out.println("choose account to deposit: ");
                    for (int i = 0; i < currentUser.getAccounts().size(); i++) {
                        System.out.println(i + 1 + "." + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1|| accountChoice > currentUser.getAccounts().size()){
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
                    break;
                }
                case 6:{
                    if (currentUser.getAccounts().isEmpty()) {
                        System.out.println("you have no accounts yet ");
                        continue;
                    }
                    System.out.println("choose account to withdraw: ");
                    for (int i = 0; i < currentUser.getAccounts().size(); i++) {
                        System.out.println(i + 1 + "." + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1|| accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account accountWithdraw = currentUser.getAccounts().get(accountChoice - 1);
                    System.out.println("enter amount of money in sum: ");
                    Double amount  = readDouble();
                    if (amount < 1000 || amount > 1000000) {
                        System.out.println("warning, amount must be between 1000 and 1000000");
                        continue;
                    }
                    if(accountWithdraw.withdraw(amount)) {
                        System.out.println("money withdraw finished successfully");
                    }else {
                        System.out.println("not enough money on your balance");
                    }
                    break;
                }
                case 7:{
                    if (currentUser .getAccounts().isEmpty()){
                        System.out.println("you have no account yet");
                        continue;
                    }
                    System.out.println("choose account to delete: ");
                    for (int i=0;i<currentUser.getAccounts().size();i++){
                        System.out.println(i + 1 + "." + currentUser.getAccounts().get(i));
                    }
                    int accountChoice = readInt();
                    if (accountChoice < 1|| accountChoice > currentUser.getAccounts().size()){
                        System.out.println("warning, invalid account choice");
                        continue;
                    }
                    Account accountDelete = currentUser.getAccounts().get(accountChoice - 1);
                    String account = accountDelete.getAccountNumber();
                    bank.deleteAccount(account); // Проверка баланса теперь под капотом Bank
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
                String line  = input.nextLine();
                int number = Integer.parseInt(line);
                return number;
            }catch (NumberFormatException e){
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
            }catch (NumberFormatException e){
                System.out.println("Warning! please enter valid number");
            }
        }
    }

    private String readName(){
        while (true){
            String line = input.nextLine();
            if (line.matches("[a-zA-Z]+")){
                return line;
            }else{
                System.out.println("enter letters only");
            }
        }
    }

    public String readPassword(){
        while (true){
            String line = input.nextLine();
            if(line.length()>= 8 && line.length()<=16){
                return  line;
            }else {
                System.out.println("password length should be between 8 and 16");
            }
        }
    }

    public String readUnique(){
        while (true){
            String login = input.nextLine();
            if (bank.isLoginTaken(login)){
                System.out.println("this nickname is already taken, enter another one");
            }else{
                return login;
            }
        }
    }
}