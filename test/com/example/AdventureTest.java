package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AdventureTest {
    private static final String URL = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
    private static Layout layout = new Layout();
    private static ArrayList<Room> roomsArray = new ArrayList<>();
    private static Room[] rooms;
    private static final int STATUS_OK = 200;

    //Parses the URL.
    @Before
    public void setUp() throws Exception{
        final HttpResponse<String> stringHttpResponse;
        new URL(URL);
        stringHttpResponse = Unirest.get(URL).asString();

        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            layout = gson.fromJson(json, Layout.class);
        }
        rooms = layout.getRooms();
        roomsArray = new ArrayList<>(Arrays.asList(rooms));
    }


    //Group of Tests for the basic get functions.
    @Test
    public void getCorrectStartingRoom() {
        assertEquals("MatthewsStreet", layout.getStartingRoom());
    }

    @Test
    public void getCorrectEndingRoom() {
        assertEquals("Siebel1314", layout.getEndingRoom());
    }

    @Test
    public void getCorrectName() {
        assertEquals("MatthewsStreet", roomsArray.get(0).getName());
        assertEquals("Siebel1112", roomsArray.get(4).getName());
        assertEquals("SiebelBasement", roomsArray.get(7).getName());
    }

    @Test
    public void getCorrectDescription() {
        assertEquals("You are on Matthews, outside the Siebel Center",
                roomsArray.get(0).getDescription());
        assertEquals("You are in the north hallway.  You can see Siebel 1112 and the door toward NCSA.",
                roomsArray.get(3).getDescription());
        assertEquals("You are in the basement of Siebel.  You see tables with students " +
                "working and door to computer labs.", roomsArray.get(7).getDescription());
    }

    @Test
    public void getCorrectDirectionName() {
        Direction[] directions = roomsArray.get(0).getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        assertEquals("East", directionsArray.get(0).getDirectionName());
    }

    @Test
    public void getCorrectRoom() {
        Direction[] directions = roomsArray.get(1).getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        assertEquals("SiebelEastHallway", directionsArray.get(3).getRoom());
    }

    //Group of tests for Helper Methods of runAdventure.
    @Test
    public void printCorrectDirections() {
        assertEquals("From here you can go: West, Northeast, North, East.",
                Adventure.printDirections(roomsArray.get(1)));
    }

    @Test
    public void getCorrectStartingRoomDirections() {
        assertEquals(roomsArray.get(0), Adventure.getStartingRoomDirections());
    }

    @Test
    public void correctlyChecksIfUserQuits() {
        assertEquals("User quit.", Adventure.checkIfUserQuits("quit"));
        assertEquals("User has not quit.", Adventure.checkIfUserQuits("nothing"));
    }
}
