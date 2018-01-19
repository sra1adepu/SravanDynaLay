package com.example.admin.jsonarray;

/**
 * Created by admin on 11-05-2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "MoOffender";
    public static final String CONTACTS_TABLE_NAME = "offenders";
    public static final String CONTACTS_OFFENDER_ID = "_id";
    public static final String CONTACTS_COLUMN_RELATIONTYPE = "relationtype";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_MOBILENO = "mobileno";
    public static final String CONTACTS_COLUMN_ADDRESS = "address";
    public static final String CONTACTS_COLUMN_OCCUPATION = "occupation";
    public static final String CONTACTS_COLUMN_ANYPROOF = "anyproof";
    public static final String CONTACTS_COLUMN_CATEGORY = "category";


    public static final String PERSONAL_TABLE_NAME = "personal";
    //public static final String PERSONAL_OFFENDER_ID = "_id";
    public static final String PERSONAL_COLUMN_OFFENDERNAME = "offendername";
    public static final String PERSONAL_COLUMN_ALIASES = "aliases";
    public static final String PERSONAL_COLUMN_DOB = "dob";
    public static final String PERSONAL_COLUMN_AGE = "age";
    public static final String PERSONAL_COLUMN_MOBILENUMBER = "mobilenumber";
    public static final String PERSONAL_COLUMN_EMAIL = "email";
    public static final String PERSONAL_COLUMN_PSID = "psid";
    public static final String PERSONAL_COLUMN_VEHILCEDETAILS = "vehicledetails";
    public static final String PERSONAL_COLUMN_CURRENTACITIVITY = "cactivity";
    public static final String PERSONAL_COLUMN_PRESENTADDRESS = "presentaddress";
    public static final String PERSONAL_COLUMN_PERMENTADDRESS = "peraddress";
    public static final String PERSONAL_COLUMN_OFFENDEROWNERNAME = "ownername";
    public static final String PERSONAL_COLUMN_DOLR = "dolr";
    public static final String PERSONAL_COLUMN_PSAR = "psar";
    public static final String PERSONAL_COLUMN_DOR = "dor";
    public static final String PERSONAL_COLUMN_MO = "mo";
    public static final String PERSONAL_COLUMN_HS = "hs";
    public static final String PERSONAL_COLUMN_LPC = "lpc";
    public static final String PERSONAL_COLUMN_REASON = "reason";
    public static final String PERSONAL_COLUMN_CATEGORY = "category";
    //Associates table
    public static final String ASSOCIATES_TABLE_NAME = "associates";
    public static final String ASSOCIATES_OFFENDER_ID = "_id";
    public static final String ASSOCIATES_COLUMN_ASSOCIATESNAME = "associatesname";
    public static final String ASSOCIATES_COLUMN_ASSOCIATESTYPE = "associatestype";
    public static final String ASSOCIATES_COLUMN_ASSOCIATESDESCRIPTION = "associatesdescription";
    public static final String ASSOCIATES_COLUMN_ASSOCIATESMOBILE = "associatesmobile";
    public static final String ASSOCIATES_COLUMN_ASSOCIATESOCCUPATION = "associatesoccupation";
    public static final String ASSOCIATES_COLUMN_ASSOCIATESADDRESS = "associatesaddress";
    public static final String ASSOCIATES_COLUMN_CATEGORY = "associatescategory";
// Involved table
    public static final String INVOLVED_TABLE_NAME = "involvedcases";
    public static final String INVOLVED_OFFENDER_ID = "_id";
    public static final String INVOLVED_COLUMN_CRIMENO = "crimeno";
    public static final String INVOLVED_COLUMN_POLICESTATION = "policestation";
    public static final String INVOLVED_COLUMN_DATEOFARREST = "dateofarrest";
    public static final String INVOLVED_COLUMN_STATUSOFCRIME = "statusofcrime";
    public static final String INVOLVED_COLUMN_CATEGORY = "involvedcasescategory";
    //Offender Documents
    public static final String DOCUMENTS_TABLE_NAME = "offenderdocuments";
    public static final String DOCUMENTS_OFFENDER_ID = "_id";
    public static final String DOCUMENTS_COLUMN_PROOFTYPE = "prooftype";
    public static final String DOCUMNETS_COLUMN_PROOFVALUE = "proofvalue";
    public static final String DOCUMNETS_COLUMN_PROOFIDATTACHMENT = "proofidattachment";
    public static final String DOCUMENTS_COLUMN_CATEGORY = "offenderdocumentscategory";
    // photo
    public static final String DOCUMENTS_PHOTO_NAME = "photo";
    public static final String DOCUMENTS_PHOTO_PHOTOTYPE = "phototype";
    SQLiteDatabase db;
    Context c;
    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE_NAME + "("
//                + CONTACTS_OFFENDER_ID + " INTEGER ," + CONTACTS_COLUMN_RELATIONTYPE + " TEXT,"
//                + CONTACTS_COLUMN_NAME + " TEXT," + CONTACTS_COLUMN_MOBILENO + " TEXT," +  CONTACTS_COLUMN_ADDRESS + " TEXT," + CONTACTS_COLUMN_OCCUPATION + " TEXT" + CONTACTS_COLUMN_ANYPROOF + "TEXT"+ ")";


        String CREATE_CONTACTS_TABLE="CREATE TABLE offenders (_id INTEGER, relationtype TEXT,name TEXT,mobileno text,address text,occupation text,anyproof text,category text)";
        String ASSOCIATES_CONTACTS_TABLE="CREATE TABLE associates (_id INTEGER, associatesname TEXT,associatestype TEXT,associatesdescription text,associatesmobile text,associatesoccupation text,associatesaddress text,associatescategory text)";
        String INVOLVED_TABLE_NAME="CREATE TABLE involvedcases (_id INTEGER, crimeno TEXT,policestation TEXT,dateofarrest text,statusofcrime text,involvedcasescategory text)";
        String DOCUMENTS_TABLE_NAME="CREATE TABLE offenderdocuments (_id INTEGER, prooftype TEXT,proofvalue TEXT,proofidattachment text,offenderdocumentscategory text)";
        String PERSONAL_TABLE_NAME="CREATE TABLE personal (offendername TEXT,aliases TEXT,dob text,age text,mobilenumber text,email text,psid text,vehicledetails text,cactivity text,presentaddress text,peraddress text,ownername text,dolr text,psar text,dor text,mo text,hs text,lpc text,reason text,category text)";
        String DOCUMENTS_PHOTO_NAME="CREATE TABLE photo (phototype TEXT)";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(ASSOCIATES_CONTACTS_TABLE);
        db.execSQL(INVOLVED_TABLE_NAME);
        db.execSQL(DOCUMENTS_TABLE_NAME);
        db.execSQL(PERSONAL_TABLE_NAME);
        db.execSQL(DOCUMENTS_PHOTO_NAME);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ASSOCIATES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INVOLVED_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DOCUMENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PERSONAL_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DOCUMENTS_PHOTO_NAME);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
//


    public void associatesinsert(int i,String associatename,String associateaddress,String associatestype, String associatesdescription,String mobileno,String associatesoccupation,String associates){
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put(ASSOCIATES_OFFENDER_ID, i); // Contact Name
    values.put(ASSOCIATES_COLUMN_ASSOCIATESNAME, associatename);
    values.put(ASSOCIATES_COLUMN_ASSOCIATESTYPE, associatestype);
    values.put(ASSOCIATES_COLUMN_ASSOCIATESDESCRIPTION, associatesdescription);
    values.put(ASSOCIATES_COLUMN_ASSOCIATESMOBILE,  mobileno);
    values.put(ASSOCIATES_COLUMN_ASSOCIATESOCCUPATION, associatesoccupation);
    values.put(ASSOCIATES_COLUMN_ASSOCIATESADDRESS, associateaddress);// Contact Phone getCategory
    values.put(ASSOCIATES_COLUMN_CATEGORY, associates);
    // Inserting Row
    Log.d("sravan1", String.valueOf(values));
    db.insert(ASSOCIATES_TABLE_NAME, null, values);
    db.close(); // Closing database connection
}

    // Adding new contact
   public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CONTACTS_OFFENDER_ID, contact.getID()); // Contact Name
        values.put(CONTACTS_COLUMN_RELATIONTYPE, contact.getRelationtype());
        values.put(CONTACTS_COLUMN_NAME, contact.getName());
        values.put(CONTACTS_COLUMN_MOBILENO, contact.getMobileno());
        values.put(CONTACTS_COLUMN_ADDRESS, contact.getAddress());
        values.put(CONTACTS_COLUMN_OCCUPATION, contact.getOccupation());
        values.put(CONTACTS_COLUMN_ANYPROOF, contact.getAnyproof());// Contact Phone getCategory
        values.put(CONTACTS_COLUMN_CATEGORY, contact.getCategory());
        // Inserting Row
        db.insert(CONTACTS_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CONTACTS_TABLE_NAME, new String[] { CONTACTS_OFFENDER_ID,CONTACTS_COLUMN_RELATIONTYPE,
                CONTACTS_COLUMN_NAME, CONTACTS_COLUMN_MOBILENO,CONTACTS_COLUMN_ADDRESS,CONTACTS_COLUMN_OCCUPATION
                ,CONTACTS_COLUMN_ANYPROOF,CONTACTS_COLUMN_CATEGORY}, CONTACTS_OFFENDER_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public Cursor getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;
        //String selectQuery1="SELECT  * FROM " + ASSOCIATES_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       // Cursor cursor1=db.rawQuery(selectQuery1,null);
       // return contact list
        return cursor;

    }
    //delete all family details
    public void deleteallfamily() {
        SQLiteDatabase sd = this.getWritableDatabase();
        sd.delete("offenders", null, null);


        sd.close();
    }



    public Cursor getAllAsscoiaates(){
        String selectQuery1="SELECT  * FROM " + ASSOCIATES_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1=db.rawQuery(selectQuery1,null);
        return cursor1;
    }

    public void open(){
        db = this.getWritableDatabase();
    }
    //delete associtaes

//    public void deleteallassocitates() {
//        SQLiteDatabase sd = this.getWritableDatabase();
//        sd.delete("associates", null, null);
//
//
//        sd.close();
//    }

    public Cursor getAllInvolved(){
        String selectQuery2="SELECT  * FROM " + INVOLVED_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor2=db.rawQuery(selectQuery2,null);
        return cursor2;
    }

    public void deleteallcases() {
        SQLiteDatabase sd = this.getWritableDatabase();
        sd.delete("involvedcases", null, null);


        sd.close();
    }
    public Cursor getAllDocuments(){
        String selectQuery3="SELECT  * FROM " + DOCUMENTS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor3=db.rawQuery(selectQuery3,null);
        return cursor3;
    }

    public void deletealldocuments() {
        SQLiteDatabase sd = this.getWritableDatabase();
        sd.delete("offenderdocuments", null, null);
        sd.close();
    }




    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACTS_OFFENDER_ID, contact.getName()); // Contact Name
        values.put(CONTACTS_COLUMN_RELATIONTYPE, contact.getOccupation());
        values.put(CONTACTS_COLUMN_NAME, contact.getName());
        values.put(CONTACTS_COLUMN_MOBILENO, contact.getMobileno());
        values.put(CONTACTS_COLUMN_ADDRESS, contact.getAddress());
        values.put(CONTACTS_COLUMN_OCCUPATION, contact.getOccupation());
        values.put(CONTACTS_COLUMN_ANYPROOF, contact.getAnyproof());// Contact Phone
        values.put(CONTACTS_COLUMN_CATEGORY, contact.getCategory());
        // updating row
        return db.update(CONTACTS_TABLE_NAME, values, CONTACTS_OFFENDER_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, CONTACTS_OFFENDER_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void involvedinsert(int i, String crimeno, String policestation, String dateofarrest, String statusofcrime, String involved) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INVOLVED_OFFENDER_ID, i); // Contact Name
        values.put(INVOLVED_COLUMN_CRIMENO, crimeno);
        values.put(INVOLVED_COLUMN_POLICESTATION, policestation);
        values.put(INVOLVED_COLUMN_DATEOFARREST, dateofarrest);
        values.put(INVOLVED_COLUMN_STATUSOFCRIME,  statusofcrime);
        values.put(INVOLVED_COLUMN_CATEGORY,  involved);

        // Inserting Row
        Log.d("involvedsravan", String.valueOf(values));
        db.insert(INVOLVED_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void offenderdocumentinsert(int i, String prooftype, String proofvalue, String photo, String documents) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DOCUMENTS_OFFENDER_ID, i); // Contact Name
        values.put(DOCUMENTS_COLUMN_PROOFTYPE, prooftype);
        values.put(DOCUMNETS_COLUMN_PROOFVALUE, proofvalue);
        values.put(DOCUMNETS_COLUMN_PROOFIDATTACHMENT, photo);
        values.put(DOCUMENTS_COLUMN_CATEGORY,  documents);


        // Inserting Row
        Log.d("documentssravan", String.valueOf(values));
        db.insert(DOCUMENTS_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void personalinsert(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10, String s11, String s12, String s13, String s14, String s15, String s16, String s17, String s18, String s19, String associates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // Contact Name
        values.put(PERSONAL_COLUMN_OFFENDERNAME, s1);
        values.put(PERSONAL_COLUMN_ALIASES, s2);
        values.put(PERSONAL_COLUMN_DOB, s3);
        values.put(PERSONAL_COLUMN_AGE,  s4);
        values.put(PERSONAL_COLUMN_MOBILENUMBER,  s5);
        values.put(PERSONAL_COLUMN_EMAIL,  s6);
        values.put(PERSONAL_COLUMN_PSID,  s7);
        values.put(PERSONAL_COLUMN_VEHILCEDETAILS,  s8);
        values.put(PERSONAL_COLUMN_CURRENTACITIVITY,  s9);
        values.put(PERSONAL_COLUMN_PRESENTADDRESS,  s10);
        values.put(PERSONAL_COLUMN_PERMENTADDRESS,  s11);
        values.put(PERSONAL_COLUMN_OFFENDEROWNERNAME,  s12);
        values.put(PERSONAL_COLUMN_DOLR,  s13);
        values.put(PERSONAL_COLUMN_PSAR,  s14);
        values.put(PERSONAL_COLUMN_DOR,  s15);
        values.put(PERSONAL_COLUMN_MO,  s16);
        values.put(PERSONAL_COLUMN_HS,  s17);
        values.put(PERSONAL_COLUMN_LPC,  s18);
        values.put(PERSONAL_COLUMN_REASON,  s19);
        values.put(PERSONAL_COLUMN_CATEGORY,  associates);

        long i =db.insert(PERSONAL_TABLE_NAME, null, values);
        db.close();

    }
    public void photoinsert(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCUMENTS_PHOTO_PHOTOTYPE, s);
        db.insert(DOCUMENTS_PHOTO_NAME, null, values);
        db.close();
    }



    public Cursor getAllpersonalinformation(){
        String selectQuery1="SELECT  * FROM " + PERSONAL_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1=db.rawQuery(selectQuery1,null);
        return cursor1;
    }

    public void deleteallpersonal() {
        SQLiteDatabase sd = this.getWritableDatabase();
        sd.delete("associates", null, null);


        sd.close();
    }

    public Cursor getphotoinsert() {
        String selectQuery1="SELECT  * FROM " + DOCUMENTS_PHOTO_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1=db.rawQuery(selectQuery1,null);
        return cursor1;
    }
}
