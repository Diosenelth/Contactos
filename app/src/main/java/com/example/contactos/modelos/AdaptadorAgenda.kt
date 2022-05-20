package com.example.contactos.modelos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.contactos.IActualizarVista
import com.example.contactos.R

class AdaptadorAgenda(
    private val model: ArrayList<AgendaModel>,
    private val actualizar: IActualizarVista
) :
    RecyclerView.Adapter<AdaptadorAgenda.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nota: TextView = view.findViewById(R.id.nota)
        val fechaHora: TextView = view.findViewById(R.id.fechaHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.modelo_agendas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nomAp = "Fecha "+model[position].fecha + "\nHora " + model[position].hora
        holder.fechaHora.text = nomAp
        holder.nota.text = model[position].notas
        holder.itemView.setOnLongClickListener {
            SweetAlertDialog(it.context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Desea eliminar la nota?")
                .setConfirmText("Si")
                .setCancelText("No")
                .setConfirmClickListener { sweetAlertDialog ->
                    actualizar.actualizarVista(model[position].id.toString())
                    sweetAlertDialog.dismissWithAnimation()
                }
                .show()
            true
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", model[position].id)
            bundle.putString("fecha", model[position].fecha)
            bundle.putString("hora", model[position].hora)
            bundle.putString("notas", model[position].notas)

            it.findNavController().navigate(R.id.action_FirstFragment_to_editarAgendaFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

}