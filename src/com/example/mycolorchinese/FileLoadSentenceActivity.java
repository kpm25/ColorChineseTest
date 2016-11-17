package com.example.mycolorchinese;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import helper.ChineseCharacter;
import helper.DatabaseHelper;


public class FileLoadSentenceActivity extends Activity {

	
	DatabaseHelper myDb;
    public EditText editText,saveFileNameEditText;
    public TextView textView;
    public Button save, load, loadFromFileButton, saveDatabaseToFileButton;
    LinearLayout layout;

  public String path; 
  //  public String path = "/storage/extSdCard"; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.setBackgroundColor(Color.YELLOW);
		
		editText = new EditText(this);
		saveFileNameEditText = new EditText(this);
		saveFileNameEditText.setHint("Filename");
		
		
	 	
RelativeLayout.LayoutParams textDetails = new RelativeLayout.LayoutParams(
				
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
				
				
				);
        textView =  new TextView(this);
		
        save = new Button(this);
        save.setText("save");
      
        loadFromFileButton= new Button(this);
        loadFromFileButton.setText("Upload Character List From File");
        
        saveDatabaseToFileButton= new Button(this);
        saveDatabaseToFileButton.setText("Backup All Character Lists To File");
        load = new Button(this);
        load.setText("load");
        
		layout.addView(editText,textDetails);
		layout.addView(saveFileNameEditText);
	
		layout.setBackgroundColor(Color.rgb(255,255,255));
		
		layout.addView(save);
		layout.addView(load);
		layout.addView(loadFromFileButton);
		layout.addView(saveDatabaseToFileButton);
		
		
		
		layout.addView(textView);
		
		editText.setLayoutParams(new LinearLayout.LayoutParams(800,400));
		
	
		this.setTitle("Chinese Sentence File Manager...");
        setContentView(layout);

        myDb = new DatabaseHelper(this);
        //   path = "/storage/extSdCard";
    //    path = Environment.getExternalStorageDirectory().getAbsolutePath()+dirNameEditText.getText().toString();   
       path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ColorChinese";
        
     
        textView.setText(path);
		
        
        saveDatabaseToFileButton.setOnClickListener
        (
        		new OnClickListener(){
        			@Override
                	public void onClick(View v) {
                	  
        				
        				convertDatabaseToFileFormat();
        				
        			}
        		});

        loadFromFileButton.setOnClickListener
        (
        		new OnClickListener()
        		{
        			@Override
                	public void onClick(View v) 
        			{
                     try{
        				String file = path+ "/"+saveFileNameEditText.getText().toString()+".txt";
        			 	ArrayList<ChineseCharacter> chineseCharacters =  readFromFile(file);
        				
                           //trying to load from internal text file
                 //      ArrayList<ChineseCharacter> chineseCharacters = LoadCharactersFromFile.readFromFile();
                   
        			
 
                        StringBuffer buffer = new StringBuffer();
                        for(int i=0;i<chineseCharacters.size();i++) 
                        {
                            buffer.append("listtitle: "+ chineseCharacters.get(i).getListtitle()+"\n");
                            buffer.append("character :"+ chineseCharacters.get(i).getHanzi()+", ");
                            buffer.append("pinyin :"+ chineseCharacters.get(i).getPinyin()+"\n\n");
                           
                            
                            boolean isInserted = myDb.insertCharacterData(chineseCharacters.get(i).getListtitle(),
                            		chineseCharacters.get(i).getHanzi(),
                            		chineseCharacters.get(i).getPinyin());
                          
                            
                        }
 
                       if(buffer.toString().equals(""))
                    	   showMessage("FILE DOESN'T EXIST!!","");
                       else
                        // Show all data
                    	   showMessage("Characters successfully inserted:",buffer.toString());
                     }
                     catch(ArrayIndexOutOfBoundsException e)
                     {
                    	 showMessage("error..:","invalid file format!!\n\n==>Check the format of data to load."
                    	 		+ "First line should start with \"listtitle:,\" followed by listname for the list to update into."
                    	 		+ "Second line should start with \"characters:,\" followed by characters seperated by a comma."
                    	 		+ "Third line should start with \"pinyin:,\" followed by pinyin values seperated by a comma."
                    	 		+ "Number of pinyin should equal number of characters.\n"
                    	 		+ "***If a character has no pinyin to be entered, use a space between commas "
                    	 		+ "for that field. and vice versa..");
                     }
                       
        				
        			}
        });
  
        save.setOnClickListener(
        		new OnClickListener(){
        			@Override
                	public void onClick(View v) {
                	  // TODO Auto-generated method stub
        		//	File file = new File(path+ "/savedFile3.txt");
        				// File dir = new File(path);
        		       //  dir.mkdirs();
        				
        		         File file = new File(path+ "/"+saveFileNameEditText.getText().toString()+".txt");
        				
        		        String [] saveText = String.valueOf(editText.getText()).split(System.getProperty("line.separator"));

        		        editText.setText("");

        		        Toast.makeText(getApplicationContext(), "Saved: " +path+ "/"+saveFileNameEditText.getText().toString()+".txt", Toast.LENGTH_LONG).show();

        		        Save (file, saveText);
        				
        			}
        		});
        		
        load.setOnClickListener(
        		new OnClickListener(){
        			@Override
                	public void onClick(View v) {
                	  // TODO Auto-generated method stub
        				
        			//	File file = new File (path+ "/savedFile3.txt");
        				File file = new File(path+ "/"+saveFileNameEditText.getText().toString()+".txt");
        				
        		        String [] loadText = Load(file);

        		        String finalString = "";

        		        for (int i = 0; i < loadText.length; i++)
        		        {
        		            finalString += loadText[i] + System.getProperty("line.separator");
        		        }

        		        
        		        Toast.makeText(getApplicationContext(), "Loaded from: " +path+ "/"+saveFileNameEditText.getText().toString()+".txt", Toast.LENGTH_LONG).show();
        		        
        		        textView.setText(finalString);
        			}
        		});
        

        	
        	   
        	   
        
        

    }

    

    public static void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }


    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }








   public ArrayList<ChineseCharacter> readFromFile(String filePath)
   {
	   
	   String csvFile = filePath;
       BufferedReader br = null;
       String line = "";
       String cvsSplitBy = ",";

       ArrayList<String> lineList =  new ArrayList<String>() ;
       
       ArrayList<ChineseCharacter> chineseCharacterArraylist = new ArrayList<ChineseCharacter>() ;
       int charactercount =0;
       try {

           br = new BufferedReader(new FileReader(csvFile));
           int count = 1;
           int listLength = 0;
           
         //  ChineseCharacter character;
           String listtitle = "";
           ArrayList<String> pinyinlistArray = new ArrayList<String>();
           
           ArrayList<Character> hanzilistArray = new ArrayList<Character>() ;
           int linecount = 0;
           
           while ((line = br.readLine()) != null) 
           {
        	   linecount++;
		        
        	   
		       //listtitle: characters: pinyin:     
		               // use comma as separator
		              
		               if(count ==1)
		               {
		            	   
		            	   String[] listtitleLine = line.split(cvsSplitBy);
			               if(listtitleLine[0].equals("listtitle:"))
			               {	
			            	   
			            	   listtitle = listtitleLine[1];
			            	   
			                 
			                
			               
			              }
			              
			               if(linecount==1)
			               {
			            	   listtitle = listtitleLine[1];
			            	   
				                 System.out.println("linecount==1, Linetype: " + listtitleLine[0] + " value: " + listtitle);
				                 
			               }
			             
			               
		               }//if(count ==1)
		               
		               if(count ==2)
		               {
		            	   String[] charactersLine = line.split(cvsSplitBy);
			               if(charactersLine[0].equals("characters:"))
			               {	
			            	   listLength = charactersLine.length;
			                
			            	   for(int i =0; i<listLength-1; i++)
		            			   {
			            		    charactersLine[i+1] = charactersLine[i+1].replace(" ", "");
			            		//   charactersLine[i+1] = charactersLine[i+1].replace(",", "");//remove all comma
			            		   
			            		      hanzilistArray.add(charactersLine[i+1].charAt(0));
		            				 
		            				   
		            			   }
			            	   System.out.print("\n");   
			                
			               
			               }
			            
		               }//if(count ==2)
		               
		               if(count ==3)
		               {
		            	   String[] pinyinLine = line.split(cvsSplitBy);
			               if(pinyinLine[0].equals("pinyin:"))
			               {	
			            	   //check pinyin and characters have same no. of elements
			            	  if (listLength == pinyinLine.length)
			            	  {
			            		 
						                 
						                 for(int i =0; i<listLength-1; i++)
				            			   {
						                	 
						                	 pinyinlistArray.add(pinyinLine[i+1]);
				            				
				            				   
				            				   ChineseCharacter character = new ChineseCharacter(listtitle,hanzilistArray.get(i),pinyinlistArray.get(i)); 
				            				 
				            				   chineseCharacterArraylist.add(charactercount, character);;
				            				// chineseCharacters[charactercount] = character;
				            				   
				            				 charactercount++; 
				            			   }
						               
						                 
						                 
			            	  }          
			            	  else 
			            	  {
			            		  System.out.println("listLength != pinyinLine.length!!..unable to add list: "+ listtitle);
			            	  }
			                
			               }
			             
			               hanzilistArray.clear();
        				   pinyinlistArray.clear(); 
		               }//if(count ==3)
		               
		               
		               if(count == 3) { count = 1;}
		               else{   count++;}
		               
		               
           }//while

           
           
          
           
       }
       catch (FileNotFoundException e) 
       {
           e.printStackTrace();
       } 
       catch (IOException e) 
       {
           e.printStackTrace();
       } finally 
       {
           if (br != null) 
           {
               try 
               {
                   br.close();
               } catch (IOException e) 
               {
                   e.printStackTrace();
               }
           }
       }
	   
       
       return chineseCharacterArraylist;
   }



   
    
   public void convertDatabaseToFileFormat()
   {
	   try {

			String content = "This is the content to write into file";

		
			File file = new File(path+ "/"+saveFileNameEditText.getText().toString()+".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			                  
		//	System.out.println("writing to file:");
		//	bw.write("writing to file:");
		//	bw.newLine();
			
			ArrayList<ChineseCharacter> chineseCharacterArraylist = new ArrayList<ChineseCharacter>();
			
			 Cursor res = myDb.getAllCharacterData();
             if(res.getCount() == 0) {
                 // show message
                 showMessage("Error","Nothing found");
                 return;
             }

             StringBuffer buffer = new StringBuffer();
           
             while (res.moveToNext()) {
                 buffer.append("ID :"+ res.getString(0)+", ");
              
                 buffer.append("listtitle: "+ res.getString(1)+"\n");
                 
                 buffer.append("character: "+ res.getString(2)+", ");
                 
                 buffer.append("pinyin: "+ res.getString(3)+"\n\n");
                
                 ChineseCharacter chineseCharacter = new ChineseCharacter(res.getString(1), res.getString(2).charAt(0),res.getString(3));
                 chineseCharacterArraylist.add(chineseCharacter);
				
             }

            
			  
			
			
					 for(int i =0; i<chineseCharacterArraylist.size(); i++)
					   {    
						 
						 
						   bw.write("listtitle:, "+chineseCharacterArraylist.get(i).getListtitle()+"\n"
				    			   + "characters:, "+chineseCharacterArraylist.get(i).getHanzi()+"\n"
				    			   +"pinyin:, "+chineseCharacterArraylist.get(i).getPinyin());
						    			   
				    	  
						   if(i != chineseCharacterArraylist.size()-1)
						       { 
							       bw.newLine();
						       }
					   }
			
					 // Show all data
		             showMessage("All Character Lists Saved To File:",path+ "/"+saveFileNameEditText.getText().toString()+".txt");
		         
			                 
			bw.close();

			System.out.println("Done");
			
			

		} catch (IOException e)
      	{
			e.printStackTrace();
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
