package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityPromocionBinding
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.Promocion
import mx.uv.smartcupon.modelo.util.Constantes
import mx.uv.smartcupon.modelo.util.Validador
import java.util.Locale

class PromocionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPromocionBinding
    private lateinit var cliente: Cliente
    private var promociones: ArrayList<Promocion> = ArrayList()
    private var listaBusqueda: ArrayList<Promocion> = ArrayList()
    private lateinit var adaptador: PromocionesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promocion)
        binding = ActivityPromocionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var cadenaJson = intent.getStringExtra("cliente")

        if (cadenaJson != null) {
            serializarCliente(cadenaJson)
            peticionObtenerPromociones()
        }

        binding.svBuscarPromocion.clearFocus()
        binding.svBuscarPromocion.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtro(newText)
                return true
            }

        })

        val botonNavegacionVista = binding.botonNavegacionVista
        botonNavegacionVista.selectedItemId = R.id.itm_boton_promociones
        botonNavegacionVista.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itm_boton_inicio -> {
                    val intent = Intent(this@PromocionActivity, HomeActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_categoria -> {
                    val intent = Intent(this@PromocionActivity, CategoriaActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_promociones -> {
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_configuracion -> {
                    val intent = Intent(this@PromocionActivity, PerfilActivity::class.java)
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

    }

    private fun filtro(newText: String?) {
        var listaBusquedaa: ArrayList<Promocion> = ArrayList()
        for (promocion in promociones){
            if(promocion.empresaNombre!!.toLowerCase().contains(newText!!.toLowerCase()) ||
                promocion.fechaTermino!!.toLowerCase().contains(newText!!.toLowerCase())){
                listaBusquedaa.add(promocion)
                binding.tvDefault.visibility = View.GONE
            }else{
                binding.tvDefault.visibility = View.VISIBLE
            }
        }

        adaptador.filtroLista(listaBusquedaa)
    }

    fun serializarCliente(cadenaJson: String) {
        val gson = Gson()
        cliente = gson.fromJson(cadenaJson, Cliente::class.java)
    }

    fun peticionObtenerPromociones(){
        Ion.with(this@PromocionActivity)
            .load("GET", "${Constantes.URL_WS}promociones/obtenerPromociones")
            .setHeader("Content-Type","application/json")
            .asString()
            .setCallback { e, result ->
                if (e == null && result!= null){
                    serializarInformacionPromocion(result)
                }else{
                    Toast.makeText(this@PromocionActivity, "Error en la petición", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun serializarInformacionPromocion(json : String){
        val gson = Gson()
        val typePromociones = object  : TypeToken<ArrayList<Promocion>>() {}.type
        promociones = gson.fromJson(json, typePromociones)
        listaBusqueda.addAll(promociones)
        mostrarInformacionPromociones(listaBusqueda)
    }

    fun mostrarInformacionPromociones(listaBusqueda: ArrayList<Promocion>){
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this@PromocionActivity)
        binding.recyclerPromociones.setHasFixedSize(true)
        if(promociones.size > 0){
            binding.tvDefault.visibility = View.GONE
            adaptador = PromocionesAdapter(listaBusqueda)
            binding.recyclerPromociones.adapter = adaptador

            adaptador.onitemClick = {
                val intent = Intent(this@PromocionActivity, DetallePromocionActivity::class.java)
                val gson = Gson()
                val cadenaJson = gson.toJson(it)
                intent.putExtra("detallePromocion", cadenaJson)
                startActivity(intent)
            }
        }
    }

}

