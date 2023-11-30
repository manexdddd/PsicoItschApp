package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Alumno
import java.text.DateFormat
import java.util.Calendar

class Tutoria_individual : AppCompatActivity() {
    private lateinit var  alumno: Alumno
    private lateinit var  tema: TextInputLayout
    private lateinit var  fecha:TextView
    private lateinit var  buton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tutoria_individual)
        var emaild:String=""
        var date:String=""
        alumno  = intent.getParcelableExtra<Alumno>("id")!!

        if(alumno!=null) {
             emaild= alumno.email.toString()
        }
        tema = findViewById(R.id.temaindividual)
        fecha= findViewById(R.id.fechaindividual)
       buton= findViewById(R.id.btnindividual)


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

            val map = mapOf("fecha" to date, "tema" to tema.editText?.text.toString(),"tutor" to tutor.nombret+ " "
                    +tutor.paternot+ " "+ tutor.maternot)

            docRef.update("entrevista", FieldValue.arrayUnion(map))
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!")
                    val intent = Intent(this, Alumnos::class.java)
                    startActivity(intent)
                finish()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        }


    }
}