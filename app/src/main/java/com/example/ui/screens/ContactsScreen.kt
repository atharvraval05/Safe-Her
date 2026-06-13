package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EmergencyContact
import com.example.ui.SafetyViewModel
import com.example.ui.bounceClickable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.RepeatMode
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset

@Composable
fun ContactsScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val contactsList by viewModel.contacts.collectAsState()
    val location by viewModel.currentLocation.collectAsState()
    val lat = location?.latitude ?: 37.4220
    val lng = location?.longitude ?: -122.0841

    var showForm by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var relationInput by remember { mutableStateOf("") }
    var isPrimaryInput by remember { mutableStateOf(false) }

    // Clicked preset banner notification state
    var selectedPresetName by remember { mutableStateOf<String?>(null) }

    val scrollState = androidx.compose.foundation.rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition(label = "contacts_ambient")
        val animPulse by infiniteTransition.animateFloat(
            initialValue = 0.10f,
            targetValue = 0.18f,
            animationSpec = infiniteRepeatable(
                animation = tween(8000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse_contacts"
        )
        val animX by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 0.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(14000, easing = androidx.compose.animation.core.LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "x_contacts"
        )
        val animY by infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 0.7f,
            animationSpec = infiniteRepeatable(
                animation = tween(16000, easing = androidx.compose.animation.core.LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "y_contacts"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(Color(0xFF070512))
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4F46E5).copy(alpha = animPulse),
                                Color.Transparent
                            ),
                            center = Offset(size.width * animX, size.height * animY),
                            radius = 280.dp.toPx()
                        ),
                        center = Offset(size.width * animX, size.height * animY),
                        radius = 280.dp.toPx()
                    )
                }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ContactPhone,
                    contentDescription = "Contacts Icon",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Distress Circle Grid",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Broadcast Live Alerts to Your Circle & Active Local Guards",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFCAC4D0)
                    )
                }
            }

        // ================= QUICK BROADCAST PRESETS =================
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "QUICK PRESET BROADCASTS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White.copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "One-tap dispatch message presets to your preloaded circle",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val presets = listOf(
                        Triple("Boarded Cab", "Boarded Cab Session: Registered plate and tracking active.", "HIGH"),
                        Triple("Dark Pathway", "Pathway Watch Session: Entered dimly-lit segment.", "MEDIUM"),
                        Triple("Safely Home", "Arrived Safely: Dismantled all tracking sessions successfully.", "INFO")
                    )

                    presets.forEach { preset ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0x0AFFFFFF))
                                .border(
                                    width = 1.dp,
                                    color = Color(0x15FFFFFF),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .bounceClickable {
                                    val mapUri = "https://maps.google.com/?q=$lat,$lng"
                                    val fullMessage = "${preset.second} My location: $mapUri"

                                    viewModel.addIncident(
                                        title = "Preset Broadcast: ${preset.first}",
                                        description = preset.second,
                                        severity = preset.third
                                    )
                                    viewModel.broadcastSms(fullMessage)

                                    selectedPresetName = preset.first
                                    if (preset.first == "Boarded Cab" || preset.first == "Dark Pathway") {
                                        viewModel.startLocationUpdates()
                                    } else {
                                        viewModel.stopLocationUpdates()
                                    }
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = if (preset.first == "Safely Home") Icons.Default.Check
                                    else if (preset.first == "Boarded Cab") Icons.Default.Phone
                                    else Icons.Default.DirectionsWalk,
                                    contentDescription = "Icons preset",
                                    tint = if (preset.first == "Safely Home") Color(0xFF10B981)
                                    else if (preset.first == "Boarded Cab") Color(0xFFF43F5E)
                                    else Color(0xFF6366F1),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = preset.first,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                selectedPresetName?.let { presetName ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x1A10B981), RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFF10B981).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Done",
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Preset '$presetName' dispatched & logged in incident logs!",
                                color = Color(0xFF10B981),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // ADD NEW CONTACT DRAWER / SHEET
        AnimatedVisibility(visible = showForm) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF18142C)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1EFFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "REGISTER EMERGENCY CONTACT",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = Color(0xFF6366F1),
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    androidx.compose.material3.OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Contact Name", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x22FFFFFF)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("contact_name_field"),
                        singleLine = true
                    )

                    androidx.compose.material3.OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { phoneInput = it },
                        label = { Text("Phone Number", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x22FFFFFF)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("contact_phone_field"),
                        singleLine = true
                    )

                    androidx.compose.material3.OutlinedTextField(
                        value = relationInput,
                        onValueChange = { relationInput = it },
                        label = { Text("Relation (e.g. Mother, Sister)", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x22FFFFFF)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("contact_relation_field"),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isPrimaryInput,
                            onCheckedChange = { isPrimaryInput = it },
                            modifier = Modifier.testTag("contact_primary_checkbox")
                        )
                        Text("Set as Primary SOS Dispatch", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { showForm = false },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Dismiss", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                if (nameInput.isNotBlank() && phoneInput.isNotBlank()) {
                                    viewModel.addContact(
                                        name = nameInput,
                                        phone = phoneInput,
                                        relation = relationInput.ifBlank { "Guardian Circle" },
                                        isPrimary = isPrimaryInput
                                    )
                                    // Reset inputs
                                    nameInput = ""
                                    phoneInput = ""
                                    relationInput = ""
                                    isPrimaryInput = false
                                    showForm = false
                                }
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                            modifier = Modifier.testTag("save_contact_button")
                        ) {
                            Text("Save Entry", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // REGISTER NEW ENTRY BUTTON
        if (!showForm) {
            Button(
                onClick = { showForm = true },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("show_add_contact_form_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(6.dp))
                Text("Register New Guardian", fontWeight = FontWeight.Black)
            }
        }

        // CONTACTS LIST
        Text(
            text = "TRUSTED EMERGENCY CIRCLE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = Color.White.copy(alpha = 0.6f),
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

        if (contactsList.isEmpty()) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.FolderShared,
                        contentDescription = "Empty",
                        tint = Color.LightGray.copy(alpha = 0.4f),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No custom emergency contacts registered.\nPlease register trusted numbers to build your safety circle.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                contactsList.forEach { contact ->
                    ContactCard(
                        contact = contact,
                        onCallPressed = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phoneNumber}"))
                            context.startActivity(intent)
                        },
                        onDeletePressed = {
                            viewModel.deleteContact(contact)
                        }
                    )
                }
            }
        }

        // Removed hardcoded verified community guardians gimmicks
    }
}
}

@Composable
fun ContactCard(
    contact: EmergencyContact,
    onCallPressed: () -> Unit,
    onDeletePressed: () -> Unit
) {
    val firstLetter = contact.name.firstOrNull()?.uppercaseChar()?.toString() ?: "C"
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (contact.isPrimary) Color(0x22F43F5E) else Color(0x1AFFFFFF)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (contact.isPrimary) Color(0x66F43F5E) else Color(0x15FFFFFF)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Elegant Initial Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (contact.isPrimary) Color(0xFFF43F5E) else Color(0xFF6366F1)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = firstLetter,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = contact.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    if (contact.isPrimary) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Primary",
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text(
                    text = "${contact.relation} • ${contact.phoneNumber}",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }

            // Call Trigger Action inside high density capsule
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0x2210B981))
                    .border(1.dp, Color(0x3310B981), CircleShape)
                    .size(36.dp)
                    .clickable { onCallPressed() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Dial Phone",
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Delete Action
            IconButton(
                onClick = { onDeletePressed() },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Contact",
                    tint = Color(0xFFF43F5E),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
