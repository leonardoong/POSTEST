package com.example.android.postest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.User;
import com.example.android.postest.Validation.InputValidation;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private SQLite databaseHelper;

    ArrayList<User> userList;
    User user;
    String username, dbEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new SQLite(this);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();

                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Isi DUlud emailnya")) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "harus email")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "isi dulu passwordnya")) {
            return;
        }
        String email = textInputEditTextEmail.getText().toString().trim();
        String password= textInputEditTextPassword.getText().toString().trim();
        if (databaseHelper.checkUser(email, password)) {
//            Cursor c = databaseHelper.getUsername(email);
//            if(c.getCount()>0)
//            {
//                c.moveToPosition(0);
//                String username = c.getString(c.getColumnIndex("user_name"));
//                SharedPreferences.Editor prefs = getSharedPreferences("userSession", MODE_PRIVATE).edit();
//                prefs.putString("username", username);
//                prefs.apply();
//            }
//            databaseHelper.close();
            userList = databaseHelper.getAllUser();
            for (int i = 0; i < userList.size(); i++ ){
                user = userList.get(i);
                dbEmail = user.getEmail();
                if(dbEmail.equals(email)){
                    user = userList.get(i);
                    username = user.getName();
                }
            }
            SharedPreferences.Editor prefs = getSharedPreferences("userSession", MODE_PRIVATE).edit();
                prefs.putString("username", username);
                prefs.apply();

            Intent accountsIntent = new Intent(activity, MainActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, "password / email salah", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}