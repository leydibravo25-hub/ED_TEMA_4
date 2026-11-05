
package com.leydisystems.grafos.controller;

import com.leydisystems.grafos.classes.GraphType;

public class WeightedGraphController extends GraphController {

    @Override
    protected GraphType getGraphType() {
        return GraphType.WEIGHTED;
    }
}
