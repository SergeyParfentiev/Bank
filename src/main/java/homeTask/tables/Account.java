package homeTask.tables;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(name = "getAccount", query = "SELECT a FROM Account a WHERE a.number = :number"),
        @NamedQuery(name = "replenish", query = "UPDATE Account a SET a.amount = amount + :amount WHERE a.number = :number"),
        @NamedQuery(name = "customerAccounts", query = "SELECT a FROM Account a WHERE a.customer = :customer_id")
})
public class Account {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, unique = true)
    private long number;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String rate;

    public Account() {
    }

    public Account(double amount, String rate) {
        this.amount = amount;
        this.rate = rate;
        number = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getNumber() {
        return number;
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
