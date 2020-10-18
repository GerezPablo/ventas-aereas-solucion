package ar.edu.unahur.obj2.ventasAereas

import java.time.LocalDate

class Empresa {
    lateinit var politicaDeVenta: PoliticaDeVenta
    val vuelos = mutableListOf<Vuelo>()

    fun puedeVender(vuelo: Vuelo) = politicaDeVenta.puedeVender(vuelo)

    fun venderPasaje(vuelo: Vuelo, fechaDeVenta: LocalDate, DNIPasajero: String) {
        if(!puedeVender(vuelo)) { throw Exception("No se pueden vender pasajes para ese vuelo... :(") }
        else {
            vuelos.add(vuelo)
            vuelo.recibirPasaje(Pasaje(fechaDeVenta, DNIPasajero))
        }
    }

    fun vuelosEnLosQueViaja(dni: String) = vuelos.filter { it -> it.viajaEnEsteVuelo(dni) }

    fun cuandoViajaA(dni: String, destino: String) = vuelosEnLosQueViaja(dni).filter{ it -> it.destino == destino}.map { it -> it.fecha }

    fun vuelosEntre(fechainicio: LocalDate, fechaFin: LocalDate) = vuelos.filter {it -> it.fecha.isAfter(fechainicio) && it.fecha.isBefore(fechaFin)}

    fun asientosDisponiblesEntre(fechainicio: LocalDate, fechaFin: LocalDate) = vuelosEntre(fechainicio, fechaFin).sumBy { it -> it.asientosDisponibles() }
    fun sonCompanierys(dni1: String, dni2: String) = (vuelosEnLosQueViaja(dni1) intersect  vuelosEnLosQueViaja(dni2)).size >= 3
}



/*
Saber para qué fecha o fechas, una determinada persona (que se identifica por su DNI) tiene sacado pasaje para un determinado destino. P.ej. en qué fechas la persona con DNI 74404949 tiene sacado pasaje a Tahití.
Conocer el total de asientos libres para un destino entre dos fechas.
Y otra más: si dos personas son compañeras, o sea, comparten al menos 3 vuelos
 */