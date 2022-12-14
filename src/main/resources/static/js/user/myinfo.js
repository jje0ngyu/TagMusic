	$(function(){
		fn_displayArtist();
		fn_modifyArtist();
		fn_editArtist();
		
		// 이름 수정
		fn_modifyName();
		fn_editName();
		
		// 비밀번호 수정
		fn_checkPw();
		fn_modifyPw();
		fn_editPw();
		fn_msgPw();
		
		fn_modifyMobile();
		fn_editMobile();
		fn_modifyAgreeCode();
		fn_editAgreeCode();
		fn_backlayer();
		fn_retire();
		
	});
	
	function fn_displayArtist(){
	};
	
	
	// 닉네임 수정
	function fn_modifyArtist(){
		$('#info_artist').click(function(){
			$('.modify_artist').removeClass('blind');
		});
	}
	// 닉네임 수정 - 변경 버튼을 눌렀을 때
	function fn_editArtist(){
		$('#btn_artist').click(function(){
			$.ajax({
				type: 'post',
				url : '/user/info/modifyArtist',
				data: $('#frm_modifyUser').serialize(),
				dataType: 'text',
				success : function() {
					$('#artist').val('');
					$('.box_backlayer').addClass('blind');
					$('.modify_box').addClass('blind');
					$('.modify_artist').addClass('blind');
					alert('회원정보가 변경되었습니다.');
					location.reload();
					
				},
				error : function(jqXHR){
					console.log('error가 찍혔다');
				}
			})
		});
	}
	
	// 이름 수정
	function fn_modifyName(){
		$('#info_name').click(function(){
			$('.modify_name').removeClass('blind');
		});
	}
	// 이름 수정 - 변경 버튼을 눌렀을 때
	function fn_editName(){
		$('#btn_name').click(function(){
			$.ajax({
				type: 'post',
				url : '/user/info/modifyName',
				data: $('#frm_modifyUser').serialize(),
				dataType: 'text',
				success : function() {
					$('#name').val('');
					$('.box_backlayer').addClass('blind');
					$('.modify_box').addClass('blind');
					$('.modify_name').addClass('blind');
					alert('회원정보가 변경되었습니다.');
					location.reload();
					
				},
				error : function(jqXHR){
					console.log('error가 찍혔다');
				}
			})
		});
	}
	
	// 현재 비밀번호 확인
	function fn_checkPw(){
		$('#btn_checkPw').click(function(){
			var originPw = $('#originPw').val();
			$.ajax({
				type: 'post',
				url : '/user/info/checkPw',
				data: $('#frm_modifyUser').serialize(),
				dataType: 'json',
				success : function(resData) {
					if (resData.result) {
						alert('조회되었습니다.');
						$('#new_pw_box').removeClass('blind');
						$('#originPw').attr('readonly',true);
						$('.info_input').addClass('read_only');
						$('#btn_checkPw').addClass('blind');
					} else {
						alert('조회에 실패');
						$('#originPw').val('');
					}
				},
				error : function(){
					console.log('에러떳다');
				}
			})
		});
	}
	
	// 비밀번호 수정 - 새로운 정보 확인
	function fn_msgPw(){
		// 비밀번호 정규식
		$('#newPw').keyup(function(){
			var newPw = $('#newPw').val();
			// 정규식 (4~60자, 숫자+소문자+대문자+특수문자(-_!@#%^&*) 조합)
			let regPw = /^[0-9a-zA-Z-_!@#%^&*]{4,30}$/;
			if(regPw.test(newPw) == false) {
				$('#msgNewPw').removeClass('blind');
			} else {
				$('#msgNewPw').addClass('blind');
			}
		});
		// 비밀번호 일치 확인
		$('#reNewPw').keyup(function(){
			var newPw = $('#newPw').val();
			var reNewPw = $('#reNewPw').val();
			if (newPw == reNewPw) {
				$('#msgPw').addClass('blind');
			} else {
				$('#msgPw').removeClass('blind');
			}
		});
	}
	
	// 비밀번호 수정
	function fn_modifyPw(){
		$('#info_pw').click(function(){
			$('.modify_pw').removeClass('blind');
		});
	}
	
	// 비밀번호 수정 - 변경 버튼을 눌렀을 때
	function fn_editPw(){
		$('#btn_pw').click(function(){
			$.ajax({
				type: 'post',
				url : '/user/info/modifyPw',
				data: $('#frm_modifyUser').serialize(),
				dataType: 'text',
				success : function() {
					$('#originPw').val('');
					$('#newPw').val('');
					$('#reNewPw').val('');
					$('.box_backlayer').addClass('blind');
					$('.modify_box').addClass('blind');
					$('.modify_pw').addClass('blind');
					alert('회원정보가 변경되었습니다.');
					location.reload();
				}
			})
		});
	}
	
	
	// 연락처 수정
	function fn_modifyMobile(){
		$('#info_mobile').click(function(){
			$('.modify_mobile').removeClass('blind');
		});
	}
	
	// 연락처 수정 - 변경 버튼을 눌렀을 때
	function fn_editMobile(){
		$('#btn_mobile').click(function(){
			$.ajax({
				type: 'post',
				url : '/user/info/modifyMobile',
				data: $('#frm_modifyUser').serialize(),
				dataType: 'text',
				success : function() {
					$('#mobile').val('');
					$('.box_backlayer').addClass('blind');
					$('.modify_box').addClass('blind');
					$('.modify_mobile').addClass('blind');
					alert('회원정보가 변경되었습니다.');
					location.reload();
				}
			})
		});
	}
	// 마케팅동의 수정
	function fn_modifyAgreeCode(){
		$('#info_agreeCode').click(function(){
			$('.modify_agreeCode').removeClass('blind');
		});
	}
	// 마케팅동의 수정 - 변경 버튼을 눌렀을 때
	function fn_editAgreeCode(){
		$('#btn_mobile').click(function(){
			$.ajax({
				type: 'post',
				url : '/user/info/modifyMobile',
				data: $('#frm_modifyUser').serialize(),
				dataType: 'text',
				success : function() {
					$('#mobile').val('');
					$('.box_backlayer').addClass('blind');
					$('.modify_box').addClass('blind');
					$('.modify_mobile').addClass('blind');
					alert('회원정보가 변경되었습니다.');
					location.reload();
				}
			})
		});
	}
	
	function fn_backlayer(){
		$('.info').click(function(){
			$('.box_backlayer').removeClass('blind');
			$('.modify_box').removeClass('blind');
		});
		$('.cancel').click(function(){
			$('.box_backlayer').addClass('blind');
			$('.modify_box').addClass('blind');
			$('.modify_artist').addClass('blind');
			$('#artist').val('');
			
			$('.modify_name').addClass('blind');
			$('#name').val('');
			
			$('.modify_pw').addClass('blind');
			$('#new_pw_box').addClass('blind');
			$('#originPw').attr('readonly',false);
			$('.info_input').removeClass('read_only');
			$('#btn_checkPw').removeClass('blind');
			$('#originPw').val('');
			$('#newPw').val('');
			$('#reNewPw').val('');
			
			$('.modify_mobile').addClass('blind');
			$('#mobile').val('');
			
			$('.modify_agreeCode').addClass('blind');
			
			$('.retire_box').addClass('blind');
			$('#pw').val('');
		})
	}
	
	// 회원탈퇴
	function fn_retire(){
		$('#retire').click(function(event){
			if($('#userNo').val() == 1) {
				alert('관리자는 탈퇴할 수 없습니다.');
				$('.box_backlayer').addClass('blind');
				return;
			}
			$('.retire_box').removeClass('blind');
			$('#btn_retire').click(function(){
				if(confirm('탈퇴하면 동일한 이메일로 재가입할 수 없습니다. 그래도 탈퇴하시겠습니까?')) {
					$.ajax({
						type: 'post',
						url : '/user/retire/checkPw',
						data: $('#frm_modifyUser').serialize(),
						dataType: 'json',
						success : function(resData) {
							console.log("resData:" + resData);
							console.log("resData.resData:" + resData.resData);
							// 비밀번호 일치
							// 1. 탈퇴 인사 페이지로 이동
							if (resData.resData > 0) {
								alert('탈퇴에 성공하셨습니다.');
								fn_goodbye();
							// 비밀번호 불일치
							// 1. 비밀번호 인풋창 리셋
							// 2. 화면 그대로 머물기
							} else {
								alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
								$('#pw').val('');
							}
						},
						error : function() {
							alert('세션이 만료되었습니다. 다시 시도해주세요.');
							location.href='/user/login/form';
						}
					}); // ajax
				} else {
					return;
				}
			});
		});
	}
	function fn_goodbye(){
		$('#frm_goodbye').submit();
	}
	
	
	