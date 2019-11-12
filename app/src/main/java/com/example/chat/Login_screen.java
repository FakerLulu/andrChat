package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_screen extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Home");


        Button button1 = (Button) findViewById(R.id.loginButton);


        Button button = (Button) findViewById(R.id.signupButton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public  void  onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Login_screen_sub.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public  void  onClick(View v1){
//                Intent intent1 = new Intent(getApplicationContext(), Chatting.class);
//                startActivity(intent1);
                EditText idet = (EditText) findViewById(R.id.idInput);
                EditText pwet = (EditText) findViewById(R.id.passwordInput);




                try {
                    String result;
                    String id = idet.getText().toString();
                    String pw = pwet.getText().toString();

                    if(pw.length()==0||id.length()==0){
                        Toast.makeText(getApplicationContext(),"빈칸을 채우세요.",Toast.LENGTH_SHORT).show();

                        return ;

                    }

                    LoginActivity task = new LoginActivity();
                    result = task.execute(id, pw).get();
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                    if(result.contains("로그인이 성공되었습니다.")){
                        Ccc.setMyName(result.split(":")[0]);
                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class) ;
                        startActivity(intent) ;
                    }

                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });
}
}
