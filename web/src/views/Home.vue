<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          @click="handleClick"
          :openKeys="openKeys"
          :style="{ height: '100%', borderRight: 0 }"
      >
        <a-menu-item key="welcome">
          <MailOutlined />
          <span>欢迎</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span><user-outlined />{{item.name}}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined /><span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item key="tip" :disabled="true">
          <span>以上菜单在分类管理配置</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div class="welcome" v-show="isShowWelcome">
        <h1>欢迎</h1>
      </div>
      <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }"
                 :data-source="books">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
                <span>
                <component v-bind:is="'FileOutlined'" style="margin-right: 8px" />
                {{ item.docCount }}
              </span>
                <span>
                <component v-bind:is="'UserOutlined'" style="margin-right: 8px" />
                {{ item.viewCount }}
              </span>
                <span>
                <component v-bind:is="'LikeOutlined'" style="margin-right: 8px" />
                {{ item.voteCount }}
              </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <router-link :to="`/doc?ebookId=${item.id}`">{{item.name}}</router-link>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
    </a-layout-content>
  </a-layout>

</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref, toRef} from 'vue';
import axios from "axios";
import {message} from 'ant-design-vue';
import {Tool} from '@/util/tool';

export default defineComponent({
  name: 'Home',
  setup(){
    // const ebooks = ref();
    const ebooks = reactive({books:[]});

    const level1 = ref();
    let categories: any;
    const handleQueryCategory = () => {
      axios.get("/category/all").then((response) => {
        const data = response.data;
        if (data.success) {
          categories = data.content;
          console.log("原始数组： ", categories);
          level1.value = [];
          level1.value = Tool.array2Tree(categories, 0);
          console.log("树形结构：",level1);
        } else {
          message.error(data.message);
        }
      });
    };

    const isShowWelcome = ref(true);

    let categoryId2 = 0;

    const handleQueryEBook = () =>{
      axios.get(`/ebook/list`,{
        params:{
          page: 1,
          size: 1000,
          categoryId2
        }
      }).then(
          (response)=>{
            const data = response.data;
            ebooks.books = data.content.list
          }
      )
    }


    const handleClick = (value: any)=>{
      // console.log("menu click")
      // isShowWelcome.value = value.key === 'welcome';
      if(value.key === 'welcome'){
        isShowWelcome.value = true;
      }else{
        categoryId2 = value.key;
        isShowWelcome.value = false;
        handleQueryEBook();
      }
    }

    // const actions: Record<string, string>[] = [
    //   { type: 'StarOutlined', text: '156' },
    //   { type: 'LikeOutlined', text: '156' },
    //   { type: 'MessageOutlined', text: '2' },
    // ];



    onMounted(()=>{
      handleQueryCategory();
      console.log('onMounted222')

    })
    return {
      books: toRef(ebooks, 'books'),
      level1,
      handleClick,
      isShowWelcome
    }
  }

});
</script>
<style scoped>
  .ant-avatar{
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>