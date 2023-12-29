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
    private lateinit var cliente: Cliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegistrar.setOnClickListener {
            if(!validarCamposRegistro()){
                registraCliente()
            }

        }
    }

    fun registraCliente(){

        cliente = Cliente()
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

        datosCliente.cliente = cliente
        datosCliente.direccion = direccion

        realizarPeticionRegistro(datosCliente)

    }
    fun validarCamposRegistro(): Boolean{

        if (binding.edtNombre.text.isEmpty()){
            binding.edtNombre.error = "Campo obligatorio"
            return true
        }
        if(binding.edtApellidoP.text.isEmpty()){
            binding.edtApellidoP.error = "Campo obligatorio"
            return true
        }
        if(binding.edtApellidoM.text.isEmpty()){
            binding.edtApellidoM.error = "Campo obligatorio"
            return true
        }
        if(binding.edtNumTelefono.text.isEmpty()){
            binding.edtNumTelefono.error = "Campo obligatorio"
            return true
        }
        if(binding.edtFechaNacimiento.text.isEmpty()){
            binding.edtFechaNacimiento.error = "Campo obligatorio"
            return true
        }
        if(binding.edtEmail.text.isEmpty()){
            binding.edtEmail.error = "Campo obligatorio"
            return true
        }
        if(binding.edtPassword.text.isEmpty()){
            binding.edtPassword.error = "Campo obligatorio"
            return true
        }
        if(binding.edtCalle.text.isEmpty()){
            binding.edtCalle.error = "Campo obligatorio"
            return true
        }

        if(binding.edtNumero.text.isEmpty()){
            binding.edtNumero.error = "Campo obligatorio"
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
            Toast.makeText(this@RegistroClienteActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
            irPantallaHome(cliente)
        }else{
            Toast.makeText(this@RegistroClienteActivity, respuesta.mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    fun irPantallaHome(cliente: Cliente){
        val intent = Intent(this@RegistroClienteActivity,HomeActivity::class.java)
        val gson = Gson()
        var cadenaJson = gson.toJson(cliente)
        intent.putExtra("cliente", cadenaJson)
        startActivity(intent)
    }

}