$(function(){
	fn_inputEmail();
	fn_inputPw();
	fn_login();
	fn_displayRememberEmail();
	$('.msg_login_box').click(function(){
		location.href='/user/join/agree';
	})	
	
	function loginWithKakao() {
	    Kakao.Auth.authorize({
	      redirectUri: 'http://localhost:9090',
	    });
	}
    Kakao.Auth.setAccessToken('${ACCESS_TOKEN}');
	displayToken();
    function displayToken() {
    	var token = getCookie('authorize-access-token');
    	
    	if(token) {
			Kakao.Auth.setAccessToken(token);
			Kakao.Auth.getStatusInfo()
			    .then(function(res) {
			    if (res.status === 'connected') {
			        document.getElementById('token-result').innerText
			             = 'login success, token: ' + Kakao.Auth.getAccessToken();
			        }
			    })
			    .catch(function(err) {
			        Kakao.Auth.setAccessToken(null);
			    });
		}
	}
	
	function getCookie(name) {
	    var parts = document.cookie.split(name + '=');
	    if (parts.length === 2) { return parts[1].split(';')[0]; }
	}
});

function fn_inputEmail(){
	/* 
		① 아이디 공백 검사
		아이디 input 박스 밑에 색깔 그림자를 넣어, 오류난 위치 사용자에게 알려주기
		오류 예시
			1) @ 와 . 이 없을 경우
				- 메시지창 : "잘못된 이메일 형식"
				- 코드 진행 금지
			2) @ 와 . 이 있으면 데이터 검사 진행
				- 메시지창 : "이 이메일 주소를 사용하는 계정을 찾을 수 없음"
				- 새로운 계정 등록 (회원가입) 유도
	*/
	// 이메일 정규식 검사
	$('#email').keyup(function(){
		let emailValue = $(this).val();
		// 정규식 (4~60자, 숫자+소문자+대문자+특수문자(-,_) 조합)
		// 최소 입력 aaaa@a.aa
		let regEmail = /^[0-9a-zA-Z][0-9a-zA-Z-_]{3,29}@[a-z]{1,10}[\.][a-z\.]{2,18}$/;
		if(regEmail.test(emailValue) == false) {
			$('.msg_email').removeClass('blind');
			$('#email').addClass('error_box');
		} else {
			$('.msg_email').addClass('blind');
			$('#email').removeClass('error_box');
			
		}
	});
	
	// 확인 메시지
	$('#email').blur(function(){
		if($('#email').val() == '') {
			$('.msg_email').removeClass('blind');
		}
	});
}

function fn_inputPw(){
	$('#pw').keyup(function(){
		let emailValue = $(this).val();
		// 정규식 (4~60자, 숫자+소문자+대문자+특수문자(-_!@#$%^&*) 조합)
		let regEmail = /^[0-9a-zA-Z-_!@#$%^&*]{4,60}$/;
		if(regEmail.test(emailValue) == false) {
			$('.msg_pw').removeClass('blind');
			$('#pw').addClass('error_box');
		} else {
			$('.msg_pw').addClass('blind');
			$('#pw').removeClass('error_box');
		}
	});
	
	// 확인 메시지
	$('#pw').blur(function(){
		if($('#pw').val() == '') {
			$('.msg_pw').removeClass('blind');
		}
	});
}

function fn_login(){
	$('#frm_login').submit(function(event){
		
		// 아이디, 비밀번호 공백 검사
		if($('#email').val() == '' || $('#pw').val() == ''){
			alert('이메일과 비밀번호를 모두 입력하세요.');
			event.preventDefault();
			return;
		}
		
		// 아이디 기억을 체크하면 rememberId 쿠키에 입력된 아이디를 저장
		if($('#rememberEmail').is(':checked')){
			$.cookie('rememberEmail', $('#email').val());
		} else {
			$.cookie('rememberEmail', '');
		}
	});
		
}
function fn_displayRememberEmail(){
	// rememberEmail 쿠키에 저장된 아이디를 가져와서 표시
	let rememberEmail = $.cookie('rememberEmail');
	if(rememberEmail == ''){
		$('#email').val('');
		$('#rememberEmail').prop('checked', false);
	} else {
		$('#email').val(rememberEmail);
		$('#rememberEmail').prop('checked', true);
	}
	
}
