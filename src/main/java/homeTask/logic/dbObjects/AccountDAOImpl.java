package homeTask.logic.dbObjects;

import homeTask.tables.Account;
import homeTask.tables.Transaction;

import javax.persistence.*;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("Bank");
    private EntityManager manager = factory.createEntityManager();

    @Override
    public void replenish(long number, double amount, String rate) {
        Account account;
        double value;
        if ((account = getAccountByNumber(number)) != null) {

            if ((value = valueOfConvertRate(rate, account.getRate())) != 0) {
                manager.getTransaction().begin();
                try {
                    changeAmount(account, amount * value);
                    manager.merge(account);
                } catch (Exception e) {
                    manager.getTransaction().rollback();
                    System.out.println("Account not replenish");
                }
                manager.clear();
            }
        }
    }

    @Override
    public void transfer(long numberFrom, long numberTo, double amount) {
        Account from, to;
        if ((from = getAccountByNumber(numberFrom)) != null && (to = getAccountByNumber(numberTo)) != null) {
            if (from.getAmount() >= amount) {
                double value = valueOfConvertRate(from.getRate(), to.getRate());

                manager.getTransaction().begin();
                try {
                    changeAmount(from, -amount);
                    changeAmount(to, amount * value);

                    manager.merge(from);
                    manager.merge(to);
                    manager.persist(new Transaction(numberFrom, numberTo, amount * value, to.getRate()));
                    manager.getTransaction().commit();
                    System.out.println("The transfer was successful");
                } catch (Exception e) {
                    manager.getTransaction().rollback();
                    System.out.println("The transfer was not successful");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Not enough money in the " + numberFrom + " account");
            }
        } else {
            System.out.println("Incorrect value");
        }
        manager.clear();
    }

    private void changeAmount(Account account, double amount) {
        account.setAmount(account.getAmount() + amount);
    }

    @Override
    public void convertRareInAccount(Account account, double newAmount, double value, String rare) {
        account.setAmount(newAmount * value);
        account.setRate(rare);
        manager.getTransaction().begin();
        try {
            manager.merge(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Account not replenish");
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
        manager.clear();
    }

    @Override
    public double valueOfConvertRate(String fromRate, String toRate) {
        double value = 0;
        if (fromRate.equals(toRate)) {
            value = 1;
        } else {
            try {
                TypedQuery<Double> query = manager.createNamedQuery("getValue", Double.class);
                query.setParameter("from", fromRate);
                query.setParameter("to", toRate);
                value = query.getSingleResult();
            } catch (NoResultException e) {
                System.out.println("Incorrect value");
            }
        }
        manager.clear();
        return value;
    }

    @Override
    public Account getAccountByNumber(long number) {
        Account account = null;
        try {
            Query query = manager.createNamedQuery("getAccount", Account.class);
            query.setParameter("number", number);
            account = (Account) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Account not found");
            manager.clear();
        }
        return account;
    }

    @Override
    public void getTotalBalanceInUAH(List<Account> accounts) {
        double total = 0;
        for (Account account : accounts) {
            total += account.getAmount() * valueOfConvertRate(account.getRate(), "UAH");
        }
        System.out.println("Total amount is " + total + " UAH");
    }
}
