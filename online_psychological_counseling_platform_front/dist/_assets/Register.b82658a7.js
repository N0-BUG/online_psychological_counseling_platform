let e=document.createElement("style");e.innerHTML=".mb-2[data-v-81702880]{margin-top:25px;margin-left:60px;opacity:.9}.t3[data-v-81702880]{font-size:30px;margin-bottom:80px;margin-top:-20px;margin-left:80px;letter-spacing:90px;opacity:.9}.link[data-v-81702880]{padding-top:20px;margin-left:110px;font-size:20px;opacity:.9}",document.head.appendChild(e);import{r as a,a as l,o as t,c as s,b as n,d as o,e as p,w as i,F as u,E as d,f as r,g as m,p as c,h as v}from"./index.5272c697.js";const y=e=>(c("data-v-81702880"),e=e(),v(),e),g=y((()=>n("h1",{class:"t3"}," 注册 ",-1))),w={style:{opacity:"0.9"}},f=y((()=>n("br",null,null,-1))),h={style:{opacity:"0.9"}},x={style:{"margin-left":"20px","margin-top":"30px",opacity:"0.9"}},V=y((()=>n("br",null,null,-1))),b={style:{"margin-left":"42px",opacity:"0.9"}},_=y((()=>n("br",null,null,-1))),k={class:"link"};var U={__name:"Register",setup(e){document.title="注册";const c=a(""),v=a("");a("");const y=a(""),U=a("");async function E(){if(c.value){if(e=c.value,!/^1[3456789]\d{9}$/.test(e))return d({type:"warning",message:"手机号格式不正确"}),void(c.value="");var e;if(v.value)if(y.value)if(U.value){if(y.value!=U.value)return d({type:"warning",message:"两次输入密码不同"}),void(U.value="");try{const e=await r.post("http://localhost:8091/account/register",{phoneNumber:c.value,username:v.value,password:y.value});if(200!==e.data.code)throw alert(e.data.msg),new Error("状态码不是200");alert(e.data.data),await m.push("/")}catch(e){console.log("请求失败")}}else d({type:"warning",message:"请输入确认密码"});else d({type:"warning",message:"密码不能为空"});else d({type:"warning",message:"用户名不能为空"})}else d({type:"warning",message:"手机号不能为空"})}return(e,a)=>{const d=l("el-input"),r=l("el-button"),m=l("router-link");return t(),s(u,null,[g,n("div",w,[o(" 请输入手机号: "),p(d,{modelValue:c.value,"onUpdate:modelValue":a[0]||(a[0]=e=>c.value=e),style:{width:"240px"},placeholder:"Please input"},null,8,["modelValue"])]),f,n("div",h,[o(" 请输入用户名: "),p(d,{modelValue:v.value,"onUpdate:modelValue":a[1]||(a[1]=e=>v.value=e),style:{width:"240px"},placeholder:"Please input"},null,8,["modelValue"])]),n("div",x,[o("请输入密码: "),p(d,{modelValue:y.value,"onUpdate:modelValue":a[2]||(a[2]=e=>y.value=e),style:{width:"240px"},type:"password",placeholder:"请输入密码","show-password":""},null,8,["modelValue"])]),V,n("div",b,[o("确认密码: "),p(d,{modelValue:U.value,"onUpdate:modelValue":a[3]||(a[3]=e=>U.value=e),style:{width:"240px"},type:"password",placeholder:"请确认密码","show-password":""},null,8,["modelValue"])]),p(r,{type:"primary",onClick:E,style:{opacity:"0.9"}},{default:i((()=>[o("点此注册")])),_:1}),_,n("div",k,[p(m,{to:"/login"},{default:i((()=>[o("已有账号？去登录")])),_:1})])],64)}},__scopeId:"data-v-81702880"};export{U as default};