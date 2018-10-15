package com.coffeeyo.product.common;

public class PdpageUtil {
	//�ݵ�� �ʿ��� ����
		private int nowPage;		//���� ���� ���� ������
		private int totalCount;		//�� �Խù�(����) ��
		//���������� ��� ����� ��������, �� �������� ������ �̵������� ǥ��
		private int listCount;		//���������� ��Ÿ�� �Խù� ��
		private int pageCount;		//���������� ��Ÿ�� ������ �̵� ����
		//����(��꿡 ���ؼ� ���Ǿ�� �� ����)
		private int totalPage;		//�� ������ ��
		private int startPage;		//ȭ�鿡 ǥ���� ���� �������� 1,4,7,...
		private int endPage;			//ȭ�鿡 ǥ���� ������ �������� 3,6,9,...
		
		
		
		//������
		//nowPage, totalCount�� ������ �Լ��� ����ϰ� �������� ���� �ڵ��ؼ� ó���Ѵ�.

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
			
			calcTotalPage();		//���������� ���
			calcStartPage();		//���� ������ ���
			calcEndPage();			//������ ������ ���
		}
		
		//���������� ���
		public void calcTotalPage() {
			//����) �������������� �Ѱ��� ���Ƶ� ���������� �� �ʿ��ϴ�.
			//�������� ������ A�� �������� ������ B�� �����϶�� 3�� �����ڸ� Ȱ��
			totalPage=(totalCount%listCount==0)?(totalCount/listCount):(totalCount/listCount)+1;
			System.out.println("totalPage="+totalPage);
		}
		//���� ������ ���
		public void calcStartPage() {
			//�� ȭ��� 5���� �����شٰ� ������ �� 
			//������� ������ nowPage�� ��� �׷쿡 ���ϴ��� �˾Ƴ��� �ش� �׷��� ���۹�ȣ�� ���Ѵ�.
			int pageGroup = (nowPage%pageCount==0)?(nowPage/pageCount):(nowPage/pageCount)+1;
			System.out.println("pageGroup="+pageGroup);
			startPage=(pageGroup-1)*pageCount+1;
			System.out.println("startPage="+startPage);
			
			
			
		}
		//������ ������ ���
		public void calcEndPage() {
			//����������+ȭ�鿡 ������ �̵���-1
			//������ȭ�鿡�� �� ���������� �Ѿ ������ ������ �ʾƵ� �ȴ�.
			endPage=startPage+pageCount-1;
			if(endPage>=totalPage) {
				endPage=totalPage;
			}
		
		}
		//�̷��� ���� �����ʹ� �信�� �̿��ϱ� ���ؼ� ���� ���̹Ƿ� getXxx�� �����ؾ� �Ѵ�.
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
