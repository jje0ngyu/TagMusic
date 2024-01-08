$(document).ready(function() {
		alert("메인!");
  // 구현 : main페이지 최신리스트바
//fn_updatedMusic();

//clearInterval(fn_updatedMusic);
//setInterval(fn_updatedMusic, 15000);

/* fn_updatedPageMove();		// 기능 : 최신리스트바 페이지 버튼
fn_updatedMusicBoard();		// 구현 : 최신리스트 게시판조회

fn_popularMusic();			// 구현 : 인기음악 전체리스트바
fn_popularPageMove();		// 기능 : 인기음악 페이지 버튼
fn_popularMusicBoard();		// 구현 : 인기음악 게시판조회
fn_popllarMusic_Total();	// 구현 : 인기음악 장르_가요 전체요청버튼
fn_popllarMusic_Gayeo();	// 구현 : 인기음악 장르_가요 리스트바
fn_popularPageMoveGenre();
fn_popllarMusic_POP();		// 구현 : 인기음악 장르_팝 리스트바
fn_popllarMusic_Trot();		// 구현 : 인기음악 장르_트로트 리스트바
fn_popllarMusic_Rock();		// 구현 : 인기음악 장르_락 리스트바
iframe_click(); */
});

// 전역변수
var page = 1  	// 페이징 처리
var pageUtil;	// 페이지유틸
var text;
var content;

// # 구현 : main페이지 최신리스트 바, ajax


  
function fn_updatedMusic() {
  
    $.ajax({
      type: 'get',
      url: '/music/list/updated',
      data : 'page=' + $('#page_updated').val(),	
      dataType: 'json',
      success: function(resData) {	
        
        // 갱신
        $('.main_lastList_body').empty();
    
        // 페이징
        pageUtil = resData.pageUtil;
        page = $('#page_updated').val();
        
        // (1) # 기능 : 페이징 좌측버튼 보여주기
        if(page == pageUtil.beginPage) {
          $('.main_lastList_body').append('<div class="main_lastList_body_left_btn" data-page="' +(pageUtil.endPage) + '"><i class="fa-solid fa-circle-chevron-left fa-3x"></i></div>');
        } else {
          $('.main_lastList_body').append('<div class="main_lastList_body_left_btn" data-page="' +(pageUtil.page -1) + '"><i class="fa-solid fa-circle-chevron-left fa-3x"></i></div>');
        }
        
        
        

        content = $('<div id="main_lastList_body_content" class="main_lastList_body_content"></div>');

        // (2) 가운데 : 게시글
        $.each(resData.musicList, function(index, music) {
          
          
          // # 구현 : 썸네일 가져오기
          if(music.hasThumbNail == 1) {
            text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
          } else if(music.hasThumbNail == 0) {
            text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
          }
          
            $('<div class="main_item">')
           .append(text)
           .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
           .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
           .appendTo( $(content) );
        }); 
  
         $('.main_lastList_body').append( $(content) );
      
        
        // (3) # 기능 : 페이징 우측버튼 보여주기
        if(page != pageUtil.endPage) {
          $('.main_lastList_body').append('<div class="main_lastList_body_right_btn main_lastList_body_right_btn_time"  data-page="' + (pageUtil.page + 1) + '"><i class="fa-solid fa-circle-chevron-right fa-3x"></i></div>');
        } else {
          $('.main_lastList_body').append('<div class="main_lastList_body_right_btn  "  data-page="1"><i class="fa-solid fa-circle-chevron-right fa-3x"></i></div>');
        }
        
      }
    });
  
    $('#page_updated').val($('.main_lastList_body_right_btn_time').data('page'));
    
  }

  
  


// 기능 : 우측버튼 최신화
function fn_updatedMusicr() {

$.ajax({
  type: 'get',
  url: '/music/list/updated',
  data : 'page=' + $('#page_updated').val(),	
  dataType: 'json',
  success: function(resData) {	
    
    // 갱신
    $('.main_lastList_body').empty();

    // 페이징
    pageUtil = resData.pageUtil;
    page = $('#page_updated').val();
    
    // (1) # 기능 : 페이징 좌측버튼 보여주기
    
    if(page == pageUtil.beginPage) {
      $('.main_lastList_body').append('<div class="main_lastList_body_left_btn" data-page="' +(pageUtil.endPage) + '"><i class="fa-solid fa-circle-chevron-left fa-3x"></i></div>');
    } else {
      $('.main_lastList_body').append('<div class="main_lastList_body_left_btn" data-page="' +(pageUtil.page -1) + '"><i class="fa-solid fa-circle-chevron-left fa-3x"></i></div>');
    }
    
    content = $('<div id="main_lastList_body_content" class="main_lastList_body_content"></div>');

    // (2) 가운데 : 게시글
    $.each(resData.musicList, function(index, music) {
      
      
      // # 구현 : 썸네일 가져오기
      if(music.hasThumbNail == 1) {
        text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      } else if(music.hasThumbNail == 0) {
        text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      }
      
        $('<div class="main_item_r">')
       .append(text)
       .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
       .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
       .appendTo( $(content) );
    }); 

     $('.main_lastList_body').append( $(content) );
  
    
    // (3) # 기능 : 페이징 우측버튼 보여주기
    if(page != pageUtil.endPage) {
      $('.main_lastList_body').append('<div class="main_lastList_body_right_btn"  data-page="' + (pageUtil.page + 1) + '"><i class="fa-solid fa-circle-chevron-right fa-3x"></i></div>');
    } else {
      $('.main_lastList_body').append('<div class="main_lastList_body_right_btn"  data-page="1"><i class="fa-solid fa-circle-chevron-right fa-3x"></i></div>');
    }
    
    
  }
});	
}




// # 기능 : 최신리스트바 페이지 버튼
function fn_updatedPageMove() {
 $(document).on('click', '.main_lastList_body_left_btn', function(){  	        
       $('#page_updated').val($(this).data('page'));	// 버튼에 저장된 page 태그값을 hidden의 페이지값에 전달 -> 새로운 페이지를 파라미터로 전달해서 재요청
       fn_updatedMusicr();
    });
    
    $(document).on('click', '.main_lastList_body_right_btn', function(){
            
       $('#page_updated').val($(this).data('page'));	// 버튼에 저장된 page 태그값을 hidden의 페이지값에 전달 -> 새로운 페이지를 파라미터로 전달해서 재요청
     
       fn_updatedMusic();
    });
}

// # 구현 : 최신리스트 게시판조회, mvc
function fn_updatedMusicBoard() {
$('#btn_updatedMusicBoard').click(function() {
  location.href="/music/board/updatedMusic?page=1";
});
}





// # 구현 : 인기음악 전체리스트바
function fn_popularMusic() {
$.ajax({
  type: 'get',
  url: '/music/list/popular',
  data : 'page=' + $('#page_popular').val(),	
  dataType: 'json',
  success: function(resData) {	// resData : musicList, pageUtil, artist
    
    // 갱신
    $('.main_musicLike_body').empty();


    // 페이징
    pageUtil = resData.pageUtil;
    page = $('#page_popular').val();
    
    
    
    content = $('<div id="main_musicLike_body_content" class="main_musicLike_body_content"></div>');

    // (2) 가운데 : 게시글
    $.each(resData.musicList, function(index, music) {
      
      
      // # 구현 : 썸네일 가져오기
      if(music.hasThumbNail == 1) {
        text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      } else if(music.hasThumbNail == 0) {
        text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      }
      
        $('<div class="main_item_p">')
       .append(text)
       .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
       .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
       .appendTo( $(content) );
    }); 

     $('.main_musicLike_body').append( $(content) );
  
    
    
  }
});	

}

// # 구현 : 인기리스트 게시판 이동
function fn_popularMusicBoard() {
$('#btn_popularMusicBoard').click(function() {
  location.href="/music/board/popularMusic?page=1";
});
}

// # 기능 : 인기리스트바 페이지 버튼
function fn_popularPageMove() {
 $(document).on('click', '.enable_listLink', function(){
       $('#page_popular').val($(this).data('page'));	// 버튼에 저장된 page 태그값을 hidden의 페이지값에 전달 -> 새로운 페이지를 파라미터로 전달해서 재요청
       fn_popularMusic();
    });
}

// # 인기리스트 : 전체버튼
function fn_popllarMusic_Total() {

$('#bar_genre_total').click(function () {
   $('#page_popular').val('1');
  fn_popularMusic();
});
}

// # 인기리스트 : 가요버튼

function fn_popllarMusic_Gayeo() {



$('#bar_genre_gayeo').click(function () { 
  
  $('#page_popular_genre').val('1');
  
  $.ajax({
  type: 'get',
  url: '/music/list/popular/genre',
  data : 'page=' + $('#page_popular_genre').val() + '&genre=' + $('#bar_genre_gayeo').text(),	
  dataType: 'json',
  success: function(resData) {	
    
    
    
    
      // 갱신
    $('.main_musicLike_body').empty();


    // 페이징
    pageUtil = resData.pageUtil;
    page = $('#page_popular_genre').val();
    
    content = $('<div id="main_musicLike_body_content" class="main_musicLike_body_content"></div>');

    
    $.each(resData.musicList, function(index, music) {
      
      
      // # 구현 : 썸네일 가져오기
      if(music.hasThumbNail == 1) {
        text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      } else if(music.hasThumbNail == 0) {
        text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      }
      
        $('<div class="main_item_p">')
       .append(text)
       .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
       .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
       .appendTo( $(content) );
    }); 

     $('.main_musicLike_body').append( $(content) );
    
    
  }
});		
});
}

// # 기능 : 인기리스트 장르 페이지

function fn_popularPageMoveGenre() {
  
  
   $(document).on('click', '.enable_listLink12', function(){
         $('#page_popular_genre').val($(this).data('page'));	// 버튼에 저장된 page 태그값을 hidden의 페이지값에 전달 -> 새로운 페이지를 파라미터로 전달해서 재요청
         fn_popllarMusic_Gayeo();
      });
}

function fn_popllarMusic_POP() {	// 팝송


  $('#bar_genre_pop').click(function () { 
    
    $('#page_popular_genre').val('1');
    
    $.ajax({
    type: 'get',
    url: '/music/list/popular/genre',
    data : 'page=' + $('#page_popular_genre').val() + '&genre=' + $('#bar_genre_pop').text(),	
    dataType: 'json',
    success: function(resData) {	// resData : musicList, pageUtil, artist
      
        // 갱신
    $('.main_musicLike_body').empty();


    // 페이징
    pageUtil = resData.pageUtil;
    page = $('#page_popular_genre').val();
    
    content = $('<div id="main_musicLike_body_content" class="main_musicLike_body_content"></div>');

    
    $.each(resData.musicList, function(index, music) {
      
      
      // # 구현 : 썸네일 가져오기
      if(music.hasThumbNail == 1) {
        text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      } else if(music.hasThumbNail == 0) {
        text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      }
      
        $('<div class="main_item_p">')
       .append(text)
       .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
       .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
       .appendTo( $(content) );
    }); 

     $('.main_musicLike_body').append( $(content) );
    }
  });		
});

}		

function fn_popllarMusic_Trot() {	// 구현 : 인기순 트로트 조회

$('#bar_genre_trot').click(function () { 
    
    $('#page_popular_genre').val('1');
    
    $.ajax({
    type: 'get',
    url: '/music/list/popular/genre',
    data : 'page=' + $('#page_popular_genre').val() + '&genre=' + $('#bar_genre_trot').text(),	
    dataType: 'json',
    success: function(resData) {	// resData : musicList, pageUtil, artist
      
          // 갱신
    $('.main_musicLike_body').empty();


    // 페이징
    pageUtil = resData.pageUtil;
    page = $('#page_popular_genre').val();
    
    content = $('<div id="main_musicLike_body_content" class="main_musicLike_body_content"></div>');

    
    $.each(resData.musicList, function(index, music) {
      
      
      // # 구현 : 썸네일 가져오기
      if(music.hasThumbNail == 1) {
        text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      } else if(music.hasThumbNail == 0) {
        text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      }
      
        $('<div class="main_item_p">')
       .append(text)
       .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
       .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
       .appendTo( $(content) );
    }); 

     $('.main_musicLike_body').append( $(content) );
    
    }
  });		
});


}

function fn_popllarMusic_Rock() {	// 구현 : 인기순 락 조회

$('#bar_genre_rock').click(function () { 
    
    $('#page_popular_genre').val('1');
    
    $.ajax({
    type: 'get',
    url: '/music/list/popular/genre',
    data : 'page=' + $('#page_popular_genre').val() + '&genre=' + $('#bar_genre_rock').text(),	
    dataType: 'json',
    success: function(resData) {	// resData : musicList, pageUtil, artist
      
          // 갱신
    $('.main_musicLike_body').empty();


    // 페이징
    pageUtil = resData.pageUtil;
    page = $('#page_popular_genre').val();
    
    content = $('<div id="main_musicLike_body_content" class="main_musicLike_body_content"></div>');

    
    $.each(resData.musicList, function(index, music) {
      
      
      // # 구현 : 썸네일 가져오기
      if(music.hasThumbNail == 1) {
        text = '<img class="item_image" src="/tune/display/image?musicNo=' + music.musicNo + '" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      } else if(music.hasThumbNail == 0) {
        text = '<img class="item_image" src="/images/defaultImage.png" width="169px" height="169px" data-musno="'+music.musicNo+'">';
      }
      
        $('<div class="main_item_p">')
       .append(text)
       .append('<a class="item_title" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.musicTitle + '</a>')
       .append('<a class="item_artist" href="tune/iframe?musicNo=' + music.musicNo + ' data-musno="'+music.musicNo+'">' + music.userDTO.artist + '</a>')
       .appendTo( $(content) );
    }); 
     
     $('.main_musicLike_body').append( $(content) );
    
    }
  });		
});


}

function iframe_click() {

var musicNo;

$(document).on('click', '.item_image', function() {
  
  musicNo = $(this).data('musno');
  
  window.parent.location.href="/tune/iframe?musicNo=" + musicNo;
});

$(document).on('click', '.item_title', function() {
  
  musicNo = $(this).data('musno');
  
  window.parent.location.href="/tune/iframe?musicNo=" + musicNo;
});

$(document).on('click', '.item_artist', function() {
  
  musicNo = $(this).data('musno');
  
  window.parent.location.href="/tune/iframe?musicNo=" + musicNo;
});


}