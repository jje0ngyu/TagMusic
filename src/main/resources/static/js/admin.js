/**
 * 
 */
 
 	// 전역변수
	var page = 1;
	var totalPage = 0;
	
	function fn_page_list(realPage){
		
		$.ajax({
			type : 'get',
			url : '/admin/user/list',
			data : 'page=' + realPage + "&list=nowUser",
			dataType : 'json',
			success : function(resData){
				
				$('#list_body').empty();
				
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
	                .append( $('<td>').append(user.snsType)) // 가입형태
	                .append( $('<td>').append(location))
	                .append( $('<td>').append(promotion))
					.appendTo('#list_body')
				});
				
				$('.list_page_footer').empty();
				$(resData.paging).appendTo('.list_page_footer');
				
			//	$('#didid').append('<input type="button" id="btn_sleepUser" value="휴면">');
				//$('#didid').append('<input type="button" id="btn_retireUser" value="탈퇴">');
				
				
			},
			error : function(jqXHR){
				console.log(jqXHR.status);
			}
		});
	};
 
 
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
 
 
 	function fn_user_search(realpage){
		 
			$.ajax({
				type : 'get',
				url : '/admin/user/search',
				data : $('#frm_search1').serialize()+'&page='+realpage,
				dataType : 'json',
				success : function(resData){
					$('#list_body').empty();
					$('#list_foot').empty();
					
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
	                .append( $('<td>').append(user.birthyear+user.birthday) ) 
	                .append( $('<td>').append(user.email) ) 
	                .append( $('<td>').append(user.mobile) ) 
					.append( $('<td>' +  moment(user.joinDate).format('YYYY.MM.DD hh:mm') + '</td>'))
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
				error : function(request,status,error){
					
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					alert('응?');
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
		
	   $('#btn_sleepUser').click(function(ev){
		   
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
	
	
	function fn_retireUser(){
		
	   $('#btn_retireUser').click(function(ev){
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
	   });
	}
	
	
	
	
	function fn_sleep_user_list(realPage){
		
		$('#list_sleep_user').click(function(){
			
			$.ajax({
			type : 'get',
			url : '/admin/user/list',
			data : 'page=' + realPage+ "&list=sleepUser",
			dataType : 'json',
			success : function(resData){
				
				$('#list_body').empty();
				
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
					
				
				
				
			},
			error : function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
		});
		
	}
		

 	function fn_retire_user_list(realPage){
		
		$('#list_retire_user').click(function(){
			
			$.ajax({
			type : 'get',
			url : '/admin/user/list',
			data : 'page=' + realPage+ "&list=retireuser",
			dataType : 'json',
			success : function(resData){
				
				$('#list_body').empty();
				
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
					
				
				
				
			},
			error : function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
		});
		
	}
 