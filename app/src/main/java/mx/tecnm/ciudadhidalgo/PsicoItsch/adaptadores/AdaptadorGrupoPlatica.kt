package mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores






import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.GrupoPlaticas
import mx.tecnm.ciudadhidalgo.PsicoItsch.MPlatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.OnRefreshListener
import mx.tecnm.ciudadhidalgo.PsicoItsch.R
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.usuario




class AdaptadorGrupoPlatica(val listaPlaticas:ArrayList<Platica>, private val listener: OnRefreshListener):
    RecyclerView.Adapter<AdaptadorGrupoPlatica.GrupoPlaticaViewHolder>(){

    var onPlaticaClick:((Platica)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorGrupoPlatica.GrupoPlaticaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_grupoplatica,parent,false)
        return GrupoPlaticaViewHolder(vista)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdaptadorGrupoPlatica.GrupoPlaticaViewHolder ,position: Int) {
        val platica = listaPlaticas[position]
        Glide.with(holder.itemView.context).load(platica.foto).into(holder.foto)

        holder.tema.text = platica.tema
        holder.fecha.text = "Fecha \uD83D\uDCC5 "+platica.fecha
        holder.hora.text ="Hora \uD83D\uDD5C "+ platica.hora
        holder.itemView.setOnClickListener{
            onPlaticaClick?.invoke(platica)
        }

        holder.itemView.setOnClickListener{
            onPlaticaClick?.invoke(platica)
        }



        holder.desus.setOnClickListener{


            val basedeDatos = Firebase.firestore
            val db = basedeDatos.collection("platicas")
            val emailToRemove = tutor.emailt

            val query = basedeDatos.collection("alumnos").whereEqualTo("grupo", tutor.grupot)

            query.get().addOnSuccessListener { querySnapshot ->
                val emailsToRemove = querySnapshot.documents.map { it.id }

                db.document(platica.id.toString()).update("asistentes", FieldValue.arrayRemove(*emailsToRemove.toTypedArray()))
                    .addOnCompleteListener{
                        db.document(platica.id.toString()).update("asistentes", FieldValue.arrayRemove(emailToRemove))
                            .addOnCompleteListener{
                                listener.refresh()
                            }
                    }
            } }
    }

    override fun getItemCount(): Int {
        return listaPlaticas.size
    }


    class GrupoPlaticaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val foto:ImageView = itemView.findViewById(R.id.fotoplatica)
        val tema:TextView = itemView.findViewById(R.id.temapla)
        val hora:TextView = itemView.findViewById(R.id.horapla)
        val fecha:TextView = itemView.findViewById(R.id.fechapla)
        val desus:TextView = itemView.findViewById(R.id.cielo)
    }



}

