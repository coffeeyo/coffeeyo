package com.coffeeyo.common.util;

/*
 * �� Ŭ������ ������ ���� ����� ó���ϱ� ����
 * �ʿ��� ������ ����ϰ� �����ϴ� Ŭ�����̴�.
 */
public class PageUtil {
	//�ݵ�� �����ڰ� �˷��־�� �� ����
	private int nowPage;		// ���� ���� ���� ������
	private int totalCount;		// �� �Խù�(����) ��
	
	//����� ���ؼ� �� �˾ƾ��� ����
	private int listCount;		// ���������� ��Ÿ�� �Խù� ��
	private int pageCount;		// ���������� ��Ÿ�� ������ �̵� ����
		
	// ��꿡 ���ؼ� ���Ǿ�� �� ����
	private int totalPage; 	//����������
	private int startPage;	//ȭ�鿡 ǥ���� ���� ��������
	private int endPage;	//ȭ�鿡 ǥ���� ������ ��������
	
	//������
	//�� ȭ�鿡 3���� �Խù��� ���̵��� �ϰ�
	//�� ȭ�鿡�� 3���� ������ �̵������ ���鿹��
	//�����ڰ� �ݵ�� �˷��־���� �ΰ��� �����ʹ� �������Լ��� �̿��Ͽ� �޵��� ����.
	//������ �� ���� �����ʹ� �ϵ��ڵ��ؼ� ó������
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
	
	//�� ��������
	public void calcTotalPage() {
		//�� �Խù�(����)�� / �� �������� ��Ÿ�� �Խù� ��
		//����) ������ �������� �� ���� ���Ƶ� �� �������� �� �ʿ��ϴ�.
		//��)		100/10		10
		//			101/10		11
		this.totalPage = (this.totalCount % this.listCount == 0) ? 
				(this.totalCount / this.listCount) : (this.totalCount / this.listCount)+1;
	}
	
	//ȭ�鿡 ǥ���� ���� ��������
	public void calcStartPage() {
		//�� ȭ��� 5���� �����شٰ� ����
		/* (1,2,3,4,5)					1�׷�
		 * 1,2,3,4�� 5�� ������ �������� �����ϰ� +1 �� �ش�. 
		 * 5/5 = 0 �� ��� ���� �״�� ����Ѵ�.
		 * (6,7,8,9,10)				2�׷�
		 * (11,12,13,14,15)		3�׷�
		 * 
		 * this.startPage = this.nowPage / this.pageCount
		 * */ 
		//�������׷� ���ϱ�
		int pageGroup =  (this.nowPage % this.pageCount == 0) ? 
								(this.nowPage / this.pageCount) : (this.nowPage / this.pageCount) + 1;
				
		/*
		 * 1�׷� 	1		(1-1)*3+1		= 1
		 * 2�׷�	2		(2-1)*3+1		= 4
		 * 3�׷�	3		(3-1)*3+1		= 7
		 */
		
		this.startPage = (pageGroup - 1) * this.pageCount + 1;
	}
	
	//ȭ�鿡 ǥ���� ������ ��������
	public void calcEndPage() {
		// ����������  + ȭ�鿡 ������ �̵��� - 1
		// 5		(1+5) - 1
		// 10	(6+5) - 1
		//����) ������ ȭ�鿡�� �� ���������� �Ѿ ������ ������ �ʾƵ� �ȴ�.
		this.endPage = this.startPage + this.pageCount - 1;
		
		if(this.endPage >= this.totalPage) {
			this.endPage = this.totalPage;
		}
		// �̷��� ���� �����ʹ� �信�� �̿��ϱ� ���ؼ� ���� ���̹Ƿ�
		// getXxx �� �����ؾ� �Ѵ�.
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