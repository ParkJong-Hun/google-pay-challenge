package co.kr.parkjonghun.google_pay_challenge.googlepay.config

import com.google.android.gms.wallet.WalletConstants

object GooglePayConstant {
    // 이 것을 ENVIRONMENT_PRODUCTION로 변경하면 API가 청구 가능한 카드 정보를 반환.
    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

    const val COUNTRY_CODE = "KR"
    const val CURRENCY_CODE = "KRW"
}