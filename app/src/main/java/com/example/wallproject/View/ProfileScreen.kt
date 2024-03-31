package com.example.wallproject.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.wallproject.Controller.GoogleSignIn
import com.example.wallproject.Model.GameSingleton
import com.example.wallproject.R
import com.example.wallproject.databinding.ActivityProfileScreenBinding
import com.google.android.gms.common.SignInButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileScreen : AppCompatActivity() {

    private lateinit var binding: ActivityProfileScreenBinding

    private lateinit var googleSignIn: GoogleSignIn
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googleSignIn = GoogleSignIn(this)

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            googleSignIn.handleSignInResult(result.data,
                onSuccess = {
                    GameSingleton.game.setGoogleId(googleSignIn.getCurrentUserUid())
                    GameSingleton.game.saveGame()
                    setVisibilityOfButtons()
                },
                onFailure = {
                    //todo add a toast
                }
            )
        }

        setVisibilityOfButtons()

        binding.signInButton.setOnClickListener {
            googleSignIn.signIn(signInLauncher)
        }

        binding.signOutButton.setOnClickListener {
            googleSignIn.signOut(
                onSuccess = {
                    GameSingleton.game.account.googleId = null
                    setVisibilityOfButtons()
                    //todo add a toast? maybe delete from account or, ask if they want to keep their game instance
                },
                onFailure = {
                    // Sign-out failed, handle the failure scenario
                }
            )
        }

        startUpdatingData()

    }

    private fun startUpdatingData() {

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                binding.villageNameTextViewProfile.text = GameSingleton.game.account.villageName

                binding.stonesTextViewProfile.text = GameSingleton.game.wallet.getBalanceInt().toString()

                binding.bronzeTextViewProfile.text = GameSingleton.game.currencyWallet.getBronze().toString()
                binding.silverTextViewProfile.text = GameSingleton.game.currencyWallet.getSilver().toString()
                binding.goldTextViewProfile.text = GameSingleton.game.currencyWallet.getGold().toString()

                binding.playerNameTextViewProfile.text = GameSingleton.game.account.heroName

                if (GameSingleton.game.account.heroName != null) {
                    binding.playerLevelTextViewProfile.text = GameSingleton.game.dungeons.player.level.toString()
                }else{
                    binding.playerLevelTextViewProfile.text = ""
                }

                binding.shellsTextViewProfile.text = GameSingleton.game.wall.shells.toString()

                // Delay for 1 second
                delay(1000)
            }
        }
    }

    private fun setVisibilityOfButtons() {
        if(GameSingleton.game.account.googleId == null){
            binding.signOutButton.visibility = View.INVISIBLE
            binding.signInButton.visibility = View.VISIBLE
        } else {
            binding.signInButton.visibility = View.INVISIBLE
            binding.signOutButton.visibility = View.VISIBLE
        }
    }
}