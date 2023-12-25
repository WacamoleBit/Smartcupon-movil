package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityMainBinding
import mx.uv.smartcupon.modelo.AutenticacionDAO
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.RespuestaLogin
import mx.uv.smartcupon.modelo.util.Constantes

class MainActivity : AppCompatActivity() {

    private lateinit var bindig: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityMainBinding.inflate(layoutInflater)
        val view = bindig.root
        setContentView(view)

        var cliente = Cliente()

        bindig.btnIniciarSesion.setOnClickListener {

            var email = bindig.edtCorreo.text.toString()
            var password = bindig.etpPassword.text.toString()

            cliente.email = email.toString()
            cliente.password = password.toString()

            Toast.makeText(this@MainActivity, cliente.email + cliente.password, Toast.LENGTH_LONG).show()

            realizarPeticionLogin(cliente)

        }
    }


    fun btnRegistrar(view: View): Unit{
        val irPantallaRegistrar = Intent(this@MainActivity, RegistroClienteActivity::class.java)
        startActivity(irPantallaRegistrar)
        var mostrarMensaje = Toast.makeText(this, "Vamos a registrarte", Toast.LENGTH_LONG).show()
    }

    fun realizarPeticionLogin(cliente: Cliente) {

        Ion.with(this@MainActivity)
            .load("POST", "${Constantes.URL_WS}autenticacion/inicioSesionCliente")
            .setHeader("Content-Type","application/json")
            .setJsonPojoBody(cliente)
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    Toast.makeText(this@MainActivity, "Respuesta del servidor: $result", Toast.LENGTH_SHORT).show()
                    serializarRespuestaLogin(result.toString())
                }else{
                    Toast.makeText(this@MainActivity, "Error en la peticion", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun serializarRespuestaLogin(json: String ){
        val gson = Gson()
        var respuesta: RespuestaLogin = gson.fromJson(json,RespuestaLogin::class.java)

        if(!respuesta.error){
            irPantallaHome(respuesta.cliente)
        }
    }

    fun irPantallaHome(cliente: Cliente){

        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}