package mx.uv.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityPerfilBinding
import mx.uv.smartcupon.modelo.poko.Cliente
import mx.uv.smartcupon.modelo.poko.DatosCliente
import mx.uv.smartcupon.modelo.poko.Direccion
import mx.uv.smartcupon.modelo.util.Constantes

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPerfilBinding
    private lateinit var cliente: Cliente
    private lateinit var datosCliente: DatosCliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var cadenaJson = intent.getStringExtra("cliente")

        if(cadenaJson != null){
            serializarCliente(cadenaJson)
            peticionCargarInformacion()
        }

        binding.btnInformacionPersonal.setOnClickListener {
            peticionCargarInformacion()
            irPantallaInformacionGeneral(datosCliente)
            }

        binding.btnEditarInformacion.setOnClickListener {
            peticionCargarInformacion()
            irPantallaFormularioCliente(datosCliente)
        }

        binding.btnSalir.setOnClickListener {
            finish()
        }

        val botonNavegacionVista = binding.botonNavegacionVista
        botonNavegacionVista.selectedItemId = R.id.itm_boton_configuracion
        botonNavegacionVista.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itm_boton_inicio -> {
                    var intent = Intent(this@PerfilActivity, HomeActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_categoria -> {
                    val intent = Intent(this@PerfilActivity, CategoriaActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_promociones -> {
                    val intent = Intent(this@PerfilActivity, PromocionActivity::class.java)
                    val gson = Gson()
                    var json = gson.toJson(cliente)
                    intent.putExtra("cliente", json)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.itm_boton_configuracion -> {
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }


    fun peticionCargarInformacion(){
        Ion.with(this@PerfilActivity)
            .load("GET", "${Constantes.URL_WS}clientes/obtenerDatos/${cliente.idCliente}")
            .setHeader("Content-Type","application/json")
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializarRespuesta(result)
                }else{
                    Toast.makeText(this@PerfilActivity, "Error en la peticion", Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun serializarRespuesta(json: String) {
        val gson = Gson()
        datosCliente = gson.fromJson(json, DatosCliente::class.java)
    }

    fun serializarCliente(cadenaJson: String) {
        val gson = Gson()
        cliente = gson.fromJson(cadenaJson, Cliente::class.java)
    }

    fun irPantallaFormularioCliente(datosCliente: DatosCliente) {
        val intent = Intent(this@PerfilActivity, FormularioClienteActivity::class.java)
        val gson = Gson()
        var cadenaJson = gson.toJson(datosCliente)
        intent.putExtra("datos", cadenaJson)
        startActivity(intent)
    }
    fun irPantallaInformacionGeneral(datosCliente: DatosCliente){
        val intent = Intent(this@PerfilActivity, InformacionActivity::class.java)
        val gson = Gson()
        var cadenaJson = gson.toJson(datosCliente)
        intent.putExtra("datos",cadenaJson)
        startActivity(intent)
    }

}