package mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass

import androidx.databinding.ObservableField

data class Usuario(
    var email:String?="",
    val nombre:String?="",
    val paterno:String?="",
    val materno:String?="",
    var foto:String?="",
    val carrera:String?="",
    val grupo:String?="",
    val control:String?="",
    val rol:String?="alumno",
    val psw:String?=""
)
