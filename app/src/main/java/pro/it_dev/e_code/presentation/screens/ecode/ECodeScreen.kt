package pro.it_dev.e_code.presentation.screens.ecode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.utils.convertToColor
import pro.it_dev.e_code.utils.fromHtml

@Composable
fun ECodeScreen(eCodeID:Int, viewModel: ECodeViewModel) {
    viewModel.loadECode(eCodeID)
    val eCode by viewModel.eCode.observeAsState(null)
    if (eCode==null){
        FullScreenText(text = "Loading...")
        return
    }
    else
        DrawECode(eCode = eCode!!)
}

@Composable
fun FullScreenText(text:String){
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = text)
    }
}

@Composable
fun DrawECode(eCode:ECode){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(eCode.color.convertToColor())
    ) {
        // ECodeCard("E${eCode.code}", bgColor = Color.Green, size = 50) {}
        Card(
            modifier = Modifier
                .padding(5.dp)
                // .background(Color.Red)
                .fillMaxSize(),
            shape = RoundedCornerShape(10.dp),// MaterialTheme.shapes.small
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(5.dp)
                    .verticalScroll(rememberScrollState())
            ){
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    text = eCode.description.fromHtml().toString()
                )
            }

        }

    }
}