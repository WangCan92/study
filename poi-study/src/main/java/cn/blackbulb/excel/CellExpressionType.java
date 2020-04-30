package cn.blackbulb.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangcan
 */
public enum CellExpressionType {
    FOR_START("\\{for (\\w*) in ((=>(\\w*)\\.(\\w*))*)( (order by( ((\\w*)(.(\\w*))?( (aes|desc))?))*))?\\}"),
    FOR_END("\\{end for\\}"),
    PARAM("\\{([\\w.]*)\\}"),
    FORMAT_PARAM("(\\{formate\\d{2})#([\\w.]*)\\}"),
    FUNCTION("\\{(sum|count|average|min|max)#((=>(\\w*)(\\.(\\w*))?)*)\\}");
    private String expression;
    public static String forStartKey = "(\\w*)\\.(\\w*)";

    CellExpressionType(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public static CellExpressionType getExpressionTypeByString(String s) {
        for (CellExpressionType cellExpressionType : CellExpressionType.values()) {
            Pattern p = Pattern.compile(cellExpressionType.getExpression());
            Matcher matcher = p.matcher(s);
            if (matcher.find()) {
                return cellExpressionType;
            }
        }
        return null;
    }

    public static CellExpressionType getExpressionTypeByCell(Cell cell) {

        if (cell != null && cell.getCellType() == CellType.STRING) {
            return getExpressionTypeByString(cell.getStringCellValue());
        }
        return null;
    }

}
