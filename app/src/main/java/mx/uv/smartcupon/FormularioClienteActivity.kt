package mx.uv.smartcupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityFormularioClienteBinding
import mx.uv.smartcupon.modelo.poko.DatosCliente
import mx.uv.smartcupon.modelo.poko.Mensaje
import mx.uv.smartcupon.modelo.util.Constantes

class FormularioClienteActivity : AppCompatActivity() {
    private lateinit var datosCliente: DatosCliente
    private lateinit var binding: ActivityFormularioClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val cadenaJson = intent.getStringExtra("datos")

        if (!cadenaJson.isNullOrEmpty()) {
            serealizarDatos(cadenaJson)
            cargarDatos()
        }

        binding.btnGuardar.setOnClickListener {
            recuperarDatos()
            peticionEditarCliente(datosCliente)
        }

        binding.imgbtRegresar.setOnClickListener {
            finish()
        }


    }

    fun cargarDatos() {
        binding.etNombre.setText(datosCliente.cliente!!.nombre)
        binding.etApellidoPaterno.setText(datosCliente.cliente!!.apellidoPaterno)
        binding.etApellidoMaterno.setText(datosCliente.cliente!!.apellidoMaterno)
        binding.etFechaNacimiento.setText(datosCliente.cliente!!.fechaNacimiento)
        binding.etTelefono.setText(datosCliente.cliente!!.telefono)
        binding.etNumero.setText(datosCliente.direccion!!.numero.toString())
        binding.etCalle.setText(datosCliente.direccion!!.calle)

        Toast.makeText(this@FormularioClienteActivity, datosCliente.direccion!!.idDireccion.toString(),Toast.LENGTH_SHORT ).show()
    }

    fun recuperarDatos(){
        datosCliente.cliente!!.nombre = binding.etNombre.text.trim().toString()
        datosCliente.cliente!!.apellidoPaterno = binding.etApellidoPaterno.text.trim().toString()
        datosCliente.cliente!!.apellidoMaterno = binding.etApellidoMaterno.text.trim().toString()
        datosCliente.cliente!!.fechaNacimiento = binding.etFechaNacimiento.text.trim().toString()
        datosCliente.cliente!!.telefono = binding.etTelefono.text.trim().toString()
        datosCliente.direccion!!.calle = binding.etCalle.text.trim().toString()
        datosCliente.direccion!!.numero = binding.etNumero.text.trim().toString().toInt()

        if (binding.etpNuevoPassword.text.trim().toString() == binding.etpVerificarPassword.text.trim().toString()){
            datosCliente.cliente!!.password = binding.etpVerificarPassword.text.trim().toString()
        }
    }

    fun peticionEditarCliente(datosCliente: DatosCliente){
        Ion.with(this@FormularioClienteActivity)
            .load("PUT", "${Constantes.URL_WS}clientes/editarCliente")
            .setHeader("Content-Type","application/json")
            .setJsonPojoBody(datosCliente)
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializarRespuesta(result)
                }else{
                    Toast.makeText(this@FormularioClienteActivity, "Error en la peticion", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun serializarRespuesta(json: String) {
        val gson = Gson()
        val respuesta = gson.fromJson(json, Mensaje::class.java)
        if (!respuesta.error){
            Toast.makeText(this@FormularioClienteActivity, respuesta.mensaje, Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@FormularioClienteActivity, respuesta.mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    fun serealizarDatos(json: String) {
        val gson = Gson()
        datosCliente = gson.fromJson(json, DatosCliente::class.java)
        cargarDatos()
    }
}