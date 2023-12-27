package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var cliente = Cliente()
        var direccion = Direccion()
        var datosCliente = DatosCliente()

        cliente.nombre = binding.edtNombre.text.toString()
        cliente.apellidoPaterno = binding.edtApellidoP.text.toString()
        cliente.apellidoMaterno = binding.edtApellidoM.text.toString()
        cliente.telefono = binding.edtNumTelefono.text.toString()
        cliente.email = binding.edtEmail.text.toString()
        cliente.fechaNacimiento = binding.edtFechaNacimiento.text.toString()
        cliente.password = binding.edtPassword.text.toString()
        direccion.calle = binding.edtCalle.text.toString()
        direccion.numero = binding.edtNumero.text.toString().toInt()



        binding.btnRegistrar.setOnClickListener {
            if(!validarCamposRegistro(cliente.nombre!!, cliente.apellidoPaterno!!, cliente.apellidoMaterno!!, cliente.telefono!!, cliente.fechaNacimiento!!, cliente.email!!,
                cliente.password!!,direccion.calle!!, direccion.numero!!)){
                 datosCliente.cliente = cliente
                 datosCliente.direccion = direccion

                Toast.makeText(this@RegistroClienteActivity, direccion.numero.toString(), Toast.LENGTH_LONG).show()
            }

            val intent = Intent(this@RegistroClienteActivity,HomeActivity::class.java)
            startActivity(intent)
        }
    }

    fun validarCamposRegistro(nombre:String, apellidoPaterno:String, apellidoMaterno:String,
                              telefono:String, fechaNacimiento:String, email:String, password:String,
                              calle:String, numero:Int): Boolean{
        if (nombre.isEmpty()){
            binding.edtNombre.error = "Campo obligatorio"
            return true
        }
        if(apellidoPaterno.isEmpty()){
            binding.edtApellidoP.error = "Campo obligatorio"
            return true
        }
        if(apellidoMaterno.isEmpty()){
            binding.edtApellidoM.error = "Campo obligatorio"
            return true
        }
        if(telefono.isEmpty()){
            binding.edtNumTelefono.error = "Campo obligatorio"
            return true
        }
        if(fechaNacimiento.isEmpty()){
            binding.edtFechaNacimiento.error = "Campo obligatorio"
            return true
        }
        if(email.isEmpty()){
            binding.edtEmail.error = "Campo obligatorio"
            return true
        }
        if(password.isEmpty()){
            binding.edtPassword.error = "Campo obligatorio"
            return true
        }
        if(calle.isEmpty()){
            binding.edtNumero.error = "Campo obligatorio"
            return true
        }
        if(numero > 0){
            binding.edtNombre.error = "Campo obligatorio"
            return true
        }

        return false
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
            Toast.makeText(this@RegistroClienteActivity, respuesta.mensjae, Toast.LENGTH_SHORT).show()
            
        }else{
            Toast.makeText(this@RegistroClienteActivity, respuesta.mensjae, Toast.LENGTH_SHORT).show()
        }
    }

    fun irPantallaHome(cliente: Cliente){

    }

}