package com.Android;

import android.app.Application;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.Android.domain.Book;
import com.Android.domain.BookInfo;
import com.Android.domain.Bookstore;
import com.Android.domain.PriceItem;
import com.Android.enums.SearchType;


public class BookSearch extends Application
{
  private static final String TAG = "BookSearch";
  private BookInfo _bookInfo;
  private List<Book> _books;
  private List<Bookstore> _bookstores;
  private Map<SearchType, List<String>> _history = new HashMap();
  private List<PriceItem> _prices;
  private SearchType _searchBy;
  private String _searchValue;
  private Bookstore _selected;
  private String _url;

  public BookSearch()
  {
    SearchType[] arrayOfSearchType = SearchType.values();
    int i = arrayOfSearchType.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      SearchType localSearchType = arrayOfSearchType[j];
      this._history.put(localSearchType, new ArrayList());
    }
  }

  public BookInfo getBookInfo()
  {
    return this._bookInfo;
  }

  public List<Book> getBooks()
  {
    return this._books;
  }

  public List<Bookstore> getBookstores()
  {
    return this._bookstores;
  }

  public List<String> getHistory(SearchType paramSearchType)
  {
    return (List)this._history.get(paramSearchType);
  }

  public List<PriceItem> getPrices()
  {
    return this._prices;
  }

  public SearchType getSearchBy()
  {
    return this._searchBy;
  }

  public String getSearchValue()
  {
    return this._searchValue;
  }

  public Bookstore getSelectedBookstore()
  {
    return this._selected;
  }

  public String getURL()
  {
    return this._url;
  }

  public int loadHistory()
  {
    int i = 0;
    SearchType[] arrayOfSearchType = SearchType.values();
    int j = arrayOfSearchType.length;
    int k = 0;
    while (true)
    {
      if (k >= j)
        return i;
      SearchType localSearchType = arrayOfSearchType[k];
      ArrayList<String> localArrayList = new ArrayList<String>();
      try
      {
        FileInputStream localFileInputStream = openFileInput("history-" + localSearchType + ".txt");
        BufferedReader localBufferedReader = null;
        if (localFileInputStream != null)
          localBufferedReader = new BufferedReader(new InputStreamReader(localFileInputStream));
        while (true)
        {
          String str = localBufferedReader.readLine();
          if (str == null)
          {
            localFileInputStream.close();
            this._history.put(localSearchType, localArrayList);
            k++;
            break;
          }
          localArrayList.add(str);
          i++;
        }
      }
      catch (Exception localException)
      {
        //while (true)
          localException.printStackTrace();
      }
    }
  }

  public void removeQuery(SearchType paramSearchType, String paramString)
  {
    ((List<?>)this._history.get(paramSearchType)).remove(paramString);
    writeHistory(paramSearchType);
  }

  public void saveQuery(SearchType paramSearchType, String paramString)
  {
    List localList = (List)this._history.get(paramSearchType);
    localList.remove(paramString);
    localList.add(0, paramString);
    while (true)
    {
      if (localList.size() <= 5)
      {
        writeHistory(paramSearchType);
        return;
      }
      localList.remove(localList.size() - 1);
    }
  }

  public void setBookstores(List<Bookstore> paramList)
  {
    this._bookstores = paramList;
  }

  public void setSearchBy(SearchType paramSearchType)
  {
    this._searchBy = paramSearchType;
  }

  public void setSearchResults(BookInfo paramBookInfo, List<PriceItem> paramList)
  {
    this._bookInfo = paramBookInfo;
    this._prices = paramList;
  }

  public void setSearchResults(List<Book> paramList)
  {
    this._books = paramList;
  }

  public void setSearchValue(String paramString)
  {
    this._searchValue = paramString;
  }

  public void setSelectedBookstore(int paramInt)
  {
    this._selected = ((Bookstore)this._bookstores.get(paramInt));
  }

  public void setURL(String paramString)
  {
    this._url = paramString;
  }

  public void writeHistory(SearchType paramSearchType)
  {
    Log.d("BookSearch", "Write history " + paramSearchType);
    try
    {
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(openFileOutput("history-" + paramSearchType + ".txt", 0));
      Iterator localIterator = ((List)this._history.get(paramSearchType)).iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          localOutputStreamWriter.close();
          return;
        }
        String str = (String)localIterator.next();
        Log.d("BookSearch", "  value " + str);
        localOutputStreamWriter.write(str + "\n");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}
