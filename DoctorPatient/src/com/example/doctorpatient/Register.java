package com.example.doctorpatient;

import com.example.database.DbHandler;
import com.example.database.User;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;




public class Register extends Activity {

	Spinner cloudType;
	EditText txtUName, txtPass, txtCPass, txtEmail, txtPhone;
	Button btnRegister, btnClear;
	DbHandler dbHandler;

	User newUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHandler = new DbHandler(getApplicationContext());

		
	
		txtUName = (EditText) findViewById(R.id.txtusername);
		txtPass = (EditText) findViewById(R.id.txtpassword);
		txtCPass = (EditText) findViewById(R.id.txtcpassword);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPhone = (EditText) findViewById(R.id.txtmobileno);
		btnRegister = (Button) findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					dbHandler.addUser(newUser);
					dbHandler.close();
					startActivity(new Intent(Register.this,Login.class));
				}
			}
		});
		btnClear = (Button) findViewById(R.id.btnClear);
		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearFields();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	private boolean validate() {
		boolean success = true;
		clearErr();
		View focusView = null;

		
		String UName = txtUName.getText().toString();
		String Pass = txtPass.getText().toString();
		String CPass = txtCPass.getText().toString();
		String Email = txtEmail.getText().toString();
		String Phone = txtPhone.getText().toString();

		
		
		
		
		if (TextUtils.isEmpty(UName)) {
			txtUName.setError(getString(R.string.error_field_required));
			focusView = txtUName;
			success = false;
		}

		
		if (TextUtils.isEmpty(Pass)) {
			txtPass.setError(getString(R.string.error_field_required));
			focusView = txtPass;
			success = false;
		} else if (Pass.length() < 6) {
			txtPass.setError(getString(R.string.error_invalid_password));
			focusView = txtPass;
			success = false;
		}

		
		if (TextUtils.isEmpty(CPass)) {
			txtCPass.setError(getString(R.string.error_field_required));
			focusView = txtCPass;
			success = false;
		} else if (!CPass.equals(Pass)) {
			txtCPass.setError(getString(R.string.error_do_not_match));
			focusView = txtCPass;
			success = false;
		}

		
		if (TextUtils.isEmpty(Email)) {
			txtEmail.setError(getString(R.string.error_field_required));
			focusView = txtEmail;
			success = false;
		} else if (!Email.contains("@")) {
			txtEmail.setError(getString(R.string.error_invalid_email));
			focusView = txtEmail;
			success = false;
		}

		
		if (TextUtils.isEmpty(Phone)) {
			txtPhone.setError(getString(R.string.error_field_required));
			focusView = txtPhone;
			success = false;
		} else if (Phone.length() < 10) {
			txtPhone.setError(getString(R.string.error_invalid_phone));
			focusView = txtPhone;
			success = false;
		}

		if (!success) {
			focusView.requestFocus();
			success = false;
		} else {
			success = true;
			
			newUser = new User( UName, Pass, Email, Phone);
		}
		return success;
	}

	private void clearFields() {
		
		clearErr();
		txtUName.setText("");
		txtPass.setText("");
		txtCPass.setText("");
		txtEmail.setText("");
		txtPhone.setText("");
		txtUName.requestFocus();
	}

	private void clearErr() {
		
		
		txtUName.setError(null);
		txtPass.setError(null);
		txtCPass.setError(null);
		txtEmail.setError(null);
		txtPhone.setError(null);
	}

}
