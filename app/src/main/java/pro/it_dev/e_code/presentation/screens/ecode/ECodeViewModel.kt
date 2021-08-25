package pro.it_dev.e_code.presentation.screens.ecode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.it_dev.e_code.repository.IRepository
import pro.it_dev.e_code.domain.ECode
import javax.inject.Inject

@HiltViewModel
class ECodeViewModel @Inject constructor(private val  repository: IRepository):ViewModel() {
    private val _eCode = MutableLiveData<ECode>()
    val eCode:LiveData<ECode> get() = _eCode

    fun loadECode(id:Int){
        repository.getById(id){
            _eCode.postValue(it)
        }
    }
}