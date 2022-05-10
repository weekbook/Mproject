<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>



<div class="row">
   <div class="col-lg-12">
      <h1 class="page-header">글쓰기</h1>
   </div>
</div>
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-body">
            <form role="form" action="/board/register" method="post">
            <input type="hidden" name="${_csrf.parameterName }"
            	value="${_csrf.token }"/>
            
               <div class="form-group">
                  <label>제목</label> <input class="form-control" name='title'>
               </div>
               <div class="form-group">
                  <label>내용</label>
                  <textarea class="form-control" row="3" name='content'></textarea>
               </div>
               <div class="form-group">
                  <label>작성자</label> <input class="form-control" name="writer"
                  value='<sec:authentication property="principal.username"/>' readonly="readonly">
               </div>
               <button type="submit" class="btn btn-default">전송</button>
               <button type="reset" class="btn btn-default">취소</button>
            </form>
         </div>
      </div>
   </div>
</div>

<!-- 첨부파일 처리 시작 -->
<br />
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <div class="panel-heading"></div>
         <div class="panel-body">
            <div class="form-group uploadDiv">
               파일 첨부: <input type="file" name="uploadFile" multiple>
            </div>
            <div class="uploadResult">
               <ul></ul>
            </div>
         </div>

      </div>
   </div>
</div>
<!-- 첨부파일 처리 끝 -->

<script>
   $(document)
         .ready(
               function() {
                  var formObj = $("form[role='form']");// 글쓰기 폼을 스크립트 객체로 할당.
                  $("button[type='submit']").on("click", function(e) {
                     e.preventDefault();
                     console.log("submit clicked");
                     
                     
                     // 첨부파일 정보를 폼에 담아서 컨트롤러로 전송 처리 시작.
                     var str ="";
                     $(".uploadResult ul li").each(function(i, obj){
                    	 // i는 인덱스, obj는 li객체.
                    	 var jobj=$(obj);
                    	 console.dir(jobj); // console.dir() JavaScript 객체의 모든 속성을 보는 방법.
                    	 console.log("--------------------------");
                    	 console.log(jobj.data("filename"));
                    	 
                    	 str+="<input type='hidden' name='attachList[";
                    	 str+=i+"].fileName' value='"+jobj.data("filename");
                    	 str+="'>";
                    	 
                    	 str+="<input type='hidden' name='attachList[";
                    	 str+=i+"].uuid' value='"+jobj.data("uuid");
                    	 str+="'>";
                    	 
                    	 str+="<input type='hidden' name='attachList[";
                    	 str+=i+"].uploadPath' value='"+jobj.data("path");
                    	 str+="'>";
                    	 
                    	 str+="<input type='hidden' name='attachList[";
                    	 str+=i+"].fileType' value='"+jobj.data("type");
                    	 str+="'>";
                     });
                     formObj.append(str).submit();
                     // 첨부파일의 정보들을 li의 data 값으로 가지고있다가
                     // hidden 으로 폼에 포함.
                     
                  });

                  var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
                  // 정규표현식. 일부 파일의 업로드 제한. 필터, 
                  // https://regexper.com/

                  var maxSize = 5242880; // 5MB

                  function checkExtension(fileName, fileSize) {
                     if (fileSize >= maxSize) {
                        alert("파일 크기 초과");
                        return false;
                     }

                     if (regex.test(fileName)) {
                        alert("해당 종류의 파일은 업로드 불가.");
                        return false;
                     }
                     return true;
                  }
                  
                  
                  var csrfHeaderName = "${_csrf.headerName}";
                  var csrfTokenValue = "${_csrf.token}";
                  /* ajax 처리시 csrf 값을 함께 전송하기 위한 준비.
                  	스프링 시큐리티는 데이터 post 전송시 csrf 값을 꼭 확인 하므로 */
                  

                  $("input[type='file']")
                        .change(
                              function(e) {
                                 // 첨부파일 정보를 변경 한다면,
                                 var formData = new FormData();// 스크립트의 폼 객체.
                                 // 폼에 담고 있지만, 기존과 동일한 항목과 값의 형태로 처리.
                                 var inputFile = $("input[name='uploadFile']");// 화면의 파일 요소를 변수에 할당.
                                 var files = inputFile[0].files;
                                 // .files 를 이용하면 배열로 파일 값들을 리턴.
                                 for (var i = 0; i < files.length; i++) {
                                    if (!checkExtension(
                                          files[i].name,
                                          files[i].size)) {
                                       return false;
                                    }
                                    formData.append("uploadFile",
                                          files[i]);
                                    // 확장자와 크기가 지정한 규격에 맞다면, 폼에 해당 파일 정보를 추가.
                                 }

                                 $.ajax({
                                    url : '/uploadAjaxAction',
                                    processData : false,
                                    contentType : false,
                                    data : formData, // 실제 2진 데이터 전송이 아니고, 파일관련 정보만 전송.
                                    type : 'post',// 첨부파일 처리는 get 방식은 불가.
                                    dataType : 'json',
                                    beforeSend : function(xhr) {
										xhr.setRequestHeader(
												csrfHeaderName
												,csrfTokenValue);
									},
                                    success : function(result) {
                                       console.log(result);
                                       showUploadResult(result);
                                    }
                                 });
                              });// end_upload_change

                  // 첨부파일 목록 시작.
                  function showUploadResult(uploadResultArr) {
                     if (!uploadResultArr || uploadResultArr.length == 0) {
                        // json 처리 결과가 없다면 함수 종료.
                        return;
                     }
                     var uploadUL = $(".uploadResult ul");
                     var str = "";

                     // each 구문은 전달된 배열의 길이 만큼, 
                     // each 이후의 함수를 반복 처리.
                     // https://api.jquery.com/jQuery.each/#jQuery-each-array-callback
                     $(uploadResultArr).each(function(i, obj) {
                        var fileCallPath = encodeURIComponent(obj.uploadPath
                              + "/" + obj.uuid + "_" + obj.fileName);
                        // encodeURIComponent : 
                        // uri 로 전달되는 특수문자의 치환.
                        // & ?
                        var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
                        // 전달되는 값들 중에서 역슬러시를 찾아서 슬러시로 변경.

                        str += "<li data-path='";
                        str += obj.uploadPath+"' data-uuid='";
                        str += obj.uuid+"' data-filename='";
                        str += obj.fileName+"' data-type='";
                        str += obj.image+"'><div>";
                        str += "<img src='/resources/img/attach.png' width='20' height='20'>";
                        str += "<span>" + obj.fileName + "</span> ";
                        str += "<b data-file='"+fileCallPath;
                        str += "' data-type='file'>[x]</b>";
                        str += "</div></li>";
                                 });
                     uploadUL.append(str);
                  }// end_showUploadResult
                  // 첨부파일 목록 끝.
                  
                  // 첨부파일 목록에서 삭제 처리 이벤트 시작
                  $(".uploadResult").on("click","b",function(e){
                	  console.log("delete file");
                	  
                	  var targetFile = $(this).data("file");
                	  var type = $(this).data("type");
                	  var targetLi = $(this).closest("li");
                	  
                	  $.ajax({
                		  url : '/deleteFile',
                		  data : {
                			  fileName : targetFile,
                			  type :type
                		  },
                		  dataType : 'text',
                		  type : 'POST',
                		  beforeSend : function(xhr) {
								xhr.setRequestHeader(
										csrfHeaderName
										,csrfTokenValue);
						  },
						  
                		  success : function(result){
                			  alert(result);
                			  targetLi.remove();
                		  }
                	  })
                  });
                  // 첨부파일 목록에서 삭제 처리 이벤트 끝.

               
               
               });
   
  
</script>

<%@ include file="../includes/footer.jsp"%>











