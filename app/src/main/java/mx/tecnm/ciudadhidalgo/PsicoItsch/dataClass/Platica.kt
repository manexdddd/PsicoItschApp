package mx.tecnm.ciudadhidalgo.PsicoItsch.dataClass

import android.os.Parcel
import android.os.Parcelable

data class Platica(
    var id:String?="",
    var cupo:Int=0,
    var  descripcion:String?="",
    var  fecha:String?="",
    var  foto:String?="",
    var  hora:String?="",
    var  lugar:String?="",
    var  ponente:String?="",
    var  tema:String?="",

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
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
        parcel.writeString(id)
        parcel.writeInt(cupo)
        parcel.writeString(descripcion)
        parcel.writeString(fecha)
        parcel.writeString(foto)
        parcel.writeString(hora)
        parcel.writeString(lugar)
        parcel.writeString(ponente)
        parcel.writeString(tema)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Platica> {
        override fun createFromParcel(parcel: Parcel): Platica {
            return Platica(parcel)
        }

        override fun newArray(size: Int): Array<Platica?> {
            return arrayOfNulls(size)
        }
    }
}

