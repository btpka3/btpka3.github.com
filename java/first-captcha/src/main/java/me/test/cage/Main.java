package me.test.cage;

import java.io.FileOutputStream;
import java.io.IOException;

import com.github.cage.Cage;
import com.github.cage.GCage;

public class Main {

    public static void main(String[] args) throws IOException {
        Cage cage = new GCage();

        for (int i = 0; i < 10; i++) {
            String str = cage.getTokenGenerator().next();
            cage.draw(str, new FileOutputStream("/tmp/cage_" + str + ".jpg", false));
        }
    }
}
