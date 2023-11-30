package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class EditarAlumno : AppCompatActivity() {
    private lateinit var nombre: TextInputLayout
    private lateinit var paterno: TextInputLayout
    private lateinit var materno: TextInputLayout
    private lateinit var grupo: TextInputLayout
    private lateinit var control: TextInputLayout
    private lateinit var carrera: TextInputLayout
    private lateinit var picture: ImageView
    private lateinit var imagen: String
    private lateinit var editbtn: Button
    private lateinit var btnpic:Button
    private val PICK_IMAGE_REQUEST= 123
    private lateinit var alumno:Alumno
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
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


        nombre = findViewById(R.id.nombreed)
        paterno = findViewById(R.id.paternoed)
        materno = findViewById(R.id.maternoed)
        grupo = findViewById(R.id.grupoed)
        carrera = findViewById(R.id.carreraed)
        picture = findViewById(R.id.pic)
        editbtn = findViewById(R.id.btnguardar)
        btnpic = findViewById(R.id.btnSubirf)
        control = findViewById(R.id.num_controled)

        alumno  = intent.getParcelableExtra<Alumno>("id")!!

        if(alumno!=null) {
            Glide.with(this)
                .load(alumno.foto)

                .into(picture);
            nombre.editText?.setText(alumno.nombre)
            paterno.editText?.setText(alumno.paterno)
            materno.editText?.setText(alumno.materno)
            grupo.editText?.setText(alumno.grupo)
            carrera.editText?.setText(alumno.carrera)
            control.editText?.setText(alumno.control)


        }







        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {

            finish()

        }
        val db = Firebase.firestore
        val baseDeDatos = Firebase.firestore
        val docRef = db.collection("alumnos").document(alumno.email.toString())
        val storage = Firebase.storage



        btnpic.setOnClickListener {

            val url =alumno.foto.toString()
            val storage = Firebase.storage
            val imageRef = storage.getReferenceFromUrl(url)

            if(alumno.foto == null){

                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)

            }else{


                imageRef.delete().addOnSuccessListener {
                    Log.d(TAG, "onSuccess: deleted file")
                    alumno.foto = ""
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, PICK_IMAGE_REQUEST)

                }.addOnFailureListener {
                    Log.d(TAG, "onFailure: did not delete file")
                }
            }



        }

        editbtn.setOnClickListener {



            if(nombre.editText?.text.toString().isNotEmpty() &&
                materno.editText?.text.toString().isNotEmpty() &&
                paterno.editText?.text.toString().isNotEmpty() &&
                grupo.editText?.text.toString().isNotEmpty() &&
                carrera.editText?.text.toString().isNotEmpty() &&  control.editText?.text.toString().isNotEmpty()
            ){
//tomamos los datos del formulario
                val updates = hashMapOf(
                    "nombre" to nombre.editText?.text.toString(),
                    "paterno" to paterno.editText?.text.toString(),
                    "materno" to materno.editText?.text.toString(),
                    "grupo" to grupo.editText?.text.toString(),
                    "carrera" to carrera.editText?.text.toString(),
                    "control" to control.editText?.text.toString(),




                    )
                docRef.update(updates as Map<String, Any>)
                    .addOnSuccessListener {

                        Toast.makeText(this, "Alumno actualizado", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, Alumnos::class.java)
                                startActivity(intent)
                            }

                    .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
            }else{
                //cuadro de dialogo si el usuario intenta enviar el formualario vacio
                val  notiDialog = AlertDialog.Builder(this)
                notiDialog.setTitle("Error")
                notiDialog.setMessage("Datos incompletos")

                notiDialog.setPositiveButton("Aceptar",null)
                notiDialog.show()


            }
        }
    }


    private fun notificacion() {
        val notiDialogo = AlertDialog.Builder(this)
        notiDialogo.setTitle("ERROR")
        notiDialogo.setMessage("Se ha producido un error en la edicion")
        notiDialogo.setPositiveButton("ACEPTAR", null)
        notiDialogo.show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            ///obtiene la imagen del imagenpicker
            val uri: Uri? = data.data


//crea una nueva instancia de storage
            val cubeta = Firebase.storage.reference

            //subir la imagen a firebase
            val imagenreferencia = cubeta.child("${"usuarios/"+uri!!.lastPathSegment}")
            val uploadTask = imagenreferencia.putFile(uri)

            //si la imagen se subio correctamente
            uploadTask.addOnSuccessListener { taskSnapshot ->

                //busca el url de la imagen despues de ser subida
                imagenreferencia.downloadUrl.addOnSuccessListener { uri ->
                    val urlimagen = uri.toString()
                    //agregamos el url a la variable string
                    imagen= urlimagen
                    //ponemos la imagen en vista previa en el formulario
                    Glide.with(this)
                        .load( urlimagen )

                        .into(picture);
                   alumno.foto= urlimagen
                    val updates = hashMapOf(
                        "foto" to alumno.foto.toString(),




                        )
                    val db = Firebase.firestore
                    val docRef = db.collection("alumnos").document(alumno.email.toString())
                    docRef.update(updates as Map<String, Any>)
                        .addOnSuccessListener{
                            Toast.makeText(this,"Se cambio de foto correctamnte",Toast.LENGTH_SHORT).show()

                        }


                    //Toast.makeText(this,"Se subio la imagen correctamente",Toast.LENGTH_SHORT)
                }



            }
        }}
}