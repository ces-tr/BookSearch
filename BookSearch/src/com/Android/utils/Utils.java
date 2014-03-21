package com.Android.utils;

import java.net.URLEncoder;

public class Utils
{
  public static String clean(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString.trim(), "UTF-8");
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString.trim().replaceAll("\\p{Punct}", "").replaceAll("[ ]", "+");
  }
}

/* Location:           C:\Documents and Settings\CYGNUX01\Mis documentos\Descargas\booksearch\pkg-dex2jar.jar
 * Qualified Name:     com.campusbooks4less.android.utils.Utils
 * JD-Core Version:    0.6.2
 */