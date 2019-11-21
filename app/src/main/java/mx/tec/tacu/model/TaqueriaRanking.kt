package mx.tec.tacu.model

import java.io.Serializable

data class TaqueriaRanking(val posicion: Int, val nombre: String, val calificacion: Double){

    constructor() : this(0, "", 0.0)

}