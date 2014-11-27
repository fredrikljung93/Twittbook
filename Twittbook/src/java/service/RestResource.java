package service;

import bo.Helper;
import bo.Message;
import bo.Post;
import bo.User;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 */
@Path("rest")
public class RestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestResource
     */
    public RestResource() {
    }

    /**
     * Retrieves representation of an instance of service.RestResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RestResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    /**@deprecated
     @return -
     test method.*/
    @GET
    @Path("password")
    @Produces("text/plain")
    public String getHtml() {
        return Helper.getUser("fredrik").getPassword();
    }

    /**@deprecated 
     @return String
     testmethod.*/
    @GET
    @Path("mess")
    @Produces("text/plain")
    public String getMess() {
        return "Forza bajen!";
    }

    /**@param id PK of User model
     @return json User object without sensitive data.
     Fetch a User from a database.*/
    @GET
    @Path("user")
    @Produces("application/json; charset=UTF-8")
    public User getUser(@QueryParam("userId") String id) {
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException ne) {
            return null;
        }
        try {
            User user = Helper.getUser(userId);
            user.setPassword(null);
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    /**@param username User username.
     @param password User password.
     @return json User ,returns the entire Object User
     Get a User instance to be able to create a session.*/
    @GET
    @Path("login")
    @Produces("application/json; charset=UTF-8")
    public User loginUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        User user = Helper.loginUser(username, password);
        return user;

    }

    /**@param username Username of User
     @return json User, returns the Object User with specified username.
     */
    @GET
    @Path("userbyname")
    @Produces("application/json; charset=UTF-8")
    public User getUserByName(@QueryParam("username") String username) {
        User user = Helper.getUser(username);
        return user;

    }

    /**
     @return json List<User>
     returns a list of User's from database.*/
    @GET
    @Path("allusers")
    @Produces("application/json; charset=UTF-8")
    public List<User> getAllUsers() {
        List<User> list = Helper.getAllUsers();

        for (User u : list) {
            u.setPassword(null);
        }

        return list;
    }

    /**@param userId PK for a User in database.
     @return json List<Post>
     returns a list of Post's with the specified userId.*/
    @GET
    @Path("feed")
    @Produces("application/json; charset=UTF-8")
    public List<Post> getFeed(@QueryParam("userId") String userId) {

        int id;
        try {
            try {
                id = Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                return null;
            }
            List<Post> list = Helper.getFeed(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**@param userId
     @return json List<Message>
     Returns a list of Message's sent by specified userId.*/
    @GET
    @Path("outbox")
    @Produces("application/json")
    public List<Message> getOutbox(@QueryParam("userId") String userId) {
        try {
            int id;
            try {
                id = Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                return null;
            }

            List<Message> list = Helper.getMyOutbox(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**@param userId
     @return json List<Message>
     returns a list of Message's received by userId.*/
    @GET
    @Path("inbox")
    @Produces("application/json; charset=UTF-8")
    public List<Message> getInbox(@QueryParam("userId") String userId) {
        try {
            int id;
            try {
                id = Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                return null;
            }

            List<Message> list = Helper.getMyInbox(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**@param userId PK from User model in DB
     @return json list of User.
     Used to get a list User's who sent messages to userId.*/
    @GET
    @Path("senders")
    @Produces("application/json; charset=UTF-8")
    public List<User> getMessageSenders(@QueryParam("userId") String userId) {
        try {
            int id = Integer.parseInt(userId);
            List<User> list = Helper.getMessageSenders(id);

            for (User u : list) {
                u.setPassword(null);
            }

            return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**@param userId PK from User model in DB.
     @param message message to be sent.
     @return HTTP-response
     Post message to a Users feed.*/
    @POST
    @Path("publishpost")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    public Response publishPost(
            @FormParam("userId") String userId,
            @FormParam("message") String message) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return Response.status(418)
                    .entity("NumberFormatException")
                    .build();
        }

        boolean publishPost = Helper.publishPost(id, new Date(), message);

        if (publishPost) {
            return Response.status(200)
                    .entity("Post saved successfully.")
                    .build();
        } else {
            return Response.status(418)
                    .entity("Error, not saved.")
                    .build();
        }

    }

    /**@param receiverId PK of User model in DB.
     @param message Message to be sent.
     @param senderId PK of User model in DB.
     @return HTTP-response
     Sends a private message from one User to another.*/
    @POST
    @Path("sendpm")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    public Response sendPM(
            @FormParam("receiver") String receiverId,
            @FormParam("message") String message,
            @FormParam("sender") String senderId) {
        int sId;
        int rId;
        try {
            sId = Integer.parseInt(senderId);
            rId = Integer.parseInt(receiverId);
        } catch (NumberFormatException e) {
            return Response.status(418)
                    .entity("NumberFormatError")
                    .build();
        }

        boolean publishPost = Helper.sendPrivateMessage(sId, rId, new Date(), message);

        if (publishPost) {
            return Response.status(200)
                    .entity("PM sent successfully.")
                    .build();
        } else {
            return Response.status(418)
                    .entity("Error, Not saved.")
                    .build();
        }

    }

    /**@param username chosen username of User
     @param password chosen password of User
     @return HTTP-response
     Register a User in DB.*/
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    public Response register(
            @FormParam("username") String username,
            @FormParam("password") String password) {

        User user = Helper.registerUser(username, password);

        System.out.println("Teeeeeeeeeeeeeeeeeeeeeeeeeeeest****************** username: "+username+" "+" password: "+ password);
        if (user != null) {
            return Response.status(200)
                    .entity("Registered successfully.")
                    .build();
        } else {
            return Response.status(418)
                    .entity("Error, user not registered.")
                    .build();
        }

    }
}
