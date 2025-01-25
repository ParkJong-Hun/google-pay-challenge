package co.kr.parkjonghun.google_pay_challenge.googlepay.config.gateway

import org.json.JSONObject

/**
 * 결제제공업체의 게이트웨이 사양.
 * @param gateway GooglePay에서 사용할 게이트웨이 이름
 * @param gatewayExtraInfo [GatewayExtraInfo]
 */
enum class Gateway(
    val gateway: String,
    val gatewayExtraInfo: GatewayExtraInfo,
) {
    ACPay(
        gateway = "acpay",
        gatewayExtraInfo = GatewayExtraInfo.Default(TODO("puy my gateway merchant id")),
    ),
    ;

    public fun toMap(): Map<String, String> = mapOf("gateway" to gateway) + gatewayExtraInfo.map()
    public fun toJSONObject(): JSONObject = JSONObject(toMap())
}
