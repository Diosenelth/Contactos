package com.example.contactos

class ContactoModel {
    var id: String? = null
    var nombre: String? = null
    var apellido: String? = null
    var telefono: String? = null
    var correo: String? = null

    constructor(id: String?, nombre: String?, apellido: String?, telefono: String?, correo: String?) {
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.correo = correo
    }
}