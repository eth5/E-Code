package pro.it_dev.e_code.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pro.it_dev.e_code.data.IData
import pro.it_dev.e_code.domain.ECode
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val  data: IData) : ViewModel() {

    private val map = mutableMapOf<String,ECode>()
    private val originSet = mutableSetOf<ECode>()

    private val _filteredString = MutableLiveData<String>()

    private val _filteredECodes = MutableLiveData<List<ECode>>()
    private val _eCodes = MutableLiveData<List<ECode>>()
    val eCodes:LiveData<List<ECode>> get() = _eCodes
    val filteredECodes:LiveData<List<ECode>> get() = _filteredECodes
    val filteredString:LiveData<String> get()= _filteredString

    init {
        viewModelScope.launch {
            data.getAll {
                originSet.clear()
                originSet.addAll(it)
                updateLiveData(originSet)
                listToMap(collection = originSet, map = map)
            }
        }
    }
    private fun listToMap(collection:Collection<ECode>, map:MutableMap<String,ECode>){
        collection.forEach {
            println("Add to map ${it.code}")
            if (map.containsKey(it.code)) throw IllegalStateException("Double key in map! ${it.code}")
            map[it.code] = it
        }
    }
    private fun updateLiveData(collection:Collection<ECode>){
        _eCodes.postValue(collection.toList())
    }

    fun filterECodes(value:String){
        _filteredString.value = value
        val filterText = value.trim().lowercase()
        if (filterText.isEmpty()){
            _filteredECodes.value = emptyList()
            return
        }
        val filterSet = mutableSetOf<ECode>()
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