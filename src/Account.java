import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Account implements Serializable {
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private User owner;

    public Account(String accountNumber, BigDecimal balance, LocalDateTime createdAt, User owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.owner = owner;
    }

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public User getOwner() { return owner; }

    public void deposit(double amount) {
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
    }

    public boolean withdraw(double amount) {
        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);
        if (this.balance.compareTo(withdrawAmount) >= 0) {
            this.balance = this.balance.subtract(withdrawAmount);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + " | Balance: " + balance;
    }
}