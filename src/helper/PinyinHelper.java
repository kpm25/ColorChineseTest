//https://chinese.yabla.com/chinese-english-pinyin-dictionary.php

package helper;

import java.util.ArrayList;



import android.content.Context;
import android.database.Cursor;



public class PinyinHelper  {
	Context ctx;
	
	
	
	private static String[] colors = {"WHITE","RED","YELLOW","GREEN","BLUE"};
	
	
	
	PinyinHelper ()
	{
		
	
	}
	
	
	
	
	
	
	public static String[] getColors() {
		return colors;
	}

	public static String pinyinToColor(String pinyin)
	{
		String result = colors[0];
		
			pinyin = pinyin.replace(" ", "");
			
		for(int i=0;i<pinyin.length(); i++)
		{
	      	
			//[115, 257, 110, 32, 119, 233, 110, 32, 115, 104, 117, 464, 32, 106, 105, 224, 111, 32]
					
			//		[s, ā, n,  , w, é, n,  , s, h, u, ǐ,  , j, i, à, o,  ]
			
           //āáǎàēéěèōóǒòīíìūúǔùǖǘǚǜ
			if(pinyin.charAt(i)== 'ā'|| pinyin.charAt(i)== 'ǖ'||(pinyin.charAt(i)==  'ē' )|| (pinyin.charAt(i)==  'ī' )|| (pinyin.charAt(i)==  'ū' )|| (pinyin.charAt(i)==  'ō' ))
	      	{
				result = colors[1];
	      		
	      	//	System.out.println("inside pinyinToColor: "+Character.getNumericValue(pinyin.charAt(i))+" color: "+result.toString());
	      		
	      	}

			else if(pinyin.charAt(i)== 'é' || pinyin.charAt(i)== 'ǘ' ||(pinyin.charAt(i)==  'í' )|| (pinyin.charAt(i)==  'á' )|| (pinyin.charAt(i)==  'ó' )|| (pinyin.charAt(i)==  'ú' ))
	      	{ 
				result = colors[2];
	      		
	      	//	System.out.println("inside pinyinToColor: "+Character.getNumericValue(pinyin.charAt(i))+" color: "+result.toString());
	      		
	      	}

			else if(pinyin.charAt(i)== 'ǐ' || (pinyin.charAt(i)==  'ǎ' ) || (pinyin.charAt(i)==  'ǔ' )|| (pinyin.charAt(i)==  'ǒ' )|| (pinyin.charAt(i)==  'ǚ' )|| (pinyin.charAt(i)==  'ě' ))
	      	{ 
				result = colors[3];
	      		
	      	//	System.out.println("inside pinyinToColor: "+Character.getNumericValue(pinyin.charAt(i))+" color: "+result.toString());
	      		
	      	}
			
			else if(pinyin.charAt(i)== 'ǜ' || pinyin.charAt(i)== 'à' || (pinyin.charAt(i)== 'è' )|| (pinyin.charAt(i)== 'ù' )|| (pinyin.charAt(i)== 'ì' )|| (pinyin.charAt(i)== 'ò' ))
	      	{   
				result = colors[4];
	      		
	      	//	System.out.println("inside pinyinToColor: "+Character.getNumericValue(pinyin.charAt(i))+" color: "+result.toString());
	      		
	      	}
			
			/*
			if ( (pinyin.charAt(i)!= 'ā') && (pinyin.charAt(i)!= 'ǖ') &&(pinyin.charAt(i)!=  'ē' )&& (pinyin.charAt(i)!=  'ī' )&& (pinyin.charAt(i)!=  'ū' )&& (pinyin.charAt(i)!=  'ō' )
					&& pinyin.charAt(i)!= 'é' && pinyin.charAt(i)!= 'ǘ' &&(pinyin.charAt(i)!=  'í' )&& (pinyin.charAt(i)!=  'á' )&& (pinyin.charAt(i)!= 'ó' )&& (pinyin.charAt(i)!=  'ú' )
							&& pinyin.charAt(i)!= 'ǐ' && (pinyin.charAt(i)!=  'ǎ' ) && (pinyin.charAt(i)!=  'ǔ' )&& (pinyin.charAt(i)!=  'ǒ' )&& (pinyin.charAt(i)!=  'ǚ' )&& (pinyin.charAt(i)!=  'ě' )
									&&pinyin.charAt(i)!= 'ǜ' && pinyin.charAt(i)!= 'à' && (pinyin.charAt(i)!= 'è' )&& (pinyin.charAt(i)!= 'ù' )&& (pinyin.charAt(i)!= 'ì' )&& (pinyin.charAt(i)!= 'ò' ) );
			{
				result = colors[0];
				
				
				
				
			}
			*/
			
	
			
			
			
		}
		
		
		return result;
	}//end pinyinToColor(String pinyin)
	
	//("pinyinWithToneNumber is not numeric");
	
	
	public static String[] pinyinListToColor(String pinyinString)
	{
		
		
		int numOfPinyin = 0;
		 for(int i=0;i<pinyinString.length();i++)
		 {
			 
			 if ( (pinyinString.charAt(i)== 'ā') || (pinyinString.charAt(i)== 'ǖ') ||(pinyinString.charAt(i)==  'ē' )|| (pinyinString.charAt(i)==  'ī' )|| (pinyinString.charAt(i)==  'ū' )|| (pinyinString.charAt(i)==  'ō' )
					 || pinyinString.charAt(i)== 'é' || pinyinString.charAt(i)== 'ǘ' ||(pinyinString.charAt(i)==  'í' )|| (pinyinString.charAt(i)==  'á' )|| (pinyinString.charAt(i)== 'ó' )|| (pinyinString.charAt(i)==  'ú' )
							 || pinyinString.charAt(i)== 'ǐ' || (pinyinString.charAt(i) ==  'ǎ' ) || (pinyinString.charAt(i)==  'ǔ' )|| (pinyinString.charAt(i)==  'ǒ' )|| (pinyinString.charAt(i)==  'ǚ' )|| (pinyinString.charAt(i)==  'ě' )
									 ||pinyinString.charAt(i)== 'ǜ' || pinyinString.charAt(i)== 'à' || (pinyinString.charAt(i)== 'è' )|| (pinyinString.charAt(i)== 'ù' )|| (pinyinString.charAt(i)== 'ì' )|| (pinyinString.charAt(i)== 'ò' ) )
				
			 numOfPinyin++;
			 
			 
			 
		 }
		
		 
		   String[] result = pinyinString.split(" ");
		 
		 
		 for(int i=0;i<result.length;i++)
		 {
			 result[i] = pinyinToColor(result[i]); 
			 
		 }
		 
		 
		 //check number of colors(non-black) pinyin should = numOfPinyin variable
		  int numOfColors = 0;
				  
		    for(int i=0;i<result.length;i++)
		   {
			  
				  if(  result[i].equals(colors[1]) || result[i].equals(colors[2]) || result[i].equals(colors[3]) || result[i].equals(colors[4]) )
				  {
			  
					  numOfColors++;
					  
				  }
		   }  
		  
		    
		    
		    if(numOfColors == numOfPinyin )
		    {
		    	System.out.println("....=======> SUCCESS!!!!!!!!!!!!! PINYIN STRING IS CORRECT");
		    
		    	
		    }
		    
		  
   return result; 
			 

	  
				  
				  
		
		
}
	

	
public static ArrayList<ChineseCharacter> getAlternatePinyinForCharacter(char ch)
	{
	
	ArrayList<ChineseCharacter> result = new ArrayList<ChineseCharacter> ();
	 
		Context ctx = null;
		
		 DatabaseHelper myDb;
		myDb = new DatabaseHelper(ctx);
		String query = "select distinct listtitle, pinyin from charactertable where character glob '*"+ch+"*'";
    	Cursor res = null;
    	
    	
         	 res = myDb.performRawQuery(query);
           
   

      
      
     

      StringBuffer buffer = new StringBuffer();
      int count = 1;
      
      String listtitle = "";
      String pinyin = "";
      
      
      
    try
      {
              
                 while (res.moveToNext()&& (res != null)) 
                 {
                	 
                    	   
                	        listtitle =  res.getString(0);
                    	
                    		   
                    	     pinyin =  res.getString(2);
                    	  
                          result.add(new ChineseCharacter(listtitle, ch, pinyin));
                       
                       count++;
                 }//end while
               
      }//end try    
      catch(java.lang.IllegalStateException e)
       {
     	 
       }
		
    
     return result;
     
}
	

private static ChineseCharacter makeChoiceBetweenAlternatePinyin(ArrayList<ChineseCharacter> chineseCharacters)
{
	//
	
	
	
	return chineseCharacters.get(0);
}
		
		
		

  
	
	public static ArrayList<ChineseCharacter> convertStringOfCharactersToArray(String chineseCharacterString)
	{ 
		ArrayList<ChineseCharacter> chineseCharacters = new ArrayList<ChineseCharacter>();
		
	//	select distinct character , pinyin from charactertable where character glob '*觉*'
		
		
		
		for(int i=0;i<chineseCharacterString.length();i++)
		{
			char ch = chineseCharacterString.charAt(i);
			ArrayList<ChineseCharacter> list = getAlternatePinyinForCharacter(ch);
			
			chineseCharacters.add(makeChoiceBetweenAlternatePinyin( list ));
			
		}
		
		return null;
	}
	
	public static String[] pinyinListToArray(String pinyinString)
	{
		
		 return pinyinString.split(" ");
		 
	}
	
	public static String makePinyinFromMany(String pinyinWithToneNumber)
	{
		String result = "";
		String[] array = pinyinWithToneNumber.split(" ");
		
		
			for(int i =0;i<array.length; i++)
			{
				if(i!=(array.length-1)){
					array[i] = makePinyin(array[i]);
					result += array[i] + " ";
				}
				else{
					array[i] = makePinyin(array[i]);
					result += array[i];
				}
			}
		return result;
	}
	
	public static String makePinyin(String pinyinWithToneNumber)
	{
		//A and e trump all other vowels and always take the tone mark. There are no Mandarin syllables in Hanyu Pinyin that contain both a and e.
		//In the combination ou, o takes the mark.
		//In all other cases, the final vowel takes the mark.
		String result = "";
		
		 //āēōūǖī   áíúéǘó ǔǎěǒǚ   
		//āáǎàēéěèōóǒòīíǐìūúǔùǖǘǚǜ
		
		System.out.println("pinyinWithToneNumber length is: "+pinyinWithToneNumber.length());
		System.out.println("pinyinWithToneNumber.charAt(pinyinWithToneNumber.length()-1: " +pinyinWithToneNumber.charAt(pinyinWithToneNumber.length()-1));
	 char toneNumber = pinyinWithToneNumber.charAt(pinyinWithToneNumber.length()-1);
		if( toneNumber == '1')
      	{
			

			System.out.println("pinyinWithToneNumber is  second tone:"+pinyinWithToneNumber);
			
			String pinyinWithoutToneNumber = pinyinWithToneNumber.replaceAll("[0-9]", "");
			pinyinWithoutToneNumber = pinyinWithoutToneNumber.toLowerCase();
			System.out.println("pinyinWithoutToneNumber:"+pinyinWithoutToneNumber);
		
			char ch1 = pinyinWithoutToneNumber.charAt(0);

             char ch2 = pinyinWithoutToneNumber.charAt(0); //the previous character
			
			for(int i=0; i<pinyinWithoutToneNumber.length();i++)
				//āáǎàēéěèōóǒòīíǐìūúǔùǖǘǚǜ
				{
				    ch1 = pinyinWithoutToneNumber.charAt(i);
				  //āēōūǖī
				
						  if(ch1 =='e')
							{
							  pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("e", "ē");
								 
							  return  pinyinWithoutToneNumber;
							}
							if(ch1 == 'a')
							{
								 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("a", "ā");
								 
								  return  pinyinWithoutToneNumber;
							}
							if(  (ch1 == 'u'&& ch2 == 'o') || (ch2 == 'u'&& ch1 == 'o')    )
							{
								pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ō");
								  return  pinyinWithoutToneNumber;
							}
							
							else 
								{
								  if(i == (pinyinWithoutToneNumber.length()-1) )
								  {	  
								     int length = pinyinWithoutToneNumber.length();
								     
											
											if(pinyinWithoutToneNumber.charAt(length-1) == 'i')
											{
												
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ī");
												 return  pinyinWithoutToneNumber;
											}
											if(pinyinWithoutToneNumber.charAt(length-1) == 'n'&& ch2 == 'g')
											{
												
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "í");
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "í");
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "í");
												 return  pinyinWithoutToneNumber;
											}
								              if(pinyinWithoutToneNumber.charAt(length-1) == 'v')
												{
													
													 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("v", "ǖ");
													 return  pinyinWithoutToneNumber;
												}
												if(pinyinWithoutToneNumber.charAt(length-1) == 'u')
												{
													  
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ū");
												
												 return  pinyinWithoutToneNumber;
												}
												if(pinyinWithoutToneNumber.charAt(length-1) == 'o')
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ō");
												   return  pinyinWithoutToneNumber;
												
												}
												if( pinyinWithoutToneNumber.charAt(length-1) == 'g'&& pinyinWithoutToneNumber.charAt(length-2) == 'n' )
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ō");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ī");
												   return  pinyinWithoutToneNumber;
												
												}
												if( pinyinWithoutToneNumber.charAt(length-1) == 'n' )
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ō");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ī");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ū");
												   return  pinyinWithoutToneNumber;
												
												}
												
								   }//if(i == (pinyinWithoutToneNumber.length()-1) )				
												
								}//else
						
						
						 ch2  = ch1;
				}//for
			
			
			
			
      	}

		if(toneNumber == '2')
      	{ 
			 //āáǎàēéěèōóǒòīíǐìūúǔùǖǘǚǜ
			
			System.out.println("pinyinWithToneNumber is  second tone:"+pinyinWithToneNumber);
			String pinyinWithoutToneNumber = pinyinWithToneNumber.replaceAll("[0-9]", "");
			pinyinWithoutToneNumber = pinyinWithoutToneNumber.toLowerCase();
			System.out.println("pinyinWithoutToneNumber:"+pinyinWithoutToneNumber);
		
			char ch1 = pinyinWithoutToneNumber.charAt(0);

             char ch2 = pinyinWithoutToneNumber.charAt(0); //the previous character
			
			for(int i=0; i<pinyinWithoutToneNumber.length();i++)
				
				{
				    ch1 = pinyinWithoutToneNumber.charAt(i);
				  //áíúéǘó
				
						  if(ch1 =='e')
							{
							  pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("e", "é");
								 
							  return  pinyinWithoutToneNumber;	
							}
							if(ch1 == 'a')
							{
								 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("a", "á");
								 return  pinyinWithoutToneNumber;
							}
							if(  (ch1 == 'u'&& ch2 == 'o') || (ch2 == 'u'&& ch1 == 'o')    )
							{
								pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ó");
								
								return  pinyinWithoutToneNumber;
							}
							
							else 
								{
								 if(i == (pinyinWithoutToneNumber.length()-1) )
								  {	
								   
								         int length = pinyinWithoutToneNumber.length();
								     
											
											if(pinyinWithoutToneNumber.charAt(length-1) == 'i')
											{
												
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "í");
												 return  pinyinWithoutToneNumber;
											}
								              if(pinyinWithoutToneNumber.charAt(length-1) == 'v')
												{
													
													 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("v", "ǘ");
													 return  pinyinWithoutToneNumber;
												}
												if(pinyinWithoutToneNumber.charAt(length-1) == 'u')
												{
													  
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ú");
												
												 return  pinyinWithoutToneNumber;
												}
												if(pinyinWithoutToneNumber.charAt(length-1) == 'o')
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ó");
												   return  pinyinWithoutToneNumber;
												
												}
												
												if( pinyinWithoutToneNumber.charAt(length-1) == 'g'&& pinyinWithoutToneNumber.charAt(length-2) == 'n' )
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ó");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "í");
												   return  pinyinWithoutToneNumber;
												
												}
												if( pinyinWithoutToneNumber.charAt(length-1) == 'n' )
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ó");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "í");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ú");
												   return  pinyinWithoutToneNumber;
												
												}
									
								     }	// if(i == (pinyinWithoutToneNumber.length()-1)
										
												
								}//else
						
						
						 ch2  = ch1;
				}//for
			
			
			
      	}

		if(toneNumber== '3' )
      	{ 
			//ǔǎěǒǚ  
			System.out.println("pinyinWithToneNumber is  third tone:"+pinyinWithToneNumber);
			String pinyinWithoutToneNumber = pinyinWithToneNumber.replaceAll("[0-9]", "");
			pinyinWithoutToneNumber = pinyinWithoutToneNumber.toLowerCase();
			System.out.println("pinyinWithoutToneNumber:"+pinyinWithoutToneNumber);
		
			char ch1 = pinyinWithoutToneNumber.charAt(0);

             char ch2 = pinyinWithoutToneNumber.charAt(0); //the previous character
			
			for(int i=0; i<pinyinWithoutToneNumber.length();i++)
				
				{
				    ch1 = pinyinWithoutToneNumber.charAt(i);
				 
				
						  if(ch1 =='e')
							{
							  pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("e", "ě");
								 
							  return  pinyinWithoutToneNumber;
							}
							if(ch1 == 'a')
							{
								 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("a", "ǎ");
								 return  pinyinWithoutToneNumber;
							}
							if(  (ch1 == 'u'&& ch2 == 'o') || (ch2 == 'u'&& ch1 == 'o')    )
							{
								pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ǒ");
								return  pinyinWithoutToneNumber;
							}
							
							else 
								{ 
								
								 if(i == (pinyinWithoutToneNumber.length()-1) )
								    { 
								          int length = pinyinWithoutToneNumber.length();
								     
											
								              if(pinyinWithoutToneNumber.charAt(length-1) == 'v')
												{
													
													 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("v", "ǚ");
													 return  pinyinWithoutToneNumber;
												}
												if(pinyinWithoutToneNumber.charAt(length-1) == 'u')
												{
													  
												 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ǔ");
												
												 return  pinyinWithoutToneNumber;
												}
												if(pinyinWithoutToneNumber.charAt(length-1) == 'o')
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ǒ");
												   return  pinyinWithoutToneNumber;
												
												}
												
												if(pinyinWithoutToneNumber.charAt(length-1) == 'i')
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ǐ");
												   return  pinyinWithoutToneNumber;
												
												}
												if( pinyinWithoutToneNumber.charAt(length-1) == 'g'&& pinyinWithoutToneNumber.charAt(length-2) == 'n' )
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ǒ");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ǐ");
												   return  pinyinWithoutToneNumber;
												
												}
												if( pinyinWithoutToneNumber.charAt(length-1) == 'n' )
												{
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ǒ");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ǐ");
												   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ǔ");
												   return  pinyinWithoutToneNumber;
												
												}
									
									
												
								     }			//end if(i == (pinyinWithoutToneNumber.length()-1) )
												
								}
						
						
						 ch2  = ch1;
				}//for
			
			 
      	}
		
		 if(toneNumber == '4')
      	{  
			//ǔǎěǒǚ  
				System.out.println("pinyinWithToneNumber is  fourth tone:"+pinyinWithToneNumber);
				String pinyinWithoutToneNumber = pinyinWithToneNumber.replaceAll("[0-9]", "");
				pinyinWithoutToneNumber = pinyinWithoutToneNumber.toLowerCase();
				System.out.println("pinyinWithoutToneNumber:"+pinyinWithoutToneNumber);
			
				char ch1 = pinyinWithoutToneNumber.charAt(0);

	             char ch2 = pinyinWithoutToneNumber.charAt(0); //the previous character
				
				for(int i=0; i<pinyinWithoutToneNumber.length();i++)
					 //àèòìùǜ
					{
					    ch1 = pinyinWithoutToneNumber.charAt(i);
					 
					
							  if(ch1 =='e')
								{
								  pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("e", "è");
								  return  pinyinWithoutToneNumber; 
									
								}
								if(ch1 == 'a')
								{
									 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("a", "à");
									  return  pinyinWithoutToneNumber; 
								}
								if(  (ch1 == 'u'&& ch2 == 'o') || (ch2 == 'u'&& ch1 == 'o')    )
								{
									pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ò");
									return  pinyinWithoutToneNumber;
								}
								
								else 
									{
									          
									    if(i == (pinyinWithoutToneNumber.length()-1) )
									       {
									
									            int length = pinyinWithoutToneNumber.length();
									     
												
									              if(pinyinWithoutToneNumber.charAt(length-1) == 'v')
													{
														
														 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("v", "ǜ");
														 return  pinyinWithoutToneNumber;
													}
									              if(pinyinWithoutToneNumber.charAt(length-1) == 'i')
													{
														
														 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ì");
														 return  pinyinWithoutToneNumber;
													}
													if(pinyinWithoutToneNumber.charAt(length-1) == 'u')
													{
														  
													 pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ù");
													 return  pinyinWithoutToneNumber;
													
													}
												
													if( pinyinWithoutToneNumber.charAt(length-1) == 'o')    
													{
														pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ò");
														return  pinyinWithoutToneNumber;
													}
													if( pinyinWithoutToneNumber.charAt(length-1) == 'g'&& pinyinWithoutToneNumber.charAt(length-2) == 'n' )
													{
													   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ò");
													   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ì");
													   return  pinyinWithoutToneNumber;
													
													}
													if( pinyinWithoutToneNumber.charAt(length-1) == 'n' )
													{
													   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("o", "ò");
													   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("i", "ì");
													   pinyinWithoutToneNumber = pinyinWithoutToneNumber.replace("u", "ù");
													   return  pinyinWithoutToneNumber;
													
													}
													
									       }//end if(i == (pinyinWithoutToneNumber.length()-1) )			
													
													
									}//end else
							
							
							 ch2  = ch1;
					}//end for(int i=0; i<pinyinWithoutToneNumber.length();i++)
				
				
      	}
		 
			
			
			
      	
		
	
		return   pinyinWithToneNumber;
	}
	
	
	public static ArrayList<String> cleanPinyinNumberString(String str)
	{
		    int pinyinArrCount = 0;
		   //  str= "hao3shuo1wen2zai4ling3  ,dai4, zhu4, kuai4, ding4 ,shuai4, chuan1, yan2 ,se4 ,kan4, xi3, nv3 ,shei2, shou3, shou1";
		    System.out.println("str before clean: "+str);
		    
		    //clean string from commas or spaces
		    str = str.replace(",", "");
		    str = str.replace(" ", "");
		    System.out.println("str after clean: "+str);
		    
		    ArrayList<String> pinyinArr = new ArrayList<String>();
		    String bufferString = "";
		    for(int i =0;i<str.length();i++)
		    {
			  	  if(str.charAt(i) == '1' ||str.charAt(i) == '2' || str.charAt(i) == '3' || str.charAt(i) == '4')
			  	  {
			  	  
			  	      pinyinArr.add(bufferString + str.charAt(i));
			  	     
			  	      System.out.println("pinyinArr["+pinyinArrCount+"] = "+pinyinArr.get(pinyinArrCount));
			  	      System.out.println(" bufferString: "+ bufferString);
			  	      System.out.println("pinyinArrCount: "+pinyinArrCount);
			  	      bufferString ="";
			  	      pinyinArrCount++;
			  	  }
			  	  else
			  	  {
			  	     bufferString += str.charAt(i);
			  	  }
			  	  
			  	  
		    }
		    
		    
		    return pinyinArr;
		    
	}
	
}
