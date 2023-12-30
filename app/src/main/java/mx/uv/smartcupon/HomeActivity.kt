package mx.uv.smartcupon

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityHomeBinding
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.DatosCliente
import mx.uv.smartcupon.modelo.util.Constantes

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var cliente: Cliente
    private lateinit var datosCliente: DatosCliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var cadenaJson = intent.getStringExtra("cliente")
        if(cadenaJson != null){
            serializarCliente(cadenaJson)
            peticionCargarInformacion()

        }

        val botonNavegacionVista = binding.botonNavegacionVista
        botonNavegacionVista.selectedItemId = R.id.itm_boton_inicio
        botonNavegacionVista.setOnItemSelectedListener{
            when(it.itemId){
                R.id.itm_boton_inicio->{
                    return@setOnItemSelectedListener true
                }
                R.id.itm_boton_categoria->{
                    val intent = Intent(this@HomeActivity, CategoriaActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.itm_boton_promociones->{
                    val intent = Intent(this@HomeActivity, PromocionActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.itm_boton_configuracion->{
                    val intent = Intent(this@HomeActivity, PerfilActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                else->{
                    return@setOnItemSelectedListener false
                }
            }

        }

    }

    fun serializarCliente(cadenaJson: String) {
        val gson = Gson()
        cliente = gson.fromJson(cadenaJson, Cliente::class.java)
    }



    fun cargarDatosCliente(){
        binding.tvCliente.setText("'${datosCliente.cliente!!.nombre} ${datosCliente.cliente!!.apellidoPaterno} ${datosCliente.cliente!!.apellidoMaterno}'")
    }

    fun peticionCargarInformacion(){
        Ion.with(this@HomeActivity)
            .load("GET", "${Constantes.URL_WS}clientes/obtenerDatos/${cliente.idCliente}")
            .setHeader("Content-Type","application/json")
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializarRespuesta(result)
                }else{
                    Toast.makeText(this@HomeActivity, "Error en la peticion", Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun serializarRespuesta(json: String) {
        val gson = Gson()
        datosCliente = gson.fromJson(json, DatosCliente::class.java)
        cargarDatosCliente()
    }
}