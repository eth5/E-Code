package pro.it_dev.e_code.repository

import pro.it_dev.e_code.domain.ECode
import pro.it_dev.e_code.utils.Resource

interface IRepository {
    suspend fun getById(id: Int): Resource<ECode>
    suspend fun getAll(): Resource<List<ECode>>//todo переписать на получение только базовой инфо для листа
    suspend fun saveAll(list: List<ECode>): Resource<String>
}