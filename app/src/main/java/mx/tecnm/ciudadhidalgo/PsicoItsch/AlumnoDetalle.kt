package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Aviso


class AlumnoDetalle : AppCompatActivity() {

    private lateinit var  fote: ImageView
    private lateinit var  nombrec: TextView
    private lateinit var  carrerac: TextView
    private lateinit var  nombrepp: TextView
    private lateinit var  carrerapp: TextView
    private lateinit var  grupopp: TextView
    private lateinit var  controlpp: TextView
    private lateinit var  emailpp: TextView
    private lateinit var alumno:Alumno
    private lateinit var boton1:Button
    private lateinit var boton2:Button
    private lateinit var boton3:Button
    private lateinit var botondelete:Button
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno_detalle)


 nombrec  = findViewById(R.id.nombrealu)
        nombrepp = findViewById(R.id.nombrep)
        carrerapp = findViewById(R.id.carrerap)
        grupopp = findViewById(R.id.grupop)
        emailpp = findViewById(R.id.emailp)
        controlpp = findViewById(R.id.controlp)
        boton1 = findViewById(R.id.button1)
        boton2 = findViewById(R.id.button3)
        boton3 = findViewById(R.id.button2)
        botondelete= findViewById(R.id.btndelete)
        fote = findViewById(R.id.fotoperf)

      alumno  = intent.getParcelableExtra<Alumno>("alumno")!!

        if(alumno!=null) {
            Glide.with(this)
                .load(alumno.foto)

                .into(fote);
            nombrec.text = alumno.nombre+" "+alumno.paterno+ " "+alumno.materno

            nombrepp.text = alumno.nombre+" "+alumno.paterno+ " "+alumno.materno
            carrerapp.text= alumno.carrera
            grupopp.text = alumno.grupo
            emailpp.text = alumno.email
            controlpp.text = alumno.control


        }


        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {

            finish()

        }


        boton1.setOnClickListener{
            val intent = Intent(this,EditarAlumno::class.java)
            intent.putExtra("id", alumno)

            startActivity(intent)


        }
        boton2.setOnClickListener{
            val intent = Intent(this,KarnetAlumno::class.java)
            intent.putExtra("id", alumno)

            startActivity(intent)


        }


        botondelete.setOnClickListener{
            val db = FirebaseFirestore.getInstance()
            val url =alumno.foto.toString()
            val storage = Firebase.storage
            val imageRef = storage.getReferenceFromUrl(url)
// Get the document reference
            val docRef = db.collection("alumnos").document(alumno.email.toString())

// Delete the document
            docRef.delete()
                .addOnSuccessListener {
                    imageRef.delete().addOnSuccessListener {
                        Log.d(ContentValues.TAG, "onSuccess: deleted file")
                        val intent = Intent(this,Alumnos::class.java)
                        Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()


                    }.addOnFailureListener {
                        Log.d(ContentValues.TAG, "onFailure: did not delete file")
                    }

                }
                .addOnFailureListener {


                }

        }

        val bottomSheetDialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.modal_bottom_sheet_content, null)
        bottomSheetDialog.setContentView(view)

        val button1 = view.findViewById<Button>(R.id.bottomsheet_button)
        button1.setOnClickListener {
            val intent = Intent(this, Tutoria_individual::class.java)
            intent.putExtra("id", alumno)
            startActivity(intent)
            finish()
        }

        val button2 = view.findViewById<Button>(R.id.bottomsheet_button2)
        button2.setOnClickListener {
            // Handle button 2 click here
            val intent = Intent(this, Canalizacion_psicoRegistro::class.java)
            intent.putExtra("id", alumno)
            startActivity(intent)
            finish()

        }

        val button3 = view.findViewById<Button>(R.id.bottomsheet_button3)
        button3.setOnClickListener {
            // Handle button 3 click here
            val intent = Intent(this, Canalizacion_academica::class.java)
            intent.putExtra("id", alumno)
            startActivity(intent)
            finish()
        }

        boton3.setOnClickListener{
            showBottomSheetDialog(bottomSheetDialog)


        }

    }

    private fun showBottomSheetDialog(bottomSheetDialog: BottomSheetDialog) {

        bottomSheetDialog.show()
    }
}