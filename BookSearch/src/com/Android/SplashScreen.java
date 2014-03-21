package com.Android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class SplashScreen extends Activity
{

    public SplashScreen()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.splash);
        setTitle("BookSearch");
       new Thread() {

            public void run()
            {
                int i = 0;
                do{
				
	                if(i >= 2000)
	                {
	                    finish();
	                    Intent intent2 = new Intent(SplashScreen.this, Main.class);
	                    startActivity(intent2);
	                    return;
	                }
	                try
	                {
	                    sleep(1000);
	                }
	                catch(InterruptedException interruptedexception)
	                {
	                    finish();
	                    Intent intent1 = new Intent(SplashScreen.this,Main.class);
	                    startActivity(intent1);
	                    return;
	                }
	                i += 1000;
	            }
                while(true);
            }
       	}
.start();
    }

    protected void onStart()
    {
        super.onStart();
        
    }

    protected void onStop()
    {
        super.onStop();
       
    }
}
