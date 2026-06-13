package com.example.data.repository

import com.example.data.database.EmergencyContactDao
import com.example.data.database.IncidentLogDao
import com.example.data.database.ChatMessageDao
import com.example.data.model.EmergencyContact
import com.example.data.model.IncidentLog
import com.example.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

class SafetyRepository(
    private val emergencyContactDao: EmergencyContactDao,
    private val incidentLogDao: IncidentLogDao,
    private val chatMessageDao: ChatMessageDao
) {
    val allContacts: Flow<List<EmergencyContact>> = emergencyContactDao.getAllContacts()
    val allLogs: Flow<List<IncidentLog>> = incidentLogDao.getAllLogs()
    val allChatMessages: Flow<List<ChatMessage>> = chatMessageDao.getAllMessages()

    suspend fun insertContact(contact: EmergencyContact) {
        emergencyContactDao.insertContact(contact)
    }

    suspend fun updateContact(contact: EmergencyContact) {
        emergencyContactDao.updateContact(contact)
    }

    suspend fun deleteContact(contact: EmergencyContact) {
        emergencyContactDao.deleteContact(contact)
    }

    suspend fun getPrimaryContact(): EmergencyContact? {
        return emergencyContactDao.getPrimaryContact()
    }

    suspend fun insertLog(log: IncidentLog) {
        incidentLogDao.insertLog(log)
    }

    suspend fun deleteLog(log: IncidentLog) {
        incidentLogDao.deleteLog(log)
    }

    suspend fun clearAllLogs() {
        incidentLogDao.clearAllLogs()
    }

    suspend fun insertChatMessage(message: ChatMessage) {
        chatMessageDao.insertMessage(message)
    }

    suspend fun clearAllChatMessages() {
        chatMessageDao.clearAllMessages()
    }
}
