package com.example.doctorpatient;



import com.example.database.DbHandler;
import com.example.database.Doctordb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Doctor extends Activity{
	EditText docName,timeEdit, dateEdit;
	Button btnBook;
	DbHandler dbHandler;
   Doctordb newdoctor;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.doctor);
    	dbHandler = new DbHandler(getApplicationContext());

		
    	
		docName = (EditText) findViewById(R.id.doctName);
		dateEdit = (EditText) findViewById(R.id.dateEdit);
		timeEdit = (EditText) findViewById(R.id.timeEdit);
		btnBook = (Button) findViewById(R.id.btnBook);
		
		btnBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String doctorName = docName.getText().toString();
				String date = dateEdit.getText().toString();
				String time = timeEdit.getText().toString();
				newdoctor = new Doctordb (doctorName,date,time);
				dbHandler.addDoctor(newdoctor);
				dbHandler.close();
				startActivity(new Intent(Doctor.this,detail.class));
			}
		});
}
}