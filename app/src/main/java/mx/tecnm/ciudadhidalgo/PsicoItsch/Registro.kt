package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.ktx.storage
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario
import java.io.File
import java.text.SimpleDateFormat

class Registro : AppCompatActivity() {

    //variable antes de enlazar
    private lateinit var btnRegistrarme:Button
    private lateinit var btnYaEstoyRegistrado:Button
    private lateinit var btntake:Button
    private lateinit var nombre:TextInputLayout
    private lateinit var paterno:TextInputLayout
    private lateinit var materno:TextInputLayout
    private lateinit var grupo:TextInputLayout
    private lateinit var control:TextInputLayout
    private lateinit var carrera:TextInputLayout
    private lateinit var correoR:TextInputLayout
    private lateinit var passR:TextInputLayout
    private val PICK_IMAGE_REQUEST= 123
    private val REQUEST_CODE= 123
    private lateinit var subirfoto: Button
    private lateinit var auth: FirebaseAuth;
    private lateinit var imagen: String
    private lateinit var fotox: ImageView
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //acceso a la base de datos


        //enlazamos el componente
        btnRegistrarme = findViewById(R.id.btnRegistrarme)
        btnYaEstoyRegistrado = findViewById(R.id.btnYaEstoyRegistrado)
        nombre = findViewById(R.id.nombre)
        paterno = findViewById(R.id.paterno)
        materno = findViewById(R.id.materno)
        grupo = findViewById(R.id.grupo_registro)
     carrera = findViewById(R.id.carrera)
        control = findViewById(R.id.num_control)
        //email registro y pass registro para no tomar los de login
        correoR = findViewById(R.id.email_registro)
        passR = findViewById(R.id.password_registro)
        subirfoto = findViewById(R.id.btnSubirArchivo)

        fotox = findViewById(R.id.pic)
        //realizar la accion de llevar a la sig ventana al dar click en el boton registrarme
        //aceso a la base de datos val baseDatos
        val BasedeDatos = FirebaseFirestore.getInstance()


        subirfoto.setOnClickListener {
//abrimos intent para sacar el imagepicker
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)



        }






        btnRegistrarme.setOnClickListener{
                val email = correoR.editText?.text
                val psw = passR.editText?.text

                if(email.toString().isNotEmpty()&& psw.toString().isNotEmpty() && nombre.toString().isNotEmpty()
                    && paterno.toString().isNotEmpty() && materno.toString().isNotEmpty()
                    && grupo.toString().isNotEmpty()
                    && carrera.toString().isNotEmpty()
                    && imagen.toString().isNotEmpty()
                    && control.toString().isNotEmpty()

                ){
                    val _usuario = Usuario(email.toString(),
                        nombre.editText?.text.toString(),
                        paterno.editText?.text.toString(),
                        materno.editText?.text.toString(),
                        imagen,
                        carrera.editText?.text.toString(),
                        grupo.editText?.text.toString(),
                        control.editText?.text.toString(),
                        "alumno",
                        psw.toString())







                    val confirmDialogo = AlertDialog.Builder(it.context)
                    confirmDialogo.setTitle("Confirmar Usuario")
                    confirmDialogo.setMessage("""
                Usuario: ${nombre.editText?.text} ${paterno.editText?.text} ${materno.editText?.text}
                Correo: ${email.toString()}
                Contraseña: ${psw.toString()}
            """.trimIndent())
                    confirmDialogo.setPositiveButton("Confirmar"){
                            confirmDialogo,which->
                        if(email.toString().isNotEmpty()&& psw.toString().isNotEmpty()){
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                                email.toString(),psw
                                    .toString()).addOnCompleteListener{
                                if(it.isSuccessful){

                                        BasedeDatos.collection("alumnos")
                                            .document(email.toString())
                                            .set(_usuario).addOnSuccessListener {

                                                val campos = mapOf(
                                                    "entrevista" to listOf<Any>(),
                                                    "academicas" to listOf<Any>(),
                                                    "canalizacion" to listOf<Any>()
                                                )

                                                BasedeDatos.collection("alumnos")
                                                    .document(email.toString())
                                                    .update(campos).addOnSuccessListener {
                                                        Firebase.auth.signOut()
                                                        val intent  = Intent(this,Login::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    }.addOnFailureListener{
                                                        notificacion()
                                                    }


                                    }.addOnFailureListener{
                                                notificacion()
                                    }
                                }else{
                                    notificacion()
                                }
                            }


                        }
                    }
                    confirmDialogo.setNegativeButton("Editar Datos"){
                            confirmDialogo,which ->
                        confirmDialogo.cancel()
                    }
                    confirmDialogo.show()
                }else{
                    notificacion()

                }
            }








        //realizar la accion de llevar a la sig ventana al dar click en el boton yaEstoyRegistrado
        btnYaEstoyRegistrado.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
 finish()
        }
    }

    private fun notificacion() {
        val notiDialogo = AlertDialog.Builder(this)
        notiDialogo.setTitle("ERROR")
        notiDialogo.setMessage("Se ha producido un error en el registro")
        notiDialogo.setPositiveButton("ACEPTAR", null)
        notiDialogo.show()
    }


    //esta funcion se ejecuta al seleccionar una imagen
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
                        .override(500, 200)
                        .into(fotox);

                    //Toast.makeText(this,"Se subio la imagen correctamente",Toast.LENGTH_SHORT)
                }



            }
        }
    }

   

}