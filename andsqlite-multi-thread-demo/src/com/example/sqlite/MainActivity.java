package com.example.sqlite;

import java.util.ArrayList;

import mars.database.base.DatabaseManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;



public class MainActivity extends Activity {
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.imageButton1).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Thread(new AddDataBase("aaaa1")).start();
						new Thread(new AddDataBase("bbbb1")).start();
						new Thread(new AddDataBase("cccc1")).start();
						new Thread(new AddDataBase("dddd1")).start();
						new Thread(new AddDataBase("eeee1")).start();
					}
				});
		findViewById(R.id.imageButton2).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						i = i + 1;
						try {
							DatabaseManager.getInstance(MainActivity.this)
									.switchDataBase(MainActivity.this,
											"Table" + i);
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.getMessage());
						}
					}
				});
		findViewById(R.id.imageButton3).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						StarDao starDao = new StarDao();
						ArrayList<Star> list = starDao.getPagingBySqlRawQuery(
								MainActivity.this, "SELECT * FROM Star", null);
						for (Star star : list) {
							System.out.println(star.getId() + "---"
									+ star.getName());
						}
					}
				});
	}

	protected class AddDataBase implements Runnable {
		String id;

		public AddDataBase(String id) {
			this.id = id;
		}

		@Override
		public void run() {
			ArrayList<Star> list = new ArrayList<Star>();
			for (int i = 0; i < 500; i++) {
				list.add(new Star(i, i, "Thread1-->" + i));
			}
			long x = System.currentTimeMillis();
			StarDao starDao = new StarDao();
			for (Star star : list) {
				System.out.println("save " + id + " result="
						+ starDao.saveStar(MainActivity.this, star));
			}
			// starDao.saveStarByTransaction(MainActivity.this,
			// list);
			System.out.println("Thread1 stop");
			System.out.println(System.currentTimeMillis() - x);

		}
	}

}
