package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorAviso
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Aviso
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class HomeT : AppCompatActivity() {
    private lateinit var txtUsuario: TextView
    private lateinit var logout: Button
    private lateinit var recyclearaviso: RecyclerView
    private lateinit var listaAvisos: ArrayList<Aviso>
    private lateinit var adaptadoraviso: AdaptadorAviso
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_t)
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

        txtUsuario = findViewById(R.id.usuarioname_home)
        recyclearaviso = findViewById(R.id.rvavisos)
        txtUsuario.text = "${tutor?.nombret} ${tutor?.paternot} ${tutor?.maternot} \uD83D\uDE0E"
        var basedeDatos = FirebaseFirestore.getInstance()

        //Recycler artesanias
        recyclearaviso.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )

        listaAvisos = ArrayList()
        val db = basedeDatos.collection("avisos")

        db.orderBy("fecha", Query.Direction.DESCENDING).get().addOnSuccessListener { documentos ->
            for (documento in documentos) {
                val aviso = documento.toObject(Aviso::class.java)
                if (aviso != null) {
                    listaAvisos.add(
                        Aviso(
                            aviso.autor,
                            aviso.contenido,
                            aviso.descripcion,
                            aviso.fecha,
                            aviso.foto,
                            aviso.lugar,
                            aviso.titulo,
                        )
                    )
                }
            }

            Log.e(
                ContentValues.TAG,
                listaAvisos.get(0).autor.toString() +
                        listaAvisos.get(0).contenido.toString() +
                        listaAvisos.get(0).descripcion.toString() +
                        listaAvisos.get(0).fecha.toString() +
                        listaAvisos.get(0).foto.toString() +
                        listaAvisos.get(0).lugar.toString() +
                        listaAvisos.get(0).titulo.toString()

            )

            adaptadoraviso = AdaptadorAviso(listaAvisos)
            recyclearaviso.adapter = adaptadoraviso

            adaptadoraviso.onAvisoClick = {
                val intent = Intent(this, DetalleAvisoTutor::class.java)
                intent.putExtra("aviso", it)

                startActivity(intent)


            }


        }.addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
        }


        val email = intent.getStringExtra("email")
        val nombre = intent.getStringExtra("nombre")
        // Configurar las opciones de inicio de sesión de Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Crear una instancia del cliente de Google Sign-In
        val sesiongoogle = GoogleSignIn.getClient(this, gso)







        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {

                    true
                }

                R.id.page_2 -> {
                    val intent = Intent(this, TutorPlaticas::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_3 -> {
                    val intent = Intent(this, Alumnos::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_5 -> {
                    val intent = Intent(this, TutorPerfil::class.java)

                    startActivity(intent)
                    false

                }

                else -> false
            }
        }
        bottomNav .selectedItemId = R.id.page_1

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)

        toolbar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.log -> {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()

                    Firebase.auth.signOut()
                    //cerrar sesion en google
                    val sesiongoogle = GoogleSignIn.getClient(this, gso)
                    sesiongoogle.signOut().addOnCompleteListener(this) {
                        // La sesión de Google se cerró con éxito

                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()

                    }
                    true
                }

                else -> false
            }
        }


    }

    override fun onBackPressed() {
        finish()
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