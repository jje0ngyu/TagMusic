
$(document).ready(function(){
	fn_couponInput();
	var userName = $('#userName').data('name');
	if(userName != null){
		fn_passList();
	} else {
		fn_isNotUser();
	}
	fn_init();

	$('#gift_close').click(function(){
		$('.backlayer').addClass('blind');
		$('.gift_session').addClass('blind');
		$('.gift_box_div').addClass('blind');
		$('#getRecipientEmail').prop('readonly', false);
	});
	$('#gift_cancle').click(function(){
		$('.backlayer').addClass('blind');
		$('.gift_session').addClass('blind');
		$('.gift_box_div').addClass('blind');
		$('#getRecipientEmail').prop('readonly', false);
		$('#gift_close').removeClass('blind');
	});



	var userEmail = $('#userEmail').data('email');
	
	$('#send_gift').click(function(ev){
		$('#recipientInfoBox').text('');
		var recipientEmail = $('#getRecipientEmail').val();
		let regEmail = /^[0-9a-zA-Z][0-9a-zA-Z-_]{3,29}@[a-z]{1,10}[\.][a-z\.]{2,18}$/;
		if(regEmail.test(recipientEmail) == false) {
			$('#recipientInfoBox').text('올바른 이메일 형식으로 작성해주세요.');
			ev.preventDefault();
			return;
		}
		if(recipientEmail == userEmail){
			$('#recipientInfoBox').text('');
			$('#recipientInfoBox').text('본인에게는 선물 할 수 없습니다!!');
			ev.preventDefault();
			return;
		}
		if(recipientEmail == 'admin@web.com'){
			$('#recipientInfoBox').text('');
			$('#recipientInfoBox').text('선물할 수 있는 대상이 아닙니다.');
			ev.preventDefault();
			return;
		}
		$.ajax({
			type: 'post',
			url: '/payment/recipient',
			data: 'email=' + recipientEmail, 
			dataType: 'json',
			success: function(resData){
				$('#recipientInfoBox').text('');
				if(resData.result == null){
					$('#recipientInfoBox').text('해당 유저는 존재하지않습니다.');
				} else {
					$('#getRecipientEmail').prop('readonly', true);
					$('#recipientInfoBox').append('\''+ recipientEmail + '(' + resData.result.artist + ')\'에게 <br>보내겠습니까?');
					$('#gift_close').addClass('blind');
					$('#gift_submit').data('artist', resData.result.artist);
					$('.gift_box_div').removeClass('blind');
				}
			}
		});
	});




		$('#payBEST').click(function(){
			$('.tab_payment').removeClass('tab_box_click');
			$(this).addClass('tab_box_click');
			$('#buyPageDiv').empty();
			var userName = $('#userName').data('name');
			if(userName != null){
				fn_passList();
			} else {
				fn_isNotUser();
			}
			fn_init();
		});
		$('#passGiftList').click(function(){
			$('.tab_payment').removeClass('tab_box_click');
			$(this).addClass('tab_box_click');
			$('#buyPageDiv').empty();
			fn_passGiftList();
		});
		$('#payAllList').click(function(){
			$('.tab_payment').removeClass('tab_box_click');
			$(this).addClass('tab_box_click');
			$('#buyPageDiv').empty();
		});
		$('#useCoupon').click(function(){
			$('.tab_payment').removeClass('tab_box_click');
			$(this).addClass('tab_box_click');
			$('#buyPageDiv').empty();
			var userName = $('#userName').data('name');
			var txt = '';
			txt += '<div class="pass_box">';
			txt += '<div class="pass_box_left">';
			txt += '<div class="pass_name">쿠폰번호<br/>입력</div>';
			txt += '<div class="pass_msg_price">';
			txt += '<div class="pass_msg"><input type="text" class="coupon_input" id="coupon_input" maxlength="16" placeholder="이용하실 쿠폰 16자리를 입력해주세요!"></div>';
			txt += '<div class="pass_price"></div>';
			txt += '</div>';
			txt += '</div>';
			txt += '<div class="pass_box_right">';
			if(userName != null){
				txt += '<input id="couponRegister" type="button" value="쿠폰등록"  onclick="fn_registerCoupon()">';
			} else {
				txt += '<input id="couponRegister" type="button" value="쿠폰등록" onclick="fn_login()">';
			}
			txt += '</div>';
			txt += '</div>';
			
			txt += '<div>';
			txt += '<li>쿠폰 번호는 대문자와 숫자로 이루어져 있습니다.</li>';
			txt += '<li>쿠폰에 표기된 유효기간을 꼭 확인해주세요.</li>';
			txt += '</div>';
			
			$('#buyPageDiv').append(txt);
			
			
		});

	
	    // ID가 message에서 엔터키를 누를 경우
	    
		        
	    
	
	function fn_couponInput(){
		$(document).on('blur keyup', '#coupon_input', function(key){
			var userName = $('#userName').data('name');
			if (key.keyCode == 13) {
				if(userName != null){
					fn_registerCoupon();
				} else {
					fn_login();
				}
	        } 
			var str = $(this).val();
			var reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
			var reg2 = /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
			var regResult = str.replace(reg,'');
			var regResult = regResult.replace(reg2,'');
			$(this).val(regResult.toUpperCase()); 
		});
		
		
	}
	



});
function fn_init(){
	$('.backlayer').addClass('blind');
	$('.gift_session').addClass('blind');
	$('.gift_box_div').addClass('blind');
	$('#getRecipientEmail').prop('readonly', false);
}
function fn_isNotUser(){
	$.ajax({
		type: 'post',
		url: '/pass/list',
		dataType: 'json',
		success: function(resData){
			var txt = '';
			$.each(resData.passList, function(i, pass){
				txt += '<div class="pass_box">';
				txt += '<div class="pass_box_left">';
				txt += '<div class="pass_name">'+pass.passName+'</div>';
				txt += '<div class="pass_msg_price">';
				txt += '<div class="pass_msg">무제한 듣기 + 무제한 다운로드</div>';
				txt += '<div class="pass_price">'+pass.passPrice+' 원!</div>';
				txt += '</div>';
				txt += '</div>';
				txt += '<div class="pass_box_right">';
				txt += '<input type="button" value="결제하기" onclick="fn_login()">';
				txt += '</div>';
				txt += '</div>';
			});
			$('#buyPageDiv').append(txt);
		}
	});
}
function fn_passList(){
	$.ajax({
		type: 'post',
		url: '/pass/list',
		dataType: 'json',
		success: function(resData){
			var txt = '';
			$.each(resData.passList, function(i, pass){
				txt += '<div class="pass_box">';
				txt += '<div class="pass_box_left">';
				txt += '<div class="pass_name">'+pass.passName+'</div>';
				txt += '<div class="pass_msg_price">';
				txt += '<div class="pass_msg">무제한 듣기 + 무제한 다운로드</div>';
				txt += '<div class="pass_price">'+pass.passPrice+' 원!</div>';
				txt += '</div>';
				txt += '</div>';
				txt += '<div class="pass_box_right">';
					txt += '<input type="button" value="결제하기" data-ticket="'+ pass.passName+'" data-passno="'+pass.passNo+'" data-pg="inicis통합결제" data-amount="'+pass.passPrice+'" onclick="fn_buyTicket(this)">';
				txt += '</div>';
				txt += '</div>';
			});
			
			$('#buyPageDiv').append(txt);
		}
	});
}
function fn_passGiftList(){
	var userName = $('#userName').data('name');
	$.ajax({
		type: 'post',
		url: '/pass/list',
		dataType: 'json',
		success: function(resData){
			var txt = '';
			$.each(resData.passList, function(i, pass){
				 if(pass.passName == "30일이용권"){
					 txt += '<div class="pass_box">';
					txt += '<div class="pass_box_left">';
					txt += '<div class="pass_name">'+pass.passName+'</div>';
					txt += '<div class="pass_msg_price">';
					txt += '<div class="pass_msg">무제한 듣기 + 무제한 다운로드</div>';
					txt += '<div class="pass_price">'+pass.passPrice+' 원!</div>';
					txt += '</div>';
					txt += '</div>';
					txt += '<div class="pass_box_right">';
					if(userName != null){
						txt += '<input class="btn_gift_30days" type="button" value="선물하기" data-if="0" data-ticket="'+ pass.passName + '" data-passno="'+pass.passNo+'" data-pg="inicis통합결제" data-amount="'+pass.passPrice+'" onclick="fn_presentTicket(this)">';
					} else {
						txt += '<input class="btn_gift_30days" type="button" value="선물하기" onclick="fn_login()">';
						
					}
					txt += '</div>';
					txt += '</div>';
				} 
			});
			
			$('#buyPageDiv').append(txt);
		}
	});
}


function fn_login(){
	
	 if(confirm('아직 로그인을 안하셨네요. 로그인 페이지로 이동하겠습니까?')){
		$('#frm_login').submit();
	}
}

function fn_registerCoupon(){
    
	var couponCode = $('#coupon_input').val();
	var userEmail = $('#userEmail').data('email');
	
	if(couponCode.length < 16){
		alert('정확한 쿠폰번호를 입력해주세요');
		return;
	}
	 $.ajax({
		type: 'post',
		url: '/payment/coupon',
		data: 'email=' + userEmail + '&couponCode=' + couponCode,
		dataType: 'json',
		success: function(resData){
			switch(resData.result){
			case 'null': alert('해당 쿠폰은 존재하지 않습니다.'); break;
			case 'duplication': alert('해당 쿠폰은 이미 사용한 쿠폰입니다.'); break;
			case 'success' : alert('쿠폰이 등록되었습니다.'); break;
			case 'extension': alert('15일 연장되었습니다.'); break;
			case 'false': alert('실패'); break;
			}
			fn_remainingDay();
			fn_alarm();
		}
	});  
	
	
	
}

function fn_buyTicket(e){
	
	var musicAmount = $(e).data('amount');
	var paymentPg = $(e).data('pg');
	var ticketName = $(e).data('ticket');
	var passNo = $(e).data('passno');
	var userName = $('#userName').data('name');
	var userEmail = $('#userEmail').data('email');
	var userMobile = $('#userMobile').data('mobile');
	var userAddr = '';
	userAddr += $('#userAddr1').data('addr');
	userAddr += $('#userAddr2').data('addr');
	userAddr += $('#userAddr3').data('addr');
	userAddr += $('#userAddr4').data('addr');
	var userPostcode = $('#postcode').data('postcode');
		
	var IMP = window.IMP; 
	IMP.init('imp66111374');
	
	var merchant_uid = 'TagMusic_' + new Date().getTime();
	IMP.request_pay({ 
		pg: "html5_inicis",
		pay_method: 'card',
		merchant_uid: merchant_uid,
		name: ticketName, 
		//name: '노래제목:'+firstCartMusicName+' 외'+ (muprice-1) +'곡',
		amount: musicAmount,
		buyer_email: userEmail,
		buyer_name: userName,
		buyer_tel: userMobile,
		buyer_addr: '서울시',
		buyer_postcode: '123-111',
		m_redirect_url: 'https://www.yourdomain.com/payments/complete'
		}, function (rsp) {
			if (rsp.success) {
				
				 $.ajax({
					type: 'post',
					url: '/payment/thirtyDay',
					data: 'email=' + userEmail + '&price=' + musicAmount + '&passNo=' + passNo + '&payPg=' + paymentPg + '&ticketName=' + ticketName + '&merchantUid=' + merchant_uid,
					dataType: 'json',
					success: function(resData){
						var msg = '';
						switch(resData.result){
						case 'success' : msg += '이용권 구매가 완료되었습니다.'; break;
						case 'extension' : msg += '이용권 기간이 30일 연장되었습니다.'; break;
						}
						alert(msg);
						fn_remainingDay();
						fn_alarm();
					}
					
				}); 
				 
			} else {
			var msg = '';
			msg += rsp.error_msg;
			alert(msg)
		}
			
	});
	
}
function fn_presentTicket(e){
	$('#recipientInfoBox').text('');
	var recipientEmail = $('#getRecipientEmail').val();
	$('#getRecipientEmail').val('');
	$('.backlayer').removeClass('blind');
	$('.gift_session').removeClass('blind');
	var userNickName = $(e).data('artist');
	var ticketName = $('.btn_gift_30days').data('ticket');
	ticketName += '(TO.'+ userNickName +')';
		
		var musicAmount = $('.btn_gift_30days').data('amount');
		var paymentPg = $('.btn_gift_30days').data('pg');
		
		var passNo = $('.btn_gift_30days').data('passno');
		var userName = $('#userName').data('name');
		var userEmail = $('#userEmail').data('email');
		var userMobile = $('#userMobile').data('mobile');
		var userAddr = '';
		userAddr += $('#userAddr1').data('addr');
		userAddr += $('#userAddr2').data('addr');
		userAddr += $('#userAddr3').data('addr');
		userAddr += $('#userAddr4').data('addr');
		var userPostcode = $('#postcode').data('postcode');
			
		var IMP = window.IMP; 
	
		
		IMP.init('imp66111374');//imp72345478
		var merchant_uid = 'TagMusic_' + new Date().getTime();
		if($(e).data('if') == '1'){
		IMP.request_pay({ 
			pg: "html5_inicis",
			pay_method: 'card',
			merchant_uid: merchant_uid,
			name: ticketName, 
			//name: '노래제목:'+firstCartMusicName+' 외'+ (muprice-1) +'곡',
			amount: musicAmount,
			buyer_email: userEmail,
			buyer_name: userName,
			buyer_tel: userMobile,
			buyer_addr: '서울시',
			buyer_postcode: '	-111',
			m_redirect_url: 'https://www.yourdomain.com/payments/complete'
			}, function (rsp) {
				if (rsp.success) {
					 $.ajax({
						type: 'post',
						url: '/payment/present/thirtyDay',
						data: 'email=' + recipientEmail  +'&sessionEmail=' + userEmail + '&price=' + musicAmount + '&passNo=' + passNo + '&payPg=' + paymentPg + '&ticketName=' + ticketName + '&merchantUid=' + merchant_uid,
						dataType: 'json',
						success: function(resData){
							var msg = '';
							console.log(resData);
							if(resData.result == 1){
								msg += '이용권 선물이 완료되었습니다.';
								$('#gift_close').removeClass('blind');
							} else {
								msg += '이용권 선물을 실패하였습니다..';
							}
							alert(msg);
							fn_remainingDay();
							fn_init();
							fn_alarm();
						}
					}); 
				} else {
				var msg = '';
				msg += rsp.error_msg;
				alert(msg)
			}
		});
	}
}
