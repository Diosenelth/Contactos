package com.example.contactos.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contactos.IActualizarVista
import com.example.contactos.databinding.FragmentAgendaBinding
import com.example.contactos.modelos.AdaptadorAgenda
import com.example.contactos.modelos.AgendaSqlite

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AgendaFragment : Fragment(), IActualizarVista {

    private var _binding: FragmentAgendaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAgendaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mostrar()
    }

    private fun mostrar() {
        val db = AgendaSqlite(requireContext())
        val items = db.getArrayList()
        val adapter = AdaptadorAgenda(items, this)
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
        val dividerItemDecoration = androidx.recyclerview.widget.DividerItemDecoration(
            context, androidx.recyclerview.widget.LinearLayoutManager(this.context).orientation
        )
        binding.rv.addItemDecoration(dividerItemDecoration)
        binding.rv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun actualizarVista(String: String) {
        val db = AgendaSqlite(requireContext())
        val res = db.deleteDatos(String)
        if (res) {
            Toast.makeText(this.context, "Nota Eliminada", Toast.LENGTH_SHORT).show()
            mostrar()
        } else {
            Toast.makeText(this.context, "Error al eliminar", Toast.LENGTH_SHORT).show()
        }
    }
}