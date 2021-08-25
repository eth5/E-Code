package pro.it_dev.e_code.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AssetMover {
    fun copyFromAssetsToLocalStorage(fileName:String, context:Context){
        val inputStream = context.assets.open(fileName)
        val nameSplit = fileName.split(".")
        val name = nameSplit[0]
        val extension = nameSplit[1]
        inputStream.getFilePath(context.filesDir, name, extension)
    }

    private fun InputStream.getFilePath(dir: File, name: String, extension: String): String {
        val file = File(dir, "$name.$extension")
        val outputStream = FileOutputStream(file)
        this.copyTo(outputStream, 4096)
        outputStream.close()
        return file.absolutePath
    }

}