package com.example.mycolorchinese;


//https://docs.oracle.com/javase/tutorial/i18n/locale/matching.html



import java.util.ArrayList;
import java.util.Locale;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import helper.ChineseCharacter;
import helper.DatabaseHelper;
import helper.PinyinHelper;

public class ReviewCharacterClassroomActivity extends Activity implements OnInitListener,RecognitionListener {
 ArrayList<String> listtitleListsToReview =  new  ArrayList<String>();
 ArrayList<ChineseCharacter> chineseCharactersToReview = new ArrayList<ChineseCharacter>();
 private DatabaseHelper myDb;
 private CheckBox colorCheckBox; 
 private CheckBox randomCheckBox;
	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS =1;
	private static final int CHAR_SIZE =300;
	private static final int PIN_SIZE =90;
	private ChineseCharacter currentChineseCharacter;
	public static final String EXTRA_MESSAGE = null;
	private static final float speechRate = (float) 0.8;  //1.0 = normal speed
	private TextToSpeech tts;
	private String Lang = "CHINESE";
//	private String Lang = "ENGLISH";
	private  char ch;
	private String myLanguage = "cmn-Hans-CN";
//	private String myLanguage = "en-US";
	private ToggleButton toggleButton;
	private Button nextButton;
	private ProgressBar progressBar;
	private SpeechRecognizer speech = null;
	private Intent recognizerIntent;
	private String LOG_TAG = "VoiceRecognitionActivity";
//
	private boolean isPinyin = false;
	

//	private String[] stringArray = { "我星期六8点起床", "你睡得太晚了", "我星期五晚上不看电视", "你住在哪儿", "你写汉字写得很好", "我今天早上起得太晚了",
//			"丁新家离梅西大学不远", "我家在梅西大学旁边" };
	private String[] stringArray;
	
	
	private String   correctString= "";
	private int rand, count = 0;

	
	 Boolean hasStarted = false;
	int charNum = 0;
	SeekBar seekbarRows,seekbarCols;
//	String characterListAsString;
//	CharacterSet characterSetTool;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	//private int charCount = 0;
	Button btnNext;
	TextView text ;
 
	Boolean toggle = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_character_classroom);

		
		listtitleListsToReview =  getIntent().getStringArrayListExtra("listtitleListsToReviewName");
		
		 colorCheckBox = (CheckBox)findViewById(R.id.checkBoxToneColorsCharacterClassrom);
		colorCheckBox.setChecked(true);
		randomCheckBox = (CheckBox)findViewById(R.id.checkBoxRandom);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
		
		
		
		/*
		text = (TextView) findViewById(R.id.textTest);
		String str = "";
		
		for(int i =0;i<(listtitleListsToReview.size());i++)
		{
		       str = str +( i+1)+".) "+ listtitleListsToReview.get(i)+"\n"; 
				        
		}
		   String test =  getIntent().getStringExtra("stringtest");
		   text.setText("Character Lists To Review==>\n"+ str);

       showMessage("test:   ",str);
       text = (TextView) findViewById(R.id.textTest);
       */
		
		tts = new TextToSpeech(this, this);
		 tts.setLanguage(Locale.CHINESE);
	//	 tts.setLanguage(Locale.ENGLISH);
		
		
		
	//	 characterSetTool = new CharacterSet();
	//	 characterListAsString = characterSetTool.getSet();
		
		
		myDb = new DatabaseHelper(this);
		
		setChineseCharacters();
		String str = "TRY TO SAY ANY CHINESE WORDS THAT USE THIS CHARACTER ("+(charNum+1)+"/"+chineseCharactersToReview.size()+"):";
		toggleButton.setText(str);
		
		populateButtons(); 
		
		

		progressBar.setVisibility(View.INVISIBLE);
		speech = SpeechRecognizer.createSpeechRecognizer(this);
		speech.setRecognitionListener(this);

		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
		// recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
		// recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		// RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		// recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

	//	String myLanguage = "cmn-Hans-CN";
		myLanguage = "cmn-Hans-CN";
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, myLanguage);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, myLanguage);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, myLanguage);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

		
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
			//	returnedText.setText("");

				if (isChecked) {
					String str = "TRY TO SAY ANY CHINESE WORDS THAT USE THIS CHARACTER ("+(charNum+1)+"/"+chineseCharactersToReview.size()+"):";
					toggleButton.setText(str);
				//	progressBar.setVisibility(View.VISIBLE);
					progressBar.setIndeterminate(true);
					speech.startListening(recognizerIntent);

				} else {
					progressBar.setIndeterminate(false);
					progressBar.setVisibility(View.INVISIBLE);
					speech.stopListening();
				}
			}
		});

		
		
		
		/*
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (count ==(stringArray.length-1))
				{
					count = 0;
				}
				else {count++;}
				
				tospeakText.setText(stringArray[count]);

				textProgress.setText("");

				
			}
		});
*/
		
		this.setTitle("CHARACTER CLASSROOM...");
		
	
	}
	
	public void randomNum(View view)
	{
		
		   if (randomCheckBox.isChecked())
         {
	              charNum = (int) (  (Math.random()*( chineseCharactersToReview.size())) );
         }
		   
		//String str = "TRY TO SAY ANY CHINESE WORDS THAT USE THIS CHARACTER ("+(charNum+1)+"/"+chineseCharactersToReview.size()+"):";
		//toggleButton.setText(str);
		
		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (speech != null) {
			speech.destroy();
			Log.i(LOG_TAG, "destroy");
		}

	}

	@Override
	public void onBeginningOfSpeech() {
		Log.i(LOG_TAG, "onBeginningOfSpeech");
		progressBar.setIndeterminate(false);
		progressBar.setMax(10);
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		Log.i(LOG_TAG, "onBufferReceived: " + buffer);
	}

	@Override
	public void onEndOfSpeech() {
		Log.i(LOG_TAG, "onEndOfSpeech");
		progressBar.setIndeterminate(true);
		toggleButton.setChecked(false);
	}

	@Override
	public void onError(int errorCode) {
		String errorMessage = getErrorText(errorCode);
		Log.d(LOG_TAG, "FAILED " + errorMessage);
		showMessage("error..",errorMessage);
		
		toggleButton.setChecked(false);
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) 
	{
		Log.i(LOG_TAG, "onEvent");
	}

	@Override
	public void onPartialResults(Bundle arg0) 
	{
		Log.i(LOG_TAG, "onPartialResults");
	}

	@Override
	public void onReadyForSpeech(Bundle arg0)
	{
		Log.i(LOG_TAG, "onReadyForSpeech");
	}

	
	private String checkString(String string)
	{
		String result = string;
		switch (3) 
		{
      case 1:  result = "一 ";
               break;
      case 2:  result = "二";
               break;
      case 3:  result = "三";
               break;
      case 4:  result = "四";
               break;
      case 5:  result = "五";
               break;
      case 6:  result = "六";
               break;
      case 7:  result = "七";
               break;
      case 8:  result = "八";
               break;
      case 9:  result = "九";
               break;
      case 10:  result = "十";
              break;
      
		}    
			
		return result;
	}
	
	@Override
	public void onResults(Bundle results)
	{
		Log.i(LOG_TAG, "onResults");
		ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
	//	String text = "";
	//	for (String result : matches)
	//		text += result + "\n";

	//	String result = matches.get(0);
	 
//		if(result.charAt(0) == currentChineseCharacter.getHanzi())  
		if(matches.get(0).contains(String.valueOf(currentChineseCharacter.getHanzi())))
		{
			tts.setLanguage(Locale.ENGLISH);		    
		//	showMessage("result:","You said: " + matches.get(0)+"\nCORRECT!!!");
			speakOut("Correct!!");
			  tts.setLanguage(Locale.CHINESE);	
		}
		else
		{
			tts.setLanguage(Locale.ENGLISH);
		 //    showMessage("result:","You said: " + matches.get(0)+"\nTRY AGAIN!!");
			speakOut("Not Correct!!");
			 tts.setLanguage(Locale.CHINESE);	
		}
				

		Toast.makeText(ReviewCharacterClassroomActivity.this, "You said: " + matches.get(0),Toast.LENGTH_SHORT).show(); 
		
	     //   if(Integer.getInteger(result)==3)
	      //  	result = "三";
				
	  //      String result2 = checkString(result);
	        
		
	
		
		
		
  
		 
		
		//String stringBeSpeak = String.valueOf(currentChineseCharacter.getHanzi());

	
														// stringToBeSpeak

		// float percentage =
		// (numOfCorrectChar/howManyCorrectCharInStringToSpeak);

		/*
		 * if(percentage <0.3) textProgress.setText(""+numOfCorrectChar+
		 * " out of "+howManyCorrectCharInStringToSpeak+" correct: Not Good! ");
		 * if(percentage >0.7 && percentage < 0.9)
		 * textProgress.setText(""+numOfCorrectChar+" out of "
		 * +howManyCorrectCharInStringToSpeak+" correct: Almost There! "); else
		 * textProgress.setText(""+numOfCorrectChar+" out of "
		 * +howManyCorrectCharInStringToSpeak+" correct: Perfect! ");
		 */
	//  showMessage("Message:",numOfCorrectChar + " out of " + 1 + " are correct: "+"Incorrect: "+numOfCorrectChar);

		//if (result.equals(stringBeSpeak.charAt(0)))
		//	Toast.makeText(VoiceRecognitionActivity.this, "Answer is Correct!!!", Toast.LENGTH_LONG).show();

			// changetextToSpeak
			// int rand = (int) (Math.random()*stringArray.length);

			
		
	}

	
	@Override
	public void onRmsChanged(float rmsdB) 
	{
		Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
		progressBar.setProgress((int) rmsdB);
		
	//	Toast.makeText(VoiceRecognitionActivity.this, "rmsdB: "+(int) rmsdB, Toast.LENGTH_LONG).show();

	}

	public static String getErrorText(int errorCode) 
	{
		String message;
		switch (errorCode) {
		case SpeechRecognizer.ERROR_AUDIO:
			message = "Audio recording error";
			break;
		case SpeechRecognizer.ERROR_CLIENT:
			message = "Client side error";
			break;
		case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
			message = "Insufficient permissions";
			break;
		case SpeechRecognizer.ERROR_NETWORK:
			message = "Network error";
			break;
		case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
			message = "Network timeout";
			break;
		case SpeechRecognizer.ERROR_NO_MATCH:
			message = "No match";
			break;
		case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
			message = "RecognitionService busy";
			break;
		case SpeechRecognizer.ERROR_SERVER:
			message = "error from server";
			break;
		case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
			message = "No speech input";
			break;
		default:
			message = "Didn't understand, please try again.";
			break;
		}
		return message;
	}
	
	
	
	public void setChineseCharacters()
	{

		
	//		String query = "select distinct listtitle , character , pinyin from charactertable where listtitle glob '*"+listtitle1+"*'"
	//				+ " or listtitle glob '*"+listtitle2+"*' or listtitle glob '*"+listtitle3+"*'"; 
      	
			String query = "";
			for(int i=0; i<listtitleListsToReview.size();i++)
		   {
				
				
				if(i==0)
				{	
					 query = query + "select distinct listtitle , character , pinyin from charactertable where listtitle glob '*"
				    +listtitleListsToReview.get(i)+"*' ";
				}
				else
				{
					 query = query +  " or listtitle glob '*"+listtitleListsToReview.get(i)+"*' ";
							  
					
				}
				
			}
			Cursor res = null;
			
			
      	res = myDb.performRawQuery(query);



        
      try
        {         
                
      	if(res.getCount() == 0) {
              // show message
              showMessage("Error","Nothing found");
             
          }

          StringBuffer buffer = new StringBuffer();
          while (res.moveToNext()) {
            
              buffer.append("listname :"+ res.getString(0)+"\n");
              buffer.append("character :"+ res.getString(1)+", ");
             buffer.append("pinyin :"+ res.getString(2)+"\n\n");
             
             ChineseCharacter chineseCharacter = new ChineseCharacter(res.getString(0),res.getString(1).charAt(0),res.getString(2));
            
             chineseCharactersToReview.add(chineseCharacter);
             
          }

          // Show all data
        // showMessage("Character Lists:",buffer.toString());
          
        }
        catch(java.lang.IllegalStateException e)
         {
       	  showMessage("Error:","Array out of bounds!!");
         }
     
			
  	
  	

    
    
    if(res.getCount() == 0) {
        // show message
        showMessage("Error","Nothing found");
        return;
    }

  
		
	}
	
	
	
	

	private void populateButtons()
	{  
		TableLayout table = (TableLayout) findViewById(R.id.tableForVocabLists); 
		
		
		
	
		 
		   
		
		  for (int row = 0; row != NUM_ROWS; row++) 
		     {
			  TableRow tableRow = new TableRow(this); 
		     tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,
		    		                                             TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
		        table.addView(tableRow); 
		       for (int col = 0; col != NUM_COLS; col++)
		         { 
		    	    int scaleFactor = NUM_ROWS;
		    	    if(NUM_ROWS<NUM_COLS)
		    	    {
		    	    	scaleFactor = NUM_COLS;
		    	    }
		    	    final int FINAL_COL = col; final int FINAL_ROW = row;
		            Button button = new Button(this);
		          //  button.setTextColor(Color.BLACK);
		            button.setTextSize(CHAR_SIZE/scaleFactor);
		              button.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
		             
		              button.setBackgroundColor(color.transparent);

		      
		          	
	              
	              /*    if (randomCheckBox.isChecked())
	                  {
			              charNum = (int) (  (Math.random()*( chineseCharactersToReview.size())) );
	                  }
	                */  
			         	 speakOut(String.valueOf( button.getText()));
			         	 ch = chineseCharactersToReview.get(charNum).getHanzi();
		
			         	
	         	 if (colorCheckBox.isChecked())
		        	 {    
			              if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[1]) )	
			      			button.setTextColor(Color.RED);
			      		else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[2]) )	
			      			button.setTextColor(Color.rgb(255, 190, 0));
			      		else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[3]) )	
			      			button.setTextColor(Color.GREEN);
			      		else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[4]) )	
			      		{
			      			button.setTextColor(Color.BLUE);
			      			
			      		}
			      		else 	
			      		{
			      			button.setTextColor(Color.BLACK);
			      			
			      		}
	        	 }//end if (colorCheckBox.isChecked())
		      		
	         	 currentChineseCharacter = chineseCharactersToReview.get(charNum);
		             
		            
		        
			              
		            //  ch = characterListAsString.charAt(charCount);
		              
		              button.setText(""+ch); 
		               // Make text not clip on small
		               button.setPadding(0, 0, 0, 0); 
		               if(row == 0) { button.setPadding(0, 0, 0, 0);}
		               button.setOnClickListener(new View.OnClickListener() 
		                  {
		            	   @Override public void onClick(View v) 
		                      {  
		            		   gridButtonClicked(FINAL_COL, FINAL_ROW); 
		            		   } 
		            	   }); 
		               
		               
		               btnNext = (Button) findViewById(R.id.buttonNextCharacterClassroom);
		       		
		       		btnNext.setOnClickListener (  new View.OnClickListener()
		               {
		                   @Override
		                   public void onClick(View v)
		                   {
		                	  
		                   	nextCharacter(FINAL_COL, FINAL_ROW);
		                	
						
		                   	
		                   }
		               });
		               
		               
		               
		                       tableRow.addView(button); 
		                       buttons[row][col] = button; 
		           } 
		        }
		       

	}

private void nextCharacter(int col, int row)
{

		//	Toast.makeText(this, "Button clicked: " + col + "," + row,Toast.LENGTH_SHORT).show(); 
			
			//button.setTextColor(Color.BLACK);
		  
					// Lock Button Sizes: 
	
//	showMessage("..debugging..inside. Next...charNum:",""+charNum);

	  
				 
					
					lockButtonSizes(); // Does not scale image.
		
		
  	Button button = buttons[row][col];
		
  	 if((toggle)) { button.setTextSize(PIN_SIZE); }
  	 else { button.setTextSize(CHAR_SIZE); }
			
		
		
		
		if (randomCheckBox.isChecked())
		{
		    charNum = (int) (  (Math.random()*( chineseCharactersToReview.size())) );
		    
		}
		else
		{
			charNum++;
			  if( charNum == (chineseCharactersToReview.size()) )
					  {
				  			charNum=0;
					  }
			 
				  
		}
		
	//	 speakOut(String.valueOf( currentChineseCharacter.getHanzi()));
		 ch = chineseCharactersToReview.get(charNum).getHanzi();
		 
		
		 if (colorCheckBox.isChecked())
			 
		 {
			 
					//button.setTextSize(30);
				if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[1]) )	
					button.setTextColor(Color.RED);
				else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[2]) )	
					button.setTextColor(Color.rgb(255, 190, 0));
				else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[3]) )	
					button.setTextColor(Color.GREEN);
				else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[4]) )	
				{
					button.setTextColor(Color.BLUE);
					
				}
				else 	
		  		{
		  			button.setTextColor(Color.BLACK);
		  			
		  		}
		 }//end if (colorCheckBox.isChecked())	
				
		 else 	
			{
				button.setTextColor(Color.BLACK);
				
			}
			  if(!toggle)
				button.setText("" + ch); 
			  else
				  button.setText("" + currentChineseCharacter.getPinyin());  
				
				 currentChineseCharacter = chineseCharactersToReview.get(charNum);
				  
				
				 String str = "TRY TO SAY ANY CHINESE WORDS THAT USE THIS CHARACTER ("+(charNum+1)+"/"+chineseCharactersToReview.size()+"):";
					toggleButton.setText(str);
				
		   
			
}
	private void gridButtonClicked(int col, int row) { 
		
//		Toast.makeText(this, "Button clicked: " + col + "," + row,Toast.LENGTH_SHORT).show(); 
		Button button = buttons[row][col];
		//button.setTextColor(Color.BLACK);
	  
		

	//	showMessage("..debugging..inside...gridButtonClicked..charNum:",""+charNum);
				// Lock Button Sizes: 
				
				lockButtonSizes(); // Does not scale image.

	
				 
				   if((toggle))
					 {
							 button.setTextSize(PIN_SIZE);
							 
							button.setText("" + currentChineseCharacter.getPinyin()); 
						//	 speakOut(String.valueOf(currentChineseCharacter.getHanzi()));
							
							
							
							
								
							
							
							isPinyin = true;
							 
					      }
						 else
						 {
							 button.setTextSize(CHAR_SIZE);
							 if (randomCheckBox.isChecked())
							    {
							        charNum = (int) (  (Math.random()*( chineseCharactersToReview.size())) );
							     
							    }
							   
							  	 
							 
							 
							 
							 

							    
								
									 speakOut(String.valueOf(currentChineseCharacter.getHanzi()));
									 ch = chineseCharactersToReview.get(charNum).getHanzi();
									 
									
									 if (colorCheckBox.isChecked())
										 
									 {
										 
												//button.setTextSize(30);
											if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[1]) )	
												button.setTextColor(Color.RED);
											else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[2]) )	
												button.setTextColor(Color.rgb(255, 190, 0));
											else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[3]) )	
												button.setTextColor(Color.GREEN);
											else if(chineseCharactersToReview.get(charNum).getColor().equals(chineseCharactersToReview.get(charNum).getColors()[4]) )	
											
												button.setTextColor(Color.BLUE);
										
											else 	
								      		{
								      			button.setTextColor(Color.BLACK);
								      			
								      		}
							        }//end if (colorCheckBox.isChecked())	
									
							 else 	
								{
									button.setTextColor(Color.BLACK);
									
								}
							 
							 
							 
							 
							 currentChineseCharacter = chineseCharactersToReview.get(charNum);
							  

							 
							 
							 
							 
							 
							 
							 
							 
							 
						   
							 
							 
							 button.setTextSize(CHAR_SIZE/NUM_ROWS);
							 button.setText("" + currentChineseCharacter.getHanzi());
							
							  
								String str = "TRY TO SAY ANY CHINESE WORDS THAT USE THIS CHARACTER ("+(charNum+1)+"/"+chineseCharactersToReview.size()+"):";
								toggleButton.setText(str); 
						 
						  
	
 
							  
	 }//end else                                                                                                                                       
  
  
		 
		 
		 
		       

				  	  
				   
	    if(toggle){ charNum++;}
		
	  	toggle = !toggle;
	  	
	  				  	

			if(charNum == (chineseCharactersToReview.size()) )
			  {
		        charNum = 0;
		      
			  }
			
			
			
}

	private void lockButtonSizes() { 
		 for (int row = 0; row != NUM_ROWS; row++) 
		   { 
			 for (int col = 0; col != NUM_COLS; col++) 
	            { 
				     Button button = buttons[row][col]; 
				     int width = button.getWidth(); 
				     button.setMinWidth(width);
					    button.setMaxWidth(width);
					     int height = button.getHeight(); 
					      button.setMinHeight(height);
					      button.setMaxHeight(height);
			     }
		   }
	  }
	
	public void showMessage(String title,String Message){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setCancelable(true);
	    builder.setTitle(title);
	    builder.setMessage(Message);
	    builder.show();
	}



//For translating:
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	    if (status == TextToSpeech.SUCCESS) {
	          
	        int result = tts.setLanguage(Locale.CHINESE);
	 
	        if (result == TextToSpeech.LANG_MISSING_DATA
	                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	            Log.e("TTS", "This Language is not supported");
	        } else {
	             
	            //speakOut("Ich");
	        }
	 
	    } else {
	        Log.e("TTS", "Initilization Failed!");
	    }
	}

	private void speakOut(String text) {
		tts.setSpeechRate(speechRate);
	    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	    
	}
	
	public void pinyinConvert(View view)
	{
		EditText editPinyin = (EditText)findViewById(R.id.editPinyinCharacterClassroom);
	
		
                  	if(editPinyin.getText().toString().length()==0)
                  	{
                  		 showMessage("No Pinyin Entered to Convert!!","");
                  		return;
                  	}
                  	if((editPinyin.getText().length()>7) ||editPinyin.getText().toString().length()==0  )
                  	{
                  		Toast.makeText(ReviewCharacterClassroomActivity.this,"Pinyin is Too Long, is not converted, or is empty!!!\n\nMax length of pinyin is 6 characters.",Toast.LENGTH_LONG).show();
                          return;
                  	}
                  	
                    editPinyin.setText(PinyinHelper.makePinyin(editPinyin.getText().toString()));
                  	
                    String pinyin = currentChineseCharacter.getPinyin().replace(" ", "");
                    String result = editPinyin.getText().toString();
                    if(result.equals(pinyin))
                  		  {
		                    	  Toast.makeText(ReviewCharacterClassroomActivity.this,"Pinyin Correct!!!\nWell Done!! ",Toast.LENGTH_LONG).show();
		                          return;
                  	  
                  		  }
                    else
                    {
                  	  Toast.makeText(ReviewCharacterClassroomActivity.this,"Pinyin not Correct!!!\nTry Again!!",Toast.LENGTH_LONG).show();
                        return;
                  	  
                    }
              
                 
		
	
	} 
	
	

	
}
