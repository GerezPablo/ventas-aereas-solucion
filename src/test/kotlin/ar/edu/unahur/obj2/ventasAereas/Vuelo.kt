package ar.edu.unahur.obj2.ventasAereas

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.time.LocalDate


class VueloTest : DescribeSpec({
  describe("Vuelos tests") {
    val empresa = Empresa()
    empresa.politicaDeVenta = Laxa
    val cumbanchaVolante = Avion(500, 3.0, 80000.0)

    val hoy = LocalDate.now()
    fun meterGente(vuelo: Vuelo, cant: Int) {
      for (i in 1..cant) {
        empresa.venderPasaje(vuelo, hoy, "123456${i}")
      }
    }

    val vueloNormal = Normal(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, estricta, 8.0)
    val vueloCarga = Carga(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, ventaAnticipada, 500.0)
    val vueloCharter = Charter(hoy, cumbanchaVolante, "Springfield", "Ciudad Gotica", 10.0, 500.0, remate, 25)

    describe("capacidad") {
      it("Capacidad de aviones sin gente") {
        vueloNormal.asientosLibres().shouldBe(500)
        vueloCarga.asientosLibres().shouldBe(30)
        vueloCharter.asientosLibres().shouldBe(450)
      }

      it("Capacidad de aviones con gente") {
        meterGente(vueloNormal, 50)
        vueloNormal.asientosLibres().shouldBe(450)

        meterGente(vueloCarga, 20)
        vueloCarga.asientosLibres().shouldBe(10)

        meterGente(vueloCharter, 50)
        vueloCharter.asientosLibres().shouldBe(400)
      }
  }
    describe("Politicas de precio") {

      it("Politicas para Vuelo Normal") {
        estricta.precioDeVenta(vueloNormal).shouldBe(500)
        ventaAnticipada.precioDeVenta(vueloNormal).shouldBe(150)
        remate.precioDeVenta(vueloNormal).shouldBe(125)
      }
      it("Politicas para Vuelo de carga") {
        estricta.precioDeVenta(vueloCarga).shouldBe(500)
        ventaAnticipada.precioDeVenta(vueloCarga).shouldBe(150)
        remate.precioDeVenta(vueloCarga).shouldBe(250)
      }
      it("Politicas para vuelo charter") {
        estricta.precioDeVenta(vueloCharter).shouldBe(500)
        ventaAnticipada.precioDeVenta(vueloNormal).shouldBe(150)
        remate.precioDeVenta(vueloNormal).shouldBe(125)
      }
    }

    describe("Politicas de venta") {

      it("Segura") {
        empresa.politicaDeVenta = Segura

        meterGente(vueloCarga, 27)
        empresa.puedeVender(vueloCarga).shouldBeTrue()
        meterGente(vueloCarga, 1)
        empresa.puedeVender(vueloCarga).shouldBeFalse()

        meterGente(vueloNormal, 497)
        empresa.puedeVender(vueloNormal).shouldBeTrue()
        meterGente(vueloNormal, 1)
        empresa.puedeVender(vueloNormal).shouldBeFalse()

        meterGente(vueloCharter, 447)
        empresa.puedeVender(vueloCharter).shouldBeTrue()
        meterGente(vueloCharter, 1)
        empresa.puedeVender(vueloCharter).shouldBeFalse()
      }

      it("Laxa") {
        empresa.politicaDeVenta = Laxa

        meterGente(vueloCarga, 40)
        empresa.puedeVender(vueloCarga).shouldBeTrue()
        meterGente(vueloCarga, 1)
        empresa.puedeVender(vueloCarga).shouldBeFalse()

        meterGente(vueloNormal, 510)
        empresa.puedeVender(vueloNormal).shouldBeTrue()
        meterGente(vueloNormal, 1)
        empresa.puedeVender(vueloNormal).shouldBeFalse()

        meterGente(vueloCharter, 460)
        empresa.puedeVender(vueloCharter).shouldBeTrue()
        meterGente(vueloCharter, 1)
        empresa.puedeVender(vueloCharter).shouldBeFalse()
      }

      it("Porcentaje") {
        empresa.politicaDeVenta = Porcentaje

        meterGente(vueloCarga, 33)
        empresa.puedeVender(vueloCarga).shouldBeTrue()
        meterGente(vueloCarga, 1)
        empresa.puedeVender(vueloCarga).shouldBeFalse()

        meterGente(vueloNormal, 550)
        empresa.puedeVender(vueloNormal).shouldBeTrue()
        meterGente(vueloNormal, 1)
        empresa.puedeVender(vueloNormal).shouldBeFalse()

        meterGente(vueloCharter, 495)
        empresa.puedeVender(vueloCharter).shouldBeTrue()
        meterGente(vueloCharter, 1)
        empresa.puedeVender(vueloCharter).shouldBeFalse()
      }

      it("Pandemia") {
        empresa.politicaDeVenta = Pandemia
        empresa.puedeVender(vueloCarga).shouldBeFalse()
        empresa.puedeVender(vueloNormal).shouldBeFalse()
        empresa.puedeVender(vueloCharter).shouldBeFalse()
      }
    }
  }
})
