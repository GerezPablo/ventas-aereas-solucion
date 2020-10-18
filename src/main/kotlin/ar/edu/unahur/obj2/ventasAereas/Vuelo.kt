package ar.edu.unahur.obj2.ventasAereas

import java.time.LocalDate

class Pasaje(val fechaDeVenta: LocalDate, val DNIPasajero: String)

class Avion (val cantAsientos: Int, val alturaCabina: Double, val peso: Double)


abstract class Vuelo (val fecha: LocalDate, val avion: Avion, val origen: String, val destino: String, val tiempoDeVuelo: Double,val precioEstandar: Double, val politicaDePrecio: PoliticaDePrecio) {
    val pasajes = mutableListOf<Pasaje>()

    abstract fun asientosDisponibles(): Int
    abstract fun pesoDeCarga(): Double

    fun asientosLibres() =  maxOf(0, asientosDisponibles() - asientosOcupados())
    fun asientosOcupados() = pasajes.size


    fun esRelajado() = avion.alturaCabina > 4.0 && asientosDisponibles() < 100
    fun precioDeVentaDePasaje() = politicaDePrecio.precioDeVenta(this)
    fun importeTotalGenerado() = asientosOcupados() * precioDeVentaDePasaje()
    fun pesoPasajeros() = asientosOcupados() * IATA.pesoEstandar
    fun pesoMaximo() = avion.peso + pesoPasajeros() + pesoDeCarga()
    fun viajaEnEsteVuelo(dni: String) = pasajes.any { it -> it.DNIPasajero == dni }
    fun recibirPasaje(pasaje: Pasaje) { pasajes.add(pasaje)}
}


class Normal( fecha: LocalDate, avion: Avion, origen: String, destino: String, tiempoDeVuelo: Double, precioEstandar: Double, politicaDePrecio: PoliticaDePrecio, val equipajeMaximo: Double):
        Vuelo(fecha, avion, origen, destino, tiempoDeVuelo, precioEstandar, politicaDePrecio) {

    override fun asientosDisponibles() = avion.cantAsientos
    override fun pesoDeCarga() =  asientosOcupados() * equipajeMaximo
}

class Carga( fecha: LocalDate, avion: Avion, origen: String, destino: String, tiempoDeVuelo: Double, precioEstandar: Double, politicaDePrecio: PoliticaDePrecio, val pesoCarga: Double):
    Vuelo(fecha, avion, origen, destino, tiempoDeVuelo, precioEstandar, politicaDePrecio) {
    override fun asientosDisponibles() = 30
    override fun pesoDeCarga() =  pesoCarga + 700.0
}

class Charter( fecha: LocalDate, avion: Avion, origen: String, destino: String, tiempoDeVuelo: Double, precioEstandar: Double, politicaDePrecio: PoliticaDePrecio, val asientosPreventa: Int):
        Vuelo(fecha, avion, origen, destino, tiempoDeVuelo, precioEstandar, politicaDePrecio) {
    override fun asientosDisponibles() = avion.cantAsientos - 25 - asientosPreventa
    override fun pesoDeCarga() = 5000.0
}