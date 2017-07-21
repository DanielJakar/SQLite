package danandroid.course.sqlite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *An sqlite DataBase Helper
 *
 * Create a database
 * access the database
 * modify the database as necessary
 */

public class TodosDBHelper extends SQLiteOpenHelper{
    public TodosDBHelper(Context context) {
        super(context, TodosContract.DBName, null, TodosContract.DBVersion);
    }

    //will run once, when the database is first created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //write sql script to create the tables.
        db.execSQL("CREATE TABLE " + TodosContract.TBL_TODOS+ " (" +
                              TodosContract.TBL_TODOS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              TodosContract.TBL_TODOS_COL_MISSION + " TEXT NOT NULL, " +
                              TodosContract.TBL_TODOS_COL_IMPORTANCE + " TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //modify the database as necessary
    }


//A nice design pattern for databases:
    public static class TodosContract{

    /**
     * The database name
     */
        public static final String DBName = "Todos";
    /**
     * The current data base version
     */
        public static final int DBVersion = 1;

    public static final String TBL_TODOS = "Todos";

    public static final String TBL_TODOS_COL_MISSION = "mission";
    public static final String TBL_TODOS_COL_ID = "id";
    public static final String TBL_TODOS_COL_IMPORTANCE = "importance";
    }
}