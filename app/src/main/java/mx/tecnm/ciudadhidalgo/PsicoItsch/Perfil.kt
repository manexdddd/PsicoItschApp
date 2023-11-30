package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class Perfil : AppCompatActivity() {
    private lateinit var  fote:ImageView
    private lateinit var  nombrec: TextView
    private lateinit var  carrerac: TextView

    private lateinit var  nombrepp: TextView
    private lateinit var  carrerapp: TextView
    private lateinit var  grupopp: TextView
    private lateinit var  controlpp: TextView
    private lateinit var  emailpp: TextView
    private lateinit var  logou: TextView
    private lateinit var  btnnext: ImageButton
    private lateinit var  btnnext1: ImageButton
    private lateinit var  btnnext2: ImageButton
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
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


        fote = findViewById(R.id.roundedfoto)
        carrerac = findViewById(R.id.carrera_perfil)
        nombrec = findViewById(R.id.nombre_perfil)

        nombrepp = findViewById(R.id.nombrep)
        carrerapp = findViewById(R.id.carrerap)
        grupopp = findViewById(R.id.grupop)
        emailpp = findViewById(R.id.emailp)
        controlpp = findViewById(R.id.controlp)
            btnnext = findViewById(R.id.next)
        btnnext1 = findViewById(R.id.next3)
        btnnext2 = findViewById(R.id.next2)
      logou= findViewById(R.id.logp)
        Glide.with(this).load(usuario.foto).into(fote)
        carrerac.text = "Ing. "+usuario.carrera
        nombrec.text = usuario.nombre
        nombrepp.text = usuario.nombre
        carrerapp.text= usuario.carrera
        grupopp.text = usuario.grupo
        emailpp.text = usuario.email
         controlpp.text = usuario.control




        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
            finish()

        }

   btnnext.setOnClickListener{
       val intent = Intent(this, EditarPerfil::class.java)
       startActivity(intent)

   }


        btnnext1.setOnClickListener{
            val intent = Intent(this, Credential::class.java)
            startActivity(intent)

        }

        btnnext2.setOnClickListener{
            val intent = Intent(this, Carnet_vista::class.java)
            startActivity(intent)

        }

        logou.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            Firebase.auth.signOut()
            //cerrar sesion en google
            val sesiongoogle = GoogleSignIn.getClient(this, gso)
            sesiongoogle.signOut().addOnCompleteListener(this) {
                // La sesión de Google se cerró con éxito

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()

            }



            val intent = Intent(this, Login::class.java)
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