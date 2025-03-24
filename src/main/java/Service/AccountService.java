package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.*;

public class AccountService {
    AccountDAO accountDAO;


    public AccountService(){
        accountDAO = new AccountDAO(); //dependency injection
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //create
    public void createAccount(Account account){
        if(account.getUsername() == ""){
            System.out.println("You must enter a username!");
            //return null;
        }
        else if(account.getPassword() == ""){
            System.out.println("You must enter a password!");
            //return null;
        }
        else{
            System.out.println("Account created!");
            accountDAO.createAccount(account); 
        }
    } 

    //read
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account getAccountByID(int id){
        if(accountDAO.getAccountById(id) == null){
            System.out.println("Account ID " + id + " does not exist.");
            return null;
        }
        return accountDAO.getAccountById(id);
    }

    //update
    public boolean updateAccount(int id, Account account){
        if(accountDAO.getAccountById(id) == null){
            System.out.println("Account ID " + id + " does not exist.");
            return false;
        }
        else{
            accountDAO.updateAccount(id, account);
            account.setAccount_id(id);
            return true;
        }
    }
    //delete
    public boolean deleteAccount(int id){
        if(accountDAO.getAccountById(id) != null){
            accountDAO.deleteAccount(id);
            System.out.println("Account ID " + id + " has been deleted!");
            return true;
        }
        else{
            System.out.println("Account ID " + id + " does not exist.");
            return false;
        }
    }
    
}
