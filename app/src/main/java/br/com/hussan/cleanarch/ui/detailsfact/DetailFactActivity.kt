package br.com.hussan.cleanarch.ui.detailsfact

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.hussan.cleanarch.AppNavigator
import br.com.hussan.cleanarch.data.model.FactViewModel

class DetailFactActivity : AppCompatActivity() {

    lateinit var factModel: FactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factModel = intent.getParcelableExtra(AppNavigator.FACT)!!
        setContent {
            showFact()
        }
    }

    @Preview
    @Composable
    private fun showFact() {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = factModel.value,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
