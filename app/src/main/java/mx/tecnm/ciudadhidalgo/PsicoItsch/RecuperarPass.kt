package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RecuperarPass : AppCompatActivity() {

    private lateinit var btnRecuperar:Button
    private lateinit var correo:TextInputLayout
    private lateinit var btnRegresar:Button
    private var auth = FirebaseAuth.getInstance()
    var email =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_pass)

        correo = findViewById(R.id.email_recuperar)
        btnRecuperar = findViewById(R.id.btnRecuperarPass)
        btnRegresar = findViewById(R.id.btnRegresarRecup)

        btnRecuperar.setOnClickListener{
            email = correo.editText?.text.toString()
            if(email.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    correo.error = null
                    recuperarPassword()
                } else {
                    correo.error = "Correo Invalido"
                }
            }else{
                correo.error = "El correo es un campo obligatorio"
            }
        }
        btnRegresar.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
    }
    private fun recuperarPassword(){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{
                Toast.makeText(this,
                    "Se ha enviado $email el correo de recuperacion",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
        }
    }
}