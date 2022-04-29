CAFE24.MOBILE_WEB = false; var mobileWeb = CAFE24.MOBILE_WEB;
var bUseElastic = false;
var sSearchBannerUseFlag = 'F';
$(function() {
$('.boardListAll').click(function() {
if ( this.checked ) {
$('.boardChk').prop('checked', true);
} else {
$('.boardChk').prop('checked', false);
}
});
});
$(function() {
$('.boardListAll').click(function() {
if ( this.checked ) {
$('.boardChk').prop('checked', true);
} else {
$('.boardChk').prop('checked', false);
}
});
});
var bIsUseSpread = false;
var sIsSecret = false;
var iBoardNo = "5";
$(function(){
FwValidator.bind("form_0639814166", false);
});
var aLogData = {"log_server1":"eclog2-225.cafe24.com","log_server2":"elg-db-svcm-285.cafe24.com","mid":"rlgh2587","stype":"e","domain":"","shop_no":1,"lang":"ko_KR","ver":2,"hash":"e6f690e2ce8a0f4f2a79eb2bdaa52ea8","ca":"cfa-js.cafe24.com\/cfa.js","etc":""};
var sMileageName = '적립금';
var sMileageUnit = '[:PRICE:]원';
var sDepositName = '예치금';
var sDepositUnit = '원';
CAFE24.SHOP_CURRENCY_INFO = {"1":{"aShopCurrencyInfo":{"currency_code":"KRW","currency_no":"410","currency_symbol":"\uffe6","currency_name":"South Korean won","currency_desc":"\uffe6 \uc6d0 (\ud55c\uad6d)","decimal_place":0,"round_method_type":"F"},"aShopSubCurrencyInfo":null,"aBaseCurrencyInfo":{"currency_code":"KRW","currency_no":"410","currency_symbol":"\uffe6","currency_name":"South Korean won","currency_desc":"\uffe6 \uc6d0 (\ud55c\uad6d)","decimal_place":0,"round_method_type":"F"},"fExchangeRate":1,"fExchangeSubRate":null,"aFrontCurrencyFormat":{"head":"","tail":"\uc6d0"},"aFrontSubCurrencyFormat":{"head":"","tail":""}}}; var SHOP_CURRENCY_INFO = CAFE24.SHOP_CURRENCY_INFO;
var EC_ASYNC_LIVELINKON_ID = '';
if ($('[async_section=before]').length > 0) {
$('[async_section=before]').addClass('displaynone');
}