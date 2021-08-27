package pro.it_dev.e_code.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.domain.ECodeMinimal
import pro.it_dev.e_code.repository.IRepository
import pro.it_dev.e_code.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val  repository: IRepository) : ViewModel() {

    private val map = mutableMapOf<String,ECodeMinimal>()
    private val originSet = mutableSetOf<ECodeMinimal>()

    private val _filteredString = MutableLiveData<String>()

    private val _filteredECodes = MutableLiveData<List<ECodeMinimal>>()

    private val _eCodes = MutableLiveData<Resource<List<ECodeMinimal>>>(Resource.Loading())
    val eCodes:LiveData<Resource<List<ECodeMinimal>>> get() = _eCodes

    val filteredECodes:LiveData<List<ECodeMinimal>> get() = _filteredECodes
    val filteredString:LiveData<String> get()= _filteredString

    init {
        viewModelScope.launch {
            val list = repository.getAllMinial()
            if (list is Resource.Success){
                originSet.clear()
                originSet.addAll(list.data!!)
                listToMap(
                    collection = originSet,
                    map = map
                )
            }
            _eCodes.value = list
        }
    }
    private fun listToMap(collection:Collection<ECodeMinimal>, map:MutableMap<String,ECodeMinimal>){
        collection.forEach {
            if (map.containsKey(it.code)) throw IllegalStateException("Double key in map! ${it.code}")
            map[it.code] = it
        }
    }

    fun filterECodes(value:String){
        _filteredString.value = value
        val filterText = value.trim().lowercase()
        if (filterText.isEmpty()){
            _filteredECodes.value = emptyList()
            return
        }
        val filterSet = mutableSetOf<ECodeMinimal>()
        filterText.split(" ").forEach {
            val d = if ( it.first() == 'e' || it.first() == 'е' )
                if (it.length > 1) it.substring(1, it.length)  else ""
            else it
            originSet
                .filter { it.code.startsWith( d ) }
                .forEach { filterSet.add( it ) }
        }
        _filteredECodes.value = filterSet.toList()
    }

}