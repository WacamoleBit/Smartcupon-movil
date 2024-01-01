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
    private lateinit var promociones: ArrayList<Promocion>
    private lateinit var listaBusqueda:ArrayList<Promocion>
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
            val categoria = binding.btnCine.text
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        binding.btnAerolinea.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            val categoria = binding.btnAerolinea.text
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        binding.btnCalzado.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            val categoria = binding.btnCalzado.text
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        binding.btnRopa.setOnClickListener {

        }

        binding.btnComida.setOnClickListener {
            val intent = Intent(this@CategoriaActivity, CategoriaPromocionActivity::class.java)
            val categoria = binding.btnComida.text
            Toast.makeText(this@CategoriaActivity, categoria, Toast.LENGTH_SHORT).show()
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        binding.btnVideojuegos.setOnClickListener {

        }
        binding.btnLimpieza.setOnClickListener {

        }

        binding.btnTecnologia.setOnClickListener {

        }
    }

    fun peticionObtenerPromociones(){
        Ion.with(this@CategoriaActivity)
            .load("GET", "${Constantes.URL_WS}promociones/obtenerPromociones")
            .setHeader("Content-Type","application/json")
            .asString()
            .setCallback { e, result ->
                if (e == null && result!= null){
                    serializarInformacionPromocion(result)
                }
            }
    }
    private fun serializarCliente(cadenaJson: String) {
        val gson = Gson()
        cliente = gson.fromJson(cadenaJson, Cliente::class.java)
    }

    fun serializarInformacionPromocion(result: String) {
        val gson = Gson()
        val typePromociones = object  : TypeToken<ArrayList<Promocion>>() {}.type
        promociones = gson.fromJson(result, typePromociones)
    }
}