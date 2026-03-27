import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import StudentList from '../views/StudentList.vue'
import StudentEdit from '../views/StudentEdit.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/students', name: 'StudentList', component: StudentList, meta: { requiresAuth: true } },
  { path: '/students/add', name: 'StudentAdd', component: StudentEdit, meta: { requiresAuth: true } },
  { path: '/students/:id/edit', name: 'StudentEdit', component: StudentEdit, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
