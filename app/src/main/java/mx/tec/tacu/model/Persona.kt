package mx.tec.tacu.model

data class Persona(val id: String, val nombre: String, val apellido: String, val correo: String, val sexo: String, val telefono: String,
                   val nacimiento: String, val creacion: String) {

    constructor() : this("", "", "", "", "", "", "", "")
}