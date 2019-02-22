/**
 * Created by xulu on 2017/12/15.
 */
/**
 * 钱箱的金额-在高级登录展示
 * @type {{total: number, m1: number, m5: number, m10: number, m20: number, m50: number, m100: number}}
 */
var moneyBoxModel = {
    total: 0,
    m1: 0,
    m5: 0,
    m10: 0,
    m20: 0,
    m50: 0,
    m100: 0,
};

/**
 * 查询到的订单列表
 * @type {{orders: [*], price: number, state: number, type: number}}
 */
var orderDatasModel = {
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

}

/**
 * 未上报的异常订单
 * @type {[*]}
 */
var notReportOrdersModel = [{
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
}]

/**
 * 非正常订单
 * @type {[*]}
 */
var AbNormalOrdersModel = [
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
]

/**
 * 布局左侧进度标志
 * @type {[*]}
 */
const leftImg = [
    'img/left_1_c.png',
    'img/left_2.png',
    'img/left_3.png',
    'img/left_4.png',
    'img/left_5_c.png',
];

/**
 * 车牌号 首位
 * @type {[*]}
 */
const keyBoardHeadEntity = [
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
]

/**
 * 车牌号 字母数字
 * @type {[*]}
 */
const keyBoardBodyEntity = [
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
]
/**
 * swiper轮播的图片地址
 * 目前弃用
 * @type {[*]}
 */
const swiperUrlModel = [
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


]

