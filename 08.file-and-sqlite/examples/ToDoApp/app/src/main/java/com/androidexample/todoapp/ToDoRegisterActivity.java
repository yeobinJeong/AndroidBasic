package com.androidexample.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ToDoRegisterActivity extends AppCompatActivity {

    Button registerButton, cancelButton;
    EditText title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_register);

        title = (EditText)findViewById(R.id.title_in_register);
        content = (EditText)findViewById(R.id.content_in_register);
        registerButton = (Button)findViewById(R.id.register_button);
        cancelButton = (Button)findViewById(R.id.cancel_button_in_register);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //등록과 관련된 처리 (DTO 객체를 만들고 intent에 담아서 ListActivity로 반환)
                ToDo toDo = new ToDo();
                toDo.setTitle(title.getText().toString());
                toDo.setContent(content.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("todo", toDo);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });


    }
}
