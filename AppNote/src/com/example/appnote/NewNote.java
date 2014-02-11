package com.example.appnote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewNote extends Activity {
	int check = 0;
	String titleReturn;
	String noteReturn;
	int anInt = 0;
	int position = 0;
	int first = 1;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		//setTitle(titleReturn);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		check = intent.getIntExtra(MainActivity.CHECK, 0);
		position = intent.getIntExtra("position", 0);
		if(check==1)
		{
			titleReturn = intent.getStringExtra(MainActivity.TITLE);
			noteReturn = intent.getStringExtra(MainActivity.NOTE);
			
			TextView titleBar = (TextView)findViewById(R.id.title);
			TextView noteBar = (TextView)findViewById(R.id.note_input);
			
			titleBar.setText(titleReturn);
			noteBar.setText(noteReturn);
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.new_note_actions, menu);
		    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_saveNote:
			EditText t=(EditText)findViewById(R.id.title);
			String title=t.getText().toString();
			EditText n = (EditText)findViewById(R.id.note_input);
			String noteIn = n.getText().toString();
			Note note = new Note(title, noteIn);
			((NotesArray)getApplication()).notes.add(note);
			Context context = getApplicationContext();
			CharSequence text0 = "Note Added!";
			CharSequence text1 = "Note Updated!";
			int duration = Toast.LENGTH_SHORT;
			if(check==1)
				Toast.makeText(context, text1, duration).show();
			else
				Toast.makeText(context, text0, duration).show();
			anInt=1;
			backToMain();
			return true;
		case R.id.action_deleteNote:
			/*if(position!=0)
				((NotesArray)getApplication()).notes.remove(position);
			//first = 0;*/
			Context context1 = getApplicationContext();
			CharSequence deleteText = "Note Deleted!";
			int deleteDuration = Toast.LENGTH_SHORT;
			Toast.makeText(context1, deleteText, deleteDuration).show();
			anInt = 1;
			backToMain();
			return true;
		case android.R.id.home:
			if(check==1)
			{
				Note oldNote = new Note(titleReturn, noteReturn);
				((NotesArray)getApplication()).notes.add(oldNote);
			}
			backToMain();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void backToMain() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(MainActivity.CHECK, anInt);
		intent.putExtra(MainActivity.FIRST, first);
		startActivity(intent);
	}

}
