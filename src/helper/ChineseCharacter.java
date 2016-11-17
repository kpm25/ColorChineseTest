package helper;



public class ChineseCharacter {
	
	private String listtitle;
    private String[] colors = {"WHITE","RED","YELLOW","GREEN","BLUE"};
	private char hanzi;
	private String pinyin;
	private char traditionalCharacter;
	private String english;
	private String color;
	private String notes;
	
	public ChineseCharacter (String listTitle, char character, String pinYin)
	{
		listtitle = listTitle;
		hanzi = character;
		pinyin = pinYin;
		
		color = PinyinHelper.pinyinToColor(pinyin);
		
	}
	

	public String getColor() {
		return color;
	}

	

	public String getListtitle() {
		return listtitle;
	}

	public void setListtitle(String listtitle) {
		this.listtitle = listtitle;
	}

	public char getHanzi() {
		return hanzi;
	}

	public void setHanzi(char simplifiedCharacter) {
		this.hanzi = simplifiedCharacter;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public char getTraditionalCharacter() {
		return traditionalCharacter;
	}

	public void setTraditionalCharacter(char traditionalCharacter) {
		this.traditionalCharacter = traditionalCharacter;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}


	public String[] getColors() {
		return colors;
	}


	public void setColors(String[] colors) {
		this.colors = colors;
	}


	public void setColor(String color) {
		this.color = color;
	}

}