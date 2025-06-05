package com.seraphim.music

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.seraphim.domain.login.AuthManager
import com.seraphim.domain.login.AuthResult
import com.seraphim.music.shared.common.KEY_AUTH_ACCESS_TOKEN
import com.seraphim.music.shared.mmkv.safeKvGet
import com.seraphim.music.shared.network.bff.toBffException
import com.seraphim.music.shared.repository.ArtistRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val repository by inject<ArtistRepository>()
    private val authManager by inject<AuthManager>()
    private val authResult by inject<AuthResult>()
    private lateinit var authLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        window.edgeToEdgeWindowInsetsControllerCompat()
//        setContent {
//            MusicTheme {
//                MainScreen()
//            }
//        }
        lifecycleScope.launch {
            repository.searchArtist("Taylor")
                .catch {
                    Napier.e {
                        it.toBffException().message ?: "Unknown error"
                    }
                }
                .collectLatest {

                }
        }
        if (safeKvGet(KEY_AUTH_ACCESS_TOKEN,"").isEmpty()){
            fetchAuthResult()
        }else{
            Napier.e("Access token already exists, skipping authentication")
        }
//        SeraphimPermissionsUtils.checkPermissions(
//            this,
//            permissions = PermissionHelper.getCameraPermission(),
//            title = "Camera Permissions Required",
//            description = "Please grant Camera permissions to access your music files."
//        )
    }
    private fun fetchAuthResult() {
        authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                authResult.onActivityResult(result.data ?: Intent(),{

                },{
                    Napier.e("Auth request failed with exception: ${it.message}")
                })
            } else {
                Napier.e("Auth request failed with resultCode: ${result.resultCode}")
            }
        }
        authLauncher.launch(authManager.getAuthRequestIntent())
    }
}