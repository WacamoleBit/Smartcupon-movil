package mx.uv.smartcupon.modelo.poko

class DatosCliente {
    var cliente: Cliente ?= Cliente()
    var direccion: Direccion?= Direccion()
    var filasAfectadas: Int = 0
    var error: String = ""
}