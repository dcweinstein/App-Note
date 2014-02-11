package com.example.appnote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	public final static String CHECK = "check";
	public final static String TITLE = "title";
	public final static String NOTE = "note";
	public final static String FIRST = "First";
	public SharedPreferences sp;
	public static String filename = "notesInfoFile";

	
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = getSharedPreferences(filename, 0);
		Intent intent = getIntent();
		int check = intent.getIntExtra(MainActivity.CHECK, 0);
		if(check == 1)
			save();
		listView=(ListView)findViewById(R.id.list);
		int first = intent.getIntExtra(MainActivity.FIRST, 0);
		if(first==0)
			populate();
		String[] titles=new String[((NotesArray)getApplication()).notes.size()];
		for(int i=0; i<((NotesArray)getApplication()).notes.size(); i++)
		{
			titles[i]=((NotesArray)getApplication()).notes.get(i).getTitle();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			 
			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               
             // ListView Clicked item index
             int itemPosition = position;
              
             String titleReturn = ((NotesArray)getApplication()).notes.get(itemPosition).getTitle();
             String noteReturn = ((NotesArray)getApplication()).notes.get(itemPosition).getNote();
             
             ((NotesArray)getApplication()).notes.remove(position);
             
             returnToNote(titleReturn, noteReturn, itemPosition);
             }

        }); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		//Handle presses on the action bar item
		switch (item.getItemId())
		{
		case R.id.action_addNote:
			makeNote();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void makeNote() {
		Intent intent = new Intent(this, NewNote.class);
		intent.putExtra(CHECK, 0);
		startActivity(intent);
	}
	
	private void returnToNote(String title, String note, int position) {
		Intent intent = new Intent(this, NewNote.class);
		intent.putExtra(TITLE, title);
		intent.putExtra(NOTE, note);
		intent.putExtra("position", position+1);
		intent.putExtra(CHECK, 1);
		startActivity(intent);
	}
	
	

	public void save()
	{
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		for(int i=0; i<((NotesArray)getApplication()).notes.size(); i++)
		{
			String keyTitle = i + "Title";
			String keyNote = i + "Note";
			editor.putString(keyTitle, ((NotesArray)getApplication()).notes.get(i).getTitle());
			editor.putString(keyNote, ((NotesArray)getApplication()).notes.get(i).getNote());
		}
		editor.putInt("numNotes", ((NotesArray)getApplication()).notes.size());
		editor.commit();
	}
	
	public void populate()
	{
		sp = getSharedPreferences(filename, 0);
		int num = sp.getInt("numNotes", 0);
		if(num==0)
			return;
		for(int i=0; i<num; i++)
		{
			String kt = i+"Title";
			String kn = i+"Note";
			String noteTitle = sp.getString(kt, "No Data");
			String noteNote = sp.getString(kn, "No Data");
			((NotesArray)getApplication()).notes.add(new Note(noteTitle, noteNote));
		}
	}


}
