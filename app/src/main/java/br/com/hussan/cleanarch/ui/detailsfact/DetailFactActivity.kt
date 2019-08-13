package br.com.hussan.cleanarch.ui.detailsfact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.hussan.cleanarch.AppNavigator
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.data.model.FactView
import br.com.hussan.cleanarch.databinding.ActivityDetailsFactBinding

class DetailFactActivity : AppCompatActivity() {

    lateinit var fact: FactView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailsFactBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_details_fact
        )

        fact = intent.getParcelableExtra(AppNavigator.FACT)
        binding.fact = fact
    }

}
