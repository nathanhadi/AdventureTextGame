package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Adventure {
    private static final int STATUS_OK = 200;
    private static boolean GAME_ENDED = false;
    private static Layout layout = new Layout();

    /**
     * Get the adventure layout based on the command argument input.
     * If given url or file does not work, default file will be used.
     *
     * @param input - command line arguments
     * @return the parsed layout
     */
    public static Layout getAdventureLayout(String[] input) throws UnirestException, MalformedURLException{
        String defaultFile = "adventure.json";
        if (input[0].equalsIgnoreCase("url")) {
            if (checkIfURLIsValid(input[1])) {
               layout = makeApiRequest(input[1]);
            } else {
                layout = getInputtedAdventureLayout(defaultFile);
            }
        } else if (input[0].equalsIgnoreCase("file")) {
            layout = getInputtedAdventureLayout(input[1]);
        } else {
            layout = getInputtedAdventureLayout(defaultFile);
        }
        return layout;
    }

    /**
     * Get the adventure layout for the inputted file.
     *
     * @param input - the file name
     * @return the parsed layout
     */
    private static Layout getInputtedAdventureLayout(String input) {
        String file = Data.getFileContentsAsString(input);
        Gson gson = new Gson();
        layout = gson.fromJson(file, Layout.class);
        return layout;
    }

    /**
     * Parses the url and returns the parsed layout.
     *
     * @param url - the user inputted url
     * @return The parsed layout
     */
    private static Layout makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;
        new URL(url);
        stringHttpResponse = Unirest.get(url).asString();

        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            layout = gson.fromJson(json, Layout.class);
        }
        return layout;
    }

    /**
     * Gets the starting room directions.
     *
     * @return the starting rom directions
     */
    public static Room getStartingRoomDirections() {
        Room[] rooms = layout.getRooms();
        ArrayList<Room> roomsArray = new ArrayList<>(Arrays.asList(rooms));
        Room currentRoom = new Room();
        String startingRoom = layout.getStartingRoom();
        for (int i = 0; i < roomsArray.size(); i++) {
            if (roomsArray.get(i).getName().equals(startingRoom)) {
                currentRoom = roomsArray.get(i);
            }
        }
        return currentRoom;
    }

    /**
     * Runs the adventure game.
     */
    public static void runAdventure(String[] arguments) throws Exception {
        layout = getAdventureLayout(arguments);
        Room[] rooms = layout.getRooms();
        ArrayList<Room> roomsArray = new ArrayList<>(Arrays.asList(rooms));
        Room currentRoom = getStartingRoomDirections();
        while (!GAME_ENDED) {
            //Print out description of room and possible directions.
            System.out.println(currentRoom.getDescription());
            System.out.println(PrintValues.printDirections(currentRoom));

            //Get user input.
            Scanner input = new Scanner(System.in);
            String command = input.nextLine().toLowerCase();

            //Check if user quits game.
            if (PrintValues.printUserQuitStatus(command).equals("User quit.")) {
                System.exit(0);
            }

            //Check if command is valid and print out respective output.
            String newRoom = PrintValues.printOutputFromCommand(command, currentRoom);

            //Set newRoom to the currentRoom object.
            for (int i = 0; i < roomsArray.size(); i++) {
                if (roomsArray.get(i).getName().equals(newRoom)) {
                    currentRoom = roomsArray.get(i);
                }
            }

            //Check if game has ended.
            if (newRoom.equals(layout.getEndingRoom())) {
                GAME_ENDED = true;
                System.out.println(currentRoom.getDescription());
                System.out.println("You have reached your final destination and exit.");
                System.exit(0);
            }
        }
    }

    /**
     * Requests the url from the user.
     *
     * @return the user inputted URL
     */
    private static boolean checkIfURLIsValid(String url) {
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
            return false;
        } catch (MalformedURLException e) {
            System.out.println("That URL is not valid: " + url);
            return false;
        }
        return true;
    }
}