let e=document.createElement("style");e.innerHTML=".common-layout[data-v-aaaf7660]{height:100%;width:100%;background-color:#cce5f3}.mb-2[data-v-aaaf7660]{--el-alert-bg-color:blue;text-align:center;font-size:20px}.main[data-v-aaaf7660]{width:100%;border:1px solid #f5f5f5;margin-left:-170px;margin-top:65px;background-color:#f5f5f5}",document.head.appendChild(e);import{s as a,r as l,l as t,f as n,a as s,o as u,c as o,e as d,w as i,v as c,b as r,d as f,t as m,g as p,A as _,B as x,E as h,C as g,p as v,h as k,D as y,y as w,G as C,H as b,I as E,J as B,K as I}from"./index.5272c697.js";const T=e=>(v("data-v-aaaf7660"),e=e(),k(),e),z={class:"common-layout"},H={class:"flex items-center"},P=T((()=>r("span",{class:"text-large font-600 mr-3"}," 贵大心理 ",-1))),U=T((()=>r("span",{class:"text-sm mr-2",style:{color:"var(--el-text-color-regular)"}}," 管理员主页 ",-1))),L=T((()=>r("span",null,"首页",-1))),M=T((()=>r("span",null,"个人中心",-1))),N=T((()=>r("span",null,"个人资料",-1))),j=T((()=>r("span",null,"账户管理",-1))),A=T((()=>r("span",null,"人员管理",-1))),D=T((()=>r("span",null,"业务处理",-1))),F=T((()=>r("span",null,"设置",-1))),G=T((()=>r("span",null,"注销账户",-1)));var J={__name:"Manage",setup(e){document.title="管理员主页";const v=()=>{p.back()};function k(){sessionStorage.removeItem("token"),p.push({name:"login"})}function T(){p.push({name:"adminHome"})}function J(){p.push({name:"adminPassword"})}function K(){p.push({name:"adminName"})}function R(){p.push({name:"adminInfo"})}function S(){p.push({name:"adminPfp"})}const q=a().params.username,O=()=>{_.confirm("你的账号将被注销,所有信息将被清空，确定吗?","警告",{cancelButtonText:"取消",confirmButtonText:"确定",type:"warning",icon:x(y)}).then((()=>{(async()=>{try{200===(await n.delete("http://localhost:8091/account/remove",{params:{username:q}})).data.code?(alert("注销成功,如有需要可自行注册!"),await p.push({name:"login"})):h({type:"error",message:"注销失败"})}catch(e){h({type:"error",message:"注销失败"})}})()})).catch((()=>{h({type:"info",message:"放弃操作"})}))},Q=l("");t((async()=>{try{const e=await n.get("http://localhost:8091/admin/selectPfp",{params:{adminName:a().params.username}});Q.value=e.data.data}catch(e){console.error("Error fetching avatar URL:",e)}}));const V=()=>{const e=g.service({lock:!0,text:"数据加载中",background:"rgba(0, 0, 0, 0.7)"});setTimeout((()=>{e.close()}),2e3)};return(e,l)=>{const t=s("el-avatar"),n=s("el-dropdown-item"),_=s("el-dropdown-menu"),x=s("el-dropdown"),h=s("el-tag"),g=s("el-page-header"),q=s("el-header"),W=s("el-icon"),X=s("el-menu-item"),Y=s("el-menu-item-group"),Z=s("el-sub-menu"),$=s("User"),ee=s("el-menu"),ae=s("el-col"),le=s("el-row"),te=s("el-aside"),ne=s("router-view"),se=s("el-main"),ue=s("el-container");return u(),o("div",z,[d(ue,null,{default:i((()=>[d(q,{style:{height:"60px",width:"100%","background-color":"#cce5F3","z-index":"2",position:"fixed"}},{default:i((()=>[d(g,{icon:c(w),style:{"margin-top":"15px"},onBack:v},{content:i((()=>[r("div",H,[d(x,null,{dropdown:i((()=>[d(_,null,{default:i((()=>[d(n,{onClick:k},{default:i((()=>[f("退出登录")])),_:1})])),_:1})])),default:i((()=>[d(t,{size:32,src:Q.value,class:"mr3"},null,8,["src"])])),_:1}),P,U,d(h,null,{default:i((()=>[f("访问管理员:"+m(c(a)().params.username),1)])),_:1})])])),_:1},8,["icon"])])),_:1}),d(ue,null,{default:i((()=>[d(te,{width:"360px"},{default:i((()=>[d(le,{class:"tac",style:{"margin-top":"65px",height:"770px"}},{default:i((()=>[d(ae,{span:12},{default:i((()=>[d(ee,{"active-text-color":"#ffd04b","background-color":"#545c64",class:"el-menu-vertical-demo","default-active":"2","text-color":"#fff"},{default:i((()=>[d(X,{index:"1",onClick:T},{default:i((()=>[d(W,null,{default:i((()=>[d(c(C))])),_:1}),L])),_:1}),d(Z,{index:"2"},{title:i((()=>[d(W,null,{default:i((()=>[d(c(b))])),_:1}),M])),default:i((()=>[d(Z,{index:"2-1"},{title:i((()=>[d(W,null,{default:i((()=>[d(c(E))])),_:1}),N])),default:i((()=>[d(Y,null,{default:i((()=>[d(X,{index:"2-1-1",onClick:R},{default:i((()=>[f(" 基本信息 ")])),_:1}),d(X,{index:"2-1-2",onClick:S},{default:i((()=>[f("头像修改")])),_:1})])),_:1})])),_:1}),d(Z,{index:"2-2"},{title:i((()=>[d(W,null,{default:i((()=>[d($)])),_:1}),j])),default:i((()=>[d(Y,null,{default:i((()=>[d(X,{index:"2-2-1",onClick:J},{default:i((()=>[f("登录密码")])),_:1}),d(X,{index:"2-2-2",onClick:K},{default:i((()=>[f("用户名")])),_:1})])),_:1})])),_:1}),d(Z,{index:"2-3"},{title:i((()=>[d(W,null,{default:i((()=>[d(c(b))])),_:1}),A])),default:i((()=>[d(Y,null,{default:i((()=>[d(X,{index:"2-3-1",onClick:l[0]||(l[0]=e=>{V(),p.push({name:"selectUser"})})},{default:i((()=>[f("用户管理")])),_:1}),d(X,{index:"2-3-2",onClick:l[1]||(l[1]=e=>{V(),p.push({name:"selectConsultant"})})},{default:i((()=>[f("咨询师管理")])),_:1})])),_:1})])),_:1}),d(Z,{index:"2-4"},{title:i((()=>[d(W,null,{default:i((()=>[d(c(B))])),_:1}),D])),default:i((()=>[d(Y,null,{default:i((()=>[d(X,{index:"2-4-1",onClick:l[2]||(l[2]=e=>{V(),p.push({name:"evaluateExa"})}),type:"primary"},{default:i((()=>[f("评价审核 ")])),_:1}),d(X,{index:"2-4-2",onClick:l[3]||(l[3]=e=>{V(),p.push({name:"pmExa"})})},{default:i((()=>[f("私信审核")])),_:1}),d(X,{index:"2-4-3",onClick:l[4]||(l[4]=e=>{V(),p.push({name:"appliExa"})})},{default:i((()=>[f("资质核验")])),_:1})])),_:1})])),_:1})])),_:1}),d(Z,{index:"3"},{title:i((()=>[d(W,null,{default:i((()=>[d(c(I))])),_:1}),F])),default:i((()=>[d(Y,null,{default:i((()=>[d(X,{index:"3-1",onClick:O},{default:i((()=>[d(W,null,{default:i((()=>[d(c(y))])),_:1}),G])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})])),_:1}),d(ue,null,{default:i((()=>[d(se,{class:"main"},{default:i((()=>[d(ne)])),_:1})])),_:1})])),_:1})])),_:1})])}},__scopeId:"data-v-aaaf7660"};export{J as default};