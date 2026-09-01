import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Account {
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private User owner;

    public Account(String accountNumber, BigDecimal balance, LocalDateTime createdAt, User owner){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.owner = owner;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public void deposit(double amount){
        if(amount > 0){
            this.balance = balance.add(BigDecimal.valueOf(amount));
        } else {
            System.out.println("Amount can not be a negative number");
        }
    }

    public boolean withdraw(double amount){
        if (amount > 0 && balance.compareTo(BigDecimal.valueOf(amount)) >= 0){
            balance = balance.subtract(BigDecimal.valueOf(amount));
            return true;
        } else {
            System.out.println("not enough money");
            return false;
        }
    }

    @Override
    public String toString(){
        return "Account: " + accountNumber + " Balance: " + balance;
    }
}