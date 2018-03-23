package com.github.catalpaflat.third.model.to;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CatalpaFlat
 */
@Getter
@Setter
public class ThirdLoginTO {
    private ThirdLoginWxTO wx;
    private ThirdLoginQQTO qq;
}
