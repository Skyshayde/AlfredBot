package io.github.skyshayde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Token {

    /**
     * token txt file organization
     * Discord Bot Token
     */

    public static String getDiscordToken() {
        InputStream tokenStream = new Token().getClass().getClassLoader().getResourceAsStream("tokens.txt");
        try {
            return new BufferedReader(new InputStreamReader(tokenStream)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getGCPToken() {
        InputStream tokenStream = new Token().getClass().getClassLoader().getResourceAsStream("tokens.txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(tokenStream));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
