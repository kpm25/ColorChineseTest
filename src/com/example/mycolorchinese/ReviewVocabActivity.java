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
import android.widget.TextView;
import android.widget.Toast;
import helper.ChineseCharacter;
import helper.DatabaseHelper;

public class ReviewVocabActivity extends Activity{
private static final int NUM_ROWS = 8;
private static final int NUM_COLS = 2;
private static final int LIST_SIZE_LIMIT = 40;
private boolean isAllCheckedToggle = false;


//ArrayList<CheckBox>  checkboxes = new ArrayList<CheckBox>();
//ArrayList<CheckBox>  checkboxesOnDisplay = new ArrayList<CheckBox>();

ArrayList<CheckBox>  checkboxesOnDisplay = new ArrayList<CheckBox>();

//private int charCount = 0;
private DatabaseHelper myDb;
private Button btnClassroom;
private Button moreButton;
private CheckBox allCheckbox;
private int pageCount = 1;

//private TableLayout table;

private int currentPageNum =0;
 
ArrayList<String> listtitleList = new ArrayList<String>();
ArrayList<String> sublisttitleList = new ArrayList<String>();

ArrayList<Boolean> checkboxFlags = new ArrayList<Boolean>();
ArrayList<String> listtitleListsToReview = new ArrayList<String>();
@Override 
protected void onCreate(Bundle savedInstanceState) 
{  
			super.onCreate(savedInstanceState);
			
			
			setContentView(R.layout.activity_review_vocab);
		 
	 btnClassroom = (Button)findViewById(R.id.buttonReviewVocab);
		//	btnClassroom = new Button(this);
		//	btnClassroom.setText("Select lists to Review");
			
	 moreButton = (Button)findViewById(R.id.buttonVocabReviewMore);
		//	moreButton = new Button(this);
		//	moreButton.setText("==>");
			
	// table = (TableLayout) findViewById(R.id.tableReviewVocabActivity); 
	 
	 allCheckbox = (CheckBox) findViewById(R.id.checkBoxAllVocab); 
//	 allCheckbox = new CheckBox(this);
	// allCheckbox.setText("All    ");
	 
	
	 
	
	
	 
		myDb = new DatabaseHelper(this);
		
		
		
		
		  Cursor res = myDb.getVocabDataByListtitleDistinct();
		    
          if(res.getCount() == 0) {
              // show message
              showMessage("There are no currently no Vocab Lists in database!!","Go to Data Management, under Vocabulary table setion,  to either load default lists, create new, or upload lists from file.");
              return;
          }

          StringBuffer buffer = new StringBuffer();
          int count = 0;
          int listcount = 0;
          
        try
          {
     
                  while (res.moveToNext()) 
                  {
                	  if(listcount ==( LIST_SIZE_LIMIT) )
                      {
                   	   pageCount++;
                   	   listcount = 0;
                   	  
                      }
                	  
                 	
                    buffer.append(count+".) listname : "+ res.getString(0)+"\n");
             
                    
                   String listName =  res.getString(0).replace(" ","");
                    
                 
                	  
                	  
                   if(!listtitleList.contains(listName))
                   {
                       listtitleList.add(listName);
                      boolean bool = false;
                       checkboxFlags.add(count,bool);
                       buffer.append(count+".) checkboxFlags: "+ bool+"\n");
                       
                      
                       
                   }
                   
                   if(count>LIST_SIZE_LIMIT)
                   {
                		 moreButton.setVisibility(View.VISIBLE);

                   }
                   
                   count++;
                   listcount++;
                   
                  }
                  
              //    showMessage("buffer",buffer.toString());

          }
          catch(java.lang.IllegalStateException e)
        {
         	  showMessage("Error:","Array out of bounds!!");
        }
	
        
        
        if(listtitleList.size()>LIST_SIZE_LIMIT) 
    	    moreButton.setVisibility(View.VISIBLE);
        else
        	moreButton.setVisibility(View.INVISIBLE);
        
        
       /* 
        if(listtitleList.size() <LIST_SIZE_LIMIT)
        {
        	 populateButtons(0,listtitleList.size()-1);
        }
        else
        {
        	populateButtons(0,LIST_SIZE_LIMIT);
        }
    	*/
   //   populateButtons(0,listtitleList.size());
        
      
        
        
        
       if(listtitleList.size()<LIST_SIZE_LIMIT)
       {
    	   sublisttitleList = listtitleList;
       }
       else
       {
    	   sublisttitleList = getsublisttitlelist(listtitleList, 0,LIST_SIZE_LIMIT-1 );
       }
        
  //     
       
       populateButtons(sublisttitleList); 
       
       viewMoreLists();
       setListsToReview();
       
    
        Toast.makeText(ReviewVocabActivity.this, "pageCount: " + pageCount+"\nListTitleCount: "+count+"\ncurrentPageNum: " + (currentPageNum+1)
        		+ "\nsublisttitleList.size: "+sublisttitleList.size()
        		+"\ncheckboxFlags.size: "+checkboxFlags.size(),Toast.LENGTH_SHORT).show(); 


        this.setTitle("VOCABULARY LISTS...");
        
        
        
        
}

public ArrayList<String> getsublisttitlelist(ArrayList<String> list, int startindex, int endindex){
	ArrayList<String> result = new ArrayList<String>();
	
	
	for(int i = startindex; i<=endindex; i++)
	{
		result.add(list.get(i));
	}
	
	
	
	return result;
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
		            	
		            	
		            	
		            	Intent intent = new Intent(getApplicationContext(), ReviewVocabClassroomActivity.class);
		            	  
		            	
		            //	String[] listtitleListsToReviewAsArray = new String[listtitleListsToReview.size()];
		            	
		            	
		            	intent.putStringArrayListExtra("listtitleListsToReviewName",listtitleListsToReview);
		            	
		            //	String test = "BOOOOOO!!!!!!!";
		            	
		            //	intent.putExtra("stringtest", test);
		            	startActivity(intent);
		        		
			
		            }
		            
                  }
	       );
	    
		                
}			
	 	 
		
public void viewMoreLists()
{
		
	     	
	
	
	moreButton.setOnClickListener
		( 
				
			new View.OnClickListener()
                  {
			
		            @Override
		            public void onClick(View v)
		         
		            { 
		            	
		            	
		            	currentPageNum++;
		            	if(currentPageNum == (pageCount))
                        	currentPageNum =0;

		            	
		            	 


		            	 //	table.removeAllViews();
		            	 	/*
		            	  for (int i = 0; i< checkboxes.size(); i++)
		            		 {	
		            		 checkboxes.get(i).setVisibility(View.GONE);
		            		//  checkboxes.get(i).setActivated(false);
		            		 }
		            	  */
		            	  int startCount = 0;
		            	  int endCount = 0;

	            	  if( !(currentPageNum == (pageCount-1)) )
		            	 {	
	            		  startCount = (currentPageNum*LIST_SIZE_LIMIT);
	            		  endCount = ((currentPageNum*LIST_SIZE_LIMIT)+(LIST_SIZE_LIMIT-1));
		            	 }
		             else
		            	{
		            	 startCount = (currentPageNum*LIST_SIZE_LIMIT);
	            		  endCount = listtitleList.size()-1;  
		            	
		            	}
		            	  
		            	  
	            	 

		            
		            	 /*
		            	 	 Toast.makeText(ReviewVocabActivity.this,  "currentPageNum: " + (currentPageNum+1)+"\nstartCount: " +startCount
		            	 			+"\nEndCount: "+endCount
		            	 			+"\nlisttitleList.size(): "+listtitleList.size()
		            	 		//	+"\ncheckboxes.get(endCount).getText(): "+checkboxesOnDisplay.get(endCount).getText()
		            	 			+"\ncheckboxes count: "+checkboxesOnDisplay.size()
		            	 			+"\ncheckboxFlags count: "+checkboxFlags.size()
		            	 	 +"\nlisttitleList count: "+listtitleList.size(),Toast.LENGTH_SHORT).show(); 
		             */
		            	    
		            	// 	performSelections();	  
		          //  	 populateButtons(startCount,endCount-1);
		            	//  rePopulateButtons(startCount, endCount);	  
		            		  
		            		       
		            	     
		            //	 populateButtons(0,listtitleList.size()-1);  
		            	 	 
		            	 	 
		            	 	sublisttitleList = getsublisttitlelist(listtitleList,startCount,endCount);
		            	  
		            	//  populateButtons(0,listtitleList.size());
		            	  
		            	 	 populateButtons(sublisttitleList);
		            	 	 
		            	 	 
		            	 	 Toast.makeText(ReviewVocabActivity.this,  "currentPageNum: " + (currentPageNum+1)+"\nstartCount: " +startCount
			            	 			+"\nEndCount: "+endCount
			            	 			+"\nlisttitleList.size(): "+listtitleList.size()
			            	 		//	+"\ncheckboxes.get(endCount).getText(): "+checkboxesOnDisplay.get(endCount).getText()
			            	 			+"\ncheckboxes count: "+checkboxesOnDisplay.size()
			            	 			+"\ncheckboxFlags count: "+checkboxFlags.size()
			            	 	 +"\nlisttitleList count: "+listtitleList.size()
			            	 	 +"\nsublisttitleList count: "+sublisttitleList.size() ,Toast.LENGTH_SHORT).show(); 
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
	for(int i=0;i<checkboxFlags.size();i++)
	{    
		
		if(checkboxFlags.get(i).booleanValue()==true)
		{
			anyChecked = true;
		}
	
	
	}
	
	return anyChecked;
}






private ArrayList<String> performSelections() {
	// TODO Auto-generated method stub
	ArrayList<String> listtitlesToBeReviewed = new ArrayList<String>();
	
	for(int i=0;i<checkboxFlags.size();i++)
	{    
		
		if(checkboxFlags.get(i).booleanValue())
		{
			
			String listTitleToReview = listtitleList.get(i);
	//		checkboxesOnDisplay.get(i).setChecked(true);
//			 checkboxFlags.add(i,true);
			listtitlesToBeReviewed.add(listTitleToReview);
			
			
		}
		else
		{
			
           String listTitleToReview = listtitleList.get(i);
     //      checkboxesOnDisplay.get(i).setChecked(false);
    //       checkboxFlags.add(i,false);
			
			listtitlesToBeReviewed.remove(listTitleToReview);
			
			
		}
		
		
		
		
	}
	
	String temp =  "";
	
	for(int i=0; i< listtitlesToBeReviewed.size(); i++)
	{
		temp += listtitlesToBeReviewed.get(i)+"\n";
		
	}
	
	 Toast.makeText(ReviewVocabActivity.this,  "listtitlesToBeReviewed:\n "+temp,Toast.LENGTH_SHORT).show(); 
	
	
	return listtitlesToBeReviewed;
}




private void checkIfAllChecked()
{
	boolean result = false;
//	CheckBox allCheckbox = (CheckBox) findViewById(R.id.checkBoxAllVocab);
	
	 int count = 0;
//	 if(checkboxAll.isChecked()&& !isAllCheckedToggle)
	 if(allCheckbox.isChecked()&& !isAllCheckedToggle)
	 {
		 
		
		 for(int i =0; i<checkboxFlags.size();i++)
		 {
		    
	        checkboxFlags.set(i,true);
	//	    peformAllSetToggle(allCheckbox);
		    isAllCheckedToggle =true;
		 }
		
	 }
	 
	 else if(!allCheckbox.isChecked()&& isAllCheckedToggle)
	 {
		 
		
		 for(int i =0; i<checkboxFlags.size();i++)
		 {
			 checkboxFlags.set(i,false);
		//	 peformAllSetToggle(allCheckbox);
			 isAllCheckedToggle =false;
		 }
		 
		 
		 
	 }
	 
	 count = 0;
	 if(isAllCheckedToggle==true)
	 {
		 for(CheckBox checkbox: checkboxesOnDisplay)
		 {
			 checkbox.setChecked(true);
		//	 peformAllSetToggle(allCheckbox);
			 
		//	 peformAllSetToggle(allCheckbox);
			 
	     }
		 
	 }
	 else
	 {
		 for(CheckBox checkbox: checkboxesOnDisplay)
		 {
			 checkbox.setChecked(false);
			
		//	 peformAllSetToggle(allCheckbox);
			// peformAllSetToggle(allCheckbox);
			 
	     }
		 
	 }
	 
	
}

/*

private void rePopulateButtons(int start, int end)
{  
	
		
		TableLayout table = (TableLayout) findViewById(R.id.tableReviewVocabActivity); 


	 	table.removeAllViews();
	 	
	 	table.clearAnimation();
	 	
	 	
	 	
	     int count = 0;
	    int modulus = (listtitleList.size()%2);
	      
	   if (modulus ==1 && !listtitleList.get(listtitleList.size()-1).contains("dummy"))
	    	//listtitleList.add("dummy");
	   
	  
	    
		
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

		             
		              
		              if( count>= start &&  count< end)
		              {
		            	  checkboxesOnDisplay.get(count).setVisibility(View.VISIBLE);
		            	  checkboxesOnDisplay.get(count).setActivated(true);
		            	  if(!checkbox.getText().equals("dummy"))
				             {

		    	              checkbox.setTextColor(Color.BLACK);
		    	              checkbox.setTextSize(15);
		    	              checkbox.setText(""+listtitleList.get(count)); 
		    			               // Make text not clip on small
		    	              checkbox.setPadding(0, 0, 0, 0); 
		    			            
		            		  
				            	 tableRow.addView(checkbox);
				              
				             
				             }
				             else
				             {
				            	 listtitleList.remove("dummy");
				            //	 showMessage("listtitleList:","\n"+listtitleList.toString());
				             }
		            	  
		              }
		              else
		              {
		            	  checkbox.setVisibility(View.GONE); 
		            	  
		            	  
		            	 
		            	  
		              }
				             
				                 
				               
				            
	                             
				                 


		                 
		               
		                 count++;
		         }//end inside for
		      
	           
		     }//for
		 
		
				  
		  
		       
	     
		  
	       
     
	  
}
*/
 
private void populateButtons(ArrayList<String> list)
{  
	//checkboxFlags = checkboxflags;
	TableLayout table = (TableLayout) findViewById(R.id.tableReviewVocabActivity); 
	checkboxesOnDisplay.clear();

 	table.removeAllViews();
 	
 	table.clearAnimation();
	     
     int count = 0;
    int modulus = (list.size()%2);
      
 //  if (modulus ==1)
	//   list.add("dummy");

    

	  for (int row = 0; row < ((LIST_SIZE_LIMIT)/2); row++) 
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

	              
	             
	              
	              
	            
	              
                 final int index = count;
                 
                if(index<(list.size()) )
                {
                    // checkboxFlags.set(index, false);
	                
		              if( checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index)==null)
		              {
		            	  checkbox.setChecked(false);
		              }
		              else
		              {
		            	  checkbox.setChecked( checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index).booleanValue());
		              }
		           
                }
                
                /*
                else if(index==(list.size()-1) )
                {
                	 if( checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index)==null)
		              {
		            	  checkbox.setChecked(false);
		              }
		              else
		              {
		            	  checkbox.setChecked( checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index).booleanValue());
		              }
                	
                }
                
	              */
	              
	             
	               if(count<list.size())
	               {
			              checkbox.setTextColor(Color.BLACK);
			              checkbox.setTextSize(15);
			              checkbox.setText(""+list.get(count)); 
					               // Make text not clip on small
			              checkbox.setPadding(0, 0, 0, 0); 
	               }
	               else
	               {
	            	   checkbox.setText(""); 
	            	   checkbox.setVisibility(View.INVISIBLE);
		               // Make text not clip on small
	               }
			             
	             
			                 
			               
			             if(!checkbox.getText().equals("dummy"))
			             {
			            	 tableRow.addView(checkbox);
			            	 checkboxesOnDisplay.add(checkbox);
			             }
			             else
			             {
			            	 list.remove("dummy");
			            //	 showMessage("listtitleList:","\n"+listtitleList.toString());
			             }
                             
			                 

			             checkbox.setOnClickListener(new View.OnClickListener() 
		                  {
			            	   @Override public void onClick(View v) 
			                      {  
			            		   gridButtonClicked(index); 
			            		   } 
			            	   });
	                 
	               
	                 count++;
	         }//end inside for
	      //(listtitleList.size() == listtitles.size()) && 
	     
	        //  
            //is odd?
           
	     }//for
	 
	  
	    
}



private void populateButtons(int start, int end)
{  
	
	
	TableLayout table = (TableLayout) findViewById(R.id.tableReviewVocabActivity); 
	
	checkboxesOnDisplay.clear();

 	table.removeAllViews();
 	
 	table.clearAnimation();
 	
 	
 	
 	
 	
    int count = start;
 //   int modulus = ((listtitleList.size())%2);
    int modulus = (((end)-start)%2);
      
    
	   if (modulus ==1 )
	//   	listtitleList.add("dummy");
	   
	   if (modulus ==0 && listtitleList.get(listtitleList.size()-1).contains("dummy"))
         	listtitleList.remove("dummy");
	  
  
	  
    
	
	  for (int row = start; row <  (end/2); row++) 
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
	              
	             /*
                  if(checkboxFlags.get(count)==true)
                  {
                	  checkbox.setChecked(true);
                  }
	            */
	           
	              
	              
	              
	              
	              
	              
	              
	              
	              
	              
	            	  checkbox.setVisibility(View.VISIBLE);
	            	  
	            	 
	    	              checkbox.setTextColor(Color.BLACK);
	    	              checkbox.setTextSize(15);
	    	              checkbox.setText(""+listtitleList.get(count)); 
	    			               // Make text not clip on small
	    	              checkbox.setPadding(0, 0, 0, 0); 
	    			            
	    	             
	    	             
	    	             
	    	              final int index = count;
	    	              
	    	            
	    	                
	    	                
	    	              if( checkboxFlags.get(index)==null)
	    	              {
	    	            	  checkbox.setChecked(false);
	    	              }
	    	              else
	    	              {
	    	            	  checkbox.setChecked( checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index).booleanValue());
	    	              }
	    	             

	            	  
	           
			                 
	            	     if(!listtitleList.get(index).equals("dummy"))
			             {
	            	    	 	
	            	    	 tableRow.addView(checkbox);
			            	 checkboxesOnDisplay.add(index,checkbox);
			            	// checkboxFlags.add(index, false);
			             }
			             else if( (index ==listtitleList.size()-1) && listtitleList.get(index).equals("dummy"))
			             {
			            	 checkbox.setText("");
			            	 checkboxesOnDisplay.add(index,checkbox);
			            	 listtitleList.remove("dummy");
			            //	 showMessage("listtitleList:","\n"+listtitleList.toString());
			             }
	            	
	            	     
		            
	            	     checkbox.setOnClickListener(new View.OnClickListener() 
	                      {
	    	            	   @Override public void onClick(View v) 
	    	                      {  
	    	            		   gridButtonClicked(index); 
	    	            		   } 
	    	            	   }); 
	    	              
			                 


	                 
	               
	                 count++;
	         }//end inside for
	      
           
	     }//for
	 
	
		  
	  Toast.makeText(ReviewVocabActivity.this,"checkboxFlags.get.size(): "+checkboxFlags.get(checkboxFlags.size()-1)  
	   + "listtitleList.get(listtitleList.size()-1): "+listtitleList.get(listtitleList.size()-1)
	  + "\nmodulus: "+modulus,Toast.LENGTH_SHORT).show();
	       
     
	  
}






private void gridButtonClicked(int index) 
{ 
	//inverts both values:
	checkboxFlags.set((currentPageNum*LIST_SIZE_LIMIT)+index, !checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index).booleanValue());
	
	//   checkboxesOnDisplay.get(index).setChecked(!checkboxesOnDisplay.get(index).isChecked());
	
	   checkboxesOnDisplay.get(index).setChecked(checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index).booleanValue());
	   Toast.makeText(ReviewVocabActivity.this,  index+".) checkboxFlags.get("+((currentPageNum*LIST_SIZE_LIMIT)+index)+").booleanValue():\n "+checkboxFlags.get((currentPageNum*LIST_SIZE_LIMIT)+index).booleanValue()
			   + "\n"+index+".) checkboxesOnDisplay.get("+index+").booleanValue():\n "+checkboxesOnDisplay.get(index).isChecked(),Toast.LENGTH_SHORT).show(); 
		
 
}




public void showMessage(String title,String Message){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(Message);
    builder.show();
}



}
