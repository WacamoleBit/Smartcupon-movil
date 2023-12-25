package mx.uv.smartcupon.modelo

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.LoginActivity
import mx.uv.smartcupon.MainActivity
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.RespuestaLogin
import mx.uv.smartcupon.modelo.util.Constantes

object AutenticacionDAO {
    fun autenticacionLogin(context:Context, cliente: Cliente){
        Ion.getDefault(context).conscryptMiddleware.enable(false)

        val gson = Gson()
        val json = gson.toJson(cliente)

        Ion.with(context)
            .load("POST", "${Constantes.URL_WS}autenticacion/inicioSesionCliente")
            .setHeader("Content-Type","application/json")
            .setStringBody(json)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null){
                    serealizarRespuesta(result, context)
                }else{
                    Toast.makeText(context, "Error en la petición", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun serealizarRespuesta(json: String, context: Context) {
        val gson = Gson()
        var respuesta: RespuestaLogin = gson.fromJson(json, RespuestaLogin::class.java)
        Toast.makeText(context, respuesta.mensaje, Toast.LENGTH_SHORT).show()

        if (!respuesta.error){
            val login = LoginActivity()
            login.irPantalla(respuesta.cliente)
        }
    }

}