package com.example.mycolorchinese;


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
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import helper.ChineseCharacter;
import helper.ChineseSentence;
import helper.DatabaseHelper;

public class ReviewSentenceClassroomActivity extends Activity implements OnInitListener ,RecognitionListener {
	
private static final int NUM_ROWS = 1;
private static final int NUM_COLS = 1;
private static final int TEXT_SIZE = 200;
private static final int PINYIN_TEXT_SIZE = 70;
private static final int TRANSLATION_TEXT_SIZE = 40;
public static final String EXTRA_MESSAGE = null;
private static final float speechRate = (float) 0.8;  //1.0 = normal speed
private TextToSpeech tts;
private String Lang = "CHINESE";
private  String string;
private int  count = 0;
private Button buttonNext;

ArrayList<TextView> buttons= new ArrayList<TextView>();
ArrayList<ChineseCharacter> currentChineseCharacters = new ArrayList<ChineseCharacter>();
ArrayList<Boolean> isPinyinFlags = new ArrayList<Boolean>();
ArrayList<TableRow> tableRows= new ArrayList<TableRow>();
private int sentenceCount = 0;

private int gridWidth = 0;
private ChineseSentence currentChineseSentence;

private ProgressBar progressBar;
private SpeechRecognizer speech = null;
private Intent recognizerIntent;
private String LOG_TAG = "VoiceRecognitionActivity";
private String myLanguage = "cmn-Hans-CN";
//private boolean isPinyin = false;
private int translationCount = 0;

private ToggleButton toggleButton;
private CheckBox colorCheckBox; 
private CheckBox randomCheckBox;
ArrayList<String> listtitleListsToReview =  new  ArrayList<String>();
ArrayList<ChineseSentence> chineseSentencesToReview = new ArrayList<ChineseSentence>();
private DatabaseHelper myDb;
TableLayout table;
@Override 
protected void onCreate(Bundle savedInstanceState) 
{ 
	
			super.onCreate(savedInstanceState);
			
			
			setContentView(R.layout.activity_review_sentence_classroom);
		 
		tts = new TextToSpeech(this, this);
		 tts.setLanguage(Locale.CHINESE);
	
		 myDb = new DatabaseHelper(this);
		//R.layout.activity_review_sentence_classroom.setBackgroundColor(Color.rgb(39, 131,162));
			listtitleListsToReview =  getIntent().getStringArrayListExtra("listtitleListsToReviewName");
			
			 colorCheckBox = (CheckBox)findViewById(R.id.checkBoxToneColorsSentenceClassroom);
			colorCheckBox.setChecked(true);
			randomCheckBox = (CheckBox)findViewById(R.id.checkBoxRandomSentence);
			buttonNext = (Button)findViewById(R.id.buttonNextSentenceClassroom);
			 table = (TableLayout) findViewById(R.id.tableForSentenceLists); 
			 
			 toggleButton = (ToggleButton)findViewById(R.id.toggleButtonSentence);
		// initializeSentences();
		 setChineseSentences();
		 currentChineseSentence = chineseSentencesToReview.get(0);
		 for(int i = 0;i<currentChineseSentence.getPinyinArray().length;i++)
		 {
			 currentChineseCharacters.add(new ChineseCharacter("",currentChineseSentence.getChinesesentence().charAt(i),currentChineseSentence.getPinyin(i))); 
			 
			 
		 }
		 
		 for(int i = 0; i<currentChineseSentence.getPinyinArray().length;i++)
		 {
			 isPinyinFlags.add(false);
			 
		 }
		 
	/*	 
		 int maxLength =0;
			for(ChineseSentence temp: chineseSentencesToReview)
			{
				if(temp.getChinesesentence().length()>maxLength)
				{
					maxLength = temp.getChinesesentence().length();
				}
			}
			  



			int gridWidth = (int)Math.sqrt(currentChineseSentence.getChinesesentence().length());
*/
	//	 buttons = new TextView[gridWidth+1][gridWidth];
		 
		 progressBar = (ProgressBar )findViewById(R.id.progressBarSentence );
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
		 
		 
		 
		 
			Button buttonListen = (Button) findViewById(R.id.buttonListenSentence);
				 buttonListen.setText("Listen To Sentence ("+(sentenceCount+1)+"/"+chineseSentencesToReview.size()+")");
		   
		
		 
		populateButtons(); 
		
		this.setTitle("SENTENCE CLASSROOM...");
	//	translate();
}





public void showTranslation(View view)
{
	
	CheckBox translationCheckBox = (CheckBox) view;
	if(translationCheckBox.isChecked())
	{    
		translationCount = sentenceCount;
	
			table.removeAllViews();
		
			
		
				//	 TableLayout table = (TableLayout) findViewById(R.id.tableForVocabLists); 
				
			
		   	  TableRow tableRow = new TableRow(this); 
			     tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,
			    		                                             TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
			        table.addView(tableRow); 
			  		    	   TextView textView = new TextView(this);
						   	   textView.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
						    	   
						    	   textView.setBackgroundColor(color.transparent);
						    	   
					
						    	   textView.setPadding(0, 0, 0, 0);
						    	   textView.setTextSize(TRANSLATION_TEXT_SIZE); 
						           textView.setTextColor(Color.MAGENTA);
						    				   
							             
						           textView.setText(""+currentChineseSentence.getEnglishsentence()); 
						         //ensures default is black
						           
						              
						                       tableRow.addView(textView); 
		     
						                       tableRows.add(tableRow);
						                 
						                       		                       
			 
						              //        
							       		//		return;
		
	}//end if(translationCheckBox.isChecked())	
	else
	{
		
		 populateButtons();
	}
						                       
						                       
}

  

@Override
public void onResults(Bundle results)
{
	Log.i(LOG_TAG, "onResults");
	ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
	String text = "";
	for (String result : matches)
		text += result + "\n";

	String result = matches.get(0);
	//! .   。  . 。  ?？  ..==>punctuation currupts program at the moment...need to fix
   String trimmedResult = currentChineseSentence.getChinesesentence();
    trimmedResult = trimmedResult.replace("!", "");
  trimmedResult = trimmedResult.replace(".", "");
    trimmedResult = trimmedResult.replace("?", "");
    trimmedResult = trimmedResult.replace("？", "");
    trimmedResult = trimmedResult.replace("。", "");
    
//	if(result.charAt(0) == currentChineseCharacter.getHanzi())  
	if(result.equals(String.valueOf( trimmedResult) )  )
	{ tts.setLanguage(Locale.ENGLISH);
	//	showMessage("result: CORRECT!!!","You said: " + result+"\nans:"+trimmedResult);
	//	showMessage("result: CORRECT!!!","You said: " + result);
		speakOut("Correct!!");
		 tts.setLanguage(Locale.CHINESE);
		 
			//	
				 
	}
	else 
	{
		tts.setLanguage(Locale.ENGLISH);		
	//	showMessage("result:  DOESN'T MATCH!","You said: " + result);
	//	showMessage("result:  DOESN'T MATCH!","You said: " + result+"\nans:"+trimmedResult);	
		speakOut("Not Correct!!");
		 tts.setLanguage(Locale.CHINESE);	
	}  
	

	Toast.makeText(ReviewSentenceClassroomActivity.this, "You said: " + result,Toast.LENGTH_SHORT).show(); 
	
	

		
	
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







public void speakOut (View view)
{
	
	//CheckBox translationCheckBox = (CheckBox) findViewById(R.id.checkBoxTranslationSentenceClassroom);

		speakOut(currentChineseSentence.getChinesesentence());
	
}

public void setChineseSentences()
{

	
//		String query = "select distinct listtitle , character , pinyin from charactertable where listtitle glob '*"+listtitle1+"*'"
//				+ " or listtitle glob '*"+listtitle2+"*' or listtitle glob '*"+listtitle3+"*'"; 
    	
		String query = "";
		for(int i=0; i<listtitleListsToReview.size();i++)
	   {
			
			
			if(i==0)
			{	
				 query = query + "select distinct listtitle , chinese , english, pinyin from sentencetable where listtitle glob '*"
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
         //   showMessage("Error","Nothing found");
           
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
          
        	
        	String replace = res.getString(1);
        	 replace = 		replace.replace("?", "");
	          replace = replace.replace(".", "");
	          replace = replace.replace("？", "");
	          replace = replace.replace(" 。", "");
	          replace = replace.replace(".", "");
	          replace = replace.replace("？", "");
	          replace = replace.replace(" ", "");
	          replace = replace.replace("？", "");
	          replace = replace.replace("！", "");
	          replace = replace.replace(",", "");
            buffer.append("listname :"+ res.getString(0)+"\n");
            buffer.append("chinese :"+ replace+"\n");
           buffer.append("english :"+ res.getString(2)+"\n");
           buffer.append("pinyin :"+ res.getString(3)+"\n\n");
           
        
           ChineseSentence chineseSentence = new ChineseSentence(res.getString(0),replace,res.getString(2), res.getString(3));
          
           chineseSentencesToReview.add(chineseSentence);
           
           
        }

        // Show all data
   //   showMessage("Sentence Lists:",buffer.toString());
      
        
      }
      catch(java.lang.IllegalStateException e)
       {
     	  showMessage("Error:","Array out of bounds!!");
       }
   
		
	
	

  
  
  if(res.getCount() == 0) {
      // show message
 //     showMessage("Error","Nothing found");
      return;
  }


	
}




private void populateButtons()
{  
	buttons.clear();
   currentChineseCharacters.clear();
   isPinyinFlags.clear();
   
	table.removeAllViews();
CheckBox translationCheckBox = (CheckBox) findViewById(R.id.checkBoxTranslationSentenceClassroom);
	
	for(int i = 0; i<currentChineseSentence.getPinyinArray().length;i++)
	{
		 isPinyinFlags.add(false);
		 
	}
	
	
	 table.clearAnimation();
		table.refreshDrawableState();
		table.bringChildToFront(table);
	
	String replaceChinese ="";
	
	buttons.clear();
	//currentChineseCharacters.clear();
	
	
//      currentChineseSentence = chineseSentences.get(1);
//    currentChineseSentence =    chineseSentencesToReview.get(1);
   currentChineseSentence =    chineseSentencesToReview.get(sentenceCount);

	final int gridWidth = (int)Math.sqrt(currentChineseSentence.getChinesesentence().length());

	
    
    
	     //    rand = (int) (  (Math.random()*( chineseSentencesToReview.size())) );

	  //      currentChineseSentence = chineseSentences.get(1);
	    //    currentChineseSentence =    chineseSentencesToReview.get(1);
	      //  currentChineseSentence =    chineseSentencesToReview.get(rand);
	          replaceChinese = currentChineseSentence.getChinesesentence().replace("?", "");
	          replaceChinese = replaceChinese.replace(".", "");
	          replaceChinese = replaceChinese.replace("？", "");
	          replaceChinese = replaceChinese.replace("。", "");
	          replaceChinese = replaceChinese.replace(".", "");
	          replaceChinese = replaceChinese.replace("？", "");
	          replaceChinese = replaceChinese.replace(" ", "");
	          replaceChinese = replaceChinese.replace("？", "");
	          replaceChinese = replaceChinese.replace("！", "");
	          replaceChinese = replaceChinese.replace(",", "");
	       
	 //       currentChineseSentence.setChinesesentence(replaceChinese);
	   //     buttons = new TextView[gridWidth][gridWidth+1];
	     //   showMessage("replaceChinese: "+replaceChinese,"pinyin: "+currentChineseSentence.getPinyinsentence());
     
	
  for (int row = 0; row < gridWidth+1; row++) 
     {
		  TableRow tableRow = new TableRow(this); 
	     tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,
	    		                                             TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
	        table.addView(tableRow); 
	  
	       for (int col = 0; col < gridWidth+1; col++)
	         {    final int num = ((gridWidth+1)*row)+col;
	    	     if(num<currentChineseSentence.getPinyinArray().length)
	    	     {
	    	    	 
				    	   TextView textView = new TextView(this);
				    	   final int FINAL_COL = col; final int FINAL_ROW = row;
				    	   textView.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
				    	   
				    	   textView.setBackgroundColor(color.transparent);
				    	   
			
				    	   textView.setPadding(0, 0, 0, 0);
				             
				            
				        
				          //    int startIntCharacterListAsString = Integer.decode("0x"+ Integer.toHexString(characterListAsString.charAt(0)));
				          //    int endIntCharacterListAsString = Integer.decode("0x"+ Integer.toHexString(characterListAsString.charAt(characterListAsString.length()-1)));
			
				                    
				            //  ch = characterListAsString.charAt(charCount);
				    	   if (colorCheckBox.isChecked())
				        	 {   
				              if(currentChineseSentence.getPinyinColor(num).equals(currentChineseSentence.getColors()[1]))
				            	  textView.setTextColor(Color.RED);
				              else if(currentChineseSentence.getPinyinColor(num).equals(currentChineseSentence.getColors()[2]))
				            	  textView.setTextColor(Color.rgb(255, 190, 0));
				              else if(currentChineseSentence.getPinyinColor(num).equals(currentChineseSentence.getColors()[3]))
				            	  textView.setTextColor(Color.GREEN);
				              else if(currentChineseSentence.getPinyinColor(num).equals(currentChineseSentence.getColors()[4]))
				            	  textView.setTextColor(Color.BLUE);
				        	 }
				              else
				                  textView.setTextColor(Color.BLACK);
				    	   
				              
				              textView.setTextSize(TEXT_SIZE/(gridWidth+1));
				        //      if(row ==0)
				            //  textView.setText(""+currentChineseSentence.getPinyinColor(col).charAt(0)); 
				      
				              String pinyinSentence = currentChineseSentence.getPinyinsentence();
							/*   
				              if(  pinyinSentence.contains("。"))
								  pinyinSentence = pinyinSentence.replace("。", " 。"); 
							  else  if(  pinyinSentence.contains("?") )
								  pinyinSentence = pinyinSentence.replace("？", " ？");
									else  if(  pinyinSentence.contains(".") )
										  pinyinSentence = pinyinSentence.replace(".", " .");
									 else  if(  pinyinSentence.contains("!") )
										  pinyinSentence = pinyinSentence.replace("!", " !");
									  
				              */
				              
				              
				              
				       /*       
					   if(num==currentChineseSentence.getPinyinArray().length-1)
					   {        
							 
							
					   }
					   else
					   {
						   textView.setText(""+replaceChinese.charAt(num)); 
						   
					   }
				        */    	  
					   
					   
					   
				               // Make text not clip on small
				              textView.setPadding(0, 0, 0, 0); 
				             
				            	 textView.setTextSize(TEXT_SIZE/(gridWidth+1));
				                 textView.setText(""+currentChineseSentence.getChinesesentence().charAt(num));
				            
				             //set currentChineseCharacters
				              currentChineseCharacters.add(new ChineseCharacter("currentChineseCharacters",currentChineseSentence.getChinesesentence().charAt(num),currentChineseSentence.getPinyin(num)));	 
				              isPinyinFlags.add(false);
				            
				            
				         textView.setOnClickListener(new View.OnClickListener() 
			                  {
				            	   @Override public void onClick(View v) 
				                      {  
				            		   gridButtonClicked(num, gridWidth); 
				            		   } 
				            	   }); 
				              
				              buttonNext.setOnClickListener(new View.OnClickListener() 
			                  {
			            	   @Override public void onClick(View v) 
			                      {  
			            		   next(); 
			                   		     
			            		    
			            		   } 
			            	   }); 
				              
				                       tableRow.addView(textView); 
				                       
				                  //     buttons[row][col] = textView; 
				                       buttons.add(textView);
				                       tableRows.add(tableRow);
				                       
	    	     }//end if   
	    	     else
	    	    	 return;
	           } //end for
	        }//end for
	       

}


private void gridButtonClicked(int index, int gridWidth) { 
	
	   
	
	
	TextView button = buttons.get(index);
	
	//Toast.makeText(this, "ch: " + currentChineseCharacters.get(index).getPinyin(),Toast.LENGTH_SHORT).show(); 
	
			
			lockButtonSizes(index); // Does not scale image.

			String pinyin = currentChineseSentence.getPinyinsentence().replace(" ","");
			boolean check = false;
			if(currentChineseSentence.getChinesesentence().contains(pinyin))
			{
				speakOut(String.valueOf( currentChineseCharacters.get(index).getHanzi()));
				
				return;
			//	button.setTextSize(TEXT_SIZE/gridWidth);
			//	check = true;
				
			}
			
	//	showMessage("pinyin:",pinyin+"\nare equal: "+check);   	


		if(isPinyinFlags.get(index)==false)
		{
			speakOut(String.valueOf( currentChineseCharacters.get(index).getHanzi()));
				
			isPinyinFlags.add(index, true);
			 button.setTextSize(PINYIN_TEXT_SIZE/gridWidth);
			 button.setText(""+currentChineseCharacters.get(index).getPinyin());
			 
			
		}
		else if(isPinyinFlags.get(index)==true)
		{
			isPinyinFlags.add(index, false);
			 button.setTextSize(TEXT_SIZE/(gridWidth+1));
			 button.setText(""+currentChineseCharacters.get(index).getHanzi());
			 
		}
		
		
}

private void lockButtonSizes(int num) { 
	 for (int row = 0; row != gridWidth; row++) 
	   { 
		 for (int col = 0; col != gridWidth+1; col++) 
           { 
			 
			     TextView button = buttons.get(num); 
			     int width = button.getWidth(); 
			     button.setMinWidth(width);
				    button.setMaxWidth(width);
				     int height = button.getHeight(); 
				      button.setMinHeight(height);
				      button.setMaxHeight(height);
		     }
	   }
 }

public void  next()
{
	
	isPinyinFlags.clear();
	// Lock Button Sizes:  

	

          //    table.removeAllViews();
	
         //   toggleButton.
	
	//speakOut(currentChineseSentence.getChinesesentence());
	int rand = 0;
    
//	buttons.clear();
//	currentChineseCharacters.clear();
	 
	Button buttonListen = (Button) findViewById(R.id.buttonListenSentence);
	
	if(randomCheckBox.isChecked())
	{
		rand = (int) (  (Math.random()*( chineseSentencesToReview.size())) );
		sentenceCount  = rand;
		  currentChineseSentence =    chineseSentencesToReview.get(rand);
		  buttonListen.setText("Listen To Sentence ("+(sentenceCount+1)+"/"+chineseSentencesToReview.size()+")");
	}
	else
	{
		sentenceCount++;
		if(sentenceCount == chineseSentencesToReview.size() )
			sentenceCount = 0;
		
		  currentChineseSentence =    chineseSentencesToReview.get(sentenceCount);
		  buttonListen.setText("Listen To Sentence ("+(sentenceCount+1)+"/"+chineseSentencesToReview.size()+")");
	}
    
	
	
	
 

	
	
	
	CheckBox translationCheckBox = (CheckBox) findViewById(R.id.checkBoxTranslationSentenceClassroom);
	if(translationCheckBox.isChecked())
	{
	   showTranslation(translationCheckBox);
	 
	}
	else
	{
		populateButtons();
	}
	

	
	
}


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



public void showMessage(String title,String Message){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(Message);
    builder.show();
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



}