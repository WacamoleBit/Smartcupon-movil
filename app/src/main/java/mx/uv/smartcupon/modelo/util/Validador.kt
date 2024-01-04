package mx.uv.smartcupon.modelo.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Validador {
    fun esNumero(texto: String): Boolean {
        return texto.toIntOrNull() != null
    }
    fun esFechaNacimientoValida(fecha: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.isLenient = false
        try {
            val fechaNacimiento = dateFormat.parse(fecha)
            val fechaActual = Calendar.getInstance().time

            if (fechaNacimiento != null && fechaNacimiento.before(fechaActual)) {
                return true
            }
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
        }

        return false
    }
    fun esCalleValida(calle: String): Boolean {
        val regex = """^[a-zA-Z0-9\s]+$""".toRegex()
        return regex.matches(calle)
    }
    fun esCadenaValida(texto: String): Boolean {
        val regex = """^[a-zA-Z]+$""".toRegex()
        return regex.matches(texto)
    }
    fun esContrasenaValida(contrasena: String): Boolean {
        val regex = """^[a-zA-Z0-9]+$""".toRegex()
        return regex.matches(contrasena)
    }
    fun esNumeroTelefonoValido(numeroTelefono: String): Boolean {
        val regex = """^\d{10}$""".toRegex()
        return regex.matches(numeroTelefono)
    }
    fun esCorreoElectronicoValido(correo: String): Boolean {
        val regex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""".toRegex()
        return regex.matches(correo)
    }

    fun esNombreValido(nombre: String): Boolean {
        val regex = """^[a-zA-ZáéíóúüÁÉÍÓÚÜ]+(?: [a-zA-ZáéíóúüÁÉÍÓÚÜ]+)?$""".toRegex()
        return regex.matches(nombre)
    }
}