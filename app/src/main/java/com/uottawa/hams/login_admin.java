package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class login_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        TextView username = (TextView) findViewById(R.id.p_admin_username);
        TextView password = (TextView) findViewById(R.id.p_admin_password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.b_admin_login);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username != null && password != null &&
                        username.getText() != null && password.getText() != null &&
                        username.getText().toString().equals("admin333") && password.getText().toString().equals("hamsadmin123")) {

                    loginbtn.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Intent intent = new Intent (login_admin.this, welcome_administrator.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(login_admin.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}
