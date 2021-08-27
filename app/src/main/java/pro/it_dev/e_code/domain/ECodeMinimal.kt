package pro.it_dev.e_code.domain

import androidx.room.ColumnInfo

data class ECodeMinimal (
    var id:Long,
    var code:String,
    var name:String,
    var color:String,
    @ColumnInfo(name = "danger_status")
    var dangerStatus:String
)