$(function(){
	fn_checkAll();
	fn_checkOne();
	fn_toggleCheck();
	fn_submit();
});

// 모두 동의 체크하면, 개별 체크에 체크 표시
function fn_checkAll(){
	$('#check_all').click(function(){
		$('.check_one').prop('checked', $(this).prop('checked'));
		if($(this).is(':checked')){
			$('.lbl_one').addClass('lbl_checked');
		} else {
			$('.lbl_one').removeClass('lbl_checked');
		}
	});
}


//	개별 체크 4개 모두 선택되었다면 모두 동의 check
function fn_checkOne(){
	$('.check_one').click(function(){
		let checkCount = 0;
		for(let i = 0; i < $('.check_one').length; i++){
			checkCount += $($('.check_one')[i]).prop('checked');
		}
		$('#check_all').prop('checked', $('.check_one').length == checkCount);
		if($('#check_all').is(':checked')){
			$('.lbl_all').addClass('lbl_checked');
		} else {
			$('.lbl_all').removeClass('lbl_checked');
		}
	});
}

// 체크할때마다 lbl_checked 클래스를 줬다 뺐었다 하기
function fn_toggleCheck(){
	$('.lbl_all, .lbl_one').click(function(){
		$(this).toggleClass('lbl_checked');
	});
}

// 서브밋 (필수 체크 여부 확인)
function fn_submit(){
	$('#frm_agree').submit(function(event){
		if($('#service').is(':checked') == false || $('#privacy').is(':checked') == false){
			alert('필수 약관에 동의하세요.');
			event.preventDefault();
			return;
		}
	});
}
