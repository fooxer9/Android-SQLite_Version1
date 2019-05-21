package msc.fooxer.studdatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME: String = "StudDB"
const val DATABASE_VERSION: Int = 1
const val TABLE_NAME: String = "Students"
const val KEY_INDEX: String = "_ID"
const val KEY_NAME: String = "FullName"
const val KEY_TIME: String = "Created"
class DBHelper(context: Context?, name: String? = DATABASE_NAME, factory: SQLiteDatabase.CursorFactory? = null, version: Int = DATABASE_VERSION) :
    SQLiteOpenHelper(context, name, null, version ) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("create table $TABLE_NAME($KEY_INDEX integer primary key, $KEY_NAME text,$KEY_TIME text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("drop table if exists $TABLE_NAME")
        }
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("drop table if exists $TABLE_NAME")
        }
        onCreate(db)
    }

}