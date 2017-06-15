package com.limayeneha.twitter_trov.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.limayeneha.twitter_trov.R;
import com.limayeneha.twitter_trov.model.User;

public class LoginActivity extends AppCompatActivity {
    EditText idEt;
    EditText pwdEt;
    String username = "";
    String pwd = "";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLogin = sharedPref.getBoolean(getResources().getString(R.string.isLogin), false);
        if(isLogin) {
            Intent intent = new Intent(LoginActivity.this, TweetsListActivity.class);
            intent.putExtra(getResources().getString(R.string.user), new User(1234, getResources().getString(R.string.username)));
            startActivity(intent);
            finish();
        }

        idEt = (EditText) findViewById(R.id.idText);
        pwdEt = (EditText) findViewById(R.id.pwdText);

        Button loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(idEt!=null) username = idEt.getText().toString();
                if(pwdEt!=null) pwd = pwdEt.getText().toString();
                if(username.equals(getResources().getString(R.string.username)) && pwd.equals(getResources().getString(R.string.password))) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(getResources().getString(R.string.isLogin), true);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, TweetsListActivity.class);
                    intent.putExtra(getResources().getString(R.string.user), new User(1234, getResources().getString(R.string.username)));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_user) , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
