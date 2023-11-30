package mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores





import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.MPlatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.OnRefreshListener
import mx.tecnm.ciudadhidalgo.PsicoItsch.R
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.usuario




class AdaptadorAlumno(val listaALumnos:ArrayList<Alumno>):
    RecyclerView.Adapter<AdaptadorAlumno.AlumnoViewHolder>(){

    var onPlaticaClick:((Alumno)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorAlumno.AlumnoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_alumno,parent,false)
        return AlumnoViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorAlumno.AlumnoViewHolder, position: Int) {
        val alumno =listaALumnos[position]
        Glide.with(holder.itemView.context).load(alumno.foto).into(holder.foto)

        holder.nombre.text = alumno.nombre
        holder.apellidos.text = alumno.paterno+" "+alumno.materno
        holder.control.text = alumno.control
        holder.itemView.setOnClickListener{
            onPlaticaClick?.invoke(alumno)
        }

        holder.ver.setOnClickListener{
            onPlaticaClick?.invoke(alumno)
        }



        holder.ver.setOnClickListener{
            onPlaticaClick?.invoke(alumno)

        }


    }

    override fun getItemCount(): Int {
        return listaALumnos.size
    }


    class AlumnoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foto:ImageView = itemView.findViewById(R.id.fotoalumno)
        val nombre:TextView = itemView.findViewById(R.id.nombrealumno)
        val apellidos:TextView = itemView.findViewById(R.id.apellidosalumno)
        val control:TextView = itemView.findViewById(R.id.controlalumno)
        val ver:TextView = itemView.findViewById(R.id.hell)
    }



}

