/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloWorld;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Fredrik
 */
@Path("helloworld")
public class HelloWorld {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HelloWorld
     */
    public HelloWorld() {
    }

    /**
     * Retrieves representation of an instance of helloWorld.HelloWorld
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getXml() {
        //TODO return proper representation object
        return "<html><head><title>titel</title></head><body>Hejsan!</body></html>";
    }

    /**
     * PUT method for updating or creating an instance of HelloWorld
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    @GET
    @Path("mess")
    @Produces("text/plain")
    public String getMess() {
        return "Yo!";
    }
}
