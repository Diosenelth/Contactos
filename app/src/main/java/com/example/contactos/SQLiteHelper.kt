package com.example.contactos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Diosenelth on 2/09/2021.
 */
class SQLiteHelper(contexto: Context?) :
    SQLiteOpenHelper(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Contactos (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,apellido TEXT ,telefono TEXT ,correo TEXT)")
        db.execSQL("CREATE TABLE Agenda (id INTEGER PRIMARY KEY AUTOINCREMENT,fecha TEXT,hora TEXT, notas TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val NOMBRE_BASE_DATOS = "mydb.db"
        private const val VERSION_ACTUAL = 1
    }
}