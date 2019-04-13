package com.example;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Adventure {
    /** The sixth index of the command user input. */
    private static final int SIXTH_INDEX_OF_COMMAND = 6;

    private static boolean gameEnded = false;
    private static Layout layout = new Layout();
    private static ArrayList<Item> playerItems = new ArrayList<>();
    private static ArrayList<Room> roomsArray = new ArrayList<>();
    private static ArrayList<Direction> directionsArray = new ArrayList<>();
    private static ArrayList<Item> roomItems = new ArrayList<>();


    /**
     * Gets the starting room directions.
     *
     * @return the starting rom directions
     */
    public static Room getStartingRoomDirections() {
        Room[] rooms = layout.getRooms();
        roomsArray = new ArrayList<>(Arrays.asList(rooms));
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
     * Set the layout of the game up and other items.
     *
     * @param arguments - the given arguments
     */
    private static void setTheGameUp(String[] arguments) throws MalformedURLException, UnirestException {
        PrintValues.printTutorialInstructions();
        layout = LayoutCreator.getAdventureLayout(arguments);
        Room[] rooms = layout.getRooms();
        roomsArray = new ArrayList<>(Arrays.asList(rooms));
        Item[] items = layout.getPlayer().getItems();
        playerItems = new ArrayList<>(Arrays.asList(items));
    }

    /**
     * Runs the adventure game.
     */
    public static void runAdventure(String[] arguments) throws Exception {
        setTheGameUp(arguments);
        Room currentRoom = getStartingRoomDirections();
        String newRoom = "";
        while (!gameEnded) {
            //Print out description of room and possible directions.
            System.out.println(currentRoom.getDescription());
            System.out.println(PrintValues.printDirections(currentRoom));
            Direction[] directions = currentRoom.getDirections();
            directionsArray = new ArrayList<>(Arrays.asList(directions));
            Item[] item = currentRoom.getItems();
            roomItems = new ArrayList<>(Arrays.asList(item));

            //Get user input.
            Scanner input = new Scanner(System.in);
            String command = input.nextLine().toLowerCase();

            //Check if user quits game.
            if (PrintValues.printUserQuitStatus(command).equals("User quit.")) {
                System.exit(0);
            }

            //Check if command is valid and print out respective output.
            newRoom = checkCommand(command, currentRoom);

            //Set newRoom to the currentRoom object.
            for (int i = 0; i < roomsArray.size(); i++) {
                if (roomsArray.get(i).getName().equals(newRoom)) {
                    currentRoom = roomsArray.get(i);
                }
            }

            //Check if game has ended.
            if (newRoom.equals(layout.getEndingRoom())) {
                gameEnded = true;
                System.out.println(currentRoom.getDescription());
                System.out.println("You have reached your final destination and exit.");
                System.exit(0);
            }
        }
    }

    /**
     * Checks which command method to run.
     *
     * @param command - the command the user inputs
     * @param currentRoom - the currentRoom the player is in
     * @return the newRoom
     */
    private static String checkCommand(String command, Room currentRoom) {
        String newRoom = currentRoom.getName();
        if (command.equalsIgnoreCase("Check Inventory")) {
            System.out.println("Your inventory: " + playerItems);
        } else if (command.contains("pickup ")) {
            addPickupItemToPlayerItems(command);
        } else if (command.contains("use") && command.contains("with")) {
            newRoom = checkIfPlayerItemUsedIsValid(command, currentRoom);
        } else if (command.contains("go ")) {
            newRoom = PrintValues.printOutputFromCommand(command, currentRoom);
        }
        return newRoom;
    }

    /**
     * Checks if the player uses a valid item or not.
     *
     * @param command - the command the user inputs
     * @param currentRoom - the currentRoom the player is in
     * @return the newRoom
     */
    private static String checkIfPlayerItemUsedIsValid(String command, Room currentRoom) {
        String newRoom = currentRoom.getName();
        for (int i = 0; i < directionsArray.size(); i++) {
            for (int j = 0; j < playerItems.size(); j++) {
                if (command.contains(playerItems.get(j).getName().toLowerCase())) {
                    for (int k = 0; k < directionsArray.get(i).getValidKeyNames().length; k++) {
                        if (command.contains(directionsArray.get(i).getValidKeyNames()[k].toLowerCase())) {
                            if (command.contains(directionsArray.get(i).getDirectionName().toLowerCase())) {
                                newRoom = directionsArray.get(i).getRoom();
                            }
                        }
                    }
                }
            }
        }
        return newRoom;
    }

    /**
     * Adds the item the player picks up to the player inventory.
     *
     * @param command - the command the user inputs
     */
    private static void addPickupItemToPlayerItems(String command) {
        if (command.contains("pickup ")) {
            for (int i = 0; i < roomItems.size(); i++) {
                if (command.contains(roomItems.get(i).getName().toLowerCase())) {
                    playerItems.add(roomItems.get(i));
                    System.out.println("You have picked up: " + roomItems.get(i).getName() + "\n");
                }
            }
        } else if (command.contains("pickup")) {
            System.out.println("You can not pickup: " + command.substring(SIXTH_INDEX_OF_COMMAND));
        }
    }
}
