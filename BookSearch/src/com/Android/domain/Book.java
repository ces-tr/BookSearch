package com.Android.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Book
{
  private String _authors;
  private String _format;
  private String _isbn;
  private String _title;
  private String _thumbnailURL;
  private String _publishDate;

  public Book(Element paramElement)
  {
    this._title = paramElement.getElementsByTagName("title").item(0).getTextContent();
    this._authors = paramElement.getElementsByTagName("authors").item(0).getTextContent();
    this._format = paramElement.getElementsByTagName("format").item(0).getTextContent();
    this._isbn = paramElement.getElementsByTagName("isbn").item(0).getTextContent();
  }

  public Book(JSONObject json) {
	  String temp="";
	  try {
		  JSONObject volumeInfo = json.getJSONObject("volumeInfo");
		  this._title = volumeInfo.getString("title");
		 
			  try {
			  	JSONArray authors = volumeInfo.getJSONArray("authors");
				  	if (authors.length() != 0) {
				  		this._authors = authors.getString(0);
				  		
				  	}
			  	}catch(JSONException e){this._authors="Unavailable";}
		temp +=this._authors +",";
		  	 try {
		  		this._publishDate = volumeInfo.getString("publishedDate");
		  		
		  	 	}catch(JSONException e){this._publishDate = "Unavailable";}
		 
		  	 try {
			  	JSONObject images = volumeInfo.getJSONObject("imageLinks");
			  	this._thumbnailURL = images.getString("smallThumbnail");
		  	 	}catch(JSONException e){this._thumbnailURL = "Unavailable";}
		  	 
		  	  
		  	 try {
				  JSONArray isbnIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
				  	for (int j = 0; j < isbnIdentifiers.length(); j++) {
						  JSONObject obj = isbnIdentifiers.getJSONObject(j);
						  String type = obj.getString("type");
							  if ("ISBN_10".equals(type) || "ISBN_13".equals(type)) {
								  this._isbn = obj.getString("identifier");
							          
							  }
				  	}
		  	 	}catch(JSONException e){this._isbn = "Unavailable";
		  	 							}
		  	  temp += this._isbn;
		  	 this._format = "Unavailable";
	System.out.println(temp);
	  } catch (Exception e) {
    	e.printStackTrace();
    	}
   }

  public String getAuthors()
  {
    return this._authors;
  }

  public String getFormat()
  {
    return this._format;
  }

  public String getISBN()
  {
    return this._isbn;
  }

  public String getTitle()
  {
    return this._title;
  }
  
  public String getPublishDate() {
      return this._publishDate;
  }
  
  public String getThumbnailURL() {
      return this._thumbnailURL;
  }
  
}
