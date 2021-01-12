package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LawyersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lawyers.db";

    public LawyersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LawyersContract.LawyerEntry.TABLE_NAME + " ("
                + LawyersContract.LawyerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LawyersContract.LawyerEntry.ID + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.NAME + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.SPECIALTY + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.BIO + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + LawyersContract.LawyerEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //De momento no ponemos nada
    }
}
