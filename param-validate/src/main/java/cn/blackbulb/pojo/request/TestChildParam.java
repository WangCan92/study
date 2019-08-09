package cn.blackbulb.pojo.request;

import javax.validation.constraints.NotBlank;

public class TestChildParam {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
