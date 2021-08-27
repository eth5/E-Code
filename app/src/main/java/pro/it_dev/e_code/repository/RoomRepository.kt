package pro.it_dev.e_code.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.domain.ECodeMinimal
import pro.it_dev.e_code.repository.room.ECodeDatabase
import pro.it_dev.e_code.utils.Resource

class RoomRepository(private val roomDatabase: ECodeDatabase) : IRepository {
    override suspend fun getAllMinial(): Resource<List<ECodeMinimal>> {
        return try {
            val eCode = (Dispatchers.IO){ roomDatabase.dataECode().getAllECodesMinimal() }
            Resource.Success(eCode)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getById(id: Int): Resource<ECode> {
        return try {
            val eCode = (Dispatchers.IO){ roomDatabase.dataECode().getECodeById(id) }
            Resource.Success(eCode)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun saveAll(list: List<ECode>): Resource<String> {
        return try {
            roomDatabase.dataECode().insertAllECodes(*list.toTypedArray())
            Resource.Success("Ok")
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}