package dev.matsem.spacenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.retainedComponent
import dev.matsem.spacenews.shared.arch.presentation.defaultAppComponentContext
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.navigation.root.RootNavHost
import dev.matsem.spacenews.shared.navigation.root.RootNavHostFactory
import dev.matsem.spacenews.ui.RootNavHostUi

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val rootNavHost: RootNavHost = retainedComponent { retainedContext ->
            RootNavHostFactory.create(defaultAppComponentContext(retainedContext))
        }

        setContent {
            SpaceNewsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RootNavHostUi(rootNavHost)
                }
            }
        }
    }
}
