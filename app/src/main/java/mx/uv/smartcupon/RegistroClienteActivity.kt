package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mx.uv.smartcupon.databinding.ActivityMainBinding
import mx.uv.smartcupon.databinding.ActivityRegistroClienteBinding

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegistrar.setOnClickListener {
            val intent = Intent(this@RegistroClienteActivity,HomeActivity::class.java)
            startActivity(intent)
        }
    }

    fun btnRegistrar(view : View) : Unit{

    }
}