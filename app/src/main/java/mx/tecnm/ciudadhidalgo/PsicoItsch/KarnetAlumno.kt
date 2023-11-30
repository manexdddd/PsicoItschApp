package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorAcademica
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorCanal
import mx.tecnm.ciudadhidalgo.PsicoItsch.adaptadores.AdaptadorEntrevista
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Academica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Canal
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Entrevista
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class KarnetAlumno : AppCompatActivity() {

    private lateinit var btnRegresar:ImageButton
    private lateinit var  nombrez: TextView
    private lateinit var  apellidoz: TextView
    private lateinit var  grupoz: TextView
    private lateinit var  carreraz: TextView
    private lateinit var  controlz: TextView
    private lateinit var  alumno: Alumno
    private lateinit var  controlca: TextView
    private lateinit var  grupoca: TextView
    private lateinit var  carreraca: TextView
    private lateinit var  completon:TextView
    private lateinit var  tutorfirma:TextView
    private lateinit var recyclearentrevista: RecyclerView
    private lateinit var recyclearacademica: RecyclerView
    private lateinit var recyclearcanal: RecyclerView
    private lateinit var listaacademica: ArrayList<Academica>
    private lateinit var listacanal: ArrayList<Canal>
    private lateinit var listaentrevista: ArrayList<Entrevista>
    private lateinit var adaptadoracademica: AdaptadorAcademica
    private lateinit var adaptadorcanal: AdaptadorCanal
    private lateinit var adaptadorentrevista: AdaptadorEntrevista
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karnet_alumno)
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
        nombrez= findViewById(R.id.nombrecc)
        apellidoz = findViewById(R.id.apellidoscc)
        carreraz = findViewById(R.id.carreracc)
        controlz = findViewById(R.id.controlcc)
        grupoz = findViewById(R.id.grupocc)
        tutorfirma = findViewById(R.id.firmatutor_carnet2)
        completon = findViewById(R.id.nombre_carnet2)
        carreraca = findViewById(R.id.carrera_carnet2)
        controlca = findViewById(R.id.numcontrol_carnet2)
        grupoca = findViewById(R.id.grupo_carnet2)
        recyclearacademica = findViewById(R.id.rvacademica)
        recyclearcanal = findViewById(R.id.rvcanal)
        recyclearentrevista = findViewById(R.id.rventrevista)
        alumno  = intent.getParcelableExtra<Alumno>("id")!!

        if(alumno!=null) {
            Glide.with(this)
                .load(alumno.foto)

            nombrez.text =alumno.nombre
            apellidoz.text = alumno.paterno+" "+alumno.materno
            carreraz.text = alumno.carrera
            controlz.text = alumno.control
            grupoz.text = alumno.grupo


            completon.text = alumno.paterno+" "+alumno.materno+" "+alumno.nombre
            carreraca.text  = alumno.carrera
            controlca.text = alumno.control
            grupoca.text = alumno.grupo
        }
        tutorfirma.text = tutor.nombret+" "+ tutor.paternot+" "+ tutor.maternot

        var basedeDatos = FirebaseFirestore.getInstance()
        listaacademica = ArrayList()
        listaentrevista = ArrayList()
        listacanal = ArrayList()
        basedeDatos.collection("alumnos")
            .whereEqualTo("email",alumno.email.toString())
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

                    // Get the "usuario" array
                    val acaArray = documento.get("academicas") as List<Map<String, Any>>
                    val canalArray = documento.get("canalizacion") as List<Map<String, Any>>
                    val EntrevistaArray = documento.get("entrevista") as List<Map<String, Any>>
                    if (acaArray != null) {
                        for (usuarioMap in acaArray) {
                            // Do something with usuarioMap
                            val asignaturaf = usuarioMap["asignatura"] // Replace "someField" with the key of the field you want to retrieve
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
                        }
                    }else{
                        listaacademica.add(
                            Academica(
                                "Sin Canalizaciones Academicas",
                                ".",
                                ".",
                                ".",
                                ".",
                                "."
                            )
                        )

                    }
                    if (canalArray != null) {
                    for (usuarioMap in canalArray) {
                        // Do something with usuarioMap
                        val areaf = usuarioMap["area"] // Replace "someField" with the key of the field you want to retrieve
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
                    }}else{
                        listacanal.add(
                            Canal(
                                "Sin Canalizaciones",
                                ".",
                                ".",
                                ".",
                                "."
                            )  )

                    }


                    if (canalArray != null) {
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
                    }}else{
                        listaentrevista.add(
                            Entrevista(
                                "Sin Entrevistas",
                                ".",
                                ".",
                                "."
                            )  )

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
            finish()
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