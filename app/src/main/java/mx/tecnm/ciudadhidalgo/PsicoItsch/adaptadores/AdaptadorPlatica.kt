package mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores



import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import mx.tecnm.ciudadhidalgo.PsicoItsch.R
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica


class AdaptadorPlatica (val listaPlaticas:ArrayList<Platica>):
    RecyclerView.Adapter<AdaptadorPlatica.PlaticaViewHolder>(){

    var onPlaticaClick:((Platica)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorPlatica.PlaticaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_platica,parent,false)
        return PlaticaViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorPlatica.PlaticaViewHolder, position: Int) {
        val platica = listaPlaticas[position]
        Glide.with(holder.itemView.context).load(platica.foto).into(holder.foto)

        holder.tema.text = platica.tema
        holder.descripcion.text = platica.descripcion
        holder.fecha.text = "\uD83D\uDCC5 Fecha de la platica: "+platica.fecha
        holder.hora.text = "\uD83D\uDD5C Hora de la Platica: "+platica.hora
        holder.itemView.setOnClickListener{
            onPlaticaClick?.invoke(platica)
        }



        holder.ver.setOnClickListener {
            onPlaticaClick?.invoke(platica)
        }
    }

    override fun getItemCount(): Int {
        return listaPlaticas.size
    }


    class PlaticaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foto:ImageView = itemView.findViewById(R.id.fotoplatica)
        val tema:TextView = itemView.findViewById(R.id.tema)
        val hora:TextView = itemView.findViewById(R.id.hora)
        val fecha:TextView = itemView.findViewById(R.id.fecha)
        val descripcion:TextView = itemView.findViewById(R.id.descripcion)
        val ver:TextView = itemView.findViewById(R.id.cielo)
    }


}