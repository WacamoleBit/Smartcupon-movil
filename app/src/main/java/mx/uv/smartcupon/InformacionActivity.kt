package mx.uv.smartcupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import mx.uv.smartcupon.databinding.ActivityFormularioClienteBinding
import mx.uv.smartcupon.databinding.ActivityInformacionBinding
import mx.uv.smartcupon.modelo.poko.DatosCliente
import mx.uv.smartcupon.modelo.poko.Mensaje
import mx.uv.smartcupon.modelo.util.Constantes

class InformacionActivity : AppCompatActivity() {


    private lateinit var bindig: ActivityInformacionBinding
    private lateinit var datosCliente: DatosCliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityInformacionBinding.inflate(layoutInflater)
        val view = bindig.root
        setContentView(view)

        bindig.imgbtRegresar.setOnClickListener {
            finish()
        }

        var datos = intent.getStringExtra("datos")

        if (!datos!!.isEmpty()){
            serializarDatos(datos)
            cargarDatos(datosCliente)
        }
    }

    fun cargarDatos(datosCliente: DatosCliente){
        bindig.tvNombreCompleto.text = "${datosCliente.cliente!!.nombre} ${datosCliente.cliente!!.apellidoPaterno} ${datosCliente.cliente!!.apellidoMaterno}"
        bindig.tvFechaNacimiento.text = "${datosCliente.cliente!!.fechaNacimiento}"
        bindig.tvEmail.text = "${datosCliente.cliente!!.email}"
        bindig.tvTelefono.text = "${datosCliente.cliente!!.telefono}"
        bindig.tvCalle.text = "${datosCliente.direccion!!.calle}"
        bindig.tvNumero.text = "${datosCliente.direccion!!.numero}"
    }

    fun serializarDatos(json: String){
        val gson = Gson()
        datosCliente = gson.fromJson(json, DatosCliente::class.java)
    }

}