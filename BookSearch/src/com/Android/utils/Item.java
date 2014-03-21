package com.Android.utils;

import com.Android.enums.SearchType;

public class Item {

    public enum Type {
            TITLE, AUTHOR, ALL_FIELDS, ISBN
    }
   
    SearchType type;
    String to_search;
    public SearchType getType() {
            return type;
    }
    public void setType(SearchType type) {
            this.type = type;
    }
    public String getSearch() {
            return to_search;
    }
    public void setSearch(String tosearch) {
            this.to_search = tosearch;
    }
   
}
