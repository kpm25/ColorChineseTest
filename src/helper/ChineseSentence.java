package helper;

import java.util.ArrayList;

public class ChineseSentence {

	
	private String[] colors = {"WHITE","RED","YELLOW","GREEN","BLUE"};
	private String[] pinyinColors;
	private String[] pinyinArray;
	private String listtitle;
    private String  chinesesentence;
    private String  englishsentence;
    private String  pinyinsentence;
	
	public ChineseSentence (String listTitle, String chinesesentence, String englishsentence, String pinyin)
	{
		this.listtitle = listTitle;
		this.chinesesentence = chinesesentence;
		this.englishsentence = englishsentence;
		this.pinyinsentence =  pinyin;
	/*	
		//intizialise  pinyinColors:
		String replace = pinyinsentence.replace("?", "");
			
		replace = replace.replace(".", "");
        replace = replace.replace("？", "");
        replace = replace.replace(" 。", "");
        replace = replace.replace(".", "");
        replace = replace.replace("？", "");
      
        replace = replace.replace("？", "");
        replace = replace.replace("！", "");
        
		pinyinColors = PinyinHelper.pinyinListToColor( replace);
		*/
		
		pinyinArray = PinyinHelper.pinyinListToArray(pinyin);
		
		pinyinColors = new String[pinyinArray.length];
		
		for(int i=0;i<pinyinColors.length;i++)
		{
			pinyinColors[i] = PinyinHelper.pinyinToColor(pinyinArray[i]);
		}
				
				
		
	}



	public String[] getPinyinArray() {
		return pinyinArray;
	}

   public String getPinyin(int num)
   {
	   
	   return pinyinArray[num];
   }

	public void setPinyinArray(String[] pinyinArray) {
		this.pinyinArray = pinyinArray;
	}



	public String getPinyinsentence() {
		return pinyinsentence;
	}



	public String[] getColors() {
		return colors;
	}



	public void setColors(String[] colors) {
		this.colors = colors;
	}



	public String[] getPinyinColors() {
		return pinyinColors;
	}

	public String getPinyinColor(int i) {
		return pinyinColors[i];
	}

	public void setPinyinColors(String[] pinyinColors) {
		this.pinyinColors = pinyinColors;
	}



	public void setPinyinsentence(String pinyinsentence) {
		this.pinyinsentence = pinyinsentence;
	}



	public String getListtitle() {
		return listtitle;
	}

	public void setListtitle(String listtitle) {
		this.listtitle = listtitle;
	}

	public String getChinesesentence() {
		return chinesesentence;
	}

	public void setChinesesentence(String chinesesentence) {
		this.chinesesentence = chinesesentence;
	}

	public String getEnglishsentence() {
		return englishsentence;
	}

	public void setEnglishsentence(String englishsentence) {
		this.englishsentence = englishsentence;
	}
	
	
}
