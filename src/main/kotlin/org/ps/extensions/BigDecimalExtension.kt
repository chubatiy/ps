package org.ps.extensions

import java.math.BigDecimal

fun BigDecimal.isEven(): Boolean = this.remainder(BigDecimal(2)).compareTo(BigDecimal.ZERO) == 0