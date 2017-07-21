package danandroid.course.sqlite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import danandroid.course.sqlite.models.Todo;

/**
 * Data Access Object.
 * access db helper
 */

//Singleton is not thread safe.
// Unless we make it thread safe...synchronized
    // Synchronized is nor efficient since 99.9% of the time the instance will not be null
    // to fix this we use double check
public class DAO {
    private SQLiteDatabase db;
    private Context context;
    private DAO(Context context /*No Instances please*/){
        db = new TodosDBHelper(context).getWritableDatabase();

    }
    private static DAO sharedInstance;
    //Factory method
    public static DAO getInstance (Context context){
        // if no instance created? -> new Instance
        //save this one instance.

        //double checked locking on singleton
        if (DAO.sharedInstance == null) {
            synchronized (DAO.class) {
                if (sharedInstance == null) {
                    DAO.sharedInstance = new DAO(context);
                }
            }
        }
        return DAO.sharedInstance;
    }

    /**
     * add todos
     */
    public void addTodo (String mission, String importance){
        //maps key value pairs
        ContentValues values = new ContentValues();
        values.put(TodosDBHelper.TodosContract.TBL_TODOS_COL_MISSION, mission);
        values.put(TodosDBHelper.TodosContract.TBL_TODOS_COL_IMPORTANCE, importance);

        db.insert(TodosDBHelper.TodosContract.TBL_TODOS, null, values);

    }

    public List<Todo> getTodos(){
        ArrayList<Todo> todos = new ArrayList<>();


        String[] columns = {"Mission", "importance", "id"};
        String where = "mission LIKE ? AND importance = ?";
        String [] selectionArgs = {"C%", "High"};

        //SELECT * FROM Todos
//        db.query(TodosDBHelper.TodosContract.TBL_TODOS,
//                null /* Same as SELECT * FROM Todos*/,
//                where,
//                selectionArgs,
//                null /* Did not study aggregation functions*/,
//                null /* Did not study aggregation functions*/,
//                "importance"
//                );

        Cursor cursor = db.query(TodosDBHelper.TodosContract.TBL_TODOS,
                new String[] {"id", "mission", "importance"}
                , null, null, null, null, "Importance");

        if (cursor != null && cursor.moveToFirst()){
            //int missionIDx = cursor.getColumnIndex("mission");
            do {
                //String mission = cursor.getString(missionIDx);
                int id = cursor.getInt(0);
                String mission = cursor.getString(1);
                String importance = cursor.getString(2);

                Todo todo = new Todo(id, mission, importance);
                todos.add(todo);
            }while (cursor.moveToNext());
        }

        return todos;
    }
}
