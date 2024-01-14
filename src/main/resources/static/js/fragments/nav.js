// 페이지 이동 function()
$(function(){
	$('#add_music').click(function(){
		location.href='/tune/write';
	});
	
	// 관리자 페이지
	$('#myinfo').click(function(){
		location.href="/user/mypage/info";
	})
	$('#mypaymentHistory').click(function(){
		$('#submit_history').submit();
	});
	$('#tuneList').click(function(){
		location.href="/tune/list";
	})
	$('#manageUser').click(function(){
		location.href="/admin/user/control";
	});
	$('#manageMusic').click(function(){
		location.href="/tune/list";
	});
	$('.notice').click(function(){
		location.href="/board/list";
	});
	$('.faq').click(function(){
		location.href="/upload/list";
	});
});

// 내 정보 홈
$('#mypageHome').click(function(){
	$('.text_box').removeClass('.text_box_click');
	$('#mypageHome').addClass('.text_box_click');
	location.href='/user/mypage';
});
// 개인정보 관리
$('#info').click(function(){
	$('.text_box').removeClass('.text_box_click');
	$('#info').addClass('.text_box_click');
	location.href="/user/mypage/info";
});
// 음원 등록
$('#addMusic').click(function(){
	location.href="/tune/write";
});
// 결제이력
$('#paymentHistory').click(function(){
	$('.text_box').removeClass('.text_box_click');
	$('#mypageHome').addClass('.text_box_click');
	$('#submit_history').submit();
});
// 관리자 - 유저관리
$('#userManagement').click(function(){
	location.href="/admin/user/control";
});
// 관리자 - 공지사항
$('.notice').click(function(){
	location.href="/board/list";
});
// 관리자 - 자주 하는 질문
$('.faq').click(function(){
	location.href="/upload/list";
});
// 1:1 문의
$('#chat').click(function() {
	$('.text_box').removeClass('.text_box_click');
	$('#mypageHome').addClass('.text_box_click');
	location.href="/customerService/chat";
})
// 로그아웃
$('.logout_box').click(function() {
	$('.text_box').removeClass('.text_box_click');
	$('#mypageHome').addClass('.text_box_click');
	window.parent.location.href="/user/logout";
})