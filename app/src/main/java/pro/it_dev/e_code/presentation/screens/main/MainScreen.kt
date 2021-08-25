package pro.it_dev.e_code.presentation.screens.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import pro.it_dev.e_code.R
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.presentation.nav.Screen
import pro.it_dev.e_code.presentation.views.ECodeListEntry
import pro.it_dev.e_code.presentation.views.ECodeLine
import pro.it_dev.e_code.presentation.views.MyTextField
import pro.it_dev.e_code.presentation.views.StateWrapper
import pro.it_dev.e_code.utils.Resource
import pro.it_dev.e_code.utils.convertToColor

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel= hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            val list by viewModel.eCodes.observeAsState(Resource.Loading())
            ECodeListStateWrapper(eCodeList = list, navController = navController)
        }
        Box(
            modifier = Modifier
                .background(Color.DarkGray)
        ) {
            val list by viewModel.filteredECodes.observeAsState(initial = emptyList())
            FilterTextField(list, viewModel.filteredString, filter = viewModel::filterECodes){
                navController.navigate(Screen.ECodeScreen.route + "/${it.id}")
            }
        }

    }
}

@Composable
fun FilterTextField(
    list: List<ECode>,
    filterText: LiveData<String>,
    filter: (String) -> Unit,
    onClick: (ECode) -> Unit
) {

    Column(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.Bottom)
            .padding(1.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (list.isNotEmpty()) {
            val size = 50
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .wrapContentHeight(align = Alignment.Bottom),
                propagateMinConstraints = true
            ) {
                ResultOfFind(list = list, size = size, onClick = onClick)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            var text by remember { mutableStateOf(filterText.value ?: "") }
            // val text by filterText.observeAsState("")
            MyTextField(
                value = text,
                label = LocalContext.current.getString(R.string.find)
            ) {
                text = it
                filter.invoke(text)
            }
            if (text.isNotEmpty()) {
                Button(
                    onClick = {
                        text = ""
                        filter.invoke(text)
                              },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.clear)
                        )
                }
            }
        }
    }
}

@Composable
fun ECodeListStateWrapper(eCodeList:Resource<List<ECode>>, navController: NavController) {
    StateWrapper(
        state = eCodeList,
        onLoad = {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        },
        onError = {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = it.message ?: LocalContext.current.getString(R.string.unknown_error),
                    color = Color.Red
                    )
            }
        }
    ) {
        GridECodeList(list = it.data!!, 60) {
            navController.navigate(Screen.ECodeScreen.route + "/${it.id}")
        }
    }
}

@Composable
fun ResultOfFind(list: List<ECode>,size:Int, onClick: (ECode) -> Unit){
    RowECode(list = list, size = size, onClick = onClick)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowECode(list: List<ECode>, size: Int, onClick: (ECode) -> Unit) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(
            contentPadding = PaddingValues(2.dp)
        ) {
            items(list) {
                ECodeLine(eCode = it, size) {
                    onClick.invoke(it)
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridECodeList(list: List<ECode>, size: Int, onClick: (ECode) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = size.dp),
        contentPadding = PaddingValues(5.dp),
    ) {
        items(list) {
            ECodeListEntry(
                "E${it.code}",
                it.name,
                bgColor = it.color.convertToColor(),
                size = size
            ) {
                onClick.invoke(it)
            }
        }
    }
}
