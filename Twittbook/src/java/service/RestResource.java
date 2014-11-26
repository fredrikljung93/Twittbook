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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;

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

    @GET
    @Path("password")
    @Produces("text/plain")
    public String getHtml() {
        return Helper.getUser("fredrik").getPassword();
    }

    @GET
    @Path("mess")
    @Produces("text/plain")
    public String getMess() {
        return "yo!";
    }

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
            System.out.println("username = " + user.getUsername() + "------------------");
            user.setPassword(null);
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    @GET
    @Path("login")
    @Produces("application/json; charset=UTF-8")
    public User loginUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        User user = Helper.loginUser(username, password);
        return user;

    }

    @GET
    @Path("userbyname")
    @Produces("application/json; charset=UTF-8")
    public User getUserByName(@QueryParam("username") String username) {
        User user = Helper.getUser(username);
        return user;

    }

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

    @GET
    @Path("feed")
    @Produces("application/json; charset=UTF-8")
    public List<Post> getFeed(@QueryParam("userId") String userId) {

        try {
            int id = Integer.parseInt(userId);
            List<Post> list = Helper.getFeed(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @GET
    @Path("outbox")
    @Produces("application/json")
    public List<Message> getOutbox(@QueryParam("userId") String userId) {
        try {
            int id = Integer.parseInt(userId);
            List<Message> list = Helper.getMyOutbox(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @GET
    @Path("inbox")
    @Produces("application/json; charset=UTF-8")
    public List<Message> getInbox(@QueryParam("userId") String userId) {
        try {
            int id = Integer.parseInt(userId);
            List<Message> list = Helper.getMyInbox(id);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

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

    @POST
    @Path("post")
    @Consumes("text/plain")
    public void publishPost(@QueryParam("id") String userId, @QueryParam("message") String message) {
        Helper.publishPost(Integer.parseInt(userId), new Date(), message);
    }

    //TODO: Post methods, login, register, send pm, send post (feed)
}
