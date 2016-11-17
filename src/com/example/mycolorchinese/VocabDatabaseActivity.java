package com.example.mycolorchinese;






import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;


//original code: http://www.codebind.com/android-tutorials-and-examples/android-sqlite-tutorial-example/

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import helper.ChineseSentence;
import helper.ChineseVocab;
import helper.DatabaseHelper;
import helper.DownloadImages;
import helper.LoadSentencesFromFile;
import helper.LoadVocabFromFile;
import helper.PinyinHelper;
import helper.TranslateTool;

public class VocabDatabaseActivity  extends Activity  {
	final Context context = this;
	private String translation = "";
	private String textToTranslate ="";
	
	String somelisttitle = "";
	String somechinese = "";
	String somepinyin = "";
	String someenglish = "";
	
	Language	lang = Language.CHINESE_SIMPLIFIED;
	
	private boolean pastePinyin = false;
	private DatabaseHelper myDb;
	LinearLayout layout1;
	LinearLayout buttonLayout,buttonLayout2,buttonLayout3;
	LinearLayout row1Layout;
	LinearLayout row2Layout;
	LinearLayout row3Layout;
	LinearLayout row4Layout;
	LinearLayout row5Layout;
	LinearLayout row6Layout;
	LinearLayout queryLayout;

	TextView idLabel,listtitleLabel,characterLabel, pinyinLabel,pinyinInstructionLabel , englishLabel;
    EditText editListtitle, editCharacter, editPinyin ,editTextId , editQuery, editEnglish;
    Button btnAddData;
    Button btnUpdate;
    Button btnDelete,btnDeleteAll, btnViewList,btnViewPinyinByCharacter;

    Button btnviewAll;
	Button button2;
	Button button3;
	Button buttonPinyinCovert, buttonGetPinyin,buttonSuggestion, buttonQuery, buttonTranslate;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		somelisttitle =  getIntent().getStringExtra("aListtitle");
		somechinese =  getIntent().getStringExtra("aChinese");
		somepinyin =  getIntent().getStringExtra("aPinyin");
		someenglish =  getIntent().getStringExtra("aEnglish");
		
		Color color = new Color();
		//Font font;
		
		
		layout1 = new LinearLayout(this);
		buttonLayout = new LinearLayout(this);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout2 = new LinearLayout(this);
		buttonLayout2.setOrientation(LinearLayout.HORIZONTAL);
		buttonLayout2.setGravity(Gravity.CENTER_HORIZONTAL);
		
		
		buttonLayout3 = new LinearLayout(this);
		buttonLayout3.setOrientation(LinearLayout.HORIZONTAL);
		buttonLayout3.setGravity(Gravity.CENTER_HORIZONTAL);
		
		row1Layout = new LinearLayout(this);
		row1Layout.setOrientation(LinearLayout.HORIZONTAL);
		row1Layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		row2Layout = new LinearLayout(this);
		row2Layout.setOrientation(LinearLayout.HORIZONTAL);
		row2Layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		row3Layout = new LinearLayout(this);
		row3Layout.setOrientation(LinearLayout.HORIZONTAL);
		row3Layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		row4Layout = new LinearLayout(this);
		row4Layout.setOrientation(LinearLayout.HORIZONTAL);
		row4Layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		row5Layout = new LinearLayout(this);
		row5Layout.setOrientation(LinearLayout.HORIZONTAL);
		row5Layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		row6Layout = new LinearLayout(this);
		row6Layout.setOrientation(LinearLayout.HORIZONTAL);
		row6Layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		queryLayout = new LinearLayout(this);
		queryLayout.setOrientation(LinearLayout.HORIZONTAL);
		queryLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		layout1.setBackgroundColor(Color.rgb(255,255,255));
		
		idLabel = new TextView(this);
		idLabel.setText("ID: ");
		listtitleLabel = new TextView(this);
		listtitleLabel.setText("Listtitle: ");
		characterLabel = new TextView(this);
		characterLabel.setText("Chinese: ");
		
		englishLabel = new TextView(this);
		englishLabel.setText("English: ");
		pinyinLabel = new TextView(this);
		pinyinLabel.setText("Pinyin:");
		pinyinInstructionLabel = new TextView(this);
		pinyinInstructionLabel.setText("ex: wo3 = wǒ,shi4 = shì,xue2 = xué,sheng1 = shēng");
		
		
		button2 = new Button(this);
		button3 = new Button(this);
		buttonPinyinCovert = new Button(this);
		buttonPinyinCovert.setText("Convert To Pinyin");
		
		buttonGetPinyin = new Button(this);
		buttonGetPinyin.setText("Go Get Pinyin");
		
		
		
		buttonPinyinCovert.setBackgroundColor(Color.rgb(255, 30,10));
		buttonPinyinCovert.setWidth(500);
		
		buttonGetPinyin.setBackgroundColor(Color.rgb(0, 234,215));
		buttonGetPinyin.setWidth(500);
		
		buttonSuggestion= new Button(this);
		buttonSuggestion.setText("Get Sentence Suggestion");
		buttonSuggestion.setBackgroundColor(Color.rgb(0, 120,215));
		buttonSuggestion.setWidth(500);
	
		buttonTranslate = new Button(this);
		buttonTranslate.setText("Translate");
		buttonTranslate.setBackgroundColor(Color.rgb(255,120,10));
		buttonTranslate.setWidth(500);
		
		buttonQuery = new Button(this);
		buttonQuery.setText("HOME");
		editListtitle = new EditText(this);
		
		editCharacter = new EditText(this);
		editEnglish = new EditText(this);
		editPinyin = new EditText(this);
		
		editCharacter.setWidth(500);
		editEnglish.setWidth(500);
		editPinyin.setWidth(500);
		
		
			
	
		editQuery = new EditText(this);
	//	editQuery.setWidth(500);
		editQuery.setHint("ENTER SQLITE QUERY");
		
	
		editTextId = new EditText(this);
		btnAddData = new Button(this);
		btnAddData.setText("Add Data");
		btnviewAll = new Button(this);
		btnviewAll.setText("view All");
		btnUpdate = new Button(this);
		btnUpdate.setText("Update");
		btnDelete = new Button(this);
		btnDelete.setText("Delete");
		
		/*
		editTextId.setText("");
		editListtitle.setText("");
		editCharacter.setText("");
		editPinyin.setText("");
		editQuery.setText("");
		*/
		
		btnDeleteAll= new Button(this);
		btnDeleteAll.setText("Delete All");
		btnViewList= new Button(this);
		btnViewList.setText("View List");
		btnViewPinyinByCharacter= new Button(this);
		btnViewPinyinByCharacter.setText("View Listtitles");
		
	
		
		
		buttonQuery.setBackgroundColor(Color.rgb(60, 120,170));
		buttonQuery.setWidth(600);
		
		
		
		 
		buttonSuggestion= new Button(this);
		buttonSuggestion.setText("Get Sentence Suggestion");
		buttonSuggestion.setBackgroundColor(Color.rgb(0, 120,215));
		buttonSuggestion.setWidth(600);
		
		
		editListtitle.setTextColor(Color.BLACK);
		editCharacter.setTextColor(Color.BLACK);
		
		editPinyin.setTextColor(Color.BLACK);
	
		editTextId.setTextColor(Color.BLACK);
		editTextId.setInputType(InputType.TYPE_CLASS_NUMBER);
		
	
		RelativeLayout.LayoutParams buttonDetails = new RelativeLayout.LayoutParams(
				
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
				
				
				);
		
RelativeLayout.LayoutParams textDetails = new RelativeLayout.LayoutParams(
				
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
				
				
				);
		
		layout1.setOrientation(LinearLayout.VERTICAL);
		layout1.setGravity(Gravity.CENTER_HORIZONTAL);
	
		
		buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
		buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);
		
		
		
		
	
		button2.setBackgroundColor(Color.rgb(30, 255,50));
		button3.setBackgroundColor(Color.rgb(255, 30,50));
		button2.setText(" File Manager ");
		button3.setText(" Load Default Lists  ");
		
		
		buttonLayout.addView(btnAddData,buttonDetails);
		buttonLayout.addView(btnviewAll,buttonDetails);
		buttonLayout.addView(btnUpdate,buttonDetails);
		buttonLayout.addView(btnDelete,buttonDetails);
		
		
		buttonLayout2.addView(button2,buttonDetails);
		buttonLayout2.addView(button3,buttonDetails);
	
		
		
		buttonLayout3.addView(btnDeleteAll,buttonDetails);
		buttonLayout3.addView(btnViewList,buttonDetails);
		buttonLayout3.addView(btnViewPinyinByCharacter,buttonDetails);
		
	
		row1Layout.addView(idLabel);
		
		row1Layout.addView(editTextId);
		row2Layout.addView(listtitleLabel);
		row2Layout.addView(editListtitle,textDetails);
		row3Layout.addView(characterLabel);
		row3Layout.addView(editCharacter);
		row3Layout.addView(buttonTranslate);
		
		row6Layout.addView(englishLabel);
		row6Layout.addView(editEnglish);
		row6Layout.addView(buttonGetPinyin);
		
		
		
		row4Layout.addView(pinyinLabel);
		row4Layout.addView(editPinyin);
		row4Layout.addView(buttonPinyinCovert);
		
		
		queryLayout.addView(buttonQuery);
		queryLayout.addView(buttonSuggestion);
		

		
		layout1.addView(row1Layout);
		layout1.addView(row2Layout);
		layout1.addView(row3Layout);
		layout1.addView(row4Layout);
		layout1.addView(row6Layout);
		layout1.addView(row5Layout);
		layout1.addView(pinyinInstructionLabel,textDetails);
		
		

		layout1.addView(buttonLayout,buttonDetails);
		layout1.addView(buttonLayout2,buttonDetails);
		layout1.addView(buttonLayout3,buttonDetails);
		
		layout1.addView(queryLayout,textDetails);
		layout1.addView(editQuery,textDetails);
	
		
		
		
		setContentView(layout1);
		this.setTitle("Chinese Vocabulary Table Manager...");

		myDb = new DatabaseHelper(this);
		
		
		
		if(somechinese !=null && somepinyin !=null && someenglish !=null && somelisttitle !=null)
		{
			editListtitle.setText(somelisttitle);
			editCharacter.setText(somechinese);
			editPinyin.setText(somepinyin);
			editEnglish.setText(someenglish);
			
	    	
	    	
	    	if((editListtitle.getText().length()>15)  )
	    	{
	    		Toast.makeText(VocabDatabaseActivity.this,"Listtitle is Too Long!!!\n\nMax length of listtitle is 15 characters.",Toast.LENGTH_LONG).show();
	            return;
	    	}
	    	
	    	
	    	
	    	else
	    	{
	    		String listtitle = (editListtitle.getText().toString().replace(" ", ""));
	    		
	    		String      search_string = editCharacter.getText().toString();
                search_string = search_string.replace(" ", "");
                	
                	DownloadImages download = new DownloadImages(search_string);
                	download.execute();
                	String str = editCharacter.getText().toString().replace(" !", "!"); 
	           boolean isInserted = myDb.insertVocabData(listtitle,
	        		   str,editEnglish.getText().toString()
	                    , editPinyin.getText().toString() );
	            if(isInserted == true)
	                Toast.makeText(VocabDatabaseActivity.this,"New Vocab Inserted",Toast.LENGTH_LONG).show();
	            else
	                Toast.makeText(VocabDatabaseActivity.this,"Vocab not Inserted",Toast.LENGTH_LONG).show();
	    	
	    	}
	    	
		}	
		
/*
		btnAddData.setLayoutParams(new LinearLayout.LayoutParams(400,200));
		btnviewAll.setLayoutParams(new LinearLayout.LayoutParams(400,200));
		btnUpdate.setLayoutParams(new LinearLayout.LayoutParams(400,200));
		btnDelete.setLayoutParams(new LinearLayout.LayoutParams(400,200));
		*/
	
	//	button2.setWidth(100);
	
		//button2.setPadding(0,20, 0, 0);
		
		multipleLists();
		pinyinConvert();
		AddData();
		viewAll();
		UpdateData();
        DeleteData();
        fileLoadPage();
        deleteAllVocab();
        viewPinyinByCharacter();
        loadSentencesFromFile();
        TranslateChineseToEnglish();
        viewResultByID();
        goGetPinyin();
        getSuggestion();
        
        editQuery.setVisibility(View.INVISIBLE);
        
       
    //   buttonQuery.setVisibility(View.GONE);
	}	

	public void multipleLists()
	{
		buttonQuery.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    /*
                     ArrayList<String> multipleLists = new ArrayList<String>();
                     multipleLists.add("241.101_one");
                     multipleLists.add("241.101_two");
                     multipleLists.add("241.102_ten");
                     multipleLists.add("241.101_four");
                     multipleLists.add("241.102_sixteen");
                   */
                    	
                    	/*
                   
                    	
                    	String query = editQuery.getText().toString();
                    	Cursor res = null;
                    	
                    	
                    	
                    	if(query.contains("sentencetable"))
		               		 {
		       	        	query = query.replace("sentencetable", "");
		       	        	 Toast.makeText(VocabDatabaseActivity.this, "Can't query to sentencetable from here!", Toast.LENGTH_LONG).show();
		       	        	 return;
		               		 }
		                      else if(query.contains("charactertable"))
		               		 {
		       	        	query = query.replace("charactertable", "");
		       	        	 Toast.makeText(VocabDatabaseActivity.this, "Can't query to charactertable from here!", Toast.LENGTH_LONG).show();
		       	        	  return;
		               		 }
		                      else
		                      {

		                     	 res = myDb.performRawQuery(query);

		                         
		                      }
                    
                   

                      
                      
                      if(res.getCount() == 0) {
                          // show message
                          showMessage("Error","Nothing found");
                          return;
                      }

                      StringBuffer buffer = new StringBuffer();
                      int count = 1;
                      
                      
                    try
                      {
                              
		                         while (res.moveToNext()&& (res != null)) 
		                         {
		                        	 
		                               for(int i=0; i<res.getColumnCount();i++)
		                               {
		                            	   
		                            	   if(i==0)
		                            	   {
		                            		   buffer.append("\n"+count+".) "+ res.getString(i)+", ");
		                            		   
		                            	   }
		                            	   else if(i!=(res.getColumnCount()-1) )
		                             	   {
				                            buffer.append(res.getString(i)+", ");
		                            	   }
		                            	   else if(i==(res.getColumnCount()-1) )
		                            	   {
		                            		   buffer.append(res.getString(i)+"\n\n ");
		                            	   }
		                               }
		                               
		                               count++;
		                         }//end while
		                         if(res == null)
		                         {
		                        	 showMessage("SQLite Query return: ","NULL");
		                         }
		                         else
		                         {
		                        	    // Show all data
			                         showMessage("SQLite Query return: ",buffer.toString());
	                      
		                         }
                      }//end try    
                      catch(java.lang.IllegalStateException e)
                       {
                     	  showMessage("Error:","Array out of bounds!!");
                       }
                       
                       */
                    	
                    	
                    	
                    	 startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    
                   
                }
            });
		
	}	
	
	
	public void getSuggestion()
	{
		buttonSuggestion.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    
                    
                    { 
                    	Intent intent = new Intent(getApplicationContext(), SentenceSuggestActivity.class);
		            	
						 String suggestion = editCharacter.getText().toString();
						 String str =  suggestion.replace(" !", "!");   
				          intent.putExtra("asuggestion", str);				       				       
				          intent.putExtra("alisttitle", editListtitle.getText().toString());
				          
				          
				          
		            
		                startActivity(intent);
                  }
                }
        );
		
		
	}
	
	
	public void goGetPinyin()
	{
		buttonGetPinyin.setOnClickListener
        (
                new View.OnClickListener()
                {
                   
					@Override
                    public void onClick(View v)
                    {
                    	
                    	

		            	  
		           
		            	//translation = "";
		            	//textToTranslate = "";
		            	
					
		            	
		            	/*
		            	ClipboardManager myClipboard = myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		            	ClipData myClip;
		            	//editCharacter.getText().toString() 
		            	int min = 0;
		            	int max = editCharacter.getText().length();
		            	if (editCharacter.isFocused()) {
		            	    final int selStart = editCharacter.getSelectionStart();
		            	    final int selEnd = editCharacter.getSelectionEnd();
		            	    min = Math.max(0, Math.min(selStart, selEnd));
		            	    max = Math.max(0, Math.max(selStart, selEnd));
		            	}
		            	// here is your selected text
		            	final CharSequence selectedText = editCharacter.getText().subSequence(min, max);
		            	String text = selectedText.toString();


		            	// copy to clipboard
		            	myClip = ClipData.newPlainText("text", text);
		            	myClipboard.setPrimaryClip(myClip);
		            	*/
		            
		            	
		            	
		            	
		            //	 ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
		            //	 ClipData clip = ClipData.newPlainText("text", editCharacter.getText().toString());
		            //	 clipboard.setPrimaryClip(clip);
		            	
     					
						if((editListtitle.getText().length()>15) ||(editListtitle.getText().length()==0) ||editEnglish.getText().toString().length()==0  ||editCharacter.getText().toString().length()==0 )
				    	{
				    		Toast.makeText(VocabDatabaseActivity.this,"Listtitle is Too Long, or a textfield is empty!!!\n\nMax length of listtitle is 15 characters.",Toast.LENGTH_LONG).show();
				            return;
				    	}
						
		            	Intent intent = new Intent(getApplicationContext(), LearnAboutPinyinActivity.class);
		            
		            	String str =  editCharacter.getText().toString().replace(" !", "!"); 
		            
		            	intent.putExtra("alisttitle", editListtitle.getText().toString());
		            	intent.putExtra("achinese", str);
						intent.putExtra("aenglish", editEnglish.getText().toString());
						
						 setClipboard(getApplicationContext(), editCharacter.getText().toString());
						
		                startActivity(intent);
                    	
                    	
                    	
                    }
                }
        );
		
		
	}
	
	
	// copy text to clipboard
	private void setClipboard(Context context,String text) {
	    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
	        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	        clipboard.setText(text);
	    } else {
	        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
	        clipboard.setPrimaryClip(clip);
	    }
	}
	
	public void pinyinConvert()
	{
		buttonPinyinCovert.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    	if(editPinyin.getText().toString().length()==0)
                    	{
                    		 showMessage("No Pinyin Entered to Convert!!","");
                    		return;
                    	}
                    /*	
                    	String[] result =PinyinHelper.pinyinListToColor( editPinyin.getText().toString() );
                      
                    	 String str ="";
                    	 
                    	 for(String temp:result)
                    	 {
                    		 str += temp +" ";
                    		 
                    	 }
                    	 
              
                    			 
                    			 
                    	
                    	editPinyin.setText(str);
                    */
                    	
                    	editPinyin.setText(PinyinHelper.makePinyinFromMany(editPinyin.getText().toString()));
                    	
                      
                    //  text = translate.translate("Hello!", Language.ENGLISH, Language.ROMANIAN);
                  
                      
                    //  String text = "";
                   
                    	/*
                    	ArrayList<ChineseCharacter>  list =   PinyinHelper.convertStringOfCharactersToArray(editPinyin.getText().toString());
                    	
                    	
                    	for(int i=0;i<list.size();i++)
                    	{
                    	    editPinyin.append(", "+PinyinHelper.pinyinToColor( list.get(i).getPinyin() )  );
                    	}
                    	
                    	*/
                      /*
                      //	String[] output = PinyinHelper.pinyinListToArray(editPinyin.getText().toString());
                      //	String[] output = PinyinHelper.pinyinListToColor(editPinyin.getText().toString());
                    	for(int i=0;i<output.length;i++)
                    	{
                    	    editPinyin.append(", "+PinyinHelper.pinyinToColor((output[i])));
                    	}
                      */
                    }
                }
        );
		
		
	}
	
	
	
	public void  viewPinyinByCharacter()
	{
		btnViewPinyinByCharacter.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                     	Cursor res = myDb.getVocabDataByListtitleDistinct();
                   // 	Cursor res = myDb.getDataByCharacterDistinct();
                //    	Cursor res = myDb.getDataCharacterPinyinDistinct(editListtitle.getText().toString());
                  //  	Cursor res = myDb.getDataCharacterMulitiplePinyin(editCharacter.getText().toString());
                   // 	Cursor res = myDb.getDataByPinyinDistinct();
                    //	Cursor res = myDb.getDataByListtitleCharacterDistinct();
                    //	Cursor res = myDb.getDataCharacterPinyinDistinct();
                    //	 Cursor res = myDb.getDataByCharacter(editCharacter.getText().toString());
                   // 	Cursor res = myDb.getDataByListtitle(editListtitle.getText().toString());
                    	// Cursor res = myDb.getDataByPinyin(editPinyin.getText().toString());
                         if(res.getCount() == 0) {
                             // show message
                             showMessage("Error","Nothing found");
                             return;
                         }
  
                         StringBuffer buffer = new StringBuffer();
                         int count = 0;
                         
                         
                       try
                         {
                    
		                         while (res.moveToNext()) 
		                         {
		                        	 count++;
		                          
		                           buffer.append(count+".) listname : "+ res.getString(0)+"\n");
		                      //      buffer.append(count+".) character: "+ res.getString(0)+", ");
		                      //       buffer.append("pinyin: "+ res.getString(1)+"\n\n");
		                            
		                         }
		  
		                         // Show all data
		                         showMessage("Chinese Vocab:",buffer.toString());
                         }
                         catch(java.lang.IllegalStateException e)
                       {
                        	  showMessage("Error:","Array out of bounds!!");
                       }
                         
                         
                         
                    }
                }
        );
		
		
	}
	
	public void loadSentencesFromFile()
	{
		button3.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    
                    
                    { 
                    	   
                    	 ArrayList<ChineseVocab> chineseVocabArraylist = LoadVocabFromFile.readVocabFromFile();
                    //	 ArrayList<ChineseVocab> chineseVocabArraylist = new ArrayList<ChineseVocab>();
                    //	 chineseVocabArraylist.add(new ChineseVocab("241.102_14","知道","to be aware of","zhī dào"));
                    	 
                    	 Toast.makeText(VocabDatabaseActivity.this,"chineseCharacterArraylist: "+chineseVocabArraylist.get(0).getListtitle(),Toast.LENGTH_LONG).show();
                    	 boolean isInserted = false;
                    	
                   
                   	  for(int i =0;i< chineseVocabArraylist.size() ;i++)
                   	  {
                   	   String      search_string = chineseVocabArraylist.get(i).getChinesevocab();
	                   	search_string = search_string.replace(" ", "");
                   		  
                   		 String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ColorChinese/images";
                         
                         File imageFolder = new File(path +"/"+	search_string);
                         //if images dont exist thn create
                         if(!imageFolder.canRead())
                         {
                   		 
			                   	DownloadImages download = new DownloadImages(search_string);
			                   	download.execute();
	                   	
                         }
                   		  
                   		 isInserted = myDb.insertVocabData(chineseVocabArraylist.get(i).getListtitle(),chineseVocabArraylist.get(i).getChinesevocab(),chineseVocabArraylist.get(i).getEnglishvocab()
                  				  ,chineseVocabArraylist.get(i).getPinyinvocab() );
                        
                   	  }
                           
                   	if(isInserted == true)
                        Toast.makeText(VocabDatabaseActivity.this,"New Vocab: "+somechinese+".. added to Vocab Table",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(VocabDatabaseActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show(); 
                    }
                }
        );
		
		
	}
	
	
	
	
	
	public void TranslateChineseToEnglish()
	{
		buttonTranslate.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {  
                    	
                    	
                  //  	boolean isChar = editCharacter.getText().toString().matches("[a-zA-z]{1}");
                    	
                    	/*
                   // 	boolean isDigit = character.matches("\\d{1}"); 
                    	
                    	
                    	 boolean x;
                    	String character = in.next();
                    	char c = character.charAt(0);
                    	if(Character.isLetterOrDigit(charAt(c)))
                    	{ 
                    	  x = true;
                    	}
                    	*/
                    	
                    	boolean isChar = false;
                    	
                    	String str = editCharacter.getText().toString();
                    	
                    	boolean atleastOneAlpha = str.matches(".*[a-zA-Z]+.*");
                    	if(atleastOneAlpha)
                    	{
                    		isChar = true;
                    	}
                    	/*
                    	for (int i = 0; i < str.length(); i++)
                    	{
                    		if(Character.isLetter(str.charAt(i)))
                        	{ 
                    			
                        	}
                    		
                    	}
                    	*/
                    	
                    	// 
                    	
                    	/*
                    	 try {
							 	lang = Detect.execute(editCharacter.getText().toString());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                    	
                    	*/
                    	
					     if(!isChar)
					     {
					    	 Toast.makeText(VocabDatabaseActivity.this,"Translating from "+lang.toString()+" to Chinese...",Toast.LENGTH_LONG).show();
					    	
					    	 
					    	 String multiLines = editCharacter.getText().toString();
				    		 final String[] lines;
				    	//	 String delimiter = "\n";

				    		 lines = multiLines.split("\n");
					    	 
					    	 class bgStuff extends AsyncTask<Void, Void, Void>{
	                          

					    		 
					    		 

					    		
					    		 
					    		 
	                              @Override
	                              protected Void doInBackground(Void... params) {
	                                  // TODO Auto-generated method stub
	                                  try {
	                                	  
		                                	  textToTranslate =  editCharacter.getText().toString();	
		                                	  
		                                	//  textToTranslate = translate(textToTranslate,lang);
		                                	  translation = translate(textToTranslate,Language.ENGLISH);
		                                	  
		                                	  
		                                	/*
		                                       for(int i = 0; i<lines.length; i++)
		                                       {
		                                	     // translation = translate(textToTranslate,lang);
		                                    	   translation = translate(lines[i],lang);
		                                       }
	                                	  */
	                                	  
	                                	   /*
	                                	   else
	                                	   {
			                                		boolean isEnglish = false;
			                                       	
			                                       	String str = editEnglish.getText().toString();
			                                       	
			                                       	boolean atleastOneAlpha = str.matches(".*[a-zA-Z]+.*");
			                                       	if(atleastOneAlpha)
			                                       	{
			                                       		isEnglish = true;
			                                       	}
		                                		   textToTranslate =  editEnglish.getText().toString();	   
		                                		  if(isEnglish) 
				                                      translation = translate(textToTranslate,lang);
		                                		  else
		                                			  translation = translate(textToTranslate,Language.ENGLISH);
	                                		   
	                                	   }
	                                	   */
	                                     
	                                  } catch (Exception e) {
	                                      // TODO Auto-generated catch block
	                                  //    e.printStackTrace();
	                                    //  translatedText = e.toString();
	                                	  translation = "No internet!";
	                                  }
	                                   
	                                  return null;
	                              }
	           
	                              @Override
	                              protected void onPostExecute(Void result) {
	                                  // TODO Auto-generated method stub
	                            //	String str =  editCharacter.getText().toString().replace(" !", "!");  
	                        	  editCharacter.setText(translation);
                            	  editEnglish.setText(textToTranslate);
	                         
	                              editPinyin.setText("");
                             	  setClipboard(getApplicationContext(), editCharacter.getText().toString());
                             	  
	                                  super.onPostExecute(result);
	                              }
	                               
	                          }
	                           
	                          new bgStuff().execute();
	                     
					         
					     }//end if 
					     else 
			                {
			                	
					    	 Toast.makeText(VocabDatabaseActivity.this,"Translating from Chinese to "+lang.toString()+"...",Toast.LENGTH_LONG).show();
					    	 
					    	
					    	 class bgStuff extends AsyncTask<Void, Void, Void>{
	                              
	                             
	                              @Override
	                              protected Void doInBackground(Void... params) {
	                                  // TODO Auto-generated method stub
	                                  try {
	                                	 
	                                	  textToTranslate =  editCharacter.getText().toString();
	                               // 	  textToTranslate = translate(textToTranslate,Language.CHINESE_SIMPLIFIED);
	                                      translation = translate(textToTranslate, Language.CHINESE_SIMPLIFIED);
	                                     
	                                  } catch (Exception e) {
	                                      // TODO Auto-generated catch block
	                                  //    e.printStackTrace();
	                                    //  translatedText = e.toString();
	                                	  translation = "No internet!";
	                                  }
	                                   
	                                  return null;
	                              }
	           
	                              @Override
	                              protected void onPostExecute(Void result) {
	                                  // TODO Auto-generated method stub
	                            	  
	                            	
	                            	  editCharacter.setText(translation);
	                            	  editEnglish.setText(textToTranslate);
	                            	  setClipboard(getApplicationContext(), editCharacter.getText().toString());
	                               	
	                                	  editPinyin.setText("");
	                                  super.onPostExecute(result);
	                              }
	                               
	                          }
	                           
	                          new bgStuff().execute();
	                     
			                }
			                    	 
			                    	
			                    	
			                    	
			           }
                    
                    
                }
               
                  	    
           );
		
		
	}
	
	public void viewResultByID()
	{
		btnViewList.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {  
                    	
                    	
                    	
                    	if(editListtitle.getText().toString().length()==0)
                    	{
                    		 showMessage("No Listtitle Entered to Search","");
                    		return;
                    	}
                    	
                    //	 Cursor res = myDb.getCharacterData(Integer.parseInt(editCharacter.getText().toString()));
                    	Cursor res = myDb.getVocabDataByListtitle(editListtitle.getText().toString());
                         if(res.getCount() == 0) {
                             // show message
                             showMessage("Error","Nothing found");
                             return;
                         }
  
                         ArrayList<String> vocabList = new ArrayList<String>();
                         ArrayList<String> pinyinList = new ArrayList<String>();
                         ArrayList<String> englishList = new ArrayList<String>();
                         
                         StringBuffer buffer = new StringBuffer();
                         String listtitle = "";
                         int lineCount = 0;
                         while (res.moveToNext()) {
                          //   buffer.append("ID :"+ res.getString(0)+", ");
                          //   buffer.append("listname:, "+ res.getString(0)+"\n");
                          //   buffer.append("character:, "+ res.getString(1)+"\n");
                         //   buffer.append("pinyin:, "+ res.getString(2)+"\n");
                            listtitle = res.getString(0);
                    //        vocabList.add(res.getString(1));
                      //      pinyinList.add(res.getString(2));
                      //      englishList.add(res.getString(3));
                            
                            if(lineCount == 0)
                               buffer.append("listname:, "+ res.getString(0)+"\n");
                            
                               buffer.append(lineCount+".) "+ res.getString(1));
                        //      buffer.append("pinyin:, "+ res.getString(2)+"\n");
                               buffer.append(", "+ res.getString(3)+"\n");
                           
                               lineCount++;
                         }
                         /*
                         String characterText = "Vocab:, ";
                         
                         for(String result: vocabList )
                        	 characterText += result + ", ";
                         
                         characterText += "\n";
                         
                         String pinyinText = "pinyin:,";
                         
                         for(String result: pinyinList )
                        	 pinyinText += result + ", ";
                         
                         pinyinText += "\n";
                         
                         String englishText = "pinyin:,";
                         
                         for(String result: englishList )
                        	 englishText += result + ", ";
                         
                         englishText += "\n";
                         
                         */
                         
                         // Show all data
                     
                      //   showMessage("Results:","listtitle:, "+listtitle+"\n"+characterText+pinyinText + englishText);
                    
                    	
                         showMessage("Results:",buffer.toString());
                   
                         
                    }
               }
        );
		
		
	}
	
	
	public void deleteAllVocab()
	{
		btnDeleteAll.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {


                    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
        						context);

        				// set title
        				alertDialogBuilder.setTitle("ALERT!!!");

        				// set dialog message
        				alertDialogBuilder
        						.setMessage("Are you sure you want to delete all Vocab?")
        						.setCancelable(false)
        						.setPositiveButton("Yes.",
        								new DialogInterface.OnClickListener() {
        									public void onClick(DialogInterface dialog,
        											int id) {
        										// if this button is clicked, close
        										// current activity
        									//	VocabDatabaseActivity.this.finish();
        										Integer deletedRows = myDb.deleteAllVocabData();
        				                         if(deletedRows > 0)
        				                             Toast.makeText(VocabDatabaseActivity.this,"Data Deleted..  "+deletedRows+" rows were deleted!!",Toast.LENGTH_LONG).show();
        				                         else
        				                             Toast.makeText(VocabDatabaseActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
        				    					
        									}
        								})
        						.setNegativeButton("No",
        								new DialogInterface.OnClickListener() {
        									public void onClick(DialogInterface dialog,
        											int id) {
        										// if this button is clicked, just close
        										// the dialog box and do nothing
        										dialog.cancel();
        									}
        								});

        				// create alert dialog
        				AlertDialog alertDialog = alertDialogBuilder.create();

        				// show it
        				alertDialog.show();

                    	 
    	
                    }
                }
        );
		
		
	}
	
	public void fileLoadPage()
	{
        button2.setOnClickListener
        (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    	
                    	

                    	startActivity(new Intent(getApplicationContext(), FileLoadVocabActivity.class));
    	
                    }
                }
        );
    }
	
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteVocabData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(VocabDatabaseActivity.this,"Data Deleted..at id: "+editTextId.getText().toString(),Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(VocabDatabaseActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateCharacterData(editTextId.getText().toString(),
                        		editListtitle.getText().toString(),
                        		editCharacter.getText().toString().charAt(0),editPinyin.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(VocabDatabaseActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(VocabDatabaseActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	
                    	 String thispinyin = editPinyin.getText().toString();
                    	 
                    	if( editCharacter.getText().toString().length()==0 )
                    	{
                    		 Toast.makeText(VocabDatabaseActivity.this,"Can only enter a single Character, or is empty!",Toast.LENGTH_LONG).show();
                            return;
                    	}
                    	
                    	
                    	if((editListtitle.getText().length()>15) ||editCharacter.getText().toString().length()==0 )
                    	{
                    		Toast.makeText(VocabDatabaseActivity.this,"Listtitle is Too Long, or is empty!!!\n\nMax length of listtitle is 15 characters.",Toast.LENGTH_LONG).show();
                            return;
                    	}
                    	
                    	if(editListtitle.getText().toString().length()==0  )
                    	{
                    		Toast.makeText(VocabDatabaseActivity.this,"Must Enter A Listtitle!!!",Toast.LENGTH_LONG).show();
                            return;
                    	}
                    	if(editPinyin.getText().toString().length()==0  )
                    	{
                    		Toast.makeText(VocabDatabaseActivity.this,"Pinyin is empty!!!\n\nCharacters Will Have No Tone Colors!.",Toast.LENGTH_LONG).show();
                    		
                    		
                    		 
                    		 for(int i=0;i<editCharacter.getText().toString().length(); i++)
                    		 {
                    			 if(i==(editCharacter.getText().toString().length()-1))
                    			 {
                    				 thispinyin += editCharacter.getText().toString().charAt(i);
                    			 }
                    			 else
                    			 {
                    				 thispinyin += editCharacter.getText().toString().charAt(i) + " ";
                    			 }
                    			
                    		 }
                           // return;
                    	}
                    	
                    		
                    	
                    
                    
                    		
                    		if(editEnglish.getText().toString().length()==0  )
                        	{
                        		Toast.makeText(VocabDatabaseActivity.this,"English is empty!!!\n\nPush Translate to get English if unknown.",Toast.LENGTH_LONG).show();
                             //   return;
                        	}
                        
                    		
                    		String listtitle = (editListtitle.getText().toString().replace(" ", ""));
                    		
		             
                    		
                    		boolean isInserted = false;
                    		
                    		 //for testing:
                  //  for(int i =0;i<500; i++)
                //    {
                    		 isInserted = myDb.insertVocabData(listtitle,
		                    		   editCharacter.getText().toString(), editEnglish.getText().toString()
		                                ,thispinyin );
                   // }
	                       
	                    String      search_string = editCharacter.getText().toString();
	                   	search_string = search_string.replace(" ", "");
	                   	
	                   	DownloadImages download = new DownloadImages(search_string);
	                   	download.execute();

	                   	if(isInserted == true)
	                            Toast.makeText(VocabDatabaseActivity.this,"New Vocab Inserted",Toast.LENGTH_LONG).show();
	                        else
	                            Toast.makeText(VocabDatabaseActivity.this,"Vocab not Inserted",Toast.LENGTH_LONG).show();
                    	
                    	      
                    }
                }
        );
    }
    
    
    
 
    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res = myDb.getAllVocabData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
 
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID: "+ res.getString(0)+", ");
                            buffer.append("listname: "+ res.getString(1)+"\n");
                            buffer.append("sentence: "+ res.getString(2)+"\n");
                            buffer.append("english: "+ res.getString(3)+"\n");
                            buffer.append("pinyin: "+ res.getString(4)+"\n\n");
                           
                        }
 
                        // Show all data
                        showMessage("Chinese Vocab List:",buffer.toString());
                    }
                }
        );
    }
    
    public String translate(String text, Language lang) throws Exception{
        
        
        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
           Translate.setClientId("kpm928"); //Change this
           Translate.setClientSecret("eYPQfzU/zlcCs5keH4OIVJmzerKixHmFB6/pweX7Gls="); //change
         
            
           String translatedText = "";
            
           translatedText = Translate.execute(text,lang);
            
           return translatedText;
       }
     
    
 
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



    
    
    
 
}
    