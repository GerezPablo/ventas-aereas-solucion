package ar.edu.unahur.obj2.ventasAereas

interface PoliticaDePrecio {
    fun precioDeVenta(vuelo: Vuelo): Double
}

object estricta: PoliticaDePrecio {
    override fun precioDeVenta(vuelo: Vuelo) = vuelo.precioEstandar
}

object ventaAnticipada: PoliticaDePrecio {
    override fun precioDeVenta(vuelo: Vuelo) = when {
        vuelo.asientosOcupados() < 40 -> { vuelo.precioEstandar * 0.30 }
        vuelo.asientosOcupados() in 40..79 -> { vuelo.precioEstandar * 0.60 }
        else -> { vuelo.precioEstandar }
    }
}

object  remate: PoliticaDePrecio {
    override fun precioDeVenta(vuelo: Vuelo) = if (vuelo.asientosDisponibles() > 30) vuelo.precioEstandar * 0.25 else vuelo.precioEstandar * 0.50
}

