package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityMainBinding
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.RespuestaLogin
import mx.uv.smartcupon.modelo.util.Constantes
import mx.uv.smartcupon.modelo.util.Validador

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var cliente:Cliente = Cliente()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIniciarSesion.setOnClickListener {

            var email = binding.etEmail.text.toString()
            var password = binding.etpPassword.text.toString()

            if(!validarCamposLogin(email, password)){
                cliente.email = email.toString()
                cliente.password = password.toString()

                realizarPeticionLogin(cliente)
            }

        }
    }

    fun validarCamposLogin(email: String, password: String) : Boolean {
        if (email.isEmpty()){
            binding.etEmail.error = "Correo electrónico obligatorio"
            return true
        }else if(!Validador.esCorreoElectronicoValido(binding.etEmail.text.trim().toString())){
            binding.etEmail.error = "Correo no valido"
            return true
        }
        if(password.isEmpty()){
            binding.etpPassword.error = "Contraseña obligatoria"
            return true
        }

        return false
    }


    fun btnRegistrar(view: View): Unit{
        val irPantallaRegistrar = Intent(this@MainActivity, RegistroClienteActivity::class.java)
        startActivity(irPantallaRegistrar)
        finish()
    }

    fun realizarPeticionLogin(cliente: Cliente) {
        Ion.getDefault(this@MainActivity).conscryptMiddleware.enable(false)

        Ion.with(this@MainActivity)
            .load("POST", "${Constantes.URL_WS}autenticacion/inicioSesionCliente")
            .setHeader("Content-Type","application/json")
            .setJsonPojoBody(cliente)
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializarRespuestaLogin(result.toString())
                }else{
                    Toast.makeText(this@MainActivity, "Error en la peticion", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun serializarRespuestaLogin(json: String ){
        val gson = Gson()
        var respuesta: RespuestaLogin = gson.fromJson(json,RespuestaLogin::class.java)
        Toast.makeText(this@MainActivity, respuesta.mensaje, Toast.LENGTH_LONG).show()

        if(!respuesta.error){
            irPantallaHome(respuesta.cliente)
        }
    }

    fun irPantallaHome(sesionCliente: Cliente){

        val gson = Gson()
        var cadenaJson:String = gson.toJson(sesionCliente)

        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.putExtra("cliente", cadenaJson)

        startActivity(intent)
        finish()
    }

}