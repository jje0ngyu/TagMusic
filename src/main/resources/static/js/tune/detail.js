$(function(){
	fn_tabContent();
	fn_next_track_box();
	fn_lyrics_box();
	fn_comment_box();
	
	// 음원트랙
	fn_trackList();
	
	// 댓글
	fn_commentCount();
	fn_commentList();
	fn_addComment();
	fn_removeComment();

});

	

function fn_tabContent(){
	$('.tune_tab_content').click(function(){
		$('.tune_tab_content').removeClass('border_bottom_solid_white');
		$('.tune_next_track_box').addClass('blind');
		$('.tune_lyrics_box').addClass('blind');
		$('.tune_comment_box').addClass('blind');
		$(this).addClass('border_bottom_solid_white');
	});
}

function fn_next_track_box(){
	$('.tab_next_track').click(function(){
		$('.tune_next_track_box').removeClass('blind');
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
	$.ajax({
		type: 'get',
		url: '/comment/getCount',
		data: 'musicNo=[[${music.musicNo}]]',
		dataType: 'json',
		success: function(resData){  // resData = {"commentCount": 개수}
			alert('resData.commentCount: ' + resData.commentCount);
			$('#comment_count').text(resData.commentCount);
		}
	});
}
	
function fn_commentList(){
	$.ajax({
		type: 'get',
		url: '/comment/list',
		data: 'musicNo=[[${music.musicNo}]]&page=' + $('#page').val(),
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
					div += '<div>';
					div += '<div style="margin-left: 40px;">';
				
					div += '<div>';
					div += comment.commentContent;
					// 작성자만 삭제할 수 있도록 if 처리
					if ('${session.loginUser}' != null && comment.email == $('#email')) {
						div += '<input type="button" value="삭제" class="btn_comment_remove" data-comment_no="' + comment.commentNo + '">';
					}
					div += '</div>';
				
				div += '<div>';
				moment.locale('ko-KR');
				div += '<span style="font-size: 12px; color: silver;">' + moment(comment.createDate).format('YYYY. MM. DD hh:mm') + '</span>';
				div += '</div>';
				div += '</div>';
				$('#comment_list').append(div);
				$('#comment_list').append('<div style="border-bottom: 1px dotted gray;"></div>');
			});
			// 페이징
			$('#paging').empty();
			var pageUtil = resData.pageUtil;
			var paging = '';
			// 이전 블록
			if(pageUtil.beginPage != 1) {
				paging += '<span class="enable_link" data-page="'+ (pageUtil.beginPage - 1) +'">◀</span>';
			}
			// 페이지번호
			for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++) {
				if(p == $('#page').val()){
					paging += '<strong>' + p + '</strong>';
				} else {
					paging += '<span class="enable_link" data-page="'+ p +'">' + p + '</span>';
				}
			}
			// 다음 블록
			if(pageUtil.endPage != pageUtil.totalPage){
				paging += '<span class="enable_link" data-page="'+ (pageUtil.endPage + 1) +'">▶</span>';
			}
			$('#paging').append(paging);
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

