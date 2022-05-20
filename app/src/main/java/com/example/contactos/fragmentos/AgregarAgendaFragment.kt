package com.example.contactos.fragmentos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contactos.R
import com.example.contactos.databinding.FragmentAgregarNotaBinding
import com.example.contactos.modelos.AgendaModel
import com.example.contactos.modelos.AgendaSqlite
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AgregarAgendaFragment : Fragment() {

    private var _binding: FragmentAgregarNotaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAgregarNotaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = AgendaSqlite(requireContext())
        binding.tvTitle.text="Agregar a la agenda"
        binding.fecha.setOnClickListener{showDatePickerDialog()}
        binding.hora.setOnClickListener{showTimePickerDialog()}
        binding.guardar.setOnClickListener {
            if (binding.fecha.text.toString().isNotEmpty() && binding.hora.text.toString()
                    .isNotEmpty() && binding.nota.text.toString().isNotEmpty()
            ) {
                val fecha = binding.fecha.text.toString().uppercase()
                val hora = binding.hora.text.toString().uppercase()
                val nota = binding.nota.text.toString().uppercase()
                val agenda = AgendaModel(null, fecha,hora,nota)
                val save = db.saveDatos(agenda)
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

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
           val mDatePickerDialog = DatePickerDialog(
                requireContext(),
                { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    binding.fecha.setText(
                        String.format(
                            resources.getString(
                                R.string.GENERAL_formato_fecha
                            ), year, monthOfYear + 1, dayOfMonth
                        )
                    )
                },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH])
        mDatePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val mHourPickerDialog = TimePickerDialog(
            requireContext(),
            { _: TimePicker?, hourOfDay: Int, minute: Int ->

                val horaFormateada =
                    if (hourOfDay < 10) ("0$hourOfDay") else hourOfDay.toString()
                val minutoFormateado =
                    if (minute < 10) ("0$minute") else minute.toString()
                val hora = "$horaFormateada:$minutoFormateado"
                binding.hora.setText(
                    hora
                    //                    "$horaFormateada:$minutoFormateado:00"
                )
            },
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE],
            false
        )
        mHourPickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}