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
               <div class="form-group">
                  <label>제목</label> <input class="form-control" name='title'>
               </div>
               <div class="form-group">
                  <label>내용</label>
                  <textarea class="form-control" row="3" name='content'></textarea>
               </div>
               <div class="form-group">
                  <label>작성자</label> <input class="form-control" name="writer">
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
$(document).ready(function(){
   var formObj=$("form[role='form']");// 글쓰기 폼을 스크립트 객체로 할당.
   $("button[type='submit']").on("click",function(e){
      e.preventDefault();
      console.log("submit clicked");
   });
   
   var regex=new RegExp("(.*?)\.(exe|sh|zip|alz)$");
   // 정규표현식. 일부 파일의 업로드 제한. 필터, 
   // https://regexper.com/
   
   var maxSize = 5242880; // 5MB
   
   function checkExtension(fileName, fileSize){
      if(fileSize >=maxSize){
         alert("파일 크기 초과");
         return false;
      }
      
      if(regex.test(fileName)){
         alert("해당 종류의 파일은 업로드 불가.");
         return false;
      }
      return true;
   }
   
   $("input[type='file']").change(function(e){
      // 첨부파일 정보를 변경 한다면,
      var formData = new FormData();// 스크립트의 폼 객체.
      // 폼에 담고 있지만, 기존과 동일한 항목과 값의 형태로 처리.
      var inputFile=$("input[name='uploadFile']");// 화면의 파일 요소를 변수에 할당.
      var files=inputFile[0].files;
      // .files 를 이용하면 배열로 파일 값들을 리턴.
      for(var i=0;i<files.length;i++){
         if(!checkExtension(files[i].name, files[i].size)){
            return false;
         }
         formData.append("uploadFile",files[i]);
         // 확장자와 크기가 지정한 규격에 맞다면, 폼에 해당 파일 정보를 추가.
      }
      
      $.ajax({
         url:'/uploadAjaxAction',
         processData:false,
         contentType:false,
         data:formData, // 실제 2진 데이터 전송이 아니고, 파일관련 정보만 전송.
         type:'post',// 첨부파일 처리는 get 방식은 불가.
         dataType:'json',
         success:function(result){
            console.log(result);
            
         }
      });
   });


});
</script>

<%@ include file="../includes/footer.jsp"%>











