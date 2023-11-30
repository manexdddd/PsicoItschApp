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
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Sesiones
import mx.tecnm.ciudadhidalgo.PsicoItsch.tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.usuario




class AdaptadorSesion(val listasesiones:ArrayList<Sesiones>, private val listener: OnRefreshListener):
    RecyclerView.Adapter<AdaptadorSesion.SesionViewHolder>(){

    var onSesionClick:((Platica)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorSesion.SesionViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_sesion,parent,false)
        return SesionViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorSesion.SesionViewHolder, position: Int) {
        val sesion = listasesiones[position]
        Glide.with(holder.itemView.context).load(sesion.foto).into(holder.foto)

        holder.atendedor.text = "Psicolog@ "+sesion.nombreMedico
        holder.fecha.text = "Fecha \uD83D\uDCC5 "+sesion.fecha
        holder.hora.text = "Hora \uD83D\uDD5C "+sesion.hora
        holder.tipo.text = "tipo \uD83D\uDD5C "+sesion.tipo



    }

    override fun getItemCount(): Int {
        return listasesiones.size
    }


    class SesionViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foto:ImageView = itemView.findViewById(R.id.fotosesion)
        val atendedor:TextView = itemView.findViewById(R.id.psic)
        val hora:TextView = itemView.findViewById(R.id.horase)
        val fecha:TextView = itemView.findViewById(R.id.fechase)
        val tipo:TextView = itemView.findViewById(R.id.tipo)
    }



}

