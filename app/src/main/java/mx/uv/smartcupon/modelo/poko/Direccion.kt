package mx.uv.smartcupon.modelo.poko

data class Direccion(
    private var idDireccion:Int? = null,
    private var calle:String? = null,
    private var numero: Int? = null,
    private var colonia: String? = null,
    private var codigoPostal: String? = null,
    private var ciudad: Int? = null,
    private var tipodireccion: Int? = null,
    private var estado: Int? = null,
) {

}