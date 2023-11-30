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


class AdaptadorAcademica (val listaAcademias:ArrayList<Academica>):
    RecyclerView.Adapter<AdaptadorAcademica.AcademicaViewHolder>(){

    var onAcademiaClick:((Academica)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorAcademica.AcademicaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_academica,parent,false)
        return AcademicaViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorAcademica.AcademicaViewHolder, position: Int) {
        val academica = listaAcademias[position]

        holder.tipo.text = academica.tipo
        holder.asignatura.text = academica.asignatura
        holder.estatus.text = academica.estatus
        holder.tutor.text = academica.profesor
        holder.fecha.text = academica.fecha

    }

    override fun getItemCount(): Int {
        return listaAcademias.size
    }


    class AcademicaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tipo:TextView = itemView.findViewById(R.id.asesoria_academica)
        val asignatura:TextView = itemView.findViewById(R.id.nombreAsignatura)
        val fecha:TextView = itemView.findViewById(R.id.fecha_atencion)
        val estatus:TextView = itemView.findViewById(R.id.estatus)
        val tutor:TextView = itemView.findViewById(R.id.nombrefirma_tutor2)
    }


}