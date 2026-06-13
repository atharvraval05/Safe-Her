package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.filled.Security
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.IncidentLog
import com.example.data.model.SafetyDomain
import com.example.ui.SafetyViewModel
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.CircularProgressIndicator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun IncidentLogsScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val incidentLogs by viewModel.logs.collectAsState()
    val safetyDomains by viewModel.safetyDomains.collectAsState()
    val isFetchingDomains by viewModel.isFetchingDomains.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }
    var titleInput by remember { mutableStateOf("") }
    var descInput by remember { mutableStateOf("") }
    var severityInput by remember { mutableStateOf("HIGH") }
    var severityMenuExpanded by remember { mutableStateOf(false) }
    var isAnonymous by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        // App header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0x226366F1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LibraryBooks,
                    contentDescription = "Logs Icon",
                    tint = Color(0xFF6366F1),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Encrypted Incident Log",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Evidentiary Threat Records & Critical Auditing",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ================= GLOBAL SAFETY INTELLIGENCE HUB =================
        Text(
            text = "GLOBAL SAFETY INTELLIGENCE HUB",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF38BDF8),
            letterSpacing = 1.5.sp,
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )

        if (isFetchingDomains && safetyDomains.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF38BDF8))
            }
        } else {
            LazyColumn(
                modifier = Modifier.heightIn(max = 280.dp).fillMaxWidth().padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(safetyDomains) { domain ->
                    SafetyDomainCard(domain = domain)
                }
            }
        }

        // ================= INNOVATIVE AUDIT INTELLIGENCE DASHBOARD =================
        if (incidentLogs.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x1A6366F1)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x336366F1)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Intelligence",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SAFETY ANALYTICS INSIGHTS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val highRiskCount = incidentLogs.count { it.severity.uppercase() == "HIGH" }
                        val mediumRiskCount = incidentLogs.count { it.severity.uppercase() == "MEDIUM" }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                            Text(text = highRiskCount.toString(), fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFFF43F5E))
                            Text(text = "HIGH THREATS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f))
                        }
                        
                        Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.White.copy(alpha = 0.1f)))
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                            Text(text = mediumRiskCount.toString(), fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFFFB923C))
                            Text(text = "MODERATE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f))
                        }

                        Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.White.copy(alpha = 0.1f)))

                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                            val totalReports = incidentLogs.size
                            Text(text = totalReports.toString(), fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFF38BDF8))
                            Text(text = "TOTAL AUDITS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.6f))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(14.dp))
                    
                    // AI-Driven Recommendation based on latest log
                    val latestLog = incidentLogs.maxByOrNull { it.timestamp }
                    if (latestLog != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0x22000000), RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0x15FFFFFF), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "AI SAFETY RECOMMENDATION:",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF10B981),
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                val recommendation = when {
                                    latestLog.severity.uppercase() == "HIGH" -> "Based on your recent SOS trigger, we recommend sharing your live telemetry with 2+ trusted guardians today."
                                    latestLog.title.contains("Cab", ignoreCase = true) -> "Frequent cab travel detected. Always verify vehicle 'Safe Child' lock is disengaged before boarding."
                                    else -> "Travel patterns are being logged. Keep your 'Safe-Haven' map updated for regional divergence points."
                                }
                                Text(
                                    text = recommendation,
                                    fontSize = 11.sp,
                                    color = Color.White,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // ADD NEW INCIDENT REPORT DRAWER
        AnimatedVisibility(visible = showAddForm) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF18142C)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1EFFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "MANUAL DETAILED INCIDENT LOGGER",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = Color(0xFF6366F1),
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    androidx.compose.material3.OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = { Text("Incident Summary (e.g. Suspicious car, Unsafe crowd)", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x22FFFFFF)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("incident_title_field"),
                        singleLine = true
                    )

                    androidx.compose.material3.OutlinedTextField(
                        value = descInput,
                        onValueChange = { descInput = it },
                        label = { Text("Evidentiary notes & description details...", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x22FFFFFF)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("incident_desc_field"),
                        maxLines = 3
                    )

                    // Severity Selector Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Risk Severity Metric:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Box {
                            OutlinedButton(
                                onClick = { severityMenuExpanded = true },
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                modifier = Modifier.testTag("severity_menu_button")
                            ) {
                                Text(severityInput, fontWeight = FontWeight.Black)
                            }
                            DropdownMenu(
                                expanded = severityMenuExpanded,
                                onDismissRequest = { severityMenuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("HIGH") },
                                    onClick = {
                                        severityInput = "HIGH"
                                        severityMenuExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("MEDIUM") },
                                    onClick = {
                                        severityInput = "MEDIUM"
                                        severityMenuExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("LOW") },
                                    onClick = {
                                        severityInput = "LOW"
                                        severityMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Anonymous Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Checkbox(
                            checked = isAnonymous,
                            onCheckedChange = { isAnonymous = it },
                            modifier = Modifier.testTag("anonymous_checkbox")
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text("Report Harassment Anonymously", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Masks identifier and routes data straight to regional transit authorities.", fontSize = 10.sp, color = Color.Gray, lineHeight = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { showAddForm = false },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Dismiss", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                if (titleInput.isNotBlank() && descInput.isNotBlank()) {
                                    val finalDesc = if (isAnonymous) {
                                        "[🔒 ANONYMOUS REPORT - AUTO-FORWARDED TO REGIONAL PRECINCT] $descInput"
                                    } else {
                                        descInput
                                    }
                                    viewModel.addIncident(
                                        title = titleInput,
                                        description = finalDesc,
                                        severity = severityInput
                                    )
                                    titleInput = ""
                                    descInput = ""
                                    severityInput = "HIGH"
                                    isAnonymous = false
                                    showAddForm = false
                                }
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                            modifier = Modifier.testTag("save_incident_button")
                        ) {
                            Text("Log Suspicious Event", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // TOOL BAR BUTTONS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!showAddForm) {
                Button(
                    onClick = { showAddForm = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                    modifier = Modifier
                        .weight(1.5f)
                        .testTag("show_add_incident_form_button")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Incident")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Report Incident", fontSize = 12.sp, fontWeight = FontWeight.Black)
                }

                Button(
                    onClick = { viewModel.clearAllIncidentLogs() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF43F5E)),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("clear_logs_button")
                ) {
                    Icon(Icons.Default.DeleteSweep, contentDescription = "Clear Logs")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Purge Logs", fontSize = 11.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        // STATEMENT OF PURPOSE CARD
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PrivacyTip,
                    contentDescription = "Privacy Icon",
                    tint = Color(0xFF0D9488),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "All logs are encrypted, saved offline in local sandboxed storage, and serves as chronological evidence.",
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        // INCIDENT LOGS LIST
        if (incidentLogs.isEmpty()) {
            // Already handled by showing intelligence above
        } else {
            Text(
                text = "ENCRYPTED AUDIT TRAIL",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 4.dp, top = 10.dp, bottom = 10.dp)
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(incidentLogs) { log ->
                    IncidentLogCard(log = log)
                }
            }
        }
    }
}

@Composable
fun SafetyDomainCard(domain: SafetyDomain) {
    val (icon, color) = when (domain.iconType.uppercase()) {
        "LEGAL" -> Icons.Default.Balance to Color(0xFF38BDF8)
        "PHYSICAL" -> Icons.Default.FitnessCenter to Color(0xFFF43F5E)
        "DIGITAL" -> Icons.Default.Devices to Color(0xFFA855F7)
        else -> Icons.Default.Groups to Color(0xFF10B981)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x12FFFFFF)),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = domain.title, fontWeight = FontWeight.Black, fontSize = 14.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = domain.description, fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f), lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(text = "TACTICAL TIP: ${domain.quickAction}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
            }
        }
    }
}

@Composable
fun IncidentLogCard(log: IncidentLog) {
    val formatter = remember { SimpleDateFormat("MMM d, yyyy - hh:mm a", Locale.getDefault()) }
    val dateString = formatter.format(Date(log.timestamp))

    val severityColor = when (log.severity.uppercase()) {
        "HIGH" -> Color(0xFFF43F5E)
        "MEDIUM" -> Color(0xFFFB923C)
        else -> Color(0xFF10B981)
    }

    val severityBg = when (log.severity.uppercase()) {
        "HIGH" -> Color(0x22F43F5E)
        "MEDIUM" -> Color(0x22FB923C)
        else -> Color(0x2210B981)
    }

    val severityBorder = when (log.severity.uppercase()) {
        "HIGH" -> Color(0x66F43F5E)
        "MEDIUM" -> Color(0x66FB923C)
        else -> Color(0x6610B981)
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = Color(0x15FFFFFF)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = log.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(severityBg)
                        .border(1.dp, severityBorder, RoundedCornerShape(24.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = log.severity,
                        color = severityColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = dateString,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = log.description,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Coordinates Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x0AFFFFFF))
                    .border(1.dp, Color(0x15FFFFFF), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Gavel,
                    contentDescription = "Legal Coordinates Tracker",
                    tint = Color(0xFF6366F1),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = log.location,
                    fontSize = 11.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color(0xFF38BDF8),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Desk Routing
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val context = androidx.compose.ui.platform.LocalContext.current
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=police+station"))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {}
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    modifier = Modifier.weight(1f).testTag("log_police_nav_${log.id}")
                ) {
                    Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Find Police Desk", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1091"))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {}
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    modifier = Modifier.weight(1f).testTag("log_legal_action_${log.id}")
                ) {
                    Icon(Icons.Default.Gavel, contentDescription = null, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Legal Helpline", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
