
$(document).ready(function(){
	var userName = $('#userName').data('name');
	//fn_userLog();
	fn_logRemove();
	if(userName != null){
		fn_userLog();
	} else {
		fn_isNotUser();
	}
	
	$('#pass_log').click(function(){
		fn_userLog();
		$('.tab_payment').removeClass('tab_box_click');
		$(this).addClass('tab_box_click');
	})
	
	$('#purchased_pass').click(function(){
		fn_userBuyLog();
		$('.tab_payment').removeClass('tab_box_click');
		$(this).addClass('tab_box_click');
	})
	
	$('#gifted_pass').click(function(){
		fn_userGiftLog();
		$('.tab_payment').removeClass('tab_box_click');
		$(this).addClass('tab_box_click');
	})
	
 });
function fn_userLog(){
	var userEmail = $('#userEmail').data('email');
	var userName = $('#userName').data('name');
	$.ajax({
		type: 'post',
		url: '/payment/buyLogList',
		data: 'email=' + userEmail + '&page=' + $('#page').val(),
		dataType: 'json',
		success: function(resData){
			console.log(resData.logCount);
			$('#list_head').empty();
		    $('#list_body').empty();
		    $('#list_foot').empty();
		    $('#logCountDiv').empty();
		    $('#logCountDiv').append('전체 결제건 ' + resData.logCount +'개');
		    
		    $('<tr>')
		    	.append($('<th>').text("주문번호"))	
		    	.append($('<th>').text("이용권이름"))
		    	.append($('<th>').text("결제날짜(시간)"))
		    	.append($('<th>').text("결제방식"))
		    	.append($('<th>').text("결제가격"))
		    	.append($('<th>').append('<input type="button" value="선택 삭제" id="btn_logRemove" class="btn_logRemove">'))
		    	.appendTo('#list_head');
		    if(resData.logList.length == 0){
		    	$('<tr>')
		    	.append( $('<td colspan="6">').text('결제 내역이 없습니다!'))
		    	.appendTo('#list_body');
		  	}
		    
			$.each(resData.logList, function(i, buyLog){
				
				const logDate = moment(buyLog.payLogDate).format('YYYY년 MM월 DD일 (HH시 mm분)');
				$('<tr>')
				.append( $('<td>').text(buyLog.payLogNo))
				//.append( $('<td>').text(buyLog.payLogName.substr(0, 6)))
				.append( $('<td>').text(buyLog.payLogName))
				.append( $('<td>').text(logDate))
				.append( $('<td>').text(buyLog.payLogPg))
				.append( $('<td>').text('₩' + buyLog.payLogPrice))
				.append($('<td>').append($('<input type="checkbox" name="logCheck" value="'+ buyLog.payLogNo +'">')))
				.appendTo('#list_body');
			});
				
				
		}
		
	});
}
function fn_userBuyLog(){
	var userEmail = $('#userEmail').data('email');
	var userName = $('#userName').data('name');
	$.ajax({
		type: 'post',
		url: '/payment/buyLogList',
		data: 'email=' + userEmail + '&page=' + $('#page').val(),
		dataType: 'json',
		success: function(resData){
			console.log(resData.logCount);
			$('#list_head').empty();
		    $('#list_body').empty();
		    $('#list_foot').empty();
		    $('#logCountDiv').empty();
		    
		    
		    $('<tr>')
		    	.append($('<th>').text("주문번호"))	
		    	.append($('<th>').text("이용권이름"))
		    	.append($('<th>').text("결제날짜(시간)"))
		    	.append($('<th>').text("결제방식"))
		    	.append($('<th>').text("결제가격"))
		    	.append($('<th>').append('<input type="button" value="선택 삭제" id="btn_logRemove" class="btn_logRemove">'))
		    	.appendTo('#list_head');
		    
		    var count = 0;
			$.each(resData.logList, function(i, buyLog){
				if (buyLog.payLogName.endsWith(')') == false) {
				count += 1;
				 
				
				const logDate = moment(buyLog.payLogDate).format('YYYY년 MM월 DD일 (HH시 mm분)');
				$('<tr>')
				.append( $('<td>').text(buyLog.payLogNo))
				//.append( $('<td>').text(buyLog.payLogName.substr(0, 6)))
				.append( $('<td>').text(buyLog.payLogName))
				.append( $('<td>').text(logDate))
				.append( $('<td>').text(buyLog.payLogPg))
				.append( $('<td>').text('₩' + buyLog.payLogPrice))
				.append($('<td>').append($('<input type="checkbox" name="logCheck" value="'+ buyLog.payLogNo +'">')))
				.appendTo('#list_body');
				}
				
			});
			if(count == 0){
		    	$('<tr>')
		    	.append( $('<td colspan="6">').text('구매 내역이 없습니다!'))
		    	.appendTo('#list_body');
		  	}
			$('#logCountDiv').append('구매 결제건 ' + count +'개');
				
		}
		
	});
}
function fn_userGiftLog(){
	var userEmail = $('#userEmail').data('email');
	var userName = $('#userName').data('name');
	$.ajax({
		type: 'post',
		url: '/payment/buyLogList',
		data: 'email=' + userEmail + '&page=' + $('#page').val(),
		dataType: 'json',
		success: function(resData){
			console.log(resData.logCount);
			$('#list_head').empty();
		    $('#list_body').empty();
		    $('#list_foot').empty();
		    $('#logCountDiv').empty();
		    
		    
		    $('<tr>')
		    	.append($('<th>').text("주문번호"))	
		    	.append($('<th>').text("이용권이름"))
		    	.append($('<th>').text("결제날짜(시간)"))
		    	.append($('<th>').text("결제방식"))
		    	.append($('<th>').text("결제가격"))
		    	.append($('<th>').append('<input type="button" value="선택 삭제" id="btn_logRemove" class="btn_logRemove">'))
		    	.appendTo('#list_head');
		    var count = 0;
			$.each(resData.logList, function(i, buyLog){
				
				if (buyLog.payLogName.endsWith(')')) {
					
				count += 1;
				
				
				const logDate = moment(buyLog.payLogDate).format('YYYY년 MM월 DD일 (HH시 mm분)');
				$('<tr>')
				.append( $('<td>').text(buyLog.payLogNo))
				//.append( $('<td>').text(buyLog.payLogName.substr(0, 6)))
				.append( $('<td>').text(buyLog.payLogName))
				.append( $('<td>').text(logDate))
				.append( $('<td>').text(buyLog.payLogPg))
				.append( $('<td>').text('₩' + buyLog.payLogPrice))
				.append($('<td>').append($('<input type="checkbox" name="logCheck" value="'+ buyLog.payLogNo +'">')))
				.appendTo('#list_body');
				}
				
			});
			if(count == 0){
		    	$('<tr>')
		    	.append( $('<td colspan="6">').text('선물 내역이 없습니다!'))
		    	.appendTo('#list_body');
		  	}
			$('#logCountDiv').append('선물 결제건 ' + count +'개');
				
		}
		
	});
}
function fn_logRemove(){
	$(document).on('click', '.btn_logRemove', function(){
		var payLogNo = [];
		if( $('input[name="logCheck"]:checked').length == 0){
			alert('삭제할 데이터를 선택해주세요');
		} else {
			if(confirm('결제이력 삭제시 환불요청에 어려움이 있을 수 있습니다. 정말로 삭제하시겠습니까?')){
				$('input[name="logCheck"]:checked').each(function (index) {
			    	payLogNo.push($(this).val());
			    });
			    $.ajax({
			    	type: 'post',
			    	url: '/payment/logRemove',
			    	data: {
			    		"payLogNo" : payLogNo
			    	},
			    	dataType: 'json',
			    	success: function(resData){
			    		alert(resData.result + '개의 결제 이력을 삭제하였습니다.');
			    		fn_userLog();
			    		$('.tab_payment').removeClass('tab_box_click');
						$('#pass_log').addClass('tab_box_click');
			    	}
			    });
			} else {
				$('input[name="logCheck"]').prop('checked', false);
			}
		}
	});
}
function fn_userLogSelect(){
	$('#pass_log').click(function(){
		$("#column").val("").prop("selected", true);
		fn_userLog();
	});
	$('#purchased_pass').click(function(){
		$("#column").val("BUY").prop("selected", true);
	});
	$('#gifted_pass').click(function(){
		$("#column").val("GIFT").prop("selected", true);
	});
}