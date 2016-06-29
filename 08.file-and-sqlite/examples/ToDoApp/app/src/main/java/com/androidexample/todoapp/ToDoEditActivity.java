package com.androidexample.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ToDoEditActivity extends AppCompatActivity {

    Button registerButton, cancelButton;
    EditText title, content;

    ToDo toDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_register);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        title = (EditText)findViewById(R.id.title_in_register);
        content = (EditText)findViewById(R.id.content_in_register);

        ToDo toDo = (ToDo)intent.getSerializableExtra("todo");
        title.setText(toDo.getTitle());
        content.setText(toDo.getContent());

        registerButton = (Button)findViewById(R.id.register_button);
        registerButton.setText("수정");
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

            }
        });


    }
}
