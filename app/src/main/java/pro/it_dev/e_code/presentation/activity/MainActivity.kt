package pro.it_dev.e_code.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import pro.it_dev.e_code.ad.AdBannerUtil
import pro.it_dev.e_code.presentation.nav.Navigation
import pro.it_dev.e_code.presentation.ui.theme.ECodeTheme
import pro.it_dev.e_code.utils.AssetMover
import pro.it_dev.e_code.utils.Constants.DB_FILENAME
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var surfaceColor:MutableState<Color>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assetMoveData()
        setContent{
            ECodeTheme() {
                val color by remember{ surfaceColor }
                Surface(
                    color = color,
                    modifier = Modifier.fillMaxSize()
                    ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        AndroidView(
                            factory = { ctx ->
                                val adUtil = AdBannerUtil()
                                val adView = adUtil.createBanner(ctx,"ca-app-pub-6127542757275882/1103625381")
                                adUtil.initialBanner(ctx, adView)
                                adView
                            }
                        )
                        Navigation()
                    }
                }
            }
        }
    }

    private fun assetMoveData(){
        if (!dbIsPresent(applicationContext.filesDir.path + "/${DB_FILENAME}")){
            AssetMover().copyFromAssetsToLocalStorage(DB_FILENAME, applicationContext)
        }
    }
    private fun dbIsPresent(pathToFile:String):Boolean {
        val file = File(pathToFile)
        return file.isFile && file.exists()
    }
}
