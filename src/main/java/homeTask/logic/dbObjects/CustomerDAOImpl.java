package homeTask.logic.dbObjects;

import homeTask.tables.Account;
import homeTask.tables.Customer;

import javax.persistence.*;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("Bank");
    private EntityManager manager = factory.createEntityManager();

    @Override
    public void addCustomer(String name) {
        manager.getTransaction().begin();

        if (!name.isEmpty()) {
            try {
                manager.persist(new Customer(name));

                manager.getTransaction().commit();
                System.out.println("Customer added");
            } catch (Exception e) {
                manager.getTransaction().rollback();
                System.out.println("Customer not added");
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect customer value");
        }
        manager.clear();
    }

    @Override
    public void addAccount(String customerName, double amount, String rate) {
        Customer customer;
        Account account = new Account(amount, rate);
        if ((customer = getCustomerByName(customerName)) != null) {
            account.setCustomer(customer);
            customer.addAccount(account);

            manager.getTransaction().begin();
            try {
                manager.persist(account);
                manager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Account not create");
                manager.getTransaction().rollback();
            }
        }
        manager.clear();
    }

    @Override
    public List<Account> getAccountsList(String customerName) {
        List<Account> accountList = null;

        if ((getCustomerByName(customerName)) != null) {
            try {
                Query query = manager.createNamedQuery("getCustomer", Customer.class);
                query.setParameter("name", customerName);
                Customer customer = (Customer) query.getSingleResult();
                accountList = customer.getAccounts();

                System.out.println("The total number of accounts: " + accountList.size());
            } catch (Exception e) {
                System.out.println("Customer " + customerName + " not found");
                e.printStackTrace();
                manager.clear();
            }
        }
        return accountList;
    }

    @Override
    public Customer getCustomerByName(String customerName) {
        Customer customer = null;
        try {
            Query query = manager.createNamedQuery("getCustomer", Customer.class);
            query.setParameter("name", customerName);
            customer = (Customer) query.getSingleResult();

        } catch (NoResultException e) {
            System.out.println("Customer not found");
            manager.clear();
        }
        return customer;
    }
}
