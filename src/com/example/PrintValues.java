package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class PrintValues {
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
