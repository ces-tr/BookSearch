package com.Android;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.Android.domain.Book;
import com.Android.utils.Query;
import com.Android.utils.QueryResults;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SearchResults extends ListActivity
{
  public static final int MENU_EMAIL = 2;
  private static final String TAG = "SearchResults";
  private List<Book> _books;
  private String[] _titles;

  public void emailResults()
  {
    final EditText localEditText = new EditText(this);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Email Results").setMessage("Email the search results to...").setView(localEditText).setCancelable(true).setPositiveButton("Send", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        BookSearch localBookSearch = (BookSearch)SearchResults.this.getApplication();
        String str1 = localEditText.getText().toString();
        String str2 = new StringBuilder(String.valueOf(SearchResults.this.getString(R.string.base_url))).append("booksearch-email-script/send.php?").toString() + "searchBy=" + localBookSearch.getSearchBy() + "&searchVal=" + localBookSearch.getSearchValue() + "&email=" + str1;
        Log.d("SearchResults", "Sending: " + str2);
        
        try
        {
          HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str2).openConnection();
          localHttpURLConnection.setReadTimeout(10000);
          localHttpURLConnection.setConnectTimeout(15000);
          localHttpURLConnection.setRequestMethod("GET");
          localHttpURLConnection.setDoInput(true);
          localHttpURLConnection.addRequestProperty("Referer", "http://blog.dahanne.net");
          localHttpURLConnection.connect();
          localHttpURLConnection.getInputStream();
          Toast.makeText(SearchResults.this, "Email Sent", 0).show();
          return;
        }
        catch (IOException localIOException)
        {
          //while (true)
            localIOException.printStackTrace();
        }
      }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.create().show();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    
    requestWindowFeature(Window.FEATURE_PROGRESS);
    //setContentView(R.layout.books);
    
    setTitle("Search Results");
    this._books = ((BookSearch)getApplication()).getBooks(); //Retorna instancia de Booksearch en ejecucion
    this._titles = new String[this._books.size()];
    for (int i = 0; ; i++)
    {
      if (i >= this._books.size())
      {
        setListAdapter(new BookAdapter());
        return;
      }
      this._titles[i] = this._books.get(i).getTitle();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(0, 2, 0, "Email").setIcon(17301545);
    return super.onCreateOptionsMenu(paramMenu);
  }

  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    Book localBook = this._books.get(paramInt);
    Log.d("SearchResults", localBook.getTitle() + " - " + localBook.getAuthors() + " - " + localBook.getFormat() + " - " + localBook.getISBN());
    BookSearch localBookSearch = (BookSearch)getApplication();
    
    new Query().searchByISBN(localBookSearch, this, localBook.getISBN(), new QueryResults(this, DisplayPrices.class));
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
	  /*switch (paramMenuItem.getItemId())
    {
    default:
    case 2:
    return super.onOptionsItemSelected(paramMenuItem);
    }
    emailResults();
    return true;
    */
    if(paramMenuItem.getItemId() == 2){
    	emailResults();
        return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }

  protected void onStart()
  {
    super.onStart();
    
  }

  protected void onStop()
  {
    super.onStop();
    
  }

  class BookAdapter extends ArrayAdapter<String>
  {
    BookAdapter()
    {
      super(SearchResults.this, R.layout.bookdetails, R.id.booktitle, SearchResults.this._titles);
    }

   public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
	      View localView = super.getView(paramInt, paramView, paramViewGroup);
	      ((TextView)localView.findViewById(R.id.author)).setText(((Book)SearchResults.this._books.get(paramInt)).getAuthors());
	      //((TextView)localView.findViewById(R.id.format)).setText("Formato: " + ((Book)SearchResults.this._books.get(paramInt)).getFormat());
	      //((TextView)localView.findViewById(R.id.pubdate)).setText("Fec Pub: " + ((Book)SearchResults.this._books.get(paramInt)).getPublishDate());
	      ((ProgressBar)localView.findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
      return localView;
    }
  }
}
