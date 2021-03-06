package org.test.bankapp.model;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.test.bankapp.exception.NotEnoughFundsException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Client implements Serializable {
    private static final Logger log = LogManager.getLogger(Client.class);
    private String name;
    private Set<Account> accounts = new HashSet<Account>();
    private Account activeAccount;
    private float initialOverdraft;
    private Gender gender;
    private String email;
    private String phone;
    private String city;

    private final static int INITIAL_OVERDRTAFT = 300;
    public final static String CLIENT_CHECKING_ACCOUNT_TYPE = "checking";
    public final static String CLIENT_SAVING_ACCOUNT_TYPE = "saving";

    public Client(String name, Gender gender, float initialOverdraft) {
        setGender(gender);
        setInitialOverdraft(initialOverdraft);
        setName(name);
    }

    public Client(String name, Gender gender) {
        this(name, gender, INITIAL_OVERDRTAFT);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public String getName() {
        return name;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void deposit(float x) {
        if (activeAccount != null) {
            activeAccount.deposit(x);
        } else {
            log.log(Level.ERROR, "Deposit is not possible. \n" +
                    "Ddeposit with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
            throw new RuntimeException("Deposit is not possible. \n" +
                    "Ddeposit with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
        }
    }

    public void withdraw(float x) throws NotEnoughFundsException {
        if (activeAccount != null) {
            activeAccount.withdraft(x);
        } else {
            log.log(Level.ERROR, "Withdraft( is not possible. \n" +
                    "Withdraft with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
            throw new RuntimeException("Withdraft( is not possible. \n" +
                    "Withdraft with value" +
                    x +
                    "  for active account is not possible. Account not defined. ");
        }
    }

    public Account createAccount(String accountType) {
        Account newAccout;
        if (!accountType.equals(CLIENT_CHECKING_ACCOUNT_TYPE) && !accountType.equals(CLIENT_SAVING_ACCOUNT_TYPE)) {
            log.log(Level.ERROR, "Account creation is not possible. \n" +
                    "Defined accountType: " +
                    accountType +
                    "  is not accessible. Accessible list(" +
                    CLIENT_CHECKING_ACCOUNT_TYPE + ", " +
                    CLIENT_SAVING_ACCOUNT_TYPE +
                    ". ");
            throw new RuntimeException("Account creation is not possible. \n" +
                    "Defined accountType: " +
                    accountType +
                    "  is not accessible. Accessible list(" +
                    CLIENT_CHECKING_ACCOUNT_TYPE + ", " +
                    CLIENT_SAVING_ACCOUNT_TYPE +
                    ". ");
        } else {
            if (accountType.equals(CLIENT_CHECKING_ACCOUNT_TYPE)) {
                newAccout = new CheckingAccount(initialOverdraft);
            } else {
                newAccout = new SavingAccount();
            }
        }
        return newAccout;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public float getBalance() {
        float result = 0.f;
        for (Account tmpAccount : accounts) {
            if (tmpAccount != null) {
                result = result + tmpAccount.getBalance();
            }
        }
        return result;
    }

    public void printReport() {
        System.out.println("  Client name       : " + getClientSalutation());
        System.out.println("  Client geder      : " + (getGender() != null ? getGender().gender : ""));
        System.out.println("  Client phone      : " + getPhone());
        System.out.println("  Client email      : " + getEmail());
        System.out.format("  Client overdraft  : %.2f\n", getInitialOverdraft());
        System.out.format("  Client balance    : %.2f\n", getBalance());
        System.out.println("  Client city       : " + getCity());
        System.out.println("  Active account    :");
        if (getActiveAccount() != null) {
            getActiveAccount().printReport();
        }
        Set<Account> accounts = getAccounts();
        System.out.println("  Client accounts information  (accounts count " + accounts.size() + ") :");
        int j = 1;
        for (Account account : accounts) {
            System.out.println("Account # [" + j + "]");
            account.printReport();
            j++;
            System.out.println("--------------------------------------------------------------");
        }
    }

    public String getClientSalutation() {
        if (gender == null) return name;
        return gender.gender + ". " + name;
    }

    // Only name and gender are used for code calculation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        if (name == null) {
            if (client.name != null) {
                return false;
            }
        } else {
            if (!name.equals(client.name)) {
                return false;
            }
        }
        if (gender == null) {
            if (client.gender != null) {
                return false;
            }
        } else {
            if (!(gender == client.gender)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder accountData = new StringBuilder();
        accountData.append("Accounts count: " + (accounts != null ? accounts.size() : 0) + "{");
        int i = 0;
        for (Account account : accounts) {

            if (i != 0) {
                accountData.append(", ");
            }
            accountData.append("acc[" + i + "]={" + account + "}");
            i++;
        }
        accountData.append("}");
        return "Client{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                ", email=" + email +
                ", city=" + city +
                ", accounts=" + accountData.toString() +
                ", activeAccount=" + activeAccount +
                ", initialOverdraft=" + initialOverdraft +
                ", gender=" + gender +
                '}';
    }


    /**
     * This method finds account by its type or create a new one
     */

    private Account getAccount(String accountType) {
        for (Account acc : accounts) {
            if ((accountType.equals(CLIENT_CHECKING_ACCOUNT_TYPE) &&
                    acc instanceof CheckingAccount) ||
                    accountType.equals(CLIENT_SAVING_ACCOUNT_TYPE) &&
                            acc instanceof SavingAccount
                    ) {
                return acc;
            }
        }
        Account tempAccount  = createAccount(accountType);
        accounts.add(tempAccount);
        return tempAccount;

    }

    public void parseFeed(Map<String, String> feed) {
        String accountType = feed.get("accounttype");

        Account acc = getAccount(accountType);
        /**
         * This method should read all account info from the feed.
         * There will be different implementations for
         * CheckingAccount and SavingAccount.
         */
        acc.parseFeed(feed);
    }
}
