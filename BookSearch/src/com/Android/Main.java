package com.Android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Main extends Activity
{
  public static final String TAG = "Main";
  private String array_spinner[];
  
  public void aboutUs(View paramView)
  {
    //startActivity(new Intent(this, About.class));
  }

  public void listBookstores(View paramView)
  {
	  /*
    final BookSearch localBookSearch = (BookSearch)getApplication();
    final Handler localHandler = new Handler();
    new Thread(new Runnable()
    {
      public void run()
      {
        Query localQuery = new Query();
        if (!localQuery.queryBookstores(localBookSearch))
        {
          this.val$qr.setMessage(localQuery.getErrorMessage());
          
          Log.d("Main", "Error Message -- " + localQuery.getErrorMessage());
        }
        this.val$pd.dismiss();
        localHandler.post(this.val$qr);
      }
    }).start();
    */
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.main);
   
    setTitle("BookSearch");
    
    
  }

  protected void onStart()
  {
    super.onStart();
   
  }

  protected void onStop()
  {
    super.onStop();
    
  }

  public void showSearch(View paramView)
  {
   startActivity(new Intent(this, SearchActivity.class));
  }
}

