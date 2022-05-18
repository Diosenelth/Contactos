package com.example.contactos

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

class AdaptadorContactos(private val model: ArrayList<ContactoModel>,private val actualizar: IActualizarVista) :
    RecyclerView.Adapter<AdaptadorContactos.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.nombreApellido)
        val telefono: TextView = view.findViewById(R.id.tel)
        val inicial: TextView = view.findViewById(R.id.Inicial)
        val llamar: ImageView= view.findViewById(R.id.llamar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.modelo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nomAp=model[position].nombre+" "+model[position].apellido
        holder.tvNombre.text = nomAp
        holder.telefono.text = model[position].telefono
        holder.inicial.text = model[position].nombre!!.substring(0,1).uppercase()
        holder.itemView.setOnLongClickListener {
            SweetAlertDialog(it.context,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Desea eliminar el contacto?")
                .setConfirmText("Si")
                .setCancelText("No")
                .setConfirmClickListener {sweetAlertDialog ->
                    actualizar.actualizarVista(model[position].id.toString())
                    sweetAlertDialog.dismissWithAnimation()
                }
                .show()
            true
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", model[position].id)
            bundle.putString("nombre", model[position].nombre)
            bundle.putString("apellido", model[position].apellido)
            bundle.putString("telefono", model[position].telefono)
            bundle.putString("correo", model[position].correo)

            it.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
//            val intent = Intent(holder.itemView.context, SecondFragment(model[position]).javaClass)
//            startActivity(holder.itemView.context, intent, null)
        }
        holder.llamar.setOnClickListener {
            val uri = "tel:" + model[position].telefono
            val intent = Intent(Intent.ACTION_DIAL).setData(Uri.parse(uri))
            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

}