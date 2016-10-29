package homeTask.logic.dbObjects;

import homeTask.tables.Customer;

import java.util.List;

public interface CustomerDAO {
    void addCustomer(String customerName);

    void addAccount(String customerName, double amount, String rate);

    Customer getCustomerByName(String customerName);

    List getAccountsList(String customerName);
}
