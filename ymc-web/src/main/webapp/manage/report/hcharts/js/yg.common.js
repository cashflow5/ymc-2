(function(window) {
    var G = {
        ExcuteModule: function(id, excutor) {
            if ($(id).length > 0) {
                try {
                    excutor();
                } catch (e) {
                    console.error('Hey,回调方法不能为空！');
                }
            }
        }
    };
    window.G = G;
})(window);

/****************
**ygSwitch
**2012-12-25 xiao.y@yougou.com
功能：旋转木马、tab切换、上下左右滑动
调用：
$("#div").ygSwitch("#div>ul>li",{config});
*****************/
eval(function(E, I, A, D, J, K, L, H) {
    function C(A) {
        return A < 62 ? String.fromCharCode(A += A < 26 ? 65 : A < 52 ? 71 : -4) : A < 63 ? '_' : A < 64 ? '$' : C(A >> 6) + C(A & 63)
    }
    while (A > 0) K[C(D--)] = I[--A];

    function N(A) {
        return K[A] == L[A] ? A : K[A]
    }
    if (''.replace(/^/, String)) {
        var M = E.match(J),
            B = M[0],
            F = E.split(J),
            G = 0;
        if (E.indexOf(F[0])) F = [''].concat(F);
        do {
            H[A++] = F[G++];
            H[A++] = N(B)
        } while (B = M[G]);
        H[A++] = F[G] || '';
        return H.join('')
    }
    return E.replace(J, N)
}('p.o(p.BN,{Ba:v(C,M,A,B,N){m-B*((M=M/N-L)*M*M*M-L)+A}});(v(N){N.w=N.w||{};N.w={S:{t:U,BR:"current",BA:K,BX:"Bo",Bb:K.L,Bu:"Bl",j:L,u:L,Bq:K.B0,BN:"Ba",z:i,1:i,Bv:i,_:U,3:U,R:i,x:i,BF:U,Bk:U,6:"next_btn_dis",7:"prev_btn_dis",8:U},Bh:v(N,M){A[N]=M}};P A={"Bl":v(M,N){T.5().hide();T.$(M).show();N.BC()},"fade":v(M,N){T.5().fadeOut();T.$(M).fadeIn();N.BC()},"ajax":v(M,N){T.5().Bx().load(T.BP().BL(M).b("a"),N)}};v M(I,C,H){P G=T,F=N(T),E=K,D=N(H.BF),M=N(H.Bk),B=e.BK(C.n/H.j)-L;N.V(H,v(M,A){O(N.Bi(A))F.X(M,A)});N.o(T,{h:v(J,C){O(!H.z&&J>=G.BB()-L)D.4(H.6);O(!H.z&&J<=K)M.4(H.7);O(H.t!=U){P B=I.BL(J);O(r J=="string"&&J.Bf("#","")){B=I.filter("[a*="+J.Bf("#","")+"]");J=e.max(B.index(),K)}I.BW(H.BR);B.4(H.BR)}C=C||N.Event();C.BO="_";F.t(C,[J]);O(C.Be())m;A[H.Bu].BC(G,J,v(){C.BO="3";F.t(C,[J])});C.BO="onStart";F.t(C,[J]);O(C.Be())m;E=J;O(H.8)N(H.8).BU("BS:Bx").Bw(J+L);m G},BH:v(){m H},BP:v(){m N(I)},5:v(){m C},BB:v(){m e.BK(C.n/H.j)},$:v(N){m G.5().k(N*H.j,(N+L)*H.j)},Bp:v(){m E},x:v(A){O(H.x){P M=T.$(A);M.V(v(A,M){N(T).d("img").V(v(A,M){P B=N(T).b("original"),C=N(T).b("Bd");O(B!=""&&C!=B)N(T).b("Bd",B)})})}},f:v(N){G.x(N);O(C.s().BV(":BM")||C.n<=H.u)m G;O(r N=="BI"){O(N<K)m H.z?G.h(B):G;Y O(N>B)m H.z?G.h(K):G;Y m G.h(N)}Y m G.h()},c:v(){O(D.Bs(H.6))m;O(H.7)M.BW(H.7);m G.f(E+L)},BZ:v(){O(M.Bs(H.7))m;O(H.6)D.BW(H.6);m G.f(E-L)},X:v(N,M){F.X(N,M);m G},Bj:v(N){F.Bj(N);m G},_:v(N){m T.X("_",N)},3:v(N){m T.X("3",N)},BT:v(N){}});N(H.BF).X("h",v(){G.c()});M.X("h",v(){G.BZ()});O(H.BA===K||H.BA>K)G.h(H.BA);C.d("By[a^=#]").h(v(M){G.h(N(T).b("a"),M)});O(H.Bv)C.Q("cursor","pointer").h(v(){G.c();m i});O(I){P J;I.V(v(M){O(H.BX==="Bo")N(T).X({"mouseenter":v(N){O(M!==E){G.x(M);J=9(v(){G.h(M,N)},H.Bb*Z)}},"mouseleave":v(){BQ(J)}});Y N(T).X("h",v(N){O(M!==E)G.h(M,N);m i})})}}N.BG.w=v(C,E){P B=T.BL(r E=="BI"?E:K).Bc("w");O(B)m B;O(N.Bi(E))E={_:E};P A=N.o({},N.w.S),D=T.n;E=N.o(A,E);T.V(v(J){P G=N(T),F=C.jquery?C:G.BU(C);O(!F.n)F=D==L?N(C):G.s().d(C);P I=U;O(E.t!=U)I=G.d(E.t);P H=e.BK(F.n/E.j);O(E.t!=U&&N(T).d(E.t).n<=K){for(P J=L;J<=H;J++)N("<"+E.t+">",{a:"javascript:void(K);",target:"_self",text:J}).appendTo(N(T));I=G.BU(E.t)}O(E.8!=U){P A=N(E.8);A.Bw("\\u7b2c<BS>L</BS>\\Bm\\uff0c\\u5171 "+H+" \\Bm")}B=new M(I,F,E);G.Bc("w",B)});m E.R?B:T}})(p);N.w.Bh("scroll",v(I,A){P D=T,G=D.BH(),F=D.$(I),N=D.5().s(),C=D.Bp(),E=D.BB()-L,M=(C===K&&I===E)||(C===E&&I===K),H=(C===K&&I===E)?W:i,B=G.1?{BD:-F.0().BD}:{g:-F.0().g};O(N.BV(":BM"))N.Bn(W);N.animate(B,G.Bq*Z,G.BN,v(){A.BC();O(M)D.BT(H)})});(v(N){N.BG.carousel=v(){T.V(v(){P F=N(T).w(),G=F.BH(),D=F.5(),A=D.s(),B=F.BB()-L,H=D.k(K,G.j),J=D.k(B*G.j),M=G.1?J.0().BD:J.0().g,E=G.1?"BD":"g",C=D.n>G.u,I=K;D.Q("0","relative").V(v(){I+=G.1?N(T).outerHeight(W):N(T).outerWidth(W)});O(C&&J.n<G.u)A.append(D.k(K,G.u).BY().4("BY"));N.o(F,{f:v(N){F.x(N);O(A.BV(":BM")||!C)m T;O(N<K){T.BJ(W);m T.h(B)}Y O(N>B){T.BJ(i);m T.h(K)}Y m T.h(N)},BJ:v(A){P M=A?J:H;N.V(M,v(){N(T).Q(E,A?-I:I+"Bt")})},BT:v(C){P B=C?J:H;N.V(B,v(){N(T).Q(E,"0px")});A.Q(E,C?-M:K+"Bt")}})});m T}})(p);(v(M){P N=M.w;N.q=N.q||{};N.q.2={S:{2:W,y:Bz,Bg:W,R:i}};M.BG.2=v(C){O(r C=="BI")C={y:C};P B=M.o({},N.q.2.S),A;M.o(B,C);T.V(v(){P D=M(T).w();O(D)A=D;P C,E,N=W;D.BE=v(){O(C)m;N=i;C=setInterval(v(){D.c()},B.y*Z);D.c()};D.l=v(){C=clearInterval(C)};D.Bn=v(){D.l();N=W};O(B.Bg){D.5().Br(v(){D.l();BQ(E)},v(){O(!N)E=9(D.BE,B.y*Z)});D.BP().Br(v(){D.l();BQ(E)},v(){O(!N)E=9(D.BE,B.y*Z)})}O(B.2)9(D.BE,B.y*Z)});m B.R?A:T}})(p)', '0|1|_|$|if|var|css|api|cfg|this|null|each|true|bind|else|1000|href|attr|next|find|Math|move|left|click|false|steps|slice|pause|return|length|extend|jQuery|plugin|typeof|parent|trigger|visible|function|ygSwitch|lazyload|interval|circular|position|vertical|autoplay|onSwitch|addClass|getPanels|nextBtnDis|prevBtnDis|pagenation|setTimeout|beforeSwitch|getVisiblePanel|initIndex|getLength|call|top|play|nextBtn|fn|getCfg|number|adjustPosition|ceil|eq|animated|easing|type|getTriggers|clearTimeout|currCls|span|resetPosition|children|is|removeClass|triggerType|clone|prev|easeOutQuart|delay|data|src|isDefaultPrevented|replace|autopause|addEffect|isFunction|unbind|prevBtn|default|u9875|stop|mouse|getIndex|speed|hover|hasClass|px|effect|panelSwitch|html|first|a|3|6'.split('|'), 107, 116, /[\w\$]+/g, {}, {}, []));

/*autocomplete*/
eval(function(E, I, A, D, J, K, L, H) {
    function C(A) {
        return A < 62 ? String.fromCharCode(A += A < 26 ? 65 : A < 52 ? 71 : -4) : A < 63 ? '_' : A < 64 ? '$' : C(A >> 6) + C(A & 63)
    }
    while (A > 0) K[C(D--)] = I[--A];

    function N(A) {
        return K[A] == L[A] ? A : K[A]
    }
    if (''.replace(/^/, String)) {
        var M = E.match(J),
            B = M[0],
            F = E.split(J),
            G = 0;
        if (E.indexOf(F[0])) F = [''].concat(F);
        do {
            H[A++] = F[G++];
            H[A++] = N(B)
        } while (B = M[G]);
        H[A++] = F[G] || '';
        return H.join('')
    }
    return E.replace(J, N)
}('(3(Y){Y.BY.BU({8:3(X,B){a A=z X=="BJ";B=Y.BU({},Y._.BW,{d:A?X:n,i:A?n:X,BQ:A?Y._.BW.BQ:Bf,c:B&&!B.BS?Bf:150},B);B.BN=B.BN||3(Y){v Y};B.BO=B.BO||B.BZ;v h.k(3(){Bg Y._(h,B)})},y:3(Y){v h.m("y",Y)},0:3(Y){v h.2("0",[Y])},Ba:3(){v h.2("Ba")},Bo:3(Y){v h.2("Bo",[Y])},Bl:3(){v h.2("Bl")}});Y._=3(H,T){a E={Bx:38,Ca:40,Cb:46,Cn:Cx,Bz:13,Cc:27,Cf:188,CU:33,B0:34,CT:W},R=Y(H).attr("8","off").4(T.Cd),S,Q="",B=Y._.Ce(T),F=U,BH,M={BR:q},C=Y._.Cg(T,H,X,M),L;Y.Bm.CX&&Y(H.CS).m("submit.8",3(){Z(L){L=q;v q}});R.m((Y.Bm.CX?"keypress":"keydown")+".8",3(A){F=V;BH=A.CJ;switch(A.CJ){l E.Bx:A.BD();Z(C.1())C.By();g K(U,j);t;l E.Ca:A.BD();Z(C.1())C.B3();g K(U,j);t;l E.CU:A.BD();Z(C.1())C.CR();g K(U,j);t;l E.B0:A.BD();Z(C.1())C.CV();g K(U,j);t;l T.5&&Y.BB(T.$)==","&&E.Cf:l E.Cn:l E.Bz:Z(X()){A.BD();L=j;v q}t;l E.Cc:C.o();t;default:Bi(S);S=Bh(K,T.BQ);t}}).CQ(3(){F++}).blur(3(){F=U;Z(!M.BR)O()}).CW(3(){Z(F++>V&&!C.1())K(U,j)}).m("0",3(){a X=(BI.w>V)?BI[V]:n;3 A(A,B){a Y;Z(B&&B.w)f(a C=U;C<B.w;C++)Z(B[C].y.7()==A.7()){Y=B[C];t}Z(z X=="3")X(Y);g R.2("y",Y&&[Y.i,Y.s])}Y.k(G(R.b()),3(X,Y){J(Y,A,A)})}).m("Ba",3(){B.CN()}).m("Bo",3(){Y.BU(T,BI[V]);Z("i"Cp BI[V])B.CB()}).m("Bl",3(){C.BT();R.BT();Y(H.CS).BT(".8")}).m("input",3(){K(U,j)});3 X(){a B=C.Co();Z(!B)v q;a A=B.y;Q=A;Z(T.5){a E=G(R.b());Z(E.w>V){a D=T.$.w,F=Y(H).6().BP,I,X=U;Y.k(E,3(A,Y){X+=Y.w;Z(F<=X){I=A;v q}X+=D});E[I]=A;A=E.B7(T.$)}A+=T.$}R.b(A);N();R.2("y",[B.i,B.s]);v j}3 K(Y,X){Z(BH==E.Cb){C.o();v}a A=R.b();Z(!X&&A==Q)v;Q=A;A=D(A);Z(A.w>=T.Bn){R.4(T.BX);Z(!T.BK)A=A.7();J(A,B4,N)}g{I();C.o()}}3 G(X){Z(!X)v[""];Z(!T.5)v[Y.BB(X)];v Y.map(X.Bb(T.$),3(A){v Y.BB(X).w?Y.BB(A):n})}3 D(B){Z(!T.5)v B;a X=G(B);Z(X.w==V)v X[U];a A=Y(H).6().BP;Z(A==B.w)X=G(B);g X=G(B.Be(B.Bv(A),""));v X[X.w-V]}3 A(X,A){Z(T.CD&&(D(R.b()).7()==X.7())&&BH!=E.CT){R.b(R.b()+A.Bv(D(Q).w));Y(H).6(Q.w,Q.w+A.w)}}3 O(){Bi(S);S=Bh(N,Bw)}3 N(){a Y=C.1();C.o();Bi(S);I();Z(T.CA)R.0(3(Y){Z(!Y)Z(T.5){a X=G(R.b()).u(U,-V);R.b(X.B7(T.$)+(X.w?T.$:""))}g{R.b("");R.2("y",n)}})}3 B4(Y,X){Z(X&&X.w&&F){I();C.CF(X,Y);A(Y,X[U].s);C.Bp()}g N()}3 J(A,F,X){Z(!T.BK)A=A.7();a E=B.B2(A);Z(E&&E.w)F(A,E);g Z((z T.d=="BJ")&&(T.d.w>U)){a G={timestamp:+Bg Date()};Y.k(T.B6,3(X,Y){G[X]=z Y=="3"?Y():Y});Y.ajax({mode:"abort",port:"8"+H.name,Cr:T.Cr,d:T.d,i:Y.BU({Cv:D(A),limit:T.c},G),success:3(X){a Y=T.B5&&T.B5(X)||P(X);B.Cm(A,Y);F(A,Y)}})}g{C.Cq();X(A)}}3 P(A){a X=[],D=A.Bb("\\Cu");f(a C=U;C<D.w;C++){a B=Y.BB(D[C]);Z(B){B=B.Bb("|");X[X.w]={i:B,s:B[U],y:T.BV&&T.BV(B,B[U])||B[U]}}}v X}3 I(){R.BC(T.BX)}};Y._.BW={Cd:"ac_input",Ch:"ac_results",BX:"ac_loading",Bn:V,BQ:Bw,BK:q,Cl:j,BF:q,BA:Bf,c:100,CA:q,B6:{},Bj:j,BZ:3(Y){v Y[U]},BO:n,CD:q,r:U,5:q,$:", ",BN:3(X,Y){v X.Be(Bg RegExp("(?![^&;]+;)(?!<[^<>]*)("+Y.Be(/([\\^\\Y\\(\\)\\[\\]\\{\\}\\*\\.\\+\\?\\|\\\\])/B1,"\\\\CI")+")(?![^<>]*>)(?![^&;]+;)","B1"),"<Ci>CI</Ci>")},BS:j,BL:180};Y._.Ce=3(F){a C={},B=U;3 D(X,Y){Z(!F.BK)X=X.7();a A=X.CO(Y);Z(F.BF=="word")A=X.7().0("\\\\Ct"+Y.7());Z(A==-V)v q;v A==U||F.BF}3 E(Y,A){Z(B>F.BA)X();Z(!C[Y])B++;C[Y]=A}3 A(){Z(!F.i)v q;a D={},H=U;Z(!F.d)F.BA=V;D[""]=[];f(a I=U,C=F.i.w;I<C;I++){a B=F.i[I];B=(z B=="BJ")?[B]:B;a A=F.BO(B,I+V,F.i.w);Z(A===q)Bu;a X=A.charAt(U).7();Z(!D[X])D[X]=[];a G={s:A,i:B,y:F.BV&&F.BV(B)||A};D[X].Bs(G);Z(H++<F.c)D[""].Bs(G)}Y.k(D,3(X,Y){F.BA++;E(X,Y)})}Bh(A,25);3 X(){C={};B=U}v{CN:X,Cm:E,CB:A,B2:3(A){Z(!F.BA||!B)v n;Z(!F.d&&F.BF){a G=[];f(a E Cp C)Z(E.w>U){a X=C[E];Y.k(X,3(X,Y){Z(D(Y.s,A))G.Bs(Y)})}v G}g Z(C[A])v C[A];g Z(F.Cl)f(a H=A.w-V;H>=F.Bn;H--){X=C[A.substr(U,H)];Z(X){G=[];Y.k(X,3(X,Y){Z(D(Y.s,A))G[G.w]=Y});v G}}v n}}};Y._.Cg=3(Q,J,B,O){a M={x:"ac_over"},A,P=-V,D,L="",H=j,X,G;3 E(){Z(!H)v;X=Y("<div/>").o().4(Q.Ch).e("position","absolute").Bt(Bc.B9);G=Y("<ul/>").Bt(X).mouseover(3(X){Z(N(X).B_&&N(X).B_.toUpperCase()=="Cs"){P=Y("Br",G).BC(M.x).index(N(X));Y(N(X)).4(M.x)}}).CW(3(X){Y(N(X)).4(M.x);B();Z(Y(".CZ").w>U)Y(".CZ").parent().4("ph-wrap-has");J.CQ();v q}).mousedown(3(){O.BR=j}).mouseup(3(){O.BR=q});Z(Q.r>U)X.e("r",Q.r);H=q}3 N(X){a Y=X.target;while(Y&&Y.tagName!="Cs")Y=Y.parentNode;Z(!Y)v[];v Y}3 I(B){A.u(P,P+V).BC(M.x);F(B);a Y=A.u(P,P+V).4(M.x);Z(Q.BS){a X=U;A.u(U,P).k(3(){X+=h.9});Z((X+Y[U].9-G.BE())>G[U].clientHeight)G.BE(X+Y[U].9-G.innerHeight());g Z(X<G.BE())G.BE(X)}}3 F(Y){P+=Y;Z(P<U)P=A.p()-V;g Z(P>=A.p())P=U}3 K(Y){v Q.c&&Q.c<Y?Q.c:Y}3 C(){G.CP();a C=K(D.w);f(a E=U;E<C;E++){Z(!D[E])Bu;a B=Q.BZ(D[E].i,E+V,C,D[E].s,L);Z(B===q)Bu;a X=Y("<Br/>").html(Q.BN(B,L)).4(E%Cw==U?"ac_even":"ac_odd").Bt(G)[U];Y.i(X,"CG",D[E])}A=G.find("Br");Z(Q.Bj){A.u(U,V).4(M.x);P=U}Z(Y.BY.CM)G.CM()}v{CF:3(Y,X){E();D=Y;L=X;C()},B3:3(){I(V)},By:3(){I(-V)},CR:3(){Z(P!=U&&P-W<U)I(-P);g I(-W)},CV:3(){Z(P!=A.p()-V&&P+W>A.p())I(A.p()-V-P);g I(W)},o:3(){X&&X.o();A&&A.BC(M.x);P=-V},1:3(){v X&&X.is(":1")},current:3(){v h.1()&&(A.Ck("."+M.x)[U]||Q.Bj&&A[U])},Bp:3(){a D=Y(J).offset();X.e({r:z Q.r=="BJ"||Q.r>U?Q.r:Y(J).r(),CK:D.CK+J.9,Bd:D.Bd}).Bp();Z(Q.BS){G.BE(U);G.e({Cj:Q.BL,overflow:"auto"});Z(Y.Bm.msie&&z Bc.B9.style.Cj==="BM"){a B=U;A.k(3(){B+=h.9});a C=B>Q.BL;G.e("height",C?Q.BL:B);Z(!C)A.r(G.r()-CH(A.e("B$-Bd"))-CH(A.e("B$-right")))}}},Co:3(){a X=A&&A.Ck("."+M.x).BC(M.x);v X&&X.w&&Y.i(X[U],"CG")},Cq:3(){G&&G.CP()},BT:3(){X&&X.remove()}}};Y.BY.6=3(C,F){Z(C!==BM)v h.k(3(){Z(h.Bq){a Y=h.Bq();Z(F===BM||C==F){Y.move("Bk",C);Y.CE()}g{Y.collapse(j);Y.moveStart("Bk",C);Y.moveEnd("Bk",F);Y.CE()}}g Z(h.B8)h.B8(C,F);g Z(h.BG){h.BG=C;h.CL=F}});a X=h[U];Z(X.Bq){a B=Bc.6.createRange(),E=X.s,D="<->",A=B.CC.w;B.CC=D;a Y=X.s.CO(D);X.s=E;h.6(Y,Y+A);v{BP:Y,CY:Y+A}}g Z(X.BG!==BM)v{BP:X.BG,CY:X.CL}}})(jQuery)', 'V|0|1|8|_|$|if|var|val|max|url|css|for|else|this|data|true|each|case|bind|null|hide|size|false|width|value|break|slice|return|length|ACTIVE|result|typeof|search|visible|trigger|function|addClass|multiple|selection|toLowerCase|autocomplete|offsetHeight|Autocompleter|multipleSeparator|cacheLength|trim|removeClass|preventDefault|scrollTop|matchContains|selectionStart|T|arguments|string|matchCase|scrollHeight|undefined|highlight|formatMatch|start|delay|mouseDownOnSelect|scroll|unbind|extend|formatResult|defaults|loadingClass|fn|formatItem|flushCache|split|document|left|replace|10|new|setTimeout|clearTimeout|selectFirst|character|unautocomplete|browser|minChars|setOptions|show|createTextRange|li|push|appendTo|continue|substring|200|UP|prev|RETURN|PAGEDOWN|gi|load|next|U|parse|extraParams|join|setSelectionRange|body|nodeName|padding|mustMatch|populate|text|autoFill|select|display|ac_data|parseInt|$1|keyCode|top|selectionEnd|bgiframe|flush|indexOf|empty|focus|pageUp|form|BACKSPACE|PAGEUP|pageDown|click|opera|end|placeholder|DOWN|DEL|ESC|inputClass|Cache|COMMA|Select|resultsClass|strong|maxHeight|filter|matchSubset|add|TAB|selected|in|emptyList|dataType|LI|b|n|q|2|9'.split('|'), 159, 177, /[\w\$]+/g, {}, {}, []));

/********
*飘窗控制
$.jqScrollTop({
    startPos:10, //滚动条滚动多高开始显示
    fixedBottom:false,//是否永远在底部
    show:false //初始化是否显示
    })
********/
(function($) {
    $.fn.jqScrollTop = function(options) {
        options = $.extend({
            startPos: 0,
            bottom: 150,
            fixedBottom: false,
            fixedRight: true,
            show: false
        }, options || {});
        var ie6 = !-[1, ] && !window.XMLHttpRequest;
        var _this = $(this);
        var _bottom = options.bottom;
        var _start = _this.parent().offset().top;
        _this.css({
            "bottom": _bottom,
            "top": "auto"
        });
        !options.fixedRight ? _this.css({
            left: '50%',
            'margin-left': $('body .cen').width() / 2 + 10
        }) : '';
        ie6 ? _this.css({
            "top": $(window).height() - _this.height()
        }) : '';
        options.show ? _this.show() : _this.hide();
        $(window).scroll(function() {
            $(document).scrollTop() > options.startPos ? _this.fadeIn(200) : _this.fadeOut(200);
            if (!options.fixedBottom) {
                var f = $(document).scrollTop() + $(window).height(),
                    $footer = $(".yg_footer_nav");
                if ($footer[0]) {
                    f = f - $footer.offset().top;
                }
                if (f > 0) {
                    _this.css({
                        bottom: function() {
                            return f + options.bottom;
                        }
                    })
                } else {
                    _this.css({
                        "bottom": options.bottom,
                        "top": "auto"
                    });
                    if (ie6) {
                        _this.css({
                            "top": $(document).scrollTop() - _this.height() - options.bottom + $(window).height()
                        });
                    }
                }
            }

        });

        $(window).resize(function() {
            _this.css({
                "bottom": _bottom
            });
        });

        _this.find('.gotop_lnk').click(function() {
            $('html,body').animate({
                scrollTop: 0
            }, 0);
            _this.css({
                "bottom": options.bottom
            });
            return false;
        });
        return $(this);
    };

})(jQuery);

/*
 Color animation jQuery-plugin
 http://www.bitstorm.org/jquery/color-animation/
 Copyright 2011 Edwin Martin <edwin@bitstorm.org>
 Released under the MIT and GPL licenses.
*/
(function(d) {
    function i() {
        var b = d("script:first"),
            a = b.css("color"),
            c = false;
        if (/^rgba/.test(a)) c = true;
        else try {
            c = a != b.css("color", "rgba(0, 0, 0, 0.5)").css("color");
            b.css("color", a)
        } catch (e) {}
        return c
    }

    function g(b, a, c) {
        var e = "rgb" + (d.support.rgba ? "a" : "") + "(" + parseInt(b[0] + c * (a[0] - b[0]), 10) + "," + parseInt(b[1] + c * (a[1] - b[1]), 10) + "," + parseInt(b[2] + c * (a[2] - b[2]), 10);
        if (d.support.rgba) e += "," + (b && a ? parseFloat(b[3] + c * (a[3] - b[3])) : 1);
        e += ")";
        return e
    }

    function f(b) {
        var a, c;
        if (a = /#([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})/.exec(b)) c = [parseInt(a[1], 16), parseInt(a[2], 16), parseInt(a[3], 16), 1];
        else if (a = /#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])/.exec(b)) c = [parseInt(a[1], 16) * 17, parseInt(a[2], 16) * 17, parseInt(a[3], 16) * 17, 1];
        else if (a = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(b)) c = [parseInt(a[1]), parseInt(a[2]), parseInt(a[3]), 1];
        else if (a = /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9\.]*)\s*\)/.exec(b)) c = [parseInt(a[1], 10), parseInt(a[2], 10), parseInt(a[3], 10), parseFloat(a[4])];
        return c
    }
    d.extend(true, d, {
        support: {
            rgba: i()
        }
    });
    var h = ["color", "backgroundColor", "borderBottomColor", "borderLeftColor", "borderRightColor", "borderTopColor", "outlineColor"];
    d.each(h, function(b, a) {
        d.fx.step[a] = function(c) {
            if (!c.init) {
                c.a = f(d(c.elem).css(a));
                c.end = f(c.end);
                c.init = true
            }
            c.elem.style[a] = g(c.a, c.end, c.pos);
        }
    });
    d.fx.step.borderColor = function(b) {
        if (!b.init) b.end = f(b.end);
        var a = h.slice(2, 6);
        d.each(a, function(c, e) {
            b.init || (b[e] = {
                a: f(d(b.elem).css(e))
            });
            b.elem.style[e] = g(b[e].a, b.end, b.pos)
        });
        b.init = true;
    }
})(jQuery);

/*
add by guoran 20140810
*/

if (typeof JSON !== 'object') {
    JSON = {};
}

(function() {
    'use strict';

    function f(n) {
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function() {

            return isFinite(this.valueOf()) ? this.getUTCFullYear() + '-' +
                f(this.getUTCMonth() + 1) + '-' +
                f(this.getUTCDate()) + 'T' +
                f(this.getUTCHours()) + ':' +
                f(this.getUTCMinutes()) + ':' +
                f(this.getUTCSeconds()) + 'Z' : null;
        };

        String.prototype.toJSON =
            Number.prototype.toJSON =
            Boolean.prototype.toJSON = function() {
                return this.valueOf();
            };
    }

    var cx,
        escapable,
        gap,
        indent,
        meta,
        rep;


    function quote(string) {

        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function(a) {
            var c = meta[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }


    function str(key, holder) {
        var i,
            k,
            v,
            length,
            mind = gap,
            partial,
            value = holder[key];
        if (value && typeof value === 'object' &&
            typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }

        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }

        switch (typeof value) {
            case 'string':
                return quote(value);

            case 'number':

                return isFinite(value) ? String(value) : 'null';

            case 'boolean':
            case 'null':

                return String(value);

            case 'object':

                if (!value) {
                    return 'null';
                }

                gap += indent;
                partial = [];

                if (Object.prototype.toString.apply(value) === '[object Array]') {

                    length = value.length;
                    for (i = 0; i < length; i += 1) {
                        partial[i] = str(i, value) || 'null';
                    }

                    v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                    gap = mind;
                    return v;
                }

                if (rep && typeof rep === 'object') {
                    length = rep.length;
                    for (i = 0; i < length; i += 1) {
                        if (typeof rep[i] === 'string') {
                            k = rep[i];
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                } else {

                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                }

                v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                gap = mind;
                return v;
        }
    }

    if (typeof JSON.stringify !== 'function') {
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        meta = {
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"': '\\"',
            '\\': '\\\\'
        };
        JSON.stringify = function(value, replacer, space) {

            var i;
            gap = '';
            indent = '';

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

            } else if (typeof space === 'string') {
                indent = space;
            }

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                (typeof replacer !== 'object' ||
                    typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

            return str('', {
                '': value
            });
        };
    }

    if (typeof JSON.parse !== 'function') {
        cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        JSON.parse = function(text, reviver) {

            var j;

            function walk(holder, key) {

                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function(a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }

            if (/^[\],:{}\s]*$/
                .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                    .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                    .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

                j = eval('(' + text + ')');
                return typeof reviver === 'function' ? walk({
                    '': j
                }, '') : j;
            }

            throw new SyntaxError('JSON.parse');
        };
    }
}());

/*扩展string，添加format方法
add by guoran 20140811
两种调用方法：
eg1:
    var template1="我是{0}，今年{1}了";
    var result1=template1.format("loogn",22);
eg2:
    var template2="我是{name}，今年{age}了";
    var result2=template2.format({name:"loogn",age:22});
    两个结果都是"我是loogn，今年22了"
*/
(function() {
    String.prototype.format = function(args) {
        var result = this;
        if (arguments.length > 0) {
            if (arguments.length == 1 && typeof(args) == "object") {
                for (var key in args) {
                    if (args[key] != undefined) {
                        var reg = new RegExp("({" + key + "})", "g");
                        result = result.replace(reg, args[key]);
                    }
                }
            } else {
                for (var i = 0; i < arguments.length; i++) {
                    if (arguments[i] != undefined) {
                        var reg = new RegExp("({)" + i + "(})", "g");
                        result = result.replace(reg, arguments[i]);
                    }
                }
            }
        }
        return result;
    };

    String.prototype.len = function() {
        return this.replace('[^\x00-\xff]/g', 'aa').length;
    };
})();

function FixedElement(option) {
        this.elem = option.element;
        //this.elemWidth=option.elemWidth;
        this.startPos = option.startPos;
        this.endPos = option.endPos;
        this.l = option.l;
    }
    //相对于窗口中点定位
FixedElement.prototype.setX = function() {
    var winWidth = $(window).width();
    var pos = winWidth / 2;
    var left = pos + this.l;
    $(this.elem).css('left', left + 'px');
};
FixedElement.prototype.doScroll = function() {
    var _this = this;
    var elemHeight = $(this.elem).outerHeight(true);
    $(window).scroll(function() {
        if ($(window).scrollTop() > 0) {
            $(_this.elem).fadeIn(200);

        } else {
            $(_this.elem).fadeOut(200);
        }

        if ($.browser.msie && ($.browser.version == "6.0") && !$.support.style) {
            $(_this.elem).css('position', 'absolute');
            if ($(window).scrollTop() >= _this.endPos - $(window).height()) {
                $(_this.elem).css('top', _this.endPos - elemHeight + 'px')
            } else {
                var top = $(window).scrollTop() + $(window).height() - elemHeight;
                $(_this.elem).css('top', top + 'px');

            }
        } else {

            if ($(window).scrollTop() >= _this.endPos - $(window).height()) {
                $(_this.elem).css('position', 'absolute').css('top', _this.endPos - elemHeight + 'px').css('bottom', 'auto');
            } else {
                $(_this.elem).css('position', 'fixed').css('top', 'auto').css('bottom', '0');
            }
        }
    });
}
FixedElement.prototype.init = function() {
    var _this = this;
    _this.setX();
    $(window).resize(function() {
        _this.setX.apply(_this);
    });
    _this.doScroll();
}

$(function() {

    $('img').focus(function() {
        $(this).blur();
    });

    $('a').focus(function() {
        $(this).blur();
    });

});

/* jquery zoom */
(function($) {
    $.fn.jqueryzoom = function(options) {
        var settings = {
            xzoom: 200,
            yzoom: 200,
            offset: 10,
            position: "right",
            lens: 1,
            preload: 1
        };
        if (options) {
            $.extend(settings, options);
        }
        var noalt = '';
        $(this).hover(function() {
            var imageLeft = $(this).offset().left;
            var imageTop = $(this).offset().top;
            var imageWidth = $(this).children('img').get(0).offsetWidth;
            var imageHeight = $(this).children('img').get(0).offsetHeight;
            noalt = $(this).children("img").attr("alt");
            var bigimage = $(this).children("img").attr("jqimg");
            $(this).children("img").attr("alt", '');
            if ($("div.zoomdiv").get().length == 0) {

                /*添加隐藏层 覆盖select IE6 bug*/
                //$(".num").css("display","none");
                //$("#numselect").css("display","none");
                /*添加隐藏层 覆盖select IE6 bug*/

                $(this).after("<div class='zoomdiv'><img class='bigimg' src='" + bigimage + "'/></div>");
                $(this).append("<div class='jqZoomPup'>&nbsp;</div>");
            }
            if (settings.position == "right") {
                if (imageLeft + imageWidth + settings.offset + settings.xzoom > screen.width) {
                    leftpos = imageLeft - settings.offset - settings.xzoom;
                } else {
                    leftpos = imageLeft + imageWidth + settings.offset;
                }
            } else {
                leftpos = imageLeft - settings.xzoom - settings.offset;
                if (leftpos < 0) {
                    leftpos = imageLeft + imageWidth + settings.offset;
                }
            }
            //$("div.zoomdiv").css({top:imageTop,left:leftpos});
            $("div.zoomdiv").width(settings.xzoom);
            $("div.zoomdiv").height(settings.yzoom);
            $("div.zoomdiv").show();
            if (!settings.lens) {
                $(this).css('cursor', 'crosshair');
            }
            $(document.body).mousemove(function(e) {
                mouse = new MouseEvent(e);
                var bigwidth = $(".bigimg").get(0).offsetWidth;
                var bigheight = $(".bigimg").get(0).offsetHeight;
                var scaley = 'x';
                var scalex = 'y';
                if (isNaN(scalex) | isNaN(scaley)) {
                    var scalex = (bigwidth / imageWidth);
                    var scaley = (bigheight / imageHeight);

                    if ((settings.xzoom) / (scalex * 1) > 480) {
                        $("div.jqZoomPup").width(80);
                    } else {
                        $("div.jqZoomPup").width((settings.xzoom) / (scalex * 1));
                    }

                    if ((settings.yzoom) / (scaley * 1) > 480) {
                        $("div.jqZoomPup").height(80);
                    } else {
                        $("div.jqZoomPup").height((settings.yzoom) / (scaley * 1));
                    }

                    if (settings.lens) {
                        $("div.jqZoomPup").css('visibility', 'visible');
                    }
                }
                xpos = mouse.x - $("div.jqZoomPup").width() / 2 - imageLeft;
                ypos = mouse.y - $("div.jqZoomPup").height() / 2 - imageTop;
                if (settings.lens) {
                    xpos = (mouse.x - $("div.jqZoomPup").width() / 2 < imageLeft) ? 0 : (mouse.x + $("div.jqZoomPup").width() / 2 > imageWidth + imageLeft) ? (imageWidth - $("div.jqZoomPup").width() - 2) : xpos;
                    ypos = (mouse.y - $("div.jqZoomPup").height() / 2 < imageTop) ? 0 : (mouse.y + $("div.jqZoomPup").height() / 2 > imageHeight + imageTop) ? (imageHeight - $("div.jqZoomPup").height() - 2) : ypos;
                }
                if (settings.lens) {
                    $("div.jqZoomPup").css({
                        top: ypos,
                        left: xpos
                    });
                    if ($(".jqZoomPup").height() > 480) {
                        $(".jqZoomPup").hide();
                        $(".zoomdiv").hide();
                    }
                }
                scrolly = ypos;
                $("div.zoomdiv").get(0).scrollTop = scrolly * scaley;
                scrollx = xpos;
                $("div.zoomdiv").get(0).scrollLeft = (scrollx) * scalex;
            });
        }, function() {
            $(this).children("img").attr("alt", noalt);
            $(document.body).unbind("mousemove");
            if (settings.lens) {

                /*添加隐藏层 覆盖select IE6 bug*/
                //$(".num").css("display","");
                /*添加隐藏层 覆盖select IE6 bug*/

                $("div.jqZoomPup").remove();
            }
            $("div.zoomdiv").remove();
        });
        count = 0;
        if (settings.preload) {
            $('body').append("<div style='display:none;' class='jqPreload" + count + "'>UGO</div>");
            $(this).each(function() {
                var imagetopreload = $(this).children("img").attr("jqimg");
                var content = jQuery('div.jqPreload' + count + '').html();
                jQuery('div.jqPreload' + count + '').html(content + '<img src=\"' + imagetopreload + '\">');
            });
        }
    }
})(jQuery);

function MouseEvent(e) {
    this.x = e.pageX;
    this.y = e.pageY;
}

/*购物车列表新样式
args:{[],callback}
*/
;
(function($) {
    $.fn.shopcartList = function(args) {
        var $this = $(this);
        var m_events = {
            mouseenter: 'mouseenter',
            mouseover: 'mouseover'
        };

        function addStyle(o) {
            o.addClass('curr_sizeList');
            $('.innerSize', o).addClass('innerSizeList');
            $('.sizeListUpdate', o).show();
            $('.btnUpdate', o).addClass('curr_btnUpdate');
            $('.btnDelete', o).addClass('curr_btnDelete');
        }

        function removeStyle(o) {
            o.removeClass('curr_sizeList');
            $('.innerSize', o).removeClass('innerSizeList');
            $('.sizeListUpdate', o).hide();
            $('.btnUpdate', o).removeClass('curr_btnUpdate');
            $('.btnDelete', o).removeClass('curr_btnDelete');
        }
        $this.live('hover', function(event) {
            var _this = $(this);
            if (event.type in m_events) {
                addStyle(_this);
            } else {
                removeStyle(_this);
            };
        });
    };
})(jQuery);

/*
create by guoran 20140911
    名称：提示插件
参数说明：
          选择器（需要附加的控件ID或Class）;
          text_html:需要显示的文本或html
eg:
1. $('.newSelectList input:text').toolstrip('超过了20件');
2. $('#txtNumber').toolstrip('超过了20件');
*/
;
(function($) {
    $.fn.toolstrip = function(text_html) {
        var $this = $(this);
        $this.each(function() {
            var _tooldiv = $('<div/>', {
                'class': 'ygtoolstrip',
                'id': 'bby' + new Date().getTime() + '' + Math.floor(Math.random() * 9999 + 1000)
            });
            _parent = $(this).parent();
            _parent.css('position', 'relative');
            var _ygts = $('.ygtoolstrip', _parent);
            if (_ygts.length > 0) {
                _ygts.show().html(text_html);
            } else {
                _parent.append(_tooldiv);
                _tooldiv.css('left', -46).css('top', -_tooldiv.outerHeight()).html(text_html);
            }
        });

        $(document).delegate('.ygtoolstrip', 'mouseout mouseleave', function() {
            $(this).hide();
        });
    };
})(jQuery);

//placeholder
(function($) {
    if ($.fn.placeholder) {
        return;
    }
    $.fn.placeholder = function() {
            var fnFocus = function() {
                $(this).addClass('ph-wrap-focus').find('input').focus();
            };
            var fnPaste = function(e) {
                $(this).parent().addClass('ph-wrap-has');
            };
            var fnKey = function() {
                this.value != '' ? $(this).parent().addClass('ph-wrap-has') : '';
            };
            var fnBlur = function() {
                if ($.trim(this.value) == '') {
                    $(this).val('').parent().removeClass('ph-wrap-has ph-wrap-focus');
                }
            }
            return this.each(function() {
                var $this = $(this),
                    dSpan = $('<span/>', {
                        'class': 'placeholder',
                        text: $this.attr('placeholder')
                    });
                dWrap = $('<div/>', {
                    'class': 'ph-wrap',
                    click: fnFocus
                });
                $this.wrap(dWrap).before(dSpan).bind({
                    keyup: fnKey,
                    blur: fnBlur,
                    paste: fnPaste
                });
                if ($.trim(this.value) != '') {
                    $this.parent().addClass('ph-wrap-has');
                }
            })
        }
        // 检测 placeholder 支持
    $(function() {
        var supportPlaceholder = 'placeholder' in document.createElement('input');
        if (!supportPlaceholder) {
            $('input[placeholder]').placeholder();
        }
    });
    window.onload = function() {
        $('.ph-wrap input').each(function() {
            if ($.trim(this.value) != '') {
                $(this).parent().addClass('ph-wrap-has');
            }
        });
    }
})(jQuery);

/*
create by guoran 20141030
    名称：备注插件
参数说明：
          选择器（需要附加的控件ID或Class）;
eg:
1. $('.plugin_remark').pluginremark(function(){});

html:<span class="plugin_remark" data-id="AK47B00037" data-value='这个东西需要开发票的'></span>
*/
;
(function($) {
    $.fn.pluginremark = function(callback) {
        function setHtml(_label, _text) {
            _label.html(_text ? _text : '--').attr('title', _text).css('display', 'inline-block');
            if (_text.len() > 10) {
                _label.addClass('ie6width');
            } else {
                _label.removeClass('ie6width');
            }
        }

        $(this).each(function() {
            var $this = $(this),
                _dvalue = $this.attr('data-value');
            var _hd = $('<div/>', {
                    'class': 'plugin_remark_hd',
                    'id': 'bby_remark' + new Date().getTime() + '' + Math.floor(Math.random() * 9999 + 1000)
                }),
                _label = $('<label/>', {
                    'class': 'plugin_remark_label',
                    'title': _dvalue,
                    'text': _dvalue
                }),
                _ibtn = $('<i/>', {
                    'class': 'plugin_remark_btn icon icon_remark'
                }),
                _bd = $('<div/>', {
                    'class': 'plugin_remark_bd'
                }),
                _text = $('<input/>', {
                    'class': 'plugin_remark_text',
                    'type': 'text',
                    'maxlength': '30',
                    'data-id': $this.attr('data-id'),
                    'value': _dvalue
                });
            setHtml(_label, _dvalue);
            _hd.append(_label).append(_ibtn);
            _bd.append(_text);
            $this.append(_hd).append(_bd);
        });

        $('.plugin_remark_btn', this.selector).bind('click', function() {
            $(this).parents('.plugin_remark_hd').hide().siblings('.plugin_remark_bd').show().find('.plugin_remark_text').focus();
        });

        $('.plugin_remark_text', this.selector).blur(function() {
            var $this = $(this),
                _label = $this.parent().hide().siblings('.plugin_remark_hd').show().find('.plugin_remark_label'),
                _text = $this.val();
            setHtml(_label, _text);
            if (callback && typeof(callback) == 'function') {
                callback($this.attr('data-id'), $this.val());
            }
        });

    };
})(jQuery);

/*简单Nav切换效果*/
;
(function($) {
    $.fn.NavBarBasic = function(callback) {
        var $this = $(this);
        $this.each(function() {
            var $that = $(this);
            $('.navbar-title a', $that).click(function(e) {
                e.preventDefault();
                var _a = $(this);
                _a.addClass('active').siblings().removeClass('active');
                $(_a.attr('href')).addClass('active').siblings().removeClass('active');
                if (callback) {
                    if (typeof callback == 'function') {
                        callback(_a);
                    } else {
                        console.error('亲，你可以不传，但如果你传的话，则必须是个方法呀！');
                    }
                }

            });
        });
        return $this;
    };

    function navItemEvent(navitem, callback) {
        navitem.on('click', '[data-toggle=navitem]', function(e) {
            e.preventDefault();
            var _a = $(this),
                _li = _a.parents('li');
            _li.addClass('active').siblings().removeClass('active');
            $(_a.data('href')).addClass('active').siblings().removeClass('active');

            if (callback) {
                if (typeof callback == 'function') {
                    callback(_a, _a.data('href'));
                    _a.data('access', 'true');
                } else {
                    console.error('亲，你可以不传，但如果你传的话，则必须是个方法呀！');
                }
            }
        });
    }

    /*默认Nav页切换效果*/
    $.fn.NavPage = function(callback) {
        navItemEvent($(this));
        return $(this);
    };

    /**
     * Nav页切换效果 带滚动
     * @callback {function} 插件调用后的回调方法
     */
    $.fn.NavPageScroll = function(callback) {
        var $this = $(this),
            navs = $('.navs', $this),
            length = $('.navs li', $this).length,
            width = $('.navs li:first').width();
        navs.width(width * length);

        /**     
         * 滚动nav
         * @obj {Object} 点击的按钮
         * @type {int} 1为向右， 0为向左
         * @speed {int} 每次滚动的数值
         * @difference 差异调整
         */
        function scrollNavs(obj, type, speed, difference) {
            ul = obj.parents('.navs'),
                ml = parseInt(ul.css('marginLeft')),
                mlcomput = (type ? ml - speed : ml + speed),
                nav = ul.parent(),
                navWidth = nav.width(),
                ulWidth = ul.width(); //obj.siblings('li').length * speed,
            range = ulWidth - navWidth - difference;
            // console.log('ul:' + ulWidth + ' nav:' + navWidth + ' range' + range);
            if (type) {
                //mlcomput = mlcomput < -range ? -range : mlcomput;
                if (mlcomput < -range) {
                    mlcomput = -range;
                    $('.nav-click-right', $this).hide();//.siblings('.nav-click-left').show();
                }else{
                    $('.nav-click-left', $this).show();
                }
            } else {
                //mlcomput = mlcomput > 0 ? 0 : mlcomput;
                if (mlcomput > 0) {
                    mlcomput = 0;
                    //update by guoran20150819 使左右按钮非第一个和最后一个时可用
                    $('.nav-click-left', $this).hide();//.siblings('.nav-click-right').show(); 
                }else{
                    $('.nav-click-right', $this).show();
                }
            }
            mlpx = mlcomput + 'px';
            ul.animate({
                marginLeft: mlpx
            });
        }

        $('<span class="nav-click-left icon iconfont" title="前一项">&#xe615;</span><span class="nav-click-right icon iconfont" title="后一项">&#xe614;</span>').appendTo(navs);

        $('.nav-click-right', $this).on('click', function() {
            scrollNavs($(this), 1, 170, 2);
        });

        $('.nav-click-left', $this).on('click', function() {
            scrollNavs($(this), 0, 170, 2);
        });

        navItemEvent($this, callback);
        return $(this);
    };

})(jQuery);

//下拉框
(function($) {

    function YGUI(type) {
        this.version = '1.0';
        this.name = "YGUI";
        this.type = type;
    }

    window.YGUI = YGUI;

    var dropdown = function(target) {
        this.target = target;
        this.status = 0;
        this.data = [];
        YGUI.call(this, 'dropdown');
    };

    //刷新数据
    dropdown.prototype.refreshData = function() {
        with(this) {
            data = [];
            for (var i = 0; i < target.length; i++) {
                data.push({
                    text: target[i].text,
                    value: target[i].value
                });
            };
        }
    }

    dropdown.prototype.init = function() {
        var that = this;
        with(that) {
            refreshData();
            render();
            triggerEvent();
        }
    };

    dropdown.prototype.render = function() {
        var ddbox = $('<span class="yg-dropdown"></span>'),
            list = [],
            $target = $(this.target),
            tclass = $target.attr('class'),
            selectedIndex = $(':selected', $target).index(),
            selectedObj = {};
        ddbox.addClass(tclass);
        $target.removeAttr('class');
        $.each(this.data, function(i, item) {
            list.push('<li data-value="{0}" class="{2}">{1}</li>'.format(item.value, item.text, (i === selectedIndex? 'active':'')));
            if(i === selectedIndex){
                selectedObj = {text:item.text, value:item.value, index:i};
            }
        });
        ddbox.append('<div class="yg-dropdown-s"></div><div class="yg-dropdown-title"><input type="text" class="yg-dropdown-val" readonly="readonly"/><i class="iconfont Gray yg-dropdown-ico">&#xe60d;</i></div><ul class="yg-dropdown-list hide">' + list.join('') + '</ul>')
        $target.wrap(ddbox);
        $target.parents('.yg-dropdown').find('.yg-dropdown-val').val(selectedObj.text ? selectedObj.text : '').data('value', selectedObj.value).data('index', selectedObj.index);
    };

    dropdown.prototype.refresh = function() {
        var list = [],
            $target = $(this.target),
            dropbox = $target.parents('.yg-dropdown'),
            ddlist = $('.yg-dropdown-list', dropbox),
            selectedIndex = $(':selected', $target).index(),
            selectedObj = {};
        this.refreshData();
        $.each(this.data, function(i, item) {
            list.push('<li data-value="{0}" class="{2}">{1}</li>'.format(item.value, item.text, (i === selectedIndex? 'active':'')));
            if(i === selectedIndex){
                selectedObj = {text:item.text, value:item.value, index:i};
            }
        });
        ddlist.html(list.join(''));
        $target.parents('.yg-dropdown').find('.yg-dropdown-val').val(selectedObj.text ? selectedObj.text : '').data('value', selectedObj.value).data('index', selectedObj.index);
        this.listitemEvent(ddlist, dropbox);
    };

    dropdown.prototype.listitemEvent = function(ul, dropbox) {
        var $target = $(this.target),
            li = $('li', ul),
            input_val = $('.yg-dropdown-val', dropbox),
            intext = li.eq(0).text();
        li.hover(function() {
            $(this).addClass('hover');
        }, function() {
            $(this).removeClass('hover');
        }).click(function(event) {
            var $this = $(this);
            ul.addClass('hide');
            $(this).addClass('active').siblings().removeClass('active');
            if (input_val.data('value') != $this.data('value')) {
                input_val.val($this.text()).data('value', $this.data('value')).data('index', $this.index());
                $('option', $target).eq($this.index()).prop('selected', true).click();
                $target.change();
            }
        });
        if(this.status && (input_val.data('value') != li.data('value') || input_val.val() != intext || $target.attr('linkage'))){
            $target.change();
        }else{
            this.status = 1;
        }
    }

    dropdown.prototype.triggerEvent = function() {
        var $target = $(this.target),
            dropbox = $target.parents('.yg-dropdown'),
            ul = $('.yg-dropdown-list', dropbox),
            yg_dropdown_title = $('.yg-dropdown-title', dropbox),
            display = ul.css("display");
        yg_dropdown_title.click(function(event) {
            var ulheight = '-'+parseInt(ul.height()-3)+'px',
                $this = $(this),bottomheigh,
                winHeight = $(window).height(),//页面可视区域的高度
                eleoffset = $this.offset().top,//是节点距离网页顶部的距离，注意不是距离浏览器顶部
                documscroll = $(document).scrollTop(),//是滚动条高度
                eleheight = $this.height();//是滚动条高度
                bottomheigh = winHeight - (eleoffset - documscroll);
            if(bottomheigh <= 150){
                ul.addClass('top-dropdown');
                ul.css({position:"absolute",top:ulheight});
            }else{
                ul.removeClass('top-dropdown');
                ul.css({position:"absolute",top:'26px'});
            }
            $('.yg-dropdown-list').not(ul).addClass('hide');
            ul.toggleClass('hide');
        });
        this.listitemEvent(ul, dropbox);
        // 滚动条的样式插件
        ul.niceScroll({
        	 //#5893ed
            cursorcolor: "#ccc", //update by guoran 20151016
            cursoropacitymax: 1,
            touchbehavior: false,
            cursorwidth: "5px",
            cursorborder: "0",
            cursorborderradius: "5px",
            autohidemode: false
        });
    }

    dropdown.prototype.linkAge = function(){
        this.linkage = true;
        return this;       
    }

    $.fn.dropdown = function(args) {
        return $(this).each(function(i, item) {
            var dd = new dropdown(item);
            dd.init();
            $(item).data('ui', dd);
        });
    }

    //排序样式设置
    var SORTOPT = {
            desc: true,
            asc: false
        },
        sortfilter = function(target) {
            this.target = target;
            this.data = SORTOPT.asc;
            YGUI.call(this, 'sort');
        };
    sortfilter.prototype.init = function() {
        var that = this;
        with(that) {
            render();
            triggerEvent();
        }
    };
    sortfilter.prototype.render = function() {
        var $target = $(this.target),
            tclass = $target.attr('class'),
            isflag = $target.attr('Nenabled'),
            sortbox = $('<span class="yg-sort"><span class="yg-sort-s"></span><span class="icon iconfont {0}"><i class="sort-up">&#xe603;</i><i class="sort-down Blue">&#xe604;</i></span>&nbsp;</span>'.format(isflag ? 'disabled': ''));
        sortbox.addClass(tclass);
        $target.wrap(sortbox);
    };
    sortfilter.prototype.triggerEvent = function() {
        var $target = $(this.target),
            sortbox = $target.parents('.yg-sort'),
            sort_icon = $('.icon', sortbox);
        sortbox.mousedown(function() {
            if($target.attr('Nenabled')){
                $target.removeAttr('Nenabled');
                sort_icon.removeClass('disabled');
                $target[0].checked = true;
            }else{
                sort_icon.toggleClass('checked');
            }
        })
    };
    $.fn.Sort = function(args) {
        return $(this).each(function(i, item) {
            var sf = new sortfilter(item);
            sf.init();
            $(item).data('ui', sf);
        });
    }

    $(function() {
        $('[data-ui-type=dropdown]').dropdown().size();
        $('[data-ui-type=sort]').Sort();
        $(window).on('mousedown.bby.dropdown', function(e) {
            var t = $(e.target);
            if (!t.hasClass('.yg-dropdown')) {
                if (!t.parents('.yg-dropdown')[0]) {
                    $('.yg-dropdown-list').addClass('hide');
                }
            }
        });
    });

})(jQuery);


(function($) {
//收藏效果设置
YGUI.collect=function (){
    var attr, length, display,
        operate = $('.operate'),
        collect = $('.collect'),
        ul = $('.collect-list'),
        li = $('.collect-item'),
        collect_btn = $('.collect-btn'),
        collect_click = $('.collect-click'),
        checkbox = li.find('input[type="checkbox"]')
        ;
    ul.on('mouseover mouseout', '.collect-item', function(event) { //设置下拉框的hover事件，添加背景高亮显示
        if (event.type == "mouseover") {
            $(this).addClass('hover').siblings().removeClass('hover');
        } else {
            $(this).removeClass('hover');
        }
    });

    collect.click(function(event) {
    	if(!$(event.target).hasClass('collect') && !$(event.target).hasClass('iconfont') && !$(event.target).hasClass('star') && !$(event.target).hasClass('collect-btn')){
    		return;
    	}
    	var $this = $(this);
    	var arr = [],collect_list,
    	collect_index = collect.index(this);
    	
    	var ommodityCode_favorite=$this.find('#ommodityCode_favorite').val();
    
    
    	 $.ajax({
  			cache : false,
  			type : 'POST',
  			dataType : "json",
  			data:"commodity_no="+ommodityCode_favorite,
  			url :basePath+"/favoriteClassifyController/queryFavoriteInfo.sc?t="+ +new Date(),  
  			success : function(data) {
  					//用于判断商品是否被收藏
  					
	  				jQuery.each(data.listMap, function(i,item){
			            var   name=item.classify_name,
			                value=item.id+";"+ommodityCode_favorite,
			                favoriteInfo=item.favoriteInfo,li_new;
			           
			            if(favoriteInfo=='y'){
			            	
			            	li_new = '<li class="collect-item"><label for="d-' + collect_index + '-' + i + '"><input     type="checkbox"class="checbox"   checked = "checked"   name="destination" id="d-' + collect_index + '-' + i + '" value="'+value+'">' + name + '</label></li>';
			            }else{
			            	li_new = '<li class="collect-item"><label for="d-' + collect_index + '-' + i + '"><input     type="checkbox"class="checbox"    name="destination" id="d-' + collect_index + '-' + i + '" value="'+value+'">' + name + '</label></li>';
			            }
			            arr.push(li_new);
			        });
			        display =$this.find('.collect-list').css('display');
			    	 collect_list =$this.find('.collect-list');
			    	 
			    	 //if ($this.find('.collect-click').hasClass('first')) { //判断是否是第一次点击
			    		 collect_list.html(arr.join("")); 
			    	 //} 
			    	 collect_list.show();
			    	 $this.find('.collect-click').find('i.up').html('&#xe60d;');
			    	 $this.find('.collect-click').removeClass('first');
  				}
  			 });
    	
    });
    collect.hover(function() {
        $(this).parent().addClass('hover'); //添加class hover解决ie7下index的问题
    }, function() {
        $(this).find('.collect-list').hide();
        $(this).find('i.up').html('&#xe610;');
        $(this).parent().removeClass('hover');
    });
    ul.on("click", 'input[type="checkbox"]', function(e) { //给input添加click事件
        attr = $(this).prop('checked'),
            length = $(this).parent().parent().parent().find('input[type="checkbox"]:checked').length;
	        var ficationClassifyId='',mmodityCodeId='';
	       
	        $(this).parent().parent().parent().find('input[type="checkbox"]:checked').each(function(){
				if(ficationClassifyId==''){
					
					var  favoriteId=$(this).val();
					var  favoriteList =favoriteId.split(";");
					ficationClassifyId=favoriteList[0];
					mmodityCodeId=favoriteList[1];
					
				}else{
					var  favoriteId=$(this).val();
					var  favoriteList =favoriteId.split(";");
					ficationClassifyId=ficationClassifyId+","+favoriteList[0];
				}
			});
	        $.ajax({
	  			cache : false,
	  			type : 'POST',
	  			dataType : "json",
	  			data:"favoriteIds="+ficationClassifyId+"&commodity_id="+mmodityCodeId,
	  			url :basePath+"/favoriteClassifyController/saveFavoriteCommodity.sc",  
	  			success : function(data) {
	  			}
	  		});
	       // saveFavoriteInfo(ficationClassifyId,mmodityCodeId);
        if (length > 0) {
        	$(this).parent().parent().parent().parent().siblings('.star').find('i').removeClass('Gray');
            $(this).parent().parent().parent().parent().siblings('.star').find('i').addClass('Blue');
        } else {
            $(this).parent().parent().parent().parent().siblings('.star').find('i').removeClass('Blue');
            $(this).parent().parent().parent().parent().siblings('.star').find('i').addClass('Gray');
        }
        if (attr) {
            $(this).parent().parent().addClass('checked').siblings().removeClass('checked');
        } else {
            $(this).parent().parent().removeClass('checked');
        }
    });
    
    // 滚动条的样式插件
    ul.niceScroll({
        cursorcolor: "#5893ed",
        cursoropacitymax: 1,
        touchbehavior: false,
        cursorwidth: "5px",
        cursorborder: "0",
        cursorborderradius: "5px"
    });
}
    YGUI.collect();
})(jQuery);

/*
*左边菜单栏收缩
*/
(function($){
    var sidebar_title = $('.sidebar-title'),
        i = sidebar_title.find('.iconfont'),
        sub_menu = $('.sub-menu');
    sidebar_title.click(function(event) {
        if(sub_menu.hasClass('hide')){
            sub_menu.removeClass('hide');
            i.html("&#xe604;");
        }else{
            sub_menu.addClass('hide');
            i.html("&#xe602;");  
        }
    });
})(jQuery)
