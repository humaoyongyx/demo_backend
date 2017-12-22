$.datepicker.regional['zh-CN'] = {
    closeText: '关闭',
    prevText: '<上月',
    nextText: '下月>',
    currentText: '今天',
    monthNames: ['一月','二月','三月','四月','五月','六月',
        '七月','八月','九月','十月','十一月','十二月'],
    monthNamesShort: ['一','二','三','四','五','六',
        '七','八','九','十','十一','十二'],
    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
    dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
    dayNamesMin: ['日','一','二','三','四','五','六'],
    weekHeader: '周',
    dateFormat: 'yy-mm-dd',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: true,
    yearSuffix: '年'
};

$.datepicker.setDefaults($.datepicker.regional['zh-CN']);

$.extend($.fn.DataTable.defaults, {
    language : {
        "sProcessing" : "处理中...",
        "sLengthMenu" : "显示 _MENU_ 项结果",
        "sZeroRecords" : "没有匹配结果",
        "sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix" : "",
        "sSearch" : "搜索:",
        "sUrl" : "",
        "sEmptyTable" : "表中数据为空",
        "sLoadingRecords" : "载入中...",
        "sInfoThousands" : ",",
        "oPaginate" : {
            "sFirst" : "首页",
            "sPrevious" : "上页",
            "sNext" : "下页",
            "sLast" : "末页"
        },
        "oAria" : {
            "sSortAscending" : ": 以升序排列此列",
            "sSortDescending" : ": 以降序排列此列"
        }
    }
});

//extend plugins
$.fn.DataTableServer = function(config) {
    var defautConfig = {
        "dom" : "tip",
        "processing" : true,
        "serverSide" : true,
        "lengthChange" : false,//是否允许用户自定义显示数量
        "pageLength" : 10,
        "searching": false,//本地搜索
        "ordering": false, //排序功能
        "Info": true,//页脚信息
        "bPaginate": true, //翻页功能
        "bFilter": false //列筛序功能
    };
    if (config) {
        $.extend(true, defautConfig, config);
    }
    return $(this).DataTable(defautConfig);
}
	