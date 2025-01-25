package co.kr.parkjonghun.google_pay_challenge.googlepay.config

import org.json.JSONArray

/**
 * 지불 카드 네트워크.
 */
enum class CardNetwork(val rawValue: String) {
    VISA("VISA"),
    MasterCard("MASTERCARD"),
    JCB("JCB"),
    ;

    fun toJsonArray() = JSONArray(entries)
}
