package mx.uv.smartcupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    fun btnIniciarSesion(view: View): Unit{

        var etCorreo = findViewById<EditText>(R.id.edtCorreo)
        var etPassword = findViewById<EditText>(R.id.etpPassword)

        var mostrarMensaje = Toast.makeText(this, "Hola "+ etCorreo.text, Toast.LENGTH_LONG).show()

    }

    fun btnRegistrar(view: View): Unit{
        var mostrarMensaje = Toast.makeText(this, "Vamos a registrarte", Toast.LENGTH_LONG).show()
    }


}