package com.androidexample.moviemanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

	//Activity 이지만 Context로 묶어서 표현
	private Context context;//기본적으로 Context를 가지고 있다. //Activity 를 벗어나는 순간 Context를 가지고 있다.//Adapter는 무조건 Activity와 연관
	private ArrayList<Movie> movies;
	//private ImageLoader loader;
	
	public MovieAdapter(Context context, ArrayList<Movie>movies){
		this.context =context;
		this.movies=movies;
		//loader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return movies.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return movies.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg) {
		if(convertView ==null) {
			convertView = View.inflate(context, R.layout.movieitem_view, null);
		}
		final View v = convertView;//값이 다 바뀐 다음에

		final Movie movie = movies.get(position);
		((TextView) convertView.findViewById(R.id.title)).setText(movie.getTitle());
		((RatingBar) convertView.findViewById(R.id.rating)).setRating(movie.getUserRating()/2);

		//동기 방식 이미지 다운로드
		try {
			if (movie.getImage() == null || movie.getImage().length() == 0) {
				Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
				((ImageView)convertView.findViewById(R.id.image)).setImageBitmap(bm);
			} else {
				Thread thread = new Thread() {
					public void run() {
						InputStream istream = null;
						try {
							URL url = new URL(movie.getImage());
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							istream = conn.getInputStream();
							final Bitmap bm = BitmapFactory.decodeStream(istream);

							((Activity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									((ImageView) v.findViewById(R.id.image)).setImageBitmap(bm);
								}
							});

						} catch (Exception ex) {
							throw new RuntimeException(ex);
						} finally {
							try { istream.close(); } catch (Exception ex) {}
						}
					}
				};
				thread.start();

				thread.join();//thread가 종료될 때까지 대기(이미지 다운로드가 끝날때까지 기다리기)//Thread를 돌리지만 동기방식으로로
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return convertView;
	}

}



















