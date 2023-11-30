package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

//variable global
    lateinit var usuario: Usuario
lateinit var tutor: Tutor
class Login : AppCompatActivity() {

    //variable antes de enlazar
    private lateinit var btnRegistrar:MaterialButton
    private lateinit var logo: ImageView
    private lateinit var btnIngresar:MaterialButton
    private lateinit var auth: FirebaseAuth
    private lateinit var btnRecuperar: Button
    private lateinit var btnGoogle: Button
    private lateinit  var sesiongoogle: GoogleSignInClient
    private lateinit var  progre:ProgressBar
    private lateinit  var cuentaGoogle: GoogleSignInClient
    private val RC_SIGN_IN = 123
  private var cargador :Boolean = false;
    //no usar las variables de Registro
    //correoR y passR
    private lateinit var correo: TextInputLayout
    private lateinit var pass: TextInputLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth
        val baseDeDatos = Firebase.firestore

        //Iniciar Sesion con Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //se pide el id de usuario google
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        cuentaGoogle = GoogleSignIn.getClient(this, gso)

        //enlazamos el componente
        btnRegistrar = findViewById(R.id.btnRegistrar)
        logo = findViewById(R.id.logo_login)
        btnIngresar = findViewById(R.id.btnIngresar)
        correo = findViewById(R.id.email)
        pass = findViewById(R.id.password)
        progre = findViewById(R.id.progreso)
        btnRecuperar = findViewById(R.id.btnOlvidoPass)
        btnGoogle = findViewById(R.id.btnGoogle)

        btnRecuperar.setOnClickListener{
            val intent = Intent(this,RecuperarPass::class.java)
            startActivity(intent)
        }
        progre.visibility =View.INVISIBLE


        //realizar la accion de llevar a la sig ventana (HOme) al dar click en el boton Ingresar
        btnIngresar.setOnClickListener{
            correo.visibility = View.INVISIBLE
            pass.visibility =View.INVISIBLE
            btnGoogle.visibility =View.INVISIBLE
            btnIngresar.visibility =View.INVISIBLE
            btnRegistrar.visibility =View.INVISIBLE
            btnRecuperar.visibility =View.INVISIBLE
            progre.visibility =View.VISIBLE
            val email = correo.editText?.text
            val psw = pass.editText?.text
            if (email.toString().isNotEmpty()&&psw.toString().isNotEmpty()){
                auth.signInWithEmailAndPassword(email.toString(),psw.toString())
                    .addOnCompleteListener() {
                        if(it.isSuccessful){
                            baseDeDatos.collection("admin")
                                .whereEqualTo("email",email.toString())
                                .get().addOnSuccessListener {
                                   documentos->
                                    if(documentos.isEmpty()){
                                        baseDeDatos.collection("alumnos")
                                            .whereEqualTo("email",email.toString())
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
                                                Log.e(TAG,"usuario")
                                                val compartido = getSharedPreferences("rol", Context.MODE_PRIVATE)
                                                val editor = compartido.edit()
                                                editor.putString("rol", usuario.rol.toString())
                                                editor.apply()
                                                val intent = Intent(this, Home::class.java)
                                                startActivity(intent)
                                                overridePendingTransition(R.anim.left, R.anim.right)
                                                finish()
                                                progre.visibility =View.INVISIBLE   }
                                    }else{
                                        for (documento in documentos){
                                            tutor = Tutor(
                                                "${documento.data.get("carrera")}",
                                                "${documento.data.get("direccion")}",
                                                "${documento.data.get("division")}",
                                                "${documento.data.get("email")}",
                                                "${documento.data.get("foto")}",
                                                "${documento.data.get("grupo")}",
                                                "${documento.data.get("materno")}",
                                                "${documento.data.get("nombre")}",
                                                "${documento.data.get("paterno")}",
                                                "${documento.data.get("psw")}",
                                                "${documento.data.get("rol")}",
                                                "${documento.data.get("telefono")}"
                                            )
                                        }
                                        val compartido = getSharedPreferences("rol", Context.MODE_PRIVATE)
                                        val editor = compartido.edit()
                                        editor.putString("rol", tutor.rolt.toString())
                                        editor.apply()

                                        Log.e(TAG,compartido.toString())
                                        val intent = Intent(this, HomeT::class.java)
                                        startActivity(intent)
                                        overridePendingTransition(R.anim.left, R.anim.right)
                                        finish()
                                        progre.visibility =View.INVISIBLE

                                    }




                                }


                        }else{
                            progre.visibility =View.INVISIBLE
                            correo.visibility = View.VISIBLE
                            pass.visibility =View.VISIBLE
                            btnGoogle.visibility =View.VISIBLE
                            btnIngresar.visibility =View.VISIBLE
                            btnRegistrar.visibility =View.VISIBLE
                            btnRecuperar.visibility =View.VISIBLE
                            notificacion()

                        }
                    }
            }
            else{
                correo.visibility = View.VISIBLE
                pass.visibility =View.VISIBLE
                progre.visibility =View.INVISIBLE
                correo.visibility = View.VISIBLE
                pass.visibility =View.VISIBLE
                btnGoogle.visibility =View.VISIBLE
                btnIngresar.visibility =View.VISIBLE
                btnRegistrar.visibility =View.VISIBLE
                btnRecuperar.visibility =View.VISIBLE
                notificacion()
            }
        }

        //Inicia Sesion Google
        btnGoogle.setOnClickListener{
            //ejecuta el inicio de sesion
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //solicitael id de cliente google
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            sesiongoogle = GoogleSignIn.getClient(this, gso)
            signg()
        }

        //realizar la accion de llevar a la sig ventana al dar click en el boton registrar
        btnRegistrar.setOnClickListener{
            val intent = Intent(this,Registro::class.java)
            startActivity(intent)
        }
    }

    private fun notificacion() {
        val notiDialogo = AlertDialog.Builder(this)
        notiDialogo.setTitle("ERROR")
        notiDialogo.setMessage("Se ha producido un error en la " +
        "Autenticacion del usuario")
        notiDialogo.setPositiveButton("ACEPTAR", null)
        notiDialogo.show()
    }

    //funcion de iniciar sesion
    private fun signg() {

        val signInIntent =  sesiongoogle.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                // Autenticación exitosa con Google, ahora puedes guardar la cuenta en Firebase
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Guarda los datos de email y nombre en los datos de usuario
                            usuario = Usuario(
                                account?.email.toString(),
                                account?.displayName.toString(),
                                "s/n",
                                "s/n",
                                account?.photoUrl.toString()
                                )



                            //Te envia al la actividad de tienda
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Error en la autenticación con Firebase
                        }
                    }
            } catch (e: ApiException) {
                // Error en la autenticación con Google
            }
        }



    }

}