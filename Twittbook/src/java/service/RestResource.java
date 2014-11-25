/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bo.Helper;
import bo.User;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import ui.entities.UserBean;

/**
 * REST Web Service
 *
 * @author Fredrik
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
    public UserBean getUser(@QueryParam("userId") String userId) {
        int id;
        try{
            id = Integer.parseInt(userId);
            return UserBean.getUser(id);
        }catch(Exception e){
            userId = null;
        }
        return null;

    }
}
