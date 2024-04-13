package com.lammyngoc.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lammyngoc.firebase.ListProductActivity;
import com.lammyngoc.firebase.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;


public class LoginActivity extends AppCompatActivity {

    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    CheckBox chkSaveInfo;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    String Key_Preferences = "LOGIN_PREFERENCE";
    private DatabaseReference accountsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addViews();
        addEvents();
        initializeFirebase();
    }
    private void addViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        txtMessage = findViewById(R.id.txtMessage);
        chkSaveInfo = findViewById(R.id.chkSaveInfo);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private void initializeFirebase() {
        // Initialize Firebase reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        accountsRef = database.getReference("accounts");
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = edtUserName.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                if (!userName.isEmpty() && !password.isEmpty()) {
                    accountsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean userFound = false;
                            for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                                String firebaseUserName = accountSnapshot.child("userName").getValue(String.class);
                                if (userName.equals(firebaseUserName)) {
                                    userFound = true;
                                    // Correctly retrieve the password as a Long then convert to String
                                    Long firebasePasswordLong = accountSnapshot.child("password").getValue(Long.class);
                                    String firebasePassword = (firebasePasswordLong != null) ? firebasePasswordLong.toString() : null;
                                    if (firebasePassword != null && firebasePassword.equals(password)) {
                                        // Login successful
                                        Intent intent = new Intent(LoginActivity.this, ListProductActivity.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    } else {
                                        txtMessage.setText("Incorrect password. Please try again.");
                                        break;
                                    }
                                }
                            }
                            if (!userFound) {
                                txtMessage.setText("User not found. Please register.");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "Error checking user database", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    txtMessage.setText("Please enter both username and password.");
                }
            }
        });
    }
}