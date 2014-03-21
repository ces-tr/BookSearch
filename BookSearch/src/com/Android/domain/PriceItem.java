package com.Android.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PriceItem
{
  private String _buyURL;
  private String _condition;
  private String _finalPrice;
  private String _price;
  private String _shippingDesc;
  private String _shippingPrice;
  private String _storeName;
  private String _storeRating;

  public PriceItem(Element paramElement)
  {
    this._storeName = paramElement.getElementsByTagName("storeName").item(0).getTextContent();
    this._condition = paramElement.getElementsByTagName("condition").item(0).getTextContent();
    this._buyURL = paramElement.getElementsByTagName("buyURL").item(0).getTextContent();
    this._shippingDesc = paramElement.getElementsByTagName("shippingDescription").item(0).getTextContent();
    this._storeRating = paramElement.getElementsByTagName("storeRating").item(0).getTextContent();
    this._price = paramElement.getElementsByTagName("price").item(0).getTextContent();
    this._shippingPrice = paramElement.getElementsByTagName("shippingPrice").item(0).getTextContent();
    this._finalPrice = paramElement.getElementsByTagName("finalPrice").item(0).getTextContent();
  }

  public String getBuyURL()
  {
    return this._buyURL;
  }

  public String getCondition()
  {
    return this._condition;
  }

  public String getFinalPrice()
  {
    return this._finalPrice;
  }

  public String getPrice()
  {
    return this._price;
  }

  public String getShippingDesc()
  {
    return this._shippingDesc;
  }

  public String getShippingPrice()
  {
    return this._shippingPrice;
  }

  public String getStoreName()
  {
    return this._storeName;
  }

  public String getStoreRating()
  {
    return this._storeRating;
  }
}
