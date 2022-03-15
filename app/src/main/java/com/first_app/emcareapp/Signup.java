package com.first_app.emcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button backToLogin, regBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)       {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        // Hooks to all xml elements in signup page
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        backToLogin = findViewById(R.id.loginback);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this,Login.class);
                startActivity(i);
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("emcareapp-10268-default-rtdb");

            }
        });//Register Button method end
    }//onCreate Method

    //Save data in FireBase on button click
    public void registerUser(View view){

        if (!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword()){
            return;
        }

        //Get all the values
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
        reference.child(username).setValue(helperClass);

    }

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

        }

    private Boolean validateUsername(){
        String val = regName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w(4,20)\\z";
        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=15){
            regName.setError("Username too Long");
            return false;
        }
        else if(!val.matches(noWhiteSpace)){
            regName.setError("White spaces are not allowed");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regName.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)){
            regName.setError("Invalid email address");
            return false;
        }
        else {
            regName.setError(null);
            return true;
        }

    }

    private Boolean validatePhoneNo(){
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\s+$)" +           //no white space
                ".(4,)" +               //at least 4 characters
                "$";

        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(passwordVal)){
            regName.setError("Password is too weak");
            return false;
        }
        else {
            regName.setError(null);
            return true;
        }

    }


}