package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import java.text.DateFormat
import java.util.Calendar

class Canalizacion_academica : AppCompatActivity() {
    private lateinit var  alumno: Alumno
    private lateinit var  tipo: TextInputLayout
    private lateinit var  asignatura: TextInputLayout
    private lateinit var  status: TextInputLayout
    private lateinit var  fecha: TextView
    private lateinit var  buton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canalizacion_academica)
        tipo = findViewById(R.id.tipoacad)
        fecha= findViewById(R.id.fechaacad)
        buton= findViewById(R.id.btnacademica)
        asignatura= findViewById(R.id.asigacad)
        status= findViewById(R.id.estatuscad)
        fecha= findViewById(R.id.fechaacad)
        var date:String=""
        var emaild:String=""
        alumno  = intent.getParcelableExtra<Alumno>("id")!!

        if(alumno!=null) {
            emaild= alumno.email.toString()
        }

        fecha.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .build()

            picker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val selectedDate = DateFormat.getDateInstance().format(calendar.time)
                fecha.setText(selectedDate)
                date = selectedDate
            }

            picker.show(supportFragmentManager, picker.toString())
        }





        buton.setOnClickListener {
            val db = Firebase.firestore
            val docRef = db.collection("alumnos").document(emaild)

            val map = mapOf("fecha" to date, "asignatura" to asignatura.editText?.text.toString(),"estatus" to
            status.editText?.text.toString(), "tipo" to tipo.editText?.text.toString(), "tutor" to
                    tutor.nombret.toString()+" "+ tutor.paternot.toString()+" " + tutor.maternot.toString())

            docRef.update("academicas", FieldValue.arrayUnion(map))
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!")
                    val intent = Intent(this, Alumnos::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating document", e) }
        }




    }
}