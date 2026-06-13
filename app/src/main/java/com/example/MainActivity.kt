package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.database.AppDatabase
import com.example.data.repository.SafetyRepository
import com.example.ui.SafetyViewModel
import com.example.ui.SafetyViewModelFactory
import com.example.ui.bounceClickable
import com.example.ui.screens.AiCompanionScreen
import com.example.ui.screens.AuraBackground
import com.example.ui.screens.ContactsScreen
import com.example.ui.screens.FacilitiesScreen
import com.example.ui.screens.IncidentLogsScreen
import com.example.ui.screens.SosScreen
import com.example.ui.theme.MyApplicationTheme


enum class ScreenTab {
    SOS,
    FACILITIES,
    AI_ADVISOR,
    CIRCLE,
    LOGS
}

class MainActivity : ComponentActivity() {
    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    private val repository by lazy {
        SafetyRepository(
            database.emergencyContactDao(),
            database.incidentLogDao(),
            database.chatMessageDao()
        )
    }
    private val viewModel: SafetyViewModel by viewModels {
        SafetyViewModelFactory(application, repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Dynamic runtime permission request on startup
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS
            ),
            101
        )

        setContent {
            MyApplicationTheme {
                var currentTab by remember { mutableStateOf(ScreenTab.SOS) }
                var sessionUser by remember { mutableStateOf("Guest") }
                var showLoginScreen by remember { mutableStateOf(false) }

                if (showLoginScreen) {
                    LoginScreen(
                        onLoginSuccess = { user ->
                            sessionUser = user
                            showLoginScreen = false
                        },
                        onDismiss = {
                            showLoginScreen = false
                        }
                    )
                } else {
                    val isSosActive by viewModel.isSosActive.collectAsState()
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Ambient dynamic glowing background
                        AuraBackground(isSosActive = isSosActive)

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // High Fidelity Glassmorphic Top Panel
                            Card(
                                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xCC0D091A)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1FFFFFFF)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .statusBarsPadding()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (sessionUser == "Guest") {
                                                        Color(0x22F43F5E)
                                                    } else {
                                                        Color(0x2210B981)
                                                    }
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (sessionUser == "Guest") Icons.Default.LockOpen else Icons.Default.Verified,
                                                contentDescription = "Verified Seal",
                                                tint = if (sessionUser == "Guest") Color(0xFFF43F5E) else Color(0xFF10B981),
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = if (sessionUser == "Guest") "SAFE HER ACTIVE CHANNELS" else "SYS-CHANNEL CODES: ${sessionUser.uppercase()}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color.White,
                                                letterSpacing = 1.sp
                                            )
                                            Text(
                                                text = if (sessionUser == "Guest") "🟢 Encrypted Session • Guest mode" else "🟢 Encrypted Session • Active Sync Secured",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF38BDF8),
                                                letterSpacing = 0.5.sp
                                            )
                                        }
                                        if (sessionUser == "Guest") {
                                            Button(
                                                onClick = { showLoginScreen = true },
                                                contentPadding = PaddingValues(horizontal = 16.dp),
                                                shape = RoundedCornerShape(24.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFFF43F5E)
                                                ),
                                                modifier = Modifier
                                                    .testTag("login_button")
                                                    .height(34.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.Login,
                                                    contentDescription = "Log in",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text("Authorize", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.White)
                                            }
                                        } else {
                                            IconButton(
                                                onClick = { sessionUser = "Guest" },
                                                modifier = Modifier
                                                    .testTag("logout_button")
                                                    .size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                                    contentDescription = "Log out of session",
                                                    tint = Color(0xFFEF4444),
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // Marquee text ticker - Exact replication of user's video horizontal marquee movement!
                                    InfiniteMarqueeTicker(
                                        text = "🚨 REAL-TIME SECURITY ACTIVE CHANNEL SECURED • BROADCAST CIRCLES CONNECTED • AUTOMATED CRITICAL DISTRESS SHIELD READY TO LAUNCH IN JUST 3 SECONDS • CONTACT GUARDS LIVE • HOSPITALS DETECTED"
                                    )
                                }
                            }

                            // Dynamic Screen Container with Premium Sliding transitions
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                AnimatedContent(
                                    targetState = currentTab,
                                    transitionSpec = {
                                        val direction = if (targetState.ordinal > initialState.ordinal) {
                                            AnimatedContentTransitionScope.SlideDirection.Left
                                        } else {
                                            AnimatedContentTransitionScope.SlideDirection.Right
                                        }
                                        slideIntoContainer(
                                            towards = direction,
                                            animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium)
                                        ) togetherWith slideOutOfContainer(
                                            towards = direction,
                                            animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium)
                                        )
                                    },
                                    label = "tabChangeGroup"
                                ) { tab ->
                                    val screenModifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 85.dp) // Leave clean space for floating dock

                                    when (tab) {
                                        ScreenTab.SOS -> {
                                            SosScreen(
                                                viewModel = viewModel,
                                                modifier = screenModifier
                                            )
                                        }
                                        ScreenTab.FACILITIES -> {
                                            FacilitiesScreen(
                                                viewModel = viewModel,
                                                modifier = screenModifier
                                            )
                                        }
                                        ScreenTab.AI_ADVISOR -> {
                                            AiCompanionScreen(
                                                viewModel = viewModel,
                                                modifier = screenModifier
                                            )
                                        }
                                        ScreenTab.CIRCLE -> {
                                            ContactsScreen(
                                                viewModel = viewModel,
                                                modifier = screenModifier
                                            )
                                        }
                                        ScreenTab.LOGS -> {
                                            IncidentLogsScreen(
                                                viewModel = viewModel,
                                                modifier = screenModifier
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Suspended Glassmorphic iOS Dock Bar Custom Widget
                        CustomIosBottomBar(
                            currentTab = currentTab,
                            onTabSelected = { currentTab = it },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            val finePermission = grantResults.getOrNull(0) == android.content.pm.PackageManager.PERMISSION_GRANTED
            val coarsePermission = grantResults.getOrNull(1) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (finePermission || coarsePermission) {
                viewModel.startLocationUpdates()
            }
        }
    }
}

@Composable
fun InfiniteMarqueeTicker(
    text: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "marquee")
    val translationX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -350f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "translationX"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE11D48).copy(alpha = 0.12f))
            .padding(vertical = 6.dp)
            .clipToBounds()
    ) {
        Row(
            modifier = Modifier
                .offset(x = translationX.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            repeat(8) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = text,
                        color = Color(0xFFF43F5E),
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        letterSpacing = 1.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun CustomIosBottomBar(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xEC0B0818)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x2BFFFFFF)),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScreenTab.entries.forEach { tab ->
                    val isSelected = currentTab == tab

                    val animatedScale by animateFloatAsState(
                        targetValue = if (isSelected) 1.15f else 1.0f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
                        label = "tab_scale"
                    )

                    val animatedOffset by animateDpAsState(
                        targetValue = if (isSelected) (-4).dp else 0.dp,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
                        label = "tab_offset"
                    )

                    val contentColor = if (isSelected) Color(0xFF38BDF8) else Color(0x7FFFFFFF)

                    val (label, icon) = when (tab) {
                        ScreenTab.SOS -> "SOS" to Icons.Default.Emergency
                        ScreenTab.FACILITIES -> "Haven" to Icons.Default.HealthAndSafety
                        ScreenTab.AI_ADVISOR -> "Advisor" to Icons.Default.AutoAwesome
                        ScreenTab.CIRCLE -> "Circle" to Icons.Default.ContactPhone
                        ScreenTab.LOGS -> "Audit" to Icons.Default.LibraryBooks
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .graphicsLayer {
                                scaleX = animatedScale
                                scaleY = animatedScale
                                translationY = animatedOffset.toPx()
                            }
                            .clip(RoundedCornerShape(24.dp))
                            .bounceClickable { onTabSelected(tab) }
                            .testTag(
                                when (tab) {
                                    ScreenTab.SOS -> "nav_sos_tab"
                                    ScreenTab.FACILITIES -> "nav_facilities_tab"
                                    ScreenTab.AI_ADVISOR -> "nav_ai_tab"
                                    ScreenTab.CIRCLE -> "nav_contacts_tab"
                                    ScreenTab.LOGS -> "nav_logs_tab"
                                }
                            )
                            .background(
                                if (isSelected) Color(0x1A38BDF8) else Color.Transparent
                            )
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "$label Tab icon",
                                tint = contentColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = label,
                                fontSize = 9.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = contentColor,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var stayAnonymous by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070512)), // High-density dark space
        contentAlignment = Alignment.Center
    ) {
        // Aesthetic dynamic bg for login
        AuraBackground(isSosActive = false)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .widthIn(max = 400.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0x1CFFFFFF))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Page",
                        tint = Color.White
                    )
                }
            }

            // Header Shield Logo
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFF43F5E), Color(0xFF6366F1))
                        )
                    )
                    .border(1.5.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.HealthAndSafety,
                    contentDescription = "Shield Logo",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "SAFE HER",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 2.sp
            )

            Text(
                text = "Establishing Live Emergency Safety Session",
                fontSize = 11.sp,
                color = Color(0xFF38BDF8),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 32.dp)
            )

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xE518142C)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1EFFFFFF)),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Sign In & Connect Session",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            showPasswordError = false
                        },
                        label = { Text("Email Address", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f)) },
                        leadingIcon = { Icon(Icons.Default.AlternateEmail, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF6366F1)) },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x33FFFFFF),
                            focusedLabelColor = Color(0xFF6366F1),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("login_email")
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            showPasswordError = false
                        },
                        label = { Text("Pin / Password", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f)) },
                        leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF6366F1)) },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color(0x33FFFFFF),
                            focusedLabelColor = Color(0xFF6366F1),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("login_password")
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = stayAnonymous,
                            onCheckedChange = { stayAnonymous = it },
                            modifier = Modifier.testTag("anonymous_login_check")
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text("Mask Device Identifier", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Keeps connection anonymous during session.", fontSize = 9.sp, color = Color.Gray)
                        }
                    }

                    if (showPasswordError) {
                        Text(
                            text = "Please enter both Email and Password to authorize.",
                            color = Color(0xFFF43F5E),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                val namePart = email.substringBefore("@")
                                onLoginSuccess(if (stayAnonymous) "Safe Her User" else namePart)
                            } else {
                                showPasswordError = true
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_login_button")
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ESTABLISH SAFE HER RUNTIME", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }

                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("CANCEL & EXPLORE GUEST SESSION", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color(0xFF38BDF8))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.VpnLock,
                    contentDescription = null,
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SAFE HER SESSION ACTIVE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981),
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

