$(function(){
	fn_musicList();
	fn_detail();
	fn_edit();
	fn_remove();
})

function fn_musicList(){
	// 음원 목록 불러오기
	$.ajax({
		type: 'get',
		url : '/tune/musicList',
		dataType: 'json',
		success : function(resData){
			var txt = '';
			moment.locale('ko');
			if (resData.musics == 0) {
				txt += '<div>등록한 글이 없습니다.</div>';
			} else {
				$.each(resData.musics, function(i, music) {
					txt += '<div class="music_controller" style="display:flex">';
						txt += '<div class="flex">';
							txt += '<div class="image_box" data-music_no="' + music.musicNo + '" style="background-image:url(/tune/display/image?musicNo=' + music.musicNo + ')"></div>';
							txt += '<div class="music_info flex_cloumn">';
								txt += '<div>음원명 : ' + music.musicTitle + '</div>';
								txt += '<div>앨범명 : ' + music.musicAlbum + '</div>';
								if ($('#userNo').val() == 1) {
									txt += '<div>작성자 : ' + music.userDTO.artist + '</div>';
								}
							txt += '</div>';
						txt += '</div>';
						txt += '<div class="reaction_box">';
							txt += '<div class="flex"><img src="/images/like.png" style="width:24px;"><span class="count_like">'+resData.like[i]+'</span></div>';
							txt += '<div class="flex"><i class="fa-solid fa-comment-dots"></i><span class="count_comment">' + resData.comment[i] + '</span></div>';
						txt += '</div>';
						txt += '<div class="date_box flex">' + moment(music.musicUploadDate).format('YYYY-MM-DD A h:mm') + '</div>';
						txt += '<form class="btn_box flex" method="get" action="/tune/musicEdit">';
							txt += '<input type="hidden" class="musicNo" name="musicNo" value="' + music.musicNo + '">';
							txt += '<input type="button" class="btn edit" value="수정">';
							txt += '<input type="button" class="btn remove" value="삭제" data-music_no="' + music.musicNo + '">';
						txt += '</form>';
					txt += '</div>';
				});
			}
			$('.music_list_box').append(txt);
		}
	})
}

// 음원 이동
function fn_detail(){
	$(document).on('click', '.image_box', function(){
		window.parent.location.href="/tune/iframe?musicNo=" + $(this).data('music_no');
	});
}

// 음원 수정
function fn_edit(){
	$(document).on('click', '.edit', function(){
		 $(this).closest('.btn_box').submit();
	});
}

// 음원 삭제
function fn_remove(){
	$(document).on('click', '.remove', function(){
		$.ajax({
			type: 'post',
			url : '/tune/musicRemove',
			data: 'musicNo=' + $(this).data('music_no'),
			dataType: 'text',
			success : function(){
				$('.music_list_box').text('');
				alert('음악이 삭제되었습니다.');
				fn_musicList();
			}
		})
	});
}