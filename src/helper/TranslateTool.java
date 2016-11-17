package helper;


import java.util.Locale;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;





public class TranslateTool  {
	 
	 static String thisTranslatedText = "";
	
	  
	  
	static  public String getTranslation(final String textToTranslate)
	  {
		   
		  
		  class bgStuff extends AsyncTask<Void, Void, Void>{
			 String translatedText = "";
	         
	          @Override
	          protected Void doInBackground(Void... params) {
	              // TODO Auto-generated method stub
	        	  
	        	 
	              try {
	                 
	            	  thisTranslatedText = translate(textToTranslate);
	              } catch (Exception e) {
	                  // TODO Auto-generated catch block
	              //    e.printStackTrace();
	                //  translatedText = e.toString();
	            	  thisTranslatedText = "No internet!";
	              }
	               
	              return null;
	          }

	          @Override
	          protected void onPostExecute(Void result) {
	              // TODO Auto-generated method stub
	        	
	              super.onPostExecute(result);
	          }
	           
	      }
	       
	 
		  
		  
		
		  new bgStuff().execute();
		return thisTranslatedText;
	  }
	  
	  
	  
	  
	
	 
	

	
	 public static String translate(String text) throws Exception{
	        
	        
	        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
	           Translate.setClientId("kpm928"); //Change this
	           Translate.setClientSecret("eYPQfzU/zlcCs5keH4OIVJmzerKixHmFB6/pweX7Gls="); //change
	         
	            
	           String translatedText = "";
	            
	           translatedText = Translate.execute(text,Language.CHINESE_SIMPLIFIED);
	            
	           return translatedText;
	       }
	
	 


	


	
	
	
	


}
