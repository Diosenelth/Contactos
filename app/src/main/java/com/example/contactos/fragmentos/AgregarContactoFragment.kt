package com.example.contactos.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contactos.modelos.ContactoModel
import com.example.contactos.modelos.ContactoSqlite
import com.example.contactos.databinding.FragmentAgregarContactoBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AgregarContactoFragment : Fragment() {

    private var _binding: FragmentAgregarContactoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAgregarContactoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = ContactoSqlite(requireContext())

        binding.guardar.setOnClickListener {
            if (binding.nombre.text.toString().isNotEmpty() && binding.telefono.text.toString()
                    .isNotEmpty()
                && binding.correo.text.toString().isNotEmpty() && binding.apellido.text.toString()
                    .isNotEmpty()
            ) {
                val nombre = binding.nombre.text.toString().uppercase()
                val telefono = binding.telefono.text.toString()
                val correo = binding.correo.text.toString().uppercase()
                val apellido = binding.apellido.text.toString().uppercase()
                val contacto = ContactoModel(null, nombre, apellido, telefono, correo)
                val save = db.saveDatos(contacto)
                if (save) Toast.makeText(this.context, "Guardado Correctamente", Toast.LENGTH_SHORT)
                    .show() else Toast.makeText(
                    this.context,
                    "Error al guardar",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(this.context, "Por favor llene todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}