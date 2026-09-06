import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JuridicalAccount extends Account {
    private String company;

    public JuridicalAccount(String accountNumber, BigDecimal balance, LocalDateTime createdAt, User owner, String company) {
        super(accountNumber, balance, createdAt, owner);
        this.company = company;
    }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
}