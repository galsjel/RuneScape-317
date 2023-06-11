import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameTest {

    public static void main(String[] args) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("combat_level_tag.csv"))) {
            writer.write("a,b,result");
            writer.newLine();
            for (int i = 3; i <= 126; i++) {
                for (int j = 3; j <= 126; j++) {
                    writer.write(String.format("%d,%d,%s", i,j,Game.getCombatLevelColorTag(i,j)));
                    writer.newLine();
                }
            }
        }
    }
}
