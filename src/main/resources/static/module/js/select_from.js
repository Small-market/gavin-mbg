layui.use(['form', 'table'], function () {
    var form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    /**
     * 初始化表单，要加上，不然刷新部分组件可能会不加载
     */
    form.render();

    // 当前弹出层，防止ID被覆盖
    const parentIndex = layer.index;

    //监听提交
    form.on('submit(sure)', function (data) {
        console.log("按钮点击了")
        // const tableNames = $.query.get("tableNames");
        // tableNames
        console.log($)
        console.log(getQueryVariable("tableNames"))
        const tableNames = getQueryVariable("tableNames")
        // const index = layer.alert(JSON.stringify(data.field), {
        //     title: '最终的提交信息'
        // }, function () {
        //
        //     // 关闭弹出层
        //     layer.close(index);
        //     layer.close(parentIndex);
        //
        // });
        const param = {
            tableList: tableNames.split(",")
        }
        JSON.stringify(param)
        window.open('/sys/downloadImage');
        $.ajax({
            method: "post",
            url: "/code",
            // dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(param)
            // success() {
            // },
            // error(e) {
            // }
        })

        return false;
    });

});

function getQueryVariable(variable) {
    const query = window.location.search.substring(1);
    const vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        const pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return "";
}