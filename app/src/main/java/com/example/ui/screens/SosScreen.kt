package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SafetyViewModel
import com.example.ui.bounceClickable

@Composable
fun SosScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isSosActive by viewModel.isSosActive.collectAsState()
    val isSirenActive by viewModel.isSirenActive.collectAsState()
    val isTimerActive by viewModel.isTimerActive.collectAsState()
    val timerSeconds by viewModel.timerSecondsLeft.collectAsState()
    val locationSharing by viewModel.isSharingLocation.collectAsState()
    val location by viewModel.currentLocation.collectAsState()
    val primaryContacts by viewModel.contacts.collectAsState()

    // High fidelity pulsing dynamic scale
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Redefined high-fidelity animated protective aura background
        AuraBackground(isSosActive = isSosActive)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Elegant Glass Header Pattern
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isSosActive) Color.Red else Color(0xFF10B981))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "GUARDIAN ACTIVE NET",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8), // Cyber Sky Blue accent
                            letterSpacing = 1.5.sp
                        )
                    }
                    Text(
                        text = "Active Safety Shield",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )
                }
                // Custom Profile Badge using Atharv's initials
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Color(0xFFE11D48), Color(0xFF4F46E5))))
                        .border(1.5.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AR",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Premium Glassmorphic Live Location Track Card
            val latLoc = location?.latitude ?: 37.4220
            val lngLoc = location?.longitude ?: -122.0841
            val currentAddress by viewModel.currentStreetAddress.collectAsState()

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0x331E1B3A) // Frosted glass dark-slate blend
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF)), // Sleek frosty border
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Color(0xFFE11D48), Color(0xFFF43F5E)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Location Pin",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Live Guarding Coordinates",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF43F5E), // Vivid rose tracker highlight
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = currentAddress,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 2,
                            lineHeight = 18.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF10B981), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "LIVE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            // DRAW COGNITIVE VECTOR MAP RADAR (Custom neon stylings nested)
            RadarMap(
                lat = latLoc,
                lng = lngLoc,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // SOS Pulsing Main Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(240.dp)
                    .testTag("sos_button_container")
            ) {
                // Energetic warnings when actively triggered
                if (isSosActive) {
                    Box(
                        modifier = Modifier
                            .size(230.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444).copy(alpha = 0.25f))
                    )
                    Box(
                        modifier = Modifier
                            .size(195.dp)
                            .scale(pulseScale * 0.9f)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444).copy(alpha = 0.45f))
                    )
                } else {
                    // Gentle, reassuring protective breath auras when standby
                    val slowPulseScale by infiniteTransition.animateFloat(
                        initialValue = 1.0f,
                        targetValue = 1.07f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2500),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "slow_pulse"
                    )
                    Box(
                        modifier = Modifier
                            .size(215.dp)
                            .scale(slowPulseScale)
                            .clip(CircleShape)
                            .background(Color(0xFF0F766E).copy(alpha = 0.12f))
                    )
                    Box(
                        modifier = Modifier
                            .size(185.dp)
                            .scale(slowPulseScale * 0.95f)
                            .clip(CircleShape)
                            .background(Color(0xFF4F46E5).copy(alpha = 0.08f))
                    )
                }

                // Main SOS circular element with high aesthetic color accents
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = if (isSosActive) {
                                    listOf(Color(0xFFDC2626), Color(0xFF7F1D1D))
                                } else {
                                    listOf(Color(0xFFE11D48), Color(0xFF4F46E5)) // Red Rose to Royal Indigo gradient
                                }
                            )
                        )
                        .bounceClickable {
                            if (isSosActive) {
                                viewModel.dismissSos()
                            } else {
                                viewModel.triggerSos()
                                val callNumber = primaryContacts.firstOrNull { it.isPrimary }?.phoneNumber
                                    ?: primaryContacts.firstOrNull()?.phoneNumber
                                    ?: "112"
                                try {
                                    val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$callNumber")).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    }
                                    if (androidx.core.content.ContextCompat.checkSelfPermission(
                                            context,
                                            android.Manifest.permission.CALL_PHONE
                                        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                                    ) {
                                        context.startActivity(callIntent)
                                    } else {
                                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$callNumber")).apply {
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        }
                                        context.startActivity(dialIntent)
                                    }
                                } catch (e: Exception) {
                                    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$callNumber")).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    }
                                    context.startActivity(dialIntent)
                                }
                            }
                        }
                        .testTag("sos_trigger_button")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (isSosActive) Icons.Default.Warning else Icons.Default.Emergency,
                            contentDescription = "Alert Symbol",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isSosActive) "SOS\nACTIVE" else "SOS\nPANIC",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 19.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isSosActive) "Safe Her SOS is triggered! Sending emergency coordinate updates & calling protective contacts." else "Tap SOS to immediately summon help & alert your safety circle.",
                color = if (isSosActive) Color(0xFFF87171) else Color(0xFFCAC4D0),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontWeight = if (isSosActive) FontWeight.Bold else FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

        // SOS ACTIVE CONTROL CABINET
        AnimatedVisibility(
            visible = isSosActive,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Red.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "EMERGENCY ACTIONS EN ROUTE",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val primaryNo = primaryContacts.firstOrNull { it.isPrimary }?.phoneNumber ?: "112"
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$primaryNo"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f).padding(end = 4.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call Primary", fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                val lat = location?.latitude ?: 37.4220
                                val lng = location?.longitude ?: -122.0841
                                val mapUri = "https://maps.google.com/?q=$lat,$lng"
                                val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("smsto:")
                                    putExtra("sms_body", "Emergency! I need help immediately. My location is: $mapUri - Sent via Safe Her.")
                                }
                                context.startActivity(smsIntent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        ) {
                            Icon(Icons.Default.Sms, contentDescription = "SMS")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Send SMS", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { viewModel.dismissSos() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Cancel, contentDescription = "Cancel")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("DISARM EMERGENCY STATUS")
                    }
                }
            }
        }

        // CONTROL ROW (High Density Redesign)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Siren button
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSirenActive) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.tertiaryContainer
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (isSirenActive) MaterialTheme.colorScheme.error.copy(alpha = 0.5f) else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .weight(1f)
                    .bounceClickable { viewModel.toggleSiren() }
                    .testTag("siren_toggle_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSirenActive) Icons.Default.AlarmOff else Icons.Default.Alarm,
                            contentDescription = "Siren Sound Indicator",
                            tint = if (isSirenActive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (isSirenActive) "Siren Active" else "Trigger Siren",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (isSirenActive) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Loud alarm deterrent signal",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f),
                        lineHeight = 14.sp
                    )
                }
            }

            // Location sharing button
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (locationSharing) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .weight(1f)
                    .bounceClickable { viewModel.toggleLocationSharing() }
                    .testTag("location_toggle_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Location Indicator",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (locationSharing) "Live GPS On" else "Track Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Broadcasting realcoordinates",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        lineHeight = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ================= SMART SAFETY ADVISOR PANEL =================
        val assuranceScore by viewModel.safetyAssuranceScore.collectAsState()
        val isAnomalyDetected by viewModel.isAnomalyDetected.collectAsState()
        val anomalyReason by viewModel.anomalyReason.collectAsState()
        val timeRisk by viewModel.timeRiskWeight.collectAsState()

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Advisor Icon",
                        tint = Color(0xFF6366F1),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Smart Environment Safety",
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "Live crowd active indicators and context assessment",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                val safetyStatusBg = if (isAnomalyDetected) Color(0x22EF4444) else Color(0x1A10B981)
                val safetyStatusBorder = if (isAnomalyDetected) Color(0x66EF4444) else Color(0x6610B981)
                val safetyStatusText = if (isAnomalyDetected) Color(0xFFF43F5E) else Color(0xFF34D399)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(safetyStatusBg, RoundedCornerShape(16.dp))
                        .border(1.dp, safetyStatusBorder, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(if (isAnomalyDetected) Color(0xFFEF4444) else Color(0xFF10B981), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isAnomalyDetected) "LIVE DEVIATION ATTENTION REQUIRED" else "GRID STABLE & STANDARD",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = safetyStatusText,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val explanationText = if (isAnomalyDetected) {
                            anomalyReason
                        } else if (timeRisk > 0.6f) {
                            "Notice: Nighttime hour segment tracking is currently active. We highly advise configuring a custom check-in countdown timer before navigating dark pathways."
                        } else {
                            "Your ambient coordinates are stable. Standard foot-traffic detected. Live GPS tracking is active, and verified Safe Havens are within reach."
                        }

                        Text(
                            text = explanationText,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // COUTNDOWN TIMER CARD
        var showTimerMinutesPicker by remember { mutableStateOf(false) }

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x1AFFFFFF)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Timer Icon",
                        tint = Color(0xFF6366F1),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Safety Check-in Countdown",
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (isTimerActive) {
                        Text(
                            text = "ARMED",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFE11D48),
                            fontSize = 10.sp,
                            modifier = Modifier
                                .background(Color(0x22E11D48), RoundedCornerShape(6.dp))
                                .border(1.dp, Color(0xFFE11D48).copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (isTimerActive) {
                    val minutesLeft = timerSeconds / 60
                    val secondsLeft = timerSeconds % 60
                    val formattedTime = String.format("%02d:%02d", minutesLeft, secondsLeft)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Auto SOS triggers in:",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                            Text(
                                text = formattedTime,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFF43F5E),
                                letterSpacing = 2.sp
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { viewModel.cancelSafetyTimer() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D9488)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Safe / Stop", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                } else {
                    Text(
                        text = "Set a security disarm timer before entering unsafe zones (cabs, dimly-lit corridors). If you do not disarm before timer runs out, SOS triggers automatically.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (!showTimerMinutesPicker) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            OutlinedButton(
                                onClick = { showTimerMinutesPicker = true },
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                            ) {
                                Icon(Icons.Default.HourglassBottom, contentDescription = "Hourglass", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Engage Countdown", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Duration:", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        viewModel.startSafetyTimer(1)
                                        showTimerMinutesPicker = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("1 Min", fontSize = 11.sp, fontWeight = FontWeight.Black)
                                }
                                Button(
                                    onClick = {
                                        viewModel.startSafetyTimer(5)
                                        showTimerMinutesPicker = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("5 Min", fontSize = 11.sp, fontWeight = FontWeight.Black)
                                }
                                Button(
                                    onClick = {
                                        viewModel.startSafetyTimer(15)
                                        showTimerMinutesPicker = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("15 Min", fontSize = 11.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // REAL-TIME LOCATION CRYPTO PIPELINE
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x1AFFFFFF)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Encrypted Location Pipeline",
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val encryptedSignature by viewModel.encryptedCoordinateSignature.collectAsState()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x22000000), RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0x15FFFFFF), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "CIPHERPAYLOAD (AES-256 HMAC)",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8),
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                letterSpacing = 0.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF10B981).copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                    .border(1.dp, Color(0xFF10B981).copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "ACTIVE",
                                    color = Color(0xFF10B981),
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = encryptedSignature,
                            fontSize = 11.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8), // Cyber sky blue
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (locationSharing) "Status: BROADCASTING ACTIVE" else "Status: MONITORING IDLE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = if (locationSharing) Color(0xFF10B981) else Color.White.copy(alpha = 0.5f)
                    )
                    IconButton(
                        onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Emergency Alert: I am tracking my coordinates. Live address resolved: $currentAddress . Encrypted SHA packet: $encryptedSignature")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Telemetry Link"))
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0x1CFFFFFF))
                            .size(36.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share Location Link", tint = Color.White)
                    }
            }
        }
    }
}
}
}

@Composable
fun RadarMap(
    lat: Double,
    lng: Double,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "map_ping")
    val radarPulseRadius by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 90f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Restart
        ),
        label = "radius"
    )
    val radarPulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )

    val baseGridColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
    val safetyShieldColor = Color(0xFF0F766E) // Emerald Teal
    val userMarkerColor = Color(0xFFE11D48) // Warm Coral Rose

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF0F172A)) // Sleek slate grid
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val center = Offset(width / 2f, height * 0.70f) // Isometric horizon center bias

            // Interactive device follow 3D Perspective Street map projection
            // Translate the fractional part of lat/lng coordinate displacement into grid scrolling offset lines
            val normalizedPanY = ((lat * 1000.0) % 65.0).toFloat()
            val normalizedPanX = ((lng * 1000.0) % 60.0).toFloat()

            // Drawing 3D Perspective street lanes converging to vanishing point
            for (i in -6..6) {
                val startX = center.x + i * 55.dp.toPx() + normalizedPanX
                val startY = height
                val endX = center.x + i * 18.dp.toPx() + (normalizedPanX * 0.3f)
                val endY = center.y - 50.dp.toPx()
                drawLine(
                    color = baseGridColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Horizontal grid perspective lines compressed towards the horizon
            for (i in 0..7) {
                val perspectiveCompressionY = center.y - 45.dp.toPx() + (i * i * 3.5f.dp.toPx()) + normalizedPanY
                if (perspectiveCompressionY in 0f..height) {
                    drawLine(
                        color = baseGridColor,
                        start = Offset(0f, perspectiveCompressionY),
                        end = Offset(width, perspectiveCompressionY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }

            // pulsing circle path
            drawCircle(
                color = safetyShieldColor.copy(alpha = radarPulseAlpha),
                radius = radarPulseRadius.dp.toPx(),
                center = Offset(center.x, center.y + 10.dp.toPx()),
                style = Stroke(width = 2.dp.toPx())
            )

            // Dynamic Safe Haven location anchor nodes
            drawCircle(color = Color(0xFF0EA5E9), radius = 6.dp.toPx(), center = Offset(center.x + 60.dp.toPx(), center.y + 20.dp.toPx() - normalizedPanY))
            drawCircle(color = Color(0xFF10B981), radius = 5.dp.toPx(), center = Offset(center.x - 50.dp.toPx(), center.y + 40.dp.toPx() - normalizedPanY))

            // User pin
            drawCircle(color = userMarkerColor, radius = 8.dp.toPx(), center = Offset(center.x, center.y + 10.dp.toPx()))
            drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(center.x, center.y + 10.dp.toPx()))
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "GPS ACTIVE ROUTE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981),
                    letterSpacing = 1.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .background(Color(0xFF0F766E), RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "GPS LIVE SIGNAL: STRONG",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "TELEMETRY COORDS: ${"%.6f".format(lat)} N, ${"%.6f".format(lng)} W",
                color = Color(0xFFE2E8F0),
                fontSize = 10.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AuraBackground(
    isSosActive: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "aura_ambient")

    // Slow organic float animation for protective spheres
    val animX1 by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "x1"
    )

    val animY1 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "y1"
    )

    val animX2 by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "x2"
    )

    val animY2 by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "y2"
    )

    val animPulse1 by infiniteTransition.animateFloat(
        initialValue = 220f,
        targetValue = 320f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse1"
    )

    val animPulse2 by infiniteTransition.animateFloat(
        initialValue = 240f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(11000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse2"
    )

    // Crimson alert pulsing background
    val sosAlertAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alert_pulse"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                if (isSosActive) {
                    // Critical Alert Mode (Intense flashing crimson/base backdrop)
                    drawRect(Color(0xFF130104))

                    // Core warning beacon center
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFDC2626).copy(alpha = sosAlertAlpha),
                                Color.Transparent
                            ),
                            center = center,
                            radius = 350.dp.toPx()
                        ),
                        center = center,
                        radius = 350.dp.toPx()
                    )

                    // Secondary warning halo swirling
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFE11D48).copy(alpha = 0.35f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * animX1, size.height * animY1),
                            radius = animPulse1.dp.toPx()
                        ),
                        center = Offset(size.width * animX1, size.height * animY1),
                        radius = animPulse1.dp.toPx()
                    )
                } else {
                    // Safe Guarding Mode (Sleek, deep luxury navy/midnight purple aura)
                    drawRect(Color(0xFF070512)) // Extremely deep space-black with premium purple shift

                    // 1. Guarding Emerald/Teal Protective Shield Aura
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF0F766E).copy(alpha = 0.22f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * animX1, size.height * animY1),
                            radius = animPulse1.dp.toPx()
                        ),
                        center = Offset(size.width * animX1, size.height * animY1),
                        radius = animPulse1.dp.toPx()
                    )

                    // 2. Empowerment Rose Aura
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFE11D48).copy(alpha = 0.16f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * animX2, size.height * animY2),
                            radius = animPulse2.dp.toPx()
                        ),
                        center = Offset(size.width * animX2, size.height * animY2),
                        radius = animPulse2.dp.toPx()
                    )

                    // 3. Central Indigo Comfort Glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4F46E5).copy(alpha = 0.12f),
                                Color.Transparent
                            ),
                            center = center,
                            radius = 280.dp.toPx()
                        ),
                        center = center,
                        radius = 280.dp.toPx()
                    )
                }
            }
    )
}
