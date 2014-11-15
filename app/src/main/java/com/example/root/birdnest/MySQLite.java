package com.example.root.birdnest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLite extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "FamilyDB";

    // Defining DB
    // Family Table

    // | Family id | Family Name    |
    // |  0        |                |
    // |  1        |                |
    //

    // Species Table
    // | Species ID | Sci Name  | First Name | Last Name | Local Name | FamilyName | Location | Image | Sound |
    // |            |           |            |           |            |            |          |       |       |

    // DB's Column Declaration
    public static final String TABLE_NAME_FAMILY = "FamilyTable";
    public static final String TABLE_NAME_SPECIES = "SpeciesTable";
    public static final String FAMILY_ID = "id";
    public static final String SPECIES_ID = "id";
    public static final String SCIENTIFIC_NAME = "SciName";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String LOCAL_NAME = "LocalName";

    public static final String LOCATION = "Location";
    public static final String BIRD_IMAGES = "Image";
    public static final String BIRD_SOUND = "Sound";
    public static final String FAMILY_NAME = "FamilyName";


    // SQLite Related Declarations
    private static final String TEXT_TYPE = " TEXT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";

    // SQLite Command for Species Table
    private static final String SQL_CREATE_ENTRIES_SPECIES =
            "CREATE TABLE " + MySQLite.TABLE_NAME_SPECIES + " (" +
                    MySQLite.SPECIES_ID + " INTEGER PRIMARY KEY"+ NOT_NULL + COMMA_SEP+
                    MySQLite.SCIENTIFIC_NAME + TEXT_TYPE + COMMA_SEP +
                    MySQLite.FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    MySQLite.LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    MySQLite.LOCAL_NAME + TEXT_TYPE + COMMA_SEP +
                    MySQLite.FAMILY_NAME + TEXT_TYPE + COMMA_SEP +
                    MySQLite.LOCATION + TEXT_TYPE + COMMA_SEP +
                    MySQLite.BIRD_IMAGES + TEXT_TYPE + COMMA_SEP+
                    MySQLite.BIRD_SOUND+ TEXT_TYPE+
                    " )";
    // SQLite Command for Family Table
    private static final String SQL_CREATE_ENTRIES_FAMILY =
            "CREATE TABLE " + MySQLite.TABLE_NAME_FAMILY + " (" +
                    MySQLite.FAMILY_ID + " INTEGER PRIMARY KEY" + NOT_NULL + COMMA_SEP +
                    MySQLite.FAMILY_NAME+ TEXT_TYPE+
                    " )";


    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table
        db.execSQL(SQL_CREATE_ENTRIES_SPECIES);
        db.execSQL(SQL_CREATE_ENTRIES_FAMILY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS "+MySQLite.TABLE_NAME_SPECIES);

        this.onCreate(db);
    }


    // Add Database
    public void addBird(Bird bird){
        // Notify that new Bird is successfully added
        Log.d("addBird", bird.toString());

        // Select database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_species = new ContentValues();

        // Add content into database
        values_species.put(MySQLite.SCIENTIFIC_NAME, bird.ScientificName);
        values_species.put(MySQLite.FIRST_NAME, bird.FamilyName);
        values_species.put(MySQLite.LAST_NAME, bird.LastName);
        values_species.put(MySQLite.LOCAL_NAME, bird.LocalName);
        values_species.put(MySQLite.FAMILY_NAME, bird.FamilyName);
        values_species.put(MySQLite.BIRD_IMAGES, bird.Image);
        values_species.put(MySQLite.BIRD_SOUND, bird.Sound);
        values_species.put(MySQLite.LOCATION, bird.Location);
        db.insert(TABLE_NAME_SPECIES,null,values_species);

        db.close();
    }

    public void addFamily(BirdFamily bFamily){
        Log.d("addFamily", bFamily.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_family = new ContentValues();

        // Add content into database
        values_family.put(MySQLite.FAMILY_NAME, bFamily.FamilyName);
        db.insert(TABLE_NAME_FAMILY,null,values_family);

        db.close();

    }

    // Get All Bird
    public List<Bird> getAllBird(){
        List<Bird> birds = new LinkedList<Bird>();

        String query = "SELECT  * FROM " + TABLE_NAME_SPECIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the database
        Bird bird = null;
        if (cursor.moveToFirst()){
            do {
                bird = new Bird();
                bird.setId(Integer.parseInt(cursor.getString(0)));
                bird.setScientificName(cursor.getString(1));
                bird.setFirstName(cursor.getString(2));
                bird.setLastName(cursor.getString(3));
                bird.setLocalName(cursor.getString(4));
                bird.setFamilyName(cursor.getString(5));
                bird.setLocation(cursor.getString(6));
                bird.setImage(cursor.getString(7));
                bird.setSound(cursor.getString(8));
                birds.add(bird);
            } while(cursor.moveToNext());

        }

        Log.d("getAllBirds()", birds.toString());

        return birds;
    }

    public List<BirdFamily> getAllBirdFamily(){
        List<BirdFamily> birdFamilyList = new LinkedList<BirdFamily>();

        String query = "SELECT  * FROM " + TABLE_NAME_FAMILY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        BirdFamily birdFamily;
        // Loop through the database
        if (cursor.moveToFirst()){
            do {
                birdFamily = new BirdFamily();
                birdFamily.setId(Integer.parseInt(cursor.getString(0)));
                birdFamily.setFamilyName(cursor.getString(1));
            } while(cursor.moveToNext());

        }

        Log.d("getAllBirdFamily()", birdFamilyList.toString());

        return birdFamilyList;
    }

    // Clean Database
    public void cleanDB (){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MySQLite.TABLE_NAME_SPECIES);
        db.execSQL("DELETE FROM " + MySQLite.TABLE_NAME_FAMILY);

    }


    // Read Database and returns class associated with the query
    public List<Bird> getBird(String Query, String Column){
        List<Bird> birds = new LinkedList<Bird>();
        SQLiteDatabase db=this.getReadableDatabase();
        // Db exec : Search Table for Entry Related to Query of a Specified Column
        String DbCommand ="SELECT  * FROM " + TABLE_NAME_SPECIES+" WHERE "+ Column + "=" + "\"" + Query+ "\"" ;
        Cursor cursor = db.rawQuery(DbCommand,null);

        Bird bird = null;
        if (cursor.moveToFirst()){
            do {
                bird = new Bird();
                bird.setId(Integer.parseInt(cursor.getString(0)));
                bird.setName(cursor.getString(1));
                bird.setLocation(cursor.getString(2));
                bird.setImage(cursor.getString(3));
                bird.setSound(cursor.getString(4));
                birds.add(bird);
            } while(cursor.moveToNext());

        }

        Log.d("getBird(" + Query+", " + Column + ")", birds.toString());

        return birds;
    }

    public List<BirdFamily> getFamily(String Query, String Column){
        List<BirdFamily> birdFamilyList = new LinkedList<BirdFamily>();
        SQLiteDatabase db=this.getReadableDatabase();
        // Db exec : Search Table for Entry Related to Query of a Specified Column
        String DbCommand ="SELECT  * FROM " + TABLE_NAME_SPECIES+" WHERE "+ Column + "=" + "\"" + Query+ "\"" ;
        Cursor cursor = db.rawQuery(DbCommand,null);

        BirdFamily birdFamily = null;
        if (cursor.moveToFirst()){
            do {
                birdFamily = new BirdFamily();
                birdFamily.setId(Integer.parseInt(cursor.getString(0)));
                birdFamily.setFamilyName(cursor.getString(1));
                birdFamilyList.add(birdFamily);
            } while(cursor.moveToNext());

        }

        Log.d("getBird(" + Query+", " + Column + ")", birdFamilyList.toString());

        return birdFamilyList;
    }


}
