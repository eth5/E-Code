package pro.it_dev.e_code.presentation.screens.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pro.it_dev.e_code.R
import pro.it_dev.e_code.domain.ECodeMinimal
import pro.it_dev.e_code.presentation.nav.Screen
import pro.it_dev.e_code.presentation.views.ECodeListEntry
import pro.it_dev.e_code.presentation.views.FullBox
import pro.it_dev.e_code.presentation.views.ProgressBarInBox
import pro.it_dev.e_code.presentation.views.StateWrapper
import pro.it_dev.e_code.utils.Resource
import pro.it_dev.e_code.utils.convertToColor

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel = hiltViewModel()) {
    val color = MaterialTheme.colors.surface
    LaunchedEffect(color){
        viewModel.surfaceColor.value = color
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val searchRequest by remember { viewModel.searchRequest }
        if (searchRequest.isNotEmpty()) {
            Text(
                text = LocalContext.current.getString(R.string.search_result),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val list by remember { viewModel.listECodes }
            ECodeListStateWrapper(eCodeList = list, navController = navController)
        }
        SearchBar(viewModel.searchRequest, modifier = Modifier.fillMaxWidth()) {
            viewModel.searchECodes(it)
        }
    }
}

@Composable
fun SearchBar(
    searchRequest: MutableState<String>,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier.padding(1.dp)
    ) {
        var text by remember { searchRequest }
        var isHintDisplayed by remember { mutableStateOf(true) }
        var borderSize by remember{ mutableStateOf(1.dp) }
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
                text = it
            },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .border(borderSize, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .padding(end = 40.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                    borderSize = if (isHintDisplayed) 1.dp else 3.dp
                }
        )
        if (text.isNotEmpty()) {
            TextButton(
                onClick = {
                    text = ""
                    onValueChange(text)
                },
                modifier = Modifier
                    //.padding(5.dp)
                    .align(Alignment.CenterEnd)
            ) {
                var pad by remember { mutableStateOf((-15).dp) }
                val anim by animateDpAsState(
                    targetValue = pad,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy
                    )
                )
                LaunchedEffect(key1 = "", block = { pad = 0.dp })
                Text(
                    text = LocalContext.current.getString(R.string.clear),
                    modifier = Modifier.offset(x = anim)
                )
            }
        }
        if (isHintDisplayed && text.isEmpty()) {
            Text(
                text = "Search",
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }
    }

}


@Composable
fun ECodeListStateWrapper(eCodeList: Resource<List<ECodeMinimal>>, navController: NavController) {
    StateWrapper(
        state = eCodeList,
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
        GridECodeList(list = it.data!!, 60) {
            navController.navigate(Screen.ECodeScreen.route + "/${it.id}")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridECodeList(list: List<ECodeMinimal>, size: Int, onClick: (ECodeMinimal) -> Unit) {
    if (list.isEmpty()) {
        Text(
            text = LocalContext.current.getString(R.string.not_found),
            color = MaterialTheme.colors.primary
        )
    } else {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .border(2.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
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
    }
}
