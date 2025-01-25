package co.kr.parkjonghun.google_pay_challenge.googlepay

import android.content.Context
import com.google.android.gms.wallet.IsReadyToPayRequest
import kotlinx.coroutines.tasks.await

object FetchCanUseGooglePayUseCase {
    suspend operator fun invoke(context: Context): Boolean {
        return GooglePayUtil.createPaymentsClient(context)
            .isReadyToPay(IsReadyToPayRequest.fromJson(GooglePayUtil.isReadyForRequest().toString()))
            .await()
    }
}