package myapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this, "인생 새옹지마.", Toast.LENGTH_SHORT).show();

            }
        });
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                /*Intent intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.google.com"));
                startActivity(intent);*/

                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);

            }
        });
    }
    //method

    public void clickHandler(View v){
            Toast.makeText(MainActivity.this, "여잔 짧은치마.", Toast.LENGTH_SHORT).show();
    }





}
