package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AdventureTest {
    private static final String fileName = "adventure.json";
    private static final String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
    private static Layout layout = new Layout();
    private static ArrayList<Room> roomsArray = new ArrayList<>();
    private static String[] arguments = { "file", fileName};

    //Parses the file.
    @Before
    public void setUp() {
        String file = Data.getFileContentsAsString(fileName);
        Gson gson = new Gson();
        layout = gson.fromJson(file, Layout.class);
        Room[] rooms = layout.getRooms();
        roomsArray = new ArrayList<>(Arrays.asList(rooms));
    }


    //Group of Tests for the basic get functions.
    @Test
    public void getCorrectStartingRoom() {
        assertEquals("LockedRoom", layout.getStartingRoom());
    }

    @Test
    public void getCorrectEndingRoom() {
        assertEquals("TheGreatOutside", layout.getEndingRoom());
    }

    @Test
    public void getCorrectName() {
        assertEquals("LockedRoom", roomsArray.get(0).getName());
        assertEquals("ElevatorBasement", roomsArray.get(11).getName());
        assertEquals("ElevatorKeyRoom", roomsArray.get(23).getName());
    }

    @Test
    public void getCorrectDescription() {
        assertEquals("You are in a locked room and see a door in front of you. " +
                        "On the floor there is a key labeled 'tutorial key'.",
                roomsArray.get(0).getDescription());
        assertEquals("You enter an elevator and see the panel has only two options, B and 2.",
                roomsArray.get(10).getDescription());
        assertEquals("Among the broken glass scattered around the floor " +
                "you see a 'key'.", roomsArray.get(23).getDescription());
    }

    @Test
    public void getCorrectDirectionName() {
        Direction[] directions = roomsArray.get(0).getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        assertEquals("South", directionsArray.get(0).getDirectionName());
    }

    @Test
    public void getCorrectRoom() {
        Direction[] directions = roomsArray.get(5).getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        assertEquals("Kitchen", directionsArray.get(3).getRoom());
    }

    @Test
    public void getCorrectPlayerItems() {
        Item[] items = layout.getPlayer().getItems();
        ArrayList<Item> itemsArray = new ArrayList<>(Arrays.asList(items));
        assertEquals("compass", itemsArray.get(0).getName());
    }

    @Test
    public void getCorrectRoomItems() {
        Item[] items = roomsArray.get(0).getItems();
        ArrayList<Item> itemsArray = new ArrayList<>(Arrays.asList(items));
        assertEquals("Tutorial Key", itemsArray.get(0).getName());
    }

    @Test
    public void getCorrectEnabledStatus() {
        Direction[] directions = roomsArray.get(5).getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        assertEquals("true", directionsArray.get(2).getEnabled());
    }

    @Test
    public void getCorrectValidKeyNames() {
        Direction[] directions = roomsArray.get(5).getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        assertEquals("Sapphire Key", directionsArray.get(0).getValidKeyNames()[0]);
    }

    //Group of tests for Print Values in PrintValues class.
    @Test
    public void printCorrectDirections() {
        assertEquals("From here you can go: East.",
                PrintValues.printDirections(roomsArray.get(1)));
    }

    @Test
    public void printCorrectUserQuitStatus() {
        assertEquals("User quit.", PrintValues.printUserQuitStatus("quit"));
        assertEquals("User has not quit.", PrintValues.printUserQuitStatus("nothing"));
    }

    @Test
    public void printCorrectCommandResultWhenValidCommandAndCheckIfUserMovesRoomsIfLockedRoom() {
        assertEquals(roomsArray.get(0).getName(),
                PrintValues.printOutputFromCommand("go south",roomsArray.get(0)));
    }

    @Test
    public void printCorrectCommandResultWhenValidCommandAndCheckIfUserMovesRooms() {
        assertEquals(roomsArray.get(6).getName(),
                PrintValues.printOutputFromCommand("go south",roomsArray.get(5)));
    }

    @Test
    public void printCorrectCommandResultWhenInvalidCommand() {
        assertEquals(roomsArray.get(0).getName(),
                PrintValues.printOutputFromCommand("go north",roomsArray.get(0)));
    }

    @Test
    public void getCorrectStatusOfURL() {
        assertTrue(LayoutCreator.checkIfURLIsValid(url));
    }
}
