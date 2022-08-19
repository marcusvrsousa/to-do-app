/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Task;

/**
 *
 * @author Marcus Rocha
 */
public class TaskTableModel extends AbstractTableModel{
    
    String[] columns = {"Nome", "Descri��o", "Prazo", "Tarefa Conclu�da", "Editar", "Excluir"};
    List<Task> tasks = new ArrayList();

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int ColumnIndex){
        return columns[ColumnIndex];
    }
    
    @Override
    //permitindo que apenas a coluna 3 seja editavel
    public boolean isCellEditable(int rowIndex, int columnIndex){
        // posso fazer assim: return columnIndex == 3;
        if (columnIndex == 3){
            return true;
        }else
            return false;
        
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        if(tasks.isEmpty()){
            return Object.class;
        }
        return this.getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tasks.get(rowIndex).setIsCompleted((boolean) aValue);

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                //pego o indice da linha (que � um vetor) e o nome que tem nele.
                return tasks.get(rowIndex).getName();
            case 1:
                return tasks.get(rowIndex).getDescription();
            case 2:
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.format(tasks.get(rowIndex).getDeadline());
            case 3:
                return tasks.get(rowIndex).isIsCompleted();
            case 4:
                return "";
            case 5:
                return "";
            default:
                return "Dados n�o encontrados";
        }
    }

    public String[] getColumns() {
        return columns;
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    
    
}
