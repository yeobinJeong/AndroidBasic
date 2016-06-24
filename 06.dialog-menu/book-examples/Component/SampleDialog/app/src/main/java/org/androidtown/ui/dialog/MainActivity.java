package org.androidtown.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 간단한 알림 대화상자를 만들어 보여주는 방법을 볼 수 있습니다.
 *
 * @author Mike
 *
 */
public class MainActivity extends AppCompatActivity {
    TextView textView1;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textView1 = (TextView) findViewById(R.id.textView1);
        //지정된 요소를 LongClick 하면 상황에 맞는 메뉴를 표시
        registerForContextMenu(textView1);
    }

    /**
     * 버튼을 눌렀을 때 대화상자 객체를 생성하는 메소드를 호출
     * @param v
     */
    public void onButton1Clicked(View v) {
        AlertDialog dialog = createDialogBox4();
        dialog.show();
    }


    /**
     * 대화상자 객체 생성
     */
    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // 예 버튼 설정
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "예 버튼이 눌렀습니다. " + Integer.toString(whichButton);
                textView1.setText(msg);
            }
        });

        // 취소 버튼 설정
        builder.setNeutralButton("취소",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "취소 버튼이 눌렸습니다. " + Integer.toString(whichButton);
                textView1.setText(msg);
            }
        });

        // 아니오 버튼 설정
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "아니오 버튼이 눌렸습니다. " + Integer.toString(whichButton);
                textView1.setText(msg);
            }
        });

        // 빌더 객체의 create() 메소드 호출하면 대화상자 객체 생성
        AlertDialog dialog = builder.create();

        return dialog;
    }

    private AlertDialog createDialogBox2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("선택").setItems(new String[]  { "항목 1", "항목 2", "항목 3"}, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                textView1.setText(String.format("항목 %d가 선택되었습니다", which + 1));
            }

        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // 예 버튼 설정
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "예 버튼이 눌렀습니다. " + Integer.toString(whichButton);
                textView1.setText(msg);
            }
        });

        // 취소 버튼 설정
        builder.setNeutralButton("취소",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "취소 버튼이 눌렸습니다. " + Integer.toString(whichButton);
                textView1.setText(msg);
            }
        });

        // 아니오 버튼 설정
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "아니오 버튼이 눌렸습니다. " + Integer.toString(whichButton);
                textView1.setText(msg);
            }
        });

        // 빌더 객체의 create() 메소드 호출하면 대화상자 객체 생성
        AlertDialog dialog = builder.create();

        return dialog;
    }

    private AlertDialog createDialogBox3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("선택").setSingleChoiceItems(new String[]  { "항목 1", "항목 2", "항목 3"}, 0 , new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                textView1.setText(String.format("항목 %d가 선택되었습니다", which + 1));
            }

        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // 예 버튼 설정
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "예 버튼이 눌렀습니다. " + Integer.toString(whichButton);
            }
        });

        // 취소 버튼 설정
        builder.setNeutralButton("취소",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "취소 버튼이 눌렸습니다. " + Integer.toString(whichButton);
            }
        });

        // 아니오 버튼 설정
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "아니오 버튼이 눌렸습니다. " + Integer.toString(whichButton);
            }
        });

        // 빌더 객체의 create() 메소드 호출하면 대화상자 객체 생성
        AlertDialog dialog = builder.create();

        return dialog;
    }


    private AlertDialog createDialogBox4(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("선택").setMultiChoiceItems(new String[]{"항목 1", "항목 2", "항목 3"}, new boolean[] {false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // 예 버튼 설정
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "예 버튼이 눌렀습니다. " + Integer.toString(whichButton);
            }
        });

        // 취소 버튼 설정
        builder.setNeutralButton("취소",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "취소 버튼이 눌렸습니다. " + Integer.toString(whichButton);
            }
        });

        // 아니오 버튼 설정
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                msg = "아니오 버튼이 눌렸습니다. " + Integer.toString(whichButton);
            }
        });

        // 빌더 객체의 create() 메소드 호출하면 대화상자 객체 생성
        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
     /*   MenuItem mi = menu.add(Menu.NONE, 1, 1, "아이콘 메뉴");

        mi.setIcon(R.mipmap.ic_launcher);
        mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        menu.add(Menu.NONE, 1, 1, "메뉴 1");
        menu.add(Menu.NONE, 1, 1, "메뉴 2");
        menu.add(Menu.NONE, 1, 1, "메뉴 3");
        menu.add(Menu.NONE, 1, 1, "메뉴 4");*/

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onCreateContextMenu(ContextMenu cmenu, View v, ContextMenu.ContextMenuInfo cmenui){

        getMenuInflater().inflate(R.menu.menu_main, cmenu);
        super.onCreateContextMenu(cmenu, v, cmenui);
    }
}
