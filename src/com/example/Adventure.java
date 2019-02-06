package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;

public class Adventure {
    private static final int STATUS_OK = 200;
    private static final String URL = requestURLFromUser();

    public static void main(String [] arguments) throws Exception {
    }

    public static Layout makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;
        new URL(url);
        Layout layout = new Layout();
        stringHttpResponse = Unirest.get(url).asString();

        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            layout = gson.fromJson(json, Layout.class);
        }
        return layout;
    }

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
}
