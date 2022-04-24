/**
 * 
 */

console.log("Reply module.....");

var replyService = (function() {
	function add(reply, callback, error) {
		// reply : 덧글 객체
		// callback : 덧글 등록 후 수행할 메소드. 비동기
		// 주문과 동시에 처리할 내용도 전달. 페이지 이동없이 새로운 내용 갱신.
		console.log("add reply.....");

		$.ajax({
			type: 'post',
			url: '/replies/new',
			data: JSON.stringify(reply),
			// 전달 받은 객체를 json으로 변환.
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
	}
	
	function getList(param, callback, error){
		console.log("getList.....");
		var bno = param.bno;
		var page = param.page || 1;
		// 페이지 번호가 있으면 페이지 번호 전달 없으면 1전달.
		$.getJSON("/replies/pages/" + bno + "/" + page,
				function(data){
					if(callback){
						callback(data);
					}
				}).fail(function(xhr, status, err){
					// xhr : xml  htpp request의 약자.
					// 현재는 사용되지않고 형식만 맞춰줌
					if (error){
						error(er);
					}
				});
	}
	return {
		add: add, // 변수명.호출명 예) replyService.add
		getList: getList
	};
})(); // 즉시 실행 함수: 명시하는 것과 동시에 메모리에 등록.