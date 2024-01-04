package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityMainBinding
import mx.uv.smartcupon.databinding.ActivityRegistroClienteBinding
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.DatosCliente
import mx.uv.smartcupon.modelo.poko.Direccion
import mx.uv.smartcupon.modelo.poko.Mensaje
import mx.uv.smartcupon.modelo.poko.RespuestaLogin
import mx.uv.smartcupon.modelo.util.Constantes
import mx.uv.smartcupon.modelo.util.Validador

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imgbtRegresar.setOnClickListener {
            val intent = Intent(this@RegistroClienteActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegistrar.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Registrar")
            builder.setMessage("¿La información ingresada es la correcta?")
            builder.setPositiveButton("Confirmar") { dialog, which ->
                if(!validarCamposRegistro()){
                    registraCliente()
                }
            }
            builder.setNegativeButton("Cancelar") { dialog, which ->
                limpiarCampos()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }


    fun limpiarCampos(){
        binding.etNombre.setText("")
        binding.etApellidoPaterno.setText("")
        binding.etApellidoMaterno.setText("")
        binding.etFechaNacimiento.setText("")
        binding.etTelefono.setText("")
        binding.etNumero.setText("")
        binding.etCalle.setText("")
        binding.etpPassword.setText("")
    }

    fun registraCliente(){

        var cliente = Cliente()
        var direccion = Direccion()
        var datosCliente = DatosCliente()

        cliente.nombre = binding.etNombre.text.trim().toString()
        cliente.apellidoPaterno = binding.etApellidoPaterno.text.trim().toString()
        cliente.apellidoMaterno = binding.etApellidoMaterno.text.trim().toString()
        cliente.telefono = binding.etTelefono.text.trim().toString()
        cliente.email = binding.etEmail.text.trim().toString()
        cliente.fechaNacimiento = binding.etFechaNacimiento.text.trim().toString()
        cliente.password = binding.etpPassword.text.trim().toString()
        direccion.calle = binding.etCalle.text.trim().toString()
        direccion.numero = binding.etNumero.text.trim().toString().toInt()

        datosCliente.cliente = cliente
        datosCliente.direccion = direccion

        realizarPeticionRegistro(datosCliente)
    }
    fun validarCamposRegistro(): Boolean{
        var isValido = false
        
        if(binding.etNombre.text.isEmpty()){
            binding.etNombre.error = "Campo obligatorio"
            isValido = true
        }else if (!Validador.esNombreValido(binding.etNombre.text.trim().toString())){
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
            binding.etFechaNacimiento.error = "Fecha no valida"
            isValido = true
        }
        if(binding.etTelefono.text.isEmpty()){
            binding.etTelefono.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esNumeroTelefonoValido(binding.etTelefono.text.trim().toString()) || binding.etTelefono.text.trim().toString().length != 10){
            binding.etTelefono.error = "Telefono no valido"
            isValido = true
        }
        if(binding.etEmail.text.isEmpty()){
            binding.etEmail.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esCorreoElectronicoValido(binding.etEmail.text.trim().toString())){
            binding.etEmail.error = "Correo no valido, Ejem. example@smartcupon.com"
            isValido = true
        }
        if(binding.etpPassword.text.isEmpty()){
            binding.etpPassword.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esContrasenaValida(binding.etpPassword.text.trim().toString()) || binding.etpPassword.text.length < 8){
            binding.etpPassword.error = "Contraseña no valida, almenos 8 caracteres /o caracteres no permitidos"
            isValido = true
        }
        if(binding.etCalle.text.isEmpty()){
            binding.etCalle.error = "Campo obligatorio"
            isValido = true
        }else if(!Validador.esCalleValida(binding.etCalle.text.trim().toString())){
            binding.etCalle.error = "Calle no valida"
            isValido = true
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

    fun realizarPeticionRegistro(datosCliente: DatosCliente){

        Ion.with(this@RegistroClienteActivity)
            .load("POST", "${Constantes.URL_WS}clientes/registrarCliente")
            .setHeader("Content-Type","application/json")
            .setJsonPojoBody(datosCliente)
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializarRespuestaLogin(result)
                }else{
                    Toast.makeText(this@RegistroClienteActivity, "Error en la peticion", Toast.LENGTH_SHORT).show()

                }
            }
    }

    fun serializarRespuestaLogin(json: String) {
        val gson = Gson()
        val respuesta = gson.fromJson(json,Mensaje::class.java)
        if (!respuesta.error){
            Toast.makeText(this@RegistroClienteActivity, "Registro éxitoso", Toast.LENGTH_SHORT).show()
            irPantallaLogin()
        }else{
            Toast.makeText(this@RegistroClienteActivity, "Error al registrar la información", Toast.LENGTH_SHORT).show()
        }
    }

    fun irPantallaLogin(){
        val intent = Intent(this@RegistroClienteActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}