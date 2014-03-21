package com.Android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.Android.enums.SearchType;
import com.Android.enums.ServerType;
import com.Android.utils.Query;
import com.Android.utils.QueryResults;


public class SearchActivity extends Activity
{
  public static final int MENU_HISTORY = 2;
  private boolean _history = false;
  private MenuItem _historyMI = null;
  private ServerType server;
  
  public void doSearch(SearchType paramSearchType, String paramString)
  {
	  System.out.println(((Spinner)findViewById(R.id.spinner1)).getSelectedItem().toString());
	  
	  if(((Spinner)findViewById(R.id.spinner1)).getSelectedItem().toString() == "GoogleBooks"){
		  
		  server= ServerType.Googlebooks;
	  
	  }else{
		  
		  server= ServerType.Campusbooks;
	  }
	  
    BookSearch localBookSearch = (BookSearch)getApplication();
    new Query()
    	.searchBy(localBookSearch, this, server, paramSearchType, paramString, new QueryResults(this, SearchResults.class));
    
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.search);
    setTitle("Find Book");
    String[] Items = {
    		"GoogleBooks",
            "CampusBooks"
            };
    
    Spinner s = (Spinner) findViewById(R.id.spinner1);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    android.R.layout.simple_spinner_item, Items);
	s.setAdapter(adapter);
	adapter.notifyDataSetChanged();
    //if (((BookSearch)getApplication()).loadHistory() > 0)
    //  this._history = true;
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    this._historyMI = paramMenu.add(0, 2, 0, "History");
    //this._historyMI.setIcon(2130837506);
    this._historyMI.setEnabled(this._history);
    return super.onCreateOptionsMenu(paramMenu);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 2:
    }
    //startActivity(new Intent(this, History.class));
    return true;
  }

  public void onResume()
  {
    super.onResume();
    //if (((BookSearch)getApplication()).loadHistory() > 0)
    //  this._history = true;
    //((ImageButton)findViewById(2131230770)).setEnabled(this._history);
    //if (this._historyMI != null)
    //  this._historyMI.setEnabled(this._history);
  }

  protected void onStart()
  {
    super.onStart();
    
  }

  protected void onStop()
  {
    super.onStop();
    
  }

  public void searchByAuthor(View paramView)
  {
    String str = ((EditText)findViewById(R.id.authorEditText)).getText().toString();
    if ((str == null) || (str.trim().length() == 0))
    {
      Toast.makeText(getApplicationContext(), "No author entered", 0).show();
      return;
    }
    doSearch(SearchType.author, str);
  }

  public void searchByISBN(View paramView)
  {
    String str1 = ((EditText)findViewById(R.id.isbnEditText)).getText().toString();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      Toast.makeText(getApplicationContext(), "No ISBN entered", 0).show();
      return;
    }
    String str2 = str1.trim();
    if ((str2.length() != 10) && (str2.length() != 13))
    {
      Toast.makeText(getApplicationContext(), "Invalid ISBN length", 0).show();
      return;
    }
    if (!str2.matches("\\d*[x]?"))
    {
      Toast.makeText(getApplicationContext(), "Invalid ISBN", 0).show();
      return;
    }
   new Query().searchByISBN((BookSearch)getApplication(), this, str2, new QueryResults(this, DisplayPrices.class));
  }

  public void searchByKeyword(View paramView)
  {
    String str = ((EditText)findViewById(R.id.keywordEditText)).getText().toString();
    if ((str == null) || (str.trim().length() == 0))
    {
      Toast.makeText(getApplicationContext(), "No keyword entered", 0).show();
      return;
    }
    doSearch(SearchType.keyword, str);
     
  }

  public void searchByTitle(View paramView)
  {
    String str = ((EditText)findViewById(R.id.titleEditText)).getText().toString();
    
    if ((str == null) || (str.trim().length() == 0)) {
      Toast.makeText(getApplicationContext(), "No title entered", 0).show();
      return;
    }
    
   doSearch(SearchType.title, str);
   
  }

  public void showHistory(View paramView)
  {
    //startActivity(new Intent(this, History.class));
  }
  
  
}

