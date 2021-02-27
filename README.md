# RuneScape #317 Client
Based on [Moparisthebest's release](https://www.moparisthebest.com/downloads/rs317.rar).

## FAQ (Probably)
- What changes have you made?
    - Removed dummies
    - Removed redundant initializers/assignments
    - Converted C-array style definitions to Java style. (int array[] to int[] array))
    - Inverted or removed empty if statements
    
- How can I be sure you didn't break anything?
    - I am very careful. In other words:you can't.
    
- What does this have over other releases?
    - A release with cleaned up code and no refactoring/renaming.
    - Documentation on how certain methods work.
    
- What are your plans for this?
    - Fully renamed, possibly a separate branch after that implementing OpenGL from OSRS's steam client. That thing runs smoooooth.
    - I'd like to have an extensive wiki teaching the inner workings that haven't been covered. Things like:
        - Scene Graph
        - Triangle Sorting
        - Occlusion
        - Animation/Transforms
