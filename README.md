# RuneScape #317 Client
Based on [Moparisthebest's release](https://www.moparisthebest.com/downloads/rs317.rar).

## FAQ (Probably)
- What changes have you made?
    - Removed dummies
    - Removed redundant initializers/assignments
    - Converted C-array style definitions to Java style. (int array[] to int[] array))
    - Inverted or removed empty if statements
    
- How can I be sure you didn't break anything?
    - I am very careful. In other words: you can't.
    
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
        - ClientScript 1

- What are you basing the naming on?
    - Common sense & research on current industry standard definitions. e.g. VertexGroup/FaceGroup not SkinList..
    
- XYZ? XZY?
    - XZY
    - The coordinate system goes as follows:
        - X+ = Right
        - Y- = Up
        - Z+ = Forward
    - To maintain naming between 2D/3D accesses to heightmaps or conversions from world space to 2D space will maintain the same naming scheme, meaning being accessed as X/Z instead of X/Y.
    
# Semantics
- Refactored
    - To change the structure of the code but have the same exact result.
- Renamed
    - To give meaningful names to classes, fields, and methods.
- Clean Deob
    - A clean deob is one that hasn't been refactored or renamed in any way, but has had dummy code removed.