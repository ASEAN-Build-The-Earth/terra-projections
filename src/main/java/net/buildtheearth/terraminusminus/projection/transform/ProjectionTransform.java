package net.buildtheearth.terraminusminus.projection.transform;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import net.buildtheearth.terraminusminus.projection.GeographicProjection;
import net.buildtheearth.terraminusminus.projection.OutOfProjectionBoundsException;

/**
 * Warps a Geographic projection and applies a transformation to it.
 */
@Getter(onMethod_ = { @JsonGetter })
public abstract class ProjectionTransform implements GeographicProjection {
    protected final GeographicProjection delegate;

    /**
     * @param delegate - projection to transform
     */
    public ProjectionTransform(GeographicProjection delegate) {
        this.delegate = delegate;
    }

    public abstract double[] transform(double[] xy) throws OutOfProjectionBoundsException;

    public double[] inverseTransform(double[] xy) throws OutOfProjectionBoundsException {
        return transform(xy);
    };

    @Override
    public boolean upright() {
        return this.delegate.upright();
    }

    @Override
    public double[] bounds() {
        return this.delegate.bounds();
    }

    @Override
    public double metersPerUnit() {
        return this.delegate.metersPerUnit();
    }

    @Override
    public double[] toGeo(double x, double y) throws OutOfProjectionBoundsException {
        double[] p = inverseTransform(new double[] { x, y });
        return this.delegate.toGeo(p[0], p[1]);
    }

    @Override
    public double[] toGeoNormalized(double lambda, double phi) throws OutOfProjectionBoundsException {
        double[] p = delegate.toGeoNormalized(lambda, phi);
        return inverseTransform(p);
    }

    @Override
    public double[] fromGeoNormalized(double lambda, double phi) throws OutOfProjectionBoundsException {
        double[] p = delegate.fromGeoNormalized(lambda, phi);
        return transform(p);
    }

    @Override
    public double[] fromGeo(double longitude, double latitude) throws OutOfProjectionBoundsException {
        double[] p = this.delegate.fromGeo(longitude, latitude);
        return transform(p);
    }
}
