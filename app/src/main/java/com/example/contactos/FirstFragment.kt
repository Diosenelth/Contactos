package com.example.contactos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contactos.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = SQLiteHelper(this.context)
        val res=db.getDatos()
//        val cursor = db.rawQuery("select * from Contactos",null)
        val items = ArrayList<ContactoModel>()
        with(res) {
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
        val adapter = AdaptadorContactos(items)
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
}