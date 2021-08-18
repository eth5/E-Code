package pro.it_dev.e_code.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.utils.convertToColor
import pro.it_dev.e_code.utils.fromHtml

@Composable
fun ECodeCard(vararg lines: String, bgColor: Color, size: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(size = size.dp)
            .padding(1.dp)
            .clickable(onClick = onClick),
        elevation = 10.dp,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color.Black),
        backgroundColor = bgColor
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(5.dp)
        )
        {
            lines.forEach {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun ECodeLine(eCode: ECode, size: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(1.dp)
            .clickable(onClick = onClick),
        elevation = 15.dp,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(1.dp)
        )
        {
            Row(
                modifier = Modifier
                    .padding(1.dp)
            ) {
                ECodeCard(
                    "E${eCode.code}",
                    bgColor = eCode.color.convertToColor(),
                    size = size
                ) {}
                Column(
                    modifier = Modifier.padding(2.dp)
                ) {
                    Row {
                        Text(
                            text = eCode.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.padding(1.dp))
                        Text(
                            text = eCode.dangerStatus,
                            maxLines = 1,
                            fontSize = 12.sp,
                            color = eCode.color.convertToColor()
                        )
                    }
                    Text(
                        text = eCode.description.fromHtml().toString(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 11.sp
                    )

                }
            }

        }
    }
}
@Composable
fun MyTextField(value:String, onChange: (String)->Unit){
    TextField(
        value = value,
        onValueChange = {
            onChange.invoke(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .background(color = Color.Yellow),
        maxLines = 1,
        textStyle = TextStyle(
            fontSize = 18.sp
        )
    )
}