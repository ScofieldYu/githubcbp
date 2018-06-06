<style>
    @import "../css/reset.css";
    @import "../css/bookingConfigure.css";
    @import "../js/dropdownlist/dropdownlist.css";
</style>
<template>
  <div class="container">
    <div class="addTimeInterval">
      <div class="aTIList canBookingDays">
        <p>可预约天数</p>
        <div id="selectDays" class="select selectDays"></div>
      </div>
      <div class="aTIList canBookingTime">
        <p>可预约时段</p>
        <div class="select">
          <div id="starTime" class="starTime"></div>
          <i class="line">-</i>
          <div id="endTime" class="endTime"></div>
        </div>
      </div>
      <div class="aTIList addBtn">
        <a href="javascript:void 0">添加时段</a>
      </div>
    </div>
    <div class="timeInterval">
      <div class="timeIntervalTitle">
        <span>预约时间段</span>
        <span>操作</span>
      </div>
      <div class="timeIntervalList">
        <ul>
          <li>
            <p>09:30 - 11:30</p>
            <a class="delBtn" href="javascript:void 0">删除</a>
          </li>
          <li>
            <p>13:30 - 15:30</p>
            <a class="delBtn" href="javascript:void 0">删除</a>
          </li>
          <li>
            <p>15:30 - 17:30</p>
            <a class="delBtn" href="javascript:void 0">删除</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="btnList">
      <a class="resetBtn" href="javascript:void 0">重 置</a>
      <a class="sureBtn" href="javascript:void 0">确 定</a>
    </div>
  </div>
</template>
<script>
    export default{
        data(){
            return {
                fruitList: []
            }
        },
        mounted(){

          var storeid = { //门店ID  后期从其他地方获取
            storeid: 11
          };
          var trueData; //数据暂存

          //获取门店预约时间
          findData();
          function findData() {
            $.ajax({
              type: "POST",
              url: srcConfig.getShopTimeTamplate,
              dataType: 'json',
              contentType: "application/json",
              data: JSON.stringify(storeid),
              /* beforeSend: function (request) {
               request.setRequestHeader("MSESSIONID", token);
               },*/
              success: function (data) {
                if (data.code == 0) {
                  console.log(data);
                  trueData = data.data;
                  var dayNum = {value: trueData.daynum, text: '未来' + trueData.daynum + '天'};
                  //绑定当前时间模版下拉列表
                  bindDropdownList(dayNum);
                  //绑定时间模版列表
                  var periodList = trueData.periodList;
                  bindTimeData(periodList)
                } else {
                  console.log(data.message)
                }
              },
              error: function (data) {
                console.log(data)
              }
            });
          }

          //绑定下拉列表
          function bindDropdownList(data) {
            $.dropdownlist({
              container: $("#selectDays"),  //承载下拉列表的容器（jquery对象）
              value: data,   //当前的值{value:value,text:text} 无当前选中值
              autoShow: false,    //true 情况下就默认选项是打开的
              data: [{value: "1", text: '未来1天'}, {value: "2", text: '未来2天'}, {
                value: "3",
                text: '未来3天'
              }, {value: "4", text: '未来4天'}, {value: "5", text: '未来5天'}, {value: "6", text: '未来6天'}],
              afterSelect: function (selectedItem) {  //下拉选中后执行的事件，参数：selectedItem选中项
                console.log(selectedItem);
              }
            });
            $.dropdownlist({
              container: $("#starTime"),  //承载下拉列表的容器（jquery对象）
              value: {value: "1", text: '09:30'},   //当前的值{value:value,text:text} 无当前选中值
              autoShow: false,    //true 情况下就默认选项是打开的
              data: [{value: "1", text: '09:30'}, {value: "2", text: '10:30'}, {value: "3", text: '11:30'}, {
                value: "4",
                text: '12:30'
              }, {value: "5", text: '13:30'}],
              afterSelect: function (selectedItem) {  //下拉选中后执行的事件，参数：selectedItem选中项
                console.log(selectedItem);
              }
            });
            $.dropdownlist({
              container: $("#endTime"),  //承载下拉列表的容器（jquery对象）
              value: {value: "1", text: '09:30'},   //当前的值{value:value,text:text} 无当前选中值
              autoShow: false,    //true 情况下就默认选项是打开的
              data: [{value: "1", text: '09:30'}, {value: "2", text: '10:30'}, {value: "3", text: '11:30'}, {
                value: "4",
                text: '12:30'
              }, {value: "5", text: '13:30'}],
              afterSelect: function (selectedItem) {  //下拉选中后执行的事件，参数：selectedItem选中项
                console.log(selectedItem);
              }
            });
          }

          //绑定时间模版列表
          function bindTimeData(data) {
            var str = '';
            str += '<ul>';
            for (var i = 0; i < data.length; i++) {
              str += '<li>';
              str += '<p>'+data[i].starttime+' - '+data[i].endtime+'</p>';
              str += '<a class="delBtn" href="javascript:void 0">删除</a></li>'
            }
            str += '</ul>';
            $('.timeIntervalList').html(str);
            // 绑定删除按钮
            bindClick();
          }

          //添加时间段按钮
          $('.addBtn a').click(function () {
            var starTime = $('.starTime label').text();
            var endTime = $('.endTime label').text();
            var str = '<li><p>' + starTime + ' - ' + starTime + '</p><a class="delBtn" href="javascript:void 0">删除</a></li>';
            $('.timeIntervalList ul').append(str);
            bindClick()
          });
          //删除时间段按钮
          function bindClick() {
            $('.delBtn').click(function () {
              $(this).parent().remove()
            })
          }

          //重置按钮
          $('.resetBtn').click(function () {
            findData()
          });

          //确定按钮
          $('.sureBtn').click(function () {
            var periodList = [];
            var $perio = $('.timeIntervalList p');
            for(var i=0;i<$perio.length;i++){
              var cur = $($perio[i]).text();
              var temp = {
                timeperiodid: trueData.periodList[0].timeperiodid,
                timeconfid: trueData.periodList[0].timeconfid,
                starttime: cur.split(' - ')[0],
                endtime: cur.split(' - ')[1]
              };
              periodList.push(temp)
            }
            var sendData={
              timeconfid:trueData.timeconfid,
              storeid:trueData.storeid,
              storename:trueData.storename,
              daynum:$('#selectDays label').attr('val'),
              periodList:periodList
            };
            $.ajax({
              type: "POST",
              url: srcConfig.setShopTimeTamplate,
              dataType: 'json',
              contentType: "application/json",
              data: JSON.stringify(storeid),
              /* beforeSend: function (request) {
               request.setRequestHeader("MSESSIONID", token);
               },*/
              success: function (data) {
                if (data.code == 0) {
                  console.log(data)
                } else {
                  console.log(data.message)
                }
              },
              error: function (data) {
                console.log(data)
              }
            });
          })
        },
        components: {

        }
    }
</script>
