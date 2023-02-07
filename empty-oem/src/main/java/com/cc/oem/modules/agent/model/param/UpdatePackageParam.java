package com.cc.oem.modules.agent.model.param;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代理商查询参数
 *
 * @author chenzj
 * @since 2018/8/7
 */
public class UpdatePackageParam {

    @NotEmpty
    private List<PackageParam> list;

    public List<PackageParam> getList() {
        return list;
    }

    public void setList(List<PackageParam> list) {
        this.list = list;
    }
}

