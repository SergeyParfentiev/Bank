package homeTask.logic.dbObjects;

import homeTask.tables.Account;

import java.util.List;

public interface AccountDAO {

    void replenish(long number, double amount, String rate);

    void transfer(long numberFrom, long numberTo, double amount);

    double valueOfConvertRate(String fromRate, String toRate);

    Account getAccountByNumber(long number);

    void getTotalBalanceInUAH(List<Account> accounts);

    void convertRareInAccount(Account account, double newAmount, double value, String rare);
}
