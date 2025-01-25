package co.kr.parkjonghun.google_pay_challenge

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import co.kr.parkjonghun.google_pay_challenge.googlepay.GooglePayUtil
import co.kr.parkjonghun.google_pay_challenge.ui.theme.GooglepaychallengeTheme
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.contract.TaskResultContracts.GetPaymentDataResult
import com.google.pay.button.PayButton
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    private val paymentLauncher = registerForActivityResult(GetPaymentDataResult()) {
        when (it.status.statusCode) {
            CommonStatusCodes.SUCCESS -> {
                // https://developers.google.com/pay/api/android/reference/response-objects#PaymentData
                // JSON으로 상세 정보 추출할 필요 있음.
                val paymentData: PaymentData? = it.result
                val billingName = paymentData?.toJson()?.let { json ->
                    JSONObject(json)
                        .getJSONObject("info")
                        .getJSONObject("billingAddress")
                        .getString("name")
                }
                Toast.makeText(this, "${billingName}의 결제가 성공했습니다.", LENGTH_SHORT)
            }

            else -> {
                Toast.makeText(this, "예기치 않은 이유로 결제가 실패했습니다.", LENGTH_SHORT)
            }
        }
    }

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
                            onClick = {
                                requestPayment()
                            },
                            allowedPaymentMethods = GooglePayUtil.isReadyForRequest().toString()
                        )
                    }
                }
            }
        }
    }

    private fun requestPayment() {
        val price = "49900"
        val request = PaymentDataRequest
            .fromJson(GooglePayUtil.getPaymentDataRequest(price).toString())
        lifecycleScope.launch {
            val client = GooglePayUtil.createPaymentsClient(this@MainActivity)
            paymentLauncher.launch(client.loadPaymentData(request))
        }
    }
}