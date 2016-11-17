package helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;




public class LoadCharactersFromFile {
	
	
	
	static  public ArrayList<ChineseCharacter> readFromFile()
	   {
		   
			
	    	// URL url = LoadCharactersFromFile.class.getClassLoader().getResource("Files/characters_set1.txt");
		//  URL url = LoadCharactersFromFile.class.getClassLoader().getResource("Files/characters_set1.txt");
  	  
		  InputStream stream = LoadCharactersFromFile.class.getClassLoader().getResourceAsStream("files/charactersSet1.txt");
				  
			
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

	      
	       
	       ArrayList<ChineseCharacter> chineseCharacterArraylist = new ArrayList<ChineseCharacter>() ;
	       int charactercount =0;
	       try {

	          // br = new BufferedReader(new FileReader(filePath));
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

	
	

}
