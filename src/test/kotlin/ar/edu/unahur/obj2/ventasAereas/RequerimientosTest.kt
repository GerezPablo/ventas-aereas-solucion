package ar.edu.unahur.obj2.ventasAereas

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldNotBeFalse
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class RequerimientosTest: DescribeSpec({
    val empresa = Empresa()
    empresa.politicaDeVenta = Laxa


    val cumbanchaVolante = Avion(500, 4.5, 80000.0)
    val avionazo = Avion(5, 2.0, 50000.0)

    val hoy = LocalDate.now()
    val ayer = hoy.minusDays(1)
    val mañana = hoy.plusDays(1)

    fun meterGente(vuelo: Vuelo, cant: Int) {
        for (i in 1..cant) {
            empresa.venderPasaje(vuelo, hoy, "123456${i}")
        }
    }

    val vueloRelajado = Carga(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, ventaAnticipada, 150.0)
    val vueloNoRelajado = Normal(hoy, avionazo, "Springfield", "Ciudad Gotica", 10.0, 500.0, estricta, 8.0)


    describe("Es relajado") {
        vueloNoRelajado.esRelajado().shouldBeFalse()
        vueloRelajado.esRelajado().shouldBeTrue()
    }
    describe("Importe Total") {

        meterGente(vueloNoRelajado, 5)
        vueloNoRelajado.importeTotalGenerado().shouldBe(2500.0)

        meterGente(vueloRelajado, 10)
        vueloRelajado.importeTotalGenerado().shouldBe(1500.0)
    }
    describe("Peso Maximo") {
        meterGente(vueloNoRelajado, 5)
        vueloNoRelajado.pesoMaximo().shouldBe(50065)

        meterGente(vueloRelajado, 5)
        vueloRelajado.pesoMaximo().shouldBe(80875)
    }
    describe("Asientos disponibles entre hoy y mañana") {
        empresa.venderPasaje(vueloNoRelajado, hoy, "12345678")
        empresa.asientosDisponiblesEntre(ayer, mañana).shouldBe(5)

        empresa.venderPasaje(vueloRelajado, hoy, "12345678")
        empresa.asientosDisponiblesEntre(ayer, mañana).shouldBe(35)
    }
    describe("Son Compas") {
        val vuelo1 = Carga(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, ventaAnticipada, 150.0)
        val vuelo2 = Carga(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, ventaAnticipada, 150.0)
        val vuelo3 = Carga(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, ventaAnticipada, 150.0)

        val personita1 = "12345678"
        val personita2 = "22345678"

        empresa.venderPasaje(vuelo1,mañana, personita1)
        empresa.venderPasaje(vuelo1,mañana, personita2)

        empresa.venderPasaje(vuelo2,mañana, personita1)
        empresa.venderPasaje(vuelo2,mañana, personita2)

        empresa.venderPasaje(vuelo3,mañana, personita1)

        empresa.sonCompanierys(personita1, personita2).shouldBeFalse()

        empresa.venderPasaje(vuelo3, mañana, personita2)

        empresa.sonCompanierys(personita1, personita2).shouldBeTrue()
    }
})