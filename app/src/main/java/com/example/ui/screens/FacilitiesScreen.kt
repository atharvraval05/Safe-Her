package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SafetyViewModel
import com.example.data.model.SafetyFacility
import kotlinx.coroutines.delay
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.graphics.Brush
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.RepeatMode
import androidx.compose.ui.draw.drawBehind

@Composable
fun FacilitiesScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val location by viewModel.currentLocation.collectAsState()
    val lat = location?.latitude ?: 37.4220
    val lng = location?.longitude ?: -122.0841
    val address by viewModel.currentStreetAddress.collectAsState()

    val facilities by viewModel.nearbyFacilities.collectAsState()
    val isFetching by viewModel.isFetchingFacilities.collectAsState()

    LaunchedEffect(lat, lng) {
        viewModel.fetchNearbyFacilities(lat, lng, address)
    }

    // Fake call controller parameters
    var fakeCallState by remember { mutableStateOf("IDLE") } // IDLE, ARMED, RINGING, CONNECTED
    var armedCountdownSec by remember { mutableIntStateOf(0) }
    var selectedCaller by remember { mutableStateOf("Family Member") }
    var callTimeElapsed by remember { mutableIntStateOf(0) }

    // Effect for the ARMED Countdown
    LaunchedEffect(fakeCallState, armedCountdownSec) {
        if (fakeCallState == "ARMED" && armedCountdownSec > 0) {
            delay(1000)
            armedCountdownSec--
            if (armedCountdownSec == 0) {
                fakeCallState = "RINGING"
            }
        }
    }

    // Effect for active Call Duration
    LaunchedEffect(fakeCallState) {
        if (fakeCallState == "CONNECTED") {
            callTimeElapsed = 0
            while (fakeCallState == "CONNECTED") {
                delay(1000)
                callTimeElapsed++
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Redefined high-fidelity animated protective aura background for Facilities Screen
        val infiniteTransition = rememberInfiniteTransition(label = "haven_ambient")
        val animPulse by infiniteTransition.animateFloat(
            initialValue = 0.10f,
            targetValue = 0.18f,
            animationSpec = infiniteRepeatable(
                animation = tween(8000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse_haven"
        )
        val animX by infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 0.8f,
            animationSpec = infiniteRepeatable(
                animation = tween(14000, easing = androidx.compose.animation.core.LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "x_haven"
        )
        val animY by infiniteTransition.animateFloat(
            initialValue = 0.7f,
            targetValue = 0.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(16000, easing = androidx.compose.animation.core.LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "y_haven"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(Color(0xFF070512)) // Deep space-black
                    // 1. Guarding Emerald/Teal Beacons
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF0F766E).copy(alpha = animPulse),
                                Color.Transparent
                            ),
                            center = Offset(size.width * animX, size.height * animY),
                            radius = 280.dp.toPx()
                        ),
                        center = Offset(size.width * animX, size.height * animY),
                        radius = 280.dp.toPx()
                    )
                    // 2. Cohesive indigo comfort center glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4F46E5).copy(alpha = 0.12f),
                                Color.Transparent
                            ),
                            center = center,
                            radius = 350.dp.toPx()
                        ),
                        center = center,
                        radius = 350.dp.toPx()
                    )
                }
        )

        // Main facilities interface screen
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Elegant Glass Header Pattern
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.HealthAndSafety,
                    contentDescription = "Safe Zones icon",
                    tint = Color(0xFF38BDF8), // Cyber Sky Blue
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Safety Safe-Haven Net",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Verified Protective Stations, Safe Haven Desks & Fast Divergents",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFCAC4D0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ================= FAKE CALL DIVERGENT CABINET =================
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .testTag("fake_call_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(Color(0xFFF43F5E), Color(0xFF6366F1)))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Fake Phone Icon",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Emergency Fake Call Divergent",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Trigger a realistic caller ID phone bypass to exit awkward situations",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (fakeCallState == "IDLE") {
                        // Choice of caller
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Dad", "Brother", "Mom", "Security").forEach { caller ->
                                val selected = selectedCaller == caller
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if (selected) Color(0xFF6366F1) else Color(0x0AFFFFFF))
                                        .border(
                                            width = 1.dp,
                                            color = if (selected) Color.Transparent else Color(0x15FFFFFF),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .clickable { selectedCaller = caller }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = caller,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    fakeCallState = "RINGING"
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF43F5E))
                            ) {
                                Text("Trigger Instant Call", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.White)
                            }

                            Button(
                                onClick = {
                                    armedCountdownSec = 10
                                    fakeCallState = "ARMED"
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                            ) {
                                Text("Trigger in 10s", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.White)
                            }
                        }
                    } else if (fakeCallState == "ARMED") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = Color(0xFF38BDF8)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Bypass call in: $armedCountdownSec seconds",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF38BDF8)
                                )
                            }
                            Text(
                                text = "Cancel",
                                color = Color(0xFFF43F5E),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier
                                    .clickable { fakeCallState = "IDLE" }
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }

            // ================= VERIFIED PROTECTION PLACES =================
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp, start = 4.dp)
            ) {
                Text(
                    text = "NEAREST SAFE-HAVENS (${facilities.size})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White.copy(alpha = 0.6f),
                    letterSpacing = 1.sp,
                    modifier = Modifier.weight(1f)
                )
                if (isFetching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        strokeWidth = 1.5.dp,
                        color = Color(0xFFF43F5E)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "GPS API FETCHING...",
                        fontSize = 9.sp,
                        color = Color(0xFFF43F5E),
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF10B981).copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFF10B981).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "LIVE FEED ACTIVE",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF10B981),
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            if (isFetching && facilities.isEmpty()) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(32.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            strokeWidth = 3.dp,
                            color = Color(0xFFF43F5E)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Querying local emergency dispatchers...",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFF43F5E)
                        )
                        Text(
                            text = "Locating closest police precincts and trauma facilities around your sector.",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }

            facilities.forEach { facility ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x1AFFFFFF)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x15FFFFFF)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (facility.type.contains("Police")) Color(0xFF6366F1).copy(alpha = 0.2f)
                                            else Color(0xFFF43F5E).copy(alpha = 0.2f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (facility.type.contains("Police")) Icons.Default.Security else Icons.Default.LocalHospital,
                                        contentDescription = "SafeZone Node type icon",
                                        tint = if (facility.type.contains("Police")) Color(0xFF6366F1) else Color(0xFFF43F5E),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = facility.name,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = facility.type,
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Address: ${facility.address}",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0x2238BDF8), RoundedCornerShape(12.dp))
                                    .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CompassCalibration,
                                        contentDescription = "dist",
                                        tint = Color(0xFF38BDF8),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = facility.distance,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                            }

                            if (facility.specialBadge.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color(0x22F43F5E), RoundedCornerShape(12.dp))
                                    .border(1.dp, Color(0xFFF43F5E).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = facility.specialBadge,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFF43F5E)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            // Interactive quick actions
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color(0x1CFFFFFF))
                                        .size(36.dp)
                                        .clickable {
                                            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${facility.phoneNumber}"))
                                            context.startActivity(dialIntent)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = "Dial safe station",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color(0x1CFFFFFF))
                                        .size(36.dp)
                                        .clickable {
                                            val mapUri = "https://maps.google.com/?q=${facility.relativeLat},${facility.relativeLng}"
                                            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUri))
                                            context.startActivity(mapIntent)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Directions,
                                        contentDescription = "Navigate to safe station",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // ================= FULL SCREEN SIMULATED INCOMING FAKE CALL =================
        AnimatedVisibility(
            visible = (fakeCallState == "RINGING" || fakeCallState == "CONNECTED"),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF070512)) // Apple-style pure black screen overlay
                    .padding(32.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                if (fakeCallState == "RINGING") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(top = 40.dp)
                    ) {
                        // iOS style avatar
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color(0x1CFFFFFF))
                                .border(1.dp, Color(0x15FFFFFF), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedCaller.firstOrNull()?.uppercaseChar()?.toString() ?: "C",
                                color = Color.White,
                                fontSize = 38.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = selectedCaller,
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "INCOMING MOBILE BYPASS CALL",
                            color = Color(0xFF38BDF8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Ringing securely...",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Decline button
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFEF4444))
                                        .clickable { fakeCallState = "IDLE" },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CallEnd,
                                        contentDescription = "Reject Simulation Call",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Decline", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            // Accept button
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF22C55E))
                                        .clickable { fakeCallState = "CONNECTED" },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = "Accept Simulation Call",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Accept", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else if (fakeCallState == "CONNECTED") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(top = 40.dp)
                    ) {
                        // Connected layout
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E).copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF22C55E)),
                                contentAlignment = Alignment.Center
                            ) {
                                val callerInitial = selectedCaller.firstOrNull()?.uppercaseChar()?.toString() ?: "C"
                                Text(
                                    text = callerInitial,
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = selectedCaller,
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val mins = callTimeElapsed / 60
                        val secs = callTimeElapsed % 60
                        Text(
                            text = String.format("%02d:%02d", mins, secs),
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Custom Audio Oscillation Simulated Waves Drawn on Grid Canvas
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0x1CFFFFFF))
                                .border(1.dp, Color(0x15FFFFFF), RoundedCornerShape(16.dp))
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height
                                val midY = height / 2f
                                val points = 40
                                val step = width / points
                                val timeFactor = (System.currentTimeMillis() / 100).toInt()
                                
                                for (i in 0 until points) {
                                    val x = i * step
                                    // Simulated wave height based on time & sine index to make it active and energetic
                                    val multiplier = if (i % 3 == 0) 1.2f else if (i % 5 == 1) 0.6f else 0.2f
                                    val angle = (i * 0.4f) + (timeFactor * 0.2f)
                                    val waveVal = kotlin.math.sin(angle) * (height * 0.3f) * multiplier
                                    
                                    drawLine(
                                        color = Color(0xFF22C55E),
                                        start = Offset(x, midY - waveVal.toFloat() - 2f),
                                        end = Offset(x, midY + waveVal.toFloat() + 2f),
                                        strokeWidth = 3f
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Simulated Caller audio is active. Tap speaker or pretend to converse naturally to discourage threats.",
                            textAlign = TextAlign.Center,
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Active Call Dashboard Options Grid
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Speaker active toggle
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF334155)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Speaker", tint = Color.White)
                            }

                            // Emergency Star Help Desk Quick Dialer
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF334155))
                                    .clickable {
                                        // Auto dial helpline immediately during fake call bypass as backup
                                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"))
                                        context.startActivity(dialIntent)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Star, contentDescription = "Intercom Support Dial", tint = Color.Yellow)
                            }
                        }

                        // Huge RED Call End Button
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEF4444))
                                .clickable { fakeCallState = "IDLE" },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CallEnd,
                                contentDescription = "End Fake Call",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
