package com.example.kolot.hackathon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetNumberActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText editText;
    private Button buttonAdd;
    private Button buttonRead;
    private Button buttonClear;
    public String number;
    private SQLiteDatabase db;

    private DBHelper dbHelper;

    public static final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_number);

        dbHelper = new DBHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        editText = (EditText) findViewById(R.id.editText);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonRead = (Button) findViewById(R.id.buttonRead);
        buttonClear = (Button) findViewById(R.id.buttonClear);

        buttonAdd.setOnClickListener(this);
        buttonRead.setOnClickListener(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void onClick(View view) {

        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        number = editText.getText().toString();

// подключаемся к БД
        db = dbHelper.getWritableDatabase();



        switch (view.getId()) {
            case R.id.buttonAdd:
                Log.d("myTag", "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("number", number);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d("myTag", "row inserted, ID = " + rowID);
                Toast.makeText(SetNumberActivity.this, "Nuber "+ number + " has been added.", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonRead:
                Log.d("myTag", "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int numberColIndex = c.getColumnIndex("number");


                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d("myTag",
                                "ID = " + c.getInt(idColIndex) +
                                        ", number = " + c.getString(numberColIndex));
                        Toast.makeText(SetNumberActivity.this, "Nuber is "+ number, Toast.LENGTH_LONG).show();

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d("myTag", "0 rows");
                c.close();
                break;
            case R.id.buttonClear:
                db.delete("mytable", null, null);
                Log.d("clear", "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d("clear", "deleted rows count = " + clearCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();

    }




}

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("myTag", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "number"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

