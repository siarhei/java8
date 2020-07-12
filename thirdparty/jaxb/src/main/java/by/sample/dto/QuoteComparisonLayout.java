package by.sample.dto;

import by.sample.dto.column.Column;
import by.sample.dto.column.ColumnX;
import by.sample.dto.column.ColumnY;
import by.sample.dto.row.Row;
import by.sample.dto.row.RowA;
import by.sample.dto.row.RowB;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author sshchahratsou
 */
@XmlRootElement
public class QuoteComparisonLayout {

    private List<Row> rows;

    private List<Column> columns;

    //eclipse-link doesn't work with such construction
    /*@XmlElements({
            @XmlElement(name="rowA", type= RowA.class),
            @XmlElement(name="rowB", type= RowB.class),
    })*/
    @XmlElementWrapper
    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @XmlElements({
            @XmlElement(name="columnX", type= ColumnX.class),
            @XmlElement(name="columnY", type= ColumnY.class)
    })
    @XmlElementWrapper
    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
