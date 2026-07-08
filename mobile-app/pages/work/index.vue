<template>
  <view class="work-container">
    <!-- 轮播图 -->
    <uni-swiper-dot class="uni-swiper-dot-box" :info="data" :current="current" field="content">
      <swiper class="swiper-box" :current="swiperDotIndex" @change="changeSwiper">
        <swiper-item v-for="(item, index) in data" :key="index">
          <view class="swiper-item" @click="clickBannerItem(item)">
            <image :src="item.image" mode="aspectFill" :draggable="false" />
          </view>
        </swiper-item>
      </swiper>
    </uni-swiper-dot>

    <!-- 宫格组件 -->
    <uni-section title="业务办理" type="line"></uni-section>
    <view class="grid-body">
      <uni-grid :column="4" :showBorder="false" @change="changeGrid">
        <uni-grid-item v-for="item in modules" :key="item.text">
          <view class="grid-item-box">
            <uni-icons :type="item.icon" size="30"></uni-icons>
            <text class="text">{{ item.text }}</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>
  </view>
</template>

<script setup>
  import { ref, getCurrentInstance } from "vue"

  const { proxy } = getCurrentInstance()
  const current = ref(0)
  const swiperDotIndex = ref(0)
  const data = ref([{ image: '/static/images/banner/banner01.jpg' }, { image: '/static/images/banner/banner02.jpg' }, { image: '/static/images/banner/banner03.jpg' }])
  const modules = ref([
    { text: '提交申请', icon: 'compose', url: '/pages/business/application/form' },
    { text: '我的申请', icon: 'list', url: '/pages/business/application/index' },
    { text: '进度通知', icon: 'chat-filled', url: '/pages/business/application/index' },
    { text: '证照办理', icon: 'paperclip', url: '/pages/business/application/form' },
    { text: '维修报备', icon: 'settings-filled', url: '/pages/business/application/form' },
    { text: 'AI助手', icon: 'chat', url: '/pages/ai/assistant/index' }
  ])

  function clickBannerItem(item) {
    uni.navigateTo({ url: '/pages/business/application/index' })
  }

  function changeSwiper(e) {
    current.value = e.detail.current
  }

  function changeGrid(e) {
    const item = modules.value[e.detail.index]
    if (!item || !item.url) {
      proxy.$modal.showToast('模块建设中~')
      return
    }
    uni.navigateTo({ url: item.url })
  }
</script>

<style lang="scss" scoped>
  /* #ifndef APP-NVUE */
  page {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    background-color: #fff;
    min-height: 100%;
    height: auto;
  }

  view {
    font-size: 14px;
    line-height: inherit;
  }
  /* #endif */

  .text {
    text-align: center;
    font-size: 26rpx;
    margin-top: 10rpx;
  }

  .grid-item-box {
    flex: 1;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 15px 0;
  }

  .uni-margin-wrap {
    width: 690rpx;
    width: 100%;
    ;
  }

  .swiper {
    height: 300rpx;
  }

  .swiper-box {
    height: 150px;
  }

  .swiper-item {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #fff;
    height: 300rpx;
    line-height: 300rpx;
  }

  @media screen and (min-width: 500px) {
    .uni-swiper-dot-box {
      width: 400px;
      /* #ifndef APP-NVUE */
      margin: 0 auto;
      /* #endif */
      margin-top: 8px;
    }

    .image {
      width: 100%;
    }
  }
</style>
