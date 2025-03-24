package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    //method that will help check for duplicate accounts
    public Account findAccountByUserName(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, username);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){ 
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
            return account;    
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    //Create
    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //needed to generate new primary key

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();
           
            // Retrieve the generated ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        
        if (generatedKeys.next()) { 
            int generatedId = generatedKeys.getInt(1); // Get generated ID
            account.setAccount_id(generatedId); // Update message object
        }
            return account;
        
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Read
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){ 
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){ 
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
            return account;    
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, username);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){ 
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
            return account;    
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Update
    public void updateAccount(int id, Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE account SET username = ?, password = ? WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setInt(3, id);
           
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Delete
    public void deleteAccount(int id){ 
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}