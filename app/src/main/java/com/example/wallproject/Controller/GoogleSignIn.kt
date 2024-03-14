package com.example.wallproject.Controller

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.wallproject.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignIn(private val activity: AppCompatActivity) {
    private val TAG = "GoogleSignInModel"
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mGoogleSignInClient: GoogleSignInClient

    init {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signIn(resultLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    fun handleSignInResult(data: Intent?, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthWithGoogle(account, onSuccess, onFailure)
            } else {
                onFailure.invoke()
            }
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            onFailure.invoke()
        }
    }

    fun signOut(onSuccess: () -> Unit, onFailure: () -> Unit) {
        // Firebase sign out
        mAuth.signOut()

        // Google sign out
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }
    }

    fun getCurrentUserUid(): String? {
        return mAuth.currentUser?.uid
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    onSuccess.invoke()
                } else {
                    // If sign in fails, display a message to the user.
                    onFailure.invoke()
                }
            }
    }
}