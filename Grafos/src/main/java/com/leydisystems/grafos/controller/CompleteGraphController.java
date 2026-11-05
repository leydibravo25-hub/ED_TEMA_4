
package com.leydisystems.grafos.controller;

import com.leydisystems.grafos.classes.GraphType;

public class CompleteGraphController extends GraphController {

    @Override
    protected GraphType getGraphType() {
        return GraphType.COMPLETE;
    }
}
