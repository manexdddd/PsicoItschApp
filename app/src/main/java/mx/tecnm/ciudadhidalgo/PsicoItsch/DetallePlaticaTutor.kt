package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Aviso
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class DetallePlaticaTutor : AppCompatActivity() {
    private lateinit var  fotoa : ImageView
    private lateinit var temaa : TextView
    private lateinit var desca : TextView
    private lateinit var fechaa : TextView
    private lateinit var cupo : TextView
    private lateinit var contenide : TextView
    private lateinit var bad : TextView
    private lateinit var inscribir: Button
    private lateinit var platica: Platica
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_platica_tutor)
        if(savedInstanceState != null){

            val gmail = savedInstanceState?.getString("emailt")
            val nombrex = savedInstanceState?.getString("nombret")
            val paterno = savedInstanceState?.getString("paternot")
            val materno= savedInstanceState?.getString("maternot")
            val grupo = savedInstanceState?.getString("grupot")
            val carrera = savedInstanceState?.getString("carrerat")
            val rol = savedInstanceState?.getString("rolt")
            val psw = savedInstanceState?.getString("pswt")
            val foto = savedInstanceState?.getString("fotot")
            val division = savedInstanceState?.getString("division")
            val direcciont = savedInstanceState?.getString("direcciont")
            val telefonot = savedInstanceState?.getString("telefonot")
            // Do the same for the other properties of the usuario object
            tutor = Tutor(carrera,direcciont,division,gmail,foto,grupo,materno,nombrex,paterno,psw,rol,telefonot)

        }



        fotoa = findViewById(R.id.fotoplatica)
        temaa = findViewById(R.id.tema)
        desca = findViewById(R.id.descripcioplatica)
        contenide = findViewById(R.id.contenido)
        fechaa = findViewById(R.id.fecha_platica)
        inscribir = findViewById(R.id.btnInscribir)
        bad = findViewById(R.id.yonagui)
        cupo = findViewById(R.id.cupo)

        platica = intent.getParcelableExtra<Platica>(
            "platica"
        )!!

        if(platica!=null) {
            Glide.with(this)
                .load(platica.foto)

                .into(fotoa);
            temaa.text = platica.tema
            desca.text = platica.descripcion
            contenide.text = "La platica se dara a cabo el "+platica.fecha+" En "+platica.lugar+" a las "+platica.hora+
                    " Impartida por el ponente: "+platica.ponente
            cupo.text = "Con un Cupo limitado de "+ platica.cupo

            fechaa.text = " \uD83D\uDDD3 "+platica.fecha+" "+platica.lugar


        }

        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)

        val db2 = Firebase.firestore
        val docRef2 = db2.collection("platicas").document(platica.id.toString())
        val query = db2.collection("alumnos").whereEqualTo("grupo", tutor.grupot)
        Log.e(TAG,query.toString())

        toolbar.setNavigationOnClickListener {
            platica.cupo = 0
            platica.id = ""
            platica.foto = ""
            platica.hora = ""
            platica.tema = ""
            platica.descripcion = ""
            platica.fecha = ""
            platica.lugar = ""
            platica.ponente = ""
            finish()
        }

        val db = Firebase.firestore
        val docRef = db.collection("platicas").document(platica.id.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val asistentes = document.get("asistentes") as List<String>
                if(asistentes.count() == platica.cupo){
                    inscribir.visibility = View.INVISIBLE

                    bad.visibility = View.VISIBLE
                }else{
                    bad.visibility = View.INVISIBLE
                    inscribir.visibility = View.VISIBLE
                }
            } else {
                Log.d(TAG, "No such document")
            }
        }


        inscribir.setOnClickListener {
            val db = Firebase.firestore
            val docRef = db.collection("platicas").document(platica.id.toString())
            val query = db.collection("alumnos").whereEqualTo("grupo", tutor.grupot)

            docRef.get().addOnSuccessListener {document->
                val asistentes = document.get("asistentes") as List<String>
                if(asistentes.count() == platica.cupo){
                    notificacion()
                }else{
                    query.get().addOnSuccessListener { querySnapshot ->
                        val newAsistentes = asistentes.toMutableList()
                        for (document in querySnapshot) {
                            newAsistentes.add(document.id)
                        }
                        newAsistentes.add(tutor.emailt.toString())
                        docRef.update("asistentes", newAsistentes,)
                            .addOnCompleteListener {
                                Toast.makeText(this,"Se registro correctamente a la platica",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, GrupoPlaticas::class.java)
                                startActivity(intent)
                                finish()
                            }
                    }
                }
            }



        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the state of the usuario object here
        outState.putString("emailt", tutor.emailt)
        outState.putString("nombret", tutor.nombret)
        outState.putString("paternot", tutor.paternot)
        outState.putString("maternot", tutor.maternot)
        outState.putString("carrerat", tutor.carrerat)
        outState.putString("grupot", tutor.grupot)
        outState.putString("division", tutor.division)
        outState.putString("direcciont", tutor.direccion)
        outState.putString("direcciont", tutor.telefono)
        outState.putString("fotot", tutor.fotot)
        outState.putString("rolt", tutor.rolt)
        outState.putString("pswt", tutor.pswt)



        // Do the same for the other properties of the usuario object
    }

    override fun onBackPressed() {
        // Your custom back button logic goes here
        // For example, you can show a confirmation dialog before exiting the app
        platica.cupo = 0
        platica.id = ""
        platica.foto = ""
        platica.hora = ""
        platica.tema = ""
        platica.descripcion = ""
        platica.fecha = ""
        platica.lugar = ""
        platica.ponente = ""
        finish()
    }
    private fun notificacion() {
        val notiDialogo = AlertDialog.Builder(this)
        notiDialogo.setTitle("ERROR")
        notiDialogo.setMessage("PLatica Llena Intenta mas tarde")
        notiDialogo.setPositiveButton("ACEPTAR", null)
        notiDialogo.show()
    }

}