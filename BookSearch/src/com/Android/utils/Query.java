package com.Android.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.Android.BookSearch;
import com.Android.domain.Book;
import com.Android.domain.BookInfo;
import com.Android.domain.Bookstore;
import com.Android.domain.PriceItem;
import com.Android.enums.SearchType;
import com.Android.enums.ServerType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Query
{
  private static String BOOKSTORE_URL = "http://campusbooks4less.com/cgi-bin/bookSearch/bookstoreDetails.cgi?src=andapp";
  private static String NEWSLETTER_URL = "http://campusbooks4less.com/cgi-bin/formmail.cgi?recipient=campusbooks4less@campusbooks4less.com&subject=Campusbooks4less+Subscription+iPhone&";
  private static final String TAG = "Query";
  private static String URL = "http://campusbooks4less.com/cgi-bin/bookSearch/iPhoneNativeXML.cgi?";
  private String _errorMessage;
  ProgressDialog pd;

   public String getErrorMessage()
  {
    return this._errorMessage;
  }
  

  public boolean query(BookSearch paramBookSearch, ServerType servertype, SearchType paramSearchType, String paramString)
  {
	  
	 if(servertype == ServerType.Googlebooks){
		 
		 System.out.println("Googlebooks");
		return queryGBooks(paramBookSearch,paramSearchType, Utils.clean(paramString));
	 }
	  //CampusBooks
	  
	  if (paramSearchType == SearchType.isbn)
		  return queryISBN(paramBookSearch, paramString);
	
	  String str = URL + "searchBy=" + paramSearchType + "&searchVal=" + Utils.clean(paramString) + "&func=Submit&src=andapp";
    
    return query(paramBookSearch, str);
  }

  public boolean query(BookSearch paramBookSearch, String paramString)
  {
	  ArrayList<Book> localArrayList = new ArrayList<Book>();
      NodeList localNodeList1,localNodeList2;
      int i=0;
      try {
		  URL localURL = new URL(paramString);
	      Document localDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(localURL.openStream()));
	      localDocument.getDocumentElement().normalize();
	      Element localElement = localDocument.getDocumentElement();
	      localNodeList1 = localElement.getElementsByTagName("error");
	      if (localNodeList1.getLength() > 0)
	        {
	          this._errorMessage = ((Element)localNodeList1.item(0)).getTextContent();
	          return false;
	        }
	      localNodeList2 = localElement.getElementsByTagName("book");
      }
      catch(Exception localException){
    	  this._errorMessage = localException.getMessage();
	        localException.printStackTrace();
	        return false;
      }
      
    while (true)
    {
      if (i >= localNodeList2.getLength())
	        {
	          paramBookSearch.setSearchResults(localArrayList);//ArrayList de Libros encontrados guardado en BookSearch
	          return true;
	        }
	      
      localArrayList.add(new Book((Element)localNodeList2.item(i)));
     i++;
    
    }
  }
  public boolean queryGBooks(BookSearch paramBookSearch, SearchType paramSearchType, String paramString){
	 
	  GoogleBooksSearch gbsearch= new GoogleBooksSearch();
	  
	  ArrayList<Item> paramlist = new ArrayList<Item>();
	  Item item = new Item();
      item.setType(paramSearchType);
      item.setSearch(paramString);
      
      paramlist.add(item);
      
      ArrayList<Book> books = gbsearch.search(paramlist);
      
      paramBookSearch.setSearchResults(books);//ArrayList de Libros encontrados guardado en BookSearch
      
  return true;
  }

  public boolean queryBookstores(BookSearch paramBookSearch)
  {
    try
    {
      URL localURL = new URL(BOOKSTORE_URL);
      Document localDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(localURL.openStream()));
      localDocument.getDocumentElement().normalize();
      Element localElement = localDocument.getDocumentElement();
      ArrayList<Bookstore> localArrayList = new ArrayList<Bookstore>();
      NodeList localNodeList = localElement.getElementsByTagName("store");
      for (int i = 0; ; i++)
      {
        if (i >= localNodeList.getLength())
        {
          paramBookSearch.setBookstores(localArrayList);
          return true;
        }
        localArrayList.add(new Bookstore((Element)localNodeList.item(i)));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this._errorMessage = localException.getMessage();
    }
    return false;
  }

  public boolean queryExact(BookSearch paramBookSearch, String paramString)
  {
	  System.out.println(paramString);
      ArrayList<PriceItem> localArrayList = new ArrayList<PriceItem>();
      NodeList localNodeList1,localNodeList2,localNodeList3;
      BookInfo localBookInfo;
      int i=0,j=0;
      try
      {
        URL localURL = new URL(paramString);
        Document localDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(localURL.openStream()));
        localDocument.getDocumentElement().normalize();
        Element localElement = localDocument.getDocumentElement();
        localNodeList1 = localElement.getElementsByTagName("error");
	        if (localNodeList1.getLength() > 0)
	        {
	          this._errorMessage = ((Element)localNodeList1.item(0)).getTextContent();
	          return false;
	        }
        localNodeList2 = localElement.getElementsByTagName("bookInfo");
        localBookInfo = new BookInfo((Element)localNodeList2.item(0));
        
        localNodeList3 = localElement.getElementsByTagName("priceItem");
        j = localNodeList3.getLength();
        
      }
      catch (Exception localException)
      {
        this._errorMessage = localException.getMessage();
        localException.printStackTrace();
        return false;
      }
     /*do {
    	PriceItem localPriceItem = new PriceItem((Element)localNodeList3.item(i));
	      localArrayList.add(localPriceItem);
    	 System.out.println(i);
	      i++;
      }
    while (i <= j);
    */
     paramBookSearch.setSearchResults(localBookInfo, localArrayList);
    return true;
   }

  public boolean queryISBN(BookSearch paramBookSearch, String paramString)
  {
    String str = paramString.trim().replaceAll("[- ]", "");
    System.out.println( URL + "searchBy=isbn&searchVal=" + str + "&func=Submit&src=andapp");
    
    return queryExact(paramBookSearch, URL + "searchBy=isbn&searchVal=" + str + "&func=Submit&src=andapp");
  }
  
 

  public void searchBy(final BookSearch paramBookSearch, final Activity paramActivity, final ServerType Servertype, final SearchType paramSearchType, final String paramString, final QueryResults paramQueryResults)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)){
    	Toast.makeText( paramActivity, "no agarra el texto", 0).show();
      return;
    }
  
   pd = ProgressDialog.show(paramActivity, "", "Searching for books...");
   
   final Handler localHandler = new Handler();
   System.out.println("localhandler");
    
    new Thread(new Runnable()
    {
      public void run()
      {
    	 Query localQuery = new Query();
        
        if (!localQuery.query(paramBookSearch,Servertype, paramSearchType, paramString))	{
          paramQueryResults.setMessage(localQuery.getErrorMessage());
        }
        else {
        	paramBookSearch.setSearchBy(paramSearchType);
          paramBookSearch.setSearchValue(paramString);
          paramBookSearch.saveQuery(paramSearchType, paramString);
        }
        pd.dismiss();
        localHandler.post(paramQueryResults);
      }
    }).start();
    return;
  }

  public void searchByISBN(final BookSearch paramBookSearch, Activity paramActivity, final String paramString, final QueryResults paramQueryResults)
  {
	  pd = ProgressDialog.show(paramActivity, "", "Searching book...");
	  
	  final Handler localHandler = new Handler();
	  new Thread(new Runnable()	{
		    	public void run()	{ 
		        Query localQuery = new Query();
			        if (!localQuery.queryISBN(paramBookSearch, paramString))
			        {
			          paramQueryResults.setMessage(localQuery.getErrorMessage());
			          System.out.println("no local query"+localQuery.getErrorMessage());
			        }
			        else
			        {
			        	paramBookSearch.setSearchBy(SearchType.isbn);
			        	paramBookSearch.setSearchValue(paramString);
			        	paramBookSearch.saveQuery(SearchType.isbn, paramString);
			        }
		      pd.dismiss();
		      localHandler.post(paramQueryResults);
		    	}
		    }).start();
	  return;
  }
}
