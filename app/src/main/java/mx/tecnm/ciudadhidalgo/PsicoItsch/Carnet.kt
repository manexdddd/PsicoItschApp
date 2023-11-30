package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Carnet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carnet)

        // Obtén una referencia al botón
        val btnSubirArchivo: Button = findViewById(R.id.btnSubirArchivo)

        // Agrega un listener al botón
        btnSubirArchivo.setOnClickListener {
            // Llama a la función para mostrar el FileChooser
            showFileChooser()
        }
    }

    // Función para mostrar el FileChooser
    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Todos los tipos de archivos
        startActivityForResult(intent, FILE_CHOOSER_REQUEST_CODE)
    }

    companion object {
        // Código de solicitud para el FileChooser
        private const val FILE_CHOOSER_REQUEST_CODE = 123
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Aquí obtienes la URI del archivo seleccionado
            val selectedFileUri = data?.data
            // Ahora puedes trabajar con la URI del archivo...
        }
    }

}