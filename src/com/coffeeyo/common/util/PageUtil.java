package com.coffeeyo.common.util;

/*
 * 이 클래스는 페이지 나눔 기능을 처리하기 위해
 * 필요한 정보를 계산하고 제공하는 클래스이다.
 */
public class PageUtil {
	//반드시 개발자가 알려주어야 할 변수
	private int nowPage;		// 현재 보고 싶은 페이지
	private int totalCount;		// 총 게시물(원글) 수
	
	//계산을 위해서 꼭 알아야할 변수
	private int listCount;		// 한페이지에 나타날 게시물 수
	private int pageCount;		// 한페이지에 나타날 페이지 이동 개수
		
	// 계산에 의해서 계산되어야 할 변수
	private int totalPage; 	//총페이지수
	private int startPage;	//화면에 표시할 시작 페이지수
	private int endPage;	//화면에 표시할 마지막 페이지수
	
	//생성자
	//한 화면에 3개의 게시물이 보이도록 하고
	//한 화면에는 3개씩 페이지 이동기능을 만들예정
	//개발자가 반드시 알려주어야할 두개의 데이터는 생성자함수를 이용하여 받도록 하자.
	//나머지 두 개의 데이터는 하드코딩해서 처리하자
	PageUtil() {}
	public PageUtil(int np, int tc) {
		this(np, tc, 3, 3);
	}
	public PageUtil(int np, int tc, int lc, int pc) {
		this.nowPage = np;
		this.totalCount = tc;
		this.listCount = lc;
		this.pageCount = pc;
		
		calcTotalPage();
		calcStartPage();
		calcEndPage();
	}
	
	//총 페이지수
	public void calcTotalPage() {
		//총 게시물(원글)수 / 한 페이지에 나타날 게시물 수
		//주의) 마지막 페이지는 한 개가 남아도 한 페이지가 더 필요하다.
		//예)		100/10		10
		//			101/10		11
		this.totalPage = (this.totalCount % this.listCount == 0) ? 
				(this.totalCount / this.listCount) : (this.totalCount / this.listCount)+1;
	}
	
	//화면에 표시할 시작 페이지수
	public void calcStartPage() {
		//한 화면당 5개씩 보여준다고 가정
		/* (1,2,3,4,5)					1그룹
		 * 1,2,3,4는 5로 나누면 나머지가 존재하고 +1 해 준다. 
		 * 5/5 = 0 일 경우 몫을 그대로 사용한다.
		 * (6,7,8,9,10)				2그룹
		 * (11,12,13,14,15)		3그룹
		 * 
		 * this.startPage = this.nowPage / this.pageCount
		 * */ 
		//페이지그룹 구하기
		int pageGroup =  (this.nowPage % this.pageCount == 0) ? 
								(this.nowPage / this.pageCount) : (this.nowPage / this.pageCount) + 1;
				
		/*
		 * 1그룹 	1		(1-1)*3+1		= 1
		 * 2그룹	2		(2-1)*3+1		= 4
		 * 3그룹	3		(3-1)*3+1		= 7
		 */
		
		this.startPage = (pageGroup - 1) * this.pageCount + 1;
	}
	
	//화면에 표시할 마지막 페이지수
	public void calcEndPage() {
		// 시작페이지  + 화면에 보여줄 이동수 - 1
		// 5		(1+5) - 1
		// 10	(6+5) - 1
		//주의) 마지막 화면에는 총 페이지수를 넘어간 내용은 만들지 않아도 된다.
		this.endPage = this.startPage + this.pageCount - 1;
		
		if(this.endPage >= this.totalPage) {
			this.endPage = this.totalPage;
		}
		// 이렇게 계산된 데이터는 뷰에서 이용하기 위해서 만든 것이므로
		// getXxx 가 존재해야 한다.
	}
	
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