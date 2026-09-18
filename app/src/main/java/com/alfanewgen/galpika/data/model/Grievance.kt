package com.alfanewgen.galpika.data.model

import java.util.Date

data class Grievance(
    val ticketId: String = "",
    val complainantName: String = "",
    val email: String = "",
    val phone: String = "",
    val articleUrlOrTitle: String = "",
    val complaintReason: String = "కంటెంట్ అభ్యంతరం",
    val description: String = "",
    val status: String = "ACKNOWLEDGED",
    val submittedAt: Date = Date()
)
