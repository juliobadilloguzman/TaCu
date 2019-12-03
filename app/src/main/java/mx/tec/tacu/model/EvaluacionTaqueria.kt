package mx.tec.tacu.model

data class EvaluacionTaqueria(val idTaqueria: String, val atencion: Int, val precio: Int, val sabor: Int, val salsas: Int, val tamano: Int) {

    constructor(): this("",0,0,0,0,0)

}