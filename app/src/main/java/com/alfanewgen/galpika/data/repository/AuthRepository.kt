package com.alfanewgen.galpika.data.repository

import com.alfanewgen.galpika.data.model.UserProfile
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
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
        var userDocListener: ListenerRegistration? = null

        val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            userDocListener?.remove()
            userDocListener = null

            if (user == null) {
                trySend(null)
            } else {
                val userRef = firestore.collection("users").document(user.uid)
                
                userDocListener = userRef.addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(
                            UserProfile(
                                uid = user.uid,
                                email = user.email ?: "",
                                displayName = user.displayName ?: "గల్పిక పాఠకుడు",
                                photoUrl = user.photoUrl?.toString() ?: "",
                                role = "reader"
                            )
                        )
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val role = snapshot.getString("role") ?: "reader"
                        trySend(
                            UserProfile(
                                uid = user.uid,
                                email = user.email ?: "",
                                displayName = user.displayName ?: "గల్పిక పాఠకుడు",
                                photoUrl = user.photoUrl?.toString() ?: "",
                                role = role
                            )
                        )
                    } else {
                        // User document doesn't exist in Firestore yet! Create it automatically.
                        val initialRole = if (user.email?.equals("alfanews0861@gmail.com", ignoreCase = true) == true) {
                            "admin"
                        } else {
                            "reader"
                        }

                        val userData = mapOf(
                            "email" to (user.email ?: ""),
                            "displayName" to (user.displayName ?: ""),
                            "photoUrl" to (user.photoUrl?.toString() ?: ""),
                            "role" to initialRole
                        )

                        userRef.set(userData)

                        trySend(
                            UserProfile(
                                uid = user.uid,
                                email = user.email ?: "",
                                displayName = user.displayName ?: "గల్పిక పాఠకుడు",
                                photoUrl = user.photoUrl?.toString() ?: "",
                                role = initialRole
                            )
                        )
                    }
                }
            }
        }

        auth.addAuthStateListener(authListener)
        awaitClose {
            auth.removeAuthStateListener(authListener)
            userDocListener?.remove()
        }
    }

    suspend fun signInWithCredential(credential: AuthCredential): Result<UserProfile> {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            val user = authResult.user ?: throw Exception("User is null after sign in")
            
            // Check or create user in firestore
            val userProfile = try {
                val userRef = firestore.collection("users").document(user.uid)
                val userDoc = userRef.get().await()
                val role = if (userDoc.exists()) {
                    userDoc.getString("role") ?: "reader"
                } else {
                    val initialRole = if (user.email?.equals("alfanews0861@gmail.com", ignoreCase = true) == true) {
                        "admin"
                    } else {
                        "reader"
                    }
                    userRef.set(
                        mapOf(
                            "email" to (user.email ?: ""),
                            "displayName" to (user.displayName ?: ""),
                            "photoUrl" to (user.photoUrl?.toString() ?: ""),
                            "role" to initialRole
                        )
                    ).await()
                    initialRole
                }
                UserProfile(
                    uid = user.uid,
                    email = user.email ?: "",
                    displayName = user.displayName ?: "గల్పిక యూజర్",
                    photoUrl = user.photoUrl?.toString() ?: "",
                    role = role
                )
            } catch (_: Exception) {
                // If Firestore throws PERMISSION_DENIED or offline error, fallback so sign-in still succeeds
                val initialRole = if (user.email?.equals("alfanews0861@gmail.com", ignoreCase = true) == true) "admin" else "reader"
                UserProfile(
                    uid = user.uid,
                    email = user.email ?: "",
                    displayName = user.displayName ?: "గల్పిక యూజర్",
                    photoUrl = user.photoUrl?.toString() ?: "",
                    role = initialRole
                )
            }

            Result.success(userProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}




