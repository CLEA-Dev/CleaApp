package com.drcmind.cleaapp.ui.menstrual.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CompleteCycleDialog(
    onDismiss: () -> Unit,
    onConfirm: (endDate: String, cycleLength: Int, periodLength: Int) -> Unit
) {
    val currentDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
    var periodLength by remember { mutableStateOf("5") }
    var cycleLength by remember { mutableStateOf("28") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Clôturer le cycle", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Félicitations pour avoir complété ce cycle. Veuillez confirmer les informations finales.")
                
                OutlinedTextField(
                    value = periodLength,
                    onValueChange = { periodLength = it },
                    label = { Text("Durée des règles (jours)") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = cycleLength,
                    onValueChange = { cycleLength = it },
                    label = { Text("Durée totale du cycle (jours)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    onConfirm(currentDate, cycleLength.toIntOrNull() ?: 28, periodLength.toIntOrNull() ?: 5) 
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF913131))
            ) {
                Text("Valider et Calculer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
