package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityCategoriaBinding
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.Promocion
import mx.uv.smartcupon.modelo.util.Constantes
import java.util.Locale

class CategoriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriaBinding
    private lateinit var cliente:Cliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var cadenaJson = intent.getStringExtra("cliente")
        if(cadenaJson!= null){
            serializarCliente(cadenaJson)
        }

        val botonNavegacionVista = binding.botonNavegacionVista
        botonNavegacionVista.selectedItemId = R.id.itm_boton_categoria
        botonNavegacionVista.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itm_boton_inicio -> {
                    val intent = Intent(this@CategoriaActivity, HomeActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_categoria -> {
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_promociones -> {

                    val intent = Intent(this@CategoriaActivity, PromocionActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_configuracion -> {
                    val intent = Intent(this@CategoriaActivity, PerfilActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }

        binding.btnCine.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnCine.text.trim().toString())
            intent.putExtra("categoria", 1)
            startActivity(intent)
        }

        binding.btnAerolinea.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnAerolinea.text.trim().toString())
            intent.putExtra("categoria", 7)
            startActivity(intent)
        }

        binding.btnCalzado.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnCalzado.text.trim().toString())
            intent.putExtra("categoria", 6)
            startActivity(intent)
        }

        binding.btnRopa.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnRopa.text.trim().toString())
            intent.putExtra("categoria", 3)
            startActivity(intent)
        }

        binding.btnComida.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnComida.text.trim().toString())
            intent.putExtra("categoria", 2)
            startActivity(intent)
        }

        binding.btnVideojuegos.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnVideojuegos.text.trim().toString())
            intent.putExtra("categoria", 5)
            startActivity(intent)
        }
        binding.btnLimpieza.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("categoria", 4)
            intent.putExtra("nombre", binding.btnLimpieza.text.trim().toString())
            startActivity(intent)
        }

        binding.btnTecnologia.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            intent.putExtra("nombre", binding.btnTecnologia.text.trim().toString())
            intent.putExtra("categoria", 8)
            startActivity(intent)
        }
    }
    private fun serializarCliente(cadenaJson: String) {
        val gson = Gson()
        cliente = gson.fromJson(cadenaJson, Cliente::class.java)
    }

}