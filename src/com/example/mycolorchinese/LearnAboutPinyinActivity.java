package com.example.mycolorchinese;





import java.util.ArrayList;

import android.animation.ObjectAnimator;

//import com.jaunt.*;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import helper.PinyinHelper;

public class LearnAboutPinyinActivity extends Activity  {
WebView webview;


private String listtitle;
private String chinese;	
private String pinyin;	
private String english;	
private String listtitlesentence;
private String chinesesentence;	

private String englishsentence;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_about_pinyin);
		
		 chinese =  getIntent().getStringExtra("achinese");
		 listtitle =  getIntent().getStringExtra("alisttitle");
		
		 english =  getIntent().getStringExtra("aenglish");
		 
		 chinesesentence =  getIntent().getStringExtra("sentencechinese");
		 listtitlesentence =  getIntent().getStringExtra("sentencelisttitle");
		
		 englishsentence =  getIntent().getStringExtra("sentenceenglish");
		
		webview = (WebView)findViewById(R.id.webView1);
		
		webview.getSettings().setJavaScriptEnabled(true);
	//	webview.loadUrl("https://chinese.yabla.com/chinese-pinyin-chart.php");
	//	http://www.words-chinese.com/pinyin-converter/
	// https://www.chineseconverter.com/en/convert/chinese-to-pinyin	
	//	http://www.pin1yin1.com/#床前明月光%0A疑是地上霜%0A举头望明月%0A低头思故乡	
		webview.loadUrl("https://www.chineseconverter.com/en/convert/chinese-to-pinyin	");
		
		
	//	  ArrayList<String> urls = new ArrayList<String>();
	// try{	
	//	 UserAgent userAgent = new UserAgent(); 
	 //     userAgent.visit("https://images.google.com/");
	 //     Document doc = userAgent.doc;
	 //     userAgent.doc.apply(  "snake"      );       //fill-out the form by applying a sequence of inputs
	 //     
	//      doc.submit(); //press the submit button labelled 'create trial account'
	    
	 //     Elements divs = userAgent.doc.findEvery("<img>");  //find table element
	//               
	//      for(Element adiv : divs)
	 //   	  {
	//    	             System.out.println(adiv.getAt("src")); 
	//    	  }
	      
	    
	      
	    
	//      for(Element adiv : divs)
	  //  	  {
	    	          
	//    	             urls.add(adiv.getAt("src"));
	    	    
	 //   	  }
	      
	  //    for(String url : urls)
	  //    {
	//    	  System.out.println(url);
	 //     }
	      
	      
	      

			
			//webview.loadUrl("https://www.purpleculture.net/vocabulary-list-generator/");
			
		//	Toast.makeText(LearnAboutPinyinActivity.this,"inside...",Toast.LENGTH_LONG).show();
				
			
	      
	      
	 //  }
	 //   catch(JauntException e){ 
	//      System.out.println(e);
	 //   }
	 //
	 
	 
	 
	 
	 
	 
	 
	 
	 
		webview.scrollBy(0, 2000);
		/*
		ObjectAnimator anim = ObjectAnimator.ofInt(webview, "scrollY",
				webview.getScrollY(), 0);
			anim.setDuration(400);
			anim.start();
	 */
	 
	// webview.loadUrl(urls.get(0));
	
	 
	 this.setTitle(R.string.app_name);
	 
	 
	 
	 
	}


	
	public void addPinyinAndAddNewVocab(View view)
	{

        //	search_string = editText.getText().toString();

		
	    	
			 
			
			
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
			//clipboard.setText("Text to copy");
			pinyin = clipboard.getText().toString();
			
			boolean isChar = false;
			String str = pinyin;
        	
        	boolean atleastOneAlpha = str.matches(".*[a-zA-Z]+.*");
        	if(atleastOneAlpha)
        	{
        		isChar = true;
        	}
        	
        	
        	if (!isChar)
        	{	
		     //	Toast.makeText(LearnAboutPinyinActivity.this,"pinyin isnt correct: "+pinyin+"...",Toast.LENGTH_LONG).show();
		     	return;
        	}
			
	       
		//	Toast.makeText(LearnAboutPinyinActivity.this,"chinesesentence: "+chinesesentence+"\npinyin:"+pinyin+"...",Toast.LENGTH_LONG).show();
			
        
    
	if(chinese!=null && english != null && listtitle != null)
	{
		
           Intent intent = new Intent(getApplicationContext(), VocabDatabaseActivity.class);
		//    Toast.makeText(LearnAboutPinyinActivity.this,"chinese: "+chinese+"\npinyin:"+pinyin+"...",Toast.LENGTH_LONG).show();
		    intent.putExtra("aChinese",chinese);
	       intent.putExtra("aPinyin", pinyin);
	       intent.putExtra("aEnglish", english);
	       intent.putExtra("aListtitle", listtitle);
	
		      startActivity(intent);
	}
	if(chinesesentence!=null && englishsentence != null && listtitlesentence != null)
	{
		Intent intent = new Intent(getApplicationContext(), SentenceDatabaseActivity.class);
	//   Toast.makeText(LearnAboutPinyinActivity.this,"chinese: "+chinese+"\npinyin:"+pinyin+"...",Toast.LENGTH_LONG).show();
		    intent.putExtra("aChinese",chinesesentence);
	       intent.putExtra("aPinyin", pinyin);
	        intent.putExtra("aEnglish", englishsentence);
	       intent.putExtra("aListtitle", listtitlesentence);
	
		      startActivity(intent);
	}
		
	}
	
	private void ifWebPageChange(View view)
	{
		
		
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
	



}


