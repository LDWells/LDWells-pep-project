package Controller;

import java.util.List;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.AccountService;
import Service.MessageService;
import Util.ConnectionUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    ObjectMapper om = new ObjectMapper();


    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserID);


        return app;
    }


      public void registerHandler(Context ctx) throws JsonProcessingException{
        Account account = om.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);
        if(addedAccount!=null){
            ctx.json(om.writeValueAsString(addedAccount)); //retrieving data from request body
        }
        else{
            ctx.status(400);
        }
    }

    public void loginHandler(Context ctx) throws JsonProcessingException {
        Account loginRequest = om.readValue(ctx.body(), Account.class);
        
        Account storedAccount = accountService.getAccountByUsername(loginRequest.getUsername());
    
        if (storedAccount != null && storedAccount.getPassword().equals(loginRequest.getPassword())) {
            // Successful login
            ctx.json(om.writeValueAsString(storedAccount));
        } else {
            // Unauthorized (invalid username or password)
            ctx.status(401);
        }
    }

    public void createMessageHandler(Context ctx) throws JsonProcessingException{
        Message message = om.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        //validation already done in service class
        if(addedMessage!=null){
            ctx.json(om.writeValueAsString(addedMessage));
        }
        else{
            ctx.status(400);
        }
    }

    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);// Sends a JSON response with the list of messages
    }

    public void getMessageByIdHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id")); //retrieving data from parameter
        Message message = messageService.getMessageByID(message_id);
        if (message != null) {
            ctx.json(message); // If found, return the message
        } 
    }

    public void deleteMessageHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(message_id);
        if(deletedMessage!= null){
        ctx.json(deletedMessage);
        }
    }

    public void updateMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
    
        // Fetch the existing message to preserve unchanged fields
        Message existingMessage = messageService.getMessageByID(message_id);
        if (existingMessage == null) {
            ctx.status(400); // Return Not Found if the message doesn't exist
            return;
        }
    
        // Deserialize the incoming request body, but we only want to update specific fields
        Message partialMessage = om.readValue(ctx.body(), Message.class);
    
        // Update only the fields that are not null (i.e., only the fields in the partial update)
        if (partialMessage.getMessage_text() != null) {
            existingMessage.setMessage_text(partialMessage.getMessage_text());
        }
        if (partialMessage.getPosted_by() != 0) {
            existingMessage.setPosted_by(partialMessage.getPosted_by());
        }
        if (partialMessage.getTime_posted_epoch() != 0) {
            existingMessage.setTime_posted_epoch(partialMessage.getTime_posted_epoch());
        }
    
        // Call service to perform the actual update in the database
        Message updatedMessage = messageService.updateMessage(message_id, existingMessage);
    
        if (updatedMessage != null) {
            ctx.json(updatedMessage); // Send back the updated message
        } else {
            ctx.status(400); // Bad request if the update failed
        }
    }
    

    public void getAllMessagesByUserID(Context ctx) throws JsonProcessingException{
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByUserID(account_id);
        ctx.json(messages);

    }

}