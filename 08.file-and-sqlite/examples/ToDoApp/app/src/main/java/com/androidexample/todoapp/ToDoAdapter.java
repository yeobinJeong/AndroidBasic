package com.androidexample.todoapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends BaseAdapter {

    //inflate : xml resource -> 객체

    private Context context;//inflate 할 때 사용할 변수
    private int layoutId;//inflate 할 대상 -> ListView 항목의 표시 layout xml id
    private List<ToDo> toDoList;//표시할 데이터 목록

    public ToDoAdapter(Context context, int layoutId, List<ToDo> toDoList) {
        this.context = context;
        this.layoutId = layoutId;
        this.toDoList = toDoList;
    }
    @Override
    public int getCount() {
        return toDoList.size();
    }
    @Override
    public Object getItem(int position) {
        return toDoList.get(position);
    }
    @Override
    public long getItemId(int position) {
        //return toDoList.get(position).getNo();
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = View.inflate(context, layoutId, null);//xml -> 객체
        }

        ToDo toDo = toDoList.get(position);
        TextView title = (TextView)view.findViewById(R.id.title_in_item_view);
        title.setText(toDo.getTitle());
        TextView content = (TextView)view.findViewById(R.id.content_in_item_view);
        content.setText(toDo.getContent());

        return view;
    }
}
