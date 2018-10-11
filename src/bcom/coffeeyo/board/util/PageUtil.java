package bcom.coffeeyo.board.util;

//이 클래스는 페이지 나눔 기능을 처리하기 위해 
//필요한 정보를 계산하고 제공하는 클래스이다

public class PageUtil {
	/*
	 [1][2][3]
	 [4][5][6]
	 [7][8][9] 이런 모습이 보이도록 만드는 유틸클래스이다
	 */
	//필수변수(반드시 개발자가 알려주어야 할 변수)
	private int nowPage;	//현재 보고 싶은 페이지
	private int totalCount;	//총 게시물(원글) 수
	
	//필수변수(계산을 위해서 꼭 알아야할 변수)
	private int listCount;	//한 페이지에 나타날 게시물 수
	private int pageCount;	//한 페이지에 나타날 페이지 이동 개수
	
	//변수(계산에 의해서 계산되어야 할 변수)
	private int totalPage;	//총 페이지 수
	private int startPage;	//화면에 표시할 시작 페이지수
	private int endPage;	//화면에 표시할 마지막 페이지수
	
	//생성자
	//한 화면에는 10개의 게시물이 보이게 하고
	//한 화면에는 3개씩 페이지 이동기능을 만들 예정
	//개발자가 반드시 알려주어야할 두개의 데이터는 생성자함수를 이용해서 받도록한다
	//나머지 두개의 데이터는 하드코딩해서 처리하자
	
	public PageUtil(int np,int tc) {
		this(np,tc,10,3);
	}
	
	public PageUtil(int np,int tc,int lc,int pc) {
		this.nowPage=np;
		this.totalCount=tc;
		this.listCount=lc;
		this.pageCount=pc;
		
		calcTotalPage();	
		calcStartPage();	
		calcEndPage();	
	}
	
	//함수
	//총 페이지 수
	public void calcTotalPage() {
		//총 게시물(원글) 수/한 페이지에 나타날 게시물 수
		//주의)마지막 페이지는 한개가 남아도 한 페이지가 더 필요하다
		
		//삼항연산자 활용
		//(조건)?조건참일경우:조건거짓일경우
		totalPage=(totalCount%listCount==0)?(totalCount/listCount):(totalCount/listCount)+1;
	}
	//화면에 표시할 시작 페이지수
	public void calcStartPage() {
		//보고싶은 페이지가 어느 그룹에 속하는지 알아내야함
		int pageGroup=(nowPage%pageCount==0)?(nowPage/pageCount):(nowPage/pageCount)+1;
		startPage=(pageGroup-1)*pageCount+1;
	}
	//화면에 표시할 마지막 페이지수
	public void calcEndPage() {
		//시작페이지+화면에보여줄이동수-1
		//주의)총페이지 수가 페이지 이동수의 배수로 딱 떨어지지 않는 경우도 있음
		//마지막 화면에는 총 페이지수를 넘어간 내용은 만들지 않아도 된다
		endPage=startPage+pageCount-1;
		if(endPage>=totalPage) {
			endPage=totalPage;
		}
	}
	//이렇게 계산된 데이터는 뷰에서 이용하기 위해 만든 것이므로
	//getXxx()가 존재해야 한다
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
