$(function(){
	
	// 서브밋 (필수항목 체크)
	fn_add();
	
	fn_albumCheck();
	fn_titleCheck();
	fn_musicCheck();
	fn_genreCheck();
});

// 전역변수 (각종 검사를 통과하였는지 점검하는 플래그 변수)
let albumPass = false;	// 앨범이름
let titlePass = false;	// 음원이름
let musicPass = false;	// 음원파일
let genrePass = false;	// 장르선택

function fn_add(){
	$('#frm_upload').submit(function(event){
		if(albumPass == false) {
			alert('앨범 이름을 확인하세요.');
			event.preventDefault();
			return;
		} else if(titlePass == false) {
			alert('음원 이름을 확인하세요.');
			event.preventDefault();
			return;
		} else if(musicPass == false) {
			alert('음악 파일을 등록해주세요.');
			event.preventDefault();
			return;
		} else if(genrePass == false) {
			alert('장르를 선택해주세요.');
			event.preventDefault();
			return;
		}
		alert('음원을 등록합니다.');
	}); // submit
}

function fn_albumCheck(){
	$('#musicAlbum').keyup(function(){
		let albumValue = $(this).val();
		if(albumValue != null && albumValue != '') {
			albumValue = true;
		}
	});
}
function fn_titleCheck(){
	$('#musicTitle').keyup(function(){
		let albumValue = $(this).val();
		if(albumValue != null && albumValue != '') {
			albumValue = true;
		}
	});
}

function fn_musicCheck(){
	$('#music').keyup(function(){
		let musicPass = $(this).val();
		if(musicPass != null && musicPass != '') {
			musicPass = true;
		}
	});
}

function fn_genreCheck(){
	$('#musicGenre').keyup(function(){
		let genrePass = $(this).val();
		if(genrePass != null && genrePass != '') {
			genrePass = true;
		}
	});
}