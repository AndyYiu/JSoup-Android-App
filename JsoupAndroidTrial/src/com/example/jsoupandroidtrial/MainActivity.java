package com.example.jsoupandroidtrial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	MyTask mt;
	TextView tvInfo;
	String URL="https://www.student.cs.uwaterloo.ca/~ayiu/revmenu.html";
	//String URL="http://en.wikipedia.org/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvInfo = (TextView) findViewById(R.id.tvinfo);
		mt = new MyTask();
		mt.execute(URL);
	}

	class MyTask extends AsyncTask<String, Void, Elements> {
		Document doc;
		Elements words;
		String what1="failed";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			tvInfo.setText("Please wait...");
		}

		protected Elements doInBackground(String... params) {
			// TimeUnit.SECONDS.sleep(2);
			String url=params[0];
			try {
				doc = Jsoup.connect(url).get();
				words = doc.select("b");
				what1 = words.text();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return words;
		}

		protected void onPostExecute(Elements result) {
			super.onPostExecute(result);
			//tvInfo.setText(result);
			String menu = "";
			ArrayList<String> lunch = new ArrayList<String>();
			ArrayList<String> dinner = new ArrayList<String>();
			ListIterator<Element> postIt = result.listIterator();
			
			for(int i = 0; i < 3; i++){
				if(postIt.hasNext()){
					lunch.add(postIt.next().text());
				}
			}
			
			for(int i = 0; i < 3; i++){
				if(postIt.hasNext()){
					dinner.add(postIt.next().text());
				}
			}
			
			String[] lunch1 = new String[lunch.size()];
			lunch.toArray(lunch1);
			String[] dinner1 = new String[dinner.size()];
			dinner.toArray(dinner1);
			
			menu = "Lunch: \n" + lunch1[0] + "\n" + lunch1[1] + "\n" + lunch1[2] + "\n" + "\n" + "Dinner: \n" + dinner1[0] + "\n" + dinner1[1] + "\n" + dinner1[2] + "\n";
			tvInfo.setText(menu);
		}
	}
}