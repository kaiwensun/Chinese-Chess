package com.skw.app.chess;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.skw.app.chess.game.ChessRoom;
import com.skw.app.chess.game.Player;
import com.skw.app.chess.game.User;

@Path("api/v1/setup")
public class SetupResource {

    private static final ChessRoom chessRoom = new ChessRoom();

    @POST
    @Path("create_player")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Player create_player(String jsonRequest) {
        String playerName = null;
        boolean isHost = false;
        try {
            JSONObject object = new JSONObject(jsonRequest);
            playerName = object.getString("player_name");
            isHost = object.getBoolean("is_host");
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println(jsonRequest);
            return null;
        }
        if (playerName == null) {
            return null;
        }
        return chessRoom.createPlayer(playerName, isHost);
    }

    @POST
    @Path("delete_player")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message delete_player(String jsonRequest) {
        String playerId = null;
        System.out.println(jsonRequest);
        try {
            JSONObject object = new JSONObject(jsonRequest);
            playerId = object.getString("player_id");
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println(jsonRequest);
            return null;
        }
        if (playerId == null) {
            return null;
        }
        chessRoom.deletePlayer(playerId);
        return new Message("OK");
    }

    // below are tests
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
    public String getIt() {
        System.out.println("abc");
        return "Got it!";
    }

    @GET
    @Path("extension")
    @Produces(MediaType.TEXT_PLAIN)
    public String extension(@QueryParam("number") int number) {
        System.out.println(number);
        return "extension Got it!" + number;
    }

    @GET
    @Path("extension/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String insertId(@PathParam("id") String id,
            @QueryParam("name") String name) {
        return "extension Got it! name = " + name + " and id = " + id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json_path")
    public Response query(@QueryParam("number") int number) {
        String result = "Test JSON created : " + number;
        System.out.println(result);
        // return result;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("recived", number);
            jsonObject.put("recived + 1", number + 1);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(jsonObject.toString());

        return Response.status(200).entity(jsonObject.toString())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/getUserJson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserJson(String jsonRequest) {
        try {
            JSONObject jsonObject = new JSONObject(jsonRequest);
            System.out.println(jsonObject.toString(4));
            System.out.println("json object");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println(e);
        }
        System.out.println(jsonRequest);
        User user = new User();
        return user;
    }

    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("I received your greating!");
        return "OK";
    }

}
