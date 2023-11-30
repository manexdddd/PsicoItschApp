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
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Academica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Aviso
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Canal


class AdaptadorCanal (val listaCanales:ArrayList<Canal>):
    RecyclerView.Adapter<AdaptadorCanal.CanalViewHolder>(){

    var onCanalClick:((Canal)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorCanal.CanalViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_canal,parent,false)
        return CanalViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorCanal.CanalViewHolder, position: Int) {
        val canal= listaCanales[position]

        holder.area.text = canal.area
        holder.solicitud.text = canal.fecha
        holder.atencion.text = canal.fechas
        holder.obser.text = canal.observaciones
        holder.tutor.text = canal.profesor
    }

    override fun getItemCount(): Int {
        return listaCanales.size
    }


    class CanalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val area:TextView = itemView.findViewById(R.id.area_canalización)
        val solicitud:TextView = itemView.findViewById(R.id.fecha_solicitud)
        val tutor:TextView = itemView.findViewById(R.id.nombrefirma_tutor1)
        val obser:TextView = itemView.findViewById(R.id.obs1)
        val atencion:TextView = itemView.findViewById(R.id.fecha_atencion1)
    }


}