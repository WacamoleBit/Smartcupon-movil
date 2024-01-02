package mx.uv.smartcupon

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.uv.smartcupon.modelo.poko.Promocion

class PromocionesAdapter(var promocionesArrayList: ArrayList<Promocion>): RecyclerView.Adapter<PromocionesAdapter.ViewHolderPromociones>(){

    var onitemClick: ((Promocion) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPromociones {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolderPromociones(itemView)
    }

    override fun getItemCount(): Int {
        return promocionesArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolderPromociones, position: Int) {
        val promocion = promocionesArrayList[position]

        holder.tvNombrePromocion.text = "Nombre: ${promocion!!.nombre.toString()}"
        holder.tvNombreEmpresa.text = "Empresa: ${promocion!!.empresaNombre.toString()}"
        holder.tvTipoDescuento.text = "Tipo: ${promocion!!.tipoPromocionNombre.toString()}"
        holder.tvValor.text = "Valor: ${promocion!!.porcentajeDescuento.toString()}"
        holder.tvCupones.text = "Cupones: ${promocion!!.cuponesDisponibles.toString()}"
        holder.tvVigencia.text = "Vigencia: ${promocion!!.fechaTermino.toString()}"

        holder.itemView.setOnClickListener {
            onitemClick?.invoke(promocion)
        }
    }

    fun filtroLista(listaBusquedaa: ArrayList<Promocion>) {
        promocionesArrayList = listaBusquedaa
        notifyDataSetChanged()
    }

    class ViewHolderPromociones(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombrePromocion: TextView = itemView.findViewById(R.id.tv_nombre)
        val tvNombreEmpresa: TextView = itemView.findViewById(R.id.tv_empresa)
        val tvTipoDescuento: TextView = itemView.findViewById(R.id.tv_tipo_descuento)
        val tvValor: TextView = itemView.findViewById(R.id.tv_valor)
        val tvCupones : TextView = itemView.findViewById(R.id.tv_cupones)
        val tvVigencia : TextView = itemView.findViewById(R.id.tv_vigencia)
    }

}