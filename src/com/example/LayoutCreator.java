package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

public class LayoutCreator {
    private static final int STATUS_OK = 200;
    private static Layout layout = new Layout();

    /**
     * Get the adventure layout based on the command argument input.
     * If given url or file does not work, default file will be used.
     *
     * @param input - command line arguments
     * @return the parsed layout
     */
    public static Layout getAdventureLayout(String[] input) throws UnirestException, MalformedURLException {
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
     * Checks if given url is valid.
     *
     * @return true for valid url
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
