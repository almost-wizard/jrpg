package ru.rsreu.gorobchenko.project.components;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import ru.rsreu.gorobchenko.project.physics.filter.Filters;

public class StaticColliderComponent extends Component {
    private final SpawnData spawnData;

    public StaticColliderComponent(SpawnData spawnData) {
        this.spawnData = spawnData;
    }

    public StaticColliderComponent(int width, int height) {
        spawnData = new SpawnData();
        spawnData.put("width", width);
        spawnData.put("height", height);
    }

    @Override
    public void onAdded() {
        entity.addComponent(new HitBoxComponent(spawnData));
        PhysicsComponent physicsComponent = new PhysicsComponent();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.setFilter(Filters.WALL.getFilter());
        physicsComponent.setFixtureDef(fixtureDef);
        entity.addComponent(physicsComponent);
    }
}
