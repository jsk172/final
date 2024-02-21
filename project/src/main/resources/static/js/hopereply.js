// 댓글 기능 구현

let replyObject = {
	init: function() {
		$("#save_reply_btn").click(() => {
			this.insertReply();   // this = 클릭한 대상 = 필수
		})
	},
	insertReply: () => {
		// alert("댓글 등록 요청됨");
		// boardId 가져오기
		let boardId = $("#board_id").val();
		// document.getElementById(replyContent).value
		// 댓글 내용
		let replyContent = $("#reply_content").val();
		
		if(replyContent==""){
			alert("댓글을 입력해 주세요");
			$("#reply_content").focus();
			return false;
		}
		
		// 댓글 data
		let reply = {
			hrcontent: replyContent    // content - 컨트롤러로 넘겨주는 값
		}
		console.log(reply);
		
		//let header = $("meta[name='_csrf_header']").attr('content');
		//let token = $("meta[name='_csrf']").attr('content');

		$.ajax({
			type: "POST",
			url: "/hopereply/" + boardId,
			//beforeSend: function(xhr) {
			//	xhr.setRequestHeader(header, token);
			//},
			data: JSON.stringify(reply),  // 자바스크립트 객체를 문자화해서 json으로 변형
			contentType: "application/json; charset=utf-8"
		}).done(function(response) {
			console.log(response);
			replyContent="";
			
			location.href="/hopeboard/"+boardId;
			
		}).fail(function(error) {
			alert("에러 발생: " + error);
		});
	}, // inserReply닫기
	
	updateReply: function(hbid, hrid) {
	    let originalContent = $('#originalContent'+hrid).text().trim(); // 기존 댓글 내용 가져오기
	    console.log(originalContent);
	    $('#replyContent'+hrid).attr('placeholder', originalContent);
	    $('#replyModal'+hrid).css('display', 'block'); // 모달 창 띄우기
	    $('#updateReplyBtn_cancel'+hrid).click(() => {
	        $('#replyModal'+hrid).css('display', 'none'); // 모달 창 숨기기
	    });
	    $('#updateReplyBtn_confirm'+hrid).click(() => {
	        let updatedContent = $('#replyContent'+hrid).val().trim(); // 수정된 내용 가져오기
	        if (!updatedContent) {
	            alert("댓글 내용을 입력하세요");
	            return;
	        }
	
	        let reply = {
	            hrcontent: updatedContent
	        };
	
	        $.ajax({
	            type: "PUT",
	            url: "/hopereply/" + hrid,
	            data: JSON.stringify(reply),
	            contentType: "application/json; charset=utf-8"
	        }).done(function(response) {
	            console.log(response);
	            location.href="/hopeboard/"+hbid; // 수정된 내용을 반영하기 위해 페이지를 새로고침
	        }).fail(function(error) {
	            alert("에러 발생: " + error);
	        });
	    });
	},
		
	
	deleteReply: function(boardId, replyId){
		if(confirm("댓글을 삭제하시겠습니까?")){
			
			//let header = $("meta[name='_csrf_header']").attr('content');
			//let token = $("meta[name='_csrf']").attr('content');
			
			$.ajax({
				type: "DELETE",
				url: "/hopereply/" + replyId,
				//beforeSend: function(xhr) {
				//	xhr.setRequestHeader(header, token);
				//}
				
			}).done((response)=>{
				console.log(response);
				location.href="/hopeboard/"+boardId;
			}).fail((error)=>{
				alert("에러 발생: "+ error)
			})
		}
	}
}

replyObject.init();   // init() 함수 호출