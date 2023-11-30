package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class Splash : AppCompatActivity() {

    private lateinit var logo:ImageView
    private lateinit var animacion:Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        logo = findViewById(R.id.logo_splash)

        animacion = AnimationUtils.loadAnimation(this,R.anim.anim_splash)

        logo.startAnimation(animacion)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,Login::class.java)
            val transicion = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair(logo,"logo_trans"))
            val user = FirebaseAuth.getInstance().currentUser
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (user != null || account != null) {
                Log.e(TAG,user?.email.toString())
                val sharedPref = getSharedPreferences("rol", Context.MODE_PRIVATE)
                val guardado = sharedPref.getString("rol", "defaultValue")
                Log.e(TAG,guardado.toString())
                if(guardado == "tutor"){
                    val baseDeDatos = Firebase.firestore
                    baseDeDatos.collection("admin")
                        .whereEqualTo("email",user?.email.toString())
                        .get().addOnSuccessListener {

                                documentos->
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
                                    "${documento.data.get("rolt")}",
                                    "${documento.data.get("telefono")}"
                                )
                            }




                            val intent = Intent(this, HomeT::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.left, R.anim.right)
                            finish()
                        }

                } else{
                    val baseDeDatos = Firebase.firestore
                    baseDeDatos.collection("alumnos")
                        .whereEqualTo("email",user?.email.toString())
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
                            val intent = Intent(this,Home::class.java)
                            startActivity(intent)
                            finish()
                        }

                    usuario = Usuario(
                        account?.email.toString(),
                        account?.displayName.toString(),
                        "s/n",
                        "s/n",
                        account?.photoUrl.toString(),


                        )


                }


            }else{
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }




        },4000)

    }

    private fun notificacion() {
        val notiDialogo = AlertDialog.Builder(this)
        notiDialogo.setTitle("ERROR")
        notiDialogo.setMessage("Se ha producido un error en la " +
                "Autenticacion del usuario")
        notiDialogo.setPositiveButton("ACEPTAR", null)
        notiDialogo.show()
    }
}