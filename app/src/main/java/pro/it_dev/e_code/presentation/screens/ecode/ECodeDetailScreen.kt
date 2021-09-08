package pro.it_dev.e_code.presentation.screens.ecode

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pro.it_dev.e_code.R
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.presentation.views.FullBox
import pro.it_dev.e_code.presentation.views.ProgressBarInBox
import pro.it_dev.e_code.presentation.views.StateWrapper
import pro.it_dev.e_code.utils.Resource
import pro.it_dev.e_code.utils.convertToColor
import pro.it_dev.e_code.utils.fromHtml

@Composable
fun ECodeDetailScreen(eCodeID: Int, viewModel: ECodeViewModel = hiltViewModel()) {
    val eCode by produceState<Resource<ECode>>(
        initialValue = Resource.Loading(),
        producer = { value = viewModel.getECode(eCodeID) }
    )
    ECodeStateWrapper(eCode = eCode)
}

@Composable
fun ECodeStateWrapper(eCode: Resource<ECode>) {
    StateWrapper(
        state = eCode,
        onLoad = { ProgressBarInBox() },
        onError = {
            FullBox {
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
fun ECodeInfo(eCode: ECode, viewModel: ECodeViewModel = hiltViewModel()) {
    val eCodeColor = remember { eCode.color.convertToColor() }
    LaunchedEffect(eCodeColor){
        viewModel.surfaceColor.value = eCodeColor
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        eCodeColor,
                        MaterialTheme.colors.background,
                        eCodeColor
                    )
                )
            )
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(
                2.dp,
                eCodeColor
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState()),
                text = eCode.description.fromHtml().toString()
            )
        }

    }
}