package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass.Usuario
import java.text.DateFormat
import java.util.Calendar

class Sesion : AppCompatActivity() {
    private lateinit var nombre: TextInputLayout
    private lateinit var grupo: TextInputLayout
    private lateinit var control: TextInputLayout
    private lateinit var telefono_SesionSolicitar: TextInputLayout
    private lateinit var carrera: TextInputLayout
    private lateinit var tutorsesion: TextInputLayout
    private lateinit var motivo: TextInputLayout
    private lateinit var obs: TextInputLayout
    private lateinit var hora: TextView
    private lateinit var fecha: TextView
    private lateinit var sesion: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion)
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

          var date:String =""
        var tiempo:String =""
        nombre = findViewById(R.id.nombre_alumnod)
        grupo = findViewById(R.id.observaciones_canalizacionPsicologicaxd)
        carrera = findViewById(R.id.grupoxd)
        control = findViewById(R.id.numeroControlxd)
        sesion = findViewById(R.id.btnsesion)
        telefono_SesionSolicitar = findViewById(R.id.telefono_SesionSolicitar)
        tutorsesion = findViewById(R.id.nombre_tutor)
        hora = findViewById(R.id.horaxd)
        fecha = findViewById(R.id.fechaat)
        motivo = findViewById(R.id.motivoSolicitud)
        obs = findViewById(R.id.obs)
       /* nombre_alumno,observaciones_canalizacionPsicologica  grupo, numeroControl*/

        nombre.editText?.setText(usuario.nombre+" "+usuario.paterno+" "+ usuario.materno)
        grupo.editText?.setText(usuario.grupo)
        carrera.editText?.setText(usuario.carrera)
        control.editText?.setText(usuario.control)


        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val navigationIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.baseline_arrow_back_24, null)
        toolbar.setNavigationIcon(navigationIcon)


        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()

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
                date = selectedDate
            }

            picker.show(supportFragmentManager, picker.toString())
        }

        hora.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Appointment time")
                .build()

            picker.addOnPositiveButtonClickListener {
                val selectedHour = picker.hour
                val selectedMinute = picker.minute
                val selectedTime = "${selectedHour.toString().padStart(2, '0')}:${selectedMinute.toString().padStart(2, '0')}"
                // Save the selected time in a variable
                tiempo = selectedTime
                // Set the selected time in your TextView
                hora.text = selectedTime
            }

            picker.show(supportFragmentManager, "tag")
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_2 -> {
                    val intent = Intent(this, Platicas::class.java)
                    startActivity(intent)
                    false
                }

                R.id.page_3 -> {

                   true
                }

                R.id.page_5 -> {
                    val intent = Intent(this, Perfil::class.java)

                    startActivity(intent)
                    false

                }

                else -> false
            }
        }
        bottomNav .selectedItemId = R.id.page_3




        sesion.setOnClickListener {
            val db = Firebase.firestore
            val solicitudes = db.collection("solicitudes")

            val data = hashMapOf(
                "canalizacion" to "voluntario",
                "carrera" to carrera.editText?.text.toString(),
                "fecha" to date,
                "grupo" to grupo.editText?.text.toString(),
                "motivo" to motivo.editText?.text.toString(),
                "nombre" to nombre.editText?.text.toString(),
                "numeroC" to control.editText?.text.toString(),
                "observaciones" to obs.editText?.text.toString(),
                "telefono" to telefono_SesionSolicitar.editText?.text.toString(),
                "tutor" to tutorsesion.editText?.text.toString(),
                "hora" to tiempo,
            )

            solicitudes.add(data)
                .addOnSuccessListener { documentReference ->
                    val toast = Toast.makeText(this, "Solicitud enviada correctamente", Toast.LENGTH_SHORT)
                    toast.show()
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
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