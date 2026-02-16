package net_alberto.rfc

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import rfc.composeapp.generated.resources.Res
import rfc.composeapp.generated.resources.compose_multiplatform

object RFCGenerator {
    fun generarRFC(
        nombre: String,
        apellidoP: String,
        apellidoM: String,
        fecha: String
    ): String {
        var rfc = "XXXX000000XXX"
        if (apellidoP.isNotEmpty()) {
            rfc = apellidoP[0].uppercaseChar() + rfc.substring(1)
        }

        if (apellidoP.length > 1) {
            val vocal = apellidoP.uppercase()
                .substring(1)
                .find { it in "AEIOU" }
            if (vocal != null) {
                rfc = rfc.substring(0,1) + vocal + rfc.substring(2)
            }
        }

        if (apellidoM.isNotEmpty()) {
            rfc = rfc.substring(0,2) +
                    apellidoM[0].uppercaseChar() +
                    rfc.substring(3)
        }

        if (nombre.isNotEmpty()) {
            rfc = rfc.substring(0,3) +
                    nombre[0].uppercaseChar() +
                    rfc.substring(4)
        }

        if (fecha.length == 10) {
            val partes = fecha.split("/")
            val yy = partes[2].substring(2,4)
            val mm = partes[1]
            val dd = partes[0]
            val fechaRFC = yy + mm + dd
            rfc = rfc.substring(0,4) +
                    fechaRFC +
                    "XXX"
        }

        return rfc
    }
}

object Validaciones {

    fun soloLetras(texto: String): Boolean {
        for (letra in texto) {
            if (!letra.isLetter() && letra != ' ') {
                return false
            }
        }
        return true
    }

    fun fechaValida(fecha: String): Boolean {
        if (fecha.length != 10) return false
        if (fecha[2] != '/' || fecha[5] != '/') return false
        return true
    }
}


@Composable
@Preview
fun App() {

    var nombre by remember { mutableStateOf("") }
    var apellidoP by remember { mutableStateOf("") }
    var apellidoM by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("")}
    var Dialogo by remember { mutableStateOf( false)}

    val rfc = RFCGenerator.generarRFC(
        nombre,
        apellidoP,
        apellidoM,
        fecha
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Generador de RFC")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nombre,
            onValueChange = {
                if(Validaciones.soloLetras(it)){
                    nombre = it
                }else{
                    mensajeError = "Solo se permiten letras"
                    Dialogo = true
                }
            },
            label = { Text("Nombre") }
        )

        TextField(
            value = apellidoP,
            onValueChange = {
                if(Validaciones.soloLetras(it)){
                    apellidoP = it
                }else{
                    mensajeError = "Solo se permiten letras"
                    Dialogo = true
                }
            },
            label = { Text("Apellido Paterno") }
        )

        TextField(
            value = apellidoM,
            onValueChange = {
                if(Validaciones.soloLetras(it)){
                    apellidoM = it
                }else{
                    mensajeError = "Solo se permiten letras"
                    Dialogo = true
                }
            },
            label = { Text("Apellido Materno") }
        )

        TextField(
            value = fecha,
            onValueChange = {
                if (it.length <= 10) {
                    fecha = it
                    if (it.length == 10) {
                        if (!Validaciones.fechaValida(it)) {
                            mensajeError = "Formato correcto DD/MM/AAAA"
                            Dialogo = true
                        }
                    }
                }
            },
            label = { Text("Fecha nacimiento DD/MM/AAAA") }
        )


        if (Dialogo) {
            AlertDialog(
                onDismissRequest = {
                    Dialogo = false
                },
                title = {
                    Text("Error")
                },
                text = {
                    Text(mensajeError)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            Dialogo = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("RFC:")
        Text(
            text = rfc
        )

    }
}