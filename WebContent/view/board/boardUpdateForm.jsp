<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
h3 {
	color:#7C5D44;
}
th {
	width:20%;
	text-align:center;
	font-weight:bold;
	background-color:#ede4de;
}
tr {
	height:30px;
}
input{
	width:95%;
}
textarea {
	height:200px;
	width:95%
}
select {
	width:30%;
}
.brdImage {
	height:500px;
	padding:10px; 
}
.btn2 {
    width:60px;
    background-color: #d2bdad;
    border: none;
    color:black;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 13px;
    margin: 4px;
    cursor: pointer;
    border-radius:5px;
 }
.btn2:hover {
	background-color: #f6f2ef;
	font-weight:bold;
    color:black;
} 
</style>
<script>
	$(function(){
		//공지 초기값 지정
		$("#notiyn option[id='${DATA.notiyn}']").attr("selected", true);
	
		//분류명 초기값 지정
		$("#cname option[id='${DATA.cname}']").attr("selected", true);
		
		//분류 변경될 때마다 상품목록 새로 불러오기
		$("select[name='cname']").change(function(){
			var cidx=$(this).val();
			$("#pname").html();
			if(cidx==0){
				var option='<option>선택하세요</option>'
				option+='<option id="없음" value="0">없음</option>';
				$("select[name='pname']").html(option)
			}
			if(cidx==1){
				var option='<option>선택하세요</option>'
				option+='<c:forEach var="data" items="${PLIST1}"><option id="${data.pname }"  value="${data.pidx }">${data.pname }</option></c:forEach>';
				$("select[name='pname']").html(option)
			}
			if(cidx==2){
				var option='<option>선택하세요</option>'
				option+='<c:forEach var="data" items="${PLIST2}"><option id="${data.pname }"  value="${data.pidx }">${data.pname }</option></c:forEach>';
				$("select[name='pname']").html(option)
			}
			if(cidx==3){
				var option='<option>선택하세요</option>'
				option+='<c:forEach var="data" items="${PLIST3}"><option id="${data.pname }"  value="${data.pidx }">${data.pname }</option></c:forEach>';
				$("select[name='pname']").html(option)
			}
		})
	})
	
	function UpdateProc(){
		if($('select[name="cname"]>option:selected').index()<1){
			alert("분류를 선택해주세요")
			$('select[name="cname"]').focus();
			return;
		}
		if($('select[name="pname"]>option:selected').index()<1){
			alert("상품을 선택해주세요")
			$('select[name="pname"]').focus();
			return;
		}
		
		if($("#subject").val()==""){
			alert("제목을 입력해주세요");
			$("#subject").focus();
			return;
		}
		
		if($("#comm").val()==""){
			alert("내용을 입력해주세요");
			$("#comm").focus();
			return;
		}
		$("#frm").submit();
	}
</script>
</head>
<body>
<table align="center" width="70%">
	<tr>
		<td>
			<h3 align="left">게시판 수정하기</h3>
		</td>
	</tr>
</table>
<form id="frm" name="frm" action="../board/boardUpdateProc.yo" method="post" enctype="multipart/form-data">
	<%--파라미터 릴레이용 --%>
	<input type="hidden" id="oriNo" name="oriNo" value="${oriNo}">
	<input type="hidden" id="nowPage" name="nowPage" value="${nowPage}">
	
	<table border="1px" align="center" width="70%">
		<c:if test="${sessionScope.userid eq 'admin' }"> 
		<tr>
			<th>공지유무</th>
			<td>&nbsp;
				<select id="notiyn" name="notiyn">
					<option id="N" value="N" >NO</option>
					<option id="Y" value="Y">YES</option>
				</select>
			</td>
		</tr>
		</c:if>
		<tr>
			<th>상품분류</th>
			<td>&nbsp;
				<select id="cname" name="cname">
					<option>선택하세요</option>
					<option id="없음" value="0">없음</option>
					<c:forEach var="data" items="${CLIST}">
						<option id="${data.cname }"  value="${data.cidx }">${data.cname }</option>
					</c:forEach> 	
				</select>
			</td>
		</tr>
		<tr>
			<th>상품명</th>
			<td>&nbsp;
				<select id="pname" name="pname">
					<option>선택하세요</option>
					<option id="${DATA.pname }"  value="${DATA.pidx }" selected>${DATA.pname }</option>
					<c:if test="${DATA.cname eq 'Cold brew'}">
						<c:forEach var="data" items="${PLIST1}"><option id="${data.pname }"  value="${data.pidx }">${data.pname }</option></c:forEach>	
					</c:if>
					<c:if test="${DATA.cname eq 'Espresso'}">
						<c:forEach var="data" items="${PLIST2}"><option id="${data.pname }"  value="${data.pidx }">${data.pname }</option></c:forEach>	
					</c:if>
					<c:if test="${DATA.cname eq 'Frappuccino'}">
						<c:forEach var="data" items="${PLIST3}"><option id="${data.pname }"  value="${data.pidx }">${data.pname }</option></c:forEach>	
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>&nbsp;<input type="text" name="subject" id="subject" value="${DATA.subject }"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td>&nbsp;<textarea name="comm" id="comm">${DATA.getCommNbr() }</textarea></td>
		</tr>
		<tr>
			<th>기존파일</th>
			<td>
				<c:if test="${DATA.image ne null}">
				<a href="../view/upload/board/${DATA.image}"> <img src="../view/upload/board/${DATA.image}" class="brdImage"/></a>
				</c:if>
				<c:if test="${DATA.image eq null}">
				&nbsp;첨부파일 없음
				</c:if>
			</td>
		</tr>	
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="file" id="file" >
			</td>
		</tr>
	</table>
	<table align="center" width="70%">
		<tr>
			<td colspan="2" align="center">
				<input type="button" class="btn2" id="sBtn" value="저장" onclick="UpdateProc()">
				<input type="button" class="btn2" id="bBtn" value="목록" onclick="location.href='/board/boardBoardList.yo?oriNo=${oriNo}&nowPage=${nowPage }'">
			</td>
		</tr>
	</table>
</form>
</body>
</html>