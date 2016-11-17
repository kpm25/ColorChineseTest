package helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadSentencesFromFile {
	
	
	public static ArrayList<ChineseSentence> readSentenceFromFile() 
	{
		
    	// URL url = LoadCharactersFromFile.class.getClassLoader().getResource("Files/characters_set1.txt");
	//  URL url = LoadCharactersFromFile.class.getClassLoader().getResource("Files/characters_set1.txt");
	  
	  InputStream stream = LoadSentencesFromFile.class.getClassLoader().getResourceAsStream("files/allSentences3.txt");
			  
		
        System.out.println("==========>   "+stream != null);
     //   stream = LoadCharactersFromFile.class.getClassLoader().getResourceAsStream("SomeTextFile.txt");
     //   System.out.println(stream != null);
    	// getResourceAsStream("file.txt")
    	    
    //    Resource resource = new ClassPathResource("com/example/Foo.class");
   
      String filePath = stream.toString();
	   
   System.out.println("filePath: "+filePath);
       BufferedReader br = new BufferedReader(new InputStreamReader(stream));
       String line = "";
       String cvsSplitBy = ",";

       
       
       ArrayList<ChineseSentence> chineseSentenceArraylist = new ArrayList<ChineseSentence>() ;
       int charactercount =0;
       try {

          // br = new BufferedReader(new FileReader(filePath));
          
           int listLength = 0;
           
         //  ChineseCharacter character;
           String listtitle = "";
       
           
           String chineseSentence = "";
        	   String englishSentence = "";
        		   String pinyinSentence = "";
        		  
        	   
           int linecount = 0;
           int count = 0;
           while ((line = br.readLine()) != null) 
           {
        	   
		        
        	   
		       //listtitle: characters: pinyin:     
		               // use comma as separator
        	   String[] aline = line.split(cvsSplitBy);
        	   if(aline[0].equals("sentencelisttitle:")||linecount==0)
        	   {
        		   listtitle = aline[1];
        		   linecount =0;
        		   count = 0;
        		   
        	   }
		              
		             
		               
		               if(linecount ==2)
		               {
		            	   String[] chineseLine = line.split(cvsSplitBy);
			               if(chineseLine[0].equals("chinesesentence:"))
			               {	
			            	 
			            	//  chineselistArray.add(chineseLine[1]);
			            	  chineseSentence = 	 chineseLine[1]; 	 
		            			   
		            			
			            	//   System.out.print("\n");   
			                
			               
			               }
			            
		               }//if(count ==1)
		               
		              		
		               if(linecount ==1)
		               {
		            	   String[] englishLine = line.split(cvsSplitBy);
			               if(englishLine[0].equals("englishsentence:"))
			               {	
			            	 
			            	 //  chineselistArray.add(englishLine[1]);
		            				 
			            	   englishSentence = 	 englishLine[1]; 
		            			
			            	//   System.out.print("\n");   
			                
			               
			               }
			            
		               }//if(count ==2)
		               
		               
		               
		               if(linecount ==3)
		               {
		            	   String[] pinyinLine = line.split(cvsSplitBy);
			               if(pinyinLine[0].equals("pinyinsentence:"))
			               {	
			            	 
			            	 //  chineselistArray.add(englishLine[1]);
		            				 
			            	   pinyinSentence = 	 pinyinLine[1]; 
		            			
			            	 //  System.out.print("\n");   

				              
				               
				               chineseSentenceArraylist.add(new ChineseSentence(listtitle, chineseSentence,englishSentence,pinyinSentence ));
				               System.out.println(count+"adding sentence: "+ chineseSentenceArraylist.get(count).getChinesesentence());
				               count++;
			                
			               
			               }
			              
		               }//if(count ==3)
		               
		               
		               
		               if(linecount == 3) { linecount = 1;}
		               else{   linecount++;}
		               
		               
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
	   
       
       return chineseSentenceArraylist;
		
		
	}
	
	
	
	

}
