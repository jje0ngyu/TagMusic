/**
 * 
 */
 
 	// checkbox 전체체크하면 개별체크 한꺼번에 됨
	function fn_checkAll(){
		$('#check_all').click(function(){
			$('.check_one').prop('checked', $(this).prop('checked'));
		});
	}
 
	//	개별체크 모두 되면 전체체크박스에 체크 들어감
	function fn_checkOne(){
		$(document).on('click', '.check_one', function(){
			let checkCount = 0;
			for(let i = 0; i < $('.check_one').length; i++){
				checkCount += $($('.check_one')[i]).prop('checked');
			}
			$('#check_all').prop('checked', $('.check_one').length == checkCount);
		});
	}
 
 	function fn_user_search1(realpage){
		 
			$.ajax({
				type : 'get',
				url : '/admin/user/search',
				data : $('#frm_search1').serialize()+'&page='+realpage,
				dataType : 'json',
				success : function(resData){
					
					
					console.log(resData.table);
					console.log(resData.userList);

										
					$('#list_body').empty();
					$('#list_foot').empty();
						
					if(resData.table == 'USERS'){
						
			            $.each(resData.userList, function(index, user) {
							
						var location;
						var promotion;
						
						switch(user.agreeCode){
						case 0 : 
							location = '';
							promotion = '';
							break;
						case 1 : 
							location = '';
							promotion = 'O';
							break;
						case 2 : 
							location = 'O';
							promotion = '';
							break;
						case 3 : 
							location = 'O';
							promotion = 'O';
							break;
						default : break;
						}
						
							$('<tr>')
							.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
							.append( $('<td>').append(user.userNo) )
							.append( $('<td>').append($('<img class="thum" src="'+user.profileImage+'"></img>')))
			                .append( $('<td>').append(user.name) )
			                .append( $('<td>').append(user.gender) )
			                .append( $('<td>').append(user.artist) )
			                .append( $('<td>').append(user.birthyear+user.birthday)) 
			                .append( $('<td>').append(user.email) ) 
			                .append( $('<td>').append(user.mobile) ) 
							.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
			                .append( $('<td>').append('')) 
			                .append( $('<td>').append(user.snsType)) // 가입형태
			                .append( $('<td>').append(location))
			                .append( $('<td>').append(promotion))
							.appendTo('#list_body')
						
		            	 });
					}else if (resData.table == 'SLEEP_USERS'){
			            $.each(resData.userList, function(index, user) {
							
						var location;
						var promotion;
						
						switch(user.agreeCode){
						case 0 : 
							location = '';
							promotion = '';
							break;
						case 1 : 
							location = '';
							promotion = 'O';
							break;
						case 2 : 
							location = 'O';
							promotion = '';
							break;
						case 3 : 
							location = 'O';
							promotion = 'O';
							break;
						default : break;
						}
						$('<tr>')
						.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
						.append( $('<td>').append(user.userNo) )
						.append( $('<td>').append($('<img class="thum" src="'+user.profileImage+'"></img>')))
		                .append( $('<td>').append(user.name) )
		                .append( $('<td>').append(user.gender) )
		                .append( $('<td>').append(user.artist) )
		                .append( $('<td>').append(user.birthyear+user.birthday)) 
		                .append( $('<td>').append(user.email) ) 
		                .append( $('<td>').append(user.mobile) ) 
						.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
						.append( $('<td>' +  moment(user.sleepDate).format('YYYY.MM.DD hh:mm') + '</td>'))
		                .append( $('<td>').append(user.snsType)) // 가입형태
		                .append( $('<td>').append(location))
		                .append( $('<td>').append(promotion))
						.appendTo('#list_body')
						
		            	 });
						
						
						
					}else{
						
			            $.each(resData.userList, function(index, user) {
							
						$('<tr>')
						.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
						.append( $('<td>').append(user.userNo) )
						.append( $('<td>').append(''))
		                .append( $('<td>').append('') )
		                .append( $('<td>').append('') )
		                .append( $('<td>').append(user.artist) )
		                .append( $('<td>').append('')) 
		                .append( $('<td>').append(user.email) ) 
		                .append( $('<td>').append('') ) 
		                .append( $('<td>').append('')) 
						.append( $('<td>' +  moment(user.retireDate).format('YYYY.MM.DD hh:mm') + '</td>'))
		                .append( $('<td>').append('')) // 가입형태
		                .append( $('<td>').append(''))
		                .append( $('<td>').append(''))
						.appendTo('#list_body')
						
		            	 });
						
					}
					
						
					$('.list_page_footer').empty();
						
	              	var pageUtil = resData.pageUtil
             		var paging = '';	
					// 이전 블록
           		    if(pageUtil.beginPage != 1){
              	  	  paging += '<a class="page_left" onclick="fn_user_search(' + (pageUtil.beginPage - 1) + ');">이전 ◀</a>'; 
              	    
             		}
             		// 페이지번호 : 현재 페이지는 링크가 없다.
              		for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++){
	              	    if(p == realpage){
							  paging += '<span class="paging">' + p + '</span>';
	              	    } else {
	                    	paging += '<a class="paging" onclick="fn_user_search('+ p +')">' + p + '</a>';
	                  	}
             		}
            		// 다음블록
             		if(pageUtil.endPage != pageUtil.totalPage){
                 		paging += '<a class="page_right" onclick="fn_user_search('+ (pageUtil.endPage + 1) +');">다음 ▶</a>';
             		}
               
               		$('.list_page_footer').append(paging);	
					
				},
				error : function(jqXHR){
					//request,status,error
					//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					alert('일치하는 회원 정보가 없습니다.');
				}
			});
	}
	
 	function fn_user_search2(realpage){
		 
			$.ajax({
				type : 'get',
				url : '/admin/user/search',
				data : $('#frm_search2').serialize()+'&page='+realpage,
				dataType : 'json',
				success : function(resData){
										
					$('#list_body').empty();
					$('#list_foot').empty();
						
					if(resData.table == 'USERS'){
						
			            $.each(resData.userList, function(index, user) {
							
						var location;
						var promotion;
						
						switch(user.agreeCode){
						case 0 : 
							location = '';
							promotion = '';
							break;
						case 1 : 
							location = '';
							promotion = 'O';
							break;
						case 2 : 
							location = 'O';
							promotion = '';
							break;
						case 3 : 
							location = 'O';
							promotion = 'O';
							break;
						default : break;
						}
						
							$('<tr>')
							.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
							.append( $('<td>').append(user.userNo) )
							.append( $('<td>').append($('<img class="thum" src="'+user.profileImage+'"></img>')))
			                .append( $('<td>').append(user.name) )
			                .append( $('<td>').append(user.gender) )
			                .append( $('<td>').append(user.artist) )
			                .append( $('<td>').append(user.birthyear+user.birthday)) 
			                .append( $('<td>').append(user.email) ) 
			                .append( $('<td>').append(user.mobile) ) 
							.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
			                .append( $('<td>').append('')) 
			                .append( $('<td>').append(user.snsType)) // 가입형태
			                .append( $('<td>').append(location))
			                .append( $('<td>').append(promotion))
							.appendTo('#list_body')
						
		            	 });
					}else if (resData.table == 'SLEEP_USERS'){
			            $.each(resData.userList, function(index, user) {
							
						var location;
						var promotion;
						
						switch(user.agreeCode){
						case 0 : 
							location = '';
							promotion = '';
							break;
						case 1 : 
							location = '';
							promotion = 'O';
							break;
						case 2 : 
							location = 'O';
							promotion = '';
							break;
						case 3 : 
							location = 'O';
							promotion = 'O';
							break;
						default : break;
						}
						$('<tr>')
						.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
						.append( $('<td>').append(user.userNo) )
						.append( $('<td>').append($('<img class="thum" src="'+user.profileImage+'"></img>')))
		                .append( $('<td>').append(user.name) )
		                .append( $('<td>').append(user.gender) )
		                .append( $('<td>').append(user.artist) )
		                .append( $('<td>').append(user.birthyear+user.birthday)) 
		                .append( $('<td>').append(user.email) ) 
		                .append( $('<td>').append(user.mobile) ) 
						.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
						.append( $('<td>' +  moment(user.sleepDate).format('YYYY.MM.DD hh:mm') + '</td>'))
		                .append( $('<td>').append(user.snsType)) // 가입형태
		                .append( $('<td>').append(location))
		                .append( $('<td>').append(promotion))
						.appendTo('#list_body')
						
		            	 });
						
						
						
					}else{
						
			            $.each(resData.userList, function(index, user) {
							
						$('<tr>')
						.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
						.append( $('<td>').append(user.userNo) )
						.append( $('<td>').append(''))
		                .append( $('<td>').append('') )
		                .append( $('<td>').append('') )
		                .append( $('<td>').append(user.artist) )
		                .append( $('<td>').append('')) 
		                .append( $('<td>').append(user.email) ) 
		                .append( $('<td>').append('') ) 
		                .append( $('<td>').append('')) 
						.append( $('<td>' +  moment(user.retireDate).format('YYYY.MM.DD hh:mm') + '</td>'))
		                .append( $('<td>').append('')) // 가입형태
		                .append( $('<td>').append(''))
		                .append( $('<td>').append(''))
						.appendTo('#list_body')
						
		            	 });
						
					}
					
						
					$('.list_page_footer').empty();
						
	              	var pageUtil = resData.pageUtil
             		var paging = '';	
					// 이전 블록
           		    if(pageUtil.beginPage != 1){
              	  	  paging += '<a class="page_left" onclick="fn_user_search(' + (pageUtil.beginPage - 1) + ');">이전 ◀</a>'; 
              	    
             		}
             		// 페이지번호 : 현재 페이지는 링크가 없다.
              		for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++){
	              	    if(p == realpage){
							  paging += '<span class="paging">' + p + '</span>';
	              	    } else {
	                    	paging += '<a class="paging" onclick="fn_user_search('+ p +')">' + p + '</a>';
	                  	}
             		}
            		// 다음블록
             		if(pageUtil.endPage != pageUtil.totalPage){
                 		paging += '<a class="page_right" onclick="fn_user_search('+ (pageUtil.endPage + 1) +');">다음 ▶</a>';
             		}
               
               		$('.list_page_footer').append(paging);	
					
				},
				error : function(jqXHR){
					//request,status,error
					//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					alert('일치하는 회원 정보가 없습니다.');
				}
			});
	}
	
	
	function fn_autoComplete(){
		$('#query').keyup(function(){
			
			alert('안되는이유가 머임');
			
				if($('#column1').val() == 'USER_NO'){
					return;
				}
				if($(query).val() == ''){
					return;
				}
				$('#auto_complete').empty();
				$.ajax({
					/* 요청 */
					type: 'get',
					url: '/user/autoComplete',
					data: $('#frm_search1').serialize(),
					/* 응답 */
					dataType: 'json',
					success: function(resData){
						
						alert('왜안되지');
						
						if(resData.status == 200){
							$.each(resData.list, function(i, emp){
								$('#auto_complete')
								.append($('<option>').val(emp[resData.column]));
							});
						}
					}
				});
		});
	}

	
	
	function fn_sleepUser(){
		
	   $(document).on('click', '#btn_sleepUser', function(ev){
		   
	      if($('input[name="userCheck"]:checked').val() == 1){
	         alert('관리자는 휴면처리 할 수 없습니다.');
	         ev.preventDefault();
	         return;
	      }
	   
	      var userArray = [];
	       $('input[name="userCheck"]:checked').each(function (index) {
	          userArray.push($(this).val());
	       });
	       
	       $.ajax({
	          type: 'post',
	          url: '/admin/sleepUser',
	          data : {
	             "userNo" : userArray
	          },
	          dataType: 'json',
	          success: function(resData){
	             if(resData.isSleepUser >= 1){
	                alert(resData.isSleepUser +"명을 휴면처리 했습니다.");
	             } else {
	                alert("휴면처리 실패");
	             }
	             fn_page_list(1);
	          }
	       });
	   });
	}
	
	
	
	
	function fn_sleep_to_now_user(){
		
	   $(document).on('click', '#btn_sleep_to_now_user', function(ev){
		   
	      if($('input[name="userCheck"]:checked').val() == 1){
	         alert('관리자는 상태를 변경할 수 없습니다.');
	         ev.preventDefault();
	         return;
	      }
	   
	      var userArray = [];
	       $('input[name="userCheck"]:checked').each(function (index) {
	          userArray.push($(this).val());
	       });
	       
	       $.ajax({
	          type: 'post',
	          url: '/admin/sleepUser',
	          data : {
	             "userNo" : userArray
	          },
	          dataType: 'json',
	          success: function(resData){
	             if(resData.isSleepUser >= 1){
	                alert(resData.isSleepUser +"명을 정상처리 했습니다.");
	             } else {
	                alert("정상처리 실패");
	             }
	             fn_sleep_user_list(1);
	          }
	       });
	   });
	}
	
	
	
	
	
	
	
	function fn_sleep_user_list(realPage){
		
		//$('#list_sleep_user').click(function(){
			
			$.ajax({
			type : 'get',
			url : '/admin/user/list',
			data : 'page=' + realPage+ "&list=sleepUser",
			dataType : 'json',
			success : function(resData){
				
				$('#various_functions').empty();
				$('#various_functions').append($('<input type="button" id="btn_sleep_to_now_user" value="현재 유저로 변경하기">'))
				$('#list_body').empty();
				$('#change_Date').text('휴면일');
				
				$('#column1').empty();
				$('#column1').append($('<option value="">:::선택:::</option>'))
				.append($('<option value="USER_NO">회원번호</option>'))
				.append($('<option value="NAME">이름</option>'))
				.append($('<option value="GENDER">성별</option>'))
				.append($('<option value="ARTIST">아티스트명</option>'))
				.append($('<option value="EMAIL">이메일</option>'))
				.append($('<option value="MOBILE">핸드폰번호</option>'))
				.append($('<option value="SNS_TYPE">가입형태</option>'));
			
				$('#column2').empty();
				$('#column2').append($('<option value="">:::선택:::</option>'))
				.append($('<option value="JOIN_DATE">가입일</option>'))
				.append($('<option value="RETIRE_DATE">휴면일</option>'))
				
				$.each(resData.userList, function(i, user) {
					
					var location;
					var promotion;
					
					switch(user.agreeCode){
					case 0 : 
						location = '';
						promotion = '';
						break;
					case 1 : 
						location = '';
						promotion = 'O';
						break;
					case 2 : 
						location = 'O';
						promotion = '';
						break;
					case 3 : 
						location = 'O';
						promotion = 'O';
						break;
					default : break;
					}
					
					$('<tr>')
					.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
					.append( $('<td>').append(user.userNo) )
					.append( $('<td>').append($('<img class="thum" src="'+user.profileImage+'"></img>')))
	                .append( $('<td>').append(user.name) )
	                .append( $('<td>').append(user.gender) )
	                .append( $('<td>').append(user.artist) )
	                .append( $('<td>').append(user.birthyear+user.birthday)) 
	                .append( $('<td>').append(user.email) ) 
	                .append( $('<td>').append(user.mobile) ) 
					.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
					.append( $('<td>' +  moment(user.sleepDate).format('YYYY.MM.DD hh:mm') + '</td>'))
	                .append( $('<td>').append(user.snsType)) // 가입형태
	                .append( $('<td>').append(location))
	                .append( $('<td>').append(promotion))
					.appendTo('#list_body')
				});
				
				$('.list_page_footer').empty();
					
              	var pageUtil = resData.pageUtil
         		var paging = '';	
				// 이전 블록
       		    if(pageUtil.beginPage != 1){
          	  	  paging += '<a class="page_left" onclick="fn_sleep_user_list(' + (pageUtil.beginPage - 1) + ');">이전 ◀</a>'; 
          	    
         		}
         		// 페이지번호 : 현재 페이지는 링크가 없다.
          		for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++){
              	    if(p == realPage){
						  paging += '<span class="paging">' + p + '</span>';
              	    } else {
                    	paging += '<a class="paging" onclick="fn_sleep_user_list('+ p +')">' + p + '</a>';
                  	}
         		}
        		// 다음블록
         		if(pageUtil.endPage != pageUtil.totalPage){
             		paging += '<a class="page_right" onclick="fn_sleep_user_list('+ (pageUtil.endPage + 1) +');">다음 ▶</a>';
         		}
           
           		$('.list_page_footer').append(paging);	
				$('#search_for_table').attr('value', 'SLEEP_USERS');	
			},
			error : function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
		//});
	}
		

 	function fn_retire_user_list(realPage){
		 
		//$('#list_retire_user').click(function(){
			
			$.ajax({
			type : 'get',
			url : '/admin/user/list',
			data : 'page=' + realPage+ "&list=retireuser",
			dataType : 'json',
			success : function(resData){
				
				$('#various_functions').empty();
				
				$('#list_body').empty();
				
				//$('#joinDate').after($('<td>').append('<span>').text('휴면일'));
				$('#change_Date').text('탈퇴일');
				
				$('#column1').empty();
				$('#column1').append($('<option value="">:::선택:::</option>'))
				.append($('<option value="USER_NO">회원번호</option>'))
				.append($('<option value="ARTIST">아티스트명</option>'))
				.append($('<option value="EMAIL">이메일</option>'))
				
				$('#column2').empty();
				$('#column2').append($('<option value="">:::선택:::</option>'))
				.append($('<option value="RETIRE_DATE">탈퇴일</option>'))
				//.append($('<option value="JOIN_DATE">생일</option>'))
				
				$.each(resData.userList, function(i, user) {
					
					$('<tr>')
					.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
					.append( $('<td>').append(user.userNo) )
					.append( $('<td>').append(''))
	                .append( $('<td>').append('') )
	                .append( $('<td>').append('') )
	                .append( $('<td>').append(user.artist) )
	                .append( $('<td>').append('')) 
	                .append( $('<td>').append(user.email) ) 
	                .append( $('<td>').append('') ) 
	                .append( $('<td>').append('')) 
					.append( $('<td>' +  moment(user.retireDate).format('YYYY.MM.DD hh:mm') + '</td>'))
	                .append( $('<td>').append('')) // 가입형태
	                .append( $('<td>').append(''))
	                .append( $('<td>').append(''))
					.appendTo('#list_body')
				});
				
				$('.list_page_footer').empty();
					
              	var pageUtil = resData.pageUtil
         		var paging = '';	
				// 이전 블록
       		    if(pageUtil.beginPage != 1){
          	  	  paging += '<a class="page_left" onclick="fn_retire_user_list(' + (pageUtil.beginPage - 1) + ');">이전 ◀</a>'; 
          	    
         		}
         		// 페이지번호 : 현재 페이지는 링크가 없다.
          		for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++){
              	    if(p == realPage){
						  paging += '<span class="paging">' + p + '</span>';
              	    } else {
                    	paging += '<a class="paging" onclick="fn_retire_user_list('+ p +')">' + p + '</a>';
                  	}
         		}
        		// 다음블록
         		if(pageUtil.endPage != pageUtil.totalPage){
             		paging += '<a class="page_right" onclick="fn_retire_user_list('+ (pageUtil.endPage + 1) +');">다음 ▶</a>';
         		}
           
           		$('.list_page_footer').append(paging);	
					
				$('#search_for_table').attr('value', 'RETIRE_USERS');	
			},
			error : function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
		//});
	}
	
		function fn_page_list(realPage){
		
		$.ajax({
			type : 'get',
			url : '/admin/user/list',
			data : 'page=' + realPage + "&list=nowUser",
			dataType : 'json',
			success : function(resData){
				
				$('#list_body').empty();
				
				$('#change_Date').text('');
				
				$('#column1').empty();
				
				$('#column1').append($('<option value="">:::선택:::</option>'))
							.append($('<option value="USER_NO">회원번호</option>'))
							.append($('<option value="NAME">이름</option>'))
							.append($('<option value="GENDER">성별</option>'))
							.append($('<option value="ARTIST">아티스트명</option>'))
							.append($('<option value="EMAIL">이메일</option>'))
							.append($('<option value="MOBILE">핸드폰번호</option>'))
							.append($('<option value="SNS_TYPE">가입형태</option>'));
				
				$('#column2').empty();
				$('#column2').append($('<option value="">:::선택:::</option>'))
				.append($('<option value="JOIN_DATE">가입일</option>'));
				//.append($('<option value="RETIRE_DATE">생일</option>'))
				
				$('#various_functions').empty();
				$('#various_functions').append($('<input type="button" id="btn_retireUser" value="탈퇴">'))
										.append($('<input type="button" id="btn_sleepUser" value="휴면">'));
				
				$.each(resData.userList, function(i, user) {
					
					var location;
					var promotion;
					
					switch(user.agreeCode){
					case 0 : 
						location = '';
						promotion = '';
						break;
					case 1 : 
						location = '';
						promotion = 'O';
						break;
					case 2 : 
						location = 'O';
						promotion = '';
						break;
					case 3 : 
						location = 'O';
						promotion = 'O';
						break;
					default : break;
					}
					
					$('<tr>')
					.append( $('<td>').append($('<input class="check_one" type="checkbox" id="userCheck" name="userCheck" value="'+ user.userNo +'">')))
					.append( $('<td>').append(user.userNo) )
					.append( $('<td>').append($('<img class="thum" src="'+user.profileImage+'"></img>')))
	                .append( $('<td>').append(user.name) )
	                .append( $('<td>').append(user.gender) )
	                .append( $('<td>').append(user.artist) )
	                .append( $('<td>').append(user.birthyear+user.birthday)) 
	                .append( $('<td>').append(user.email) ) 
	                .append( $('<td>').append(user.mobile) ) 
					.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
	                .append( $('<td>').append('')) 
	                .append( $('<td>').append(user.snsType)) // 가입형태
	                .append( $('<td>').append(location))
	                .append( $('<td>').append(promotion))
					.appendTo('#list_body')
				});
				
				$('.list_page_footer').empty();
				$(resData.paging).appendTo('.list_page_footer');
				
				$('#search_for_table').attr('value', 'USERS');
				
			},
			error : function(jqXHR){
				console.log(jqXHR.status);
			}
		});
	}
	
	
	fn_page_list(1);
	
	function fn_retireUser(){
		
		$(document).on('click', '#btn_retireUser', function(){
	  	//$('#btn_retireUser').click(function(ev){
	      if($('input[name="userCheck"]:checked').val() == 1){
	         alert('관리자는 탈퇴할 수 없습니다.');
	         ev.preventDefault();
	         return;
	      }
	   
	      var userArray = [];
	       $('input[name="userCheck"]:checked').each(function (index) {
	          userArray.push($(this).val());
	       });
	       
	       $.ajax({
	          type: 'post',
	          url: '/admin/retireUser',
	          data : {
	             "userNo" : userArray
	          },
	          dataType: 'json',
	          success: function(resData){

	             if(resData.isRemove >= 1){
	                alert(resData.isRemove +"명을 강제탈퇴 했습니다.");
	             } else {
	                alert("탈퇴처리 실패");
	             }
	             fn_page_list(1);
	          }
	       });
	   //});
			});
	}
	
	
	fn_retireUser();
	fn_sleepUser();
	