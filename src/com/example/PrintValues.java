package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class PrintValues {
    /** The first/starting index for the command input. */
    private static final int STARTING_INDEX_OF_COMMAND = 0;

    /** The third index for the command input. */
    private static final int THIRD_INDEX_OF_COMMAND = 3;

    /**
     * Prints starting instructions for the game.
     */
    public static void printTutorialInstructions() {
        System.out.println("Welcome to the Mansion. In order to escape, here are a couple helpful tips:");
        System.out.println("1. Some rooms are locked, to enter the room you might need a certain item.");
        System.out.println("2. In order to pick up an item you must use the command 'pickup (item)'. " +
                "You can view these items using the command 'check inventory'");
        System.out.println("3. To use your item you must use the command 'use (item) with (direction)'.");
        System.out.println("4. You only have 1000 seconds to complete this adventure.");
        System.out.println("The game is starting, you are currently locked in a jail cell.\n");
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
     * Takes the command inputted by user and checks if it is valid or not and prints out the corresponding output.
     *
     * @param command - command inputted by user
     * @param currentRoom - the currentRoom the player is in
     * @return the newRoom the player ends up in
     */
    public static String printOutputFromCommand(String command, Room currentRoom) {
        String newRoom = currentRoom.getName();
        Direction[] directions = currentRoom.getDirections();
        ArrayList<Direction> directionsArray = new ArrayList<>(Arrays.asList(directions));
        boolean enabled = true;
        if (command.length() < THIRD_INDEX_OF_COMMAND) {
            System.out.println("I don't understand " + command);
        } else if (command.substring(STARTING_INDEX_OF_COMMAND, THIRD_INDEX_OF_COMMAND).equals("go ")) {
            for (int i = 0; i < directionsArray.size(); i++) {
                if (command.substring(THIRD_INDEX_OF_COMMAND).
                        equalsIgnoreCase(directionsArray.get(i).getDirectionName())) {
                    if (directionsArray.get(i).getEnabled().equals("true")) {
                        newRoom = directionsArray.get(i).getRoom();
                    } else {
                        System.out.println("You may not enter this room until you get a certain item.");
                        enabled = false;
                    }
                }
            }
            if (enabled && (currentRoom.getName().equals(newRoom))) {
                System.out.println("I can't " + command);
            }
        }
        return newRoom;
    }

    /**
     * Checks if the user has quit or not.
     *
     * @param command - the user command
     * @return the status of the user
     */
    public static String printUserQuitStatus(String command) {
        if (command.equals("quit") || command.equals("exit")) {
            return "User quit.";
        }
        return "User has not quit.";
    }
}
