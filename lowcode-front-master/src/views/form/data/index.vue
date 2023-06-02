<template>
    <div className="hello">
        <h1>{{ msg }}</h1>
        <h1>{{ key }}</h1>
        <h1>{{ active }}</h1>

    </div>
</template>

<script>
// 这个函数的作用是：可以使组件在外部文件引用
export default {
    name: 'test',
    data() {
        return {
            msg: 'Hello Vue!',
            key: this.$route.query.key,
            active: this.$route.query.active

        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
    margin: 40px 0 0;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    display: inline-block;
    margin: 0 10px;
}

a {
    color: #42b983;
}
</style>

