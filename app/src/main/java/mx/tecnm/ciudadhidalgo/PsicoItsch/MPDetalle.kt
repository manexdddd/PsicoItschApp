package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Aviso
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Platica
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class MPDetalle : AppCompatActivity() {
    private lateinit var  fotoa : ImageView
    private lateinit var temaa : TextView
    private lateinit var desca : TextView
    private lateinit var fechaa : TextView
    private lateinit var cupo : TextView
    private lateinit var contenide : TextView

    private lateinit var inscribir: Button
    private lateinit var platica: Platica
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpdetalle)
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




        fotoa = findViewById(R.id.fotoplatica)
        temaa = findViewById(R.id.tema)
        desca = findViewById(R.id.descripcioplatica)
        contenide = findViewById(R.id.contenido)
        fechaa = findViewById(R.id.fecha_platica)

        cupo = findViewById(R.id.cupo)



        platica = intent.getParcelableExtra<Platica>(
            "platica"
        )!!

        if(platica!=null) {
            Glide.with(this)
                .load(platica.foto)

                .into(fotoa);
            temaa.text = platica.tema
            desca.text = platica.descripcion
            contenide.text = "La platica se dara a cabo el "+platica.fecha+" En "+platica.lugar+" a las "+platica.hora+
                    " Impartida por el ponente: "+platica.ponente
            cupo.text = "Con un Cupo limitado de "+ platica.cupo

            fechaa.text = " \uD83D\uDDD3 "+platica.fecha+" "+platica.lugar


        }

        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {
            platica.cupo = 0
            platica.id = ""
            platica.foto = ""
            platica.hora = ""
            platica.tema = ""
            platica.descripcion = ""
            platica.fecha = ""
            platica.lugar = ""
            platica.ponente = ""
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

    override fun onBackPressed() {
        // Your custom back button logic goes here
        // For example, you can show a confirmation dialog before exiting the app
        platica.cupo = 0
        platica.id = ""
        platica.foto = ""
        platica.hora = ""
        platica.tema = ""
        platica.descripcion = ""
        platica.fecha = ""
        platica.lugar = ""
        platica.ponente = ""
        finish()
    }
    private fun notificacion() {
        val notiDialogo = AlertDialog.Builder(this)
        notiDialogo.setTitle("ERROR")
        notiDialogo.setMessage("PLatica Llena Intenta mas tarde")
        notiDialogo.setPositiveButton("ACEPTAR", null)
        notiDialogo.show()
    }

}