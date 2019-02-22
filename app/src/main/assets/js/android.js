/**
 * Created by xulu on 2017/12/6.
 *
 */

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function hello(msg) {
    alert('hello android' + msg)
}

var _open = function (a) {
    Open_Paper_Money();
};
var _close = function (a) {
    Close_Paper_Money();
};
var _pay = function (m) {
    //收钱通知
    vve.getMoney(m)
};

//手机号验证
function checkPhone(phone) {
    if (!(/^1[345789]\d{9}$/.test(phone))) {
        toast("手机号码有误,请重填");
        return false;
    }
    return true;
}
//获取当前时间 yyyy-MM-dd HH:mm:ss
function currentTime() {
    //var date = new Date();
    //return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    return new Date().Format("yyyy-MM-dd hh:mm:ss");
}

/**
 * ========================================================================================================================
 * //android调用JS方法
 */

function NotifyNetState(netstate) {
    vve.netState(netstate);
}
function QuerryAdavance(result) {
    vve.openAdvance(result);
}
function QuerryAdavanceAbNormal(result) {
    vve.QuerryAdavanceAbNormal(result);
}
function NotifyCallStatus(result) {
    vve.NotifyCallStatus(result);
}
function NotifyCheckPwd(msg) {
    vve.NotifyCheckPwd(msg);
}
function getMoney(m) {
    vve.getMoney(m);
}
function getMacAddress(m) {
    vve.getMacAddress(m);
}
function ScanResult(result) {
    vve.ScanResult(result);
}
function updateSwiper(urls) {
    vve.updateSwiper(urls);
}
function getCheckStatus(check) {
    vve.getCheckStatus(check);
}
function CurrentVersion(versionname) {
    vve.CurrentVersion(versionname);
}
function OpenScanResult(msg) {
    vve.OpenScanResult(msg);
}
function ShowLoading(msg) {
    vve.ShowLoading(msg);
}
function HideLoading(msg) {
    vve.HideLoading(msg);
}
function AutoLogin(account, password, macadress) {
    vve.AutoLogin(account, password, macadress);
}
function NotReportOrders(orders) {
    vve.NotReportOrders(orders);
}
function getVersionCode(versioncode) {
    versionCode = versioncode;
}
function AutoJump(url) {
    vve.AutoJump(url);
}

function responseChangeSpecieCount(count){
	vve.responseChangeSpecieCount(count);
}

/**
 * ========================================================================================================================
 * //JS调用android方法
 */
function RequestSpecieCount(){
	window.AndroidMethod.RequestSpecieCount();
}

function RequestSpecieOut(msg){
	window.AndroidMethod.RequestSpecieOut(msg);
}

function toast(msg) {
    window.AndroidMethod.Toast(msg);
}
var versionCode = 1;
function CreateOrder(orderId, carNumber, payTime, orderPrice, currentPrice, oid) {
    if (versionCode >= 1106) {
        window.AndroidMethod.CreateOrder(orderId, carNumber, payTime, orderPrice, currentPrice, oid)
    } else {
        window.AndroidMethod.CreateOrder(orderId, carNumber, payTime, orderPrice, currentPrice)
    }
}
function UpdateOrderMoney(orderId, cash, getMoney, time, plate_number, order_price, order_price, oid) {
    if (versionCode >= 1106) {
        window.AndroidMethod.UpdateOrderMoney(orderId, cash, getMoney, time, plate_number, order_price, order_price, oid);
    } else {
        window.AndroidMethod.UpdateOrderMoney(orderId, cash, getMoney);
    }

}
function IsPay(orderId, isPay) {
    window.AndroidMethod.IsPay(orderId, isPay);
}
function IsCharge(orderId, IsCharge) {
    window.AndroidMethod.IsCharge(orderId, IsCharge);
}
function GetBoxMoney() {
    window.AndroidMethod.GetBoxMoney();
}
function AbNormalOrder() {
    window.AndroidMethod.AbNormalOrder();
}
function clearOrders() {
    window.AndroidMethod.clearOrders();
}
function CallHost() {
    window.AndroidMethod.CallHost();
}
function CallHost_LinePhone() {
    window.AndroidMethod.CallHost_LinePhone();
}
function Regist_LinePhone(account, pwd, domain, host) {
    window.AndroidMethod.Regist_LinePhone(account, pwd, domain, host);
}
function UpdateUser(account, pwd) {
    window.AndroidMethod.UpdateUser(account, pwd);
}
function IdentifyPwd(pwd) {
    window.AndroidMethod.IdentifyPwd(pwd);
}
function UpdateUser_Exit() {
    window.AndroidMethod.UpdateUser_Exit();
}
function Open_Paper_Money() {
    window.AndroidMethod.Open_Paper_Money();
}
function Close_Paper_Money() {
    window.AndroidMethod.Close_Paper_Money();
}
function LockBar() {
    window.AndroidMethod.LockBar();
}
function UnLockBar() {
    window.AndroidMethod.UnLockBar();
}
function Reload() {
    window.AndroidMethod.Reload();
}
function Print(title, park, terminal, carnumber, prepay, paytype, paytime, tale) {
    window.AndroidMethod.Print(title, park, terminal, carnumber, prepay, paytype, paytime, tale);
}
function Print2(title, park, terminal, carnumber, prepay, paytype, paytime, tale, returncash, url) {
    console.log("Print2  开始");
    window.AndroidMethod.Print(title, park, terminal, carnumber, prepay, paytype, paytime, tale, returncash, url);
}

function Print3(title, park, terminal, carnumber, prepay, paytype, paytime, tale ,returncash) {
    window.AndroidMethod.Print(title, park, terminal, carnumber, prepay, paytype, paytime, tale,returncash);
}


function ScanOpen() {
    window.AndroidMethod.ScanOpen();
}
function ScanClose() {
    window.AndroidMethod.ScanClose();
}
function MacAddress() {
    window.AndroidMethod.MacAddress();
}
function checkUpdate() {
    //window.AndroidMethod.checkUpdate();
}
function ClearCache() {
    window.AndroidMethod.ClearCache();
}
function StartGuard() {
    window.AndroidMethod.StartGuard();
}
function StopGuard() {
    window.AndroidMethod.StopGuard();
}
function IsReport(orderid, isreport) {
    if (versionCode >= 1106) {
        window.AndroidMethod.IsReport(orderid, isreport);
    }
}
function NotReportOrders_an() {
    if (versionCode >= 1106) {
        window.AndroidMethod.NotReportOrders();
    }
}
function setTradeNo(orderid, tradeNo) {
    if (versionCode >= 1106) {
        window.AndroidMethod.setTradeNo(orderid, tradeNo);
    }

}
function WriteLog(msg) {
    if (versionCode >= 1106) {
        window.AndroidMethod.WriteLog(msg);
    }

}
function RememberURL(url){
    window.AndroidMethod.RememberURL(url);
}
