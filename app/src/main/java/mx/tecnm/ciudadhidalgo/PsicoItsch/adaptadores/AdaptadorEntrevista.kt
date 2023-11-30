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
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Entrevista


class AdaptadorEntrevista (val listaentrevistas:ArrayList<Entrevista>):
    RecyclerView.Adapter<AdaptadorEntrevista.EntrevistaViewHolder>(){

    var onEntrevistaClick:((Entrevista)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorEntrevista.EntrevistaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_entrevista,parent,false)
        return EntrevistaViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorEntrevista.EntrevistaViewHolder, position: Int) {
        val entrevista = listaentrevistas[position]

        holder.tema.text = entrevista.tema
        holder.alumno.text = entrevista.alumno
        holder.tutor.text = entrevista.tutor
        holder.fecha.text = entrevista.fecha

    }

    override fun getItemCount(): Int {
        return listaentrevistas.size
    }


    class EntrevistaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tema:TextView = itemView.findViewById(R.id.tema_entrevista)
        val alumno:TextView = itemView.findViewById(R.id.nombrefirma_alumno)
        val fecha:TextView = itemView.findViewById(R.id.fechaentrevista)
        val tutor:TextView = itemView.findViewById(R.id.nombrefirma_tutor)
    }


}