package com.example.tagit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tagit.Models.EventsModel;
import com.example.tagit.Models.TagEventsModel;
import com.example.tagit.Models.TagModel;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "TagsDatabase";
    private static final int DB_VERSION = 1;
    private static final String tagTable = "Tags";
    private static final String eventsTable = "Events";

    private static final String tagName = "TagName";
    private static final String tagDescription = "TagDescription";
    private static final String tagColor = "TagColor";

    private static final String eventDate = "EventDate";
    private static final String eventId = "EventID";
    private static final String eventTagName = "TagName";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryTag = "CREATE TABLE "+tagTable+" (" +
                tagName + " TEXT PRIMARY KEY, " +
                tagDescription + " TEXT, " +
                tagColor + " TEXT)";
        sqLiteDatabase.execSQL(queryTag);

        String queryEvent = "CREATE TABLE "+eventsTable+" (" +
                eventId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                eventDate + " TEXT, " +
                eventTagName + " TEXT," +
                " FOREIGN KEY("+eventTagName+") REFERENCES Tags(tagName))";
        sqLiteDatabase.execSQL(queryEvent);
    }

    public void addNewTagIntoTable(String tn, String td, String tc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(tagName, tn);
        contentValues.put(tagDescription, td);
        contentValues.put(tagColor, tc);

        db.insert(tagTable,null, contentValues);
    }

    public ArrayList<TagModel> fetchAllTags(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + tagTable, null);
        ArrayList<TagModel> tagModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                tagModels.add(new TagModel(cursor.getString(0),cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        cursor.close();

        return tagModels;
    }

    // Events table queries
    public void addNewEventIntoTable(String ed, String etn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(eventDate, ed);
        contentValues.put(eventTagName, etn);

        db.insert(eventsTable, null, contentValues);
    }

    // Events table queries
    public ArrayList<TagModel> fetchTagFromDate(String ed) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select e.TagName, t.TagColor from " + eventsTable + " as e, " + tagTable + " as t WHERE e."+ eventDate+ " = '"+ed+"' and e."+eventTagName + " = t."+ tagName , null);
        ArrayList<TagModel> tagModels = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                tagModels.add(new TagModel(cursor.getString(0),"", cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        cursor.close();

        return tagModels;
    }

    public ArrayList<TagEventsModel> fetchAllDataFromTagEventsTable(String tn) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+ eventsTable + " as e, "+ tagTable + " as t WHERE e."+eventTagName + " = t."+ tagName + " and e." + eventTagName +" = '" + tn + "'", null);
        ArrayList<TagEventsModel> tagEventsModelArrayList = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                tagEventsModelArrayList.add(new TagEventsModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        cursor.close();

        return tagEventsModelArrayList;
    }

    public void updateValuesInTagTable(String tn, String desc, String tc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(tagDescription, desc);
        contentValues.put(tagColor, tc);
        db.update(tagTable, contentValues, "TagName=?", new String[]{tn});
        db.close();
    }

    public void deleteEvent(String ed, String etn) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(eventsTable, "EventDate=? and TagName=?", new String[]{ed,etn});
        db.close();
    }

    public void deleteTag(String tn) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tagTable, "TagName=?", new String[]{tn});
        db.delete(eventsTable, "TagName=?", new String[]{tn});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tagTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + eventsTable);
        onCreate(sqLiteDatabase);
    }
}
