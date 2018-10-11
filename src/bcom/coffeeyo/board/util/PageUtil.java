package bcom.coffeeyo.board.util;

//�� Ŭ������ ������ ���� ����� ó���ϱ� ���� 
//�ʿ��� ������ ����ϰ� �����ϴ� Ŭ�����̴�

public class PageUtil {
	/*
	 [1][2][3]
	 [4][5][6]
	 [7][8][9] �̷� ����� ���̵��� ����� ��ƿŬ�����̴�
	 */
	//�ʼ�����(�ݵ�� �����ڰ� �˷��־�� �� ����)
	private int nowPage;	//���� ���� ���� ������
	private int totalCount;	//�� �Խù�(����) ��
	
	//�ʼ�����(����� ���ؼ� �� �˾ƾ��� ����)
	private int listCount;	//�� �������� ��Ÿ�� �Խù� ��
	private int pageCount;	//�� �������� ��Ÿ�� ������ �̵� ����
	
	//����(��꿡 ���ؼ� ���Ǿ�� �� ����)
	private int totalPage;	//�� ������ ��
	private int startPage;	//ȭ�鿡 ǥ���� ���� ��������
	private int endPage;	//ȭ�鿡 ǥ���� ������ ��������
	
	//������
	//�� ȭ�鿡�� 10���� �Խù��� ���̰� �ϰ�
	//�� ȭ�鿡�� 3���� ������ �̵������ ���� ����
	//�����ڰ� �ݵ�� �˷��־���� �ΰ��� �����ʹ� �������Լ��� �̿��ؼ� �޵����Ѵ�
	//������ �ΰ��� �����ʹ� �ϵ��ڵ��ؼ� ó������
	
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
	
	//�Լ�
	//�� ������ ��
	public void calcTotalPage() {
		//�� �Խù�(����) ��/�� �������� ��Ÿ�� �Խù� ��
		//����)������ �������� �Ѱ��� ���Ƶ� �� �������� �� �ʿ��ϴ�
		
		//���׿����� Ȱ��
		//(����)?�������ϰ��:���ǰ����ϰ��
		totalPage=(totalCount%listCount==0)?(totalCount/listCount):(totalCount/listCount)+1;
	}
	//ȭ�鿡 ǥ���� ���� ��������
	public void calcStartPage() {
		//������� �������� ��� �׷쿡 ���ϴ��� �˾Ƴ�����
		int pageGroup=(nowPage%pageCount==0)?(nowPage/pageCount):(nowPage/pageCount)+1;
		startPage=(pageGroup-1)*pageCount+1;
	}
	//ȭ�鿡 ǥ���� ������ ��������
	public void calcEndPage() {
		//����������+ȭ�鿡�������̵���-1
		//����)�������� ���� ������ �̵����� ����� �� �������� �ʴ� ��쵵 ����
		//������ ȭ�鿡�� �� ���������� �Ѿ ������ ������ �ʾƵ� �ȴ�
		endPage=startPage+pageCount-1;
		if(endPage>=totalPage) {
			endPage=totalPage;
		}
	}
	//�̷��� ���� �����ʹ� �信�� �̿��ϱ� ���� ���� ���̹Ƿ�
	//getXxx()�� �����ؾ� �Ѵ�
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
