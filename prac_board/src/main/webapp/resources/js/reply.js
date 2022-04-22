/**
 * 
 */
 
console.log("Reply module.....");

var replyService = {};
// 함수형 변수. 현재 이름이 없는 함수를 가지고 있음.

var replyService = (function() {// 자바스크립트는 함수를 변수에 할당 가능.
    function add(reply, callback, error) {
       // reply : 덧글 객체,
       // callback : 덧글 등록후 수행할 메소드. 비동기.
       // 주문과 동시에 처리할 내용도 전달. 페이지 이동없이 새로운 내용 갱신.
       console.log("add reply......");
       $.ajax({
          type: 'post',
          url: '/replies/new',
          data: JSON.stringify(reply),
          // 전달 받은 객체를 json 으로 변환.
          contentType: "application/json; charset=utf-8",
          success: function(result) {
             if (callback) {
                callback(result); 
             }
          },
          error: function(er) {
             if (error) {
                error(er);
             }
          }
       });
    } // 덧글쓰기 끝
    
    // 댓글 목록 가져오기
    function getList(param, callback, error) {
            console.log("getlist....");
            var bno = param.bno;
            var page = param.page || 1; // 2개 갑중 하나로 변수 초기화
            // 페이지 번호가 있으면 페이지 번호 전달 아니면 1전달.
            $.getJSON("/replies/pages/" + bno + "/" + page,
                function(data) {
                    if(callback) {
                         callback(data);
                    }
                }).fail(function(xhr, status, err){
                    // xhr : xml http request의 약자.
                    // 현재는 사용되지 않고, 형식만 맞춰줌.
                    if(error) {
                        error(er);
                    }
                })
       } // 덧글 목록 가져오기 끝.
       
        return {
           add: add,
           getList: getList
        }; // 변수명.호출명 예) replyService.add
 })(); // 즉시 실행 함수: 명시하는 것과 동시에 메모리에 등록.
 






