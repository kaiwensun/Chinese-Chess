package com.skw.app.chess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }


    @GET
    @Path("extension")
    @Produces(MediaType.TEXT_PLAIN)
    public String extension() {
        return "extension Got it!";
    }

    @GET
    @Path("extension/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String insertId(@PathParam("id") String id, @QueryParam("name") String name) {
        return "extension Got it! name = " + name + " and id = " + id;
    }
}
