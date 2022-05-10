<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp"%>
<div class="row">
   <div class="col-lg-12">
      <h1 class="page-header">글읽기</h1>
   </div>
</div>
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-body">
            <div class="form-group">
               게시물 번호<input class="form-control" name="bno"
                  value='<c:out value="${board.bno }"/>' readonly="readonly">
            </div>

            <div class="form-group">
               <label>제목</label> <input class="form-control" name='title'
                  value='<c:out value="${board.title }"/>' readonly="readonly">
            </div>
            <div class="form-group">
               <label>내용</label>
               <textarea class="form-control" row="3" name='content'
                  readonly="readonly"><c:out value="${board.content }" /></textarea>
               <!-- textarea 사이 빈 공간을 지워줘야 한다. -->
            </div>
            <div class="form-group">
               <label>작성자</label> <input class="form-control" name="writer"
                  value='<c:out value="${board.writer }"/>' readonly="readonly">
            </div>
            
            
            <sec:authentication property="principal" var="pinfo"/>
            <!-- 프린시펄 정보를 pinfo라는 이름으로 jsp에서 이용. -->            
            <sec:authorize access="isAuthenticated()">
            <!-- 인증된 사용자만 허가 -->
            <c:if test="${pinfo.username eq board.writer }">
            <!-- 인증되었으면서 작성자가 본인 일때 수정 버튼 표시 -->
            <button data-oper="modify" id="boardModBtn" class="btn btn-warning">수정</button>
            </c:if>
            </sec:authorize>
            
            
            
            <button data-oper="list" id="boardListBtn" class="btn btn-info">목록</button>

            <!-- 글읽기 창에서 페이지 정보 처리 시작 -->
            <form id="operForm" action="/board/modify" method="get">
               <input type="hidden" id="bno" name="bno" value="${board.bno }" />
               <input type="hidden" name="pageNum" value="${cri.pageNum }" /> <input
                  type="hidden" name="amount" value="${cri.amount }" /> <input
                  type="hidden" name="type" value="${cri.type }" /> <input
                  type="hidden" name="keyword" value="${cri.keyword }" />
            </form>
            <!-- 폼을 생성해서 게시물번호를 숨김 값으로 전달. 
            나중에 현재 페이지 번호, 페이지당 게시물수, 검색어, 검색타입 추가 예정
            -->
            <!-- 글읽기 창에서 페이지 정보 처리 끝 -->


         </div>
      </div>
   </div>
</div>

<!-- 첨부파일 시작 -->
<br/>
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">첨부파일</div>
         <div class="panel-body">
            <div class="uploadResult">
               <ul></ul>
            </div>
         </div>
      </div>
   </div>
</div>
<!-- 첨부파일 끝 -->

<!-- 덧글 목록 시작 -->
<br />
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading">
            <i class="fa fa-comments fa-fw"></i>덧글
            <sec:authorize access="isAuthenticated()">
            <button id="addReplyBtn" class="btn btn-primary btn-xs float-right">
              	 새 덧글</button>
            </sec:authorize>
            
         </div>
         <br />
         <div class="panel-body">
            <ul class="chat">
               <li>good</li>
            </ul>
         </div>
         <div class="panel-footer"></div>
      </div>
   </div>
</div>
<!-- 덧글 목록 끝 -->

<!-- 덧글 작성용 모달 시작 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
   aria-labelledby="exampleModalLabel" aria-hidden="true">
   <div class="modal-dialog" role="document">
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">덧글창</h5>
         </div>
         <div class="modal-body">
            <div class="form-group">
               <label>덧글</label> <input class="form-control" name="reply"
                  value="새 덧글">
            </div>
            <div class="form-group">
               <label>작성자</label> <input class="form-control" name="replyer"
                  value="replyer" readonly="readonly">
            </div>
            <div class="form-group">
               <label>덧글 작성일</label> <input class="form-control" name="replyDate"
                  value="">
            </div>
         </div>
         <div class="modal-footer">
            <button id="modalModBtn" type="button" class="btn btnwarning">수정</button>
            <button id="modalRemoveBtn" type="button" class="btn btndanger">삭제</button>
            <button id="modalRegisterBtn" type="button" class="btn btnprimary">등록</button>
            <button id="modalCloseBtn" type="button" class="btn btndefault">닫기</button>
         </div>
      </div>
   </div>
</div>
<!-- 덧글 작성용 모달 끝 -->



<script type="text/javascript" src="/resources/js/reply.js"></script>
<script>
   $(document).ready(function() {
      var formObj = $("form");/* 문서중 form 요소를 찾아서 변수에 할당. */
      var csrfHeaderName = "${_csrf.headerName}";
      var csrfTokenValue = "${_csrf.token}";
      
      $(document).ajaxSend(function(e,xhr,options){
    	  xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
      }); // csrf 값을 미리 설정해 두고, ajax 처리시마다 이용.

      $("#boardListBtn").on("click", function(e) {
         e.preventDefault();
         var operation = $(this).data("oper");
         /* 버튼에서 oper 속성 읽어서 변수에 할당. */
         console.log(operation);
         /* 브라우저 로그로 oper값 출력. */
         if (operation === 'list') {
            //self.location = "/board/list";
            //return;
            formObj.attr("action", "/board/list");
            formObj.find("#bno").remove();
            // form 에서 아이디 bno 요소를 찾아서 삭제.
            /* 
             글읽기 페이지에는 히든값을 담은 폼이 1개 있습니다.
             검색후 읽기로 넘어가면 pageNum, amount, type, keyword 가 읽기 페이지로 전달되나,
             읽기후 목록 선택시 type, keyword 가 전달되지 않는 상황이 발생 되었습니다.
             그래서, 읽기 페이지의 폼에 type, keyword도 전달되도록 해당 항목 추가하여 해결.
             */
         }
         formObj.submit();
      });

      $("#boardModBtn").on("click", function(e) {
         e.preventDefault();
         var operation = $(this).data("oper");
         /* 버튼에서 oper 속성 읽어서 변수에 할당. */
         console.log(operation);
         /* 브라우저 로그로 oper값 출력. */
         if (operation === 'modify') {
            formObj.attr("action", "/board/modify");
            // formObj.find("#bno").remove();
            // form 에서 아이디 bno 요소를 찾아서 삭제.
         }
         formObj.submit();
      });
      
      var modalModBtn = $("#modalModBtn");
      var modalRemoveBtn = $("#modalRemoveBtn");

      console.log(replyService);
      var bnoValue = '<c:out value="${board.bno}"/>';
      var replyer=null;
      <sec:authorize access="isAuthenticated()">
      	replyer='${pinfo.username}';
      </sec:authorize>

      /* replyService.add({
         reply : "js test",
         replyer : "tester",
         bno : bnoValue
      }, function(result) {
         console.log("result: " + result);
         alert("result: " + result);
      }); */
      // 게시글을 읽을때 자동으로 댓글 1개 등록.
      // replyService.add(replyVO,callback);
      
      var modal = $("#myModal");
      // 덧글 용 모달.
      var modalInputReplyDate = modal.find("input[name='replyDate']");
      // 덧글 작성일 항목.
      var modalRegisterBtn = $("#modalRegisterBtn");
      // 모달에서 표시되는 덧글쓰기 버튼.
      var modalInputReply = modal.find("input[name='reply']");
      // 덧글 내용
      var modalInputReplyer = modal.find("input[name='replyer']");// 덧글 작성자.
      //덧글 입력 모달창 보이기.
      $("#addReplyBtn").on("click", function(e) {
               // 새덧글 버튼을 클릭한다면,
               modal.find("input").val("");
               // 모달의 모든 입력창을 초기화
               modalInputReplyDate.closest("div").hide();
               // closest : 선택 요소와 가장 가까운 요소를 지정.
               // 즉, modalInputReplyDate 요소의 가장 가까운
               // div를 찾아서 숨김. (날짜창 숨김)
               modal.find("button[id != 'modalCloseBtn']").hide();
               // 모달창에 버튼이 4개 인데, 닫기 버튼을 제외하고 숨기기.
               modalRegisterBtn.show(); // 등록 버튼은 보여라.
               $("#myModal").modal("show");// 모달 표시.
            });
      
      // 모달창 닫기
      $("#modalCloseBtn").on("click", function(e) {
         modal.modal("hide");
         // 덧글 모달 닫기 라는 버튼을 클릭한다면 모달창을 숨김.
      });
      
      // 모달창에서 덧글 쓰기.
      modalRegisterBtn.on("click", function(e) {
         // 덧글 등록 버튼을 눌렀다면,
         var reply = {
            reply : modalInputReply.val(),
            replyer : replyer,
            bno : bnoValue
         }; // ajax로 전달할 reply 객체 선언 및 할당.
         replyService.add(reply, function(result) {
            alert(result);
            // ajax 처리후 결과 리턴.
            modal.find("input").val("");
            // 모달창 초기화
            modal.modal("hide");// 모달창 숨기기
            
            // 덧글 작성 즉시 목록 갱신용 함수 호출.
            showList(-1);
            // -1 이나 99나 현재는 영향이 없지만, 차후 덧글의 페이징 처리에서 -1 사용 예정.
         });// 덧글 쓰기 함수 끝
      });// 덧글 쓰기버튼처리 끝.
      
      // 덧글 목록 보이기.
      /* replyService.getList({
         bno : bnoValue,
         page : 1
      }, function(list) {
         for (var i = 0, len = list.length || 0; i < len; i++) {
            // 루프문의 초기값은 2개, i=0 이고, len은 배열에 길이 이거나 0
            console.log(list[i]);
         }
      }); */// 덧글 목록 표시 끝.

      var replyUL = $(".chat");
      
      function showList(page) {
         replyService
               .getList(
                     {
                        bno : bnoValue,
                        page : page || 1
                     },
                     function(replyTotalCnt, list) {
                        console.log("replyTotalCnt :"+ replyTotalCnt);
                        
                        if (page == -1) {
                           // 페이지 번호가 음수 값 이라면,
                           pageNum = Math.ceil(replyTotalCnt / 10.0);
                           // 게시물별 덧글 총갯수가 11일때, 2를 리턴.
                           // 덧글의 마지막 페이지 구하기.
                           showList(pageNum);
                           // 덧글 목록 새로고침(갱신)
                           return;
                        }
                        
                        var str = "";
                        
                        if (list == null
                              || list.length == 0) {
                           replyUL.html("");
                           return;
                        }// 목록이 없을때 처리 끝.
                        
                        for (var i = 0, len = list.length || 0; i < len; i++) {
                           str += "<li class='left ";
                           str += "clearfix' data-rno='";
                           str += list[i].rno+"'>";
                           str += "<div><div class='header' ";
                           str += "><strong class='";
                           str += "primary-font'>";
                           str += list[i].replyer+ "</strong>";
                           str += "<small class='float-sm-right '>";
                           str += replyService.displayTime(list[i].replyDate)
                           + "</small></div>";
                           str += "<p>"+ list[i].reply;
                           str += "</p></div></li>";
                        }
                        replyUL.html(str);
                        //replyUL.html("<h5>하하 월요일</h5>");
						//replyUL.html("<input type='text' value='" + val + "' />");   
						showReplyPage(replyTotalCnt);// 덧글 목록 표시후 쪽번호 표시.
	              });//end
	      }//end_showList
	      showList(-1);
      
      // 기존 게시판의 쪽번호는 디비에서 읽어서 자바로 설정값을 만들고, 표시 했다면,
      // 덧글의 쪽번호는 디비에서 읽어서 스크립트로 표시한다는 차이.
      /* 덧글 페이징 시작 */
                  var pageNum = 1;
                  var replyPageFooter = $(".panel-footer");// 덧글 목록의 footer

                  function showReplyPage(replyCnt) {
                     var endNum = Math.ceil(pageNum / 10.0) * 10;
                     // pageNum : 1 이라고 가정하면,
                     // Math.ceil(1/10.0) 처리하고 *10, 즉 endNum : 10
                     var startNum = endNum - 9;// - 나올지도..
                     var prev = startNum != 1;// false = (1 !=1)
                     var next = false;
                     // replyCnt : 384, endNum : 39
                     if (endNum * 10 >= replyCnt) {// 100 >= 384
                        endNum = Math.ceil(replyCnt / 10.0);
                     }
                     if (endNum * 10 < replyCnt) {
                        next = true;
                     }
                     var str = "<ul class='pagination";
                     str+=" justify-content-center'>";
                     if (prev) {
                        str += "<li class='page-item'><a ";
                        str += "class='page-link' href='";
                        str += (startNum - 1);
                        str += "'>이전</a></li>";
                     }
                     for (var i = startNum; i <= endNum; i++) {
                        var active = pageNum == i ? "active" : "";
                        str += "<li class='page-item "+ active
                        +"'><a class='page-link' ";
                        str+="href='"+i+"'>"
                        + i + "</a></li>";
                     }
                     if (next) {
                        str += "<li class='page-item'>";
                        str += "<a class='page-link' href='";
                        str += (endNum + 1) + "'>다음</a></li>";
                     }
                     str += "</ul>";
                     console.log(str);
                     replyPageFooter.html(str);
                  }
                  /* 덧글 페이징 끝 */
                  
      // 덧글 페이징 클릭시 처리.
      replyPageFooter.on("click", "li a", function(e) {
         e.preventDefault();
         var targetPageNum = $(this).attr("href");
         pageNum = targetPageNum;
         showList(pageNum);
      });   
      
      $(".chat").on("click", "li", function(e) {
         //클래스 chat 을 클릭하는데, 하위 요소가 li라면,
         var rno = $(this).data("rno");
         // 덧글에 포함된 값들 중에서 rno를 추출하여 변수 할당.
         console.log(rno);
         
         replyService.get(rno,function(reply) {
            modalInputReply.val(reply.reply);
            modalInputReplyer.val(reply.replyer);
            modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
                  .attr("readonly","readonly");
            // 댓글 목록의 값들을 모달창에 할당.
            modal.data("rno",reply.rno);
            // 표시되는 모달창에 rno 라는 이름으로 data-rno를 저장.
            modal.find("button[id !='modalCloseBtn']").hide();
            modalModBtn.show();
            modalRemoveBtn.show();
            // 버튼 보이기 설정.
            $("#myModal").modal("show");
         });
      }); // 끝_덧글 읽기.
      
      // 덧글 수정 처리 시작.
      modalModBtn.on("click", function(e) {
    	 var originalReplyer = modalInputReplyer.val();
         var reply = {
            rno : modal.data("rno"),
            reply : modalInputReply.val(),
            replyer : originalReplyer
         };
         
         if(!replyer){
        	 alert("로그인후 수정 가능");
        	 modal.modal("hide");
        	 return;
         }
         if(replyer != originalReplyer){
        	 alert("자신이 작성한 댓글만 수정 가능");
        	 modal.modal("hide");
        	 return;
         }
         
         replyService.update(reply, function(result) {
            alert(result);
            modal.modal("hide");
            showList(-1);
         });
      });// 끝_덧글 수정.
      
      // 덧글 삭제 처리.
      modalRemoveBtn.on("click", function(e) {
         var rno = modal.data("rno");
         var originalReplyer = modalInputReplyer.val();
         
         if(!replyer){
        	 alert("로그인후 삭제 가능");
        	 modal.modal("hide");
        	 return;
         }
         if(replyer != originalReplyer){
        	 alert("자신이 작성한 댓글만 삭제 가능");
        	 modal.modal("hide");
        	 return;
         }
         
         replyService.remove(rno, originalReplyer,function(result) {
            alert(result);// 안내창 표시
            modal.modal("hide");// 모달 숨기기
            showList(-1);// 덧글 목록 새로고침.
         });
      });
      
      // 첨부파일 목록 표시.(익명 즉시 실행 함수)
      (function(){
         var bno='<c:out value="${board.bno}"/>';
         // 화면상에 공유된 bno 값 가져와 사용 준비.
         $.getJSON("/board/getAttachList"
               ,{bno:bno}, function(arr){
                  console.log(arr);
                  
                  var str="";
                  $(arr).each(function(i,attach){
                     str+="<li data-path='";
                     str+=attach.uploadPath+"' data-uuid='";
                     str+=attach.uuid+"' data-filename='";
                     str+=attach.fileName+"' data-type='";
                     str+=attach.fileType+"'><div>";
                     str+="<img src='/resources/img/dan.jpg' width='20' height='20'> ";
                     str+="<span>"+attach.fileName+"</span><br/> ";
                     
                     str+="</div></li>";
                  });
                  $(".uploadResult ul").html(str);
               });
      })();// 첨부파일 목록 표시.(익명 즉시 실행 함수) 끝

      
      // 첨부파일 다운로드 이벤트 시작
      $(".uploadResult").on("click", "li", function(e){
    	  console.log("download file");
    	  var liObj = $(this);
    	  var path = encodeURIComponent(liObj.data("path")
    			  +"/"+liObj.data("uuid")+"_"
    			  +liObj.data("filename"));
    	  self.location="/download?fileName="+path;
      });
      // 다운 끝
      
      
      
      
      
      
      
      
      
   });// 도큐먼트 레디 끝.   
</script>



<%@ include file="../includes/footer.jsp"%>
















