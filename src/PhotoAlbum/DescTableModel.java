package PhotoAlbum;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DescTableModel extends AbstractTableModel{
	
	String ColumnName[] ={ "Title" };
	private List<String> thelist;
	
	DescTableModel(List<String> list){
		thelist = list;
	}

	@Override
	public int getColumnCount() {
		return ColumnName.length;
	}

	@Override
	public int getRowCount() {
		return thelist.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		String obj = thelist.get(row);
		switch(col){
		case 0:
			return (obj);
		default:
			return obj;
		}
		
	}

	@Override
	public Class getColumnClass(int col) {
		return getValueAt(0,col).getClass();
	}

	@Override
	public String getColumnName(int col) {
		return ColumnName[col];
	}

}
