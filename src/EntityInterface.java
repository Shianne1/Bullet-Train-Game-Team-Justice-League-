/**
     @Interface: EntityInterface()
     @Function: basic interface used as a template for any similarities between actions that affect entities
     @implementedBy: Player
     @author(s) Carlton Napier
     @added 10/16/2022
  */
public interface EntityInterface {
    void healHealth(int healthModifier); // will show the health
    void takeDamage(int healthModifier); // will show damage
}