package com.example.contactos.modelos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.contactos.SQLiteHelper

class AgendaSqlite(val context: Context) {
    private val db: SQLiteDatabase = SQLiteHelper(context).writableDatabase

    fun getArrayList(): ArrayList<AgendaModel> {
        val cursor = db.rawQuery("SELECT * FROM Agenda order by fecha", null)
        val items = ArrayList<AgendaModel>()
        with(cursor) {
            while (this!!.moveToNext()) {
                items.add(
                    AgendaModel(
                        getString(getColumnIndexOrThrow("id")),
                        getString(getColumnIndexOrThrow("fecha")),
                        getString(getColumnIndexOrThrow("hora")),
                        getString(getColumnIndexOrThrow("notas"))
                    )
                )
            }
        }
        return items
    }

    fun saveDatos(agendaModel: AgendaModel): Boolean {
        val contentValues = ContentValues()
        contentValues.put("fecha", agendaModel.fecha)
        contentValues.put("hora", agendaModel.hora)
        contentValues.put("notas", agendaModel.notas)
        val resultado = db.insert("Agenda", null, contentValues)
        return resultado.toInt() != -1
    }

    fun updateDatos(agendaModel: AgendaModel): Boolean {
        val contentValues = ContentValues()
        contentValues.put("fecha", agendaModel.fecha)
        contentValues.put("hora", agendaModel.hora)
        contentValues.put("notas", agendaModel.notas)
        val resultado =
            db.update("Agenda", contentValues, "id = ?", arrayOf(agendaModel.id))
        return resultado != -1
    }

    fun deleteDatos(id: String): Boolean {
        val resultado = db.delete("Agenda", "id = ?", arrayOf(id))
        return resultado != -1
    }

}