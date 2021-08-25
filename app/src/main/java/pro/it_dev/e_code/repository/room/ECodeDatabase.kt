package pro.it_dev.e_code.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.it_dev.e_code.domain.ECode

@Database(entities = [ECode::class], version = 1)
abstract class ECodeDatabase:RoomDatabase() {
    abstract fun dataECode(): DataECode
}