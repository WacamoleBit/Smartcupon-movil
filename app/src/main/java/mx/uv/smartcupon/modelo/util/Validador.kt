package mx.uv.smartcupon.modelo.util

object Validador {
    fun esNumero(texto: String): Boolean {
        return texto.toIntOrNull() != null
    }
    fun esFechaNacimientoValida(fecha: String): Boolean {
        val regex = """^\d{4}-\d{2}-\d{2}$""".toRegex()
        return regex.matches(fecha)
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
        val regex = """^[a-zA-Z0-9._%+-]+@smartcupon\.com$""".toRegex()
        return regex.matches(correo)
    }
}