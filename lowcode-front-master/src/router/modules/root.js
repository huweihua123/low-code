export default [
  {
    path: '/',
    meta: { requireLogin: false },
    component: () => import(/* webpackChunkName: 'root' */ '@/views/home/index')
  },
  // {
  //   path: '/',
  //   meta: { requireLogin: false },
  //   component: () => import(/* webpackChunkName: 'root' */ '@/test.vue')
  // },
  {
    path: '/login',
    meta: { requireLogin: false },
    component: () => import(/* webpackChunkName: 'root' */ '@/views/account/login/index')
  },
  {
    path: '/home',
    meta: { requireLogin: true },
    component: () => import(/* webpackChunkName: 'root' */ '@/views/home'),
    children: [
      {
        path: '/',
        meta: { requireLogin: true },
        component: () => import(/* webpackChunkName: 'root' */ '@/views/project/my/index')
      },
      {
        path: 'member',
        meta: { requireLogin: true },
        component: () => import(/* webpackChunkName: 'root' */ '@/views/account/member')
      }
    ]
  },
]
