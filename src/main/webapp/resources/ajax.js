	function sendAjax(url, fn, method, jsonData) 
  {
	    let options = {
	        method: method || 'GET', //메소드 get post put 등
	        url: url,
	        contentType: "application/json" //타입은 제이슨으로 받겠다.
	    };
	    //jsonData가 있을 때만 data : JSON.stringify(jsonData) 추가
	    if (jsonData) {
	        options.data = JSON.stringify(jsonData);
	        console.log(options);
	    }

	    $.ajax(options).always((responseText, statusText, ajaxResult) => {
	        let isSuccess = statusText === 'success'; //ajax 호출 성공 여부
	        fn(isSuccess, responseText);
	        if (!isSuccess) {
	            alert("오류가 발생하였습니다. (errorMessage:" + responseText + ")");
	        }
	    })
	}