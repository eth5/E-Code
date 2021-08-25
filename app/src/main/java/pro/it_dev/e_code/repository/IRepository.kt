package pro.it_dev.e_code.repository

import pro.it_dev.e_code.domain.ECode

interface IRepository {
    fun getById(id:Int, result:(ECode)->Unit)
    fun getAll(result:(List<ECode>)->Unit)
    fun saveAll(list:List<ECode>, result:(Boolean)->Unit)
}