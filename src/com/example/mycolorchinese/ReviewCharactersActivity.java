package com.example.mycolorchinese;





import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import helper.DatabaseHelper;

public class ReviewCharactersActivity extends Activity{
private static final int NUM_ROWS = 8;
private static final int NUM_COLS = 2;
private boolean isAllCheckedToggle = false;

//ArrayList<ArrayList<Integer>> twoDArrayList = new ArrayList<ArrayList<Integer>>();

//ArrayList<TextView> listtitles = new ArrayList<TextView>();
ArrayList<CheckBox>  checkboxes = new ArrayList<CheckBox>();

//private int charCount = 0;
 DatabaseHelper myDb;
 Button btnClassroom;
ArrayList<String> listtitleList = new ArrayList<String>();
ArrayList<String> listtitleListsToReview = new ArrayList<String>();
@Override 
protected void onCreate(Bundle savedInstanceState) 
{  
			super.onCreate(savedInstanceState);
			
			
			setContentView(R.layout.activity_review_characters);
		 
	 btnClassroom = (Button)findViewById(R.id.buttonReviewcharacters);
	 
		myDb = new DatabaseHelper(this);
		
		
		
		
		  Cursor res = myDb.getCharacterDataByListtitleDistinct();
		    
          if(res.getCount() == 0) {
              // show message
              showMessage("There are no currently no Character Lists in database!!","Go to Data Management to either load default lists, create new, or upload lists from file.");
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
                 
                    
                    //listtitleList.add(res.getString(0));
                    
                   String listName =  res.getString(0).replace(" ","");
                    
                  
                   if(! listtitleList.contains(listName))
                       listtitleList.add(listName);
                  }

                  // Show all data
                 // showMessage("Chinese Character:",buffer.toString());
                  
              //    TextView resultTextView = (TextView ) findViewById(R.id.textView1);
                //  resultTextView.setText(buffer.toString());
                //  resultTextView.setText("");
          }
          catch(java.lang.IllegalStateException e)
        {
         	  showMessage("Error:","Array out of bounds!!");
        }
	
        
        
        
        populateButtons();
        setListsToReview();
        this.setTitle("CHARACTER LISTS...");
}

public void peformAllSetToggle(View view)
{
	checkIfAllChecked();
	
}


	
	public void setListsToReview()
	{
		
                 	
	
	
		btnClassroom.setOnClickListener
		( 
				
			new View.OnClickListener()
                  {
			
		            @Override
		            public void onClick(View v)
		         
		            { 
		            	
		            	// showMessage("......","pushed");
		            	if( performCheck() )		            		
		            	{ 
		            		listtitleListsToReview = createSelections();}
		            	else
		            		{ showMessage("You Must Make At Least One Selection!!", ""); 
		            		return;
		            		}
		            	
		            	
		            	
		            	Intent intent = new Intent(getApplicationContext(), ReviewCharacterClassroomActivity.class);
		            	  
		               	
		            	intent.putStringArrayListExtra("listtitleListsToReviewName",listtitleListsToReview);
		            	
		               	startActivity(intent);
		        		
			
		            }
		            
                  }
	       );
	    
		                
}			
	 	 
		

	
	
	
   



private ArrayList<String>  createSelections()
{

			            if(performSelections()!=null)
			            {
			            	listtitleListsToReview   =   performSelections();
			            }
			

			
	
			
	return listtitleListsToReview;		                  
}
		
private boolean performCheck()
{
	boolean anyChecked = false;
	for(int i=0;i<checkboxes.size();i++)
	{    
		
		if(checkboxes.get(i).isChecked())
		{
			anyChecked = true;
		}
	
	
	}
	
	return anyChecked;
}

private ArrayList<String> performSelections() {
	// TODO Auto-generated method stub
	ArrayList<String> listtitlesToBeReviewed = new ArrayList<String>();
	
	for(int i=0;i<checkboxes.size();i++)
	{    
		
		if(checkboxes.get(i).isChecked())
		{
			
			String listTitleToReview = checkboxes.get(i).getText().toString();
			
			listtitlesToBeReviewed.add(listTitleToReview);
			
			
		}
		else
		{
			
           String listTitleToReview = checkboxes.get(i).getText().toString();
			
			listtitlesToBeReviewed.remove(listTitleToReview);
			
			
		}
		
	
		
		
	}
	
	
	
	
	
	
	return listtitlesToBeReviewed;
}


private void checkIfAllChecked()
{
	boolean result = false;
	CheckBox checkboxAll = (CheckBox) findViewById(R.id.checkBoxAllCharacters);
	
//	 if(checkboxAll.isChecked()&& !isAllCheckedToggle)
	 if(checkboxAll.isChecked()&& !isAllCheckedToggle)
	 {
		 for(CheckBox checkbox: checkboxes)
		 {
			 checkbox.setChecked(true);
			 isAllCheckedToggle= true;
			 
	     }
	 }
	 
	 else if(!checkboxAll.isChecked()&& isAllCheckedToggle)
	 {
		 
		 for(CheckBox checkbox: checkboxes)
		 {
			 checkbox.setChecked(false);
			 isAllCheckedToggle= false;
			 
	     }
		
		 
	 }
	 
	 
	 
	 
	
}


private void populateButtons()
{  
	     
     int count = 0;
    int modulus = (listtitleList.size()%2);
      
   if (modulus ==1)
    	listtitleList.add("dummy");

    
	TableLayout table = (TableLayout) findViewById(R.id.tableForCharacterLists); 
	  for (int row = 0; row < ((listtitleList.size())/2); row++) 
	     {
		  TableRow tableRow = new TableRow(this); 
	     tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,
	    		                                             TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
	        table.addView(tableRow); 
	       
	     
	        
	        for (int col = 0; col != 2; col++)
	         {
	        //	 TextView listtitle = new TextView(this);
	              CheckBox checkbox = new CheckBox(this);
	          
	              checkbox.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
	           // listtitle.setBackgroundColor(color.transparent);

	             
	              checkbox.setTextColor(Color.BLACK);
	              checkbox.setTextSize(15);
	              checkbox.setText(""+listtitleList.get(count)); 
			               // Make text not clip on small
	              checkbox.setPadding(0, 0, 0, 0); 
			            
			             
			                 
			               
			             if(!checkbox.getText().equals("dummy"))
			             {
			            	 tableRow.addView(checkbox);
			                 checkboxes.add(checkbox);
			             }
			             else
			             {
			            	 listtitleList.remove("dummy");
			            //	 showMessage("listtitleList:","\n"+listtitleList.toString());
			             }
                             
			                 


	                 
	               
	                 count++;
	         }//end inside for
	      //(listtitleList.size() == listtitles.size()) && 
	     
	        //  
            //is odd?
           
	     }//for
	 
	  
	
			  
			  
	  
	       
	    
}





public void showMessage(String title,String Message){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(Message);
    builder.show();
}



}
