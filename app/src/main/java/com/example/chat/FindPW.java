package com.example.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FindPW extends AppCompatActivity {

    Button btn;
    EditText idet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pw);

        idet = findViewById(R.id.input_id);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public  void  onClick(View v1){
                try {
                    String result;
                    String id = idet.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPW.this);


                    if(id.length()==0){
                      //      Toast.makeText(getApplicationContext(),"빈칸을 채우세요.",Toast.LENGTH_SHORT).show();
                        builder.setTitle("알 림").setMessage(" ID를 입력하세요. ").setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog alertDialog = builder.create();

                        alertDialog.show();
                            return ;
                    }

                    FindPWActivity task = new FindPWActivity();

                    result = task.execute(id).get();
                    if(result.equals("pw찾기 실패")){
                        builder.setTitle("알 림").setMessage("비밀번호 찾기 실패했습니다. \nID를 확인하세요. ").setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog alertDialog = builder.create();

                        alertDialog.show();
                        return ;


                    }
                   // Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
                    builder.setTitle("PW").setMessage("비밀번호는 "+ result+" 입니다").setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }

        });

    }

}
