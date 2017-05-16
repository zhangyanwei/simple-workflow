package com.github.zhangyanwei.workflow.definition.builder.dsl;

import com.github.zhangyanwei.workflow.Maintainer;

public interface MaintainedBy extends Link {

    ViewBy maintainer(Class<? extends Maintainer> maintainerType);

    interface ViewBy<E extends Enum<E>> extends Link {
        ViewBy viewBy(E ... roles);
        ViewBy editBy(E ... roles);
        ViewBy deleteBy(E ... roles);
    }
}
