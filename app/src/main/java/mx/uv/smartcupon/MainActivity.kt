package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import mx.uv.smartcupon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnFormularioLogin.setOnClickListener {
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnFormularioRegistro.setOnClickListener {
            val intent = Intent(this@MainActivity,RegistroClienteActivity::class.java)
            startActivity(intent)
        }
    }

    fun btnFormularioLogin(view : View) : Unit{

    }

    fun btnFomrularioRegisto(view: View): Unit{
        var mostrarMensaje = Toast.makeText(this, "Vamos a registrarte", Toast.LENGTH_LONG).show()
    }


}