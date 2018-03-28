package util;

import java.util.List;

/**
 * Created by zeppelin on 8/8/14.
 */
public class Page<T> {
	private int totalRows;
	private List<T> resultList;


	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}
