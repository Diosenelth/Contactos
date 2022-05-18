package com.example.contactos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Diosenelth on 2/09/2021.
 */
class SQLiteHelper(contexto: Context?) : SQLiteOpenHelper(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Contactos (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,apellido TEXT ,telefono TEXT ,correo TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun getDatos():Cursor?{
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM Contactos order by nombre",null)
    }

    fun saveDatos(contactoModel: ContactoModel):Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nombre", contactoModel.nombre)
        contentValues.put("apellido", contactoModel.apellido)
        contentValues.put("telefono", contactoModel.telefono)
        contentValues.put("correo", contactoModel.correo)
        val resultado = db.insert("Contactos", null, contentValues)
        return resultado.toInt() != -1
    }

    fun updateDatos(contactoModel: ContactoModel):Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nombre", contactoModel.nombre)
        contentValues.put("apellido", contactoModel.apellido)
        contentValues.put("telefono", contactoModel.telefono)
        contentValues.put("correo", contactoModel.correo)
        val resultado = db.update("Contactos", contentValues, "id = ?", arrayOf(contactoModel.id.toString()))
        return resultado != -1
    }
    fun deleteDatos(id:String):Boolean {
        val db = this.writableDatabase
        val resultado = db.delete("Contactos", "id = ?", arrayOf(id))
        return resultado != -1
    }

    companion object {
        private const val NOMBRE_BASE_DATOS = "contactos.db"
        private const val VERSION_ACTUAL = 1
    }
}