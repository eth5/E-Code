package pro.it_dev.e_code.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import pro.it_dev.e_code.domain.ECode

@Dao
interface DataECode {
    @Query("SELECT * FROM e_code")
    fun getAllECodes(): List<ECode>

    @Query("SELECT * FROM e_code WHERE `id` IN (:id)")
    fun getECodeById(id: Int): ECode

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllECodes(vararg eCode: ECode)

    @Delete
    fun deleteECode(eCode: ECode)
}