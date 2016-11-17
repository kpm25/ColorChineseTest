package helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.mycolorchinese.ImageViewActivity;
import com.example.mycolorchinese.R;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadImages extends AsyncTask<Void, Void, Void>  {
	String search_string = "";
	
    String desc;
	
	public DownloadImages(String str){
		search_string = str;
	}
	
	
	
	

	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	         
	        }

	        @Override
	        protected Void doInBackground(Void... params) {
	          
	    //	 for(int i =0;i<entireVocabList.size();i++)   
	    //	 { 
	        	 
	        	
					try {
				    	
			            	
			                //	search_string = editText.getText().toString();
			                
			                	search_string = search_string.replace(" ", "");
			                	
			               	String url = "https://www.google.com/search?tbm=isch&q="+search_string;
			              Document document = Jsoup.connect(url).get();
			
			              mySaveImage(document, 0, search_string);
			              
			            } catch (IOException ex) {
			                ex.printStackTrace();
			            }
	            
	    //	 }//end for  
	           

	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void aVoid) {
	            super.onPostExecute(aVoid);

	           
	         
	        }
	    

	
	
	
	
	
	 private void mySaveImage(Document document, int index, String thisDirName)
	    {

		   Elements images = document.select("img[src]");  
		    ArrayList<String> urls = new ArrayList<String>();
	        for (Element image : images) 
	        {  
	            System.out.println("\nsrc : "+ image.attr("src"));  
	            System.out.println("height : " + image.attr("height"));  
	            System.out.println("width : " + image.attr("width"));  
	            System.out.println("alt : " + image.attr("alt"));  
	            System.out.println("\n"+document.location());
	            urls.add(image.attr("src"));
	            
	            
	          
	       
	            
	        }
	  //     Element img = document.select("img").get(19);
	       
	    	 int num = (int) (Math.random()*20);
	     
	       Element img = document.select("img").get(num);
	     //  prevImageNum = num;
	 
	   //    String srcValue = img.attr("src");
	       String srcValue = img.attr("src");
	       
	       
	     	
	       
	        InputStream input = null;
			try {
				input = new URL(srcValue).openStream();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      
	     			      
	    			      
		      
	      int count =0;
	      for(String thisUrl : urls)
	      {
		    	  System.out.println(thisUrl);
		    	  
			    	  
			    	  String imageUrl = thisUrl;
			    	  
			    	  
			    	  File f = null;
			    	  
			    	 
			    	    boolean bool = false;
			    	  //  String dirName = entireVocabList.get(index);
			    	    String dirName = thisDirName;
			    	    
			    	    dirName = dirName.replace(" ", "");
			    	    String path ="";
			    	    try{      
			    	    	
			    	    	  path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ColorChinese/images";
			    	          
			    	    	
			    	       // returns pathnames for files and directory
			    	       f = new File(path+"/"+dirName);
			    	       
			    	       // create
			    	     bool = f.mkdir();
			    	       
			    	       // print
			    	       System.out.print("Directory created? "+bool);
			    	       // returns pathnames for files and directory
			    	   
			    	      // dirName = chineseVocabToReview.get(count).getChinesevocab();
			    	     //  path = path+"/"+ chineseVocabToReview.get(count).getListtitle();
			    	     //  f = new File(path+"/"+dirName);
			    	       // create
			    	   //  bool = f.mkdir();
			    	       
			    	       // print
			    	   //    System.out.print("Directory created? "+bool);
			    	       
			    	    }catch(Exception e)
			    	    {
			    	       // if any error occurs
			    	       e.printStackTrace();
			    	    }
			    	  //  String chineseVocab = entireVocabList.get(index);
			    	    String  chineseVocab = dirName.replace(" ", "");
			    	  
			    	    // create
			    	  
			    	 
			       	String destinationFile = path+"/"+dirName+"/"+chineseVocab+"_"+count+".jpg";
			
			       	try {
						saveImage(imageUrl, destinationFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       	
			       	count++;
			     //  	if(count==10)
			       //		break;
		      }
		      
		      
		    
		      
	    	    
	    	    
	    	 System.out.println("got to here....");
	    	    
	    	    
	    	   	    
	    	    

	    }
	    
	    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
	    	URL thisUrl = new URL(imageUrl);
	    	InputStream is = thisUrl.openStream();
	    	OutputStream os = new FileOutputStream(destinationFile);

	    	byte[] b = new byte[2048];
	    	int length;

	    	while ((length = is.read(b)) != -1) {
	    		os.write(b, 0, length);
	    	}

	    	is.close();
	    	os.close();
	    }

}
