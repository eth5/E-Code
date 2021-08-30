package pro.it_dev.e_code.presentation.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.it_dev.e_code.domain.ECodeMinimal
import pro.it_dev.e_code.repository.IRepository
import pro.it_dev.e_code.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private val originList = mutableListOf<ECodeMinimal>()

    val searchRequest = mutableStateOf("")

    private val _listECodes =
        mutableStateOf<Resource<List<ECodeMinimal>>>(Resource.Success(emptyList()))
    val listECodes: State<Resource<List<ECodeMinimal>>> get() = _listECodes

    init {
        loadECodes()
    }

    fun loadECodes() {
        if (_listECodes.value is Resource.Loading) return
        _listECodes.value = Resource.Loading()

        viewModelScope.launch {
            _listECodes.value = repository.getAllMinial()
            if (_listECodes.value is Resource.Success) originList.addAll(_listECodes.value.data!!)
        }
    }


    fun searchECodes(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = query.trim().lowercase()
            if (result.isEmpty()) {
                _listECodes.value = Resource.Success(originList)
                return@launch
            }
            val searched = mutableListOf<ECodeMinimal>()
            val dubl = mutableSetOf<String>()
            result.split(" ").forEach {

                val searchString = if (it.first() == 'e' || it.first() == 'е') //todo переписать логику поиска
                    if (it.length > 1) it.substring(1, it.length) else ""
                else it
                if (!dubl.contains(searchString)){
                    dubl.add(searchString)
                    searched.addAll(
                        originList.filter { it.code.startsWith(searchString) }
                    )
                }


            }
            _listECodes.value = Resource.Success(searched)
        }
    }
}