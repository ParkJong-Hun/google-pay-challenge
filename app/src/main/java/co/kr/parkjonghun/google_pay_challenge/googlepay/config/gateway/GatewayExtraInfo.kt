package co.kr.parkjonghun.google_pay_challenge.googlepay.config.gateway

/**
 * GooglePay에서 사용할 게이트웨이 추가 정보.
 */
sealed interface GatewayExtraInfo {
    fun map(): Map<String, String>

    data class Default(
        val gatewayMerchantId: String,
    ) : GatewayExtraInfo {
        override fun map() = mapOf("gatewayMerchantId" to gatewayMerchantId)
    }

    data class Other(
        val info: Map<String, String>,
    ) : GatewayExtraInfo {
        override fun map(): Map<String, String> = info
    }
}
