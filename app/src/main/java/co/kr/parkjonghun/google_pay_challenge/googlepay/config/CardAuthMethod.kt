package co.kr.parkjonghun.google_pay_challenge.googlepay.config

import org.json.JSONArray

/**
 * 카드 인증 수단.
 * https://developers-jp.googleblog.com/2022/12/google-pay.html
 */
enum class CardAuthMethod(val rawValue: String) {
    /*  구글 계정에 결제 자격 증명을 저장.
        여러 기기에서 사용 가능.
        SCA 요구 사항을 준수하려면 3D Secure 필요.
     */
    PanOnly("PAN_ONLY"),

    /*  Android 기기에 결제 자격 증명을 연결.
        Google Chrome을 사용하는 Anroid 기기에서 3D Secure가 아닌 흐름을 통해서만 처리 가능.
        SCA 규정을 준수해, 3D 보안 처리 필요 없음.
        더 큰 보안 제공(특정 [CardNetwork]의 책임 전환 자격을 갖춤).
     */
    Cryptogram3DS("CRYPTOGRAM_3DS"),
    ;

    fun toJSONArray() = JSONArray(entries)
}