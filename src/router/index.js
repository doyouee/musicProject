import { createRouter, createWebHistory } from 'vue-router'
import GenreView from '@/views/GenreView.vue'
import CompanyView from '@/views/CompanyView.vue'
import MainView from '@/views/MainView.vue'
import LoginView from '@/views/LoginView.vue'

const routes = [
  {
    path: '/', name:"loginview", component:LoginView
  },
  {
    path: '/main',
    name: 'mainview',
    component: MainView,
    children:[
      {
        // /main/genre
        path: 'genre',
        name: 'genreview',
        component: GenreView
      },
      {
        // /main/company
        path: 'company',
        name: 'companyview',
        component: CompanyView
      }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
