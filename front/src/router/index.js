import { createRouter, createWebHistory } from 'vue-router';
import ProductView from '@/views/ProductView.vue';
import CampaignsView from '@/views/CampaignsView.vue';

const routes = [
  { path: '/', component: ProductView },
  { path: '/campaigns/:productId', name: 'campaigns', component: CampaignsView, props: true },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
