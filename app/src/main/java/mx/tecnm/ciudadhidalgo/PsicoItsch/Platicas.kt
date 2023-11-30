package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorPlatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class Platicas : AppCompatActivity() {

    private lateinit var recyclearplatica: RecyclerView
    private lateinit var listaplaticas: ArrayList<Platica>
    private lateinit var adaptadorplatica: AdaptadorPlatica
    private lateinit var  nombrepla:TextView
    private lateinit var  misspla:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_platicas)
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

        nombrepla = findViewById(R.id.spaname)
        misspla = findViewById(R.id.misplaticas)
        nombrepla.text = usuario.nombre +""+ usuario.paterno
        recyclearplatica = findViewById(R.id.rvplaticas)
        var basedeDatos = FirebaseFirestore.getInstance()
        listaplaticas = ArrayList()
        val db = basedeDatos.collection("platicas")
       db.get().addOnSuccessListener { documentos ->
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

            recyclearplatica.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL,
                false
            )

            adaptadorplatica = AdaptadorPlatica(listaplaticas)
            recyclearplatica.adapter =  adaptadorplatica

            adaptadorplatica.onPlaticaClick = {
                val intent = Intent(this, DetallePlatica::class.java)
                intent.putExtra("platica", it)

                startActivity(intent)


            }

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

                    true
                }

                R.id.page_3 -> {
                    val intent = Intent(this, MySesiones::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_5 -> {
                    val intent = Intent(this, Perfil::class.java)

                    startActivity(intent)
                    false

                }

                else -> false
            }
        }
        bottomNav .selectedItemId = R.id.page_2


        misspla.setOnClickListener {
            val intent = Intent(this, MPlatica::class.java)
            startActivity(intent)
            finish()

        }

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
}