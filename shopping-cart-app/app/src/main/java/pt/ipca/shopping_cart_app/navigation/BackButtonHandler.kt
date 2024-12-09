package pt.ipca.shopping_cart_app.navigation

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLifecycleOwner


private val LocalBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcherOwner?> { null }


// ver documentacao OnBackPressedDispatcherOwner.kt


private class ComposableBackNavigationHandler(enabled: Boolean) : OnBackPressedCallback(enabled) {

    // Função lambda para executar a ação ao pressionar "voltar".
    var onBackPressed: (() -> Unit)? = null

    override fun handleOnBackPressed() {
        onBackPressed?.invoke()
    }
}

@Composable
fun ComposableHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
        ?: return

    // Cria o handler apenas uma vez usando remember.
    val handler = remember { ComposableBackNavigationHandler(enabled) }

    // Atualiza o estado do handler sempre que 'enabled' ou 'onBackPressed' muda.
    LaunchedEffect(enabled, onBackPressed) {
        handler.isEnabled = enabled
        handler.onBackPressed = onBackPressed
    }

    // Gerencia o ciclo de vida do handler para registrá-lo e removê-lo corretamente.
    DisposableEffect(dispatcher) {
        dispatcher.addCallback(handler)
        onDispose { handler.remove() }
    }
}

@Composable
internal fun BackButtonHandler(onBackPressed: () -> Unit) {





CompositionLocalProvider(

    LocalBackPressedDispatcher provides LocalLifecycleOwner.current as ComponentActivity)

{
    ComposableHandler {

        onBackPressed()
    }
}

}
