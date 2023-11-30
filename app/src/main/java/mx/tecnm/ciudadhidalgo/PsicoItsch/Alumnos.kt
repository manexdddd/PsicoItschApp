package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorAlumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorPlatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor

class Alumnos : AppCompatActivity() {
    private lateinit var nombre: TextView
    private lateinit var grupo:TextView
    private lateinit var add:ImageButton
    private lateinit var recyclearalumno: RecyclerView
    private lateinit var adaptadoralumno: AdaptadorAlumno
    private lateinit var listaalumnos: ArrayList<Alumno>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumnos)
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

        nombre = findViewById(R.id.tutoralu)
        grupo = findViewById(R.id.grupoalu)
        add = findViewById(R.id.addalumno)
        nombre.text = "Tutorado "+ tutor.nombret +" "+tutor.paternot + " "+ tutor.maternot
           grupo.text = "Este es tu grupo "+ tutor.grupot +" \uD83D\uDC67 \uD83D\uDC68 "
        recyclearalumno = findViewById(R.id.rvalumnos)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation2)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, HomeT::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_2 -> {
                    val intent = Intent(this, TutorPlaticas::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_3 -> {

                    true
                }

                R.id.page_5 -> {
                    val intent = Intent(this, TutorPerfil::class.java)

                    startActivity(intent)
                    false

                }

                else -> false
            }
        }
        bottomNav .selectedItemId = R.id.page_3


        

        add.setOnClickListener{

            val intent = Intent(this, AgregarAlumno::class.java)

            startActivity(intent)
          finish()
        }
        var basedeDatos = FirebaseFirestore.getInstance()
        listaalumnos= ArrayList()
        val db = basedeDatos.collection("alumnos")
            .whereEqualTo("grupo", tutor.grupot)
        db.get().addOnSuccessListener { documentos ->
            val sortedDocumentos = documentos.sortedBy{ documento ->
                documento.toObject(Alumno::class.java)?.paterno
            }
            for (documento in sortedDocumentos ) {
                var id = documento.id
                val alumno = documento.toObject(Alumno::class.java)
                if (alumno != null) {
                    listaalumnos.add(
                        Alumno(
                            id,
                            alumno.email,
                            alumno.nombre,
                            alumno.paterno,
                            alumno.materno,
                            alumno.foto,
                            alumno.carrera,
                            alumno.grupo,
                            alumno.control,
                            alumno.rol,
                        )
                    )

                }
                Log.e(ContentValues.TAG,listaalumnos.count().toString())
            }

            recyclearalumno.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL,
                false
            )

            adaptadoralumno = AdaptadorAlumno(listaalumnos)
            recyclearalumno.adapter =  adaptadoralumno

            adaptadoralumno.onPlaticaClick = {
                val intent = Intent(this, AlumnoDetalle::class.java)
                intent.putExtra("alumno", it)

                startActivity(intent)

            }


            val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
            val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
            toolbar.setNavigationIcon(navigationIcon)


            toolbar.setNavigationOnClickListener {

                finish()

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
}