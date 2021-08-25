package pro.it_dev.e_code.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBarInBox(
    text:String? = null,
    modifier: Modifier = Modifier.fillMaxSize(),
    contentAlignment: Alignment = Alignment.Center
)
{
    Box (
        modifier = modifier,
        contentAlignment = contentAlignment
    ){
        if (text != null){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Loading...",
                    fontSize = 18.sp
                )
            }
        }else {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}