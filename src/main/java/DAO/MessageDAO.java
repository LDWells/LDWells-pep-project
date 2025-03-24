package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    
     //Create
     //assigns an id even if account doesnt exits, will implement logic in service
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();

        // Retrieve the generated ID
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        
        if (generatedKeys.next()) { 
            int generatedId = generatedKeys.getInt(1); // Get generated ID
            message.setMessage_id(generatedId); // Update message object
        }

        return message;

           }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    //Read
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){ 
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
    
        try {

            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    );
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    
        return null; // Return null if no message is found
    }
    

    public List<Message> getAllMessagesByUser(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){ 
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //Update
    public Message updateMessage(int id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.setInt(4, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Message updated!");
                message.setMessage_id(id);
                return message; // Fetch and return the updated message
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Delete
    public Message deleteMessage(int id){ //may have to change signature
        Connection connection = ConnectionUtil.getConnection();
        Message deletedMessage = null;
        
        try {
            // First, retrieve the message to be deleted
            String selectSql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
    
            if (resultSet.next()) {
                // Create the Message object from the result
                deletedMessage = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
            }
    
            // Now delete the message
            String deleteSql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return deletedMessage; // Return the deleted message, or null if it wasn't found
}

}