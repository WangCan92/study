package cn.blackbulb.pojo.request;

import cn.blackbulb.annos.Emails;
import cn.blackbulb.pojo.common.BaseRequest;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class TestParam extends BaseRequest {

    private static final long serialVersionUID = -7078423144841071702L;

    @Size(min = 4, max = 12,message = "姓名格式必须是4-12位")
    private String name;
    @Pattern(regexp = "^[1](([3|5|8][\\\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\\\d]{8}$",message = "手机号码格式不正确")
    private String phoneNum;
    @Email
    private String email;
    @Digits(integer = 5,fraction = 3)
    @NotNull
    private Double money;
    @NotEmpty
    @Size(min = 1,max = 2,message = "list size 不能太小")
    @Valid
    private List<TestChildParam> list;
    @Future
    private Date tomorrow;
    @Past
    private Date yesterday;
    @Emails(message = "邮件哥哥哥哥")
    private String exteralEmails;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<TestChildParam> getList() {
        return list;
    }

    public void setList(List<TestChildParam> list) {
        this.list = list;
    }

    public Date getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(Date tomorrow) {
        this.tomorrow = tomorrow;
    }

    public Date getYesterday() {
        return yesterday;
    }

    public void setYesterday(Date yesterday) {
        this.yesterday = yesterday;
    }

    public String getExteralEmails() {
        return exteralEmails;
    }

    public void setExteralEmails(String exteralEmails) {
        this.exteralEmails = exteralEmails;
    }
}
