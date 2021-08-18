package pro.it_dev.e_code.di

import android.content.Context
import android.os.Environment
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pro.it_dev.e_code.data.IData
import pro.it_dev.e_code.data.RoomData
import pro.it_dev.e_code.data.room.ECodeDatabase
import pro.it_dev.e_code.device.DeviceScreen
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context:Context):IData {
        val path = context.filesDir.absolutePath
        return RoomData(Room.databaseBuilder(context, ECodeDatabase::class.java, "$path/ecode_base.db").build())
    }

//    @Singleton
//    @Provides
//    fun provideDeviceScreen(@ApplicationContext context: Context): DeviceScreen {
//        throw Error("")
//        return DeviceScreen(context = context)
//    }
}