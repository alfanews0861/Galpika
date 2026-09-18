package com.alfanewgen.galpika.data.model

data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val role: String = "reader" // "reader", "reporter", "admin"
) {
    val isReporterOrAdmin: Boolean
        get() = role.equals("reporter", ignoreCase = true) || role.equals("admin", ignoreCase = true)

    val isAdmin: Boolean
        get() = role.equals("admin", ignoreCase = true)

    val roleDisplayTelugu: String
        get() = when (role.lowercase()) {
            "admin" -> "ప్రధాన నిర్వాహకుడు (అడ్మిన్)"
            "reporter" -> "అధికారిక వ్యంగ్య రిపోర్టర్"
            else -> "గౌరవ పాఠకుడు"
        }
}
