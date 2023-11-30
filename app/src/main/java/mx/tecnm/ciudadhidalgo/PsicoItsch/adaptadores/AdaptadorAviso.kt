package mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores



import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.tecnm.ciudadhidalgo.PsicoItsch.R
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Aviso


class AdaptadorAviso (val listaAvisos:ArrayList<Aviso>):
    RecyclerView.Adapter<AdaptadorAviso.AvisoViewHolder>(){

    var onAvisoClick:((Aviso)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorAviso.AvisoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_aviso,parent,false)
        return AvisoViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorAviso.AvisoViewHolder, position: Int) {
        val aviso = listaAvisos[position]
        Glide.with(holder.itemView.context).load(aviso.foto).into(holder.foto)

        holder.titulo.text = aviso.titulo
        holder.descripcion.text = aviso.descripcion
        holder.fecha.text = "\uD83D\uDCC5 "+aviso.fecha
        holder.itemView.setOnClickListener{
            onAvisoClick?.invoke(aviso)
        }

        holder.ver.setOnClickListener {
            onAvisoClick?.invoke(aviso)
        }
    }

    override fun getItemCount(): Int {
        return listaAvisos.size
    }


    class AvisoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foto:ImageView = itemView.findViewById(R.id.fotoaviso)
        val titulo:TextView = itemView.findViewById(R.id.titulo)
        val fecha:TextView = itemView.findViewById(R.id.fecha)
        val descripcion:TextView = itemView.findViewById(R.id.descripcion)
        val ver:TextView = itemView.findViewById(R.id.ver_card)
    }


}