//格式化地震发生日期，精确到秒
function format(prev) {

    let year = prev.getFullYear();
    //month月份要＋1，国外用法
    let month = prev.getMonth() + 1;
    let day = prev.getDate();
    let hour = prev.getHours();
    let min = prev.getMinutes();
    let sec = prev.getSeconds();
    // console.log(year, month, day);
    let cur = 'yyyy年MM月dd日HH时mm分ss秒';
    let res = cur.replace(/yyyy/, year);
    let res1 = res.replace(/MM/, month);
    let res2 = res1.replace(/dd/, day);
    let res3 = res2.replace(/HH/, hour);
    let res4 = res3.replace(/mm/, min);
    let res5 = res4.replace(/ss/, sec);

    return res5;

    // console.log(res5);
}
// format(new Date('2012/12/14 10:50:31'));

//格式化发布日期，精确到日
function format1(prev) {

    let year = prev.getFullYear();
    //month月份要＋1，国外用法
    let month = prev.getMonth() + 1;
    let day = prev.getDate();
    // console.log(year, month, day);

    let cur = 'yyyy年MM月dd日';
    let res = cur.replace(/yyyy/, year);
    let res1 = res.replace(/MM/, month);
    let res2 = res1.replace(/dd/, day);
    return res2;


}

