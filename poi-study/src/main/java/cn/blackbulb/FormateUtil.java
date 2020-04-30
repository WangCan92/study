package cn.blackbulb;

import java.text.NumberFormat;

/**
 * @author wangcan
 */
public class FormateUtil {
    public static Object formateValue(String fromateType, Object value) {
        switch (fromateType) {
            case "format10":
                //负数展示
                Double v = null;
                if (value != null) {
                    v = Double.valueOf(value.toString());
                    v = -v;
                }
                return v;
            case "format11":
                if (value != null) {
                   return "(" + value.toString() + ")";
                }
                break;
            case "format20":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 0);
                }
                break;
            case "fromat21":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 0);
                }
                break;
            case "fromat22":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 2);
                }
                break;
            case "fromat23":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 3);
                }
                break;
            case "fromat24":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 4);
                }
                break;
            case "fromat25":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 5);
                }
                break;
            case "fromat26":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 6);
                }
                break;
            case "fromat27":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 7);
                }
                break;
            case "fromat28":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 8);
                }
                break;
            case "fromat29":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getApproximateValue(dv, 9);
                }
                break;
            case "format30":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return Math.floor(dv);
                }
                break;
            case "format31":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 1);
                }
                break;
            case "format32":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 2);
                }
                break;
            case "format33":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 3);
                }
                break;
            case "format34":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 4);
                }
                break;
            case "format35":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 5);
                }
                break;
            case "format36":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 6);
                }
                break;
            case "format37":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 7);
                }
                break;
            case "format38":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 8);
                }
                break;
            case "format39":
                if (value != null) {
                    Double dv = Double.valueOf(value.toString());
                    return getFloorValue(dv, 9);
                }
                break;
            case "format40": {
                break;

            }

        }
        return null;
    }

    public static Double getApproximateValue(Double d, int dnum) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(dnum);
        return Double.valueOf(nf.format(d));
    }

    public static Double getFloorValue(Double d, int dnum) {
        d = d * Math.pow(10, dnum);
        double floor = Math.floor(d);
        return floor / Math.pow(10, dnum);
    }

}
