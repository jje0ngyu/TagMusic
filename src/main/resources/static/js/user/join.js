	
	$(function(){
		fn_emailRegEx();
		fn_emailCheck();
		fn_pwCheck();
		fn_pwCheckAgain();
		fn_nameCheck();
		fn_fileCheck();
		fn_mobileCheck();
		fn_extraInfo();
		fn_birthyear();
		fn_birthmonth();
		fn_birthdate();
		fn_join();
		fn_cancel();
	});
	
	// 전역변수 (각종 검사를 통과하였는지 점검하는 플래그 변수)
	let regEmail = /^[0-9a-zA-Z][0-9a-zA-Z-_]{3,29}@[a-z]{1,10}[\.][a-z\.]{2,18}$/;
	let emailPass = false;
	var pwPass = false;
	var rePwPass = false;
	var namePass = false;
	var mobilePass = false;
	var authCodePass = false;
	
	// 0. 이메일 정규식 검사
	function fn_emailRegEx(){
		$('#email').keyup(function(){
			$('#msg_email').text('');
			let emailValue = $(this).val();
			// 정규식 (4~60자, 숫자+소문자+대문자+특수문자(-,_) 조합)
			// 최소 입력 aaaa@a.aa
			if(regEmail.test(emailValue) == false) {
				$('#msg_email_ab').removeClass('blind');
			} else {
				$('#msg_email_ab').addClass('blind');
			}
		});
	}
	
	// 1. 이메일
	//    1) 입력된 이메일이 회원 정보에 있는지 체크하는 ajax
	//    2) 입력된 이메일로 인증번호를 보내는 ajax
	function fn_emailCheck(){
		
		$('#btn_getAuthCode').click(function(){
			
			// 인증코드를 입력할 수 있는 상태로 변경함
			$('#authCode').prop('readonly', false);
			
			// resolve : 성공하면 수행할 function
			// reject  : 실패하면 수행할 function
			new Promise(function(resolve, reject) {
				let emailValue = $('#email').val();
				if(regEmail.test(emailValue) == false){
					reject(1);  // catch의 function으로 넘기는 인수 : 1(이메일 형식이 잘못된 경우)
					authCodePass = false;
					return;
				}
				
				// 이메일 중복 체크
				$.ajax({
					type: 'get',
					url: '/user/checkReduceEmail',
					data: 'email=' + $('#email').val(),
					dataType: 'json',
					success: function(resData){
						if(resData.isUser){
							reject(2);   // catch의 function으로 넘기는 인수 : 2(다른 회원이 사용중인 이메일이라서 등록이 불가능한 경우)
						} else {
							resolve();   // Promise 객체의 then 메소드에 바인딩되는 함수
						}
					}
				});  // ajax
				
			}).then(function(){
				// 인증번호 보내는 ajax
				$.ajax({
					type: 'get',
					url: '/user/sendAuthCode',
					data: 'email=' + $('#email').val(),
					dataType: 'json',
					success: function(resData){
						alert('인증코드를 발송했습니다. 이메일을 확인하세요.');
						$('#btn_verifyAuthCode').click(function(){
							if(resData.authCode == $('#authCode').val()){
								alert('인증되었습니다.');
								authCodePass = true;
								emailPass = true;
							} else {
								alert('인증에 실패했습니다.');
								authCodePass = false;
							}
						});
					},
					error: function(jqXHR){
						alert('인증번호 발송이 실패했습니다.');
						authCodePass = false;
					}
				});  // ajax
				
			}).catch(function(code){  // 인수 1 또는 2를 전달받기 위한 파라미터 code 선언
				switch(code){
				case 1:
					$('#msg_email').text('이메일 형식이 올바르지 않습니다.');
					break;
				case 2:
					$('#msg_email').text('이미 사용중인 이메일입니다.');
					fn_emailRegEx();
					break;
				}
				authCodePass = false;
				// 입력된 이메일에 문제가 있는 경우 인증코드 입력을 막음
				$('#authCode').prop('readonly', true);
				
			});  // new Promise
			
		});  // click
		
	}  // fn_emailCheck
	
	// 2. 비밀번호
	function fn_pwCheck(){
		
		$('#pw').keyup(function(){
			let pwValue = $(this).val();
			// 정규식(4~20자, 소문자+대문자+숫자+특수문자8종(!@#$%^&*) 3개 이상 조합)
			let regPw = /^[0-9a-zA-Z!@#$%^&*]{4,20}$/;
			// 3개 이상 조합 확인
			let validatePw = /[0-9]/.test(pwValue)        // 숫자가 있으면 true, 없으면 false
			               + /[a-z]/.test(pwValue)        // 소문자가 있으면 true, 없으면 false
			               + /[A-Z]/.test(pwValue)        // 대문자가 있으면 true, 없으면 false
			               + /[!@#$%^&*]/.test(pwValue);  // 특수문자8종이 있으면 true, 없으면 false
			// 정규식 및 3개 이상 조합 검사
			if(regPw.test(pwValue) == false || validatePw < 3){
				$('#msg_pw').text('4~20자의 소문자, 대문자, 숫자, 특수문자(!@#$%^&*)를 3개 이상 조합해야 합니다.');
				pwPass = false;
			} else {
				$('#msg_pw').text('사용 가능한 비밀번호입니다.');
				pwPass = true;
			}
			               
		});  // keyup
		
	}  // fn_pwCheck
	
	// 3. 비밀번호 확인
	function fn_pwCheckAgain(){
		
		$('#re_pw').keyup(function(){
			
			$('#msg_re_pw').empty();
			let rePwValue = $(this).val();
			
			// 비밀번호와 비밀번호 재입력 검사
			if(rePwValue == '' || rePwValue != $('#pw').val()){
				$('#msg_re_pw').text('불일치');
				rePwPass = false;
			} else {
				$('#msg_re_pw').text('');
				rePwPass = true;
			}
			
		});  // keyup
		
	}  // fn_pwCheckAgain
	// 4. 이름
	function fn_nameCheck(){
		
		$('#name').keyup(function(){
			
			// 입력한 이름
			let nameValue = $(this).val();
			
			// 공백 검사
			namePass = (nameValue != '');
			
		});  // keyup
		
	}  // fn_nameCheck
	
	// 파일 체크
	function fn_fileCheck(){
		$('#profileImage').change(function(){
			let maxSize = 1024 * 1024 * 50; // 50MB
			if(this.size > maxSize){
				alert('50MB 이하의 파일만 첨부할 수 있습니다.');
				$(this).val(''); // 첨부파일 없애기
				return;
			}
		});
	}
	// 5. 휴대전화
	function fn_mobileCheck(){
		
		$('#mobile').keyup(function(){
			
			// 입력한 휴대전화
			let mobileValue = $(this).val();
			
			// 휴대전화 정규식(010으로 시작, 하이픈 없이 전체 10~11자)
			let regMobile = /^010[0-9]{7,8}$/;
			
			// 정규식 검사
			if(regMobile.test(mobileValue) == false){
				$('#msg_mobile').text('휴대전화를 확인하세요.');
				mobilePass = false;
			} else {
				$('#msg_mobile').text('');
				mobilePass = true;
			}
			
		});  // keyup
		
	}  // fn_mobileCheck
	
	// 6. 생년월일(년도)
	function fn_birthyear(){
		let year = new Date().getFullYear();
		let strYear = '<option value="">년도</option>';
		for(let y = year - 100; y <= year + 1; y++){
			strYear += '<option value="' + y + '">' + y + '</option>';
		}
		$('#birthyear').append(strYear);
	}  // fn_birthyear
	
	// 7. 생년월일(월)
	function fn_birthmonth(){
		let strMonth = '<option value="">월</option>';
		for(let m = 1; m <= 12; m++){
			if(m < 10){
				strMonth += '<option value="0' + m + '">' + m + '월</option>';
			} else {
				strMonth += '<option value="' + m + '">' + m + '월</option>';
			}
		}
		$('#birthmonth').append(strMonth);
	}  // fn_birthmonth
	
	// 8. 생년월일(일)
	function fn_birthdate(){
		
		$('#birthdate').append('<option value="">일</option>');
		
		$('#birthmonth').change(function(){
			
			$('#birthdate').empty();
			$('#birthdate').append('<option value="">일</option>');
			let endDay = 0;
			let strDay = '';
			switch($(this).val()){
			case '02':
				endDay = 29; break;
			case '04':
			case '06':
			case '09':
			case '11':
				endDay = 30; break;
			default:
				endDay = 31; break;
			}
			for(let d = 1; d <= endDay; d++){
				if(d < 10){
					strDay += '<option value="0' + d + '">' + d + '일</option>';
				} else {
					strDay += '<option value="' + d + '">' + d + '일</option>';
				}
			}
			$('#birthdate').append(strDay);
			
		});  // change
		
	}  // fn_birthdate
	// 9. 서브밋 (회원가입)
	function fn_join(){
		
		$('#frm_join').submit(function(event){
			
			if(emailPass == false){
				alert('이메일을 확인하세요.');
				event.preventDefault();
				return;
			} else if(pwPass == false){
				alert('비밀번호를 확인하세요.');
				event.preventDefault();
				return;
			} else if(rePwPass == false) {
				alert('비밀번호 재확인을 확인하세요');
				event.preventDefault();
				return;
			} else if(namePass == false){
				alert('이름을 확인하세요.');
				event.preventDefault();
				return;
			} else if($('#gender').val() == '') {
				alert('성별을 확인하세요.');
				return;
			} else if(mobilePass == false){
				alert('휴대전화번호를 확인하세요.');
				event.preventDefault();
				return;
			} else if($('#birthyear').val() == '' || $('#birthmonth').val() == '' || $('#birthdate').val() == ''){
				alert('생년월일을 확인하세요.');
				event.preventDefault();
				return;
			} else if(authCodePass == false){
				alert('이메일 인증을 받으세요.');
				event.preventDefault();
				return;
			}
			
		});  // submit
		
	}  // fn_join
	
	// 목록
	function fn_cancel(){
		$('#btn_cancel').click(function(){
			location.href = '/';
		}); 
	}
	// 추가사항 입력
	function fn_extraInfo() {
		$('#msg_extra_info').click(function(){
			$('.join_middle_controller').removeClass('blind');
			$('#btn_close').removeClass('blind');
		});
		$('#btn_close').click(function(){
			$('.join_middle_controller').addClass('blind');
			$('#btn_close').addClass('blind');
		});
	}
