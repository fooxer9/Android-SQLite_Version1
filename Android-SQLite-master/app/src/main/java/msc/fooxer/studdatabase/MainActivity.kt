package msc.fooxer.studdatabase

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.random
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object NAMES {
        val firstName : Array<String> = arrayOf("Амирхон", "Радмир", "Александр", "Григорий", "Никита", "Руслан",
            "Керолос", "Иван", "Кирилл", "Константин", "Куок Ань",
            "Андрей", "Даниил", "Павел", "Парвиз", "Дмитрий", "Эльвек", "Савелий")
        val secondName: Array <String> = arrayOf("Абддуалимов", "Акжигитов", "Артемов", "Болдырев", "Гриценко", "Гарянин", "Зекирьяев",
            "Исхак", "Коватьев", "Кузьмин", "Миночкин", "Нгуен", "Рабочих",
            "Сторожук", "Терентьев", "Турсунов", "Флоря", "Чимидов", "Шатров")
        val thirdName: Array<String> = arrayOf("Рустамович", "Руланович", "Андреевич", "Михайлович", "Сергеевич", "Тимурович",
            "Вильям Гиргис","Дмитриевич", "Даниилович", "", "Юрьевич", "Вадимович", "Бахоралиевич", "Эренценович", "Иванович")

        // Форматирование времени как "день.месяц.год"
        var dateFormat: DateFormat = SimpleDateFormat("EEE, dd.MM.yyyy, HH:mm:ss", Locale.getDefault())

        val NOTES = ArrayList<Item>()

    }
    lateinit var db: SQLiteDatabase
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(this)
        db = dbHelper.writableDatabase
        val contentValues = ContentValues()
        val random = Random()
        var dateText = dateFormat.format(Date())
        val cursor: Cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
        if (cursor.moveToFirst()) {
            db.delete(TABLE_NAME, null, null) // если есть записи - удалить все
        }
            for (i in 0..4) {
                val FIO: String =
                    "${secondName[random.nextInt(secondName.size)]} ${firstName[random.nextInt(firstName.size)]}" +
                            " ${thirdName[random.nextInt(thirdName.size)]}"
                contentValues.put(KEY_NAME, FIO)
                contentValues.put(KEY_TIME, dateText)
                db.insert(TABLE_NAME, null, contentValues)
            }

        //db.delete(TABLE_NAME, "$KEY_INDEX > 5", null)
        getColumns()
        cursor.close()

        openBDButton.setOnClickListener{
            startActivity(Intent(this,DataBaseActivity::class.java))
        }
        newNoteButton.setOnClickListener{
            addNew()
            Snackbar.make(it, R.string.add, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }

        lastNoteChangeButton.setOnClickListener {
            update()
            Snackbar.make(it, R.string.magic, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun getColumns() {
        val cursor: Cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(KEY_INDEX)
            val nameIndex = cursor.getColumnIndex(KEY_NAME)
            val dateIndex = cursor.getColumnIndex(KEY_TIME)
            do {
                val item = Item (cursor.getInt(idIndex),cursor.getString(nameIndex),cursor.getString(dateIndex))
                Log.d("mLog", "ID =  ${cursor.getInt(idIndex)} NAME = ${cursor.getString(nameIndex)}" +
                        " DATE = ${cursor.getString(dateIndex)}")
                NOTES.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    fun addNew() {
        val cv = ContentValues()
        val random = Random()
        val dateText = dateFormat.format(Date())
        val FIO: String =
            "${secondName[random.nextInt(secondName.size)]} ${firstName[random.nextInt(firstName.size)]}" +
                    " ${thirdName[random.nextInt(thirdName.size)]}"
        cv.put(KEY_NAME, FIO)
        cv.put(KEY_TIME, dateText)
        db.insert(TABLE_NAME, null, cv)
        val cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
        cursor.moveToLast()
        val idIndex = cursor.getColumnIndex(KEY_INDEX)
        val nameIndex = cursor.getColumnIndex(KEY_NAME)
        val dateIndex = cursor.getColumnIndex(KEY_TIME)
        NOTES.add(Item(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getString(dateIndex)))
        cursor.close()

    }

    fun update() {
        val cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
        cursor.moveToLast()
        val cv = ContentValues()
        val idIndex = cursor.getColumnIndex(KEY_INDEX)
        val nameIndex = cursor.getColumnIndex(KEY_NAME)
        val dateIndex = cursor.getColumnIndex(KEY_TIME)
        cv.put(KEY_INDEX,cursor.getInt(idIndex))
        cv.put(KEY_NAME, "Иванов Иван Иванович")
        cv.put(KEY_TIME, cursor.getString(dateIndex))
        val updCount =  db.update(TABLE_NAME, cv,"$KEY_INDEX = ${cursor.getInt(idIndex)}", null)
        Log.d("mLog", "updated rows count = " + updCount)
        cursor.close()
        val newCursor = db.query(TABLE_NAME,null,null,null,null,null,null)
        newCursor.moveToLast()
        NOTES.set(NOTES.lastIndex,Item(newCursor.getInt(idIndex),newCursor.getString(nameIndex),newCursor.getString(dateIndex)) )
        newCursor.close()
    }
}
