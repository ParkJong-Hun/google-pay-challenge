package co.kr.parkjonghun.google_pay_challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import co.kr.parkjonghun.google_pay_challenge.googlepay.FetchCanUseGooglePayUseCase
import co.kr.parkjonghun.google_pay_challenge.googlepay.GooglePayUtil
import co.kr.parkjonghun.google_pay_challenge.ui.theme.GooglepaychallengeTheme
import com.google.pay.button.PayButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GooglepaychallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        PayButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = FetchCanUseGooglePayUseCase(),
                            allowedPaymentMethods = GooglePayUtil.isReadyForRequest().toString()
                        )
                    }
                }
            }
        }
    }

    private fun requestPayment() {
        GooglePayUtil.
    }
}