package com.github.catalpaflat.third.model.to;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CatalpaFlat
 */
@Getter
@Setter
public class ThirdLoginWxTO {
    private AppIdWithSecretTO mini;
    private AppIdWithSecretTO h5;
    private AppIdWithSecretTO app;
}
