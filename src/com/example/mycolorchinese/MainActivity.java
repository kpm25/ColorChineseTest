package com.example.mycolorchinese;

import helper.DatabaseHelper;
import helper.DownloadImages;

import java.io.File;
import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import helper.ChineseCharacter;
import helper.ChineseSentence;
import helper.ChineseVocab;
import helper.DatabaseHelper;
import helper.LoadCharactersFromFile;
import helper.LoadSentencesFromFile;
import helper.LoadVocabFromFile;


public class MainActivity extends Activity {
	ObjectAnimator downIt, upIt, rotateIt, leftIt, rightIt;
	ImageView downMe, upMe, rotateMe, leftMe, rightMe;
	TextView  tvTitleC;
	private DatabaseHelper myDb;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		downMe = (ImageView)findViewById(R.id.imageView1);
		 upMe= (ImageView)findViewById(R.id.ImageView2);
		rotateMe = (ImageView)findViewById(R.id.ImageView3);
			leftMe = (ImageView)findViewById(R.id.ImageView4);
			rightMe = (ImageView)findViewById(R.id.imageView5);

			tvTitleC = (TextView)findViewById(R.id.tvTitle);
    	
		
	
		
			
			
		
	
		down();
		up();
		rotate();
		left();
		right();
			
		
		myDb = new DatabaseHelper(this);
		
	
		Cursor result1 = myDb.getAllCharacterData();
		Cursor result2 = myDb.getAllVocabData();
		Cursor result3 = myDb.getAllSentenceData();
		//if all tables are empty load the default sets:
        if( (result1.getCount() == 0)  &&  (result2.getCount() == 0)&&  (result3.getCount() == 0)  )
       {
            
		            
            try{      
    	    	
  	    	  String path = Environment.getExternalStorageDirectory().getAbsolutePath();
  	          
		  	    	
		  	       // returns pathnames for files and directory
		  	       File f = new File(path+"/ColorChinese");
		  	      File f2 = new File(path+"/ColorChinese/images");
		  	       
		  	       // create
		  	    boolean bool = f.mkdir();
		  	  boolean bool2 = f2.mkdir();
		  	       
		  	       // print
		  	       System.out.print("Directory created? "+bool
		  	    		   +"\nDirectory2 created? "+bool2);
		  	    
		  	       
		  	    }catch(Exception e)
		  	    {
		  	       // if any error occurs
		  	       e.printStackTrace();
		  	    }
            
            
            insertCharacters();
            insertSentences();
            insertVocab();
           
            
            // show message
            showMessage("You Started Color Chinese for First time, or there were no lists...","-All default lists are being loaded!!!!\n "
            		+ "-Go to Database Manager if you want to clear,enter your own lists, or just add to default lists.\n-Press \"back\" when complete...");
           
        }
        
		
					
			
	}


	private void insertCharacters()
	{
		 ArrayList<ChineseCharacter> chineseCharacterArraylist = LoadCharactersFromFile.readFromFile();
     	
    	 
    	 Toast.makeText(MainActivity.this,"chineseCharacterArraylist: "+chineseCharacterArraylist.get(0).getListtitle(),Toast.LENGTH_LONG).show();
    	 boolean isInserted = false;
    	
   
   	  for(int i =0;i< chineseCharacterArraylist.size() ;i++)
   	  {
   		  isInserted = myDb.insertCharacterData(chineseCharacterArraylist.get(i).getListtitle(),chineseCharacterArraylist.get(i).getHanzi(),chineseCharacterArraylist.get(i).getPinyin());
        
   	  }
	           
	   	if(isInserted == true)
	        Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
	    else
	        Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show(); 
	    
		
	}

	
	private void insertVocab()
	{
		ArrayList<ChineseVocab> chineseVocabArraylist = LoadVocabFromFile.readVocabFromFile();
	   
	   	 
	   	 Toast.makeText(MainActivity.this,"chineseVocabArraylist: "+chineseVocabArraylist.get(0).getListtitle(),Toast.LENGTH_LONG).show();
	   	 boolean isInserted = false;
	   	 
	   	 
	   	 
	   	
	   	 
	   
		
		
        	
      
       
	
	   	 
	  
	  	  for(int i =0;i< chineseVocabArraylist.size() ;i++)
	  	  {
	  		  
		  		String      search_string = chineseVocabArraylist.get(i).getChinesevocab();
		        search_string = search_string.replace(" ", "");
		        	
		        	DownloadImages download = new DownloadImages(search_string);
		        	download.execute();
		        	
		  		String str = chineseVocabArraylist.get(i).getChinesevocab().replace(" !", "!"); 
		  		String listtitle = (chineseVocabArraylist.get(i).getListtitle().replace(" ", ""));
		  		  isInserted = myDb.insertVocabData(listtitle,str,chineseVocabArraylist.get(i).getEnglishvocab()
		  				  ,chineseVocabArraylist.get(i).getPinyinvocab() );
	       
	  	  }
	          
		  	if(isInserted == true)
		       Toast.makeText(MainActivity.this,"New Vocab Inserted",Toast.LENGTH_LONG).show();
		   else
		       Toast.makeText(MainActivity.this,"Vocab not Inserted",Toast.LENGTH_LONG).show(); 
	    
		
	}
	
	private void insertSentences()
	{
		    ArrayList<ChineseSentence> chineseSentenceArraylist = LoadSentencesFromFile.readSentenceFromFile();
		    	
		   	 
		   	 Toast.makeText(MainActivity.this,"chineseSentencesArraylist: "+chineseSentenceArraylist.get(0).getListtitle(),Toast.LENGTH_LONG).show();
		   	 boolean isInserted = false;
		   	
		  
		  	  for(int i =0;i< chineseSentenceArraylist.size() ;i++)
		  	  {
		  		  isInserted = myDb.insertSentenceData(chineseSentenceArraylist.get(i).getListtitle(),chineseSentenceArraylist.get(i).getChinesesentence(),chineseSentenceArraylist.get(i).getEnglishsentence()
		  				  ,chineseSentenceArraylist.get(i).getPinyinsentence() );
		       
		  	  }
		          
			  	if(isInserted == true)
			       Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
			   else
			       Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show(); 
	}


	public void down()
	    {
		  downIt = ObjectAnimator.ofFloat(downMe, "translationY", 0f, -1200f);
			
		  downIt.setDuration(4000); //4 seconds
	//	  downIt.setRepeatMode(ValueAnimator.INFINITE);
		  downIt.start();
		}
	  
	  public void up()
	    {
		  upIt = ObjectAnimator.ofFloat(upMe, "translationY", 0f, 1200f);
			
		  upIt.setDuration(4000); //4 seconds
	//	  upIt.setRepeatMode(ValueAnimator.INFINITE);
		  upIt.start();
	  
	    }
	  public void rotate() 
	  {
		 
		 
		  rotateIt = ObjectAnimator.ofFloat(rotateMe, "rotation", 0f,720f);
		  rotateIt.setDuration(6000); //6 seconds
		
			  
		  tvTitleC.setTextColor(Color.BLACK);
		  tvTitleC.setText("Color Chinese");
		  
		  
		  
		   
		
		  rotateIt.setStartDelay(1000); //if you want delay in animation start
		  rotateIt.start();
		  
		  
		 
			 

			  
			  
			 
			 
		
			  int rand = (int) (4*Math.random()+1);

			      if(rand==1)	tvTitleC.setTextColor(Color.RED);
			   
			  
			      if(rand==2) 	tvTitleC.setTextColor(Color.rgb(255, 190, 0));
			 
			      if(rand==3) 	tvTitleC.setTextColor(Color.GREEN);
			  
			       if(rand==4) 	tvTitleC.setTextColor(Color.BLUE);
			       
			       
			       
			
  }
	  
	  public void left()
		{
		 
			  leftIt = ObjectAnimator.ofFloat(leftMe, "translationX", 0f,-1200f);
			
			  leftIt.setDuration(4000); //4 seconds
			//  leftIt.setRepeatMode(ValueAnimator.INFINITE);
			  leftIt.start();
		  
			
		}
	  
	  public void right()
		{
		  rightIt = ObjectAnimator.ofFloat(rightMe, "translationX", 0f, 1200f);
			
		  rightIt.setDuration(4000); //4 seconds
		  rightIt.setRepeatMode(ValueAnimator.INFINITE);
		  rightIt.start();
	  
			
		}
	
	
	
	
	
	

	public void openDataManagementActivity(View view)
	{
		startActivity(new Intent(getApplicationContext(), DataManagementActivity.class));
		
	}
	
	public void learnAboutPinyin(View view)
	{
   //   startActivity(new Intent(getApplicationContext(), LearnAboutPinyinActivity.class));
	//	startActivity(new Intent(getApplicationContext(), TranslateActivity.class));
		startActivity(new Intent(getApplicationContext(), ImageViewActivity.class));
	}

	
	public void loadReviewPageAcivity(View view)
	{
	 startActivity(new Intent(getApplicationContext(), ReviewPageActivity.class));
		
	}
	
	 public void showMessage(String title,String Message){
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setCancelable(true);
	        builder.setTitle(title);
	        builder.setMessage(Message);
	        builder.show();
	    }
	
}



