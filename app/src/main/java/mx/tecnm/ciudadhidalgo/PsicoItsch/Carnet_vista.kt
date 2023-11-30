package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorAcademica
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorCanal
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorEntrevista
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorPlatica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Academica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Canal
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Entrevista
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class Carnet_vista : AppCompatActivity() {

    private lateinit var btnRegresar:ImageButton
    private lateinit var  nombrez: TextView
    private lateinit var  apellidoz: TextView
    private lateinit var  grupoz: TextView
    private lateinit var  carreraz: TextView
    private lateinit var  controlz: TextView
    private lateinit var recyclearacademica: RecyclerView
    private lateinit var recyclearcanal: RecyclerView
    private lateinit var recyclearentrevista: RecyclerView
    private lateinit var  controlca: TextView
    private lateinit var  grupoca: TextView
    private lateinit var  tutorfirma:TextView

    private lateinit var  carreraca: TextView
    private lateinit var listaacademica: ArrayList<Academica>
    private lateinit var listacanal: ArrayList<Canal>
    private lateinit var listaentrevista: ArrayList<Entrevista>
   private lateinit var adaptadoracademica: AdaptadorAcademica
    private lateinit var adaptadorcanal: AdaptadorCanal
    private lateinit var adaptadorentrevista: AdaptadorEntrevista
       private lateinit var  completon:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carnet_vista)
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
        nombrez= findViewById(R.id.nombrecc)
        apellidoz = findViewById(R.id.apellidoscc)
        carreraz = findViewById(R.id.carreracc)
        controlz = findViewById(R.id.controlcc)
        grupoz = findViewById(R.id.grupocc)
        recyclearacademica = findViewById(R.id.rvacademica)
        recyclearcanal = findViewById(R.id.rvcanal)
        recyclearentrevista = findViewById(R.id.rventrevista)
        completon = findViewById(R.id.nombre_carnet2)
        carreraca = findViewById(R.id.carrera_carnet2)
        controlca = findViewById(R.id.numcontrol_carnet2)
        grupoca = findViewById(R.id.grupo_carnet2)
        tutorfirma = findViewById(R.id.firmatutor_carnet2)

        nombrez.text = usuario.nombre
        apellidoz.text = usuario.paterno+" "+usuario.materno
        carreraz.text = usuario.carrera
        controlz.text = usuario.control
        grupoz.text = usuario.grupo


        completon.text = usuario.paterno+" "+usuario.materno+" "+usuario.nombre
        carreraca.text  = usuario.carrera
        controlca.text = usuario.control
        grupoca.text = usuario.grupo
        var basedeDatos = FirebaseFirestore.getInstance()
        listaacademica = ArrayList()
        listaentrevista = ArrayList()
        listacanal = ArrayList()
        basedeDatos.collection("alumnos")
            .whereEqualTo("email",usuario.email.toString())
            .get().addOnSuccessListener {
                    documentos->
                for (documento in documentos){
                    usuario = Usuario(
                        "${documento.data.get("email")}",
                        "${documento.data.get("nombre")}",
                        "${documento.data.get("paterno")}",
                        "${documento.data.get("materno")}",
                        "${documento.data.get("foto")}",
                        "${documento.data.get("carrera")}",
                        "${documento.data.get("grupo")}",
                        "${documento.data.get("control")}"
                    )

                    val acaArray = documento.get("academicas") as List<Map<String, Any>>
                    val canalArray = documento.get("canalizacion") as List<Map<String, Any>>
                    val EntrevistaArray = documento.get("entrevista") as List<Map<String, Any>>
                    for (usuarioMap in acaArray) {
                        val asignaturaf = usuarioMap["asignatura"]
                        val estatusf = usuarioMap["estatus"]
                        val fechaf = usuarioMap["fecha"]
                        val tipo = usuarioMap["tipo"]
                        val tutor = usuarioMap["tutor"]
                        listaacademica.add(
                            Academica(
                                asignaturaf.toString(),
                                estatusf.toString(),
                                fechaf.toString(),
                                tipo.toString(),
                                usuario.nombre.toString(),
                                tutor.toString()
                            )
                        )

                        tutorfirma.text = tutor.toString()
                    }

                    for (usuarioMap in canalArray) {

                        val areaf = usuarioMap["area"]
                        val fechaf = usuarioMap["fecha"]
                        val fechasf = usuarioMap["fechas"]
                        val obser = usuarioMap["observaciones"]
                        val tutorx = usuarioMap["tutor"]
                        listacanal.add(
                            Canal(
                                areaf.toString(),
                                fechaf.toString(),
                                fechasf.toString(),
                                obser.toString(),
                                tutorx.toString()
                            )
                        )
                    }

                    for (usuarioMap in EntrevistaArray) {
                        // Do something with usuarioMap
                        val temaf = usuarioMap["tema"] // Replace "someField" with the key of the field you want to retrieve
                        val fechafx = usuarioMap["fecha"]
                        val tutorfx = usuarioMap["tutor"]
                        listaentrevista.add(
                            Entrevista(

                                fechafx.toString(),
                                temaf.toString(),
                                usuario.nombre +" "+ usuario.paterno+ " "+ usuario.materno,
                                tutorfx.toString(),
                            )
                        )
                    }



           recyclearacademica.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL,
                false
            )

            adaptadoracademica = AdaptadorAcademica(listaacademica)
            recyclearacademica.adapter =  adaptadoracademica


                    recyclearcanal.layoutManager = LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    adaptadorcanal = AdaptadorCanal(listacanal)
                    recyclearcanal.adapter =  adaptadorcanal


                    recyclearentrevista.layoutManager = LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    adaptadorentrevista = AdaptadorEntrevista(listaentrevista)
                    recyclearentrevista.adapter =  adaptadorentrevista

        }

            }





        btnRegresar = findViewById(R.id.btnRegresarHome)

        btnRegresar.setOnClickListener{
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
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