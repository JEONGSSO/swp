   
    const Templates ={},   //const쓰는 이유는 틀만 변하지 않으면 되기때문에.
             $htmls = $('script[type="text/x-handlebars-template"]');

    console.log("::htmls>>", $htmls)
    $htmls.each( (idx, h) => {   //h는 script 열고 닫기까지
//    	console.log(idx, h)
        let tmpid = $(h).attr('id');
        Templates[tmpid] = Handlebars.compile($(h).html());
    });

    let renderHbs = (tmpid, jsonData, tag) => {
//    	console.debug("QQQ>>", tmpid, jsonData, Templates);
        tag = tag || 'div'; //태그 있으면 그대로 쓰고 태그 없으면 div로
        let $tmpl = $('#' + tmpid); //tmp 아이디를 담음
        let html = Templates[tmpid](jsonData);
        let cssClass = $tmpl.attr('class') || "";
        $tmpl.replaceWith(`<${tag} id = "${tmpid}" class="${cssClass}">` + html + `</${tag}`)  
    };
    
    Handlebars.registerHelper('eq', (a, b) => {	
    	return a == b;
    })
    
    moment.locale('ko');
    Handlebars.registerHelper('fromNow', (dt, option) => {	
    	return moment(dt).fromNow();
    })
    
    Handlebars.registerHelper('fullTime', (dt, option) => {	
    	return moment(dt).format('llll');
    })