package org.androidtown.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    String databaseName;
    String tableName;
    TextView status;
    boolean databaseCreated = false;
    boolean tableCreated = false;

    final String DB_PATH;

    SQLiteDatabase db;

    public MainActivity() {
        DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText databaseNameInput = (EditText) findViewById(R.id.databaseNameInput);
        final EditText tableNameInput = (EditText) findViewById(R.id.tableNameInput);

        Button createDatabaseBtn = (Button) findViewById(R.id.createDatabaseBtn);
        if (createDatabaseBtn != null) {
            createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    databaseName = databaseNameInput.getText().toString();
                    createDatabase(databaseName);
                }
            });
        }

        Button createTableBtn = (Button) findViewById(R.id.createTableBtn);
        if (createTableBtn != null) {
            createTableBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (databaseCreated) {
                        tableName = tableNameInput.getText().toString();
                        createTable(tableName);
                        int count = insertRecord(tableName);
                        println(count + " records inserted.");
                    } else {
                        Toast.makeText(MainActivity.this, "First, Create Database", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        status = (TextView) findViewById(R.id.status);

    }

    private void createDatabase(String name) {
        println("creating database [" + name + "].");

        File dbFile = new File(DB_PATH);
        dbFile = new File(dbFile, name);

        try {
            db = openOrCreateDatabase(
                    dbFile.getAbsolutePath(),
                    Activity.MODE_PRIVATE,
                    null);

            databaseCreated = true;
            println("database is created.");
        } catch (Exception ex) {
            ex.printStackTrace();
            println("database is not created.");
        }
    }

    private void createTable(String name) {
        println("creating table [" + name + "].");

        db.execSQL("create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);");

        tableCreated = true;
    }

    private int insertRecord(String name) {
        println("inserting records into table " + name + ".");

        int count = 3;
        db.execSQL("insert into " + name + "(name, age, phone) values ('John', 20, '010-7788-1234');");
        db.execSQL("insert into " + name + "(name, age, phone) values ('Mike', 35, '010-8888-1111');");
        db.execSQL("insert into " + name + "(name, age, phone) values ('Sean', 26, '010-6677-4321');");

        return count;
    }

    private int insertRecordParam(String name) {
        println("inserting records using parameters.");

        int count = 1;
        ContentValues recordValues = new ContentValues();

        recordValues.put("name", "Rice");
        recordValues.put("age", 43);
        recordValues.put("phone", "010-3322-9811");
        int rowPosition = (int) db.insert(name, null, recordValues);

        return count;
    }

    private int updateRecordParam(String name) {
        println("updating records using parameters.");

        ContentValues recordValues = new ContentValues();
        recordValues.put("age", 43);
        String[] whereArgs = {"Rice"};

        int rowAffected = db.update(name,
                recordValues,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

    private int deleteRecordParam(String name) {
        println("deleting records using parameters.");

        String[] whereArgs = {"Rice"};

        int rowAffected = db.delete(name,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

    private void println(String msg) {
        Log.d("MainActivity", msg);
        status.append("\n" + msg);

    }

}
