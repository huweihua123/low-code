export default [
  {
    path: '/project',
    meta: { requireLogin: true },
    component: () => import(/* webpackChunkName: 'root' */ '@/views/home'),
    children: [
      {
        path: 'my',
        meta: { requireLogin: true },
        component: () => import(/* webpackChunkName: 'root' */ '@/views/project/my/index')
      },
      {
        path: 'template',
        meta: { requireLogin: true },
        component: () => import(/* webpackChunkName: 'root' */ '@/views/project/template/index')
      },
      {
        path: 'recycle',
        meta: { requireLogin: true },
        component: () => import(/* webpackChunkName: 'root' */ '@/views/project/recycle/index')
      },
    ]
  },
  // {
  //   path: '/project/form/view',
  //   meta: { requireLogin: false },
  //   component: () => import(/* webpackChunkName: 'root' */ '@/views/form/preview/ProjectForm.vue')
  // },
  // {
  //   path: '/project/public/result',
  //   meta: { requireLogin: false },
  //   component: () => import(/* webpackChunkName: 'root' */ '@/views/form/statistics/public')
  // },
  {
    path: '/s/:key',
    meta: { requireLogin: false },
    component: () => import(/* webpackChunkName: 'root' */ '@/views/form/write/index')
  },
  // {
  //   path: '/project/write',
  //   meta: { requireLogin: false },
  //   component: () => import(/* webpackChunkName: 'root' */ '@/views/form/write')
  // }
]
