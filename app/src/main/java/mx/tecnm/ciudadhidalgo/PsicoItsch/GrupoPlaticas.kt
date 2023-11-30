package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorGrupoPlatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorMplatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor

class GrupoPlaticas : AppCompatActivity() , OnRefreshListener{
    private lateinit var titulo: TextView
    private lateinit var recyclearmplatica: RecyclerView
    private lateinit var listaplaticas: ArrayList<Platica>
    private lateinit var adaptadorgrupoplatica: AdaptadorGrupoPlatica
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grupo_platicas)
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

        titulo = findViewById(R.id.grupoti)


        titulo.text = tutor.grupot+ " Platicas \uD83C\uDF88"

        recyclearmplatica = findViewById(R.id.rvmplaticas)
        var basedeDatos = FirebaseFirestore.getInstance()
        listaplaticas = ArrayList()
        val db = basedeDatos.collection("platicas")
        val misplaticas = db.whereArrayContains("asistentes", tutor.emailt.toString())
        misplaticas.get().addOnSuccessListener { documentos ->
            val sortedDocumentos = documentos.sortedByDescending { documento ->
                documento.toObject(Platica::class.java)?.fecha
            }
            for (documento in sortedDocumentos) {
                var id = documento.id
                val platica = documento.toObject(Platica::class.java)
                if (platica != null) {
                    listaplaticas.add(
                        Platica(
                            id,
                            platica.cupo,
                            platica.descripcion,
                            platica.fecha,
                            platica.foto,
                            platica.hora,
                            platica.lugar,
                            platica.ponente,
                            platica.tema,
                        )
                    )
                    Log.e(ContentValues.TAG,id)
                }
            }

            recyclearmplatica.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL,
                false
            )

            adaptadorgrupoplatica = AdaptadorGrupoPlatica(listaplaticas,this)
            recyclearmplatica.adapter =  adaptadorgrupoplatica

            adaptadorgrupoplatica.onPlaticaClick = {
                val intent = Intent(this, MPDetalle::class.java)
                intent.putExtra("platica", it)

                startActivity(intent)


            }

        }

        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, TutorPlaticas::class.java)
            startActivity(intent)
            finish()

        }


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, HomeT::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_2 -> {

                    true
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
        bottomNav .selectedItemId = R.id.page_2


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

    override fun refresh() {
        val intent = Intent(this, GrupoPlaticas::class.java)
        startActivity(intent)
        finish()
        Intent.FLAG_ACTIVITY_NO_ANIMATION
    }





}