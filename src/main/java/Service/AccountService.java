package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.*;

public class AccountService {
    AccountDAO accountDAO;


    public AccountService(){
        accountDAO = new AccountDAO(); 
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO; //dependency injection
    }

    public boolean accountValidation(Account account){
        boolean accountCreated = false;
        if(account.getUsername().isEmpty()){
            System.out.println("You must enter a username!");
        }
        else if(accountDAO.findAccountByUserName(account.getUsername()) != null){
            System.out.println("An account with this username already exists!");

        }
        else if(account.getPassword().isEmpty() || account.getPassword().length() < 4){
            System.out.println("You must enter a password that is at least 4 characters long!");
        }
        else{
            accountCreated = true;
        }

        return accountCreated;
    }

    //create
    public void createAccount(Account account){
        if(accountValidation(account)){
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
        }
        
        if(accountValidation(account)){
            accountDAO.updateAccount(id, account);
            account.setAccount_id(id);
            return true;
        }
        
        return false;
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
