package com.example.travissimple;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
        //Change test
        //Commit test

        // 1. Create a SqlBrite instance which is an adapter for the library functionality.
        SqlBrite sqlBrite = SqlBrite.create();
        // 2. Pass a SQLiteOpenHelper instance and a Scheduler to create a BriteDatabase.
        UsersDatabaseHelper openHelper = new UsersDatabaseHelper(getApplicationContext());
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());

        /*
        * 3. The BriteDatabase.createQuery method is similar to SQLiteDatabase.rawQuery except it
        * takes an additional parameter of table(s) on which to listen for changes.
        * Subscribe to the returned Observable<Query> which will immediately notify with a Query to run.
        * */

        final AtomicInteger queries = new AtomicInteger();

        Observable<SqlBrite.Query> users = db.createQuery("users", "SELECT * FROM users");
        Subscription s = users.subscribe(new Action1<SqlBrite.Query>() {
            @Override public void call(SqlBrite.Query query) {
                Cursor cursor = query.run();
                queries.getAndIncrement();

                try {
                    if (cursor.moveToFirst()) {
                        do {
                            String nameUser = cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_NAME));
                            String ageUser = cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.KEY_USER_ID));

                            Log.d(TAG, "logInformation: " + nameUser);
                            Log.d(TAG, "logInformation: " + ageUser);

                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Error while trying to get posts from database");
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }

            }
        });

        // Use transactions to prevent large changes to the data from spamming your subscribers.
        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            System.out.println("Queries: " + queries.get()); // Prints 1
            ContentValues values = UsersMapper.contentValues()
                    .id(queries.get() + 1)
                    .age(31)
                    .userName("Raul")
                    .build();
            db.insert(Users.TABLE_NAME, values);
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }

        System.out.println("Queries: " + queries.get()); // Prints 4
        // 4. Unsubscribe from the returned Subscription to stop getting updates.
        s.unsubscribe();


    }

}
