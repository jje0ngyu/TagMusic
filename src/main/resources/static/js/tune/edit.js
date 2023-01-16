$(function(){
	
	// 서브밋 (필수항목 체크)
	fn_edit();
	fn_back();
	fn_musicCheck();
	fn_imgCheck();
});

function fn_edit(){
	$('#frm_upload').submit(function(event){
		alert('음원 정보를 수정합니다.');
	}); // submit
}
function fn_back(){
	$('#btn_cancel').click(function(){
		location.href='/user/mypage';
	});
}

function fn_musicCheck(){
	$('#music').change(function(event){
		let musicValue = $(this).val();
		if(musicValue != null && musicValue != '') {
			let other = musicValue.split('.').pop().toLowerCase();
        	//아래 확장자가 있는지 체크
       	 	if(other != 'mp3' && other != 'mp4' && other != 'org') {
          		alert('지원하지 않는 파일 형식입니다.');
          		$(this).val('');
          		return;
			}
		}
	});
}

function fn_imgCheck(){
	$('#albumImg').change(function(){
		let imgValue = $(this).val();
		if(imgValue != null && imgValue != '') {
			let other = imgValue.split('.').pop().toLowerCase();
			// 아래 확장자가 있는지 체크
			if(other != 'png' && other != 'jpg' && other != 'gif' && other != 'jpeg') {
				alert('음악 파일은 png,jpg,jpeg,gif 형식만 등록할 수 있습니다.')
			}
		}
	});
}