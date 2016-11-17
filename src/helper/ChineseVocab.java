package helper;

import java.util.ArrayList;

public class ChineseVocab {

   // ArrayList<ChineseCharacter> chineseVocab;
	
    private String[] colors = {"WHITE","RED","YELLOW","GREEN","BLUE"};
	private String[] pinyinColors;
	private String[] pinyinArray;
	private String listtitle;
    private String  chinesevocab;
    private String  englishvocab;
    private String  pinyinvocab;
	
    public ChineseVocab (String listTitle, String chinesevocab, String englishvocab,String pinyinvocab)
	{
		this.listtitle = listTitle;
		this.chinesevocab = chinesevocab;
		this.englishvocab = englishvocab;
		this.pinyinvocab = pinyinvocab;
		
	/*	
		for(int i=0;i<chinesevocab.length();i++)
		{
		   chineseVocab.add(new ChineseCharacter(listTitle,chinesevocab.charAt(i),pinyinvocab ));
		}
		*/
		
		
       pinyinArray = PinyinHelper.pinyinListToArray(pinyinvocab);
		
		pinyinColors = new String[pinyinArray.length];
		
		for(int i=0;i<pinyinColors.length;i++)
		{
			pinyinColors[i] = PinyinHelper.pinyinToColor(pinyinArray[i]);
		}
				
	}

    
    public String getPinyin(int num)
    {
 	   
 	   return pinyinArray[num];
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


	public void setPinyinColors(String[] pinyinColors) {
		this.pinyinColors = pinyinColors;
	}


	


	public String[] getPinyinArray() {
		return pinyinArray;
	}



	public void setPinyinArray(String[] pinyinArray) {
		this.pinyinArray = pinyinArray;
	}

    
    
    
	

	

	public String getPinyinvocab() {
		return pinyinvocab;
	}

	public void setPinyinvocab(String pinyinvocab) {
		this.pinyinvocab = pinyinvocab;
	}

	public String getListtitle() {
		return listtitle;
	}

	public void setListtitle(String listtitle) {
		this.listtitle = listtitle;
	}

	public String getChinesevocab() {
		return chinesevocab;
	}
	public String getPinyinColor(int i) {
		return pinyinColors[i];
	}

	public void setChinesevocab(String chinesevocab) {
		this.chinesevocab = chinesevocab;
	}

	public String getEnglishvocab() {
		return englishvocab;
	}

	public void setEnglishvocab(String englishvocab) {
		this.englishvocab = englishvocab;
	}
	
}
