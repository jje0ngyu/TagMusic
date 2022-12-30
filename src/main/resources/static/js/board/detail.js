
$(function(){
	// 탭 (href)
	$('#tabHome').click(function(){
		location.href='/customerService/home';
	})
	$('#tabNotice').click(function(){
		location.href='/board/list';
	})
	$('#tabFAQ').click(function(){
		location.href='/upload/list';
	})
	$('#tabChat').click(function(){
		location.href='/customerService/chat';
	})
	

	// 하단부
	$('#btn_edit_board').click(function(){
		$('#frm_btn').attr('action', '/board/edit');
		$('#frm_btn').submit();
	});
	$('#btn_remove_board').click(function(){
		if(confirm('삭제하시겠습니까?')){
			$('#frm_btn').attr('action', '/board/remove');
			$('#frm_btn').submit();
		}
	});
	
	
})