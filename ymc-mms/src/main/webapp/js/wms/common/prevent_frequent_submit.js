    //存储变量信息
    var VAR ={
        repeatTemp:[]
    }

    var COM = {
        repeat:function(s,t){//限制执行频率，默认为1秒 允许执行时返回false
            t = t ? t * 1000 : 1000;//毫秒
            var time = microtime();
                if(!VAR.repeatTemp[s]){
                    VAR.repeatTemp[s] = time;
                    return false;//允许
                }else{
                    var ts = t - (time - VAR.repeatTemp[s]);
                    ts = parseInt(ts/1000);
                if(ts > 0){
                    //alert("您操作的太频繁了，请休息一下：还有 "+ ts +"秒才可以再执行！");
                    return true;//禁止执行
                }else{

                    VAR.repeatTemp[s] = time;//更新时间
                    return false;//允许
                }
            }
        }
    }


    //刷新 - 例子
    //function ref(){
    //    var btn = COM.repeat('btn'); //btn为ID 
    //    if(!btn){alert("可以执行了！");}else{return;}
    //}
    
    //实际调用例子
//    var btn = COM.repeat(e);
//    if(!btn){
//         $("#insideCode").removeAttr("disabled");	
//     	//alert("可以执行了！");
//     }else{
//     	return;
//     }


    //获取毫秒级时间戳
    function microtime(){

        return new Date().getTime();
    }