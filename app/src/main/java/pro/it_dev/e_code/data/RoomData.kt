package pro.it_dev.e_code.data

import kotlinx.coroutines.*
import pro.it_dev.e_code.data.room.ECodeDatabase
import pro.it_dev.e_code.domain.ECode

class RoomData(private val roomDatabase: ECodeDatabase):IData {

    override fun getAll(result: (List<ECode>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val list = runBlocking (Dispatchers.IO){
                roomDatabase.dataECode().getAllECodes()
            }

            result.invoke(list)
        }
    }

    override fun getById(id: Int, result: (ECode) -> Unit) {
        GlobalScope.launch {
            val eCode = runBlocking (Dispatchers.IO){
                roomDatabase.dataECode().getECodeById(id)
            }
            result.invoke(eCode)
        }
    }

    override fun saveAll(list: List<ECode>, result: (Boolean) -> Unit) {
        GlobalScope.launch { // todo refactor scope
            roomDatabase.dataECode().insertAllECodes( *list.toTypedArray() )
            result.invoke(true)
        }
    }
}