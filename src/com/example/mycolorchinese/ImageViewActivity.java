package com.example.mycolorchinese;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import helper.ChineseVocab;
import helper.DatabaseHelper;
import helper.DownloadImages;
import helper.LoadVocabFromFile;

import org.jsoup.Jsoup;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public class ImageViewActivity extends Activity {
private int prevImageNum= 0;
private int num = 0;
private DatabaseHelper myDb;
Spinner spinner;
ArrayList<String> entireVocabList = new ArrayList<String> ();
ArrayList<ChineseVocab> chineseVocabToReview = new ArrayList<ChineseVocab>();
//    String url = "http://lifehacker.com/";
   String search_string = "";
//   String url = "http://www.bing.com/images/search?q=";
//	String url = "https://www.google.com/search?tbm=isch&q="+search_string;
	String url = "";
    ProgressDialog progressDialog;
    EditText editText;
    
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        myDb = new DatabaseHelper(this);

        context = this.getApplicationContext();
        
        Button titleButton = (Button) findViewById(R.id.titlebutton);
        Button descButton = (Button) findViewById(R.id.descbutton);
        Button logoButton = (Button) findViewById(R.id.logobutton);
        
        
        spinner = (Spinner) findViewById(R.id.spinnerImageViewActivity);
   
        
        setChinesevocab();
        
        spinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, GetAllValues()));
        
  
       
       
       

        descButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Description().execute();
            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Logo().execute();
            }
        });
        
        
       
    }

    
    
    
    
    private String[] GetAllValues(){
	//	String lan[] = new String[entireVocabList.size()];
    	String lan[] = new String[chineseVocabToReview.size()];
    	
    	
		String temp="";
		for(int i = 0; i < chineseVocabToReview.size(); i++){
			lan[i] = chineseVocabToReview.get(i).getChinesevocab();
			temp += lan[i]+"\n";
		}
	//	 showMessage(temp,"");
		return lan;
	}
	
    
    
    private void setChinesevocab()
    {

    	
//    		String query = "select distinct listtitle , character , pinyin from charactertable where listtitle glob '*"+listtitle1+"*'"
//    				+ " or listtitle glob '*"+listtitle2+"*' or listtitle glob '*"+listtitle3+"*'"; 
        	
    		String query = "";
    		
			
    		Cursor res = null;
    		
    		
        	res = myDb.getAllVocabData();



          
        try
          {         
                  
        	if(res.getCount() == 0) {
                // show message
             //   showMessage("Error","Nothing found");
               
            }

            StringBuffer buffer = new StringBuffer();
            int index =0;
            while (res.moveToNext()) {
              
            	
            	
         //       buffer.append("listname :"+ res.getString(0)+"\n");
         //       buffer.append("chinese :"+ replace+"\n");
             
            //   buffer.append("pinyin :"+ res.getString(3)+"\n\n");
               
            
               ChineseVocab chineseVocab = new ChineseVocab(res.getString(1),res.getString(2),res.getString(3), res.getString(4));
              
               String replaceChinese = res.getString(3).replace("N.", "");
 	          replaceChinese = replaceChinese.replace("V.", "");
 	          replaceChinese = replaceChinese.replace("M.W.", "");
 	          replaceChinese = replaceChinese.replace("Prep.", "");
 	          replaceChinese = replaceChinese.replace("Adj.", ""); 
 	         replaceChinese = replaceChinese.replace("Int.", ""); 
 	        replaceChinese = replaceChinese.replace("Pron.", ""); 
 	       replaceChinese = replaceChinese.replace("Conj.", ""); 
 	      replaceChinese = replaceChinese.replace("Aux.", ""); 
 	         
 	         
           //    entireVocabList.add(replaceChinese);
 	           entireVocabList.add(res.getString(2));
               chineseVocabToReview.add(chineseVocab);
               
               buffer.append(entireVocabList.get(index)+"\n");
               index++;
            }

            // Show all data
      //    showMessage("Vocab List:",buffer.toString());
          
            
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
    
  
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ImageViewActivity.this);
            progressDialog.setTitle("Description");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
        	
        	/*
          
    //	 for(int i =0;i<entireVocabList.size();i++)   
    //	 { 
        	 
        	
				try {
			    	
		            	
		                //	search_string = editText.getText().toString();
		                	search_string = spinner.getSelectedItem().toString();
		                	search_string = search_string.replace(" ", "");
		                	
		               	 url = "https://www.google.com/search?tbm=isch&q="+search_string;
		              Document document = Jsoup.connect(url).get();
		
		              mySaveImage(document, 0);
		              
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
            
    //	 }//end for  
  */          
        	search_string = spinner.getSelectedItem().toString();
        	search_string = search_string.replace(" ", "");
        	
        	DownloadImages download = new DownloadImages(search_string);
        	download.execute();
        	

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

           
            TextView txtDesc = (TextView) findViewById(R.id.desctxt);
            txtDesc.setText(desc);
            progressDialog.dismiss();
        }
    }

    private class Logo extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ImageViewActivity.this);
            progressDialog.setTitle("Logo");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
        //    progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
        	
        	
        
            	
            //	search_string = editText.getText().toString();
           
        //   	 url = "https://www.google.com/search?tbm=isch&q="+search_string;
         //  	url = 	"https://www.flickr.com/search/?ytcheck=1&text="+search_string;
          //   url = "http://www.bing.com/images/search?q="+search_string;;
         //       Document document = Jsoup.connect(url).get();
              
                
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
               
        //    int	  num = (int) (Math.random()*20);
             
           //    Element img = document.select("img").get(num);
             //  prevImageNum = num;
             
           //    String srcValue = img.attr("src");
        //       String srcValue = img.attr("src");
            	
        //	String photoPath = Environment.getExternalStorageDirectory()+"/abc.jpg";	
            
        	
        	
        	/*
        	 String replaceEnglish = chineseVocabToReview.get(0).getEnglishvocab().replace("N.", "");
        	 replaceEnglish = replaceEnglish.replace("V.", "");
        	 replaceEnglish = replaceEnglish.replace("M.W.", "");
        	 replaceEnglish = replaceEnglish.replace("Prep.", "");
        	 replaceEnglish = replaceEnglish.replace("Adj.", ""); 
        	 replaceEnglish = replaceEnglish.replace("Int.", ""); 
        	 replaceEnglish = replaceEnglish.replace("Pron.", ""); 
        	 replaceEnglish = replaceEnglish.replace("Conj.", ""); 
        	 replaceEnglish = replaceEnglish.replace("Aux.", ""); 
        	
               */
        	/*
            	search_string =spinner.getSelectedItem().toString();
            	search_string = search_string.replace(" ", "");
            	
              String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ColorChinese/images";
             
              File imageFolder = new File(path +"/"+	search_string);
              
         	 int num = (int) (Math.random()*imageFolder.listFiles().length);    
              
              url = path +"/"+	search_string+"/"+imageFolder.listFiles()[num].getName();
             */
               
                //InputStream input = new URL(url).openStream();
             //   InputStream stream = ImageViewActivity.class.getClassLoader().getResourceAsStream(url);
            	
        	search_string =spinner.getSelectedItem().toString();
        	search_string = search_string.replace(" ", "");
        	
              BitmapFactory.Options options = new BitmapFactory.Options();
          	options.inPreferredConfig = Bitmap.Config.ARGB_8888;
          	
         //  	String url = path +"/"+	"125"+"/"+"125_3.jpg";
            //	  bitmap = BitmapFactory.decodeFile( url, options);
          	
    //     	File imageFolder = new File(ImageViewActivity.class.getClassLoader().getResourceAsStream("files/images/"+search_string).toString());
      //  	File imageFolder = new File(ImageViewActivity.class.getClassLoader().getResourceAsStream("files/images/乒乓球").toString());
            
          	
       //   	String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ColorChinese/images";
            
          
            
            int num = (int) (Math.random()*20);    
            //   num = 0; 
             //  int id = R.drawable.ic_launcher; 
            //      bitmap = BitmapFactory.decodeResource( context.getResources(), id);
         //   InputStream   stream = ImageViewActivity.class.getClassLoader().getResourceAsStream("files/images/健身房/健身房_"+num+".jpg");
             
            InputStream   stream = ImageViewActivity.class.getClassLoader().getResourceAsStream("files/images/"+search_string+"/"+search_string+"_"+num+".jpg");
            
             
           
           	
            //File imageFolder = new File(stream.toString());
           	
           	
             
            
          	
          	
         
            	 bitmap = BitmapFactory.decodeStream(stream);
            	 
           	
         	//   int id = R.drawable.ic_launcher; 
            //   bitmap = BitmapFactory.decodeResource( context.getResources(), id);
              //   return null;
      
         		// int num = (int) (Math.random()*(imageFolder.listFiles().length));    
         		//  num = 0; 
         		
              //   InputStream stream = ImageViewActivity.class.getClassLoader().getResourceAsStream("files/images/"+search_string+"/"+imageFolder.listFiles()[num].getName());
         		
                 
             //    InputStream stream = ImageViewActivity.class.getClassLoader().getResourceAsStream("files/images/korea/korea_3.jpg");
              //   bitmap = BitmapFactory.decodeStream(stream);
                
       	      
                 
        	
                 
             //       int id = R.drawable.blank; 
              //     bitmap = BitmapFactory.decodeResource( context.getResources(), id);
                 //	return null;
                 	
               //	  bitmap = BitmapFactory.decodeFile(url, options);
                   
                 
           //      String url = stream.toString() +"/"+	search_string+"/"+imageFolder.listFiles()[num].getName();
                 
                 //     num++;
                      
                        
                         //InputStream input = new URL(url).openStream();
                      //   InputStream stream = ImageViewActivity.class.getClassLoader().getResourceAsStream(url);
                     	
                       
                //   	  bitmap = BitmapFactory.decodeFile(url, options);
                       
                 
                   
                   
                  
            
        
             //   Toast.makeText(ImageViewActivity.this,"url: "+url,Toast.LENGTH_LONG).show();
             
                
            
        
           
          
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ImageView logoImg = (ImageView) findViewById(R.id.logo);
            logoImg.setImageBitmap(bitmap);
            Toast.makeText(ImageViewActivity.this,"url: "+url,Toast.LENGTH_LONG).show();
            
            progressDialog.dismiss();
        }
        
        
        

        
    }
    
    
    
    
   private void mySaveImage(Document document, int index)
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
            
            
            ImageView imageView = (ImageView)findViewById(R.id.logo);
            
       
            
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
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        
     			      
    			      
	      
      int count =0;
      for(String thisUrl : urls)
      {
	    	  System.out.println(thisUrl);
	    	  
		    	  
		    	  String imageUrl = thisUrl;
		    	  
		    	  
		    	  File f = null;
		    	  
		    	 
		    	    boolean bool = false;
		    	  //  String dirName = entireVocabList.get(index);
		    	    String dirName = spinner.getSelectedItem().toString();
		    	    
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
    
    
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
