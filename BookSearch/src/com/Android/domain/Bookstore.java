package com.Android.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Bookstore
{
  private String _imageURL;
  private String _name;
  private String _rating;
  private String _shippingDesc;
  private String _shippingPrice;
  private String _url;

  public Bookstore(Element paramElement)
  {
    this._name = paramElement.getElementsByTagName("name").item(0).getTextContent();
    this._url = paramElement.getElementsByTagName("url").item(0).getTextContent();
    this._imageURL = paramElement.getElementsByTagName("imageURL").item(0).getTextContent();
    this._shippingDesc = paramElement.getElementsByTagName("shippingDesc").item(0).getTextContent();
    this._shippingPrice = paramElement.getElementsByTagName("shippingPrice").item(0).getTextContent();
    this._rating = paramElement.getElementsByTagName("rating").item(0).getTextContent();
  }

  public String getImageURL()
  {
    return this._imageURL;
  }

  public String getName()
  {
    return this._name;
  }

  public String getRating()
  {
    return this._rating;
  }

  public String getShippingDesc()
  {
    return this._shippingDesc;
  }

  public String getShippingPrice()
  {
    return this._shippingPrice;
  }

  public String getURL()
  {
    return this._url;
  }
}

