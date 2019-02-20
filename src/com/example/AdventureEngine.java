package com.example;

import java.util.Timer;
import java.util.TimerTask;

public class AdventureEngine {
    /** The time in seconds at thirty seconds. */
    private static final int TIMER_AT_THIRTY_SECONDS = 30;

    /** The end time in seconds for the timer */
    private static final int TIMER_ENDING_TIME = 0;

    /** Half divisor for timer time */
    private static final int HALF_DIVISOR = 2;

    /** Delay of the timer */
    private static final int DELAY_OF_TIMER = 0;

    /** Period of the timer */
    private static final int PERIOD_OF_TIMER = 1000;

    /** The third index of the command argument input */
    private static final int ARGUMENT_THREE_INDEX = 2;

    /**
     * Main function that runs the entire adventure game.
     *
     * @param arguments - the given arguments takes in arguments in the given format:
     *                  - (file/url), (file/url name), (timer in seconds)
     */
    public static void main(String [] arguments) throws Exception{
        countdownTimer(arguments[ARGUMENT_THREE_INDEX]);
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
    private static void countdownTimer(String time) {
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int startTime = Integer.parseInt(time);
            int i = Integer.parseInt(time);
            public void run() {
                i--;
                if (i == (startTime / HALF_DIVISOR)) {
                    System.out.println("Half your time is up. You have " + i + " seconds remaining!");
                }
                if (i < TIMER_AT_THIRTY_SECONDS) {
                    System.out.println(i);
                }
                if (i< TIMER_ENDING_TIME) {
                    timer.cancel();
                    System.out.println("You have failed to complete the game in time. Try Again.");
                    System.exit(0);
                }
            }
        }, DELAY_OF_TIMER, PERIOD_OF_TIMER);
    }
}
