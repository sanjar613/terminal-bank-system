import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IndividualAccount extends Account {
    private String branch;

    public IndividualAccount(String accountNumber, BigDecimal balance, LocalDateTime createdAt, User owner, String branch) {
        super(accountNumber, balance, createdAt, owner);
        this.branch = branch;
    }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    @Override
    public String toString() {
        return super.toString() + " Branch: " + branch + " (Individual)";
    }
}