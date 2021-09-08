package pro.it_dev.e_code.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
inline fun FullBox(modifier: Modifier = Modifier,contentAlignment: Alignment = Alignment.Center, content: @Composable BoxScope.()->Unit) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
        content = content
    )
}