package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorMplatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorSesion
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Sesiones
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class MySesiones : AppCompatActivity() , OnRefreshListener{
    private lateinit var recyclearsesion: RecyclerView
    private lateinit var listasesiones: ArrayList<Sesiones>
    private lateinit var adaptadorsesion: AdaptadorSesion
    private lateinit var btnadds:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_sesiones)
        if(savedInstanceState != null){

            val gmail = savedInstanceState?.getString("email")
            val nombrex = savedInstanceState?.getString("nombre")
            val paterno = savedInstanceState?.getString("paterno")
            val materno= savedInstanceState?.getString("materno")
            val grupo = savedInstanceState?.getString("grupo")
            val carrera = savedInstanceState?.getString("carrera")
            val rol = savedInstanceState?.getString("rol")
            val psw = savedInstanceState?.getString("psw")
            val foto = savedInstanceState?.getString("foto")
            val control = savedInstanceState?.getString("control")

            // Do the same for the other properties of the usuario object
            usuario = Usuario(gmail,nombrex,paterno,materno,foto,carrera,grupo,control,rol,psw)
            Log.e(ContentValues.TAG,usuario.toString())
        }
        recyclearsesion = findViewById(R.id.rvsesiones)
        btnadds = findViewById(R.id.sesionb)
        var basedeDatos = FirebaseFirestore.getInstance()
        listasesiones= ArrayList()
 var completo:String=""
        var foto:String=""
        val db = Firebase.firestore
        val sesiones = db.collection("sesiones")
            .whereEqualTo("numeroC", usuario.control.toString())
            .get().addOnSuccessListener { documentos ->
                val sortedDocumentos = documentos.sortedByDescending { documento ->
                    documento.toObject(Sesiones::class.java)?.fecha
                }
                for (documento in sortedDocumentos) {
                    var id = documento.id
                    val sesion = documento.toObject(Sesiones::class.java)
                    if (sesion != null) {
                        val atd = db.collection("admin").document(sesion.nombreMedico.toString())

                        atd.get().addOnSuccessListener { document ->
                            val nombre = document.data?.get("nombre")
                            Log.e(TAG,nombre.toString())
                            val paterno = document.data?.get("paterno")
                             completo = nombre.toString() + " " + paterno.toString()
                             foto= document.data?.get("foto").toString()

                            listasesiones.add(
                                Sesiones(
                                    sesion.fecha,
                                    sesion.hora,
                                    sesion.nombreAlumno,
                                    completo,
                                    sesion.numeroC,
                                    sesion.tipo,
                                    foto,
                                ) )

                            recyclearsesion.layoutManager = LinearLayoutManager(
                                this, LinearLayoutManager.VERTICAL,
                                false
                            )

                            adaptadorsesion = AdaptadorSesion(listasesiones,this)
                            recyclearsesion.adapter =  adaptadorsesion

                            adaptadorsesion.onSesionClick = {
                                val intent = Intent(this, SesionDetalle::class.java)
                                intent.putExtra("sesion", it)

                                startActivity(intent)

                            }


                            Log.e(ContentValues.TAG, foto + completo + "sesion" + sesion.numeroC + sesion.nombreAlumno)
                        }.addOnFailureListener { exception ->
                            Log.e(ContentValues.TAG, "Error getting document: ", exception)
                        }




                        Log.e(ContentValues.TAG, sesion.foto + sesion.nombreMedico + "sesion" + sesion.numeroC + sesion.nombreAlumno)
                    }
                }







        }











           btnadds.setOnClickListener {
               val intent = Intent(this, Sesion::class.java)
               startActivity(intent)



           }




        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()

        }



        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_2 -> {
                    val intent = Intent(this, Platicas::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_3 -> {

                    true
                }

                R.id.page_5 -> {
                    val intent = Intent(this, Perfil::class.java)

                    startActivity(intent)
                    false

                }

                else -> false
            }
        }
        bottomNav .selectedItemId = R.id.page_3
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the state of the usuario object here
        outState.putString("email", usuario.email)
        outState.putString("nombre", usuario.nombre)
        outState.putString("paterno", usuario.paterno)
        outState.putString("materno", usuario.materno)
        outState.putString("carrera", usuario.carrera)
        outState.putString("grupo", usuario.grupo)
        outState.putString("control", usuario.control)
        outState.putString("foto", usuario.foto)
        outState.putString("rol", usuario.rol)
        outState.putString("psw", usuario.psw)



        // Do the same for the other properties of the usuario object
    }

    override fun refresh() {
        val intent = Intent(this, MPlatica::class.java)
        startActivity(intent)
        finish()
        Intent.FLAG_ACTIVITY_NO_ANIMATION
    }

}