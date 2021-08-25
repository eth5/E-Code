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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pro.it_dev.e_code.R
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.presentation.views.ProgressBarInBox
import pro.it_dev.e_code.presentation.views.StateWrapper
import pro.it_dev.e_code.utils.Resource
import pro.it_dev.e_code.utils.convertToColor
import pro.it_dev.e_code.utils.fromHtml

@Composable
fun ECodeScreen(eCodeID: Int, viewModel: ECodeViewModel = hiltViewModel()) {
    val eCode = produceState<Resource<ECode>>(
        initialValue = Resource.Loading(),
        producer = { value = viewModel.getECode(eCodeID) }
    ).value
    ECodeStateWrapper(eCode = eCode)
}

@Composable
fun ECodeStateWrapper(eCode: Resource<ECode>) {
    StateWrapper(
        state = eCode,
        onLoad = { ProgressBarInBox() },
        onError = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it.message ?: LocalContext.current.getString(R.string.unknown_error),
                    color = Color.Red
                )
            }
        }
    ) {
        ECodeInfo(eCode = eCode.data!!)
    }
}

@Composable
fun ECodeInfo(eCode: ECode) {

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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(5.dp)
                    .verticalScroll(rememberScrollState())
            ) {
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