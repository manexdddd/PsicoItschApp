package mx.tecnm.ciudadhidalgo.PsicoItsch

import android.app.Application
import com.google.android.material.color.DynamicColors

class PsicoItsch: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}