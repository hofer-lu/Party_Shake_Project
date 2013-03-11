package com.example.party_shake_ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Login_bt=(Button)findViewById(R.id.login_bt);
        Login_bt.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0) {
        		Intent it = new Intent(MainActivity.this,main_activity.class);
        		Bundle mBundle=new Bundle();
        		mBundle.putString("type", "ÄãºÃ");
        		it.putExtras(mBundle);
        		startActivity(it);
        	}
        }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
