package pro.it_dev.e_code.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pro.it_dev.e_code.utils.Resource

@Composable
fun <T>StateWrapper(
	state: Resource<T>,
	modifier: Modifier = Modifier,
	onLoad:@Composable ()->Unit,
	onError:@Composable (Resource.Error<T>)->Unit,
	onSuccess:@Composable (Resource.Success<T>)->Unit
){
	Box(
		modifier = modifier
	){
		when(state){
			is Resource.Success -> onSuccess(state)
			is Resource.Loading -> onLoad()
			is Resource.Error -> onError(state)
		}
	}
}