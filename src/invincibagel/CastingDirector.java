package invincibagel;

import java.util.*;

public class CastingDirector {
    private final List<Actor> CURRENT_CAST;  //holds objects currently in the scene, the list is mutable - reference to it is not.
    private final List<Actor> COLLIDE_CHECKLIST;//holds objects that are participating in collision detection
    private final Set<Actor> REMOVED_ACTORS; //hold Actors that need to be removed from the current Scene

    public CastingDirector() {
        this.CURRENT_CAST = new ArrayList<>();
        this.COLLIDE_CHECKLIST = new ArrayList<>();
        this.REMOVED_ACTORS = new HashSet<>();
    }

    // -- CurrentCast methods --
    public List<Actor> getCurrentCast() {
        return CURRENT_CAST;
    }

    public void addCurrentCast(Actor... actors) {
        CURRENT_CAST.addAll(Arrays.asList(actors));
    }

    public void removeCurrentCast(Actor... actors) {
        CURRENT_CAST.removeAll(Arrays.asList(actors));
    }

    public void resetCurrentCast() {
        CURRENT_CAST.clear();
    }

    // -- CollideChecklist methods --
    public List<Actor> getCollideChecklist() {
        return COLLIDE_CHECKLIST;
    }

    public void resetCollideChecklist() {
        COLLIDE_CHECKLIST.clear();
        COLLIDE_CHECKLIST.addAll(CURRENT_CAST);
    }

    // -- RemovedActors methods --
    public Set<Actor> getRemovedActors() {
        return REMOVED_ACTORS;
    }

    public void addToRemovedActors(Actor... actors) {
        if (actors.length > 1) {
            REMOVED_ACTORS.addAll(Arrays.asList(actors));
        } else {
            REMOVED_ACTORS.add(actors[0]);
        }
    }

    public void resetRemovedActors() {
        CURRENT_CAST.removeAll(REMOVED_ACTORS);
        REMOVED_ACTORS.clear();
    }


}
