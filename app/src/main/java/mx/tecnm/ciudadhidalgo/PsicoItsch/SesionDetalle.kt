package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Sesiones
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario

class SesionDetalle : AppCompatActivity() {
    private lateinit var sesion: Sesiones
    private lateinit var hora: TextView
    private lateinit var slumno: TextView
    private lateinit var  fecha:TextView
    private lateinit var  time:TextView
    private lateinit var  fote:ImageView
    private lateinit var  psicologa:TextView
    private lateinit var  tipo:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setContentView(R.layout.activity_sesion_detalle)
        fecha  = findViewById(R.id.fecha_Sesion)
                fote  = findViewById(R.id.fotosesion)
        psicologa  = findViewById(R.id.detalle)
        tipo  = findViewById(R.id.tipo)
        time  = findViewById(R.id.time)
        slumno = findViewById(R.id.nombresesion)
        hora = findViewById(R.id.hora)
        sesion = intent.getParcelableExtra<Sesiones>("sesion")!!

        if(sesion!=null) {
            Glide.with(this)
                .load(sesion.foto)
                .into(fote);
            Log.e(TAG,sesion.foto.toString())
            fecha.text = "\uD83D\uDCC5"+sesion.fecha
            hora.text = "\uD83D\uDD64 Hora de sesion: "+sesion.hora
            time.text = "Tu sesion esta programada para " +sesion.fecha
            psicologa.text ="Tu Psicologa sera "+ sesion.nombreMedico+ " Ubicada en el edificio A Planta baja \uD83C\uDFE2"
              slumno.text ="Para "+sesion.nombreAlumno+" con No. Control "+sesion.numeroC
            tipo.text ="Tipo de sesion: " +sesion.tipo}

        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {

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