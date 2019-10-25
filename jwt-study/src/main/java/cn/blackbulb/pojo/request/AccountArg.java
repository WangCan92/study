package cn.blackbulb.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcan
 */
@Data
public class AccountArg implements Serializable {
    private static final long serialVersionUID = -5960951985486594584L;

    private String account;
    private String passWord;
}
