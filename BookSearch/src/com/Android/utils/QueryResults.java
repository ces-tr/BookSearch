package com.Android.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class QueryResults
  implements Runnable
{
  private boolean _error;
  private Activity _from;
  private String _message;
  private Class<?> _to;

  public QueryResults()
  {
  }

  public QueryResults(Activity paramActivity, Class<?> paramClass)
  {
    this._from = paramActivity;
    this._to = paramClass;
  }

  public boolean isError()
  {
    return this._error;
  }

  public void run()
  {
    if (isError())
    {
      showMessage(this._from);
      return;
    }
    Intent localIntent = new Intent(this._from, this._to);
    this._from.startActivity(localIntent);
  }

  public void setMessage(String paramString)
  {
    this._message = paramString;
    this._error = true;
  }

  public void showMessage(Activity paramActivity)
  {
    Toast.makeText(paramActivity, this._message, 1).show();
  }
}

