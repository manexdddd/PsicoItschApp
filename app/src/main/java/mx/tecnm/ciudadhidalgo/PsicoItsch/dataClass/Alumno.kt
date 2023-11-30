package mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableField

data class Alumno(
    var id:String?="",
    var email:String?="",
    val nombre:String?="",
    val paterno:String?="",
    val materno:String?="",
    var foto:String?="",
    val carrera:String?="",
    val grupo:String?="",
    val control:String?="",
    val rol:String?="alumno",
    val psw:String?="",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(nombre)
        parcel.writeString(paterno)
        parcel.writeString(materno)
        parcel.writeString(foto)
        parcel.writeString(carrera)
        parcel.writeString(grupo)
        parcel.writeString(control)
        parcel.writeString(rol)
        parcel.writeString(psw)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alumno> {
        override fun createFromParcel(parcel: Parcel): Alumno {
            return Alumno(parcel)
        }

        override fun newArray(size: Int): Array<Alumno?> {
            return arrayOfNulls(size)
        }
    }
}