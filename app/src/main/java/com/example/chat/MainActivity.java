package com.example.chat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    Ccc ccc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ccc = new Ccc(MainActivity.this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
//참조 : http://naminsik.com/blog/3662
        final TextView textView = findViewById(R.id.textView11);
        final TextInputEditText extView = findViewById(R.id.textInputEditText23);
        final TextInputEditText iptextv = findViewById(R.id.ipEdit);
        Button btn = findViewById(R.id.sendButton);
        Button ipbtn = findViewById(R.id.ipButton);
        Button imgPick = findViewById(R.id.pickImage);

        textView.setMovementMethod(new ScrollingMovementMethod());


imgPick.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 200);
    }
});

        ipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = iptextv.getText().toString();
                if(ip.matches("^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$")){
                    ccc.start(ip);
                }else{
                    String tm = "ip 입력 다시하세요";
                    Toast.makeText(MainActivity.this,tm,Toast.LENGTH_SHORT).show();;
                }

            }
        });

        textView.post(() -> {
            int lineTop = textView.getLayout().getLineTop(textView.getLineCount());
            int scrollY = lineTop - textView.getHeight();
            if (scrollY > 0) {
                textView.scrollTo(0, scrollY);
            } else {
                textView.scrollTo(0, 0);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String aa = textView.getText().toString() + "\n" + extView.getText();
                // textView.setText(aa);


                ccc.send(extView.getText().toString());
                extView.setText("");
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri photoUri = data.getData();
            Cursor cursor = null;
            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                File tempFile = new File(cursor.getString(column_index));
                ccc.sendPhoto(tempFile);

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }

    }
}
