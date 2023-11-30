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

class Canalizacion_psicoRegistro : AppCompatActivity() {
    private lateinit var  fechas:TextView
    private lateinit var  fecha:TextView
    private lateinit var  tipo:TextInputLayout
    private lateinit var  obs:TextInputLayout
    private lateinit var  area:TextInputLayout
    private lateinit var  alumno:Alumno
    private lateinit var  buton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canalizacion_psico_registro)
        var emaild:String=""
        var date1:String=""
        var date2:String=""
        alumno  = intent.getParcelableExtra<Alumno>("id")!!

        if(alumno!=null) {
            emaild= alumno.email.toString()
        }
        fechas = findViewById(R.id.fechasol)
        fecha= findViewById(R.id.fechaat)
        obs= findViewById(R.id.observaciones)
        area= findViewById(R.id.areacanal)
        buton= findViewById(R.id.btncanal)


        fechas.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha de solicitud")
                .build()

            picker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val selectedDate = DateFormat.getDateInstance().format(calendar.time)
                fechas.setText(selectedDate)
                date1 = selectedDate
            }

            picker.show(supportFragmentManager, picker.toString())
        }

        fecha.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha de solicitud")
                .build()

            picker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val selectedDate = DateFormat.getDateInstance().format(calendar.time)
                fecha.setText(selectedDate)
                date2 = selectedDate
            }

            picker.show(supportFragmentManager, picker.toString())
        }



        buton.setOnClickListener {
            val db = Firebase.firestore
            val docRef = db.collection("alumnos").document(emaild)

            val map = mapOf("fecha" to date1, "fechas" to date2,
                "observaciones" to
                    obs.editText?.text.toString(), "tutor" to
                    tutor.nombret.toString()+" "+ tutor.paternot.toString()+" " + tutor.maternot.toString(),
            "area" to
                    area.editText?.text.toString())

            docRef.update("canalizacion", FieldValue.arrayUnion(map))
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!")
                    val intent = Intent(this, Alumnos::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating document", e) }
        }


    }
}