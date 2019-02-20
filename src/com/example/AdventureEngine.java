package com.example;

import java.util.Timer;
import java.util.TimerTask;

public class AdventureEngine {
    /**
     * Main function that runs the entire adventure game.
     *
     * @param arguments - the given arguments takes in arguments in the given format:
     *                  - (file/url), (file/url name), (timer in seconds)
     */
    public static void main(String [] arguments) throws Exception{
        countdownTimer(arguments[2]);
        Adventure.runAdventure(arguments);
    }

    /**
     * Modified from https://docs.oracle.com/javase/8/docs/technotes/guides/lang/Countdown.java
     * Timer that counts down the time and notifies the player when half time remaining
     * and less than 30 seconds remaining.
     * After timer reaches 0 System exits.
     *
     * @param time - the time the timer starts at.
     */
    public static void countdownTimer(String time) {
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int startTime = Integer.parseInt(time);
            int i = Integer.parseInt(time);
            public void run() {
                i--;
                if (i == startTime) {
                    System.out.println("Half your time is up. You have " + i + " seconds remaining!");
                }
                if (i < 30) {
                    System.out.println(i);
                }
                if (i< 0) {
                    timer.cancel();
                    System.out.println("You have failed to complete the game in time. Try Again.");
                    System.exit(0);
                }
            }
        }, 0, 1000);
    }
}
