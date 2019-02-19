#Things that need to be fixed based off of feedback:
1. Use whitespace to separate logical chunks of code in a method.
2. Do not use UPPER_CASING for normal class variables.
3. Do not have magic numbers.
4. Break very long methods into individual components.
5. Verify the scope of variables.
6. Consider which methods to make static and non-static.
7. Consider splitting up classes into multiple classes. (Object Decomposition)
8. Avoid using network requests for data required for testing
    * Consider storing data in local file.
9. Make sure commit messages matches what is in the commit.
10. Test the player moving in the game.

#Assignment Outline:
- [X] Create a json file for the new adventure game.
    * Initial json file is created.
- [ ] Add a timer with a required time the player must complete the game in.
- [ ] Add hidden rooms that the player can access by typing in a direction that is not listed.
- [X] Add items like three keys required to reach the end room.
    * Four keys created: Sapphire, Amethyst, Diamond, and Outside key.
- [ ] Create a tutorial room and script to introduce game mechanics to player.
- [ ] Add monsters like ghosts that the player can fight.
- [ ] Make sure methods are not too long and are broken up into individual components.
- [ ] Split up classes into multiple classes.
- [ ] Make data for testing class local.
- [X] Change parsing to work with new json format.