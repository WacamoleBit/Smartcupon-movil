package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityFormularioClienteBinding
import mx.uv.smartcupon.modelo.poko.DatosCliente
import mx.uv.smartcupon.modelo.poko.Mensaje
import mx.uv.smartcupon.modelo.util.Constantes
import mx.uv.smartcupon.modelo.util.Validador

class FormularioClienteActivity : AppCompatActivity() {
    private lateinit var datosCliente: DatosCliente
    private lateinit var datosClienteActualizados: DatosCliente
    private lateinit var binding: ActivityFormularioClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        datosClienteActualizados = DatosCliente()
        val cadenaJson = intent.getStringExtra("datos")

        if (!cadenaJson.isNullOrEmpty()) {
            serealizarDatos(cadenaJson)
            cargarDatos()
        }

        binding.btnGuardar.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Editar")
            builder.setMessage("¿Estás seguro de editar la información?")
            builder.setPositiveButton("Confirmar") { dialog, which ->
                if(!verificarCampos()){
                    if(!validarCamposPassword()){
                        verificarCambios()
                    }
                }
            }
            builder.setNegativeButton("Cancelar") { dialog, which ->
                finish()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

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
            val intent = Intent(this@FormularioClienteActivity, HomeActivity::class.java)
            var cadenaJson = gson.toJson(datosCliente.cliente)
            intent.putExtra("cliente", cadenaJson)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this@FormularioClienteActivity, respuesta.mensaje, Toast.LENGTH_SHORT).show()
            Log.d("tag", respuesta.mensaje)
        }
    }

    fun serealizarDatos(json: String) {
        val gson = Gson()
        datosCliente = gson.fromJson(json, DatosCliente::class.java)
        cargarDatos()
    }

    fun verificarCampos(): Boolean{
        var isValido = false

        if(binding.etNombre.text.isEmpty()){
            binding.etNombre.error = "Campo obligatorio"
            isValido = true
        }else if (!Validador.esCadenaValida(binding.etNombre.text.trim().toString())){
            binding.etNombre.error = "Nombre no valido"
            isValido = true
        }
        if(binding.etApellidoPaterno.text.isEmpty()){
            binding.etApellidoPaterno.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esCadenaValida(binding.etApellidoPaterno.text.trim().toString())){
            binding.etApellidoPaterno.error = "Apellido no valido"
            isValido = true
        }
        if(binding.etApellidoMaterno.text.isEmpty()){
            binding.etApellidoMaterno.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esCadenaValida(binding.etApellidoMaterno.text.trim().toString())){
            binding.etApellidoMaterno.error = "Apellido no valido"
            isValido = true
        }
        if(binding.etFechaNacimiento.text.isEmpty()){
            binding.etFechaNacimiento.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esFechaNacimientoValida(binding.etFechaNacimiento.text.trim().toString())){
            binding.etFechaNacimiento.error = "Fecha no valida, Ejem. 2001-12-12"
            isValido = true
        }
        if(binding.etTelefono.text.isEmpty()){
            binding.etTelefono.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esNumeroTelefonoValido(binding.etTelefono.text.trim().toString()) || binding.etTelefono.text.trim().toString().length != 10){
            binding.etTelefono.error = "Telefono no valido"
            isValido = true
        }
        if(binding.etCalle.text.isEmpty()){
            binding.etCalle.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esCalleValida(binding.etCalle.text.trim().toString())){
            binding.etCalle.error = "Calle no valida"
            isValido=true
        }
        if(binding.etNumero.text.isEmpty()){
            binding.etNumero.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esNumero(binding.etNumero.text.trim().toString())){
            binding.etNumero.error = "Numero no valido"
            isValido = true
        }

        return isValido
    }

    fun validarCamposPassword():Boolean{
        var isValido = false

        if(binding.etpNuevoPassword.text.isNotEmpty() || binding.etpVerificarPassword.text.isNotEmpty()){
            if (binding.etpNuevoPassword.text.trim().toString() == binding.etpVerificarPassword.text.trim().toString()){
                if(binding.etpVerificarPassword.text.length > 7){
                    if(Validador.esContrasenaValida(binding.etpVerificarPassword.text.trim().toString())){
                        datosClienteActualizados.cliente!!.password = binding.etpVerificarPassword.text.trim().toString()
                    }else{
                        binding.etpNuevoPassword.error = "Contraseña no valida, caracteres no permitidos"
                        binding.etpVerificarPassword.error = "Contraseña no valida, caracteres no permitidos"
                        isValido = true
                    }
                }else{
                    binding.etpNuevoPassword.error = "La contraseña debe ser de 8 caracteres"
                    binding.etpVerificarPassword.error = "La contraseña debe ser de 8 caracteres"
                    isValido = true
                }
            }else{
                binding.etpNuevoPassword.error = "La contraseñas no son iguales"
                binding.etpVerificarPassword.error = "La contraseñas no son iguales"
                isValido = true
            }
        }

        return isValido
    }
    fun verificarCambios(){
        datosClienteActualizados.cliente!!.idCliente = datosCliente.cliente!!.idCliente
        datosClienteActualizados.direccion!!.idDireccion = datosCliente.direccion!!.idDireccion

        if(datosCliente.cliente!!.nombre != binding.etNombre.text.trim().toString()){
            datosClienteActualizados.cliente!!.nombre = binding.etNombre.text.trim().toString()
        }

        if (datosCliente.cliente!!.apellidoPaterno != binding.etApellidoPaterno.text.trim().toString()){
            datosClienteActualizados.cliente!!.apellidoPaterno = binding.etApellidoPaterno.text.trim().toString()
        }
        if(datosCliente.cliente!!.apellidoMaterno != binding.etApellidoMaterno.text.trim().toString()){
            datosClienteActualizados.cliente!!.apellidoMaterno = binding.etApellidoMaterno.text.trim().toString()
        }
        if(datosCliente.cliente!!.telefono != binding.etTelefono.text.trim().toString()){
            datosClienteActualizados.cliente!!.telefono = binding.etTelefono.text.trim().toString()
        }
        if(datosCliente.cliente!!.fechaNacimiento != binding.etFechaNacimiento.text.trim().toString()){
            datosClienteActualizados.cliente!!.fechaNacimiento = binding.etFechaNacimiento.text.trim().toString()
        }
        if(datosCliente.direccion!!.calle != binding.etCalle.text.trim().toString()){
            datosClienteActualizados.direccion!!.calle = binding.etCalle.text.trim().toString()
        }
        if(datosCliente.direccion!!.numero != binding.etNumero.text.trim().toString().toInt()){
            datosClienteActualizados.direccion!!.numero = binding.etNumero.text.trim().toString().toInt()
        }

        if(binding.etpNuevoPassword.text.isNotEmpty() || binding.etpVerificarPassword.text.isNotEmpty()){
            if (binding.etpNuevoPassword.text.trim().toString() == binding.etpVerificarPassword.text.trim().toString()){
                if(binding.etpVerificarPassword.text.length > 7 && Validador.esContrasenaValida(binding.etpVerificarPassword.text.trim().toString())){
                    datosClienteActualizados.cliente!!.password = binding.etpVerificarPassword.text.trim().toString()
                }else{
                    binding.etpNuevoPassword.error = "La contraseña debe ser de 8 caracteres /o tipo de dato no valido"
                    binding.etpVerificarPassword.error = "La contraseña debe ser de 8 caracteres /o tipo de dato no valido"
                }
            }
        }

        peticionEditarCliente(datosClienteActualizados)
    }


}