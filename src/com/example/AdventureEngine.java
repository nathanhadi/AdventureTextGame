package com.example;

public class AdventureEngine {
    /**
     * Main function that runs the entire adventure game.
     *
     * @param arguments - the given arguments takes in arguments in the given format:
     *                  - (file/url), file/url name)
     */
    public static void main(String [] arguments) throws Exception{
        Adventure.runAdventure(arguments);
    }
}
