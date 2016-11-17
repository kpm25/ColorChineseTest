package com.example.mycolorchinese;






import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.animation.ObjectAnimator;

//import com.jaunt.*;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import helper.PinyinHelper;

public class SentenceSuggestActivity extends Activity  {
WebView webview;

	private String suggestion;
	private String listtitle;
	
	
	private String url = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sentence_suggest);
		
		 suggestion =  getIntent().getStringExtra("asuggestion");
		 listtitle =  getIntent().getStringExtra("alisttitle");
		 
		Toast.makeText(SentenceSuggestActivity.this,"suggestion..."+suggestion,Toast.LENGTH_LONG).show();
		
		webview = (WebView)findViewById(R.id.webView2);
		
		webview.getSettings().setJavaScriptEnabled(true);
	//	webview.loadUrl("https://chinese.yabla.com/chinese-pinyin-chart.php");
	//	http://www.words-chinese.com/pinyin-converter/
	// https://www.chineseconverter.com/en/convert/chinese-to-pinyin	
	//	http://www.pin1yin1.com/#床前明月光%0A疑是地上霜%0A举头望明月%0A低头思故乡	
	//	webview.loadUrl("https://www.chineseconverter.com/en/convert/chinese-to-pinyin	");
		webview.loadUrl("http://www.yellowbridge.com/chinese/sentsearch.php?word="+suggestion);

		
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
		
		
	
	 
	 
	 
	 
	 
	 
		webview.scrollBy(1700,1540);
		/*
		ObjectAnimator anim = ObjectAnimator.ofInt(webview, "scrollY",
				webview.getScrollY(), 0);
			anim.setDuration(400);
			anim.start();
	 */
	 
	// webview.loadUrl(urls.get(0));
	
	 
	 this.setTitle(R.string.app_name);
	 
	 
	
	 
	}

	
	
	
	
public void 	goToSentenceManager(View view)
 {
	 
	 
	 	
     	
         //	search_string = editText.getText().toString();

			Intent intent = new Intent(getApplicationContext(), SentenceDatabaseActivity.class);
	    	
			
			
			
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
			//clipboard.setText("Text to copy");
			String text = clipboard.getText().toString();
			
		//	Toast.makeText(SentenceSuggestActivity.this,"touched: "+text+"...",Toast.LENGTH_LONG).show();
			
			
	       
	    
	 String[]       texts = text.split("\n");
         	
        	
	 texts[0] = texts[0].replace(",", "");
	 texts[0] = texts[0].replace(" ", "");
	 
	 texts[1] = texts[1].replace(",", ";");
	   
	//    Toast.makeText(SentenceSuggestActivity.this,"suggestion: "+texts[0]+"\ntranslation:"+texts[1]+"...",Toast.LENGTH_LONG).show();
	    intent.putExtra("asuggestion",texts[0]);
        intent.putExtra("atranslation", texts[1]);
        intent.putExtra("alisttitle", listtitle);

	      startActivity(intent);
	 
	 
	 
	 
	 
	
	 
 	 
  
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


