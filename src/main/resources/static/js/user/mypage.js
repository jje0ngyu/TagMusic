
$(function(){
	//fn_displayImage();
	fn_changeImage();
	fn_closePopup();
	
	// 페이지 이동
	fn_myinfo();		 // 개인정보관리
	fn_paymentHistory(); // 결제이력
	fn_tuneList();		 // 내가 쓴 글
	fn_manageMusic();	 // 관리자 - 음원관리
	fn_manageUser();	 // 관리자 - 유저관리
	fn_notice();		 // 관리자 - 공지사항
	fn_faq();			 // 관리자 - 자주 하는 질문
	
	// 이미지 변경 모달팝업
	$('#mypage_profileImage').click(function(){
		$('.box_backlayer').removeClass('blind');
		$('.profile_image_controller').removeClass('blind');
	});
	
	$('#customerService').click(function(){
		location.href='/customerService/chat';
	});

});
// 이미지 노출
/*
function fn_displayImage(){
	$.ajax({
		type: 'post',
		url : '/user/info/getImage',
		dataType: 'json',
		success: function(resData){
			console.log(resData.imagePath)
			$('#profileImage').attr('src',resData.iamgePath);
		},
		error: function(){
			console.log('에러떴다');
		}
	}); // ajax
} */


// 이미지 변경
function fn_changeImage(){
	$('#frm_progileImage').submit(function(){
		$.ajax({
			type: 'post',
			enctype: 'multipart/form-data',
			url : '/user/info/modifyImage',
			data: $('#frm_progileImage').serialize(),
			processData: false,
			contentType: false
		})
	});
}
// 팝업창 닫기
function fn_closePopup(){
	$('#profile_cancel').click(function(){
		$('.box_backlayer').addClass('blind');
		$('.profile_image_controller').addClass('blind');
	});
}
