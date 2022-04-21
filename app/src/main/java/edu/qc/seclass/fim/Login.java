package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText usernameEt, pwEt;
    private String inputusername, inputpw;
    private Intent toAdmin, toHome;
    private String username = "admin";
    private String pw = "1234";
    private Button login, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Admin Login");

        usernameEt = findViewById(R.id.username);
        pwEt = findViewById(R.id.pw);

        toAdmin = new Intent(this, Admin.class);
        toAdmin.addFlags(toAdmin.FLAG_ACTIVITY_CLEAR_TOP);
        toHome = new Intent(this, Home.class);
        toHome.addFlags(toHome.FLAG_ACTIVITY_CLEAR_TOP);

        login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            inputusername = usernameEt.getText().toString();
            inputpw = pwEt.getText().toString();

            if(inputusername.equals(username) && inputpw.equals(pw))
                startActivity(toAdmin);
            else if(inputusername.isEmpty() || inputpw.isEmpty())
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            else if(!inputusername.equals(username) || !inputpw.equals(pw))
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            startActivity(toHome);
        });

    }
}