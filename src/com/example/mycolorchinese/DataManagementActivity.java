package com.example.mycolorchinese;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DataManagementActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_management);
	}

	public void openCharacterDatabase(View view)
	{
		startActivity(new Intent(getApplicationContext(), CharacterDatabaseActivity.class));
		
	}

	public void openVocabDatabase(View view)
	{
		startActivity(new Intent(getApplicationContext(), VocabDatabaseActivity.class));
		
	}
	
	public void openSentenceDatabase(View view)
	{
		startActivity(new Intent(getApplicationContext(), SentenceDatabaseActivity.class));
		
	}
	
	
}
