package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import mx.uv.smartcupon.databinding.ActivityLoginBinding
import mx.uv.smartcupon.modelo.AutenticacionDAO
import mx.uv.smartcupon.modelo.poko.Cliente

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.edtCorreo.text.toString()
            val password = binding.etpPassword.text.toString()

            cliente.email = email
            cliente.password = password

            if (!validarCampos(email, password)){
                AutenticacionDAO.autenticacionLogin(this@LoginActivity, cliente)
            }
        }
    }

    fun validarCampos(email: String, password: String): Boolean{

        if(email.isEmpty()){
            binding.edtCorreo.error = "Correo obligatorio"
            return false
        }

        if (password.isEmpty()){
            binding.etpPassword.error = "Contraseña obligatoria"
            return false
        }

        return true
    }


    fun irPantalla(cliente: Cliente){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        val gson = Gson()
        var cadenaJson = gson.toJson(cliente)

        intent.putExtra("cliente", cadenaJson)
        startActivity(intent)
        finish()
    }

}