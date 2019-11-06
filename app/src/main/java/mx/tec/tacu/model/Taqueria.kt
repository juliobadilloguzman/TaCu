package mx.tec.tacu.model

data class Taqueria(val nombre: String, val eslogan: String, val precioOrden: String, val precioUnitario: String) {

    constructor(): this("", "", "", "")

}