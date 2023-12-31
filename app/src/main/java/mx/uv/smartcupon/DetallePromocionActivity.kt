package mx.uv.smartcupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import mx.uv.smartcupon.databinding.ActivityDetallePromocionBinding
import mx.uv.smartcupon.modelo.poko.Promocion

class DetallePromocionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePromocionBinding
    private lateinit var promocion: Promocion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePromocionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val promocionJson = intent.getStringExtra("detallePromocion")
        if(promocionJson!!.isNotEmpty()){
            serializarPromocion(promocionJson)
        }

        binding.imgbtRegresar.setOnClickListener {
            finish()
        }
    }

    fun serializarPromocion(json:String){
        val gson = Gson()
        promocion = gson.fromJson(json, Promocion::class.java)
        cargarInformacion(promocion)
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