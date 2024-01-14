$(document).ready(function () {
  fn_totalSearch(); // 구현 : 전체검색 + 기능 : 빈칸검색방지
  fn_musicRanking(); // 구현 : 랭킹(좋아요수 + 댓글수)
  fn_remainingDay();

  // user
  $(".account_manage").click(function () {
    location.href = "/user/mypage";
  });
  $(".btn_logout").click(function () {
    window.parent.location.href = "/user/logout";
  });

  $(".bell_img").click(function () {
    $(".header_alarm_controller").toggleClass("blind");
    $(".profile_box").addClass("blind");
  });
  $(".profile_image").click(function () {
    $(".header_alarm_controller").addClass("blind");
  });
});

// * 랭킹 조회하기 : 평소에는 1위만, 마우스 커서를 두면 2위부터 10위까지 조회
// - 평소에는 2~10위의 내용 가리고 있음
$("#ranking_ol").mouseover(function () {
  // 1) 2~10위까지 랭킹 내용 보여주기
  // (1) 기존클래스 삭제
  $("#ranking_ol").removeClass("ranking_ol");
  // (2) hover 클래스 추가
  $("#ranking_ol").addClass("ranking_ol_hover");
  // (3) 2~10위 가려진 내용 보여주기
  $(".ranking_li").removeClass("ranking_li");
});

// * 랭킹 조회하기 : 마우스 커서가 떠나면 원상태로 복구
$("#ranking_ol").mouseleave(function () {
  // (1) 기존클래스 삭제
  $("#ranking_ol").removeClass("ranking_ol_hover");
  // (2) hover 클래스 추가
  $("#ranking_ol").addClass("ranking_ol");
  // (3) 2~10위 가려진 내용 보여주기
  $(".ranking_ol").children().addClass("ranking_li");
});

var userEmail = $("#userEmail").data("email");
moment.locale("ko");
$(document).ready(function () {
  fn_alarm();
  fn_alarmAllremove();
});
function fn_alarm() {
  $(".alarm_middle_controller").empty();
  $.ajax({
    type: "post",
    url: "/alarm/list",
    data: "email=" + userEmail,
    dataType: "json",
    success: function (resData) {
      var txt = "";
      if (resData.alarmList == 0) {
        txt += '<div class="alarm_content">';
        txt += '<div class="alarm_info" style="justify-content: center;">';
        txt += '<div class="alarm_msg">새로 도착한 알람이 없습니다.</div>';
        txt += "</div>";
        txt += "</div>";
        $(".alarm_middle_controller").append(txt);
      } else {
        $.each(resData.alarmList, function (i, alarm) {
          txt += '<div class="alarm_content">';
          txt += '<div class="alarm_info">';
          txt += '<div class="alarm_gubun">' + alarm.alarmTitle + "</div>";
          txt +=
            '<div class="alarm_createDate">' +
            moment(alarm.alarmArrivalTime).format("YYYY-MM-DD A h:mm") +
            "</div>";
          txt += "</div>";
          txt += '<div class="alarm_msg">' + alarm.alarmContent + "</div>";
          txt += "</div>";
        });
        $(".alarm_middle_controller").append(txt);
      }
    },
  });
}
function fn_alarmAllremove() {
  $("#btn_all_read").click(function () {
    $.ajax({
      type: "post",
      url: "/alarm/remove",
      data: "email=" + userEmail,
      dataType: "json",
      success: function (resData) {
        fn_alarm();
      },
    });
  });
}
function fn_remainingDay() {
  var userEmail = $("#userEmail").data("email");
  $.ajax({
    type: "post",
    url: "/payment/remainingDay",
    data: "email=" + userEmail,
    dataType: "json",
    success: function (resData) {
      if (resData.remainingDay != null) {
        $("#remainingDay").text("이용권 종료일 : " + resData.remainingDay);
        $("#dDay").text("이용권 남은일수 : " + resData.dDay + "일");
      } else {
        $("#remainingDay").text("이용중인 이용권이 없습니다!");
      }
    },
    error: function (xqXHR) {
      console.log("실패!");
    },
  });
}

// 구현 : 전체검색 + 빈칸검색방지
function fn_totalSearch() {
  $("#totalSearch").click(function () {
    if ($("#query").val() == "") {
      alert("검색어를 입력하세요");
      event.preventDefault();
      return;
    }
    location.href =
      "/music/main/totalSearch?query=" +
      $("#query").val() +
      "&page=" +
      $("#layout_page").val();
  });
}

// 구현 : 랭킹
function fn_musicRanking() {
  var index;

  $.ajax({
    type: "get",
    url: "/music/ranking",
    dataType: "json",
    success: function (resData) {
      $.each(resData.rankingList, function (index, rank) {
        $('<li class="ranking_li">')
          .append(
            '<a class="ranking_link" id="ranking_link" href="/tune/inframe?musicNo=' +
              rank.musicNo +
              '">' +
              (index + 1) +
              "&nbsp;&nbsp;&nbsp;" +
              rank.musicTitle +
              "&nbsp;&nbsp;&nbsp;" +
              rank.userDTO.artist +
              "</a>"
          )
          .appendTo("#ranking_ol");
      });
    },
  });
}
