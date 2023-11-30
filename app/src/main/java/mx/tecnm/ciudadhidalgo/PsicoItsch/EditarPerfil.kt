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
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class EditarPerfil : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
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
            Log.e(TAG,usuario.toString())
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
        Glide.with(this).load(usuario.foto).into(picture)

        nombre.editText?.setText(usuario.nombre)
        paterno.editText?.setText(usuario.paterno)
        materno.editText?.setText(usuario.materno)
        grupo.editText?.setText(usuario.grupo)
        carrera.editText?.setText(usuario.carrera)
        control.editText?.setText(usuario.control)







        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {

            finish()

        }
        val db = Firebase.firestore
        val baseDeDatos = Firebase.firestore
        val docRef = db.collection("alumnos").document(usuario.email.toString())
        val storage = Firebase.storage



        btnpic.setOnClickListener {

            val url =usuario.foto.toString()
            val storage = Firebase.storage
            val imageRef = storage.getReferenceFromUrl(url)

            if(usuario.foto == null){

                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)

            }else{


                imageRef.delete().addOnSuccessListener {
                    Log.d(TAG, "onSuccess: deleted file")
                    usuario.foto = ""
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

                        Toast.makeText(this,"Usuario actualizado",Toast.LENGTH_SHORT).show()
                        baseDeDatos.collection("alumnos")
                            .whereEqualTo("email", usuario.email.toString())
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
                                }
                                finishAffinity()
                                val intent = Intent(this,Home::class.java)
                                startActivity(intent)



                            }
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
                    usuario.foto= urlimagen
                    val updates = hashMapOf(
                        "foto" to usuario.foto.toString(),




                        )
                    val db = Firebase.firestore
                    val docRef = db.collection("alumnos").document(usuario.email.toString())
                    docRef.update(updates as Map<String, Any>)
                        .addOnSuccessListener{
                            Toast.makeText(this,"Se cambio de foto correctamnte",Toast.LENGTH_SHORT).show()

                        }


                    //Toast.makeText(this,"Se subio la imagen correctamente",Toast.LENGTH_SHORT)
                }



            }
        }}
}