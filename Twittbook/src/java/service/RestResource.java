package service;

import bo.Helper;
import bo.Message;
import bo.Post;
import bo.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
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
    @Produces("application/json")
    public User getUser(@QueryParam("userId") String userId) {

        try {
            int id = Integer.parseInt(userId);
            User user = Helper.getUser(userId);
            System.out.println("username = " + user.getUsername() + "------------------");
            user.setPassword(null);
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    @GET
    @Path("allusers")
    @Produces("application/json")
    public List<User> getAllUsers() {
        List<User> list = Helper.getAllUsers();

        for (User u : list) {
            u.setPassword(null);
        }

        return list;
    }

    @GET
    @Path("feed")
    @Produces("application/json")
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
    @Produces("application/json")
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
    @Produces("application/json")
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
    public void publishPost(Integer userId, String message) {
        Helper.publishPost(userId, new Date(), message);
    }

    //TODO: Post methods, login, register, send pm, send post (feed)
}
