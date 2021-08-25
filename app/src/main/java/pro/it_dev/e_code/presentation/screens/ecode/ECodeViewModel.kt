package pro.it_dev.e_code.presentation.screens.ecode

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.repository.IRepository
import pro.it_dev.e_code.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ECodeViewModel @Inject constructor (
    private val  repository: IRepository
    ):ViewModel() {

    suspend fun getECode(id:Int): Resource<ECode>{
        return repository.getById(id)
    }
}