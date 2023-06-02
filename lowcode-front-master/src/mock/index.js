import Mock from 'mockjs'

Mock.mock('/login/account','post', {
    code: 200,
    data: {
        "avatar":"https://freebrio.oss-cn-shanghai.aliyuncs.com/t/avatar.jpg",
        "name":"保彦慧",
        "token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTY4NSIsImlhdCI6MTY4NDgzNTQwMSwiZXhwIjoxNjg1NDQwMjAxfQ.UGdWMTilnAZJ2wgyP3xoCZft5FgaHu7es96UzWjJvYP3laA5lUO_3GNwZc-2USvWXvYq_HS6Q1dkhz0f_rvz8w","admin":false
    },
    msg: "登陆成功"
})
