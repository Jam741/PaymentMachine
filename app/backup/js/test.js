// new_element=document.createElement_x("script");
// new_element.setAttribute("type","text/javascript");
// new_element.setAttribute("src","method.js");// 在这里引入了a.js
// document.body.appendChild(new_element);
CarNumberHead = '苏';
const TimeOutMil = 20000;
const TimeOutMsg = '请求超时，请重试';
vve = new Vue({
    el: '#div-body',
    data: {
//        baseUrl: 'http://jarvisqh.vicp.io/api-web/centralpayment',
//         baseUrl: 'https://beta.bolink.club/unionapi/centralpayment',
        baseUrl: 'https://s.bolink.club/unionapi/centralpayment',

        callHostContent: '呼叫总机',
        order_price: 0,
        order_duration: 0,
        parkName: '',
        centralpaymentName: '',
        order_intime: '',
        order_prepay: 0,
        order_content: '',
        oid: 0,
        left: [
            'img/left_1_c.png',
            'img/left_2.png',
            'img/left_3.png',
            'img/left_4.png',
            'img/left_5_c.png',
        ],
        MoneyBox: {
            total: 0,
            m1: 0,
            m5: 0,
            m10: 0,
            m20: 0,
            m50: 0,
            m100: 0,
        },
        AbNormalOrders: [
            {
                carNumber: '',
                payTime: '',
                orderPrice: 0,
                getMoney: 0,
                pay: 0,
                unCharge: 0,
                m1: 0,
                m5: 0,
                m10: 0,
                m20: 0,
                m50: 0,
                m100: 0
            }
        ],
        token: '',
        carnumber: '',
        phonenumber: '',
        timeInterval: null,//倒计时器
        tabJump: 2,//,默认是从数字字母页面跳转tab
        tabPay: 1,//默认是跳到塞现金界面

        showkeyboard: false,
        showkeyboard2: true,
        showkeyboard_no: false,
        showkeyboard_no_scan: false,
        showkeyboard_no_text: '无牌车请扫码查询',
        showdetail: false,
        showdetailPic: false,
        showorders: false,
        showcodepay: false,
        showcodepayscan: false,
        showcashpay: false,
        showcashpay2: false,
        showfinishpay: false,
        showError: false,
        showPayFail: false,//放弃充值直接充话费
        showfinishcharge: false,
        showadvance: false,
        showadvanceErr: false,
        showadvanceSet: false,

        selectIndex: -1,//选中的模糊查询的图片
        InparkImageUrl: '',//展示的订单详情图片
        InparkImageUrl_position: '',//展示的订单详情图片-车辆位置反向寻车
        getCash: 0,//投币金额
        cashReturn: 0,//找零金额
        loading: false, //loading层
        dialog: false, //弹框提示层
        dialogContent: '',//弹框提示内容
        dialogFlag: -1,//弹框点击确认执行动作
        dialogLogin: true,
        dialogError: false,
        dialogAlert: false,
        dialogAlertMsg: '',
        dialogAlertTag: 0,//0代表清空钱箱，1代表清空并退出登录
        dialogLoginAdvance: false,

        MacAddress: '',//mac地址
        loginaccout: '',//登录账号
        loginpwd: '',//登录密码
        url_nocarnum: '',//无牌车二维码图片连接
        url_codepay: '',//扫码支付的二维码连接
        qrcode_nocarnum: '',//无牌车轮询要带的参数
        interval_nocar_back: true,//无牌车轮询是否返回了,只有返回了才进行下一次轮询
        interval_codepay_back: true,//轮询扫码付是否有返回
        interval_cashpay_back: true,//轮询扫码付是否有返回
        order_id: '',//选中的orderid
        plate_number: '',//选中的车牌号
        count: 0,//倒计时
        orderdetail_title: '',//订单详情顶部栏文字
        orderdetailNextGray: false,//订单详情下一步是否能点击
        tradeNo: '',//现金充值完成以后返回的tradeno
        used: 1,//是否用过了倒计时
        keyboards: [
            {text: '京'},
            {text: '沪'},
            {text: '浙'},
            {text: '苏'},
            {text: '粤'},
            {text: '鲁'},
            {text: '晋'},
            {text: '冀'},
            {text: '渝'},
            {text: '豫'},
            {text: '川'},
            {text: '辽'},
            {text: '吉'},
            {text: '黑'},
            {text: '湘'},
            {text: '鄂'},
            {text: '晥'},
            {text: '赣'},
            {text: '闽'},
            {text: '陕'},
            {text: '甘'},
            {text: '宁'},
            {text: '蒙'},
            {text: '津'},
            {text: '贵'},
            {text: '云'},
            {text: '桂'},
            {text: '琼'},
            {text: '青'},
            {text: '新'},
            {text: '藏'},
            {text: '港'},
            {text: '澳'},
            {text: '台'},
            {text: '使'}
        ],
        keyboards2: [
            {text: '1'},
            {text: '2'},
            {text: '3'},
            {text: '4'},
            {text: '5'},
            {text: '6'},
            {text: '7'},
            {text: '8'},
            {text: '9'},
            {text: '0'},
            {text: 'A'},
            {text: 'B'},
            {text: 'C'},
            {text: 'D'},
            {text: 'E'},
            {text: 'F'},
            {text: 'G'},
            {text: 'H'},
            {text: 'J'},
            {text: 'K'},
            {text: 'L'},
            {text: 'M'},
            {text: 'N'},
            {text: 'P'},
            {text: 'Q'},
            {text: 'R'},
            {text: 'S'},
            {text: 'T'},
            {text: 'U'},
            {text: 'V'},
            {text: 'W'},
            {text: 'X'},
            {text: 'Y'},
            {text: 'Z'},
        ],
        numberten: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'],
        internet: true,
        datas: {
            orders: [
                {
                    duration: 1,
                    inTime: 0,
                    inparkImageUrl: "",
                    oid: 0,
                    orderId: 0,
                    plateNumber: "",
                    freeOutTime: 10,
                }
            ],
            price: 0,
            state: 1,
            type: 0,

        },
        notReportOrdersJson: [{
            "carNumber": "苏D09999",
            "cash1": 1,
            "cash10": 0,
            "cash100": 0,
            "cash2": 0,
            "cash20": 0,
            "cash5": 0,
            "cash50": 0,
            "change": "1.0",
            "currentPrice": 15.0,
            "getMoney": 1.0,
            "isCharge": 0,
            "isPay": 0,
            "isReport": 0,
            "oid": "11095856",
            "orderId": "A1_2C1510732445",
            "orderPrice": 15.0,
            "pay": 0.0,
            "payTime": "2017/11/15 下午3:54:05",
            "prepay": 0.0,
            "trade_no": "",
            "unCharge": 0.0,
            "baseObjId": 1
        }],

        freeOutTime: '10',//从sdk获得的免费停车时长
        authcode: '',//缴费机主动扫码获得的支付码
        swiperUrl: [
            {
                url: 'https://www.bolink.club/PaymentMachine/img/1.jpg'
            },
            {
                url: 'https://www.bolink.club/PaymentMachine/img/2.jpg'
            },
            {
                url: 'https://www.bolink.club/PaymentMachine/img/3.jpg'
            },
            {
                url: 'https://www.bolink.club/PaymentMachine/img/4.jpg'
            },
            {
                url: 'https://www.bolink.club/PaymentMachine/img/5.jpg'
            },


        ],
        swiper: null,
        bottomimgs: false,
        checked: false,//是否全屏显示
        checked_guard: true,//是否开启守护，默认开启
        show_checked_guard: true,//116版本以上才有的选项
        VersionName: '',
        LoginMsg: '请填写正确的账号和密码',
        nolicence: 0,  //0车主扫码1扫码窗
        elepay: 1,    //0车主扫码1扫码窗
        searchCarText: '寻车',
        showSearchCar: true
    },

    mounted: function () {



        //this.carnumber = CarNumberHead;
        var that = this;
        setInterval(function () {
            //60秒轮询一次,如果任一页面在展示,则消耗标记。下次60秒检查标记已用过则回首页
            // 每次进到这些页面,则新建标记
            if ((that.showdetail || that.showorders || that.showfinishpay || that.showError || that.showdetailPic)) {
                if (that.used > 0) {
                    that.used = 0;
                } else {
                    that.clickHome();
                }
            }

            //用于固定时间段内自动检查更新
            // var date = new Date();
            // var curr = date.getHours() + ":" + date.getMinutes();
            // console.log("curr " + curr);
            // var begin = "2:00";
            // var end = "4:00";
            // var currd = new Date("2000", "1", "1", curr.split(":")[0], curr.split(":")[1]);
            // var begind = new Date("2000", "1", "1", begin.split(":")[0], begin.split(":")[1]);
            // var endd = new Date("2000", "1", "1", end.split(":")[0], end.split(":")[1]);
            // console.log("times " + currd + "  " + begind + "  " + endd);
            // if (currd >= begind && currd <= endd && (showkeyboard2 || showkeyboard)) {
            //     console.log("在范围内");
            //     checkUpdate();
            // }

        }, 1000 * 60);

        window.addEventListener('online', function () {
            // alert("onLine");
            internet = true;
        });
        window.addEventListener('offline', function () {
            // alert("offLine");
            internet = false;
        });

        // that.swiper = new Swiper('.swiper-container', {
        //     pagination: '.swiper-pagination',
        //     nextButton: '.swiper-button-next',
        //     prevButton: '.swiper-button-prev',
        //     paginationClickable: true,
        //     spaceBetween: 30,
        //     centeredSlides: true,
        //     autoplay: 2500,
        //     autoplayDisableOnInteraction: false,
        //     loop: true
        // });
        // swiper.removeSlide(0);
        // swiper.update();
        // hello("!!!")
        //that.NotReportOrders(that.notReportOrdersJson);
        MacAddress();
        // that.AutoLogin('2','123456','7c:1d:d9:f2:7f:5c');
        //Regist_LinePhone('0009788925601','9788925','211.157.150.111','*600');

    },
    methods: {
        clearTimeInteval: function () {
            //清除定时器
            if (this.timeInterval != null) {
                clearInterval(this.timeInterval)
            }
        },
        logUtil: function (msg) {
            this.loading = false;
            console.log(msg);
        },
        alertMsg: function (msg) {
//            alert(msg);
            toast(msg);
        },
        netState: function (hasNet) {
            this.internet = hasNet;
            // if (hasNet) {
            //     onPageFinished();
            // } else {
            //     stopPlay();
            // }
            this.logUtil(hasNet + '当前的internet=' + this.internet);
            //this.alertMsg(hasNet + '当前的internet=' + this.internet);
        },
        clickTab: function () {
            if (this.tabJump == 1) {
                //显示输入省份tab
                this.showkeyboard = true;
                this.showkeyboard2 = false;
            } else {
                //显示输入数字字母tab
                this.showkeyboard = false;
                this.showkeyboard2 = true;
            }
            this.showkeyboard_no = false;
            ScanClose();
            this.clearTimeInteval();
        },
        clickTab_no: function (indicate) {
            //切到无车牌tab
            this.showkeyboard = false;
            this.showkeyboard2 = false;
            this.showkeyboard_no = true;
            this.tabJump = indicate;
            this.url_nocarnum = '';
            this.qrcode_nocarnum = '';

            //0车主扫码1扫码窗
            if (this.nolicence == 0) {
                this.showkeyboard_no_scan = false;
                this.showkeyboard_no_text = '无牌车请扫码查询';
                WriteLog(this.baseUrl + '/getnolienceqrcode?token=' + this.token);
                if (this.internet) {
                    this.loading = true;
                    this.$http.get(this.baseUrl + '/getnolienceqrcode?token=' + this.token, {timeout: TimeOutMil})
                        .then(function (res) {
                            this.loading = false;
                            //请求无牌车二维码
                            this.logUtil(res.data)
                            if (res.data.state == 1) {
                                this.url_nocarnum = res.data.qrsrc;
                                this.qrcode_nocarnum = res.data.qrcode;
                            } else {
                                this.alertMsg(res.data.errmsg)
                            }
                        }).catch(function (e) {
                        this.logUtil(e.data)
                    })
                } else {
                    this.alertMsg('网络有问题,请稍后重试');
                }
            } else if (this.nolicence == 1) {
                this.showkeyboard_no_scan = true;
                this.showkeyboard_no_text = '请将二维码小票贴近下方扫码区';
                ScanOpen();//开始扫码
            }
            var that = this;
            var count = 60;
            that.timeInterval = setInterval(function () {
                if (count > 0) {
                    count--;
                    if (that.interval_nocar_back && that.nolicence == 0) {
                        that.interval_nocar_back = false;
                        that.$http.get(that.baseUrl + '/getorderinfosbyqrcode?token=' + that.token + '&qrcode=' + that.qrcode_nocarnum, {timeout: TimeOutMil})
                            .then(function (res) {
                                //扫描二维码 轮询
                                that.interval_nocar_back = true;
                                that.logUtil(res.data)
                                if (res.data.state == 1) {
                                    that.datas = res.data;
                                    that.showkeyboard_no = false;
                                    that.left[1] = 'img/left_2_c.png'
                                    that.showkeyboard = false;
                                    that.showkeyboard2 = false;
                                    that.oid = '';
                                    if (res.data.type == 1) {
                                        that.InparkImageUrl = res.data.orders[0].inparkImageUrl;
                                        that.orderdetailNextGray = false;
                                        that.showdetail = true;
                                        that.used = 1;
                                        that.freeOutTime = res.data.orders[0].freeOutTime;
                                        that.order_id = res.data.orders[0].orderId;
                                        that.oid = res.data.orders[0].oid;
                                        that.plate_number = res.data.orders[0].plateNumber;
                                        that.order_duration = res.data.orders[0].duration;

                                        that.order_intime = new Date(res.data.orders[0].inTime * 1000).toLocaleString();
                                        if (res.data.total == -1 || res.data.price == -1) {
                                            that.orderdetail_title = '价格查询失败,请重新查询';
                                            that.orderdetailNextGray = true;
                                            that.order_price = 0;
                                            that.order_prepay = 0;
                                        } else {
                                            that.order_price = res.data.total;
                                            that.orderdetail_title = '总计' + this.order_price + '元';
                                        }
                                        if (that.order_price == 0) {
                                            that.orderdetailNextGray = true;
                                        }
                                    } else {
                                        that.showorders = true;
                                        that.used = 1;
                                        that.datas = res.data;
                                    }
                                    that.url_nocarnum = '';
                                    that.qrcode_nocarnum = '';
                                    clearInterval(that.timeInterval)
                                } else if (res.data.state == 2) {
                                    clearInterval(that.timeInterval)
                                    that.datas = res.data;
                                    that.left[1] = 'img/left_2_c.png'
                                    that.showkeyboard = false;
                                    that.showkeyboard2 = false;
                                    that.showkeyboard_no = false;
                                    //已预付过且是精确查询
                                    that.orderdetailNextGray = true;
                                    if (res.data.type == 1) {
                                        that.freeOutTime = res.data.orders[0].freeOutTime;
                                        that.InparkImageUrl = res.data.orders[0].inparkImageUrl;
                                        that.order_id = res.data.orders[0].orderId;
                                        that.oid = res.data.orders[0].oid;
                                        that.order_duration = res.data.orders[0].duration;
                                        that.plate_number = res.data.orders[0].plateNumber;
                                        that.order_intime = new Date(res.data.orders[0].inTime * 1000).toLocaleString();
                                        that.order_price = res.data.total;
                                        that.showdetail = true;
                                        that.used = 1;
                                        if (res.data.total == -1 || res.data.price == -1) {
                                            that.orderdetail_title = '价格查询失败,请重新查询';
                                            that.order_price = 0;
                                            that.order_prepay = 0;
                                        } else {
                                            that.order_price = res.data.total;
                                            that.order_prepay = res.data.prepay
                                            that.cashReturn = 0;
                                            that.orderdetail_title = '已预付' + this.order_prepay + '元,还需支付' + res.data.price + '元';
                                        }
                                        if (that.order_price == 0) {
                                            that.orderdetailNextGray = true;
                                        }
                                    }
                                    that.url_nocarnum = '';
                                    that.qrcode_nocarnum = '';
                                    clearInterval(that.timeInterval)
                                }
                            }).catch(function (e) {
                            that.logUtil(e.data)
                        })
                    }
                } else {
                    clearInterval(that.timeInterval)
                    that.clickTab()
                }
                that.logUtil(count)
            }, 1000)
        },
        clickP: function (msg) {
            //点击字母数字
            if (this.carnumber == '' && msg != '') {
                this.showkeyboard = false
                this.showkeyboard2 = true
            }
            if (this.carnumber.length > 7) {
                this.alertMsg('车牌号长度超出限制');
                return;
            }
            this.carnumber = this.carnumber + msg;
        },
        clickP_phone: function (msg) {
            //输入手机号
            if (this.phonenumber.length > 10) {
                this.alertMsg('手机号码长度超出限制');
                return;
            }
            this.phonenumber = this.phonenumber + msg;
        },
        clickDel: function () {
            //点击删除
            if (this.carnumber.length > 0) {
                this.carnumber = this.carnumber.substring(0, this.carnumber.length - 1)
                if (this.carnumber == '') {
                    this.showkeyboard = true
                    this.showkeyboard2 = false
                }
            } else {
                this.showkeyboard = true
                this.showkeyboard2 = false
            }

        },
        clickDel_phone: function () {
            //点击删除
            if (this.phonenumber.length > 0) {
                this.phonenumber = this.phonenumber.substring(0, this.phonenumber.length - 1)
            }
        },
        clickSearch: function () {
            //点击搜索
            if (this.carnumber.length < 4) {
                this.alertMsg('请至少输入4位车牌号');
                return;
            }
            if (this.carnumber.indexOf("ADVANCE") != -1) {
                //GetBoxMoney();
                //this.showkeyboard2 = false;
                //this.showadvance = true;
                this.dialogLoginAdvance = true;
                return;
            } else if (this.carnumber.indexOf("SH0WBAR") != -1) {
                UnLockBar();
                return;
            } else if (this.carnumber.indexOf("H1DEBAR") != -1) {
                LockBar();
                return;
            } else if (this.carnumber.indexOf("TEST") != -1) {
                CreateOrder('order_1', '京123456', '2017年10月12日', 0.02, 0.02);
                return;
            } else if (this.carnumber.indexOf("REL0AD") != -1) {
                Reload();
                return;
            } else if (this.carnumber.indexOf("PR1NT") != -1) {
                Print('预付费存根', '测试停车场', '中央电梯缴费机', '京A12345', '20元', '现金支付', currentTime(), '15');
                return;
            } else if (this.carnumber.indexOf("PR2NT") != -1) {
                Print2('预付费存根', '测试停车场', '中央电梯缴费机', '京A12345', '20元', '现金支付', currentTime(), '15', "10元", "www.baidu.com");
                return;
            } else if (this.carnumber.indexOf("UPDATE") != -1) {
                checkUpdate();
                return;
            } else if (this.carnumber.indexOf("GUARD1") != -1) {
                StartGuard();
                return;
            } else if (this.carnumber.indexOf("GUARD0") != -1) {
                StopGuard();
                return;
            }
            // else if (this.carnumber.indexOf("1234") != -1) {
            //     this.swiper.removeAllSlides();
            //     this.swiperUrl = [
            //         {
            //             url: 'https://www.bolink.club/PaymentMachine/img/left_1.png'
            //         },
            //         {
            //             url: 'https://www.bolink.club/PaymentMachine/img/left_2.png'
            //         },
            //         {
            //             url: 'https://www.bolink.club/PaymentMachine/img/left_3.png'
            //         },
            //         {
            //             url: 'https://www.bolink.club/PaymentMachine/img/left_4.png'
            //         }
            //     ];
            //     for (su in this.swiperUrl) {
            //         this.swiper.appendSlide('<div class="swiper-slide"> <img  src="' + this.swiperUrl[su].url + '" width="1080px" height="607.5px"> </div>');
            //     }
            //     return;
            // }
//            Vue.http.interceptors.push(function (request, next) {
////                request.headers.set('Origin', this.baseUrl);
//                request.headers.set('Access-Control-Allow-Origin', '*');
//                next(function (response) {
//                    // 请求发送后的处理逻辑
//                    console.log('intercepter response:' + response.status)
//                    return response;
//                });
//            });
            this.oid = '';
            if (this.internet) {
                this.loading = true;
                var param = this.baseUrl + '/getorderinfos?plate_number=' + this.carnumber + '&token=' + this.token;
                WriteLog(param)
                this.$http.get(this.baseUrl + '/getorderinfos?plate_number=' + this.carnumber + '&token=' + this.token, {timeout: TimeOutMil})
                    .then(function (res) {
                        this.loading = false;
                        this.logUtil(res.data)
                        if (res.data.state == 1) {
                            this.datas = res.data;
                            this.left[1] = 'img/left_2_c.png'
                            this.showkeyboard = false;
                            this.showkeyboard2 = false;
                            if (res.data.type == 1) {
                                this.orderdetailNextGray = false;
                                this.showdetail = true;
                                this.used = 1;
                                this.freeOutTime = res.data.orders[0].freeOutTime;
                                this.InparkImageUrl = res.data.orders[0].inparkImageUrl;
                                this.order_id = res.data.orders[0].orderId;
                                this.oid = res.data.orders[0].oid;
                                this.plate_number = res.data.orders[0].plateNumber;
                                this.order_duration = res.data.orders[0].duration;
                                this.order_intime = new Date(res.data.orders[0].inTime * 1000).toLocaleString();
                                if (res.data.total == -1 || res.data.price == -1) {
                                    this.orderdetail_title = '价格查询失败,请重新查询';
                                    this.orderdetailNextGray = true;
                                    this.order_price = 0;
                                    this.order_prepay = 0;
                                } else {
                                    this.order_price = res.data.total;
                                    this.orderdetail_title = '总计' + this.order_price + '元';
                                }
                                if (this.order_price == 0) {
                                    this.orderdetailNextGray = true;
                                }
                            } else {
                                this.showorders = true;
                                this.used = 1;
                                this.datas = res.data;
                            }
                        } else if (res.data.state == 2) {
                            this.datas = res.data;
                            this.left[1] = 'img/left_2_c.png'
                            this.showkeyboard = false;
                            this.showkeyboard2 = false;
                            //已预付过且是精确查询
                            this.orderdetailNextGray = true;
                            if (res.data.type == 1) {
                                this.InparkImageUrl = res.data.orders[0].inparkImageUrl;
                                this.order_id = res.data.orders[0].orderId;
                                this.oid = res.data.orders[0].oid;
                                this.order_duration = res.data.orders[0].duration;
                                this.plate_number = res.data.orders[0].plateNumber;
                                this.order_intime = new Date(res.data.orders[0].inTime * 1000).toLocaleString();
                                this.order_price = res.data.total;
                                this.showdetail = true;
                                this.used = 1;
                                this.freeOutTime = res.data.orders[0].freeOutTime;
                                if (res.data.total == -1 || res.data.price == -1) {
                                    this.orderdetail_title = '价格查询失败,请重新查询';
                                    this.order_price = 0;
                                    this.order_prepay = 0;
                                } else {
                                    this.order_price = res.data.total;
                                    this.order_prepay = res.data.prepay
                                    this.cashReturn = 0;
                                    this.orderdetail_title = '已预付' + this.order_prepay + '元,还需支付' + res.data.price + '元';
                                }
                                if (this.order_price == 0) {
                                    this.orderdetailNextGray = true;
                                }
                            }
                        } else {
                            this.alertMsg(res.data.errmsg)
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                })
            } else {
                this.alertMsg('网络有问题,请稍后重试');
            }


        },
        clickOrderDetailPre: function () {
            //订单详情 上一步
            if (this.datas.type == 1) {
                //1是精确查询,直接返回首页
                this.clickHome()
            } else {
                //模糊查询,返回多选页
                this.showdetail = false;
                this.showdetailPic = false;
                this.showorders = true;
                this.searchCarText = '寻车';
                this.used = 1;
            }

        },
        clickOrderDetailNext: function () {
            //订单详情 下一步

            if (this.elepay == 0) {
                //0车主扫码1扫码窗
                //alert('>>'+this.elepay);
                if (this.internet) {
                    this.loading = true;
                    var par = this.baseUrl + '/getprepayqrcode?order_id=' + this.order_id + '&token=' + this.token;
                    //alert('>>'+par);
                    this.$http.get(this.baseUrl + '/getprepayqrcode?order_id=' + this.order_id + '&token=' + this.token)
                        .then(function (res) {
                            //获取扫码支付的二维码
                            this.loading = false;
                            this.logUtil(res.data);
                            //alert(res.data.state+res.data.errmsg);
                            if (res.data.state == 1) {
                                this.url_codepay = res.data.qrsrc;
                                this.showcodepay = true;
                                this.showdetail = false;
                                this.showdetailPic = false;
                                this.left[2] = 'img/left_3_c.png';
                                this.tabCodeClick(-1);
                            } else {
                                this.alertMsg(res.data.errmsg)
                            }
                        }).catch(function (e) {
                        this.logUtil(e.data)
                    })
                } else {
                    this.alertMsg('网络有问题,请稍后重试');
                }
            } else if (this.elepay == 1) {
                //0车主扫码1扫码窗
                this.showdetail = false;
                this.showdetailPic = false;
                this.left[2] = 'img/left_3_c.png';
                this.tabCodeClick(-1);
            }
        },

        clickImg: function (item) {
            //点击模糊匹配的图片
            this.InparkImageUrl = item.inparkImageUrl
            this.order_id = item.orderId;
            this.oid = item.oid;
            this.plate_number = item.plateNumber;
            this.order_duration = item.duration;
            this.order_intime = new Date(item.inTime * 1000).toLocaleString();
            this.logUtil(this.order_duration);
            this.clickOrdersNext();
        },
        clickOrdersPre: function () {
//                    订单列表页面 上一步
            this.showorders = false;
            this.showdetail = false;
            this.showdetailPic = false;
            this.searchCarText = '寻车';
            this.showkeyboard2 = true;
            this.left[1] = 'img/left_2.png'
        },
        clickOrdersNext: function () {
            //订单列表页面 下一步

            if (this.internet) {
                this.loading = true;
                this.$http.get(this.baseUrl + '/queryorderprice?oid=' + this.oid + '&token=' + this.token, {timeout: TimeOutMil})
                    .then(function (res) {
                        //查询订单价格
                        this.loading = false;
                        this.logUtil(res.data)
                        if (res.data.state == 1) {
                            this.freeOutTime = res.data.freeOutTime;
                            this.order_price = res.data.price;
                            this.showorders = false;
                            this.showdetail = true;
                            this.used = 1;
                            this.left[1] = 'img/left_2_c.png'
                            this.orderdetailNextGray = false;
                            this.order_duration = res.data.duration;
                            if (res.data.total == -1 || res.data.price == -1) {
                                this.orderdetail_title = '价格查询失败,请重新查询';
                                this.orderdetailNextGray = true;
                                this.order_price = 0;
                                this.order_prepay = 0;
                            } else {
                                this.order_price = res.data.total;
                                this.orderdetail_title = '总计' + this.order_price + '元';
                            }
                            if (this.order_price == 0) {
                                this.orderdetailNextGray = true;
                            }
                        } else if (res.data.state == 2) {
                            //已预付过
                            this.freeOutTime = res.data.freeOutTime;
                            this.showorders = false;
                            this.showdetail = true;
                            this.used = 1;
                            this.orderdetailNextGray = true;
                            this.order_duration = res.data.duration;
                            if (res.data.total == -1 || res.data.price == -1) {
                                this.orderdetail_title = '价格查询失败,请重新查询';
                                this.order_price = 0;
                                this.order_prepay = 0;
                            } else {
                                this.order_price = res.data.total;
                                this.order_prepay = res.data.prepay
                                this.cashReturn = 0;
                                this.orderdetail_title = '已预付' + this.order_prepay + '元,还需支付' + res.data.price + '元';
                            }
                            if (this.order_price == 0) {
                                this.orderdetailNextGray = true;
                            }
                        } else {
                            this.dialogError = true;
                        }
                    }, function () {
                        this.loading = false;
                        this.alertMsg(TimeOutMsg);
                    }).catch(function (e) {
                    this.logUtil(e.data)
                })
            } else {
                this.alertMsg('网络有问题,请稍后重试');
            }
        },
        clickPayPre: function () {
            //支付 上一步
            this.showcodepayscan = false;
            this.showcodepay = false;
            this.showcashpay = false;
            this.showdetail = true;
            this.used = 1;
            this.orderdetailNextGray = false;
            this.left[2] = 'img/left_3.png'
            if (this.order_price == 0) {
                this.orderdetailNextGray = true;
            }
            _close();
            ScanClose();
            this.clearTimeInteval();
        },
        clickCashPre: function (flag) {
            //现金支付上一步
            if (this.getCash <= 0) {
                this.clickPayPre();
            } else {
                this.dialog = true;
                this.dialogContent = '投入的现金';
                this.dialogFlag = flag;
            }
        },

        clickCashExit: function (flag) {
            if (this.getCash > 0) {
                this.dialog = true;
                this.dialogFlag = flag;
                if (flag == 1) {
                    this.dialogContent = '投入的现金';
                } else if (flag == 2) {
                    this.dialogContent = '的找零';
                }
            } else {
                this.clickHome();
            }

        },
        tabCodeClick: function (flag) {
            //点击 电子支付 tab页
            if (flag == 4) {
                if (this.getCash > 0) {
                    this.dialog = true;
                    this.dialogContent = '投入的现金';
                    this.dialogFlag = flag;
                    return;
                }
            }
            if (!this.showcodepayscan && this.elepay == 1) {
                ScanOpen();//开始扫码
            }
            _close();
            this.clearTimeInteval();
            this.tabPay = 1;
            this.showcashpay = false;
            this.showcashpay2 = false;

            //0车主扫码1扫码窗
            var codeurl = '';
            if (this.elepay == 0) {
                this.showcodepay = true;
                codeurl = 'querysweepprepayresult';
            } else if (this.elepay == 1) {
                this.showcodepayscan = true;
                codeurl = 'queryauthcodeprepayresult';
            }


            // this.logUtil(this.order_id + '-----' + this.plate_number)
            var that = this;
            var count = 120;
            that.timeInterval = setInterval(function () {
                if (count > 0) {
                    count--;
                    if (that.interval_codepay_back) {
                        that.interval_codepay_back = false;
                        that.$http.get(that.baseUrl + '/' + codeurl + '?token=' + that.token + '&order_id=' + that.order_id + '&plate_number=' + that.plate_number, {timeout: TimeOutMil})
                            .then(function (res) {
                                //扫描电子支付二维码 轮询
                                that.interval_codepay_back = true;
                                that.loading = false;
                                if (res.data.state == 1) {
                                    // 扫码支付成功,返回首页

                                    that.showcashpay = false;
                                    that.showcodepayscan = false;
                                    that.showcodepay = false;
                                    if (!that.showcashpay2)
                                        that.showfinishpay = true;
                                    that.left[3] = 'img/left_4_c.png'
                                    that.used = 1;
                                    clearInterval(that.timeInterval)
                                }
                            }).catch(function (e) {
                            // that.logUtil(e.data)
                        })
                    }
                } else {
                    that.loading = false;
                    clearInterval(that.timeInterval)
                    that.clickHome()
                }
                // that.logUtil(count)
            }, 1000)
        },
        ScanResult: function (result) {
            //从扫描仪获得扫码结果，调用接口发起支付
            //this.alertMsg('scan result:' + result);

            if (this.order_price <= 0) {
                return;
            }
            if (this.showkeyboard_no && !(this.showcodepay || this.showcodepayscan)) {
                //正在显示无牌车界面且没有显示扫码支付界面

            } else {
                //扫码支付获得的二维码内容
                if (this.internet) {
                    this.loading = true;
                    this.$http.get(this.baseUrl + '/handlecenterauthcodeprepay?order_id=' + this.order_id + '&token=' + this.token + '&money=' + this.order_price + '&authcode=' + result + '&car_number=' + this.plate_number, {timeout: TimeOutMil})
                        .then(function (res) {

                            if (res.data.state == 1) {
                                // 扫码支付成功,返回首页
                                this.loading = false;
                                this.showcashpay = false;
                                this.showcodepayscan = false;
                                this.showcodepay = false;
                                this.showfinishpay = true;
                                this.left[3] = 'img/left_4_c.png'
                                this.used = 1;
                                clearInterval(this.timeInterval)
                            } else if (res.data.state == 2) {

                            } else {
                                alertMsg(res.data.errmsg);
                            }
                        }).catch(function (e) {
                        this.logUtil(e.data)
                    })
                } else {
                    this.alertMsg('网络有问题,请稍后重试');
                }
            }


        },
        clickDialogCancle: function () {
            this.dialog = false;
        },
        clickDialogError: function () {
            this.dialogError = false;
        },
        clickDialogOk: function () {

            //只要投入了现金，就必须调用找零接口上报
            this.dialog = false;
            this.clearTimeInteval();
            if (this.dialogFlag == 1) {
                //现金页面退出 直接返回首页
                this.$http.get(this.baseUrl + '/mobilerecharge?oid=' + this.oid + '&token=' + this.token + '&change=' + this.getCash + '&mobile=' + '&trade_no=' + this.tradeNo, {timeout: TimeOutMil})
                    .then(function (res) {
                        if (res.data.state == 2) {
                            IsReport(this.order_id, 1);
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                })
                this.clickHome();
            } else if (this.dialogFlag == 2) {
                //找零页面退出 需要充值
                this.$http.get(this.baseUrl + '/mobilerecharge?oid=' + this.oid + '&token=' + this.token + '&change=' + this.cashReturn + '&mobile=' + '&trade_no=' + this.tradeNo, {timeout: TimeOutMil})
                    .then(function (res) {
                        if (res.data.state == 2) {
                            IsReport(this.order_id, 1);
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                })
                this.clickHome()
            } else if (this.dialogFlag == 3) {
                //现金页面上一步
                this.$http.get(this.baseUrl + '/mobilerecharge?oid=' + this.oid + '&token=' + this.token + '&change=' + this.getCash + '&mobile=' + '&trade_no=' + this.tradeNo, {timeout: TimeOutMil})
                    .then(function (res) {
                        if (res.data.state == 2) {
                            IsReport(this.order_id, 1);
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                });
                this.getCash = 0;
                this.clickPayPre();
            } else if (this.dialogFlag == 4) {
                //现金页面点击电子支付
                this.$http.get(this.baseUrl + '/mobilerecharge?oid=' + this.oid + '&token=' + this.token + '&change=' + this.getCash + '&mobile=' + '&trade_no=' + this.tradeNo, {timeout: TimeOutMil})
                    .then(function (res) {
                        if (res.data.state == 2) {
                            IsReport(this.order_id, 1);
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                });
                this.getCash = 0;
                this.tabCodeClick(-1)
            }
        },
        tabCashClick: function () {
            //点击 现金支付 tab页

            this.loading = true;
            this.getCash = 0;
            if (!this.showcashpay) {
                _open();
                ScanClose();
            }
            this.clearTimeInteval();
            this.tabPay = 2;
            this.showcashpay = true;
            this.showcodepayscan = false;
            this.showcodepay = false;
            var that = this;
            that.count = 120;
            setTimeout(function () {
                that.loading = false;


                that.timeInterval = setInterval(function () {
                    if (that.count > 0) {
                        that.count--;
                        if (that.showcashpay2) {
                            _close();
                        }
                    } else {
                        clearInterval(that.timeInterval);
                        _close();
                        if (that.getCash > 0) {
                            if (versionCode >= 1107) {
                                //超时直接打印出红包二维码
                                this.loading = true;
                                this.$http.get(this.baseUrl + '/cashprepay?oid=' + this.oid + '&token=' + this.token + '&total=' + this.order_price + '&change=' + this.cashReturn + '&receive=' + this.getCash, {timeout: TimeOutMil})
                                    .then(function (res) {
                                        //现金预付
                                        this.loading = false;
                                        this.logUtil(res.data)
                                        if (res.data.hbUrl != undefined) {
                                            this.showcashpay = false;
                                            this.showError = true;
                                            this.used = 1;
                                            this.left[3] = 'img/left_4_c.png'
                                            this.tradeNo = res.data.tradeNo;
                                            Print2('预付费存根', this.parkName, this.centralpaymentName, this.plate_number, '0元', '现金支付', currentTime(), res.freeOutTime, this.cashReturn, res.data.hbUrl + '&total_amount=' + (this.getCash * 100));
                                            _close();
                                            ScanClose();
                                        } else {
                                            this.alertMsg(res.data.errmsg)
                                        }
                                    }).catch(function (e) {
                                    this.logUtil(e.data)
                                })
                            } else {
                                //超时跳转到充话费页面
                                //that.alertMsg(that.getCash)

                                that.showcashpay2 = true;
                                that.showcashpay = false;
                                that.showPayFail = true;
                                that.cashReturn = that.getCash;

                                that.count = 120;
                                that.timeInterval = setInterval(function () {
                                    if (that.count > 0) {
                                        that.count--;
                                    } else {
                                        that.$http.get(that.baseUrl + '/mobilerecharge?oid=' + that.oid + '&token=' + that.token + '&change=' + that.cashReturn + '&mobile=' + '&trade_no=' + that.tradeNo, {timeout: TimeOutMil})
                                            .then(function (res) {
                                                that.alertMsg("找零超时！请联系车场管理员")
                                                if (res.data.state == 2) {
                                                    IsReport(that.order_id, 1);
                                                }
                                            }).catch(function (e) {
                                            that.logUtil(e.data)
                                        })
                                        clearInterval(that.timeInterval)
                                        that.clickHome()
                                    }
                                    that.logUtil(that.count)
                                }, 1000)

                            }
                        } else {
                            that.clickHome();
                        }

                    }
                    that.logUtil(that.count)
                }, 1000)

            }, 1500);
        },
        getMoney: function (m) {
            var intm = parseInt(m);
            this.getCash += intm;
            // CreateOrder(this.order_id, this.plate_number, this.order_intime, this.order_price, this.order_price, this.oid);

            UpdateOrderMoney(this.order_id, intm, this.getCash, currentTime(), this.plate_number, this.order_price, this.order_price, this.oid);
            if (this.getCash > this.order_price) {
                IsPay(this.order_id, 1);
                this.cashReturn = new Number(this.getCash - this.order_price).toFixed(2)
                if (this.internet) {
                    this.loading = true;
                    this.$http.get(this.baseUrl + '/cashprepay?oid=' + this.oid + '&token=' + this.token + '&total=' + this.order_price + '&change=' + this.cashReturn + '&receive=' + this.getCash, {timeout: TimeOutMil})
                        .then(function (res) {
                            //现金预付
                            this.loading = false;
                            this.logUtil(res.data)
                            if (res.data.state == 1) {
                                if (versionCode >= 1107) {
                                    this.showcashpay = false;
                                    this.showfinishpay = true;
                                    this.used = 1;
                                    this.left[3] = 'img/left_4_c.png'
                                    this.tradeNo = res.data.tradeNo;
                                    setTradeNo(this.order_id, this.tradeNo);
                                    WriteLog(res.data.hbUrl)
                                    Print2('预付费存根', this.parkName, this.centralpaymentName, this.plate_number, this.order_price + '元', '现金支付', currentTime(), this.freeOutTime, this.cashReturn, res.data.hbUrl + '&total_amount=' + (this.cashReturn * 100));
                                    _close();
                                    ScanClose();
                                } else {
                                    this.showcashpay = false;
                                    this.showcodepayscan = false;
                                    this.showcodepay = false;
                                    this.showcashpay2 = true;
                                    this.showfinishpay = false;
                                    this.tradeNo = res.data.tradeNo;
                                    setTradeNo(this.order_id, this.tradeNo);
                                    Print('预付费存根', this.parkName, this.centralpaymentName, this.plate_number, this.order_price + '元', '现金支付', currentTime(), this.freeOutTime);
                                }
                            } else {
                                this.alertMsg(res.data.errmsg)
                            }
                        }).catch(function (e) {
                        this.logUtil(e.data)
                    })
                } else {
                    this.alertMsg('网络有问题,请稍后重试');
                }


                var that = this;
                setTimeout(function () {
                    if (that.timeInterval != null) {
                        clearInterval(that.timeInterval)
                    }
                    that.count = 120;
                    that.timeInterval = setInterval(function () {
                        if (that.count > 0) {
                            that.count--;
                        } else {
                            that.$http.get(that.baseUrl + '/mobilerecharge?oid=' + that.oid + '&token=' + that.token + '&change=' + that.cashReturn + '&mobile=' + '&trade_no=' + that.tradeNo, {timeout: TimeOutMil})
                                .then(function (res) {
                                    that.alertMsg("找零超时！请联系车场管理员")
                                    if (res.data.state == 2) {
                                        IsReport(that.order_id, 1);
                                    }
                                }).catch(function (e) {
                                that.logUtil(e.data)
                            })
                            clearInterval(that.timeInterval)
                            that.clickHome()
                        }
                        that.logUtil(that.count)
                    }, 1000)
                }, 2000);
            } else if (this.getCash == this.order_price) {
                this.cashReturn = 0;
                IsPay(this.order_id, 1);
                IsCharge(this.order_id, 1);
                if (this.internet) {
                    this.loading = true;
                    this.$http.get(this.baseUrl + '/cashprepay?oid=' + this.oid + '&token=' + this.token + '&total=' + this.order_price + '&change=' + this.cashReturn + '&receive=' + this.getCash, {timeout: TimeOutMil})
                        .then(function (res) {
                            //现金预付
                            this.loading = false;
                            this.logUtil(res.data)
                            if (res.data.state == 1) {
                                this.showcashpay = false;
                                this.showfinishpay = true;
                                this.used = 1;
                                this.left[3] = 'img/left_4_c.png'
                                this.tradeNo = res.data.tradeNo;
                                setTradeNo(this.order_id, this.tradeNo);
                                Print('预付费存根', this.parkName, this.centralpaymentName, this.plate_number, this.order_price + '元', '现金支付', currentTime(), this.freeOutTime);
                            } else {
                                this.alertMsg(res.data.errmsg)
                            }
                        }).catch(function (e) {
                        this.logUtil(e.data)
                    })
                } else {
                    this.alertMsg('网络有问题,请稍后重试');
                }

            }
        },
        clickCharge: function () {

            if (checkPhone(this.phonenumber)) {
                if (this.internet) {
                    this.loading = true;
                    this.$http.get(this.baseUrl + '/mobilerecharge?oid=' + this.oid + '&token=' + this.token + '&change=' + this.cashReturn + '&mobile=' + this.phonenumber + '&trade_no=' + this.tradeNo, {timeout: TimeOutMil})
                        .then(function (res) {
                            //现金预付
                            this.loading = false;
                            this.logUtil(res.data)
                            if (res.data.state == 1) {
                                IsCharge(this.order_id, 1);
                                if (this.showPayFail) {
                                    this.showfinishcharge = true;
                                } else {
                                    this.showfinishcharge = false;
                                }
                                this.showcashpay2 = false;
                                this.showfinishpay = true;
                                this.used = 1;
                                this.left[3] = 'img/left_4_c.png'
                                this.clearTimeInteval();

                            } else {
                                if (res.data.state == 2) {
                                    IsReport(this.order_id, 1);
                                }
                                this.alertMsg(res.data.errmsg)
                            }
                        }).catch(function (e) {
                        this.logUtil(e.data)
                    })
                } else {
                    this.alertMsg('网络有问题,请稍后重试');
                }

            }
        },
        clickSearchCar: function () {
            if (this.searchCarText == '寻车') {
                // this.searchCarText = '详情'
                // this.showdetailPic = true;
                // this.showdetail = false;
                // this.used = 1;
                // this.InparkImageUrl_position = 'https://yinzuoapm.51park.cn/numparkplace/201712/13/20171213155402_%E9%B2%81AB3B10.jpg';

//                Vue.http.interceptors.push(function (request, next) {
//                    // modify url 本地调试使用本地ip地址
//                    var uur = "https://yinzuoapm.51park.cn/ReverseCarSystem/interface/ReverseCarSystem/GetSelectPath.php?parkid=56060&deviceid=1086&license_plate=" + encodeURI(this.carnumber);
//                    var timestamp = Date.parse(new Date()) / 1000 + '';
//                    // console.log(timestamp);
//                    var str = timestamp + uur + 'd5ed00ef6793b73127b4a91901ada5d6';
//                    // console.log(str);
//                    var md5str = hex_md5(str);
//                    // console.log(md5str)
//
//                    // request.headers.set(md5str);
//                    // request.headers.set(timestamp);
//                    request.headers.set('Authorization', md5str + ',' + timestamp);
////                    request.headers.set('Authorization1', md5str + ',' + timestamp);
//                    next(function (response) {
//                        // 请求发送后的处理逻辑
//                        console.log('response' + response.status)
//                        return response;
//                    });
//                });

                var uur = "https://yinzuoapm.51park.cn/ReverseCarSystem/interface/ReverseCarSystem/GetSelectPath.php?parkid=56060&deviceid=1086&license_plate=" + encodeURI(this.carnumber);
                var timestamp = Date.parse(new Date()) / 1000 + '';
                var str = timestamp + uur + 'd5ed00ef6793b73127b4a91901ada5d6';
                var md5str = hex_md5(str);
                // request.headers.set('Authorization', md5str + ',' + timestamp);
                // request.headers.set('Authorization1', md5str + ',' + timestamp);

                this.loading = true;
                this.$http({
                    url: 'https://yinzuoapm.51park.cn/ReverseCarSystem/interface/ReverseCarSystem/GetSelectPath.php?parkid=56060&deviceid=1086&license_plate=' + encodeURI(this.carnumber),
                    method: 'GET',
                    //请求体中发送的数据
                    data: {},
                    //设置请求头
                    headers: {
                        'Authorization': md5str + ',' + timestamp,
                        'Authorization1': md5str + ',' + timestamp
                    }
                }).then(
                    function (response) {
                        //请求成功回调

                        this.loading = false;
                        this.logUtil(response.data);
                        if (response.data.code == 200) {
                            this.searchCarText = '详情'
                            this.showdetailPic = true;
                            this.showdetail = false;
                            WriteLog(response.data.image_path)
                            this.InparkImageUrl_position = response.data.data.image_path;
                        } else {
                            this.alertMsg(response.data.msg);
                            return;
                        }
                    },
                    function (response) {
                        //请求失败回调
                        this.alertMsg(response);
                        this.loading = false;
                    }
                )

//                this.loading = true;
//                this.$http.get("https://yinzuoapm.51park.cn/ReverseCarSystem/interface/ReverseCarSystem/GetSelectPath.php?parkid=56060&deviceid=1086&license_plate=" + encodeURI(this.carnumber))
//                    .then(function (res) {
//                        this.loading = false;
//                        this.logUtil(res.data);
//                        if (res.data.code == 200) {
//                            this.searchCarText = '详情'
//                            this.showdetailPic = true;
//                            this.showdetail = false;
//                            this.InparkImageUrl_position = res.data.image_path;
//                        } else {
//                            this.alertMsg(res.data.msg);
//                            return;
//                        }
//                    }).catch(function (e) {
//                    this.alertMsg(e);
//                    this.loading = false;
//                })

            } else {
                this.showdetailPic = false;
                this.showdetail = true;
                this.searchCarText = '寻车'
            }
        },
        clickHome: function () {
            this.searchCarText = '寻车'
            this.carnumber = CarNumberHead
            this.showkeyboard2 = true;
            this.showkeyboard = false;
            this.showkeyboard_no = false;
            this.showdetail = false;
            this.showdetailPic = false;
            this.showorders = false;
            this.showcodepayscan = false;
            this.showcodepay = false;
            this.showcashpay = false;
            this.showcashpay2 = false;
            this.showfinishpay = false;
            this.showError = false;
            this.dialog = false;
            this.dialogError = false;
            this.loading = false;
            this.showfinishcharge = false;
            this.showPayFail = false;
            this.showadvance = false;
            this.showadvanceErr = false;
            this.left[1] = 'img/left_2.png';
            this.left[2] = 'img/left_3.png';
            this.left[3] = 'img/left_4.png';
            this.clearTimeInteval();
            _close();
            ScanClose();
            this.phonenumber = '';
            this.tradeNo = '';
            this.cashReturn = 0;
            this.getCash = 0;
            this.orderdetailNextGray = false;
            this.showadvanceErr = false;
            this.showadvanceSet = false;
            ClearCache();
        },
        homeInterval: function () {
            var that = this;
            that.count = 120;
            that.timeInterval = setInterval(function () {
                if (that.count > 0) {
                    that.count--;
                    if (that.showcashpay2) {
                        _close();
                    }
                } else {
                    clearInterval(that.timeInterval)
                    that.clickHome()
                }
                that.logUtil(that.count)
            }, 1000)
        },
        openAdvance: function (result) {
            this.MoneyBox = result;
        },
        //高级登录 查得异常金额结果
        QuerryAdavanceAbNormal: function (result) {
            //alert(result);
            this.AbNormalOrders = result;
        },
        //高级登录 点击取出现金
        clickAdCash: function () {
            this.dialogAlert = true;
            this.dialogAlertTag = 0;
            this.dialogAlertMsg = '请核对现金是否统计正确，点击确定后，当前统计将被清空！是否取出现金？';
        },
        //高级登录 点击取出现金 确认
        clickAlertConfirm: function () {

            if (this.internet) {
                this.loading = true;
                this.$http.get(this.baseUrl + '/clearmoneybox?machine_id=' + this.loginaccout + '&token=' + this.token, {timeout: TimeOutMil})
                    .then(function (res) {
                        //现金预付
                        this.loading = false;
                        if (res.data.state == 1) {
                            this.dialogAlert = false;
                            //清除数据库
                            clearOrders();
                            this.AbNormalOrders = '';
                            this.clickHome();
                            if (this.dialogAlertTag == 1) {
                                //为1时代表清空钱箱并退出
                                this.dialogLogin = true;
                                UpdateUser_Exit();
                            }
                        } else {
                            this.alertMsg(res.data.errmsg)
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                })
            } else {
                this.alertMsg('网络有问题,请稍后重试');
            }


        },
        //高级登录 点击取出现金 取消
        clickAlertCancel: function () {
            this.dialogAlert = false;
            this.dialogLoginAdvance = false;
        },
        //高级登录 点击 查看异常
        clickAdError: function () {
            this.showadvance = false;
            this.showadvanceErr = true;
            //获取数据库中异常订单
            AbNormalOrder();
            this.QuerryAdavanceAbNormal('');
        },
        //高级登录 点击 设置
        clickAdSet: function () {
            WriteLog(versionCode);
            if (versionCode >= 1106) {
                this.show_checked_guard = true;
            } else {
                this.show_checked_guard = false
            }
            this.showadvanceSet = true;
            this.showadvance = false;
        },
        //高级登录 异常订单 返回
        clickAdBack: function () {
            this.showadvance = true;
            this.showadvanceErr = false;
            this.showadvanceSet = false;
        },
        //呼叫总机
        clickCallHost: function () {
            if (this.callHostContent.indexOf('呼叫中') != -1) {
                this.alertMsg('正在呼叫，请耐心等待');
            } else if (this.callHostContent.indexOf('通话中') != -1) {
                this.alertMsg('正在通话中');
            } else {
                //CallHost();
                CallHost_LinePhone();
                this.callHostContent = '呼叫中...';
            }

        },
        NotifyCallStatus: function (result) {
            console.log('js收到了消息vve  ' + result);
            this.callHostContent = result;
        },
        AutoLogin: function (account, password, macadress) {
            this.loginaccout = account;
            this.loginpwd = password;
            this.MacAddress = macadress;
            this.LoginMsg = '';
            this.clickAlertLogin();
        },
        clickAlertLogin: function () {


            var head = 'test:222'
            if (this.loginaccout == '' || this.loginpwd == '') {
                this.alertMsg(this.LoginMsg);
            } else {
                this.loading = true;
                var paraa = this.baseUrl + '/login?machine_id=' + this.loginaccout + '&password=' + this.loginpwd + '&mac_address=' + this.MacAddress;
                WriteLog(paraa);
                this.$http.get(this.baseUrl + '/login?machine_id=' + this.loginaccout + '&password=' + this.loginpwd + '&mac_address=' + this.MacAddress, {headers: head})
                // this.$http.get("https://yinzuoapm.51park.cn/ReverseCarSystem/interface/ReverseCarSystem/GetSelectPath.php?parkid=56060&deviceid=1086&license_plate=" + encodeURI('鲁AK92C0'))
                    .then(function (res) {
                        this.loading = false;
                        // console.log(res.data.code+res.data.msg)
                        this.logUtil(res.data);
                        //alert(res.data);
                        if (res.data.state == 1) {
                            UpdateUser(this.loginaccout, this.loginpwd);
                            //登录成功，登录框消失
                            this.dialogLogin = false;
                            this.token = res.data.token;
                            this.parkName = res.data.parkName;
                            this.centralpaymentName = res.data.centralpaymentName;
                            CarNumberHead = res.data.provinceAbbr;
                            this.carnumber = CarNumberHead;
                            this.loginpwd = '';
                            if (res.data.nolicence != undefined)
                                this.nolicence = res.data.nolicence;
                            if (res.data.nolicence != undefined)
                                this.elepay = res.data.elepay;
                            // this.logUtil('toooooooooken:' + ths.token)
                            // Regist_LinePhone('0009788925601','9788925','211.157.150.111','*600');
                            NotReportOrders_an();
                        } else {
                            this.alertMsg(res.data.errmsg);
                            return;
                        }
                    }).catch(function (e) {
                    this.loading = false;
                    //this.logUtil('222' + e.data)
                })
            }


//            this.dialogLogin = false;
        },
        clickAlertLoginAdvance: function () {
//            IdentifyPwd(this.loginpwd);

            if (this.loginaccout == '' || this.loginpwd == '') {
                this.alertMsg('请填写正确的密码');
            } else {

                this.loading = true;
                this.$http.get(this.baseUrl + '/login?machine_id=' + this.loginaccout + '&password=' + this.loginpwd + '&mac_address=' + this.MacAddress, {timeout: TimeOutMil})
                    .then(function (res) {
                        this.loading = false;
                        this.logUtil(res.data)
                        if (res.data.state == 1) {
                            //登录成功，登录框消失
                            //UnLockBar();
                            NotifyCheckPwd(1);
                            this.loginpwd = '';
                        } else {
                            this.alertMsg(res.data.errmsg);
                            return;
                        }
                    }).catch(function (e) {
                    this.loading = false;
                    this.logUtil(e.data)
                })
            }
        },
        getMacAddress: function (msg) {
            // this.alertMsg(msg);
            this.MacAddress = msg;
        },
        NotifyCheckPwd: function (msg) {
            //验证高级登录密码
            if (msg == 1) {
                GetBoxMoney();
                this.dialogLoginAdvance = false;
                this.showkeyboard2 = false;
                this.showadvance = true;
            } else {
                this.alertMsg('密码错误,请重新输入');
            }
        },
        clickAdvanceExit: function () {
            //退出登录
            // this.clickHome();
            // this.dialogLogin = true;
            // UpdateUser_Exit();
            this.dialogAlert = true;
            this.dialogAlertTag = 1;
            this.dialogAlertMsg = '请核对现金是否统计正确，点击确定后，当前统计将被清空并返回登录页面！是否退出？';
        },
        updateSwiper: function (urls) {
            //更新底部banner图片s
            // this.swiper.removeAllSlides();
            // this.swiperUrl = urls;
            // for (su in this.swiperUrl) {
            //     this.swiper.appendSlide('<div class="swiper-slide"> <img  src="' + this.swiperUrl[su].url + '" width="1080px" height="607.5px"> </div>');
            // }
        },
        clickBar: function () {
            //隐藏或显示 虚拟按键、状态栏
            if (this.checked) {
                LockBar();
            } else {
                UnLockBar();
            }
        },
        clickGuard: function () {
            //隐藏或显示 虚拟按键、状态栏
            if (this.checked_guard) {
                StartGuard();
            } else {
                StopGuard();
            }
        },
        getCheckStatus: function (check) {
            this.checked = check;
        },
        clickUpdate: function () {
            //检查更新
            //this.ShowLoading();
            checkUpdate();
        },
        CurrentVersion: function (versionname) {
            //版本号名称
            this.VersionName = versionname;
        },
        //OpenScanResult: function (msg) {
        //this.loading = false;
        //},
        ShowLoading: function (msg) {
            // this.alertMsg("收到了消息，开始loading")
            this.loading = true;
        },
        HideLoading: function (msg) {
            this.loading = false;
        },
        NotReportOrders: function (ordersjson) {
            // alert('>>>' + ordersjson + '<<<')
            this.notReportOrdersJson = ordersjson;
            if (this.notReportOrdersJson == '')
                return;
            var that = this;
            this.notReportOrdersJson.forEach(function (item, index) {

                //that.token = 'B5C30770E8F3A04E022E4933AA784D24';
                var param = that.baseUrl + '/mobilerecharge?oid=' + item.oid + '&token=' + that.token + '&change=' + item.change + '&mobile=' + '&trade_no=' + item.trade_no;
                // // alert('>>>' + item + '<<<')
                //alert('>>>' + index + param + '<<<')

                that.$http.get(that.baseUrl + '/mobilerecharge?oid=' + item.oid + '&token=' + that.token + '&change=' + item.change + '&mobile=' + '&trade_no=' + item.trade_no, {timeout: TimeOutMil})
                    .then(function (res) {
                        //alert('返回结果' + res.data.errmsg)
                        //alert(res.data.state)
                        if (res.data.state == 2) {
                            IsReport(item.orderId, 1);
                        }
                    }).catch(function (e) {
                    this.logUtil(e.data)
                })
            });
        }
    }
})


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
    var date = new Date();
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
}
//JS调用android方法
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
    window.AndroidMethod.Print(title, park, terminal, carnumber, prepay, paytype, paytime, tale, returncash, url);
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
    window.AndroidMethod.checkUpdate();
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
//android调用JS方法
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
