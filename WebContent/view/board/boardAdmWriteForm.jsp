<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WriteForm</title>
<script src="../js/jquery-3.3.1.min.js"></script>
<script>
	//글등록버튼 클릭시 무결성 검사하고 폼을 submit시키겠다
	function WriteProc(){
		
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
	
	$(function(){
		$("select[name='cname']").change(function(){
			var cidx=$(this).val();
			if(cidx==0){
				var option='<option>선택하세요</option>'
				option+='<option id="없음" value="0">없음</option>'
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
</script>
</head>
<body>
<h1>게시판 등록</h1>
<form id="frm" name="frm" action="../admin/boardWriteProc.yo" method="post" enctype="multipart/form-data">
	<table border="1" align="center" width="600">
		<tr>
			<th>공지유무</th>
			<td>
				<select id="notiyn" name="notiyn">
					<option value="N" selected>NO</option>
					<option value="Y">YES</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>상품분류</th>
			<td>
				<select id="cname" name="cname">
					<option>선택하세요</option>
					<option value="0">없음</option>
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
				</select>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="subject" id="subject"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="comm" id="comm"></textarea></td>
		</tr>
		<tr>
			<th>파일첨부</th>
			<td><input type="file" name="file" id="file" ></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" id="wBtn" value="저장" onclick="WriteProc()">
				<input type="button" id="bBtn" value="목록" onclick="location.href='/admin/boardBoardList.yo'">
			</td>
		</tr>
	</table>
</form>
</body>
</html>