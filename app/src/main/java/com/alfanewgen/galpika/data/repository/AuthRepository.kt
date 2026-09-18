package com.alfanewgen.galpika.data.repository

import com.alfanewgen.galpika.data.model.UserProfile
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    val currentUser: Flow<UserProfile?> = callbackFlow {
        val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                trySend(null)
            } else {
                // Read role from Firestore
                firestore.collection("users").document(user.uid)
                    .get()
                    .addOnSuccessListener { doc ->
                        val role = doc.getString("role") ?: "reader"
                        trySend(
                            UserProfile(
                                uid = user.uid,
                                email = user.email ?: "",
                                displayName = user.displayName ?: "గల్పిక పాఠకుడు",
                                photoUrl = user.photoUrl?.toString() ?: "",
                                role = role
                            )
                        )
                    }
                    .addOnFailureListener {
                        trySend(
                            UserProfile(
                                uid = user.uid,
                                email = user.email ?: "",
                                displayName = user.displayName ?: "గల్పిక పాఠకుడు",
                                photoUrl = user.photoUrl?.toString() ?: "",
                                role = "reader"
                            )
                        )
                    }
            }
        }

        auth.addAuthStateListener(authListener)
        awaitClose { auth.removeAuthStateListener(authListener) }
    }

    suspend fun signInWithCredential(credential: AuthCredential): Result<UserProfile> {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            val user = authResult.user ?: throw Exception("User is null after sign in")
            
            // Check or create user in firestore
            val userDoc = firestore.collection("users").document(user.uid).get().await()
            val role = if (userDoc.exists()) {
                userDoc.getString("role") ?: "reader"
            } else {
                val initialRole = "reader"
                firestore.collection("users").document(user.uid).set(
                    mapOf(
                        "email" to (user.email ?: ""),
                        "displayName" to (user.displayName ?: ""),
                        "photoUrl" to (user.photoUrl?.toString() ?: ""),
                        "role" to initialRole
                    )
                ).await()
                initialRole
            }

            Result.success(
                UserProfile(
                    uid = user.uid,
                    email = user.email ?: "",
                    displayName = user.displayName ?: "గల్పిక యూజర్",
                    photoUrl = user.photoUrl?.toString() ?: "",
                    role = role
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
