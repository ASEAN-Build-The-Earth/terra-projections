package net.buildtheearth.terraminusminus.projection.transform;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.buildtheearth.terraminusminus.projection.GeographicProjection;

/**
 * Inverses the warped projection such that x becomes y and y becomes x.
 */
@JsonDeserialize
public class SwapAxesProjectionTransform extends ProjectionTransform {

    /**
     * @param delegate - projection to transform
     */
    @JsonCreator
    public SwapAxesProjectionTransform(
            @JsonProperty(value = "delegate", required = true) GeographicProjection delegate) {
        super(delegate);
    }

    @Override
    public double[] transform(double[] p) {
        double t = p[0];
        p[0] = p[1];
        p[1] = t;
        return p;
    }

    @Override
    public double[] bounds() {
        double[] b = this.delegate.bounds();
        return new double[]{ b[1], b[0], b[3], b[2] };
    }

    @Override
    public String toString() {
        return "Swap Axes(" + super.delegate + ')';
    }
}
