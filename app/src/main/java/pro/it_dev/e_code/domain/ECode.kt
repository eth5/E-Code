package pro.it_dev.e_code.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "e_code")
class ECode {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0
    @ColumnInfo(name = "code")
    var code:String = ""
    @ColumnInfo(name = "name")
    var name:String = ""
    @ColumnInfo(name = "color")
    var color:String = ""
    @ColumnInfo(name = "danger_status")
    var dangerStatus:String = ""
    @ColumnInfo(name = "description")
    var description:String = ""
    @ColumnInfo(name = "url")
    var url:String?=null
}