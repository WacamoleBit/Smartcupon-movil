package mx.uv.smartcupon.modelo.poko

data class Cliente(
    private var id: Int? = null,
    private var nombre: String? = null,
    private var apellidoPaterno: String? = null,
    private var apellidoMaterno: String? = null,
    private var telefono: String? = null,
    private var email: String? = null,
    private var direccion: Int? = null,
    private var fechaNacimiento: String? = null,
    private var password: String? = null
) {
}