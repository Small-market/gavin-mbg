layui.use(['form', 'jquery', 'table', 'layer'], function () {
    const $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        layer = parent.layer;

    // 模糊查询的表名
    const tableNameVal = ""

    table.render({
        elem: '#currentTableId',
        url: '/list',
        // toolbar: '#toolbarDemo',
        // defaultToolbar: ['filter', 'exports', 'print', {
        //   title: '提示',
        //   layEvent: 'LAYTABLE_TIPS',
        //   icon: 'layui-icon-tips'
        // }],
        where: {
            tableName: tableNameVal
        },
        cols: [[
            {type: "checkbox", width: 50},
            {type: "numbers", title: '序号', width: 50},
            {field: 'tableName', title: '表名', sort: true},
            {field: 'engine', title: '存储引擎'},
            {field: 'tableComment', title: '备注'},
            {field: 'createTime', title: '创建时间', sort: true}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        request: {
            pageName: "pageIndex",
            limitName: "pageSize"
        },
        parseData: function (res) { //res 即为原始返回的数据
            let result = {
                "code": 500, //解析接口状态
                "msg": "数据获取失败", //解析提示文本
                "count": 0, //解析数据长度
                "data": [] //解析数据列表
            }
            if (res.data !== undefined && res.code === 200) {
                result = {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.records //解析数据列表
                }
            }
            return result;
        },
        response: {
            statusCode: 200 //规定成功的状态码，默认：0
        }
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function ({field}) {
        if (field.tableName === null || field.tableName === "") {
            layer.msg("请输入表名");
            return false;
        }

        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
            , where: {
                tableName: field.tableName
            }
        }, 'data');

        return false;
    });

    // 监听生成按钮操作
    form.on('submit(code-mbg-btn)', function ({field}) {
        const {data} = table.checkStatus("currentTableId")
        if (data.length <= 0) {
            layer.msg("请选择一条数据");
            return false;
        }
        const a = document.createElement('a');
        a.download = '文件名';
        a.href ="/code?tableList=" + data.map(i => i.tableName).join(",");
        document.documentElement.appendChild(a);
        a.click();
        a.remove();

        // $.ajax({
        //     method: "get",
        //     url: ,
        // })
        // const index = layer.open({
        //     title: '选择模板',
        //     type: 2,
        //     shade: 0.2,
        //     maxmin: true,
        //     shadeClose: true,
        //     area: '50%',
        //     content: '/page/mbg/select_form.html?tableNames=' + data.map(i => i.tableName).join(","),
        // });
        // $(window).on("resize", function () {
        //     layer.full(index);
        // });
        return false;
    });
});