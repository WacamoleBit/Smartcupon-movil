package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import mx.uv.smartcupon.databinding.ActivityLoginBinding
import mx.uv.smartcupon.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIniciarSesion.setOnClickListener {
            val intent = Intent(this@LoginActivity,HomeActivity::class.java)
            startActivity(intent)
        }

    }

    fun btnIniciarSesion(view: View): Unit{

        var etCorreo = findViewById<EditText>(R.id.edtCorreo)
        var etPassword = findViewById<EditText>(R.id.etpPassword)

        var mostrarMensaje = Toast.makeText(this, "Hola "+ etCorreo.text, Toast.LENGTH_LONG).show()

    }
}