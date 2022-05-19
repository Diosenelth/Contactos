package com.example.contactos.modelos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.contactos.SQLiteHelper

class ContactoSqlite(val context: Context) {
    private val db: SQLiteDatabase = SQLiteHelper(context).writableDatabase

    fun getArrayList(): ArrayList<ContactoModel> {
        val cursor = db.rawQuery("SELECT * FROM Contactos order by nombre", null)
        val items = ArrayList<ContactoModel>()
        with(cursor) {
            while (this!!.moveToNext()) {
                items.add(
                    ContactoModel(
                        getString(getColumnIndexOrThrow("id")),
                        getString(getColumnIndexOrThrow("nombre")),
                        getString(getColumnIndexOrThrow("apellido")),
                        getString(getColumnIndexOrThrow("telefono")),
                        getString(getColumnIndexOrThrow("correo"))
                    )
                )
            }
        }
        return items
    }

    fun saveDatos(contactoModel: ContactoModel): Boolean {
        val contentValues = ContentValues()
        contentValues.put("nombre", contactoModel.nombre)
        contentValues.put("apellido", contactoModel.apellido)
        contentValues.put("telefono", contactoModel.telefono)
        contentValues.put("correo", contactoModel.correo)
        val resultado = db.insert("Contactos", null, contentValues)
        return resultado.toInt() != -1
    }

    fun updateDatos(contactoModel: ContactoModel): Boolean {
        val contentValues = ContentValues()
        contentValues.put("nombre", contactoModel.nombre)
        contentValues.put("apellido", contactoModel.apellido)
        contentValues.put("telefono", contactoModel.telefono)
        contentValues.put("correo", contactoModel.correo)
        val resultado =
            db.update("Contactos", contentValues, "id = ?", arrayOf(contactoModel.id))
        return resultado != -1
    }

    fun deleteDatos(id: String): Boolean {
        val resultado = db.delete("Contactos", "id = ?", arrayOf(id))
        return resultado != -1
    }

}