import Index from '../views/account/Index.vue'
import {createRouter, createWebHistory} from "vue-router";
//配置路由
const routes = [

    {
        path: '/',
        name: 'index',
        component: Index,
        redirect: '/login',
        children: [
            {
                path: 'login',
                name: 'login',
                component: () => import('../views/account/Login.vue'),
            },
            {
                path: 'register',
                name: 'register',
                component: () => import('../views/account/Register.vue'),
            },
        ]
    },
    {
        path: '/detail/:username/:isConsultant/:phoneNumber',
        name: 'detail',
        component: () => import('../views/Detail.vue'),
        meta: {
            requiresAuth: true,
        },
    },

    {
        path: '/consultantDetail/:consultantName/:consultantPhone/:circleUrl/:username',
        name: 'consultantDetail',
        component: () => import('../views/ConsultantDetail.vue'),
    },

    {
        path: '/personal/:username',
        name: 'userPersonal',
        component: () => import('../views/user/UserPersonal.vue'),
        children: [
            {
                path: 'basic',
                name: 'basic',
                component: () => import('../views/user/BasicInformation.vue'),
            },
            {
                path: 'userPfp',
                name: 'userPfp',
                component: () => import('../views/user/UserPfp.vue'),
            }, {
                path: 'password',
                name: 'password',
                component: () => import('../views/user/UserPassword.vue'),
            }
            , {
                path: 'username',
                name: 'username',
                component: () => import('../views/user/Username.vue'),
            },
            {
                path: 'userReserve',
                name: 'userReserve',
                component: () => import('../views/user/UserReserve.vue'),
            },
            {
                path: 'qualification',
                name: 'qualification',
                component: () => import('../views/user/Qualification.vue'),
            }
            , {
                path: 'home',
                name: 'home',
                component: () => import('../views/user/Home.vue'),
            },
            {
                path: 'evaluate',
                name: 'evaluate',
                component: () => import('../views/user/UserEvaluate.vue'),
            }
            , {
                path: 'userPm',
                name: 'userPm',
                component: () => import('../views/user/UserPm.vue'),
            }
            ,
            {
                path: 'userChat/:isConsultant',
                name: 'userChat',
                component: () => import('../views/Chat.vue'),
            },
            {
                path: 'log',
                name: 'log',
                component: () => import('../views/Log.vue'),
            }

        ]
    },
    {
        path: '/admin/:username',
        name: 'admin',
        component: () => import('../views/admin/Manage.vue'),
        children: [
            {

                path: 'adminHome',
                name: 'adminHome',
                component: () => import('../views/user/Home.vue')
            },
            {
                path: 'adminPassword',
                name: 'adminPassword',
                component: () => import('../views/user/UserPassword.vue'),
            }
            , {
                path: 'adminName',
                name: 'adminName',
                component: () => import('../views/user/Username.vue'),
            },
            {
                path: 'adminInfo',
                name: 'adminInfo',
                component: () => import('../views/admin/AdminInformation.vue'),
            },
            {
                path: 'adminPfp',
                name: 'adminPfp',
                component: () => import('../views/admin/AdminPfp.vue'),
            }
            ,
            {
                path: 'selectUser',
                name: 'selectUser',
                component: () => import('../views/admin/SelectUser.vue'),
            },
            {
                path: 'selectConsultant',
                name: 'selectConsultant',
                component: () => import('../views/admin/SelectConsultant.vue'),
            }
            ,
            {
                path: 'evaluateExa',
                name: 'evaluateExa',
                component: () => import('../views/admin/EvaluateExa.vue'),
            }
            ,
            {
                path: 'pmExa',
                name: 'pmExa',
                component: () => import('../views/admin/PmExa.vue'),
            }
            ,
            {
                path: 'appliExa',
                name: 'appliExa',
                component: () => import('../views/admin/AppliExa.vue'),
            },
            {
                path: 'adminLog',
                name: 'adminLog',
                component: () => import('../views/Log.vue'),
            }
            ,
            {
                path: 'notice',
                name: 'notice',
                component: () => import('../views/admin/Notice.vue'),
            }

        ]
    },

    {
        path: '/application',
        name: 'application',
        component: () => import('../views/user/Application.vue'),
    }
    ,
    {
        path: '/reserve/:consultantName/:consultantPfp/:username',
        name: 'reserve',
        component: () => import('../views/user/Reserve.vue'),
    }
    ,
    {
        path: '/consultantPersonal/:username',
        name: 'consultantPersonal',
        component: () => import('../views/consultant/ConsultantPersonal.vue'),
        children: [
            {
                path: 'consultantHome',
                name: 'consultantHome',
                component: () => import('../views/user/Home.vue'),
            }
            ,
            {
                path: 'consultantInformation',
                name: 'consultantInformation',
                component: () => import('../views/consultant/ConsultantInformation.vue'),
            }
            ,
            {
                path: 'consultantPfp',
                name: 'consultantPfp',
                component: () => import('../views/consultant/ConsultantPfp.vue'),
            },
            {
                path: 'consultantPassword',
                name: 'consultantPassword',
                component: () => import('../views/user/UserPassword.vue'),
            }
            , {
                path: 'consultantUsername',
                name: 'consultantUsername',
                component: () => import('../views/user/Username.vue'),
            },
            {
                path: 'consultantRes',
                name: 'consultantRes',
                component: () => import('../views/consultant/ConsultantReserve.vue'),
            },
            {
                path: 'consultantQualification',
                name: 'consultantQualification',
                component: () => import('../views/consultant/ConsultantQualification.vue'),
            }
            ,
            {
                path: 'consultantEvaluate',
                name: 'consultantEvaluate',
                component: () => import('../views/consultant/ConsultantEvaluate.vue'),
            }
            ,
            {
                path: 'consultantPm',
                name: 'consultantPm',
                component: () => import('../views/consultant/ConsultantPm.vue'),
            },
            {
                path: 'chat/:isConsultant/:reservationUser/:reservationConsultant',
                name: 'chat',
                component: () => import('../views/Chat.vue'),
            },
            {
                path: 'consultantLog',
                name: 'consultantLog',
                component: () => import('../views/Log.vue'),
            }

        ]
    },


]
const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = sessionStorage.getItem('token');

    if (authRequired && !loggedIn) {
        alert("请先进行登录！")
        return next('/login');
    }

    next();
})

export default router;