package pro.it_dev.e_code.presentation.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import dagger.hilt.android.AndroidEntryPoint
import pro.it_dev.e_code.R
import pro.it_dev.e_code.ad.createBanner
import pro.it_dev.e_code.ad.initialBanner
import pro.it_dev.e_code.data.IData
import pro.it_dev.e_code.data.utils.AssetMover
import pro.it_dev.e_code.presentation.activity.ui.theme.ECodeTheme
import pro.it_dev.e_code.presentation.nav.Navigation
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var data: IData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ComposeView>(R.id.main_container).apply {
            setContent{
                ECodeTheme() {
                    Surface(color = MaterialTheme.colors.background) {
                        Navigation() }
                    }
                }

        }
        assetMoveData()
        initialAd(findViewById<ViewGroup>(R.id.adContent))
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
