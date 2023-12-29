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



            if(!validarCamposLogin(email, password)){
                cliente.email = email.toString()
                cliente.password = password.toString()

                realizarPeticionLogin(cliente)
            }

        }
    }

    fun validarCamposLogin(email: String, password: String) : Boolean {
        if (email.isEmpty()){
            bindig.edtCorreo.error = "Correo electrónico obligatorio"
            return true
        }
        if(password.isEmpty()){
            bindig.etpPassword.error = "Contraseña obligatoria"
            return true
        }

        return false
    }


    fun btnRegistrar(view: View): Unit{
        val irPantallaRegistrar = Intent(this@MainActivity, RegistroClienteActivity::class.java)
        startActivity(irPantallaRegistrar)
        var mostrarMensaje = Toast.makeText(this, "Vamos a registrarte", Toast.LENGTH_LONG).show()
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