package com.Android.utils;

import android.graphics.drawable.Drawable;
import java.io.InputStream;
import java.net.URL;

public class Image
{
  public static Drawable loadImage(String paramString)
  {
    try
    {
      Drawable localDrawable = Drawable.createFromStream((InputStream)new URL(paramString).getContent(), "src name");
      return localDrawable;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}
