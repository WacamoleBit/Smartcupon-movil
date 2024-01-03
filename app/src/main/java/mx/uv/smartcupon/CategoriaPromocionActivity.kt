package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityCategoriaPromocionBinding
import mx.uv.smartcupon.modelo.poko.Categoria
import mx.uv.smartcupon.modelo.poko.Promocion
import mx.uv.smartcupon.modelo.util.Constantes
import java.util.Locale

class CategoriaPromocionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriaPromocionBinding
    private var categorias: ArrayList<Promocion> = ArrayList()
    private var promociones: ArrayList<Promocion> = ArrayList()
    private lateinit var adaptador: PromocionesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriaPromocionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var categoria = intent.getStringExtra("nombre")
        binding.tvCategoria.text = categoria!!.trim().toString()

        var idCategoria = intent.getIntExtra("categoria", -1)
        peticionObtenerPromociones(idCategoria)

        binding.imgbtRegresar.setOnClickListener {
            finish()
        }
    }
    fun peticionObtenerPromociones(idCategoria: Int){
        Ion.with(this@CategoriaPromocionActivity)
            .load("GET", "${Constantes.URL_WS}promociones/obtenerPromocionesPorCategoria/${idCategoria}")
            .setHeader("Content-Type","application/json")
            .asString()
            .setCallback { e, result ->
                if (e == null && result!= null){
                    serializarInformacionPromocion(result)
                }else{
                    Toast.makeText(this@CategoriaPromocionActivity, "Error en la petición para obtener cupones", Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun serializarInformacionPromocion(result: String) {
        val gson = Gson()
        val typePromociones = object  : TypeToken<ArrayList<Promocion>>() {}.type
        promociones = gson.fromJson(result, typePromociones)
        mostrarInformacionPromociones(promociones)
    }

    fun mostrarInformacionPromociones(listaBusqueda: ArrayList<Promocion>){
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this@CategoriaPromocionActivity)
        binding.recyclerPromociones.setHasFixedSize(true)
        if(promociones.size > 0){
            binding.tvDefault.visibility = View.GONE
            adaptador = PromocionesAdapter(listaBusqueda)
            binding.recyclerPromociones.adapter = adaptador

            adaptador.onitemClick = {
                val intent = Intent(this@CategoriaPromocionActivity, DetallePromocionActivity::class.java)
                val gson = Gson()
                val cadenaJson = gson.toJson(it)
                intent.putExtra("detallePromocion", cadenaJson)
                startActivity(intent)
            }
        }
    }
}