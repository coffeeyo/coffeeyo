<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="../js/jquery-3.3.1.min.js"></script>
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
<form id="frm" name="frm" action="../board/boardUpdateProc.yo" method="post" enctype="multipart/form-data">
	<%--파라미터 릴레이용 --%>
	<input type="hidden" id="oriNo" name="oriNo" value="${oriNo}">
	<input type="hidden" id="nowPage" name="nowPage" value="${nowPage}">
	
	<table border="1" align="center" width="600">
		<c:if test="${sessionScope.userid eq 'admin' }"> 
		<tr>
			<th>공지유무</th>
			<td>
				<select id="notiyn" name="notiyn">
					<option id="N" value="N" >NO</option>
					<option id="Y" value="Y">YES</option>
				</select>
			</td>
		</tr>
		</c:if>
		<tr>
			<th>상품분류</th>
			<td>
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
			<td>
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
			<td><input type="text" name="subject" id="subject" value="${DATA.subject }"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="comm" id="comm">${DATA.getCommNbr() }</textarea></td>
		</tr>
		<c:if test="${DATA.image ne null}">
			<tr>
				<th>기존파일</th>
				<td>
					<a href="../view/upload/board/${DATA.image}"> <img src="../view/upload/board/${DATA.image}" border="0" /></a>
				</td>
			</tr>
		</c:if>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="file" id="file" >
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" id="sBtn" value="저장" onclick="UpdateProc()">
				<input type="button" id="bBtn" value="목록" onclick="location.href='/board/boardBoardList.yo?oriNo=${oriNo}&nowPage=${nowPage }'">
			</td>
		</tr>
	</table>
</form>
</body>
</html>