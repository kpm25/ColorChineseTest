package helper;
//https://www.tutorialspoint.com/sqlite/sqlite_like_clause.htm
//https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html

import java.util.ArrayList;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ColorChinese.db";
    public static final String TABLE_NAME1 = "charactertable";
    public static final String TABLE_NAME2 = "vocabtable";
    public static final String TABLE_NAME3 = "sentencetable";
    public static final String COL_1_CHARACTER = "ID";
    public static final String COL_2_CHARACTER = "LISTTITLE";
    public static final String COL_3_CHARACTER = "CHARACTER";
    public static final String COL_4_CHARACTER = "PINYIN";
    public static final String COL_5_CHARACTER = "NOTES";
    
    public static final String COL_1_VOCAB = "ID";
    public static final String COL_2_VOCAB = "LISTTITLE";
    public static final String COL_3_VOCAB = "CHINESE";
    public static final String COL_4_VOCAB = "ENGLISH";
    public static final String COL_5_VOCAB = "PINYIN";
    
    public static final String COL_1_SENTENCE = "ID";
    public static final String COL_2_SENTENCE = "LISTTITLE";
    public static final String COL_3_SENTENCE = "CHINESE";
    public static final String COL_4_SENTENCE = "ENGLISH";
    public static final String COL_5_SENTENCE = "PINYIN";  
    Context ctx;
    
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT , LISTTITLE TEXT, CHARACTER TEXT , PINYIN TEXT , NOTES TEXT)");
       
     
        db.execSQL("create table " + TABLE_NAME2 +"  (ID INTEGER PRIMARY KEY AUTOINCREMENT, LISTTITLE TEXT , CHINESE VARCHAR(255),ENGLISH VARCHAR(255),PINYIN VARCHAR(255))");
        db.execSQL("create table " + TABLE_NAME3 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT , LISTTITLE TEXT , CHINESE VARCHAR(255),ENGLISH VARCHAR(255),PINYIN VARCHAR(255))");
        

  
  	
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        onCreate(db);
    }

    public boolean insertCharacterData(String listtitle,char character,String pinyin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_CHARACTER,listtitle);
        contentValues.put(COL_3_CHARACTER,String.valueOf(character));
        contentValues.put(COL_4_CHARACTER,pinyin);
        long result = db.insert(TABLE_NAME1,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertVocabData(String listtitle,String chinese,String english,String pinyin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_VOCAB,listtitle);
        contentValues.put(COL_3_VOCAB,chinese);
        contentValues.put(COL_4_VOCAB,english);
        contentValues.put(COL_5_VOCAB,pinyin);
        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    
    public boolean insertSentenceData(String listtitle,String chinese,String english, String pinyin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_SENTENCE,listtitle);
        contentValues.put(COL_3_SENTENCE,chinese);
        contentValues.put(COL_4_SENTENCE,english);
        contentValues.put(COL_5_SENTENCE,pinyin);
        long result = db.insert(TABLE_NAME3,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getCharacterData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME1+" where "+COL_1_CHARACTER+" = "+id, null );
        return res;
     }
    
    
    public Cursor performRawQuery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor res = null;
        
              
           
              
               
      
         
                	res =  db.rawQuery( query, null );
                	
                
			     
       
        return res;
     }
    public Cursor getDataByCharacter(String character){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME1+" where "+COL_3_CHARACTER+" GLOB '*"+character+"*'", null );
        return res;
     }
    
    public Cursor getCharacterDataByListtitle(String listtitle){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select distinct listtitle, character, pinyin from "+TABLE_NAME1+" where "+COL_2_CHARACTER+" GLOB '*"+listtitle+"*'", null );
        return res;
     }
    

    public Cursor getVocabDataByListtitle(String listtitle){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select distinct listtitle, chinese, pinyin, english from "+TABLE_NAME2+" where "+COL_2_VOCAB+" GLOB '*"+listtitle+"*'", null );
        return res;
     }
    
    public Cursor getSentenceDataByListtitle(String listtitle){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select distinct listtitle, chinese, pinyin, english from "+TABLE_NAME3+" where "+COL_2_SENTENCE+" GLOB '*"+listtitle+"*'", null );
        return res;
     }
   
    public Cursor getCharacterDataForMultipleListtitle(ArrayList<String> listtitles){
        SQLiteDatabase db = this.getReadableDatabase();
        String listtitlesString = "";
        String query = "";
        for(int i=0; i<listtitles.size();i++)
        {
        	if(i == 0)
        	{
        	    listtitlesString = listtitlesString + " GLOB '*"+listtitles.get(i)+"*' ";
        	}
        	else
        	{ 
        		 listtitlesString = listtitlesString + " OR "+COL_2_CHARACTER+" GLOB  '*"+listtitles.get(i)+"*' ";
        		
        	}
        	
        }
        
        query = "select DISTINCT "+COL_2_CHARACTER+", "+COL_3_CHARACTER+", "+COL_4_CHARACTER+"  from "+TABLE_NAME1+" where "+COL_2_CHARACTER+listtitlesString;
        Cursor res =  db.rawQuery( query, null );
        return res;
     }
    
 
    public Cursor getCharacterDataForMultipleListtitle(){
        SQLiteDatabase db = this.getReadableDatabase();
        String listtitlesString = "";
        String query = "";
       
        query = "select "+COL_2_CHARACTER+", "+COL_3_CHARACTER+", "+COL_4_CHARACTER+"  from "+TABLE_NAME1+" where "+COL_2_CHARACTER+" GLOB '*241.101_one*' OR "+COL_2_CHARACTER+" GLOB '*241.101_two*'";
        
        Cursor res =  db.rawQuery( query, null );
        return res;
     }
    
    public Cursor getCharacterDataByListtitleDistinct(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_2_CHARACTER+" from "+TABLE_NAME1, null );
        return res;
    }
    
    public Cursor getVocabDataByListtitleDistinct(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_2_VOCAB+" from "+TABLE_NAME2, null );
        return res;
    }
    
   
    public Cursor getSentenceDataByListtitleDistinct(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_2_SENTENCE+" from "+TABLE_NAME3, null );
        return res;
    }
   
    public Cursor getDataByListtitleCharacterDistinct(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_2_CHARACTER+", "+COL_3_CHARACTER+" from "+TABLE_NAME1, null );
        return res;
    }
    
    public Cursor getDataCharacterPinyinDistinctWithListTitle(String listtitle){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_3_CHARACTER+", "+COL_4_CHARACTER+" from "+TABLE_NAME1+" where "+COL_2_CHARACTER+" GLOB '*"+listtitle+"*'", null );
        return res;
    }
    public Cursor getDataCharacterPinyinDistinct(String listtitle){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_3_CHARACTER+", "+COL_4_CHARACTER+" from "+TABLE_NAME1, null );
        return res;
    }
    public Cursor getDataCharacterMulitiplePinyin(String character){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+COL_3_CHARACTER+", "+COL_4_CHARACTER+" from "+TABLE_NAME1+" where "+COL_3_CHARACTER+" GLOB '*"+character+"*'", null );
        return res;
    }
    
    

    public Cursor getDataByCharacterDistinct(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_3_CHARACTER+" from "+TABLE_NAME1, null );
        return res;
    }
    public Cursor getDataByPinyinDistinct(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DISTINCT "+COL_4_CHARACTER+" from "+TABLE_NAME1, null );
        return res;
    }
    
   
    public Cursor getDataByPinyin(String pinyin){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME1+" where "+COL_4_CHARACTER+" GLOB '*"+pinyin+"*'", null );
        return res;
     }
    public Cursor getAllCharacterData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1,null);
        return res;
    }
    
    
    public Cursor getAllVocabData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2,null);
        return res;
    }

    public Cursor getAllSentenceData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME3,null);
        return res;
    }
    public boolean updateCharacterData(String id,String listtitle,char character,String pinyin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_CHARACTER,id);
        contentValues.put(COL_2_CHARACTER,listtitle);
        contentValues.put(COL_3_CHARACTER,String.valueOf(character));
        contentValues.put(COL_4_CHARACTER,pinyin);
        db.update(TABLE_NAME1, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    public boolean updateVocabData(String id,String listtitle,String chinese,String english,String pinyin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_VOCAB,id);
        contentValues.put(COL_2_VOCAB,listtitle);
        contentValues.put(COL_3_VOCAB,chinese);
        contentValues.put(COL_4_VOCAB,english);
        contentValues.put(COL_5_VOCAB,pinyin);
        db.update(TABLE_NAME2, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    
    public boolean updateSentenceData(String id,String listtitle,String chinese,String english,String pinyin ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_SENTENCE,id);
        contentValues.put(COL_2_SENTENCE,listtitle);
        contentValues.put(COL_3_SENTENCE,chinese);
        contentValues.put(COL_4_SENTENCE, english);
        contentValues.put(COL_5_SENTENCE,pinyin);
        db.update(TABLE_NAME3, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteCharacterData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1, "ID = ?",new String[] {id});
    }
    public Integer deleteVocabData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "ID = ?",new String[] {id});
    }
    public Integer deleteSentenceData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME3, "ID = ?",new String[] {id});
    }
    
    
    public Integer deleteAllCharacterData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME1, null, null);
    }
    public Integer deleteAllVocabData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME2, null, null);
    }
    public Integer deleteAllSentenceData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME3, null, null);
    }
    
   
}