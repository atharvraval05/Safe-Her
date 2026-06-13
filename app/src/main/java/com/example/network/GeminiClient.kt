package com.example.network

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiClient {
    private const val TAG = "GeminiClient"
    private const val MODEL_NAME = "gemini-1.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun getSafetyAdvice(prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.e(TAG, "API Key is missing or default placeholder.")
            return@withContext "Safe Her AI: Please set your valid GEMINI_API_KEY in the Secrets panel or .env file to enable the intelligent safety companion."
        }

        try {
            // Build direct JSON payload using org.json.JSONObject (runs out of the box in Android)
            val root = JSONObject()
            
            // Contents array
            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()
            val partObj = JSONObject()
            partObj.put("text", prompt)
            partsArray.put(partObj)
            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            // System instructions to shape the persona/response of the safety expert
            val systemInstruction = JSONObject()
            val siPartsArray = JSONArray()
            val siPartObj = JSONObject()
            siPartObj.put("text", "You are Safe Her AI, a premium emergency safety advisor and companion designed to help women in distress. Your goal is to provide extremely clear, actionable, quick-to-read, and practical steps to ensure safety, diffuse threats, or identify escape routes. Prioritize short, crisp bullet points that can be read in 5 seconds during a stressful situation.")
            siPartsArray.put(siPartObj)
            systemInstruction.put("parts", siPartsArray)
            root.put("systemInstruction", systemInstruction)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = root.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    val errBody = response.body?.string() ?: ""
                    Log.e(TAG, "Error Response: $errBody")
                    return@withContext "Safe Her AI safety advice service temporarily unavailable. Please stay alert, find a public area, or trigger the SOS button immediately."
                }

                val responseBody = response.body?.string()
                if (responseBody != null) {
                    val responseJson = JSONObject(responseBody)
                    val candidates = responseJson.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val firstCandidate = candidates.getJSONObject(0)
                        val content = firstCandidate.optJSONObject("content")
                        if (content != null) {
                            val parts = content.optJSONArray("parts")
                            if (parts != null && parts.length() > 0) {
                                return@withContext parts.getJSONObject(0).optString("text", "No response from Safe Her AI.")
                            }
                        }
                    }
                }
                return@withContext "Stay calm and alert. Seek a populated area or call your primary emergency contact."
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Gemini Call", e)
            return@withContext "Safety tip: Stay in well-lit areas, walk confidently, and keep your phone in your hand. (Connection offline)"
        }
    }
}
