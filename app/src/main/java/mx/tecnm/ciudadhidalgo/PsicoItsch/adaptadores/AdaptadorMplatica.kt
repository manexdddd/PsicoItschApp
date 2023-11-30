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
import mx.tecnm.ciudadhidalgo.PsicoItsch.tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.usuario




class AdaptadorMplatica(val listaPlaticas:ArrayList<Platica>, private val listener: OnRefreshListener):
    RecyclerView.Adapter<AdaptadorMplatica.MplaticaViewHolder>(){

    var onPlaticaClick:((Platica)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorMplatica.MplaticaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_mplatica,parent,false)
        return MplaticaViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorMplatica.MplaticaViewHolder, position: Int) {
        val platica = listaPlaticas[position]
        Glide.with(holder.itemView.context).load(platica.foto).into(holder.foto)

        holder.tema.text = platica.tema
        holder.fecha.text = "Fecha \uD83D\uDCC5 "+platica.fecha
        holder.hora.text = "Hora \uD83D\uDD5C "+platica.hora
        holder.itemView.setOnClickListener{
            onPlaticaClick?.invoke(platica)
        }

        holder.itemView.setOnClickListener{
            onPlaticaClick?.invoke(platica)
        }



        holder.desus.setOnClickListener{

            val basedeDatos = Firebase.firestore
            val db = basedeDatos.collection("platicas")
            val misplaticas = db.whereArrayContains("asistentes", usuario.email.toString())
            val emailToRemove = usuario.email
            db.document(platica.id.toString()).update("asistentes", FieldValue.arrayRemove(emailToRemove))
                .addOnCompleteListener{
                    listener.refresh()
                }
        }


    }

    override fun getItemCount(): Int {
        return listaPlaticas.size
    }


    class MplaticaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foto:ImageView = itemView.findViewById(R.id.fotoplatica)
        val tema:TextView = itemView.findViewById(R.id.temapla)
        val hora:TextView = itemView.findViewById(R.id.horapla)
        val fecha:TextView = itemView.findViewById(R.id.fechapla)
        val desus:TextView = itemView.findViewById(R.id.cielo)
    }



}

