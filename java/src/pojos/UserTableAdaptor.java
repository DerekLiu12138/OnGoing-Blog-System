package pojos;

import pojos.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableAdaptor extends AbstractTableModel {
    private List<User> userList;
    private String[] columnNames;

    public UserTableAdaptor(List<User> userList, String[] columnNames) {
        this.userList = userList;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = userList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getUserId();
            case 1:
                return user.getUsername();
            case 2:
                return user.getPassword();
            case 3:
                return user.getRealname();
            case 4:
                return user.getGender();
            case 5:
                return user.getCountry();
            case 6:
                return user.getDescription();
            case 7:
                return user.isAdmin();
            case 8:
                return user.getAuthToken();
            case 9:
                return user.getAvatarUrl();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
