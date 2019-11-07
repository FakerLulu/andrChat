package com.example.chat;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView11);
        final TextInputEditText extView = findViewById(R.id.textInputEditText23);
        Button btn = findViewById(R.id.sendButton);

        textView.setMovementMethod(new ScrollingMovementMethod());
        Ccc ccc = new Ccc(MainActivity.this);
        ccc.start();


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


}
