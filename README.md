# RuneScape #317 Client
Based on [Moparisthebest's release](https://www.moparisthebest.com/downloads/rs317.rar).

## FAQ (Probably)
- How can I be sure you didn't break anything?
    - I am very careful.
    
# Semantics
- Refactored
    - To change the structure of the code but have the same exact result.
- Renamed
    - To give meaningful names to classes, fields, and methods.
- Clean Deob
    - A clean deob is one that hasn't been refactored or renamed in any way, but has had dummy code removed.
    
# Conventions/Standards
Since there are a lot of different types of coordinates used, I'll be standardizing some acronyms to better identify their usages and ranges using [interval notation](https://en.wikipedia.org/wiki/Interval_(mathematics)#Integer_intervals).

```glsl
int x, y, z;    // Context specific.    see note 1
int mx, my;     // A map square.        (50, 50)            [0...65536]
int tx, ty;     // A tile.              (3222, 3222)        [0...65536]
int zx, zy, zz; // A zone.              (402, 0, 402)       [0...65536]
int ztx, ztz;   // A zone tile.         (3, 3)              [0...7]
int stx, stz;   // A scene tile.        (50, 50)            [0...103]
int sx, sy, sz; // A scene coordinate.  (6400, -104, 6400)  [0...13312] see note 2
```
<sub><sup><sup>1</sup>Description should be inferred by method name or be in comments.</sup></sub><br/>
<sub><sup><sup>2</sup>The y coordinate is generally between 0 and -960 depending on the terrain height. Technically the limit is created by the use of 16.16 fixed point math with sin/cos allowing overflow for scene values over 32768.</sup></sub>