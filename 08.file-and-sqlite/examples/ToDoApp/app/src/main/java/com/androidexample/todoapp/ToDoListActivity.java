package com.androidexample.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    private final int REGISTER_REQUEST_CODE = 1;
    private final int EDIT_REQUEST_CODE = 2;

    ListView toDoListView;

    ToDoDbHelper helper;

    ToDoAdapter adapter;

    ArrayList<ToDo> toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        toDoListView = (ListView)findViewById(R.id.toDoListView);

        helper = new ToDoDbHelper(this, "todo.sqlite", null, 1);

        toDoList = new ArrayList<>();
        //toDoList에 데이터 채우기
        loadDataFromDb();
        adapter = new ToDoAdapter(this, R.layout.todo_item_view, toDoList);
        toDoListView.setAdapter(adapter);

        //리스트뷰의 항목을 선택하면 호출되는 이벤트 처리기
        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                //선택된 항목 반환
                ToDo toDo = toDoList.get(position);
                Intent intent = new Intent(ToDoListActivity.this, ToDoEditActivity.class);
                intent.putExtra("todo", toDo);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    private void loadDataFromDb() {
        toDoList.clear();//목록 비우기
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, title, content FROM todo", null);
        int count = cursor.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                cursor.moveToNext();
                ToDo toDo = new ToDo();
                toDo.setNo(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setContent(cursor.getString(2));
                toDoList.add(toDo);
            }
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item = menu.add(Menu.NONE, 1, Menu.NONE, "할 일 등록");
        item.setIcon(R.drawable.plus_icon);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()) {
            case 1 :
                intent = new Intent(this, ToDoRegisterActivity.class);
                //startActivity(intent);//호출
                startActivityForResult(intent, REGISTER_REQUEST_CODE);//호출 + 응답 (onActivityResult)
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REGISTER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                ToDo todo = (ToDo)data.getSerializableExtra("todo");
                //SQLite에 데이터 저장
                SQLiteDatabase db = helper.getWritableDatabase();

                db.execSQL("INSERT INTO todo (title, content) VALUES(?, ?)",
                        new String[] { todo.getTitle(), todo.getContent() });
                db.close();

                loadDataFromDb();//데이터 재로딩
                adapter.notifyDataSetChanged();//변경사항 통지 -> 화면갱신
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
