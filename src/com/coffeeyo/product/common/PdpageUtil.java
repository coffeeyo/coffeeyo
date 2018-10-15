package com.coffeeyo.product.common;

public class PdpageUtil {
	//반드시 필요한 변수
		private int nowPage;		//현재 보고 싶은 페이지
		private int totalCount;		//총 게시물(원글) 수
		//한페이지당 몇개의 목록을 보여줄지, 한 페이지당 페이지 이동개수도 표현
		private int listCount;		//한페이지당 나타날 게시물 수
		private int pageCount;		//한페이지에 나타날 페이지 이동 개수
		//변수(계산에 의해서 계산되어야 할 변수)
		private int totalPage;		//총 페이지 수
		private int startPage;		//화면에 표시할 시작 페이지수 1,4,7,...
		private int endPage;			//화면에 표시한 마지막 페이지수 3,6,9,...
		
		
		
		//생성자
		//nowPage, totalCount는 생성자 함수를 사용하고 나머지는 직접 코딩해서 처리한다.

		public PdpageUtil(int np, int tc) {
			this(np, tc, 3, 3);
		/*this.nowPage=np;
			this.totalCount=tc;
			this.listCount=3;
			this.pageCount=3;*/
			
		}
		public PdpageUtil(int np, int tc, int lc, int pc) {
			this.nowPage=np;
			this.totalCount=tc;
			this.listCount=lc;
			this.pageCount=pc;
			
			calcTotalPage();		//총페이지수 계산
			calcStartPage();		//시작 페이지 계산
			calcEndPage();			//마지막 페이지 계산
		}
		
		//총페이지수 계산
		public void calcTotalPage() {
			//주의) 마지막페이지는 한개가 남아도 한페이지가 더 필요하다.
			//나머지가 없으면 A를 나머지가 있으면 B를 선택하라는 3항 연산자를 활용
			totalPage=(totalCount%listCount==0)?(totalCount/listCount):(totalCount/listCount)+1;
			System.out.println("totalPage="+totalPage);
		}
		//시작 페이지 계산
		public void calcStartPage() {
			//한 화면당 5개씩 보여준다고 가정할 때 
			//보고싶은 페이지 nowPage가 어느 그룹에 속하는지 알아내고 해당 그룹의 시작번호를 구한다.
			int pageGroup = (nowPage%pageCount==0)?(nowPage/pageCount):(nowPage/pageCount)+1;
			System.out.println("pageGroup="+pageGroup);
			startPage=(pageGroup-1)*pageCount+1;
			System.out.println("startPage="+startPage);
			
			
			
		}
		//마지막 페이지 계산
		public void calcEndPage() {
			//시작페이지+화면에 보여줄 이동수-1
			//마지막화면에는 총 페이지수를 넘어간 내용을 만들지 않아도 된다.
			endPage=startPage+pageCount-1;
			if(endPage>=totalPage) {
				endPage=totalPage;
			}
		
		}
		//이렇게 계산된 데이터는 뷰에서 이용하기 위해서 만든 것이므로 getXxx가 존재해야 한다.
		public int getNowPage() {
			return nowPage;
		}
		public void setNowPage(int nowPage) {
			this.nowPage = nowPage;
		}
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public int getListCount() {
			return listCount;
		}
		public void setListCount(int listCount) {
			this.listCount = listCount;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public int getStartPage() {
			return startPage;
		}
		public void setStartPage(int startPage) {
			this.startPage = startPage;
		}
		public int getEndPage() {
			return endPage;
		}
		public void setEndPage(int endPage) {
			this.endPage = endPage;
		}

}
