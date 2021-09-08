package pro.it_dev.e_code.presentation.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.domain.ECodeMinimal
import pro.it_dev.e_code.utils.convertToColor
import pro.it_dev.e_code.utils.fromHtml

@Composable
fun ECodeListEntry(vararg lines: String, bgColor: Color, size: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(size = size.dp)
            .padding(1.dp)
            .clickable(onClick = onClick),
        elevation = 10.dp,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
        backgroundColor = bgColor
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(2.dp)
        )
        {
            lines.forEach {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}