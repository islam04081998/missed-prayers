package com.example.missedprayers.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.missedprayers.R
import com.example.missedprayers.data.entity.QadaBacklogEntity
import com.example.missedprayers.ui.theme.MissedPrayersTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QadaBacklogScreen(
    qadaList: List<QadaBacklogEntity>,
    onIncrement: (String) -> Unit,
    onDecrement: (String) -> Unit,
    onSetCount: (String, Int) -> Unit
) {
    val totalMissed = qadaList.sumOf { it.count }

    var editingPrayerType by remember { mutableStateOf<String?>(null) }
    var customCountText by remember { mutableStateOf("") }

    val prayerNames = mapOf(
        "SUBH" to R.string.prayer_subh,
        "DHUHR" to R.string.prayer_dhuhr,
        "ASR" to R.string.prayer_asr,
        "MAGHRIB" to R.string.prayer_maghrib,
        "ISHA" to R.string.prayer_isha
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_qada_screen), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.total_missed_title),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "$totalMissed",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Text(
                            text = stringResource(R.string.qada_instruction),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.section_prayer_breakdowns),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(listOf("SUBH", "DHUHR", "ASR", "MAGHRIB", "ISHA")) { type ->
                val entity = qadaList.find { it.prayerType == type } ?: QadaBacklogEntity(prayerType = type, count = 0)
                val nameRes = prayerNames[type] ?: R.string.prayer_subh
                val displayName = stringResource(nameRes)

                QadaBacklogCard(
                    prayerName = displayName,
                    count = entity.count,
                    onIncrement = { onIncrement(type) },
                    onDecrement = { onDecrement(type) },
                    onEdit = {
                        editingPrayerType = type
                        customCountText = entity.count.toString()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (editingPrayerType != null) {
        val type = editingPrayerType!!
        val nameRes = prayerNames[type] ?: R.string.prayer_subh
        val displayName = stringResource(nameRes)
        AlertDialog(
            onDismissRequest = { editingPrayerType = null },
            title = { Text(stringResource(R.string.dialog_set_qada_title, displayName)) },
            text = {
                OutlinedTextField(
                    value = customCountText,
                    onValueChange = { customCountText = it },
                    label = { Text(stringResource(R.string.label_exact_count)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val parsed = customCountText.toIntOrNull()
                        if (parsed != null && parsed >= 0) {
                            onSetCount(type, parsed)
                        }
                        editingPrayerType = null
                    }
                ) {
                    Text(stringResource(R.string.btn_save))
                }
            },
            dismissButton = {
                TextButton(onClick = { editingPrayerType = null }) {
                    Text(stringResource(R.string.btn_cancel))
                }
            }
        )
    }
}

@Composable
fun QadaBacklogCard(
    prayerName: String,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = prayerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ) {
                        Text(
                            text = stringResource(R.string.missed_count_format, count),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = stringResource(R.string.cd_edit_count),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrement (Made up)
                FilledTonalIconButton(
                    onClick = onDecrement,
                    enabled = count > 0,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Remove,
                        contentDescription = stringResource(R.string.cd_decrement)
                    )
                }

                // Increment (Missed)
                FilledIconButton(
                    onClick = onIncrement,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.cd_increment)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QadaBacklogScreenPreview() {
    MissedPrayersTheme {
        QadaBacklogScreen(
            qadaList = listOf(
                QadaBacklogEntity("SUBH", 12),
                QadaBacklogEntity("DHUHR", 5),
                QadaBacklogEntity("ASR", 0),
                QadaBacklogEntity("MAGHRIB", 3),
                QadaBacklogEntity("ISHA", 8)
            ),
            onIncrement = {},
            onDecrement = {},
            onSetCount = { _, _ -> }
        )
    }
}
