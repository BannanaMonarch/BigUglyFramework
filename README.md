# BigUglyFramework
The BigUgly Framework is a java framework for making games developed by me. Open Source!

This Framework takes standard AWT packages, and uses them in new game specific handlers.
Handlers included in the project are:

SpriteHandler - which handles importing spritesheets, 
    removing alpha-representative colors from the spritesheet (such as greenscreen or magenta colors),
    magnifying sprites, and displaying sprites, as well as cropping margins from a spritesheet, 
    adding tints to a spritesheet, and flipping sprites.
    
AnimationHandler - which controls a SpriteHandler to create and play animations based on the spritesheet, as well
    as flipping tiles when necessary. Control the speed and framecount that your animations use with this nifty handler.
    
Audio - audio is handled through the AudioClip package included in the standard Java SDK, and allows creation of new
    sounds, muting and unmuting, but sadly does not support volume manipulation, because JavaFX scene disclusion from
    the standard Java SDK. I'm still looking for a way around this.
    
PropertyHandler - allows a program to grab values from a ".properties" file and set variables to those values. Currently
    supports int, double and String, but can easily be used for many more data types.

Utility - this handles general game functions, such as FPS, looping, requesting feedback from other 
    Objects in the BigUgly Framework, and more.
    
EnvironmentHandler - Allows environmental layering, layer parallaxing, point anchoring, single environmental object
    handling, foreground and background differentiation, and seamless tileable backgrounds - also with parallax.
    
Player - This handles player's position for tile-constrained games. Soon I will make TilablePlayer and FreePlayer 
    (which will support simple physics rules).
    
UPCOMING ADDITIONS:
EntityInterface - to handle entities such as NPCs, Players, and Enemies.
FreePlayer - to handle physics enabled, platform and otherwise free movement enabled Player objects.
PhysicsHandler, Law - physics such as gravity, wind, buoyancy, acceleration, friction, inertia and weight.
LevelHandler, Level - handles level creation, switching, and loading.
EnemyAI (Wavefront pathfinding, possibly A* or Djiktra Algorithms)
EnemyHandler, Enemy
ItemHandler, Item
InventoryHandler
GUIHandler
ExtensiveDebugger
SaveHandler - handles saving, loading and removing playstate.
MapEditor - A seperate editor that can create maps compatible with the game.
ViewportHandler - handling camera position, switching, and multi-player offset.
WeatherHandler - handling weather events, likelihood of event triggers, weather persistance, viewport shaking,
    environmental effects, effects on maps, and dangers created by weather.
LocalMultiplayer - handling multiple key configurations for players, multiple player interactions, 
    and player look changing.
OnlineMultiplayer - handling hosting and connecting to virtual and physical servers, most likely with UDP and Sockets.
ConsequenceHandler
