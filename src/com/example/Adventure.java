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
    private static Layout LAYOUT = new Layout();

    /**
     * Parses the url and returns the parsed layout.
     *
     * @param url - the user inputted url
     * @return The parsed layout
     */
    public static Layout makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;
        new URL(url);
        stringHttpResponse = Unirest.get(url).asString();

        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            LAYOUT = gson.fromJson(json, Layout.class);
        }
        return LAYOUT;
    }

    /**
     * Gets the starting room directions.
     *
     * @return the starting rom directions
     */
    public static Room getStartingRoomDirections() {
        Room[] rooms = LAYOUT.getRooms();
        ArrayList<Room> roomsArray = new ArrayList<>(Arrays.asList(rooms));
        Room currentRoom = new Room();
        String startingRoom = LAYOUT.getStartingRoom();
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
    private static void runAdventure() throws Exception {
        Layout layout = makeApiRequest(requestURLFromUser());
        Room[] rooms = layout.getRooms();
        ArrayList<Room> roomsArray = new ArrayList<>(Arrays.asList(rooms));
        String newRoom = "";
        Room currentRoom = getStartingRoomDirections();
        while (!GAME_ENDED) {
            //Print out description of room and possible directions.
            System.out.println(currentRoom.getDescription());
            System.out.println(printDirections(currentRoom));
            Direction[] directions = currentRoom.getDirections();
            ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));

            //Get user input.
            Scanner input = new Scanner(System.in);
            String command = input.nextLine().toLowerCase();

            //Check if user quits game.
            if (checkIfUserQuits(command).equals("User quit.")) {
                System.exit(0);
            }

            //Check if command is valid and print out respective output.
            if (command.length() < 3) {
                System.out.println("I don't understand " + command);
            } else if (command.substring(0, 3).equals("go ")) {
                for (int i = 0; i < directionsArray.size(); i++) {
                    if (command.substring(3).equalsIgnoreCase(directionsArray.get(i).getDirectionName())) {
                        newRoom = directionsArray.get(i).getRoom();
                    }
                }
                if (currentRoom.getName().equals(newRoom)) {
                    System.out.println("I can't " + command);
                }
            } else {
                System.out.println("I don't understand " + command);
            }

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
    private static String requestURLFromUser() {
        System.out.println("Enter your url:");
        Scanner input = new Scanner(System.in);
        String url = input.nextLine().toLowerCase();
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("That URL is not valid: " + url);
        }
        return url;
    }

    /**
     * Prints the directions for the given room.
     *
     * @param currentRoom - the current room the player is in
     * @return the directions for the current room
     */
    public static String printDirections(Room currentRoom) {
        Direction[] directions = currentRoom.getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        String[] directionsNameArray = new String[directionsArray.size()];
        for (int i = 0; i < directionsArray.size(); i++) {
            directionsNameArray[i] = directionsArray.get(i).getDirectionName();
        }
        String array = Arrays.toString(directionsNameArray).replace("[", "")
                .replace("]", "");
        return "From here you can go: " + array + ".";
    }

    /**
     * Checks if the user has quit or not.
     *
     * @param command - the user command
     * @return the status of the user
     */
    public static String checkIfUserQuits(String command) {
        if (command.equals("quit") || command.equals("exit")) {
            return "User quit.";
        }
        return "User has not quit.";
    }

    /**
     * Runs the game.
     * @param arguments - given arguments
     */
    public static void main(String [] arguments) throws Exception{
        runAdventure();
    }
}