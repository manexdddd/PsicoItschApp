package mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass

import android.os.Parcel
import android.os.Parcelable

data class Sesiones(
    val fecha:String?="",
    val hora:String?="",
    val nombreAlumno:String?="",
    val nombreMedico:String?="",
    val numeroC:String?="",
    val tipo:String?="",
    val foto:String?="",
) : Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(fecha)
        parcel.writeString(hora)
        parcel.writeString(nombreAlumno)
        parcel.writeString(nombreMedico)
        parcel.writeString(numeroC)
        parcel.writeString(tipo)
        parcel.writeString(foto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sesiones> {
        override fun createFromParcel(parcel: Parcel): Sesiones {
            return Sesiones(parcel)
        }

        override fun newArray(size: Int): Array<Sesiones?> {
            return arrayOfNulls(size)
        }
    }
}
