<template>
  <div>
    <Post v-for="post in posts" :post="post" :key="post.id"/>
  </div>
</template>

<script>
import axios from 'axios'
import Post from './Post'

export default {
  name: "Index",
  props: ['posts'],
  beforeCreate() {
    axios.get('api/1/posts').then(response => this.posts = response.data)
  },
  components: {
    Post
  }
}
</script>

<style scoped>

</style>