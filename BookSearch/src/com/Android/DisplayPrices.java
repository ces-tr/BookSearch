package com.Android;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.Android.domain.BookInfo;
import com.Android.domain.PriceItem;
import com.Android.utils.Image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DisplayPrices extends ListActivity
{
  public static final int MENU_EMAIL = 2;
  public static final String TAG = "DisplayPrices";
  private BookInfo _book;
  private List<PriceItem> _prices;
  private String[] _stores;

  public void emailResults()
  {
    final EditText localEditText = new EditText(this);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Email Results").setMessage("Email the search results to...").setView(localEditText).setCancelable(true).setPositiveButton("Send", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        
        BookSearch _tmp = (BookSearch)getApplication();

        String str1 = localEditText.getText().toString();
        String str2 = new StringBuilder(String.valueOf(DisplayPrices.this.getString(R.string.base_url))).append("booksearch-email-script/send.php?").toString() + "searchBy=isbn&searchVal=" + DisplayPrices.this._book.getISBN() + "&email=" + str1;
        Log.d("DisplayPrices", "Sending: " + str2);
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
          Toast.makeText(DisplayPrices.this, "Email Sent", 0).show();
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
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
    setContentView(R.layout.prices);
    setTitle("Book");
    BookSearch localBookSearch = (BookSearch)this.getApplication();
    this._book = localBookSearch.getBookInfo();
    this._prices = localBookSearch.getPrices();
    this._stores = new String[this._prices.size()];
    
    final ImageView localImageView;
    
    for(int var3 = 0; var3 < this._prices.size(); ++var3) {
        this._stores[var3] = ((PriceItem)this._prices.get(var3)).getStoreName();
     }
   
    Log.d("DisplayPrices", this._book.getTitle());
    Log.d("DisplayPrices", this._book.getImageURL());
    System.out.println(this._prices.size());
    
    ((TextView)findViewById(R.id.booktitle)).setText(this._book.getTitle());
    ((TextView)findViewById(R.id.author)).setText(this._book.getAuthors());
    ((TextView)findViewById(R.id.isbn)).setText("ISBN: " + this._book.getISBN());
    ((TextView)findViewById(R.id.format)).setText("Format: " + this._book.getFormat());
    
    localImageView = (ImageView)findViewById(R.id.bookcover);
    Bitmap bitmap;
    if("Uknown".equals(_book.getImageURL()))
    {
  	  localImageView.setImageResource(R.drawable.unavailable);
  	 
    }
    else{
    	 
    	final Handler handler = new Handler();

 	    	new Thread(new Runnable() {
 	        public void run() {

 	            URL url = null; 
 	            InputStream content = null;
 	            try { 
 	                url = new URL(_book.getImageURL()); 

 	                content = (InputStream)url.getContent();
 	                Drawable d = Drawable.createFromStream(content , "src");  
 	                final Bitmap mIcon1 = BitmapFactory.decodeStream(url.openConnection().getInputStream()); 

 	                handler.post(new Runnable() {

 	                    public void run() {
 	                        //here you can do everything in UI thread, like put the icon in a imageVew
 	                    	localImageView.setImageBitmap(mIcon1); 
 	                    }
 	                });

 	           } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               
 	                handler.post(new Runnable() {

 	                    public void run() {
 	                        //Toast.makeText(YourActivityName.this, e.getMessage(), Toast.LENGTH_LONG);
 	                    }
 	                });
 	            } 


 	        }
 	    })
 	.start();
	  	  
    }
    this.setListAdapter(new PriceAdapter());
}
  /*private Drawable grabImageFromUrl(String url) throws Exception {
      return Drawable.createFromStream((InputStream)new URL(url).getContent(), "src");
  }*/
  
  public void getimage(){
	  
	}

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(0, 2, 0, "Email").setIcon(17301545);
    return super.onCreateOptionsMenu(paramMenu);
  }

  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    PriceItem localPriceItem = (PriceItem)this._prices.get(paramInt);
    Log.d("DisplayPrices", localPriceItem.toString());
    ((BookSearch)getApplication()).setURL(localPriceItem.getBuyURL());
    //startActivity(new Intent(this, Browser.class));
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 2:
    }
    emailResults();
    return true;
  }

  protected void onStart()
  {
    super.onStart();
    
  }

  protected void onStop()
  {
    super.onStop();
   
  }

  class PriceAdapter extends ArrayAdapter<String>
  {
    PriceAdapter()
    {
      super(DisplayPrices.this,R.layout.pricedetails, R.id.store, DisplayPrices.this._stores);
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = super.getView(paramInt, paramView, paramViewGroup);
      PriceItem localPriceItem = (PriceItem)DisplayPrices.this._prices.get(paramInt);
      ((TextView)localView.findViewById(R.id.shipping)).setText(localPriceItem.getShippingDesc());
      ((TextView)localView.findViewById(R.id.totalPrice)).setText("$" + localPriceItem.getFinalPrice());
      
      return localView;
    }
  }
}
