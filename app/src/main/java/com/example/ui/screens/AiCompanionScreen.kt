package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.data.model.ChatMessage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SafetyViewModel

@Composable
fun AiCompanionScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val messages by viewModel.chatMessages.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickPrompts = listOf(
        "I am being followed, what should I do?",
        "How can I stay alert walking in a dark alley?",
        "Give me quick tactical physical self-defence tips",
        "How to handle a suspicious driver in a cab?"
    )

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
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Icon",
                    tint = Color(0xFF6366F1),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Safe Her AI Companion",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Personal Safety Advisor & Threat Analysis",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { viewModel.clearChatHistory() },
                modifier = Modifier.testTag("clear_chat_button")
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteSweep,
                    contentDescription = "Clear Chat History",
                    tint = Color(0xFFF43F5E)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chats field
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages) { msg ->
                val isUser = msg.sender == "User"
                val alignSelf = if (isUser) Alignment.End else Alignment.Start
                val bubbleBrush = if (isUser) {
                    Brush.linearGradient(colors = listOf(Color(0xFFF43F5E), Color(0xFF6366F1)))
                } else {
                    null
                }
                val bubbleColor = if (isUser) {
                    Color.Transparent
                } else {
                    Color(0x1AFFFFFF)
                }
                val textColor = Color.White

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalAlignment = alignSelf
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.widthIn(max = 290.dp)
                    ) {
                        if (!isUser) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = "AI Agent Icon",
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isUser) 16.dp else 0.dp,
                                        bottomEnd = if (isUser) 0.dp else 16.dp
                                    )
                                )
                                .then(
                                    if (bubbleBrush != null) {
                                        Modifier.background(bubbleBrush)
                                    } else {
                                        Modifier
                                            .background(bubbleColor)
                                            .border(1.dp, Color(0x15FFFFFF), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp))
                                    }
                                )
                                .padding(14.dp)
                        ) {
                            Column {
                                Text(
                                    text = msg.text,
                                    color = textColor,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                // Actionable live escape map launcher
                                if (!isUser && msg.text.contains("maps.google.com/?q=")) {
                                    val mapUrl = remember(msg.text) {
                                        val pattern = """https://maps\.google\.com/\?q=[-0-9a-zA-Z.,_]+""".toRegex()
                                        val match = pattern.find(msg.text)
                                        match?.value?.trim()?.removeSuffix(".")?.removeSuffix("*")?.removeSuffix(")")?.removeSuffix(".")?.removeSuffix("]")
                                    }

                                    if (mapUrl != null) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        androidx.compose.material3.Button(
                                            onClick = {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {}
                                            },
                                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF0D9488)
                                            ),
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("open_escape_map_button")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MyLocation,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                tint = Color.White
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "LAUNCH ESCAPE ROUTE MAP",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isAiLoading) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = Color(0xFF6366F1)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Analyzing threat situation & computing escape routes...",
                            fontSize = 11.sp,
                            color = Color(0xFF6366F1),
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // QUICK QUESTION PROMPTS
        if (messages.size <= 1 && !isAiLoading) {
            Text(
                text = "Quick Situational Help Shortcuts:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                quickPrompts.forEach { prompt ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0x0AFFFFFF))
                            .border(
                                width = 1.dp,
                                color = Color(0x15FFFFFF),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable {
                                viewModel.sendAskAiPrompt(prompt)
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color(0xFF6366F1),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = prompt,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // BOTTOM CONTROLS INPUT FIELD
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Describe threat or ask safety tips...", fontSize = 13.sp, color = Color.White.copy(alpha = 0.5f)) },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .testTag("ai_input_text_field"),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0x1AFFFFFF),
                    unfocusedContainerColor = Color(0x0AFFFFFF),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                maxLines = 2
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = {
                    if (inputText.isNotBlank() && !isAiLoading) {
                        viewModel.sendAskAiPrompt(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF6366F1))
                    .size(48.dp)
                    .testTag("ai_send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message Icon",
                    tint = Color.White
                )
            }
        }
    }
}
