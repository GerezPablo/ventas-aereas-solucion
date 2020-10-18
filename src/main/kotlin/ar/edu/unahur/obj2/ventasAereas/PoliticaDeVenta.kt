package ar.edu.unahur.obj2.ventasAereas

interface PoliticaDeVenta {
    fun puedeVender(vuelo: Vuelo): Boolean
}


object Segura: PoliticaDeVenta {
    override fun puedeVender(vuelo: Vuelo) = vuelo.asientosLibres() >= 3
}

object Laxa: PoliticaDeVenta {
    override fun puedeVender(vuelo: Vuelo) = vuelo.asientosOcupados() <= (vuelo.asientosDisponibles() + 10)
}

object Porcentaje: PoliticaDeVenta {
    override fun puedeVender(vuelo: Vuelo) = vuelo.asientosOcupados() <= (vuelo.asientosDisponibles() * 1.1)
}

object Pandemia: PoliticaDeVenta {
    override fun puedeVender(vuelo: Vuelo) = false
}