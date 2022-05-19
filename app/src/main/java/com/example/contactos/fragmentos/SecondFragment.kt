package com.example.contactos.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.contactos.modelos.ContactoModel
import com.example.contactos.modelos.ContactoSqlite
import com.example.contactos.databinding.FragmentAgregarBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    private var _binding: FragmentAgregarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = ContactoSqlite(requireContext())

        binding.guardar.text = "Actualizar"
        binding.tvTitle.text = "Actualizar Contacto"
        binding.nombre.setText(arguments?.getString("nombre"))
        binding.apellido.setText(arguments?.getString("apellido"))
        binding.telefono.setText(arguments?.getString("telefono"))
        binding.correo.setText(arguments?.getString("correo"))
        binding.guardar.setOnClickListener {
            if (binding.nombre.text.toString().isNotEmpty() && binding.apellido.text.toString()
                    .isNotEmpty() && binding.telefono.text.toString()
                    .isNotEmpty() && binding.correo.text.toString().isNotEmpty()
            ) {
                val nombre = binding.nombre.text.toString().uppercase()
                val apellido = binding.apellido.text.toString().uppercase()
                val telefono = binding.telefono.text.toString()
                val correo = binding.correo.text.toString().uppercase()
                val contactos =
                    ContactoModel(arguments?.getString("id"), nombre, apellido, telefono, correo)
                val up = db.updateDatos(contactos)
                if (up) {
                    Toast.makeText(context, "actualizado", Toast.LENGTH_SHORT).show()
                    activity?.onBackPressed()
                } else {
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}