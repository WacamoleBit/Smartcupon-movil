package mx.uv.smartcupon

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityDetallePromocionBinding
import mx.uv.smartcupon.modelo.poko.Promocion
import mx.uv.smartcupon.modelo.util.Constantes

class DetallePromocionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePromocionBinding
    private lateinit var promocion: Promocion
    private lateinit var logo: Promocion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePromocionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val detallePromocion = intent.getStringExtra("detallePromocion")
        if(detallePromocion!!.isNotEmpty()){
            serializarPromocion(detallePromocion)
            peticionObtenerLogoPromocion(promocion.idPromocion!!)
        }


        binding.imgbtRegresar.setOnClickListener {
            finish()
        }
    }

    fun peticionObtenerLogoPromocion(idPromocion:Int){
        Ion.with(this@DetallePromocionActivity)
            .load("GET", "${Constantes.URL_WS}promociones/obtenerLogoPorId/$idPromocion")
            .setHeader("Content-Type","application/json")
            .asString()
            .setCallback { e, result ->
                if(e == null && result!= null){
                    mostrarLogo(result)
                }else{
                    Toast.makeText(this@DetallePromocionActivity, "Error en la petición", Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun serializarPromocion(json:String){
        val gson = Gson()
        promocion = gson.fromJson(json, Promocion::class.java)
        cargarInformacion(promocion)
    }

    fun mostrarLogo(json: String){
        val gson = Gson()
        logo = gson.fromJson(json, Promocion::class.java)
        if(logo.imagenBase64!!.isNotEmpty()){
            val imgByte = Base64.decode(logo.imagenBase64, Base64.DEFAULT)
            val bitMapFoto = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.size)
            binding.imgvPromocion.setImageBitmap(bitMapFoto)
        }
    }

    fun cargarInformacion(promocion: Promocion){
        binding.tvNombrePromocion.text = promocion.nombre
        binding.tvTipoPromocion.text = promocion.tipoPromocionNombre
        binding.tvDescripcion.text = promocion.descripcion
        binding.tvFechaInicioFinal.text = "Del ${promocion.fechaInicio} al ${promocion.fechaTermino}"
        binding.tvRestricciones.text = promocion.restricciones
        binding.tvCuponesDisponibles.text = promocion.cuponesDisponibles.toString()
        binding.tvCodigo.text = promocion.codigoPromocion
    }


}