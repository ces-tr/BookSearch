package com.Android.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Android.domain.Book;

public class GoogleBooksSearch  {
        private static final String API_KEY = "AIzaSyAg0846miymBTw7QBv6uJ5aENqAs5-Fpik";
        private static String URL="https://www.googleapis.com/books/v1/volumes?q=";

        public ArrayList<Book> search(ArrayList<Item> requestItems) {
                try {
                        String request = constructUri(requestItems);
                        JSONObject json = GoogleBookHttpConnector.httpsGetRequest(request);
                        
                       return parseJson(json);
                } catch (JSONException ex) {
                		System.out.println("error json");
                        System.err.println(ex.toString());
                        return (ArrayList<Book>) Collections.<Book> emptyList();
                } catch (IOException e) {
                	System.out.println("error io");
                        System.err.println(e.toString());
                        return (ArrayList<Book>) Collections.<Book> emptyList();
                }
        }

       private static String constructUri(ArrayList<Item> requestItems) {
                StringBuilder builder = new StringBuilder();
                builder.append(URL);
                
                for (int i = 0; i < requestItems.size(); i++) {
                        builder.append(itemToParam(requestItems.get(i)));
                        builder.append('&');
                }
                //builder.append("key=").append(API_KEY);
                builder.append("startIndex=0&maxResults=40");
                System.out.println("url: " + builder.toString());
                return builder.toString();
                
                // "https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes&key=AIzaSyAg0846miymBTw7QBv6uJ5aENqAs5-Fpik";
        }

        private static String itemToParam(Item item) {
                switch (item.type) {
                case author:
                        return "inauthor:" + item.getSearch();
                case title:
                        return "intitle:" + item.getSearch();
                case isbn:
                        return "isbn:" + item.getSearch();
                case ALL_FIELDS:
                default:
                        return item.getSearch();
                }
        }

        private ArrayList<Book> parseJson(JSONObject requestResult) {
                try {
                        JSONArray item = requestResult.getJSONArray("items");
                        ArrayList<Book> result = new ArrayList<Book>(item.length());
                        for (int i = 0; i < item.length(); i++) {
                                
                            JSONObject json = item.getJSONObject(i);
                            Book book = new Book(json);
                            result.add(book);
                        }
                return result;
                
                } catch (JSONException e) {
                        return (ArrayList<Book>)Collections.<Book> emptyList();
                }
        }
}



