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
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Tutor
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class TutorPerfil : AppCompatActivity() {
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
        setContentView(R.layout.activity_tutor_perfil)
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


        fote = findViewById(R.id.roundedfoto)
        carrerac = findViewById(R.id.carrera_perfil)
        nombrec = findViewById(R.id.nombre_perfil)

        nombrepp = findViewById(R.id.nombrep)
        carrerapp = findViewById(R.id.carrerap)
        grupopp = findViewById(R.id.grupop)
        emailpp = findViewById(R.id.emailp)
        controlpp = findViewById(R.id.controlp)
        btnnext = findViewById(R.id.next)
        btnnext1 = findViewById(R.id.next2)
        btnnext2 = findViewById(R.id.next3)
        logou= findViewById(R.id.logp)
        Glide.with(this).load(tutor.fotot).into(fote)
        carrerac.text = tutor.carrerat
        nombrec.text = tutor.nombret
        nombrepp.text = tutor.nombret+" "+tutor.paternot+ " "+tutor.maternot
        carrerapp.text= tutor.carrerat
        grupopp.text = tutor.grupot
        emailpp.text = tutor.emailt
        controlpp.text = tutor.division




        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {
            val intent = Intent(this,HomeT::class.java)
            startActivity(intent)
            finish()

        }

        btnnext.setOnClickListener{
            val intent = Intent(this, EditarTutorPerfil::class.java)
            startActivity(intent)

        }

        btnnext1.setOnClickListener{
            val intent = Intent(this, AgregarAlumno::class.java)
            startActivity(intent)

        }



        btnnext2.setOnClickListener{
            val intent = Intent(this, Alumnos::class.java)
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

    override fun onBackPressed() {
        finish()
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