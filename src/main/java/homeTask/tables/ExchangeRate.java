package homeTask.tables;

import javax.persistence.*;

@Entity
@Table
@NamedQuery(name = "getValue", query = "SELECT er.value FROM ExchangeRate er WHERE er.fromRate = :from AND er.toRate = :to")
public class ExchangeRate {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String fromRate;

    @Column(nullable = false)
    private String toRate;

    @Column(nullable = false)
    private double value;

    public ExchangeRate() {
    }

    public ExchangeRate(String fromRate, String toRate, double value) {
        this.fromRate = fromRate;
        this.toRate = toRate;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromRate() {
        return fromRate;
    }

    public void setFromRate(String fromRate) {
        this.fromRate = fromRate;
    }

    public String getToRate() {
        return toRate;
    }

    public void setToRate(String to) {
        this.toRate = to;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
