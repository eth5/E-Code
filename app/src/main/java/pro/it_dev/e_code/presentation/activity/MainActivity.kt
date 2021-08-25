package pro.it_dev.e_code.presentation.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import dagger.hilt.android.AndroidEntryPoint
import pro.it_dev.e_code.R
import pro.it_dev.e_code.ad.createBanner
import pro.it_dev.e_code.ad.initialBanner
import pro.it_dev.e_code.presentation.nav.Navigation
import pro.it_dev.e_code.presentation.ui.theme.ECodeTheme
import pro.it_dev.e_code.repository.IRepository
import pro.it_dev.e_code.utils.AssetMover
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var repository: IRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adContent = findViewById<ViewGroup>(R.id.adContent)

        findViewById<ComposeView>(R.id.main_container).apply {
            setContent{
                ECodeTheme() {
                    Surface(color = MaterialTheme.colors.background) {
                        adContent.setBackgroundColor(MaterialTheme.colors.background.toArgb())
                        Navigation()
                    }
                }
            }
        }
        assetMoveData()
        initialAd(adContent)
    }

    private fun initialAd(viewGroup: ViewGroup){
        val adView = createBanner(this)
        viewGroup.addView(adView)
        initialBanner(this, adView)
    }
    private fun assetMoveData(){
        AssetMover().copyFromAssetsToLocalStorage("ecode_base.db", applicationContext)
    }
}
