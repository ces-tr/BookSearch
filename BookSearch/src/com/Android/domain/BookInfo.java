package com.Android.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BookInfo
{
  private String _authors;
  private String _format;
  private String _imageURL;
  private String _isbn;
  private String _title;

  public BookInfo(Element paramElement)
  {
    this._imageURL = paramElement.getElementsByTagName("ImageURL").item(0).getTextContent();
    this._title = paramElement.getElementsByTagName("Title").item(0).getTextContent();
    this._authors = paramElement.getElementsByTagName("Authors").item(0).getTextContent();
    this._isbn = paramElement.getElementsByTagName("ISBN").item(0).getTextContent();
    this._format = paramElement.getElementsByTagName("Format").item(0).getTextContent();
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

  public String getImageURL()
  {
    return this._imageURL;
  }

  public String getTitle()
  {
    return this._title;
  }
}
