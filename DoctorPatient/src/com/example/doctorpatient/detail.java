package com.example.doctorpatient;

import com.example.database.DbHandler;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
public class detail extends Activity{
	
	Context ctx=this;
	TextView doctName,dateText,timeText;
	 Button btnShow;
	 DbHandler dbHandler;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.detail);
    	
    	dbHandler = new DbHandler(getApplicationContext());
    	
    	doctName = (TextView) findViewById(R.id.doctName);
    	dateText = (TextView) findViewById(R.id.dateText);
    	timeText =  (TextView) findViewById(R.id.timeText);
    	btnShow = (Button) findViewById(R.id.btnShow);
    	btnShow.setOnClickListener(new OnClickListener() {
    		String docName="";
	    	String doDate="";
	    	String doTime="";
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DbHandler Dop = new DbHandler(ctx);
		    	
				Cursor CR = Dop.getInformation(Dop);
		    	CR.moveToFirst();
		    	
		    	do
		    	{
		    	docName=CR.getString(0);
		    	doDate= CR.getString(1);
		    	doTime= CR.getString(2);
		    	
		    	}while(CR.moveToNext());
		    	doctName.setText(docName);
		    	dateText.setText(doDate);
		    	timeText.setText(doTime);
			}
		});
    	
    	
	}
	

	
         
	   }

