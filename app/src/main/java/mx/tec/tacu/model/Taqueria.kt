package mx.tec.tacu.model

import com.google.firebase.firestore.GeoPoint

data class Taqueria(val nombre: String, val telefono: String, val descripcion: String, val calificacion: Double, val horario: String, val imagen: String, val latitud: Double, val longitud: Double) {

    constructor(): this("", "", "", 0.0, "", "", 0.0, 0.0)

}