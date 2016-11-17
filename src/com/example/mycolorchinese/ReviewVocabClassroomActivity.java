package com.example.mycolorchinese;




import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import helper.ChineseCharacter;
import helper.ChineseVocab;
import helper.DatabaseHelper;

public class ReviewVocabClassroomActivity extends Activity implements OnInitListener ,RecognitionListener {
	private int	  num =0;
	private static final int NUM_ROWS = 1;
private static final int NUM_COLS = 1;
private static final int TEXT_SIZE = 140;
private static final int PINYIN_TEXT_SIZE = 50;
private static final int TRANSLATION_TEXT_SIZE = 40;

public static final String EXTRA_MESSAGE = null;
private static final float speechRate = (float) 0.8;  //1.0 = normal speed
private TextToSpeech tts;
private String Lang = "CHINESE";
//private String Lang = "ENGLISH";
private  String string;
private int  count = 0;
private Button buttonNext;
private Button buttonListen;

ArrayList<TextView> buttons= new ArrayList<TextView>();
ArrayList<ChineseCharacter> currentChineseCharacters = new ArrayList<ChineseCharacter>();
ArrayList<TableRow> tableRows= new ArrayList<TableRow>();
private int vocabCount = 0;
private boolean isPinyin = false;
private int gridWidth = 0;
ChineseVocab currentChineseVocab;
private int imageFolderLength = 0;
private ProgressBar progressBar;
private SpeechRecognizer speech = null;
private Intent recognizerIntent;
private String LOG_TAG = "VoiceRecognitionActivity";
//https://www.ietf.org/assignments/language-tags/language-tags.xml
//https://www.zhihu.com/question/20797118
//en-GB-oed;en-US;cmn-Hans-CN
//cmn-Hans-CN 普通话 (简体, 中国大陆)
//cmn-Hant-TW 普通话
//private String myLanguage = "en-US";
private String myLanguage = "cmn-Hans-CN";

private boolean hasStarted = false;

private ToggleButton toggleButton;
private CheckBox colorCheckBox; 
private CheckBox randomCheckBox;
ArrayList<String> listtitleListsToReview =  new  ArrayList<String>();
ArrayList<ChineseVocab> chineseVocabToReview = new ArrayList<ChineseVocab>();
private DatabaseHelper myDb;
TableLayout table;

private Context context;



@Override 
protected void onCreate(Bundle savedInstanceState) 
{ 
	
			super.onCreate(savedInstanceState);
			 context = this.getApplicationContext();
			
			setContentView(R.layout.activity_review_vocab_classroom);
		 
		tts = new TextToSpeech(this, this);
	//	 tts.setLanguage(Locale.CHINESE);
		tts.setLanguage(Locale.CHINESE);
		
		 myDb = new DatabaseHelper(this);
		
			listtitleListsToReview =  getIntent().getStringArrayListExtra("listtitleListsToReviewName");
			
			 colorCheckBox = (CheckBox)findViewById(R.id.checkBoxToneColorsVocabClassroom);
			colorCheckBox.setChecked(true);
			randomCheckBox = (CheckBox)findViewById(R.id.checkBoxRandomVocab);
			buttonNext = (Button)findViewById(R.id.buttonNextVocabClassroom);
			buttonListen  = (Button)findViewById(R.id.buttonListenVocab);
			
			
			 table = (TableLayout) findViewById(R.id.tableForVocabLists); 
			 
			 toggleButton = (ToggleButton)findViewById(R.id.toggleButtonVocab);
			 setChinesevocab();
			 
		
			 
		 
		 progressBar = (ProgressBar )findViewById(R.id.progressBarVocab );
		 progressBar.setVisibility(View.INVISIBLE);
			speech = SpeechRecognizer.createSpeechRecognizer(this);
			speech.setRecognitionListener(this);

			recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			
		//	myLanguage = "cmn-Hans-CN";
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, myLanguage);
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, myLanguage);
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, myLanguage);
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

			toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
				
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
		 
		 
		 
		 
		 
		 
		 
		 
			 
		 
		 
	
				 
				 
				 ImageView image = (ImageView) findViewById(R.id.imageViewVocabClassroom);
				 new Logo().execute();
			
		   image.setOnClickListener(new View.OnClickListener() {
			            @Override
			            public void onClick(View v) {
			            	new Logo().execute();
			            }
			        });
		 
		
		 
		populateButtons(); 
		
		buttonListen.setText("Listen To Vocab ("+(vocabCount+1)+"/"+chineseVocabToReview.size()+")"+
				 ":\n"+"(List: "+currentChineseVocab.getListtitle()+")");
		
		
		this.setTitle("VOCABULARY CLASSROOM...");
	//	
		
		
		hasStarted = true;
		
		
	//	translate();
}

public void showTranslation(View view)
{
	CheckBox translationCheckBox = (CheckBox) view;
	if(translationCheckBox.isChecked())
	{
	
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
						    				   
							             
						           textView.setText(""+currentChineseVocab.getEnglishvocab()); 
						        
						              
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
   String trimmedResult = currentChineseVocab.getChinesevocab();
    trimmedResult = trimmedResult.replace("!", "");
  trimmedResult = trimmedResult.replace(".", "");
    trimmedResult = trimmedResult.replace("?", "");
    trimmedResult = trimmedResult.replace("？", "");
    trimmedResult = trimmedResult.replace("。", "");
    trimmedResult = trimmedResult.replace(" ", "");
    
//	if(result.charAt(0) == currentChineseCharacter.getHanzi())  
	//if(result.equals(String.valueOf( trimmedResult) )  )
    
    if(result.matches(trimmedResult))
	{ tts.setLanguage(Locale.ENGLISH);
	//	showMessage("result: CORRECT!!!","You said: " + result+"\nans:"+trimmedResult);
	//	showMessage("result: CORRECT!!!","You said: " + result);
		speakOut("Correct!!");
		 tts.setLanguage(Locale.CHINESE);
	}
	else
	{
		 tts.setLanguage(Locale.ENGLISH);
	//	showMessage("result:  DOESN'T MATCH!","You said: " + result);
	//	showMessage("result:  DOESN'T MATCH!","You said: " + result+"\nans:"+trimmedResult);	
	//	speakOut("Not Correct!!");
		 tts.setLanguage(Locale.CHINESE);
	}  
	
	Toast.makeText(ReviewVocabClassroomActivity.this, "You said: " + result
			+ "\nresult.matches(trimmedResult): "+result.matches(trimmedResult)
			+ "\ntrimmedResult: " +trimmedResult,Toast.LENGTH_SHORT).show(); 
	
	
	

	 
	

		
	
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

	
		speakOut(currentChineseVocab.getChinesevocab());
		
	
	
}

public void setChinesevocab()
{

	
//		String query = "select distinct listtitle , character , pinyin from charactertable where listtitle glob '*"+listtitle1+"*'"
//				+ " or listtitle glob '*"+listtitle2+"*' or listtitle glob '*"+listtitle3+"*'"; 
    	
		String query = "";
		for(int i=0; i<listtitleListsToReview.size();i++)
	   {
			
			
			if(i==0)
			{	
				 query = query + "select distinct listtitle , chinese , english, pinyin from vocabtable where listtitle glob '*"
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
           
           ChineseVocab chineseVocab = null;
           
           
       if(listtitleListsToReview.contains(res.getString(0).replace(" ","")))
          {
                chineseVocab = new ChineseVocab(res.getString(0),replace,res.getString(2), res.getString(3));
                 chineseVocabToReview.add(chineseVocab); 
          
          }
           
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
	table.removeAllViews();
	
	
	CheckBox translationCheckBox = (CheckBox) findViewById(R.id.checkBoxTranslationVocabClassroom);
	
		String replaceChinese ="";
	
	
	
	
//      currentChineseSentence = chineseSentences.get(1);
//    currentChineseSentence =    chineseSentencesToReview.get(1);
	currentChineseVocab =    chineseVocabToReview.get(vocabCount);
	
	
	buttonListen.setText("Listen To Vocab ("+(vocabCount+1)+"/"+chineseVocabToReview.size()+")"+
			 ":\n"+"(List: "+currentChineseVocab.getListtitle()+")");

	
	
	final int gridWidth = (int)Math.sqrt(currentChineseVocab.getChinesevocab().length());

	
    
    
	     //    rand = (int) (  (Math.random()*( chineseSentencesToReview.size())) );

	  //      currentChineseSentence = chineseSentences.get(1);
	    //    currentChineseSentence =    chineseSentencesToReview.get(1);
	      //  currentChineseSentence =    chineseSentencesToReview.get(rand);
	          replaceChinese = currentChineseVocab.getChinesevocab().replace("?", "");
	          replaceChinese = replaceChinese.replace(".", "");
	          replaceChinese = replaceChinese.replace("？", "");
	          replaceChinese = replaceChinese.replace(" 。", "");
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
	    	     if(num<currentChineseVocab.getPinyinArray().length)
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
				              if(currentChineseVocab.getPinyinColor(num).equals(currentChineseVocab.getColors()[1]))
				            	  textView.setTextColor(Color.RED);
				              else if(currentChineseVocab.getPinyinColor(num).equals(currentChineseVocab.getColors()[2]))
				            	  textView.setTextColor(Color.rgb(255, 190, 0));
				              else if(currentChineseVocab.getPinyinColor(num).equals(currentChineseVocab.getColors()[3]))
				            	  textView.setTextColor(Color.GREEN);
				              else if(currentChineseVocab.getPinyinColor(num).equals(currentChineseVocab.getColors()[4]))
				            	  textView.setTextColor(Color.BLUE);
				        	 }
				              else
				                  textView.setTextColor(Color.BLACK);
				    	   
				              
				              textView.setTextSize(TEXT_SIZE/(gridWidth+1));
				        //      if(row ==0)
				            //  textView.setText(""+currentChineseSentence.getPinyinColor(col).charAt(0)); 
				      
				        
					   
					   
				               // Make text not clip on small
				              textView.setPadding(0, 0, 0, 0); 
				             
				              textView.setText(""+currentChineseVocab.getChinesevocab().charAt(num)); 
				                			   
			            		     
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
			            		//   gridButtonClicked(num, gridWidth); 
			            		     
			            //		   buttons = new TextView[gridWidth][gridWidth+1];
			           	   //     showMessage("replaceChinese: ","pinyin: "+currentChineseSentence.getPinyinsentence());
			            		//  populateButtons();
			            	//	  showMessage("buttons.size(): "+buttons.size(),"");
			            		     
			            		    
			            		   } 
			            	   }); 
				              
				                       tableRow.addView(textView); 
				                       
				                  //     buttons[row][col] = textView; 
				                       buttons.add(textView);
				                       currentChineseCharacters.add(new ChineseCharacter("currentChineseCharacters",currentChineseVocab.getChinesevocab().charAt(num),currentChineseVocab.getPinyin(num)));	   
								         
				                       tableRows.add(tableRow);
				                       
	    	     }//end if   
	    	     else
	    	    	 return;
	           } //end for
	        }//end for
	       

  
 
}


private void gridButtonClicked(int index, int gridWidth) { 
	   
	isPinyin = !isPinyin;
	
	
	TextView button = buttons.get(index);
	ChineseCharacter ch = currentChineseCharacters.get(index);
//	Toast.makeText(this, "ch: " + currentChineseCharacters.get(index).getPinyin(),Toast.LENGTH_SHORT).show(); 
	//  char ch = button.getText().toString().charAt(0);
		//	int pinyinIndex =  currentChineseVocab.getChinesevocab().indexOf(ch.getHanzi());
			// Lock Button Sizes: 
			
			lockButtonSizes(index); // Does not scale image.

			
 
// speakOut(String.valueOf( button.getText()));
		if(isPinyin)
		{
			speakOut(String.valueOf( currentChineseCharacters.get(index).getHanzi()));
			String pinyin = currentChineseVocab.getPinyinvocab().replace(" ","");
			boolean check = false;
			if(currentChineseVocab.getChinesevocab().contains(pinyin))
			{
				return;
			//	button.setTextSize(TEXT_SIZE/gridWidth);
			//	check = true;
				
			}
			else
			{
				button.setTextSize(PINYIN_TEXT_SIZE/gridWidth);
			}
			//showMessage("pinyin:",pinyin+"\nare equal: "+check);   
			
			// button.setText(currentChineseCharacters.get(index).getPinyin());
			// button.setText(""+currentChineseVocab.getPinyinArray()[pinyinIndex]);
			 button.setText(""+currentChineseCharacters.get(index).getPinyin());
			 
			// button.setClickable(false);
		}
		else if(!isPinyin)
		{
			//speakOut(String.valueOf( currentChineseCharacters.get(num).getHanzi()));
				
		
			 button.setTextSize(TEXT_SIZE/(gridWidth+1));
		//	 button.setText(ch.getHanzi());
			 button.setText(""+currentChineseCharacters.get(index).getHanzi());
			// button.setClickable(false);
		}
 
}

private void showMessage() {
	// TODO Auto-generated method stub
	
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
	
//	 showMessage("ListTitle:",chineseVocabToReview.get(vocabCount).getListtitle());
	new Logo().execute();
	
	 
	
}


@Override
public void onInit(int status) {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
    if (status == TextToSpeech.SUCCESS) {
          
   //     int result = tts.setLanguage(Locale.CHINESE);
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







private class Logo extends AsyncTask<Void, Void, Void> {
    Bitmap bitmap;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        
		if(hasStarted)	 
		{
			        isPinyin = false;
			      	// Lock Button Sizes:  
			
			
			                    table.removeAllViews();
			      	
			               //   toggleButton.
			      	
			      	//speakOut(currentChineseSentence.getChinesesentence());
			      	int rand = 0;
			          
			      	
			      	buttons.clear();
			      	currentChineseCharacters.clear();
			      	 
			      	if(randomCheckBox.isChecked())
			      	{
			      		rand = (int) (  (Math.random()*( chineseVocabToReview.size())) );
			      		vocabCount  = rand;
			      		  currentChineseVocab =    chineseVocabToReview.get(vocabCount);
			      	}
			      	else
			      	{
			      		
			      		vocabCount++;
			      		if(vocabCount == chineseVocabToReview.size() )
			      			vocabCount = 0;
			      		
			      		currentChineseVocab =    chineseVocabToReview.get(vocabCount);
			      	}
			          
			       
			      	Button buttonListen = (Button) findViewById(R.id.buttonListenVocab);
			      	
			      	
			      	if(randomCheckBox.isChecked())
			      	{
			          rand = (int) (  (Math.random()*( chineseVocabToReview.size())) );
			          vocabCount = rand;
			          buttonListen.setText("Listen To Sentence ("+(vocabCount+1)+"/"+chineseVocabToReview.size()+")");
			      	}
			      	else
			      	{
			      		
			      		 buttonListen.setText("Listen To Vocab ("+(vocabCount+1)+"/"+chineseVocabToReview.size()+")");
			          }
			
			      	CheckBox translationCheckBox = (CheckBox) findViewById(R.id.checkBoxTranslationVocabClassroom);
			      	if(translationCheckBox.isChecked())
			      	{
			      	   showTranslation(translationCheckBox);
			      	 
			      	}
			      	else
			      	{
			      		populateButtons();
			      	}
			      	
    }//end if(hasStarted)
       
    //    progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
    	
    	
    	
    	

       
    	
    	
       
        	
        	 String replaceChinese = currentChineseVocab.getEnglishvocab().replace("N.", "");
	          replaceChinese = replaceChinese.replace("V.", "");
	          replaceChinese = replaceChinese.replace("M.W.", "");
	          replaceChinese = replaceChinese.replace("Prep.", "");
	          replaceChinese = replaceChinese.replace("Adj.", ""); 
	         replaceChinese = replaceChinese.replace("Int.", ""); 
	        replaceChinese = replaceChinese.replace("Pron.", ""); 
	       replaceChinese = replaceChinese.replace("Conj.", ""); 
	      replaceChinese = replaceChinese.replace("Aux.", ""); 
        	
        //	search_string = editText.getText().toString();
        	
	   //   String search_string =  replaceChinese +" "+ currentChineseVocab.getChinesevocab();
        	
        	
      // 	   String  url = "https://www.google.com/search?tbm=isch&q="+search_string;
     //  	url = 	"https://www.flickr.com/search/?ytcheck=1&text="+search_string;
      //   url = "http://www.bing.com/images/search?q="+search_string;;
      //      Document document = Jsoup.connect(url).get();
          
            
        /*    
         Elements images = document.select("img[src]");  
          
            for (Element image : images) {  
                System.out.println("\nsrc : "+ image.attr("src"));  
                System.out.println("height : " + image.attr("height"));  
                System.out.println("width : " + image.attr("width"));  
                System.out.println("alt : " + image.attr("alt"));  
                System.out.println("\n"+document.location());
                
               
            }
            
           */
      //     Element img = document.select("img").get(19);
           
      	//  num = (int) (Math.random()*20);
        
      //     Element img = document.select("img").get(num);
         //  prevImageNum = num;
           
       //    String srcValue = img.attr("src");
       //    String srcValue = img.attr("src");
           
           
           
        //  showMessage("srcValue: "+srcValue,"");
           
         //   InputStream input = new URL(srcValue).openStream();
        //    bitmap = BitmapFactory.decodeStream(input);
	      String search_string = currentChineseVocab.getChinesevocab(); 
        	
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ColorChinese/images";
           
            File imageFolder = new File(path +"/"+	search_string);
            
            if(!imageFolder.canRead())
            {
            	
              //   Toast.makeText(ReviewVocabClassroomActivity.this,"This Vocab has no images!: ",Toast.LENGTH_LONG).show();
               
                BitmapFactory.Options options = new BitmapFactory.Options();
           	options.inPreferredConfig = Bitmap.Config.ARGB_8888;
         //  	String url = path +"/"+	"125"+"/"+"125_3.jpg";
            //	  bitmap = BitmapFactory.decodeFile( url, options);
           // context = this.getApplicationContext();
          
                int id = R.drawable.blank; 
                bitmap = BitmapFactory.decodeResource( context.getResources(), id);
            	return null;
            	
            	
               
                   
                
            }
            
       	     num = (int) (Math.random()*imageFolder.listFiles().length);  
      
           // if(num<0) num =0;
            
         //   if(num >= imageFolder.listFiles().length) num=0;
            
           String url = path +"/"+	search_string+"/"+imageFolder.listFiles()[num].getName();
           
      //     num++;
           
             
              //InputStream input = new URL(url).openStream();
           //   InputStream stream = ImageViewActivity.class.getClassLoader().getResourceAsStream(url);
          	
            BitmapFactory.Options options = new BitmapFactory.Options();
        	options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        	  bitmap = BitmapFactory.decodeFile(url, options);
            
  
            
          
          
            
            
         imageFolderLength = imageFolder.listFiles().length;
            
            
            

        

        return null;
   }

    @Override
    protected void onPostExecute(Void aVoid) {
    	
  //  	Toast.makeText(ReviewVocabClassroomActivity.this,"num: "+num+" ,imageFolderLength: "+imageFolderLength,Toast.LENGTH_LONG).show();
        
    	
    	  ImageView logoImg = (ImageView) findViewById(R.id.imageViewVocabClassroom);
    	  if(bitmap==null)
    	  {
    		  logoImg.setBackgroundResource(R.drawable.blank);
    		  
    	  }
    	  else{
              logoImg.setImageBitmap(bitmap);

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

}