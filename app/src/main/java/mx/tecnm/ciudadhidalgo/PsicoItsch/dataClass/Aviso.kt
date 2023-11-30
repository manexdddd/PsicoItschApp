package mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass

import android.os.Parcel
import android.os.Parcelable

data class Aviso(
    val autor:String?="",
    val contenido:String?="",
    val descripcion:String?="",
    val fecha:String?="",
    val foto:String?="",
                  val lugar:String?="",
                  val titulo:String?="",




): Parcelable {
    constructor(parcel: Parcel) : this(

        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
                parcel.readString(),
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(autor)
        parcel.writeString(contenido)
        parcel.writeString(descripcion)
        parcel.writeString(fecha)
        parcel.writeString(foto)
        parcel.writeString(lugar)
        parcel.writeString(titulo)

    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Aviso> {
        override fun createFromParcel(parcel: Parcel): Aviso {
            return Aviso(parcel)
        }
        override fun newArray(size: Int): Array<Aviso?> {
            return arrayOfNulls(size)
        }
    }
}