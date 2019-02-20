#Things that need to be fixed based off of feedback:
1. ~~Use whitespace to separate logical chunks of code in a method.~~
2. ~~Do not use UPPER_CASING for normal class variables.~~
3. ~~Do not have magic numbers.~~
4. ~~Break very long methods into individual components.~~
5. ~~Verify the scope of variables.~~
6. Consider which methods to make static and non-static.
7. ~~Consider splitting up classes into multiple classes. (Object Decomposition)~~
8. ~~Avoid using network requests for data required for testing~~
    * Consider storing data in local file.
9. ~~Make sure commit messages matches what is in the commit.~~
10. ~~Test the player moving in the game.~~

#Assignment Outline:
- [X] Create a json file for the new adventure game.
    * Initial json file is created.
- [X] Add a timer with a required time the player must complete the game in.
- [X] Add items like three keys required to reach the end room.
    * Four keys created: Sapphire, Amethyst, Diamond, and Outside key.
- [X] Create a tutorial room and script to introduce game mechanics to player.
- [X] Add ability to use command line arguments for running the adventure.
- [X] Make sure methods are not too long and are broken up into individual components.
    * Run Adventure method split up into multiple parts.
- [X] Split up classes into multiple classes.
    * Adventure class split up into three classes so far with one class running the game, 
    and another returning print values.
    * Need to Split up run adventure class even more.
- [X] Make data for testing class local.
    * Data stored in Data folder under file name called adventure.json.
- [X] Change parsing to work with new json format.
    * Tests added for new json format.
    