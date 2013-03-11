package com.PartyShake;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import connect.Connection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PartyShakeActivity extends Activity {
	Socket socket = null;
	Connection cnn = null;
	TextView UserName = null;
	TextView ServerIp = null;
	Client c = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		Button Login = (Button) findViewById(R.id.Login_LoginButton);
		UserName = (TextView) findViewById(R.id.Login_UserId);
		ServerIp = (TextView) findViewById(R.id.Login_ServerIp);
		Login.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//cnn.send(text.getText().toString());
				cnn = new Connection(ServerIp.getText().toString());
				try {
					Log.i("TCP", "Begin Socket Init");
					cnn.init();
					Log.i("TCP", "Socket Init succeed");
					c=new Client(UserName.getText().toString(),cnn);
				} catch (IOException e) {
					e.printStackTrace();
					Log.i("TCP", "Socket Init Failed");
				}
				Log.i("TCP", "Begin Login with"+UserName.getText().toString()+"to"+ServerIp.getText().toString());
				if(cnn.login(UserName.getText().toString())){
				    Log.i("TCP", "login send succeed");
				}else{
					Log.i("TCP", "login send failed");	
				}
				//Position party_position=new Position(1.0,1.0,"hello world");
				//Date party_date=new Date(10000000000000l);
				//cnn.search_party(party_position);
				//cnn.party_setup("hofer_lu", "hofer_party", party_date, 3, party_position);
			}

		});
	}
}