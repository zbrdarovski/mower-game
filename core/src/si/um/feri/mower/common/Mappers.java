package si.um.feri.mower.common;

import com.badlogic.ashley.core.ComponentMapper;

import si.um.feri.mower.ecs.component.BoundsComponent;
import si.um.feri.mower.ecs.component.DimensionComponent;
import si.um.feri.mower.ecs.component.MovementComponent;
import si.um.feri.mower.ecs.component.ObstacleComponent;
import si.um.feri.mower.ecs.component.PositionComponent;
import si.um.feri.mower.ecs.component.TextureComponent;
import si.um.feri.mower.ecs.component.ZOrderComponent;

public final class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor(MovementComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);

    public static final ComponentMapper<ObstacleComponent> OBSTACLE =
            ComponentMapper.getFor(ObstacleComponent.class);

    private Mappers() {
    }
}
