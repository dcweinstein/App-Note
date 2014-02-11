package com.example.appnote;



public class Note{

	private String title;
	private String note;
	public Note(String s, String t)
	{
		title=s;
		note=t;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getNote()
	{
		return note;
	}

}
