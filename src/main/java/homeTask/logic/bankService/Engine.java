package homeTask.logic.bankService;

import homeTask.logic.dbObjects.AccountDAO;
import homeTask.logic.dbObjects.AccountDAOImpl;
import homeTask.logic.dbObjects.CustomerDAO;
import homeTask.logic.dbObjects.CustomerDAOImpl;
import homeTask.tables.Account;

import java.util.List;

public class Engine {

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private AccountDAO accountDAO = new AccountDAOImpl();

    public void addCustomer(String customerName) {
        customerDAO.addCustomer(customerName);
    }

    public void addAccount(String customerName, double amount, String rate) {
        customerDAO.addAccount(customerName, amount, rate);
    }

    public void replenishAccount(long number, double amount, String rate) {
        accountDAO.replenish(number, amount, rate);
    }

    public void transferFromOneAccountToAnother(long numberFrom, long numberTo, double amount) {
        accountDAO.transfer(numberFrom, numberTo, amount);
    }

    public void currencyConversionWithinSameUser(String customerName, String rate) {
        List<Account> accountList = customerDAO.getAccountsList(customerName);
        for (Account account : accountList) {
            double value = accountDAO.valueOfConvertRate(account.getRate(), rate);
            accountDAO.convertRareInAccount(account, account.getAmount(), value, rate);
        }
    }

    public void getTotalBalanceOfOneUserInUAH(String customerName) {
        List<Account> accountList = customerDAO.getAccountsList(customerName);
        accountDAO.getTotalBalanceInUAH(accountList);
    }
}
