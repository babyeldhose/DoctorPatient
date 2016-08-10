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
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	EditText txtUser, txtPass;
	Spinner cloudType;
	TextView txtType;
	DbHandler dbHandler;
	User mUser;
	String user, pass, type, cloud;;
	boolean isUser;
	Button btnLogin, btnRegister, btnClear;
	Intent home, register, admin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		dbHandler = new DbHandler(getApplicationContext());
		

		
		txtUser = (EditText) findViewById(R.id.userName);
		txtPass = (EditText) findViewById(R.id.userPass);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);

		btnClear = (Button) findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this);

		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		btnRegister.setOnClickListener(this);

		home = new Intent(Login.this, Doctor.class);
		
		register = new Intent(Login.this, Register.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			user = txtUser.getText().toString();
			pass = txtPass.getText().toString();
						 if (validate()) {
				if (isUser) {
					home.putExtra("user", user);
					startActivity(home);
				} else {
					Toast.makeText(getApplicationContext(),
							"Invalid Username or password", Toast.LENGTH_SHORT)
							.show();

				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Incorrect Username or Password", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btnRegister:
			startActivity(register);
			break;
		case R.id.btnClear:
			clearFields();
			break;
		}

	}

	private boolean validate() {
		boolean success = true;
		clearErr();
		View focusView = null;

		user = txtUser.getText().toString();
		pass = txtPass.getText().toString();

		// Check Cloud Type
		
		
		// Check for a User name.
		if (TextUtils.isEmpty(user)) {
			txtUser.setError(getString(R.string.error_field_required));
			focusView = txtUser;
			success = false;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(pass)) {
			txtPass.setError(getString(R.string.error_field_required));
			focusView = txtPass;
			success = false;
		} else if (pass.length() < 6) {
			txtPass.setError(getString(R.string.error_invalid_password));
			focusView = txtPass;
			success = false;
		}

		if (!success) {
			focusView.requestFocus();
		} else {
			mUser = dbHandler.getUser(user);
			if (pass.equals(mUser.getPassword())) {
				isUser = true;
			} else {
				isUser = false;
			}
		}
		return success;
	}

	private void clearFields() {
		// Clear Fields
		clearErr();
		
		txtUser.setText("");
		txtPass.setText("");
		txtUser.requestFocus();
	}

	private void clearErr() {
		// Reset errors.
		txtUser.setError(null);
		txtPass.setError(null);
	}

}
