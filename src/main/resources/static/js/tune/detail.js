$(function(){
	fn_tabContent();
	fn_next_track_box();
	fn_lyrics_box();
	fn_comment_box();
	
	// 음원트랙
	fn_goMusic();
	fn_nextTrack();
//	fn_shuffleTrack();
	
	// 댓글
	fn_commentCount();
	fn_commentList();
	fn_addComment();
	fn_removeComment();

});

var nextMusicNo = 0;

function fn_tabContent(){
	$('.tune_tab_content').click(function(){
		$('.tune_tab_content').removeClass('border_bottom_solid_white');
		$('.tune_next_track_box').addClass('blind');
		$('.tune_lyrics_box').addClass('blind');
		$('.tune_comment_box').addClass('blind');
		$(this).addClass('border_bottom_solid_white');
	});
}
function fn_goMusic() {
	$(document).on('click', '.music_track_box', function(){
		var musicNo = $(this).children().data('music');
		if(musicNo != null){
			window.parent.location.href = '/tune/iframe?musicNo=' + musicNo;
			
		}
	});
}

function fn_next_track_box(){
	$('.tab_next_track').click(function(){
		$('.tune_next_track_box').removeClass('blind');
	});
}
function fn_shuffleTrack(){
	$.ajax({
		type: 'get',
		url : '/tune/shuffleTrack',
		dataType: 'json',
		success : function(resData) {
			var txt = '';
			if(resData.musicList != 0) {
				$('.tune_next_track_box').empty();
				$.each(resData.musicList, function(i, music){
					txt += '<div class="music_track_box">';
						txt += '<input type="hidden" id="'+ i +'" value="'+ music.musicNo+ '">';
						txt += '<div class="music_img">';
						txt += '<img src="/tune/display/image?musicNo='+ music.musicNo + '" id="iframe_album_image">'
						txt += '</div>';
						txt += '<div class="music_info">';
						txt += '<div>' + music.musicNo+ '</div>';
							txt += '<div style="font-weight:600">' + music.musicTitle + '</div>';
							txt += '<div style="font-size:14px color: #bdc2ce">' + music.userDTO.artist + '</div>';
						txt += '</div>'
					txt += '</div>'
				});
				$('.tune_next_track_box').append(txt);
				nextMusicNo = $('#0').val();
				/*
					window.onload = function() {
					console.log('child load');  
					window.parent.postMessage({ childData : 'nextMusicNo' }, '*	');
				}
				alert('부모창으로 보내기!');
				*/
			}
		},
	});
}

function fn_lyrics_box(){
	$('.tab_lyrics').click(function(){
		$('.tune_lyrics_box').removeClass('blind');
	});
}
function fn_comment_box(){
	$('.tab_comment').click(function(){
		$('.tune_comment_box').removeClass('blind');
	});
}



function fn_click_album_image(){
	$('#albumImg').click(function(){
		var imgSrc =  $(this).attr('src').val();
		$('#music_iframe').attr('src', imgSrc);
	});
}

	
function fn_commentCount(){
	var musicNo = $('#musicNo').val();
	$.ajax({
		type: 'get',
		url: '/comment/getCount',
		data: 'musicNo=' + musicNo,	// 'musicNo=[[${music.musicNo}]]'
		dataType: 'json',
		success: function(resData){  // resData = {"commentCount": 개수}
			$('#comment_count').text(resData.commentCount);
		}
	});
}
	
function fn_commentList(){
	var musicNo = $('#musicNo').val();
	$.ajax({
		type: 'get',
		url: '/comment/list',
		data: 'musicNo=' + musicNo,
		dataType: 'json',
		success: function(resData){
			/*
				resData = {
					"commentList": [
						{댓글하나},
						{댓글하나},
						...
					],
					"pageUtil": {
						page: x,
						...
					}
				}
			*/
			// 화면에 댓글 목록 뿌리기
			$('#comment_list').empty();
			$.each(resData.commentList, function(i, comment){
				var div = '';
					div += '<div class="comment_bottom_comment">';
						// 코맨트 리스트 상단 (닉네임, 작성일)
						div += '<div class="bottom_comment_info">';
						div += comment.userDTO.artist;
						moment.locale('ko-KR');
						div += '<span  class="comment_info_date">' + moment(comment.createDate).format('YYYY. MM. DD A hh:mm') + '</span>';
						div += "</div>";
						// 코맨트 리스트 하단 (내용)
						div += '<div class="comment_bottom_content">';
						div += comment.commentContent;
						// 작성자만 삭제할 수 있도록 if 처리
						if ('${session.loginUser}' != null && comment.email == $('#email')) {
						div += '<input type="button" value="삭제" class="btn_comment_remove" data-comment_no="' + comment.commentNo + '">';
						}
						div += '</div>';
					div += '</div>';
				$('#comment_list').append(div);
				$('#comment_list').append('<div style="border-bottom: 1px dotted gray;"></div>');
			});
		}
	});
}  // fn_commentList

function fn_addComment(){
	$('#btn_add_comment').click(function(){
		if($('#commentContent').val() == ''){
			alert('댓글 내용을 입력하세요');
			return;
		}
		$.ajax({
			type: 'post',
			url: '/comment/add',
			data: $('#frm_comment').serialize(),
			dataType: 'json',
			success: function(resData){  // resData = {"isAdd", true}
				if(resData.isAdd){
					alert('댓글이 등록되었습니다.');
					$('#commentContent').val('');
					fn_commentList();   // 댓글 목록 가져와서 뿌리는 함수
					fn_commentCount();  // 댓글 목록 개수 갱신하는 함수
				}
			}
		});
	});
}
function fn_removeComment(){
	$(document).on('click', '.btn_comment_remove', function(){
		if(confirm('삭제된 댓글은 복구할 수 없습니다. 댓글을 삭제할까요?')){
			$.ajax({
				type: 'post',
				url: '/comment/remove',
				data: 'commentNo=' + $(this).data('comment_no'),
				dataType: 'json',
				success: function(resData){  // resData = {"isRemove": true}
					if(resData.isRemove){
						alert('댓글이 삭제되었습니다.');
						fn_commentList();
						fn_commentCount();
					}
				}
			});
		}
	});
}

