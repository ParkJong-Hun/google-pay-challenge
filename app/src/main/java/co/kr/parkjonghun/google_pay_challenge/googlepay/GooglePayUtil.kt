package co.kr.parkjonghun.google_pay_challenge.googlepay

import android.content.Context
import co.kr.parkjonghun.google_pay_challenge.googlepay.config.GooglePayConstant.PAYMENTS_ENVIRONMENT
import co.kr.parkjonghun.google_pay_challenge.googlepay.config.CardAuthMethod
import co.kr.parkjonghun.google_pay_challenge.googlepay.config.CardNetwork
import co.kr.parkjonghun.google_pay_challenge.googlepay.config.GooglePayConstant
import co.kr.parkjonghun.google_pay_challenge.googlepay.config.gateway.Gateway
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object GooglePayUtil {
    // 내 앱에서 사용할 Google Pay API 버전 정보.
    private val apiVersion = JSONObject()
        .put("apiVersion", 2)
        .put("apiVersionMinor", 0)

    private val allowedPaymentMethod = getAllowedPaymentMethod()
        .put("tokenizationSpecification", getTokenizationSpecification(Gateway.ACPay))

    // 상점 정보.
    private val merchantInfo = JSONObject()
        .put("merchantName", "Jonghun cafe")

    /**
     * Google Pay로 결제를 처리하기 위해 Google Wallet과 상호작용하는 [PaymentsClient] 생성.
     * @return 해당 앱의 결제 방법을 포함하는 [PaymentsClient].
     */
    fun createPaymentsClient(context: Context): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(PAYMENTS_ENVIRONMENT)
            .build()

        return Wallet.getPaymentsClient(context, walletOptions)
    }

    /**
     * @return API 정보 + 결제 요청 수단 JSON 생성. 에러시 null.
     */
    fun isReadyForRequest(): JSONObject? = try {
        apiVersion.put("allowPaymentMethod", JSONArray().put(getAllowedPaymentMethod()))
    } catch (e: JSONException) {
        null
    }

    /**
     * @return 결제 요청 정보.
     */
    fun paymentDataRequest(priceLabel: String): JSONObject =
        apiVersion
            .put("allowedPaymentMethods", allowedPaymentMethod)
            .put("transactionInfo", getTransactionInfo(priceLabel))
            .put("merchantInfo", merchantInfo)
            .put("shippingAddressRequired", true)
            .put(
                "shippingAddressParameters", JSONObject()
                    .put("phoneNumberRequired", false)
                    .put("allowedCountryCodes", JSONArray(listOf("KR")))
            )

    // 결제제공업체의 결제토큰 정보.
    private fun getTokenizationSpecification(
        gateway: Gateway,
    ): JSONObject {
        return JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put(
                "parameters",
                gateway.toJSONObject(),
            )
        }
    }

    // 카드 지불수단 정보.
    private fun getAllowedPaymentMethod(): JSONObject =
        JSONObject()
            .put("type", "CARD")

            // https://developers.google.com/pay/api/android/reference/request-objects#CardParameters
            // 카드,
            // 신용카드, 체크카드만 허용 등 설정도 가능.
            // 보증 세부 정보 필수, 청구 주소 필수 설정도 가능.
            .put(
                "parameters", JSONObject()
                    .put("allowedAuthMethods", CardAuthMethod.entries)
                    .put("allowedCardNetworks", CardNetwork.entries)
                    .put("billingAddressRequired", true)
                    .put(
                        "billingAddressParameters", JSONObject()
                            .put("format", "FULL")
                    )
            )

    // 거래 정보.
    private fun getTransactionInfo(price: String): JSONObject =
        JSONObject()
            .put("totalPrice", price)
            .put("totalPriceStatus", "FINAL")
            .put("countryCode", GooglePayConstant.COUNTRY_CODE)
            .put("currencyCode", GooglePayConstant.CURRENCY_CODE)
}