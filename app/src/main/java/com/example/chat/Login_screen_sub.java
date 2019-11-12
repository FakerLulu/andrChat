package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_screen_sub extends AppCompatActivity {




        Button registerBtn;
        EditText idet, pwet, name;
  ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_screen_sub);

        setTitle("회원가입");

            registerBtn = (Button)findViewById(R.id.register_btn);
            idet = (EditText)findViewById(R.id.register_id);
            pwet = (EditText)findViewById(R.id.register_pw);
            name = (EditText)findViewById(R.id.register_name);

            registerBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        String result;
                        String id = idet.getText().toString();
                        String pw = pwet.getText().toString();
                        String nm = name.getText().toString();
                        if(nm.length()==0||pw.length()==0||id.length()==0){
                            Toast.makeText(getApplicationContext(),"빈칸을 채우세요.",Toast.LENGTH_SHORT).show();

                            return ;

                        }

                        RegisterActivity task = new RegisterActivity();
                        result = task.execute(id, pw, nm).get();
                        if(result.equals("회원 가입 성공!")){
                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login_screen.class) ;
                            startActivity(intent) ;

                        }else{

                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        }


                  //      "회원 가입 성공!" "실패" "이미 존재하는 아이디 입니다."

                    } catch (Exception e) {
                        Log.i("DBtest", ".....ERROR.....!");
                    }
                }
            });


    }
}
