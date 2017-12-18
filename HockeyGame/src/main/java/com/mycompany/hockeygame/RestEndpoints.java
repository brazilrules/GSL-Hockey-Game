/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hockeygame;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author leo_k
 */
@Path("/hockey")
public class RestEndpoints {

    @GET
    @Path("/login/{email}/{password}")
    public Response checkLogin(@PathParam("email") String email, @PathParam("password") String password) {
        try {
            java.nio.file.Path path = Paths.get("/hockeyGame/Users/" + email);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                if(lines.get(0).equals(password)) {
                    return Response.status(200).entity("true").build();
                }
            }
            return Response.status(200).entity("false").build();
        } catch(Exception ex) {
            return Response.status(200).entity("There was an error, please try again.").build();
        }
    }
    
    @GET
    @Path("/register/{email}/{password}")
    public Response register(@PathParam("email") String email, @PathParam("password") String password) {
        try {
            java.nio.file.Path path = Paths.get("/hockeyGame/Users/" + email);
            if (Files.exists(path)) {
                return Response.status(200).entity("false").build();
            }
            Files.createDirectories(path.getParent());
            Files.write(path, (password + "\n0").getBytes("UTF-8"));
            return Response.status(200).entity("true").build();
        } catch(Exception ex) {
            return Response.status(200).entity("There was an error, please try again.").build();
        }
    }
}
