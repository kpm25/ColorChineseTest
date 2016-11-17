package com.example.mycolorchinese;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReviewPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_page);
	}


	
	public void reviewSentences(View view)
	{
	startActivity(new Intent(getApplicationContext(), ReviewSentencesActivity.class));
		
	}
	

	public void reviewVocab(View view)
	{
	startActivity(new Intent(getApplicationContext(), ReviewVocabActivity.class));
		
	}
	

	public void reviewCharacters(View view)
	{
		startActivity(new Intent(getApplicationContext(), ReviewCharactersActivity.class));
		
	}
	
	
	
	
}
