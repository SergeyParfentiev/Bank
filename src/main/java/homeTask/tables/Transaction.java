package homeTask.tables;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long accountNumberFrom;

    @Column(nullable = false)
    private long accountNumberTo;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String rate;

    public Transaction() {
    }

    public Transaction(long accountNumberFrom, long accountNumberTo, double amount, String rate) {
        this.accountNumberFrom = accountNumberFrom;
        this.accountNumberTo = accountNumberTo;
        this.amount = amount;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountNumberFrom() {
        return accountNumberFrom;
    }

    public void setAccountNumberFrom(long accountNumberFrom) {
        this.accountNumberFrom = accountNumberFrom;
    }

    public long getAccountNumberTo() {
        return accountNumberTo;
    }

    public void setAccountNumberTo(long accountNumberTo) {
        this.accountNumberTo = accountNumberTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
